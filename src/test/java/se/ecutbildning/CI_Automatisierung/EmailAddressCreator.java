package se.ecutbildning.CI_Automatisierung;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmailAddressCreator {
    
    static Domain domainName;
    TopDomain topDomain;
    Random rnd = ThreadLocalRandom.current();

    public String getEmailAddress() {
	StringBuilder email = new StringBuilder();
	email.append(Stream.generate(new WordGenerator())
			.limit(ThreadLocalRandom.current().nextInt(5, 13))
			.collect(Collectors.joining()
			));
	email.append("@");
	email.append(getDomain().toString()+ ".");
	email.append(getTopDomain().toString());
	return email.toString();
    }
    
    private Domain getDomain() {
	int selection = rnd.nextInt(Domain.values().length);
	return Domain.values()[selection];
    }
    
    private TopDomain getTopDomain() {
	int selection = rnd.nextInt(TopDomain.values().length);
	return TopDomain.values()[selection];
    }

}
