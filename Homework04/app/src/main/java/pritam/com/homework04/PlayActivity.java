package pritam.com.homework04;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, android.widget.MediaController.MediaPlayerControl {

    MediaPlayer mediaPlayer;
    ProgressDialog progressDialog;
    android.widget.MediaController mediaController;
    Handler handler = new Handler();

    /*@Override
     public void onBackPressed() {
         //super.onBackPressed();
         mediaPlayer.stop();
         mediaPlayer.release();
 //        mediaController.setEnabled(false);
 //        finish();

         //Toast.makeText(PlayActivity.this, "Lauda", Toast.LENGTH_SHORT).show();

         /*if(mediaPlayer != null)
             mediaPlayer.release();
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play2);
        /*progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("wait...");
        progressDialog.show();*/

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        Channel p=(Channel) getIntent().getExtras().getSerializable("OBJECT");

        mediaController = new android.widget.MediaController(this){
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    super.hide();//Hide mediaController
                    finish();//Close this activity
                    return true;//If press Back button, finish here
                }
                //If not Back button, other button (volume) work as usual.
                return super.dispatchKeyEvent(event);
            }
        };

        TextView duration=(TextView)findViewById(R.id.preview_duration);
        TextView description=(TextView)findViewById(R.id.description);
        TextView date=(TextView)findViewById(R.id.publication_date);
        TextView title = (TextView) findViewById(R.id.preview_title);
        ImageView img = (ImageView) findViewById(R.id.preview_img);

        //duration.setText(p.getDuration());
        date.setText(p.getChannel_date());
        description.setText(p.getChannel_description());
        duration.setText("Duration :"+p.getChannel_duration()+" secs");
        title.setText(p.getChannnel_title());
        Picasso.with(this).load(p.getChannel_image()).into(img);


      try {
            Uri uri = Uri.parse(p.getChannel_url());
          ProgressDialog pd =new ProgressDialog(this);
          pd.setMessage("Streaming...");
          pd.show();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
          pd.dismiss();
        } catch(Exception e) {
            System.out.println(e.toString());
        }


    }

    @Override
    public void start() {

        mediaPlayer.start();



    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return false;
    }

    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("TAG", "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.videoView));
       /*VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.
        videoView.setMediaController(mediaController);
*/

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }
}
