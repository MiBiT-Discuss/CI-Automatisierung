package se.ecutbildning.CI_Automatisierung;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CucumberHelperTest {

    static CucumberHelper helper;

    @BeforeAll
    public static void createObjects() {
	helper = new CucumberHelper();
    }

    @Test
    public void validatePassword_Null() {
	// setup
	String password = null;
	// execute
	// assert
	assertThatNullPointerException().isThrownBy(() -> CucumberHelperTest.validatePassword(password));
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
	// assert
	assertFalse(actual);
    }

    @Test
    public void validatePassword_NumbersMet() {
	// setup
	String password = "Ab4cdefg57#";
	// execute
	boolean actual = CucumberHelperTest.validatePassword(password);
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

    // ***Words***
    @Test
    public void wordHasRequestedMinimumLength() {
	// setup
	int wordLength = 8;
	// execute
	String actual = helper.getWord(wordLength);
	// assert
	assertTrue(actual.length() >= wordLength);
    }
    
    @Test
    public void wordHasLessThanRequestedMinimumLength() {
	// setup
	int minWordLength = 8;
	// execute
	String actual = helper.getWord(7);
	// assert
	assertFalse(actual.length() >= minWordLength);
    }

    @Test
    public void wordIsMadeOfLetters() {
	// execute
	String actual = helper.getWord(8);
	// assert
	assertTrue(actual.matches("\\p{L}+"));
	// assertTrue(actual.chars().allMatch(Character::isLetter));
    }

    @Test
    public void twoWordsAreNotTheSame() {
	// execute
	String word = helper.getWord(5);
	String word2 = helper.getWord(5);
	// assert
	assertFalse(word.equals(word2));
    }
    
    // ***Passwords***
    
    @Test
    public void twoPasswordsAreNotTheSame() {
	// execute
	String password = helper.getPassword(8);
	String password2 = helper.getPassword(8);
	// assert
	assertFalse(password.equals(password2));
    }
    
    @Test
    public void passwordMinLength() {
	// execute
	String password = helper.getPassword(8);
	// assert
	assertFalse(validatePassword(password));
    }
    
    @Test
    public void generatedPasswordContainsUpperCase() {
	// execute
	String password = helper.getPassword(8);
	// assert
	assertTrue(oneIsUpperCase(password));
    }
    
    @Test
    public void generatedPasswordContainsLowerCase() {
	// execute
	String password = helper.getPassword(8);
	// assert
	System.out.println(password);
	assertTrue(oneIsLowerCase(password));
    }
    
    @Test
    public void generatedPasswordContainsNumber() {
	// execute
	String password = helper.getPassword(8);
	// assert
	assertTrue(oneIsNumber(password));
    }
    
    @Test
    public void generatedPasswordContainsSpecialChar() {
	// execute
	String password = helper.getPassword(8);
	// assert
	assertTrue(oneIsSpecial(password));
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
	/*
	 * long count = m.results().count(); return (count != 0);
	 */
	return m.find();
    }

    private static boolean oneIsLowerCase(String pw) {
	// At least one lowercase letter that has an uppercase variant
	Pattern p = Pattern.compile("\\p{Ll}");
	Matcher m = p.matcher(pw);
	return m.find();
    }

    private static boolean oneIsUpperCase(String pw) {
	// At least one uppercase letter that has an lowercase variant
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