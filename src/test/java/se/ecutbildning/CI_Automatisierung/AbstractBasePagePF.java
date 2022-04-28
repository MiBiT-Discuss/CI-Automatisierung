package se.ecutbildning.CI_Automatisierung;

public abstract class AbstractBasePagePF {

    protected String pageTitle;
    
    public FormPagePF navigateToURL() {
	SingletonWebDriver.getInstance().getWebDriver().navigate().to(GLOBAL_DATA.URL);
	return new FormPagePF();
    }
    
    public void openBrowser(String browserName, String OS, String gridSetting) {
	SingletonWebDriver.getInstance().setWebDriver(browserName, OS, gridSetting);
    }

    public abstract String getPageTitle();
    public abstract void setPageTitle(String pageTitle);
   
}
