package pritam.com.homework04;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pritam on 6/20/16.
 */
public class ParserUtil {

    public static class ChannelParser{
        public static List<Channel> getChannels(InputStream io) throws XmlPullParserException, IOException {
            List channelList = new ArrayList<Channel>();
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = parserFactory.newPullParser();
            xmlPullParser.setInput(io,"UTF-8");
            Channel channel=null;
            int event =xmlPullParser.getEventType();
            Log.d("Demo","Starting Parsing");
            while(!(event== XmlPullParser.START_TAG && xmlPullParser.getName().equalsIgnoreCase("item")))
                    event=xmlPullParser.next();
            while (event!=XmlPullParser.END_DOCUMENT)
            {

                switch (event){
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equalsIgnoreCase("title"))
                        {
                            channel = new Channel();
                            String title=xmlPullParser.nextText().trim();
                            channel.setChannnel_title(title);
                            //Log.d("Demo",title);
                        }
                        else if (xmlPullParser.getName().equalsIgnoreCase("description"))
                        {
                            channel.setChannel_description(xmlPullParser.nextText().trim());
                        }
                        else if (xmlPullParser.getName().equalsIgnoreCase("pubDate"))
                        {
                            channel.setChannel_date(xmlPullParser.nextText().substring(0,16).trim());
                        }
                        else if (xmlPullParser.getName().equalsIgnoreCase("image"))
                        {
                            channel.setChannel_image(xmlPullParser.getAttributeValue(null,"href"));

                        }
                        else if (xmlPullParser.getName().equalsIgnoreCase("duration"))
                        {
                            channel.setChannel_duration(Integer.parseInt(xmlPullParser.nextText().trim()));

                        }
                        else if (xmlPullParser.getName().equalsIgnoreCase("enclosure"))
                        {
                            channel.setChannel_url(xmlPullParser.getAttributeValue(null,"url"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equalsIgnoreCase("item"))
                        {
                            channelList.add(channel);
                        }
                        break;

                }

                event= xmlPullParser.next();
            }




            return channelList;
        }
    }
}
