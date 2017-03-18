package pritam.com.homework04;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Pritam on 6/19/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    public static List<Channel> mDataset;
    static PlayAudio.Playable playable;
    static Context context;
    static PlayAudio playAudio;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Channel> myDataset, MainActivity mainActivity) {
        mDataset = myDataset;
        playable=mainActivity;
        context=mainActivity;
        //playAudio=new PlayAudio(playable);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //TextView podcastName = (TextView) v.findViewById(R.id.podcast_name);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getChannnel_title());
        holder.postedDate.setText("Posted: "+mDataset.get(position).getChannel_date());
        Picasso.with(holder.itemView.getContext()).load(mDataset.get(position).getChannel_image()).into(holder.channelImg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView,postedDate;
        public ImageView channelImg;
        public ImageButton play_button;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.podcast_name);
            postedDate = (TextView) v.findViewById(R.id.posted_date);
            channelImg = (ImageView) v.findViewById(R.id.channel_img);
            play_button = (ImageButton) v.findViewById(R.id.play_button);
            play_button.setOnClickListener(this);
            v.setOnClickListener(this);

        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.play_button)
            {
                int position = getAdapterPosition();
            Log.d("Demo","position is "+position+" View is - "+MyAdapter.mDataset.get(position).getChannel_url());
                if (playAudio!=null)
                    playAudio.cancel(true);
                playAudio =new PlayAudio(MyAdapter.playable);
            playAudio.execute(MyAdapter.mDataset.get(position));
            }
            else {
                if (PlayAudio.mediaPlayer!=null) {
                    PlayAudio.mediaPlayer.pause();
                    MainActivity.isPlaying=false;
                }
                Intent intent = new Intent(itemView.getContext(),PlayActivity.class);
                intent.putExtra("OBJECT",MyAdapter.mDataset.get(getAdapterPosition()));
                playable.startMyActivity(intent);
            }

            Log.d("MAD",v.getId()+ " and - button id is -"+ R.id.play_button);

            //MyAdapter.context.startActivity(intent);

        }
    }
}