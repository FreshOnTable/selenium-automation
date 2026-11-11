package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;

}
	
	    private By username = By.xpath("//input[@name='userName']");
	    private By password = By.xpath("//input[@type='password']");
	    private By loginBtn = By.xpath("//span[text()='Sign in']");
	   

	    public void enterUsername(String user) {
	        driver.findElement(username).sendKeys(user);
	    }

	    public void enterPassword(String pass) {
	        driver.findElement(password).sendKeys(pass);
	    }

	    public void clickLoginButton() {
	        driver.findElement(loginBtn).click();
	    }

}
