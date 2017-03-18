package pritam.com.homework04;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Displayable,PlayAudio.Playable{
    public static ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private ImageButton playerButton;
    private LinearLayout player;
    public static boolean isPlaying=false;
    private RecyclerView recyclerView;
    public static MediaPlayer mediaPlayer;
    public boolean isLinear=true;
    public List<Channel> channelList;
    private RecyclerView.Adapter linearAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private RecyclerView.Adapter gridAdapter;
    private RecyclerView.LayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar= (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("TED Videos");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        /* RecyclerView.Adapter adapter = new MyAdapter(new String[]{"Fucker1","Fucker2","Fucker3","Fucker4","Fucker5","Fucker6"});
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/
        mediaPlayer = new MediaPlayer();
        progressBar = (ProgressBar) findViewById(R.id.playBar);
        playerButton = (ImageButton) findViewById(R.id.playerButton);
        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer=PlayAudio.mediaPlayer;
                if (isPlaying)
                {
                    mediaPlayer.pause();
                    //recyclerView.setEnabled(true);
                    playerButton.setImageResource(android.R.drawable.ic_media_play);
                    isPlaying=false;
                }
                else
                {
                    mediaPlayer.start();
                    //recyclerView.setEnabled(false);
                    playerButton.setImageResource(android.R.drawable.ic_media_pause);
                    isPlaying=true;
                }
            }
        });
        player = (LinearLayout) findViewById(R.id.playerView);

        new GetChannels(MainActivity.this).execute("http://www.npr.org/rss/podcast.php?id=510298");
    }

    @Override
    public void startLoading() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Channels...");
        progressDialog.setTitle("TED");
        //progressDialog.setIcon(android.R.drawable.ic_media_play);
        progressDialog.setCancelable(false);
        progressDialog.show();
        isPlaying=true;
    }

    @Override
    public void stopLoading(List<Channel> channels) {
        progressDialog.dismiss();
        channelList=channels;
        Log.d("Demo",channels.toString());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearAdapter = new MyAdapter(channels,this);
        gridAdapter = new GridAdapter(channels,this);

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this,2);
        //new GridLayoutManager(this,2);//
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearAdapter);


    }

    @Override
    public void playAudio() {
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        player.setVisibility(View.VISIBLE);
        playerButton.setImageResource(android.R.drawable.ic_media_pause);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        recyclerView.setEnabled(false);
        //mediaPlayer.stop();
    }

    @Override
    public void stopAudio(Boolean aBoolean) {
        //mediaPlayer.stop();
        //mediaPlayer.release();
        player.setVisibility(View.INVISIBLE);

        //recyclerView.setLayoutFrozen(false);
    }

    @Override
    public void showProgress(int progress) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        progressBar.setProgress(progress);
        //Log.d("Progress",""+progress);
    }

    @Override
    public void startMyActivity(Intent intent) {
       // progressDialog.show();
        startActivity(intent);
    }

    @Override
    public MediaPlayer getMediaPlayer() {
            return new MediaPlayer();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if (isLinear){
                    /*RecyclerView.Adapter adapter = new GridAdapter(channelList,this);

                    //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);*/
                    //new GridLayoutManager(this,2);//
                    GridAdapter.playAudio=MyAdapter.playAudio;

                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(gridAdapter);
                    isLinear=false;
                    //recyclerView
                }
                else {
                    /*RecyclerView.Adapter adapter = new MyAdapter(channelList,this);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                    //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
                    //new GridLayoutManager(this,2);//*/
                    MyAdapter.playAudio=GridAdapter.playAudio;
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(linearAdapter);
                    isLinear=true;
                }
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
