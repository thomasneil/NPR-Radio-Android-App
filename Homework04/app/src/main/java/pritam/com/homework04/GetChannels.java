package pritam.com.homework04;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Pritam on 6/20/16.
 */
public class GetChannels extends AsyncTask<String,Void,List<Channel>> {

    Displayable displayable;

    public GetChannels(Displayable displayable) {
        this.displayable = displayable;
    }

    @Override
    protected List<Channel> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status_code = con.getResponseCode();
            if(status_code==HttpURLConnection.HTTP_OK) {
                InputStream io = con.getInputStream();
                return ParserUtil.ChannelParser.getChannels(io);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        displayable.startLoading();
    }

    @Override
    protected void onPostExecute(List<Channel> channels) {
        super.onPostExecute(channels);
        displayable.stopLoading(channels);
    }
}
