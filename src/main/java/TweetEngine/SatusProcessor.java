package TweetEngine;


import RaspBerryLED.LED;
import com.wolfram.alpha.*;
import org.apache.commons.lang3.StringUtils;
import twitter4j.Status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joel on 28/01/17.
 */
public class SatusProcessor implements Runnable{

    private BotProcessor tweetProcessor = new BotProcessor();
    private ReadProperties twitterProperties = new ReadProperties("/Users/joel/git/twitterbot/src/main/conf/api.properties");
    private Boolean gotResponse = false;
    private String appid;
    //LED red = new LED(1);
    //LED green = new LED(7);

    public SatusProcessor() {
        appid = twitterProperties.getProperty("wolfram.alpha.appId");
    }

    synchronized public void processStatus() throws InterruptedException{

        if (!BotProcessor.statusQueue.isEmpty()){
            Status status = BotProcessor.statusQueue.element();
            String StatusText = status.getText().toLowerCase();
            String processedText = cleanText(StatusText,BotClient.tweetKeyword);
            System.out.println(processedText);
            if(status.getText().toLowerCase().contains("retweet")){
                tweetProcessor.retweetStatus(String.valueOf(status.getId()));
                //red.tweet();
                BotProcessor.statusQueue.remove();
            }else if (status.getText().toLowerCase().contains("favourite")){
                tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
                //green.tweet();
                BotProcessor.statusQueue.remove();
            }else if(status.getText().toLowerCase().contains("blink")){
                System.out.println("Check me out");
                BotProcessor.statusQueue.remove();
            }else if(status.getText().toLowerCase().contains("?")){
                List<String> StringResponse = wolfQuestion(processedText);
                if (StringResponse!=null){
                    for (String Response:
                            StringResponse) {

                        gotResponse = false;
                        String finalResponse = null;
                        if(Response.length() >139){
                            finalResponse = Response.substring(0,139);
                        }else{
                            finalResponse = Response;
                        }
                        System.out.println("This is the cut response! \n"+finalResponse);
                        //red.pulse();
                        //green.pulse();
                        tweetProcessor.replyTweet(String.valueOf(status.getId()),finalResponse);

                    }
                }else{
                    tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
                    //green.tweet();
                }
                BotProcessor.statusQueue.remove();
            }else {
                tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
                //green.tweet();
                BotProcessor.statusQueue.remove();
            }
        }
    }

    public void run() {
        while (true){
            try {
                processStatus();
                //BotProcessor.killThread.add(Date.from(Instant.now()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> wolfQuestion(String question){
        List<String> stringResponse = new ArrayList<String>();
        StringBuilder response = new StringBuilder();
        WAEngine engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat("plainText");
        WAQuery query = engine.createQuery();
        query.setInput(question);
        try{
            System.out.println("Query URL");
            System.out.println(engine.toURL(query));
            System.out.println("");

            WAQueryResult queryResult = engine.performQuery(query);
            StringBuffer xmlData = new StringBuffer();
            xmlData.append(queryResult.getXML());
            System.out.println(xmlData);
            if (queryResult.isError()){
                System.out.println("Query Error");
                System.out.println(" error code :"+queryResult.getErrorCode());
                System.out.println(" error message : "+queryResult.getErrorMessage());
            }else if (!queryResult.isSuccess()){
                System.out.println("Query was not understood; no results available");
                stringResponse.add("Query was not understood; no results available");
                gotResponse = true;
            }else {
                System.out.println("Successful query, Pods follow: ");
                for (WAPod pod: queryResult.getPods()) {
                    if(!pod.isError()){
                        System.out.println(pod.getTitle());
                        System.out.println("-------------");
                        for (WASubpod subpod:pod.getSubpods()) {
                            for (Object elements: subpod.getContents()) {
                                if (elements instanceof WAPlainText){
                                    System.out.println(((WAPlainText) elements).getText());
                                    String check = ((WAPlainText) elements).getText().trim();
                                    if(!StringUtils.isNotBlank(check)){
                                        continue;
                                    }
                                    stringResponse.add(((WAPlainText) elements).getText().trim());
                                    gotResponse = true;
                                    System.out.println("This is the response \n");
                                    System.out.println("-========================");

                                }
                            }
                        }
                        System.out.println("");
                    }
                }
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
        System.out.println(response.toString());
        stringResponse.remove(0);
        if(gotResponse){
            return stringResponse;
        }else{
            return null;
        }

    }

    public String cleanText(String tweetText, String[] tweetKeywords){
        String content = tweetText;
        for (String v: tweetKeywords) {
            content = content.replace(v,"");
        }
        return content;
    }

}
