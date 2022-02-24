package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class EmailAddressCreatorTest {
    
    CucumberHelper helper = new CucumberHelper();

    @Test
    void addressDoesNotContainUpperCaseChars() {
	String email = helper.getEmailAddress();
	assertThat(email).containsPattern("[a-z]");
	assertThat(email).doesNotContainPattern("[A-Z]");
    }
    @Test
    void addressNotNull() {
	String email = helper.getEmailAddress();
	assertThat(!email.isBlank());
    }
    @Test
    void addressContainsAtChar() {
	String email = helper.getEmailAddress();
	assertThat(email).contains("@");
    }
    
    @RepeatedTest(100)
    void twoAddressesAreNotTheSame() {
	String email = helper.getEmailAddress();
	String email2 = helper.getEmailAddress();
	assertFalse(email.equals(email2));
    }

}
