import twitter4j.*;

/**
 * Created by joel on 28/01/17.
 */
public class StreamTwitter extends Auth implements Runnable {

    private String[] tweetKeywords;
    private String[] language;
    private String tweetId;


    public StreamTwitter(String[] tweetKeywords, String[] language) {
        super.tweetStreamStart();
        this.tweetKeywords = tweetKeywords;
        this.language = language;
    }

    synchronized public void StreamingStatuses(){

        super.getTweetStream().addListener(new StatusListener() {
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                System.out.println(status.getText());
                BotProcessor.statusQueue.add(status);

            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice){
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int i) {
                System.out.println("Got track limitation notice:" + i);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning stallWarning) {
                System.out.println("Got stall warning:" + stallWarning);
            }

            public void onException(Exception e) {
                e.printStackTrace();
            }
        });

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(tweetKeywords);
        tweetFilterQuery.language(language);
        super.getTweetStream().filter(tweetFilterQuery);
    }

    public void run() {
        StreamingStatuses();
    }


    public void passStatus(Status status){
        tweetId = String.valueOf(status.getId());
    }
}
