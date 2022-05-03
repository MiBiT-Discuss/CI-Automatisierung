package se.ecutbildning.CI_Automatisierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingletonWebDriverTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
	SingletonWebDriver.getInstance().closeDriver();
    }

    @Test
    void testGetInstance() {
	assertNotNull(SingletonWebDriver.getInstance());
    }

    @Test
    void testGetWebDriver() {
	SingletonWebDriver.getInstance().setWebDriver(GLOBAL_DATA.BROWSER, 
		GLOBAL_DATA.OS, GLOBAL_DATA.ENVIRONMENT);
	assertNotNull(SingletonWebDriver.getInstance().getWebDriver());
    }

}
