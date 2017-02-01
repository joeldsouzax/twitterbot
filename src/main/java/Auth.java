package src.main.java;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by joel on 28/01/17.
 */
abstract public class Auth {

    private TwitterFactory tweetFact;
    private Twitter twitter;
    private TwitterStream tweetStream;
    private ReadProperties twitterProperties = new ReadProperties("src/main/conf/api.properties");

    public Auth() {
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setDebugEnabled(Boolean.parseBoolean(twitterProperties.getProperty("debug")));
        conf.setOAuthConsumerKey(twitterProperties.getProperty("twitter.consumer.api.key"));
        conf.setOAuthConsumerSecret(twitterProperties.getProperty("twitter.consumer.secret.api"));
        conf.setOAuthAccessToken(twitterProperties.getProperty("twitter.access.token"));
        conf.setOAuthAccessTokenSecret(twitterProperties.getProperty("twitter.access.token.secret"));

        tweetFact = new TwitterFactory(conf.build());
        twitter = tweetFact.getInstance();

    }

    public void tweetStreamStart(){
        ConfigurationBuilder streamConf = new ConfigurationBuilder();
        streamConf.setDebugEnabled(Boolean.parseBoolean(twitterProperties.getProperty("debug")));
        streamConf.setOAuthConsumerKey(twitterProperties.getProperty("twitter.consumer.api.key"));
        streamConf.setOAuthConsumerSecret(twitterProperties.getProperty("twitter.consumer.secret.api"));
        streamConf.setOAuthAccessToken(twitterProperties.getProperty("twitter.access.token"));
        streamConf.setOAuthAccessTokenSecret(twitterProperties.getProperty("twitter.access.token.secret"));
        tweetStream = new TwitterStreamFactory(streamConf.build()).getInstance();
    }




    public Twitter getTwitter() {
        return twitter;
    }

    public TwitterStream getTweetStream() {
        return tweetStream;
    }

    public ReadProperties getTwitterProperties() {
        return twitterProperties;
    }

}
