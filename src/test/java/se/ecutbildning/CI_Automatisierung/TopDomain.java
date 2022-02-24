package se.ecutbildning.CI_Automatisierung;

public enum TopDomain {
    
    COM,
    ORG, 
    NET;
    
    @Override
    public String toString() {
	return name().toLowerCase().toString();
    }
}
