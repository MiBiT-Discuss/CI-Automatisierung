package se.ecutbildning.CI_Automatisierung;


public class CucumberHelper {
    
    private static CucumberHelper thisHelper = null;
    EmailAddressCreator emailCreator = new EmailAddressCreator();
    
    
    
    public static CucumberHelper getThisHelperInstance() {
	if(thisHelper == null)
	    thisHelper = new CucumberHelper();
        return thisHelper;
    }

    public String getEmailAddress() {
	return emailCreator.getEmailAddress();
    }
    
    public String getEmailAddressWithANameLengthOf(int nameLength) {
   	return emailCreator.getEmailAddressWithNameLength(nameLength);
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
	return new PasswordGenerator().get();
    }

}
