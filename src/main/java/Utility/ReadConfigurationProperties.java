package Utility;

import java.util.ResourceBundle;

public class ReadConfigurationProperties implements GlobalObjects{

    public static String apidatapath;
    public static String testpostpath;
    public static String mentionflowpath;

    public void  retrieve_configproperties(){

        ResourceBundle sb  = ResourceBundle.getBundle("properties.config");
        apidatapath = (String) sb.getObject("APIDATAPATH");
        testpostpath = (String) sb.getObject("TESTPOSTPATH");
        mentionflowpath = (String) sb.getObject("MENTIONFLOWPATH");




    }
}
