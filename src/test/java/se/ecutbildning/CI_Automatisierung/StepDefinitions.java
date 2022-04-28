package se.ecutbildning.CI_Automatisierung;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * https://us20.admin.mailchimp.com/signup/setup/
 * https://us20.admin.mailchimp.com/
 * */

public class StepDefinitions {

    CucumberHelper helper = CucumberHelper.getThisHelperInstance();
    FormPagePF formPage;
    // WebDriver currentDriver;
    String theEmail;
    // d = new ChromeDriver(new ChromeOptions().setHeadless(true));

    @Before
    public void setUp() {
	/*
	 * WebDriverManager.firefoxdriver().setup(); currentDriver = new
	 * FirefoxDriver(new FirefoxOptions().setHeadless(false));
	 * currentDriver.manage().deleteAllCookies(); String url =
	 * String.format("https://login.mailchimp.com/signup/");
	 */
	formPage = new FormPagePF();
	formPage.openBrowser(GLOBAL_DATA.BROWSER, GLOBAL_DATA.OS, GLOBAL_DATA.ENVIRONMENT);
	formPage.navigateToURL();
	// currentDriver.get(url);
	// currentDriver.manage().window().setSize(new Dimension(1200, 1200));
	// System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    }

    /*
     * @Given("a {string} name length") public void a_valid_name_length(String
     * lengthType) {
     * 
     * }
     */

    @Given("a {string} email")
    public void a_normal_email(String emailStatus) {

	if (emailStatus.equalsIgnoreCase("missing"))
	    theEmail = helper.getEmailAddressWithANameLengthOf(0);
	else if (emailStatus.equalsIgnoreCase("too long")) {
	    int nameLength = ThreadLocalRandom.current().nextInt(GLOBAL_DATA.INVALIDMINUMUM,
		    (GLOBAL_DATA.INVALIDMINUMUM + 24));
	    theEmail = helper.getEmailAddressWithANameLengthOf(nameLength);
	} else { // if emailStatus is "normal"
	    theEmail = helper.getEmailAddress();
	}
	formPage.fillInRegistrationForm(theEmail);
    }

    @And("I register as the same user")
    public void i_register_as_the_same_user() {
	SingletonWebDriver.getInstance().getWebDriver().manage().deleteAllCookies();
	formPage.navigateToURL();
	formPage.fillInRegistrationForm(theEmail);
    }

    @When("I complete registration")
    public void i_complete_registration() {

	formPage.completeRegistration();

    }

    @Then("I get the {string} message")
    public void i_get_the_message(String expected) {

	String actual = formPage.getElement(expected).getText().substring(0, expected.length());
	// System.out.println(actual);
	assertTrue(actual.equalsIgnoreCase(expected));
    }

    @After
    public void tearDown() {
	theEmail = null;
	SingletonWebDriver.getInstance().getWebDriver().manage().deleteAllCookies();
	SingletonWebDriver.getInstance().getWebDriver().quit();
    }

}
