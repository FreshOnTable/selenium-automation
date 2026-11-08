package runnerClass;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/featureFile",
		tags="@Regression",
		glue = { "stepDefinitions", }, 		 
		dryRun=false,
		monochrome = true,
		plugin = { "pretty",
				   "summary", 
				   "html:target/cucumber-reports.html"})

public class TestRunner {

}
