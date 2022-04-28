package se.ecutbildning.CI_Automatisierung;

import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SingletonWebDriver {
    
    private static SingletonWebDriver thisInstance = null;
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private ThreadLocal<String> sessId = new ThreadLocal<>();
    private ThreadLocal<String> sBrowser= new ThreadLocal<>();
    private ThreadLocal<String> sOS= new ThreadLocal<>();
    private ThreadLocal<String> sVersion= new ThreadLocal<>();
    
    private String getEnv = null;
    
    private SingletonWebDriver() {}
    
    public static SingletonWebDriver getInstance() {
	if ( thisInstance == null)
	    thisInstance = new SingletonWebDriver();
	return thisInstance;
    }
    
    @SafeVarargs
    public final void setWebDriver(String browser, String os, String environ, Map<String, Object>... prefs ) {
	
	switch (browser) {
	case "Firefox":
	    FirefoxOptions browserOptions = new FirefoxOptions()
	    .addPreference("browser.autofocus", true)
	    .addPreference("browser.tabs.remote.autostart.2",false)
	    .addPreference("marionette", true)
	    //	.setPlatformName(Platform.BIG_SUR.name())
	    .setBrowserVersion(GLOBAL_DATA.FIREFOXVERSION)
	    .setHeadless(false);
	    
	    if(environ.equalsIgnoreCase("local")) {
		WebDriverManager.firefoxdriver().setup();
		webDriver.set(new FirefoxDriver(browserOptions));
	    }
	    break;
	}//switch
	getEnv = environ;
	sessId.set(((RemoteWebDriver)webDriver.get()).getSessionId().toString());
	sBrowser.set(browser);
	sOS.set(os);
	
	System.out.println(this.toString());
	getWebDriver().manage().window().maximize();
    }
    public WebDriver getWebDriver() {
   	return webDriver.get();
       }
       
    public void closeDriver() {
	getWebDriver().quit();
    }
    

    public String getSessId() {
        return sessId.get();
    }

    public String getSessionBrowser() {
        return sBrowser.get();
    }

    public String getSessionOS() {
        return sOS.get();
    }

    public String getsSessionVersion() {
        return sVersion.get();
    }

    @Override
    public String toString() {
	return "[Test Environment Details:"
    + ", Session Browser=" + getSessionBrowser() 
    + ", Sesion OS=" + sOS 
    + ", Session Environment=" + getEnv
    //+ ", Selenium version is " + props.getProperty("selenium.revision")
    + "[session ID=" + getSessId()+ "]";
    }
}
