package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * https://us20.admin.mailchimp.com/signup/setup/
 * https://us20.admin.mailchimp.com/
 * */

public class StepDefinitions {
    
    CucumberHelper helper = new CucumberHelper();;
    WebDriver driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
    String url;
    // d = new ChromeDriver(new ChromeOptions().setHeadless(true));
    
 	

    // @BeforeEach
    @Before
    public void setUp() {
	System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    }

    @Given("I want to register as new user")
    public void i_want_to_register_as_new_user() throws TooShortPasswordException {

	//driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000L));
	//url = String.format("https://login.mailchimp.com/signup/");
	url = String.format("https://us20.admin.mailchimp.com/signup/");
	driver.get(url);
	driver.manage().window().setSize(new Dimension(829, 854));
	String theEmail = helper.getEmailAddress();
	driver.findElement(By.id("email")).sendKeys(theEmail);
	driver.findElement(By.id("new_username")).sendKeys(StringUtils.substringBefore(theEmail, "@"));
	driver.findElement(By.id("new_password")).sendKeys(helper.getPassword());

	driver.findElement(By.id("marketing_newsletter")).click();
	//driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000L));
	driver.findElement(By.id("onetrust-reject-all-handler")).click();
    }

    @When("I complete registration")
    public void i_complete_registration() {
	
	  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                        	  .withTimeout(Duration.ofSeconds(30))
                        	  .pollingEvery(Duration.ofMillis(500))
                        	  .ignoring(ElementClickInterceptedException.class);
	 	
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
	assertThat(driver.findElement(By.cssSelector("#signup-content h1")).getText()).isEqualTo(message);
    }

    //@After
    public void tearDown() {
	driver.close();
    }

}
