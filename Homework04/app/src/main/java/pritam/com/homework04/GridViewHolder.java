package pritam.com.homework04;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pritam on 6/21/16.
 */
public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView gridTitle;
    public ImageView gridImg;
    public ImageButton gridImgBtn;
    public GridViewHolder(View itemView) {
        super(itemView);
        gridTitle = (TextView) itemView.findViewById(R.id.gridTitle);

        gridImg = (ImageView) itemView.findViewById(R.id.gridChannnelImage);
        gridImgBtn = (ImageButton) itemView.findViewById(R.id.gridPlayButton);
        gridImgBtn.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (v.getId()==R.id.gridPlayButton)
        {
            if (GridAdapter.playAudio!=null)
                GridAdapter.playAudio.cancel(true);
            GridAdapter.playAudio =new PlayAudio(GridAdapter.playable);
            GridAdapter.playAudio.execute(GridAdapter.mDataset.get(position));

        }else {
            if (PlayAudio.mediaPlayer!=null) {
                PlayAudio.mediaPlayer.pause();
                MainActivity.isPlaying=false;
            }
            Intent intent = new Intent(itemView.getContext(),PlayActivity.class);
            intent.putExtra("OBJECT",GridAdapter.mDataset.get(position));
            GridAdapter.playable.startMyActivity(intent);
        }
            //Log.d("Demo","position is "+position+" View is - "+MyAdapter.mDataset.get(position).getChannel_url());

            /*if(playAudio.cancel(false))
                Toast.makeText(itemView.getContext(), "Cannot be Cancelled!!", Toast.LENGTH_SHORT).show();*/


        /**/

    }
}
