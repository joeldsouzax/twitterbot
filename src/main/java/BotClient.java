package src.main.java;

import org.apache.commons.codec.binary.Base64;
import twitter4j.Status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by joel on 19/01/17.
 */
public class BotClient {

    private static ReadProperties searchProperties = new ReadProperties("src/main/conf/api.properties");
    public static String[] tweetKeyword = new String[]{"#joeltestingouthisbot"};
    public static String[] language = new String[]{"en"};


    public static void main(String[] args) throws InterruptedException {

        StreamTwitter stream = new StreamTwitter(tweetKeyword,language);
        Thread StreamingTwitter = new Thread(stream);
        StreamingTwitter.setName("StreamigTwitter");
        StreamingTwitter.start();
        Thread main = Thread.currentThread();
        main.setName("Main");
        main.sleep(1000);
        SatusProcessor satusProcessor = new SatusProcessor();
        Thread statusProcessThread = new Thread(satusProcessor);
        statusProcessThread.setName("statusProcessor");
        statusProcessThread.start();

    }
}
