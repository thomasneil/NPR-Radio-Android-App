package pritam.com.homework04;

import java.io.Serializable;

/**
 * Created by Pritam on 6/20/16.
 */
public class Channel implements Serializable {
    String channnel_title, channel_description, channel_url,channel_image, channel_date;
    int channel_duration;


    public String getChannnel_title() {
        return channnel_title;
    }

    public String getChannel_description() {
        return channel_description;
    }

    public String getChannel_url() {
        return channel_url;
    }

    public String getChannel_image() {
        return channel_image;
    }

    public String getChannel_date() {
        return channel_date;
    }

    public int getChannel_duration() {
        return channel_duration;
    }

    public void setChannnel_title(String channnel_title) {
        this.channnel_title = channnel_title;
    }

    public void setChannel_description(String channel_description) {
        this.channel_description = channel_description;
    }

    public void setChannel_url(String channel_url) {
        this.channel_url = channel_url;
    }

    public void setChannel_image(String channel_image) {
        this.channel_image = channel_image;
    }

    public void setChannel_date(String channel_date) {
        this.channel_date = channel_date;
    }

    public void setChannel_duration(int channel_duration) {
        this.channel_duration = channel_duration;
    }


    @Override
    public String toString() {
        return "Channel{" +
                "channnel_title='" + channnel_title + '\'' +
                ", channel_description='" + channel_description + '\'' +
                ", channel_url='" + channel_url + '\'' +
                ", channel_image='" + channel_image + '\'' +
                ", channel_date='" + channel_date + '\'' +
                ", channel_duration=" + channel_duration +
                '}';
    }
}
