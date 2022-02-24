package se.ecutbildning.CI_Automatisierung;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("se/ecutbildning/CI_Automatisierung")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "junit")

public class RunCucumberIT {
}
