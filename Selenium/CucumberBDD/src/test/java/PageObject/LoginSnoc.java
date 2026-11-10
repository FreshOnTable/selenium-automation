package PageObject;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginSnoc {
	
	public WebDriver driver;

	public LoginSnoc(WebDriver driver) {
		this.driver = driver;	
	}
		
//	By phNum = By.xpath("//input[@type='text']");  //*[@id=\"username\"]
	By phNum = By.xpath("//*[@name='userName']");
	By passWord = By.xpath("//input[@type='password']");
	By login = By.xpath("//button[text()='Login']");
	By LoginVali = By.xpath("//*[@id=\"root\"]/div/div[1]/div[2]/span");
	
//	public void enterusername() throws InterruptedException {
//		Thread.sleep(1000);
//	driver.findElement(phNum).sendKeys("UAEADMIN");
//	}
	public void enterusername() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    WebElement username = wait.until(
	            ExpectedConditions.elementToBeClickable(phNum));

	    username.clear();
	    username.sendKeys("UAEADMIN");
	}
	
	public void enterpassword() {
		driver.findElement(passWord).sendKeys("Admin@1234");
	}

	public void clickOnLoginButton() {
		driver.findElement(login).click();
    }
	
	public void dismissPopupIfPresent() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[text()='OK']")));
	        closeBtn.click();
	    } catch (Exception e) {
	        // ignore if not present
	    }
	}

	public void SnocAdminValidation() {
		WebElement element = driver.findElement(LoginVali);
		String expectedValue = "FRESHONTABLE";
        String actualValue = element.getText();	        
        Assert.assertEquals(actualValue, expectedValue, "The values don't match!");
        System.out.println("User is able to Login sucessfully");
               
	}

}

