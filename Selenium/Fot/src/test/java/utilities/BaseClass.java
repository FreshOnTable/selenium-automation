package utilities;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import pageObject.ObjectManager;

public class BaseClass {
	
	public WebDriver driver;
//	public SetupClass setupclass;
	public ObjectManager pageObjectManager;

	public BaseClass() throws IOException {
//		setupclass = new SetupClass();
//		pageObjectManager = new ObjectManager(setupclass.webDriverManager());

	}

}