package se.ecutbildning.CI_Automatisierung;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NonSensicalWordGenerator implements Supplier<String> {
	
	Random rnd = ThreadLocalRandom.current();
	String alphabet;
	
	public NonSensicalWordGenerator() {
	    alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
	}
		
	@Override
	public String get() {
	    char[] letters = alphabet.toCharArray();
	    return  "" + letters[rnd.nextInt(letters.length)];
	}

	public static void main(String[] args) {
		String word = Stream.generate(new NonSensicalWordGenerator())
				.limit(30).collect(Collectors.joining());
		String word2 = Stream.generate(new NonSensicalWordGenerator())
			.limit(30).collect(Collectors.joining());
		System.out.println(word + " / " + word2);
		System.out.println("word equals word2 is " + word.equals(word2));

	}
}