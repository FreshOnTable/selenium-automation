package Utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import PageObject.Objectmanager;

public class Setuptest {
	
	public WebDriver driver;
	public Basetest testBase;
	public Objectmanager pageObjectManager;
	public String productName;
	public String productUOM;
	public String sellerName;
	public String RecQty;

	public Setuptest() throws IOException {
		testBase = new Basetest();
		
		pageObjectManager = new Objectmanager(testBase.webDriverManager());

	}

}
