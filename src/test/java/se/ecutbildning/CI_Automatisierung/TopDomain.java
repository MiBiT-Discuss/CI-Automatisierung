package se.ecutbildning.CI_Automatisierung;

public enum TopDomain {
    
    COM;
    
    @Override
    public String toString() {
	return name().toLowerCase().toString();
    }
}
