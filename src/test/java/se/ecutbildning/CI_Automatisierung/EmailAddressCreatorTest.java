package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.StringUtils;
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
	assertFalse(email.isBlank());
    }
    
    @Test
    void zeroNameLengthGivesBlankEmail() {
	String email = helper.getEmailAddressWithANameLengthOf(0);
	assertTrue(email.isBlank());
    }
    
    @Test
    void aCertainNameLengthGivesSame() {
	String email = helper.getEmailAddressWithANameLengthOf(30);
	assertTrue(StringUtils.substringBefore(email, "@").length() == 30);
    }
    
    @Test
    void aCertainToolongNameLengthGivesSame() {
	String email = helper.getEmailAddressWithANameLengthOf(130);
	assertTrue(StringUtils.substringBefore(email, "@").length() == 130);
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
