package se.ecutbildning.CI_Automatisierung;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PasswordGenerator implements Supplier<String> {
	
	Random rnd = new Random();
	String alphabet;
	
	public PasswordGenerator() {
	    StringBuilder alphabetic = new StringBuilder();
	    alphabetic.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	    alphabetic.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());
	    alphabetic.append("012345678901234567890123456789");
	    for(int i = 33; i <= 45; i++)
		alphabetic.append((char)i);
	    this.alphabet = alphabetic.toString();
	}
		
	@Override
	public String get() {
	    char[] letters = alphabet.toCharArray();
	    return  "" + letters[rnd.nextInt(letters.length)];
	}

	public static void main(String[] args) {
		String word = Stream.generate(new PasswordGenerator())
				.limit(30).collect(Collectors.joining());
		String word2 = Stream.generate(new PasswordGenerator())
			.limit(30).collect(Collectors.joining());
		System.out.println(word + " / " + word2);
		System.out.println("word equals word2 is " + word.equals(word2));

	}
}