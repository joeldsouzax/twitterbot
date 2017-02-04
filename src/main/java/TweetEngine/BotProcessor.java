package TweetEngine;

import twitter4j.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by joel on 25/01/17.
 */
public class BotProcessor extends Auth {

    public static Queue<Status> statusQueue = new LinkedList<Status>();


    public BotProcessor() {
        super();
    }

    public String getStatus(String tweetId){
        String status = null;
        try {
            Status getStatus = super.getTwitter().showStatus(Long.parseLong(tweetId));
                status = "@" + getStatus.getUser().getScreenName()
                        + " - " + getStatus.getText();
        } catch (TwitterException e) {
            e.printStackTrace();
            System.err.print("Failed to search tweets: " + e.getMessage());
        }

        return status;
    }

    public String retweetStatus(String tweetId){
        String status = null;
        try{
            if(!checkRetweet(tweetId)){
                Status getStatus = super.getTwitter().retweetStatus(Long.parseLong(tweetId));
                status ="Status retweeted :"+"@" + getStatus.getUser().getScreenName()
                        + " - " + getStatus.getText();
            }else{
                return null;
            }

        } catch (TwitterException e) {
            System.out.println("Tweet Already Retweeted");
        }
        return status;
    }

    public String favoriteStatus(String tweetId){
        String status = null;
        try{
            if(!cheackFavourite(tweetId)){
                Status getStatus = super.getTwitter().createFavorite(Long.parseLong(tweetId));
                status ="Status favourited :"+"@" + getStatus.getUser().getScreenName()
                        + " - " + getStatus.getText();
                System.out.println(cheackFavourite(tweetId));
            }else{
                return null;
            }

        } catch (TwitterException e) {
            System.out.println("error in Favouriting a tweet");
        }

        return status;
    }


    public void replyTweet(String tweetid,String replyText){
        try{
            Status userStatus = super.getTwitter().showStatus(Long.parseLong(tweetid));
            User tweetUser = userStatus.getUser();
            StatusUpdate replyToStatus = new StatusUpdate("@"+tweetUser.getScreenName()+" "+replyText);
            replyToStatus.inReplyToStatusId(Long.parseLong(tweetid));
            super.getTwitter().updateStatus(replyToStatus);
        }catch (TwitterException e){
            System.out.println("Problem in replying to the tweet");
        }

    }

    public String updateStatus(String statusUpdateText){
        String statusText = null;
        try {
            Status status = super.getTwitter().updateStatus(statusUpdateText);
            statusText = "Successfully updated the status to [ "+status.getText()+" ]";
        } catch (TwitterException e) {
            System.out.println("Duplicate Tweet");
            statusText = "duplicate status update";
        }
        return statusText;
    }

    public String becomeFriends(String tweetId){
        String statusText = null;
        try{
            User tweetUser = super.getTwitter().createFriendship(Long.parseLong(tweetId));
            statusText = "User is : "+tweetUser.getScreenName()+"Whose screen name is "+tweetUser.getScreenName()+" from :"+tweetUser.getLocation();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statusText;
    }

    public boolean checkRetweet(String tweetId){
        boolean retweet = false;
        try{
            Thread.currentThread().sleep(10);
            Status status = super.getTwitter().showStatus(Long.parseLong(tweetId));
            retweet = status.isRetweetedByMe();
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return retweet;
    }

    public boolean cheackFavourite(String tweetId){
        boolean favourite = false;
        try{

            Thread.currentThread().sleep(10);
            Status getstatus = super.getTwitter().showStatus(Long.parseLong(tweetId));
            favourite = getstatus.isFavorited();
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return favourite;
    }

    public String getTimeline(){
        StringBuffer timeLineTweet = new StringBuffer();
        try {
            List<Status> statuses = super.getTwitter().getHomeTimeline();
            System.out.println("Showing home timeline");
            for (Status status:
                    statuses) {
                timeLineTweet.append("@"+status.getUser()+" :"+status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return timeLineTweet.toString();
    }
}