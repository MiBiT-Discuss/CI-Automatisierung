package se.ecutbildning.CI_Automatisierung;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class FormPagePF extends AbstractBasePagePF {

    CucumberHelper helper = CucumberHelper.getThisHelperInstance();

    protected void fillInRegistrationForm(String theEmail) {

	if (!(theEmail.isEmpty())) {
	    findById("email").sendKeys(theEmail);
	    findById("new_username").sendKeys(StringUtils.substringBefore(theEmail, "@"));
	} else {
	    findById("new_username").sendKeys(Stream.generate(new NonSensicalWordGenerator())
		    .limit(ThreadLocalRandom.current().nextInt(5, 13)).collect(Collectors.joining()));
	}
	findById("new_password").sendKeys(helper.getPassword());
    }

    private WebElement findById(String _id) {
	return SingletonWebDriver.getInstance().getWebDriver().findElement(By.id(_id));
    }

    private WebElement findBySelector(String _sel) {
	return SingletonWebDriver.getInstance().getWebDriver().findElement(By.cssSelector(_sel));
    }

    public void completeRegistration() {
	Wait<WebDriver> wait = getWait(60);

	WebElement createAccount;
	try {
	    createAccount = wait.until(new Function<WebDriver, WebElement>() {
		public WebElement apply(WebDriver driver) {
		    return driver.findElement(By.id("create-account"));
		}
	    });
	    // clickBait();
	    // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-group-container")));
	    wait.until(ExpectedConditions.elementToBeClickable(createAccount));
	    createAccount.click();
	} catch (NoSuchElementException | ElementNotInteractableException e) {
	    e.printStackTrace();
	    throw new AssertionError("The requested element \"create-account\" couldn't be found.");
	}

    }//

    protected FluentWait<WebDriver> getWait(int sec) {
	return new FluentWait<WebDriver>(SingletonWebDriver.getInstance().getWebDriver())
		.withTimeout(Duration.ofSeconds(sec)).pollingEvery(Duration.ofMillis(250))
		.ignoring(ElementClickInterceptedException.class);
    }

    private void clickBait() {
	Wait<WebDriver> wait = getWait(60);
	WebElement oneTrust = null;
	try {
	    // findById("onetrust-reject-all-handler").click();
	    oneTrust = wait.until(new Function<WebDriver, WebElement>() {
		public WebElement apply(WebDriver driver) {
		    return driver.findElement(By.cssSelector("#onetrust-accept-btn-handler"));
		}
	    });
	    if (oneTrust.isDisplayed())
		oneTrust.click();
	    // findById("onetrust-accept-btn-handler").click();
	} catch (ElementNotInteractableException nse) {
	    nse.printStackTrace();
	}
    }

    public WebElement getElement(String expected) {
	WebElement result = null;
	if (expected.equalsIgnoreCase("Check your email")) {
	    try {
		result = findBySelector("#signup-content h1");
	    } catch (NoSuchElementException nse) {
		nse.printStackTrace();
	    }
	} else {
	    Wait<WebDriver> wait = getWait(20);
	    try {
		result = wait.until(new Function<WebDriver, WebElement>() {
		    public WebElement apply(WebDriver driver) {
			return driver.findElement(By.cssSelector("#signup-form .invalid-error"));
		    }
		});
	    } catch (NoSuchElementException nse) {
		nse.printStackTrace();
	    }
	}
	return result;
    }//

    @Override
    public String getPageTitle() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setPageTitle(String pageTitle) {
	// TODO Auto-generated method stub

    }

}
