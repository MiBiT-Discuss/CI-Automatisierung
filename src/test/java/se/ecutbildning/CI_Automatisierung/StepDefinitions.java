package se.ecutbildning.CI_Automatisierung;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    WebDriver foxyDriver = new FirefoxDriver(new FirefoxOptions().setHeadless(false));
    String theEmail;
    int invalidMinimum = 101;
    // d = new ChromeDriver(new ChromeOptions().setHeadless(true));

    @Before
    public void setUp() {
	foxyDriver.manage().deleteAllCookies();
	// System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    }

    @Given("a {string} name length")
    public void a_valid_name_length(String lengthType) {

	if (lengthType.equalsIgnoreCase("zero"))
	    theEmail = helper.getEmailAddressWithANameLengthOf(0);
	else if (lengthType.equalsIgnoreCase("too long")) {
	    int nameLength = ThreadLocalRandom.current().nextInt(invalidMinimum, (invalidMinimum + 24));
	    theEmail = helper.getEmailAddressWithANameLengthOf(nameLength);
	} else {
	    theEmail = helper.getEmailAddress();
	}
    }

    @And("a {string} email")
    public void a_normal_email(String emailStatus) {
	fillInRegistrationForm(theEmail);
    }

    @And("I register as the same user")
    public void i_register_as_the_same_user() {
	foxyDriver.manage().deleteAllCookies();
	fillInRegistrationForm(theEmail);
    }

    @When("I complete registration")
    public void i_complete_registration() {

	Wait<WebDriver> wait = getWait(30);

	WebElement createAccount;
	try {
	    createAccount = wait.until(new Function<WebDriver, WebElement>() {
		public WebElement apply(WebDriver driver) {
		    return driver.findElement(By.id("create-account"));
		}
	    });
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-group-container")));
	    createAccount.click();
	} catch (NoSuchElementException e) {
	    e.printStackTrace();
	    throw new AssertionError("The requested element couldn't be found.");
	}

    }

    @Then("I get the {string} message")
    public void i_get_the_message(String expected) {
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

	String actual = result.getText().substring(0, expected.length());
	System.out.println(actual);
	assertTrue(actual.equalsIgnoreCase(expected));
    }

    private void fillInRegistrationForm(String theEmail) {
	String url = String.format("https://login.mailchimp.com/signup/");
	foxyDriver.get(url);
	// foxyDriver.manage().window().maximize();;

	if (!(theEmail.isEmpty())) {
	    findById("email").sendKeys(theEmail);
	    findById("new_username").sendKeys(StringUtils.substringBefore(theEmail, "@"));
	} else {
	    findById("new_username").sendKeys(Stream.generate(new NonSensicalWordGenerator())
		    .limit(ThreadLocalRandom.current().nextInt(5, 13)).collect(Collectors.joining()));
	}
	findById("new_password").sendKeys(helper.getPassword());

	try {
	    findById("marketing_newsletter").click();
	    findById("onetrust-reject-all-handler").click();
	} catch (ElementNotInteractableException nse) {
	    nse.printStackTrace();
	}
    }

    private WebElement findById(String _id) {
	return foxyDriver.findElement(By.id(_id));
    }

    private WebElement findBySelector(String _sel) {
	return foxyDriver.findElement(By.cssSelector(_sel));
    }

    private FluentWait<WebDriver> getWait(int sec) {
	return new FluentWait<WebDriver>(foxyDriver).withTimeout(Duration.ofSeconds(sec))
		.pollingEvery(Duration.ofMillis(250)).ignoring(ElementClickInterceptedException.class);
    }

    @After
    public void tearDown() {
	theEmail = null;
	foxyDriver.manage().deleteAllCookies();
	foxyDriver.close();
    }

}
