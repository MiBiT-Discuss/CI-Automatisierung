package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * https://us20.admin.mailchimp.com/signup/setup/
 * https://us20.admin.mailchimp.com/
 * */

public class StepDefinitions {
    
    CucumberHelper helper;

    WebDriver driver = new FirefoxDriver(new FirefoxOptions().setHeadless(false));
    String url;

    // @BeforeEach
    @Before
    public void setUp() {
	// d = new ChromeDriver(new ChromeOptions().setHeadless(true));
	System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
	driver = new FirefoxDriver();
	helper = new CucumberHelper();
    }

    @Given("I want to register as new user")
    public void i_want_to_register_as_new_user() throws TooShortPasswordException {

	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000L));
	//url = String.format("https://login.mailchimp.com/signup/");
	url = String.format("https://us20.admin.mailchimp.com/signup/");
	driver.get(url);
	// driver.get("https://login.mailchimp.com/signup/");
	driver.manage().window().setSize(new Dimension(829, 854));
	String theEmail = helper.getEmailAddress();
	driver.findElement(By.id("email")).sendKeys(theEmail);
	driver.findElement(By.id("new_username")).sendKeys(StringUtils.substringBefore(theEmail, "@"));
	driver.findElement(By.id("new_password")).sendKeys(helper.getPassword(9));

	driver.findElement(By.id("marketing_newsletter")).click();
	driver.findElement(By.id("onetrust-reject-all-handler")).click();
    }

    @When("I complete registration")
    public void i_complete_registration() {
	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000L));
	driver.findElement(By.id("create-account")).click();
	// driver.findElement(By.id("content")).click();
    }

    @Then("I get the message {string}")
    public void i_get_the_message(String message) {
	assertThat(driver.findElement(By.cssSelector("#signup-content h1")).getText()).isEqualTo(message);
    }
    
    private String wordStream(int numOfCharacters) {
	return Stream.generate(new WordGenerator())
		.limit(numOfCharacters)
		.collect(Collectors.joining());
    }
    
    private String createPassword() {
	/*
	 * Rules:
	 * One lowercase character
	 * One uppercase character
	 * One number
	 * One special character
	 * 8 characters minimum
	 * */
	
	return "";
    }

    // @AfterEach
    //@AfterAll
    public void tearDown() {
	driver.close();
    }

}
