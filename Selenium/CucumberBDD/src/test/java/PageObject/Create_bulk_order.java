package PageObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Create_bulk_order {
	
	public WebDriver driver;

	public Create_bulk_order(WebDriver driver) {
		this.driver = driver;	
	}
public void CreateBulkOrder() throws InterruptedException {
	
	driver.findElement(By.name("userName")).sendKeys("UAEADMIN");
	driver.findElement(By.name("password")).sendKeys("Admin@1234");
	Thread.sleep(1000);
	driver.findElement(By.xpath("//button[@class=\"button button-primary\"]")).click(); //login
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class='menu-cont' ] //img[@alt='img']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul//li//span[text()='Order']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul//li//span[text()='Place Order']")).click();
	
	for (int i = 1; i <= 50; i++) {
	     System.out.println("Creating Order " + i);		  	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@style=\"position: absolute; top: 10px; right: 12px;\"]")).click();
			
		Thread.sleep(3000);	
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		Random random = new Random();
		int randomIndex = random.nextInt(rows.size());
		rows.get(randomIndex).click();
		
		//Product Select
		
		List<WebElement> products = driver.findElements(
				By.xpath("(//input[contains(@class,'grid-checkbox-input') and @type='checkbox' and not(@disabled)])[position()>1]"));

		int numberOfProductsToSelect = Math.min(4, products.size());

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Random rand = new Random();

		for (int index = 0; index < numberOfProductsToSelect; index++) {

			WebElement checkbox = products.get(index);

			try {

				wait.until(ExpectedConditions.visibilityOf(checkbox));

				js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", checkbox);

				Thread.sleep(500);

				wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();

			} catch (Exception e) {

				// Fallback JS click
				js.executeScript("arguments[0].click();", checkbox);
			}

			Thread.sleep(200);

			// Locate row and quantity input
			WebElement row = checkbox.findElement(By.xpath("./ancestor::tr"));
			WebElement qtyInput = row.findElement(By.xpath(".//td[10]//input"));

			Thread.sleep(500);

			// Random quantity between 4 and 10
			int q = rand.nextInt(5) + 1;
			String qty = String.valueOf(q);

		// Focus input
		qtyInput.click();
		Thread.sleep(150);

		// Clear existing value
		qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Thread.sleep(100);
		qtyInput.sendKeys(Keys.DELETE);
		Thread.sleep(100);

		// Enter new quantity
		qtyInput.sendKeys(qty);
		Thread.sleep(150);

		// Trigger JS events for React / Angular grids
		js.executeScript("arguments[0].dispatchEvent(new Event('focus', { bubbles: true }));", qtyInput);
		js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", qtyInput);
		js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", qtyInput);
		js.executeScript("arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", qtyInput);
		js.executeScript("arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));", qtyInput);

		// Remove focus
		js.executeScript("document.activeElement.blur();");

		Thread.sleep(200);
	}
	
	
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String lpoNum = "Regression Test " + timestamp;

		WebElement LPONumberField = driver.findElement(By.name("lpo_number"));
		LPONumberField.sendKeys(lpoNum);
		
		WebElement radioButton = driver.findElement(By.name("billing_address"));
		
       if (!radioButton.isSelected()) {
          
           radioButton.click();
       }
       
       WebElement radio1 = driver.findElement(By.name("shipping_address"));
       
       if (!radio1.isSelected()) {
           
           radio1.click();
       }
       
       Thread.sleep(2000);
       
       driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div/div/select")).click();
       driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div/div/select/option[3]")).click();
       
       driver.findElement(By.name("ship_dt")).click();

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

		LocalDate nextDay = LocalDate.now().plusDays(1);
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d"); 

		String targetDay = nextDay.format(dayFormatter);
		String targetMonthYear = nextDay.format(monthYearFormatter);


		while (!driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"))
		        .getText()
		        .contains(targetMonthYear)) {
			Thread.sleep(1000);
		    driver.findElement(By.xpath("//button[contains(@class, 'react-datepicker__navigation--next')]")).click();
		}

		List<WebElement> days = driver.findElements(By.cssSelector("div.react-datepicker__month-container div.react-datepicker__month div.react-datepicker__week div.react-datepicker__day"));

		for (WebElement day : days) {
		    String ariaLabel = day.getAttribute("aria-label");
		    if (ariaLabel != null && ariaLabel.contains(targetDay) && ariaLabel.contains(nextDay.format(DateTimeFormatter.ofPattern("MMMM")))) { //check for Day and Month
		       Thread.sleep(500);
		    	day.click();
		        break; 
		    }
		}
		
//select delivery user
		Thread.sleep(1000);
		    WebElement deliveryUserDropdown = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.name("del_user_id")
		            )
		    );

		    Select select = new Select(deliveryUserDropdown);

		    List<WebElement> options = select.getOptions();

		    List<WebElement> validOptions = new ArrayList<>();

		    for (WebElement option : options) {

		        String text = option.getText().trim();

		        // Skip Select / empty options
		        if (!text.isEmpty()
		                && !text.equalsIgnoreCase("Select")
		                && option.isEnabled()) {

		            validOptions.add(option);
		        }
		    }

		    if (validOptions.isEmpty()) {
		        throw new RuntimeException(
		                "No delivery users available in Delivery User dropdown"
		        );
		    }

//		    Random random = new Random();

		    WebElement randomUser =
		            validOptions.get(
		                    random.nextInt(validOptions.size())
		            );

		    String selectedUser =
		            randomUser.getText().trim();

		    select.selectByVisibleText(selectedUser);

		    System.out.println(
		            "✅ Random Delivery User selected: "
		            + selectedUser
		    );
		
//		Thread.sleep(1000);
//		driver.findElement(By.xpath("//button[text()='Yes']")).click();    	
		Thread.sleep(2000);    
	   driver.findElement(By.xpath("//button[text()='Review Order']")).click();
	   
	   Thread.sleep(2000);	
	   driver.findElement(By.xpath("//button[text()='Place Order']")).click();
	   
	   Thread.sleep(2000);   
	   driver.findElement(By.xpath("//button[text()='No']")).click();
	   
	   System.out.println("Order " + i + " created successfully!");
	   
		 }
	}
}



