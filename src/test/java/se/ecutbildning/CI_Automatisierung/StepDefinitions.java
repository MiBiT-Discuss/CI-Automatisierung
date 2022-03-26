package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * https://us20.admin.mailchimp.com/signup/setup/
 * https://us20.admin.mailchimp.com/
 * */

public class StepDefinitions {

    CucumberHelper helper = new CucumberHelper();
    WebDriver foxyDriver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
    String url;
    String theEmail;
    // d = new ChromeDriver(new ChromeOptions().setHeadless(true));

    @Before
    public void setUp() {
	//System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    }

    @Given("I register as new user")
    public void i_register_as_new_user() throws TooShortPasswordException {

	theEmail = helper.getEmailAddress();
	fillInRegistrationForm(theEmail);
    }

    @And("I register as the same user")
    public void i_register_as_the_same_user() {
	foxyDriver.manage().deleteAllCookies();
	fillInRegistrationForm(theEmail);
    }

    @Given("I want to register as new user with a {int} long name")
    public void i_want_to_register_as_new_user_with_a_length_long_name(int nameLength) {
	String theEmail = helper.getEmailAddressWithANameLengthOf(nameLength);
	fillInRegistrationForm(theEmail);
    }

    @When("I complete registration")
    public void i_complete_registration() {

	Wait<WebDriver> wait = new FluentWait<WebDriver>(foxyDriver).withTimeout(Duration.ofSeconds(30))
		.pollingEvery(Duration.ofMillis(250)).ignoring(ElementClickInterceptedException.class);

	WebElement createAccount = wait.until(new Function<WebDriver, WebElement>() {
	    public WebElement apply(WebDriver driver) {
		return driver.findElement(By.id("create-account"));
	    }
	});
	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-group-container")));
	createAccount.click();
    }

    @Then("I get the message {string}")
    public void i_get_the_message(String message) {
	WebElement signup = null;
	try {
	    signup = findBySelector("#signup-content h1");
	} catch (NoSuchElementException nse) {
	    nse.printStackTrace();
	}
	assertTrue(signup.getText().equalsIgnoreCase(message));
    }

    @Then("I get the error message {string}")
    public void i_get_the_error_message(String message) {

	Wait<WebDriver> wait = new FluentWait<WebDriver>(foxyDriver).withTimeout(Duration.ofSeconds(30))
		.pollingEvery(Duration.ofMillis(250)).ignoring(NoSuchElementException.class);

	WebElement errorMsg = null;
	try {
	    errorMsg = wait.until(new Function<WebDriver, WebElement>() {
		public WebElement apply(WebDriver driver) {
		    return driver.findElement(By.cssSelector("#av-flash-errors"));
		}
	    });
	} catch (NoSuchElementException nse) {
	    nse.printStackTrace();
	}
	System.out.println(errorMsg.getText());
	assertTrue(errorMsg.getText().equalsIgnoreCase(message));
    }

    private void fillInRegistrationForm(String theEmail) {
	url = String.format("https://login.mailchimp.com/signup/");
	foxyDriver.get(url);
	foxyDriver.manage().window().setSize(new Dimension(829, 854));
	
	if(! (theEmail.isEmpty()) ) {
	findById("email").sendKeys(theEmail);
	findById("new_username").sendKeys(StringUtils.substringBefore(theEmail, "@"));
	} else {
	    findById("new_username").sendKeys(
		    			Stream.generate(new NonSensicalWordGenerator())
		    			.limit(ThreadLocalRandom.current()
		    			.nextInt(5, 13))
		    			.collect(Collectors.joining()
		    			));
	}
	findById("new_password").sendKeys(helper.getPassword());

	findById("marketing_newsletter").click();
	findById("onetrust-reject-all-handler").click();
    }

    private WebElement findById(String _id) {
	return foxyDriver.findElement(By.id(_id));
    }

    private WebElement findBySelector(String _sel) {
	return foxyDriver.findElement(By.cssSelector(_sel));
    }

    @After
    public void tearDown() {
	theEmail = null;
	foxyDriver.manage().deleteAllCookies();
	foxyDriver.close();
    }

}
