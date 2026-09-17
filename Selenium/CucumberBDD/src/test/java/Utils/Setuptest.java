package Utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import PageObject.Objectmanager;

public class Setuptest {
	
	public WebDriver driver;
	public Basetest testBase;
	public Objectmanager pageObjectManager;

	public Setuptest() throws IOException {
		testBase = new Basetest();
		pageObjectManager = new Objectmanager(testBase.webDriverManager());

	}

}
