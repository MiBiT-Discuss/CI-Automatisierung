package se.ecutbildning.CI_Automatisierung;


public class CucumberHelper {
    
    EmailAddressCreator emailCreator = new EmailAddressCreator();
    
    public String getEmailAddress() {
	return emailCreator.getEmailAddress();
    }
    
 
    public String getPassword() throws TooShortPasswordException {
	/*
   	 * Rules:
   	 * One lowercase character
   	 * One uppercase character
   	 * One number
   	 * One special character
   	 * 8 characters minimum
   	 * 50 characters maximum
   	 * */
	return new PasswordGenerator().get();
    }

}
