package pritam.com.homework04;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pritam on 6/21/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridViewHolder> {
    public static List<Channel> mDataset;
    static PlayAudio.Playable playable;
    static Context context;
    static PlayAudio playAudio;

    public GridAdapter(List<Channel> myDataset, MainActivity mainActivity) {
        mDataset = myDataset;
        playable=mainActivity;
        context=mainActivity;
    }


    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //TextView podcastName = (TextView) v.findViewById(R.id.podcast_name);
        GridViewHolder vh = new GridViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        holder.gridTitle.setText(mDataset.get(position).getChannnel_title());
        //holder.postedDate.setText("Posted: "+mDataset.get(position).getChannel_date());
        Picasso.with(holder.itemView.getContext()).load(mDataset.get(position).getChannel_image()).placeholder(R.drawable.loading).into(holder.gridImg);

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
