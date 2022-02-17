package se.ecutbildning.CI_Automatisierung;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CucumberHelper {
    
    public String getPassword() {
   	/*
   	 * Rules:
   	 * One lowercase character
   	 * One uppercase character
   	 * One number
   	 * One special character
   	 * 8 characters minimum
   	 * */
   	
   	return "A";
       }
    
    public String getWord(int length) {
	return Stream.generate(new WordGenerator())
	.limit(length).collect(Collectors.joining());
    }
    
    public String getPassword(int length) throws TooShortPasswordException {
	return Stream.generate(new PasswordGenerator(length))
		.limit(length).collect(Collectors.joining());
    }

}
