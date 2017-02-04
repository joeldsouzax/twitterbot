package TweetEngine;


/**
 * Created by joel on 19/01/17.
 */
public class BotClient {

    private static ReadProperties searchProperties = new ReadProperties("/Users/joel/git/twitterbot/src/main/conf/api.properties");
    public static String[] tweetKeyword = new String[]{"#xaviersIT"};
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
