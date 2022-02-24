package se.ecutbildning.CI_Automatisierung;

public enum Domain {
    
    GMAIL, 
    HOTMAIL,
    OUTLOOK,
    YAHOO,
    GMX;

    @Override
    public String toString() {
	return name().toLowerCase().toString();
    }

}
