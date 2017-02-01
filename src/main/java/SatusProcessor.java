
import com.wolfram.alpha.*;
import org.apache.commons.lang3.StringUtils;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joel on 28/01/17.
 */
public class SatusProcessor implements Runnable{

    private BotProcessor tweetProcessor = new BotProcessor();
    private ReadProperties twitterProperties = new ReadProperties("src/main/conf/api.properties");
    private Boolean gotResponse = false;
    private String appid;

    public SatusProcessor() {
        appid = twitterProperties.getProperty("wolfram.alpha.appId");
    }

    synchronized public void processStatus(){

        if (!BotProcessor.statusQueue.isEmpty()){
            Status status = BotProcessor.statusQueue.element();
            String StatusText = status.getText().toLowerCase();
            String processedText = cleanText(StatusText,BotClient.tweetKeyword);
            System.out.println(processedText);
            if(status.getText().toLowerCase().contains("retweet")){
                tweetProcessor.retweetStatus(String.valueOf(status.getId()));
                BotProcessor.statusQueue.remove();
            }else if (status.getText().toLowerCase().contains("favourite")){
                tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
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
                        tweetProcessor.replyTweet(String.valueOf(status.getId()),finalResponse);

                    }
                }else{
                    tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
                }
                BotProcessor.statusQueue.remove();
            }else {
                tweetProcessor.favoriteStatus(String.valueOf(status.getId()));
                BotProcessor.statusQueue.remove();
            }
        }
    }

    public void run() {
        while (true){
            processStatus();
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
