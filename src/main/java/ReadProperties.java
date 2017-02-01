import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by joel on 19/01/17.
 */
public class ReadProperties {
    /*Sepcifying the property file path name in a String variable, to load it and read.
    * */
    private String filename ;
    private Properties botProperties;
    /*
    Logging mechanism to give detailed output in logs file, which helps in debugging a large enterprise program.
     */

    public ReadProperties() {
        /*
        Hardcoded file name in the default constructor
         */
        filename = "src/main/conf/config.properties";
        try {
            /*
            Instantiating the object of FileReader class and passing the path were property file is present.
             */
            FileInputStream propertyFile = new FileInputStream(filename);

            /*
            Creating a Java Properties object instance to load all the properties from the file into this objet.
             */
            botProperties = new Properties();
            botProperties.load(propertyFile);
            propertyFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ReadProperties(String filename) {
        this.filename = filename;
        try {
            /*
            Instantiating the object of FileReader class and passing the path were property file is present.
             */
            FileInputStream propertyFile = new FileInputStream(filename);

            /*
            Creating a Java Properties object instance to load all the properties from the file into this objet.
             */
            botProperties = new Properties();
            botProperties.load(propertyFile);
            propertyFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    gettng a particular property by passing its key
     */

    public String getProperty(String key){
        return botProperties.getProperty(key);
    }

}
