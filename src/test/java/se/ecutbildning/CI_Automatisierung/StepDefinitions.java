package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
 * https://us20.admin.mailchimp.com/signup/setup/
 * https://us20.admin.mailchimp.com/
 * */

public class StepDefinitions {

    WebDriver driver = new FirefoxDriver(new FirefoxOptions().setHeadless(false));
    String url;

    // @BeforeEach
    // @BeforeAll
    public void setUp() {
	// d = new ChromeDriver(new ChromeOptions().setHeadless(true));
	System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
	// driver = new FirefoxDriver();
    }

    @Given("I want to register as new user")
    public void i_want_to_register_as_new_user() {

	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000L));
	url = String.format("https://login.mailchimp.com/signup/");
	driver.get(url);
	// driver.get("https://login.mailchimp.com/signup/");
	driver.manage().window().setSize(new Dimension(829, 854));
	driver.findElement(By.id("email")).sendKeys("KJSUAre4545894@banan.com");
	driver.findElement(By.id("new_username")).sendKeys("IOPSert");
	driver.findElement(By.id("new_password")).sendKeys("123456qwertyASDFG%");

	driver.findElement(By.id("marketing_newsletter")).click();
    }

    @When("I complete registration")
    public void i_complete_registration() {
	driver.findElement(By.id("create-account")).click();
	// driver.findElement(By.id("content")).click();
    }

    @Then("I get the message {string}")
    public void i_get_the_message(String message) {
	assertThat(driver.findElement(By.cssSelector("#signup-content h1")).getText()).isEqualTo(message);
    }

    // @AfterEach
    @AfterAll
    public void tearDown() {
	driver.close();
    }

}
