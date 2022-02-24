package se.ecutbildning.CI_Automatisierung;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/*import java.util.function.Supplier;*/
import java.util.stream.Collectors;
/*import java.util.stream.Stream;*/

import org.apache.commons.text.RandomStringGenerator;

public class PasswordGenerator /* implements Supplier<String> */ {
	
	Random rnd = new SecureRandom();
	//String alphabet;
	String password;
	
	public PasswordGenerator()  {
	    
	    String pwString = getRandomSpecialCharacters(2)
		    .concat(getRandomNumbers(2))
	            .concat(getRandomAlphabet(2, true))
	            .concat(getRandomAlphabet(2, false))
	            .concat(getRandomCharacters(2));
	    
	    List<Character> pwChars = pwString.chars()
		    			.mapToObj(data -> (char) data)
		    			.collect(Collectors.toList());
	    Collections.shuffle(pwChars);
	    password = pwChars.stream()
	            	.collect(StringBuilder::new, 
	            		StringBuilder::append, 
	            		StringBuilder::append)
	            	.toString();  
	}
	

	private String getRandomNumbers(int length) {
	   RandomStringGenerator numberGen = new RandomStringGenerator.Builder()
		   				.withinRange(48, 57).build();
	   return numberGen.generate(length);
	}

	private String getRandomCharacters(int length) {
	    RandomStringGenerator randomCharGen = new RandomStringGenerator.Builder()
							.withinRange(48, 57).build();
	    return randomCharGen.generate(length);
	}

	private String getRandomAlphabet(int length, boolean lowerCase) {
	    int low;
	    int hi;
	    if (lowerCase) {
		low = 97;
		hi = 122;
	    } else {
		low = 65;
		hi = 90;
	    }
	    RandomStringGenerator randomAlphabet = new RandomStringGenerator.Builder()
		    					.withinRange(low, hi).build();
	    return randomAlphabet.generate(length);
	}

	private String getRandomSpecialCharacters(int length) {
	    RandomStringGenerator specCharGen = new RandomStringGenerator.Builder()
						.withinRange(33, 45).build();
	    return specCharGen.generate(length);
	}

	/* @Override */
	public String get() {
	      return password;
	}
	
	public static void main (String[] args) {
	    PasswordGenerator pg = new PasswordGenerator();
	    String thePass = pg.get();
	    System.out.println(thePass + " has a length of " + thePass.length());
	}
}