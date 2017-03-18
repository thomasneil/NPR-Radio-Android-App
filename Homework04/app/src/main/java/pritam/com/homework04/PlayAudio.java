package pritam.com.homework04;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Pritam on 6/20/16.
 */
public class PlayAudio extends AsyncTask<Channel,Integer,Boolean> {
    Playable playable;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        playable.showProgress((values[1]*100)/values[0]);
        //Log.d("Progress","Current Position - "+values[1]+" and Duration is - "+values[0]);

    }

    public PlayAudio(Playable playable) {
        this.playable=playable;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        playable.getMediaPlayer().seekTo(playable.getMediaPlayer().getDuration());
        Log.d("onCancelled","Trying to cancel");
    }

    @Override
    protected Boolean doInBackground(Channel... params) {
        String url = params[0].getChannel_url(); // your URL here
        //MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.d("URL",params[0].getChannel_url());
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
                while (mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration() && !isCancelled()) {

                        //Thread.sleep(10000);
                        publishProgress(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());

                }
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (IOException e) {
                e.printStackTrace();
            } /*catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // playable.stopAudio(true);
        playable.playAudio();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        playable.stopAudio(aBoolean);
    }


    public interface Playable{
        public void playAudio();
        public void stopAudio(Boolean aBoolean);
        public void showProgress(int progress);
        public void startMyActivity(Intent intent);
        public MediaPlayer getMediaPlayer();

    }



}
