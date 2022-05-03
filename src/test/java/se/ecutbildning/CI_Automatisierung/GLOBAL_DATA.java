package se.ecutbildning.CI_Automatisierung;

import java.io.*;

public class GLOBAL_DATA {
    

    //driver class default values
    public static String propFile = "src/test/resources/selenium.properties";
    public static final String SE_PROPERTIES = new File(propFile).getAbsolutePath();
    public static final String URL = "https://login.mailchimp.com/signup/";
    public static final String BROWSER = "Firefox";
    public static final String OS = "macOS";
    public static final String ENVIRONMENT = "local";
    public static int INVALIDMINUMUM = 101;
    public static String FIREFOXVERSION = "99";

}
