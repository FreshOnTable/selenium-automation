package CucumberTestNg;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

public class TestNgRunner {
	
@CucumberOptions(
		features = "src/test/java/FeatureFile", 
		glue = "StepDefinition", 
		monochrome = true, 
        tags = "@SellerAccount",

		plugin = { "html:target/cucmber.html", "json:target/cucumber.json"})

			public class TestNgtestRunner extends AbstractTestNGCucumberTests{
		
}
}
