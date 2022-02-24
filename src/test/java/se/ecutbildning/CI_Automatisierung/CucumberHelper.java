package se.ecutbildning.CI_Automatisierung;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CucumberHelper {
    
    EmailAddressCreator emailCreator = new EmailAddressCreator();
    
    public String getEmailAddress() {
	return emailCreator.getEmailAddress();
    }
    
    public String getPassword() {
   	/*
   	 * Rules:
   	 * One lowercase character
   	 * One uppercase character
   	 * One number
   	 * One special character
   	 * 8 characters minimum
   	 * 50 characters maximum
   	 * */
   	
   	return "A43948SJKWJWj%sdd";
       }
    public String getPassword(int length) throws TooShortPasswordException {
	return Stream.generate(new PasswordGenerator())
		.limit(length).collect(Collectors.joining());
    }

}
