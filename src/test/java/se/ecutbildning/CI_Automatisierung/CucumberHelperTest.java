package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class CucumberHelperTest {

    static CucumberHelper helper;

    @BeforeAll
    public static void createObjects() {
	helper = CucumberHelper.getThisHelperInstance();
    }

    @Test
    public void validatePassword_Null() {
	// setup
	String password = null;
	// execute
	// assert
	assertThatNullPointerException().isThrownBy(() -> 
	CucumberHelperTest.validatePassword(password));
    }

    @Test
    public void validatePassword_EmptyString() {
	// setup
	String password = "";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_Missing_OneNumber() {
	// setup
	String password = "Abcdefg#";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_Missing_OneUpperCaseLetter() {
	// setup
	String password = "abcdefg5#";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_Missing_OneLowerCaseLetter() {
	// setup
	String password = "ABCDEFG5#";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_Missing_OneSpecialCharacter() {
	// setup
	String password = "Abcdefg5";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_Missing_SpecialCharacters() {
	// setup
	String password = "MMDDHHFUEIiaiu4isiduisudksdh";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assertx
	assertFalse(actual);
    }

    @Test
    public void validatePassword_NumbersMet() {
	// setup
	String password = "Ab4cdefg57#";
	// execute
	boolean actual = CucumberHelperTest.oneIsNumber(password);
	// assert
	assertTrue(actual);
    }

    @Test
    public void validatePassword_AllRulesMet() {
	// setup
	String password = "Abcdefg5#";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
	// assert
	assertTrue(actual);
    }

    // ***Names***
    @Test
    public void nameHasRequestedMinimumLength() {
	// setup
	int nameLength = 8;
	// execute
	String actual = helper.getEmailAddress();
	// assert
	assertTrue(actual.length() >= nameLength);
    }
    
    @Test
    public void nameHasLessThanRequestedMinimumLength() {
	// setup
	int minNameLength = 5;
	// execute
	String actual = "act@lookahead.com";//helper.getEmailAddress();
	// assert
	assertFalse(StringUtils.substringBefore(actual, "@").length() >= minNameLength);
    }

    @Test
    public void nameIsMadeOfLetters() {
	// execute
	String actual = helper.getEmailAddress();
	// assert
	assertTrue(StringUtils.substringBefore(actual, "@").matches("\\p{L}+"));
    }

    @Test
    public void twoNamesAreNotTheSame() {
	// execute
	String name = helper.getEmailAddress();
	String name2 = helper.getEmailAddress();
	// assert
	assertFalse(name.equals(name2));
    }
    
    @Test
    public void namesOfZeroLengthRendersEmtpyString() {
	// execute
	String name = helper.getEmailAddressWithANameLengthOf(0);
	// assert
	assertTrue(name.equals(""));
    }
    
    // ***Passwords***
    
    @Test
    public void twoPasswordsAreNotTheSame() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword();
	String password2 = helper.getPassword();
	// assert
	assertFalse(password.equals(password2));
    }
    
    @Test
    public void passwordMinLength() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword() ;
	//System.out.println(password);
	// assert
	assertTrue(validatePassword(password));	
    }
    
    @RepeatedTest(100)
    public void generatedPasswordContainsUpperCase() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword() ;
	// assert
	assertTrue(oneIsUpperCase(password));
    }
    
    @RepeatedTest(100)
    public void generatedPasswordContainsLowerCase() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword() ;
	// assert
	//System.out.println(password);
	assertTrue(oneIsLowerCase(password));
    }
    
    @RepeatedTest(100)
    public void generatedPasswordContainsNumber() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword() ;
	// assert
	assertTrue(oneIsNumber(password));
    }
    
    @RepeatedTest(100)
    public void generatedPasswordContainsSpecialChar() throws TooShortPasswordException {
	// execute
	String password = helper.getPassword() ;
	// assert
	assertTrue(oneIsSpecial(password));
	//System.out.println(password);
    }
    

    // Helper methods
    private static boolean validatePassword(String pw) {
	// true lies

	boolean hasNumber = oneIsNumber(pw);
	boolean hasLowerCase = oneIsLowerCase(pw);
	boolean hasUpperCase = oneIsUpperCase(pw);
	boolean hasSpecial = oneIsSpecial(pw);
	
	if (hasNumber && hasLowerCase && hasUpperCase && hasSpecial)
	    return true;
	return false;
    }

    private static boolean oneIsSpecial(String pw) {
	// At least one special character, i e not a number nor a letter
	Pattern p = Pattern.compile("[\\P{L}&&\\P{N}]");
	Matcher m = p.matcher(pw);
	return m.find();
    }

    private static boolean oneIsLowerCase(String pw) {
	// At least one lowercase letter that has an uppercase variant
	Pattern p = Pattern.compile("\\p{Ll}");
	Matcher m = p.matcher(pw);
	return m.find();
    }

    private static boolean oneIsUpperCase(String pw) {
	// At least one uppercase letter that has a lowercase variant
	Pattern p = Pattern.compile("\\p{Lu}");
	Matcher m = p.matcher(pw);
	return m.find();
    }

    private static boolean oneIsNumber(String pw) {
	// At least one number
	Pattern p = Pattern.compile("\\p{N}");
	Matcher m = p.matcher(pw);
	return m.find();
    }
}
