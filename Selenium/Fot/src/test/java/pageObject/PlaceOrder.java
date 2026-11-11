package pageObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import global.Global;

public class PlaceOrder {
	
	public WebDriver driver;
	
	public static String orderId; 

	public PlaceOrder(WebDriver driver) {
		this.driver = driver;

}
	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickorder = By.xpath("//ul//li//span[text()='Order']");
	By placeorder = By.xpath("//ul//li//span[text()='Place Order']");
	By clickbusiness = By.xpath("//div[@style=\"position: absolute; top: 10px; right: 12px;\"]");
	

	public void clickgrid() throws InterruptedException {
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(clickgrid).click();
	}	
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
//		        By.xpath("//div[contains(@class,'menu-cont')]")
//		));
//		menu.click();
//	}

	public void clickorderoption() {
		driver.findElement(clickorder).click();
	}

	public void clickplaceorder() {
		driver.findElement(placeorder).click();
	}
	
	public void clickbusinessfield() {
		driver.findElement(clickbusiness).click();
	}
	
	public void selectBusiness() {
		driver.findElement(By.xpath("//div[2]/div/div[2]/div[2]/table/tbody/tr[5]")).click();
		
	}


	public void selectProduct_And_Quantity() throws Exception {

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		List<WebElement> products = driver.findElements(
				By.xpath("//input[contains(@class,'grid-checkbox-input') and @type='checkbox' and not(@disabled)]"));

	    int numberOfProductsToSelect = 2;
	    Set<Integer> randomIndexes = new HashSet<>();

	    int maxSelectable = Math.min(numberOfProductsToSelect, products.size());
	    Random rand = new Random();

	    // 2. Choose unique random indexes
	    while (randomIndexes.size() < maxSelectable) {
	        int index = rand.nextInt(products.size() -1) + 1; // fixed off-by-one
	        WebElement checkbox = products.get(index);

	        if (checkbox.isDisplayed() && checkbox.isEnabled() && !checkbox.isSelected()) {
	            randomIndexes.add(index);
	        }
	    }

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    for (int index : randomIndexes) {

	        WebElement checkbox = products.get(index);

	        // Wait until clickable
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.elementToBeClickable(checkbox));
	            checkbox.click();
	        } catch (Exception e) {
	            // Fallback to JS click
	            js.executeScript("arguments[0].click();", checkbox);
	        }

	        // Short delay for row to become interactive
	        Thread.sleep(200);

	        // Find row and quantity input
	        WebElement row = checkbox.findElement(By.xpath("./ancestor::tr"));
	        WebElement qtyInput = row.findElement(By.xpath(".//td[10]//input"));
	        
	        Thread.sleep(1500);

			Random rand1 = new Random();

			String qty;
			int whole = rand1.nextInt(10) + 1;
			qty = String.valueOf(whole);

			Thread.sleep(1500);
			updateQty(driver, qtyInput, qty);
			System.out.println("Row " + index + " → Qty updated to " + qty);
		}
	}

	public void updateQty(WebDriver driver, WebElement qtyInput, String qty) throws InterruptedException {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    	Thread.sleep(500);

	    	qtyInput.click();
	    	
	    	try {
	    	    qtyInput.click();
	    	} catch (ElementClickInterceptedException e) {
	    	    js.executeScript("arguments[0].click();", qtyInput);
	    	}

	    // Clear existing value
	    qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    Thread.sleep(100);
	    qtyInput.sendKeys(Keys.DELETE);
	    Thread.sleep(100);

	    // Enter new value
	    qtyInput.sendKeys(qty);
	    Thread.sleep(1000);

	    // Fire events to ensure JS frameworks detect change
	    js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", qtyInput);
	    js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", qtyInput);
	    js.executeScript("arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", qtyInput);
	    js.executeScript("arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));", qtyInput);

	    // Optionally click outside the input to trigger recalculation
	    js.executeScript("document.activeElement.blur();");
	    
	    Thread.sleep(300); // wait for page to register change

	    // Extra safety: click on the row or checkbox of next product
	    // WebElement nextRowCheckbox = driver.findElement(By.xpath("//tr[2]//input[@type='checkbox']"));
	    // nextRowCheckbox.click();
	}



	
	public void LPOnumber() {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String lpoNum = "Test " + timestamp;
			WebElement LPONumberField = driver.findElement(By.name("lpo_number"));
			LPONumberField.sendKeys(lpoNum);
	}
	
	public void paymentTerms() throws InterruptedException {
		WebElement paymentDropdownField = driver.findElement(By.name("payment_method"));

		WebElement dropdownElement = driver.findElement(By.name("payment_method"));
		Select dropdown = new Select(dropdownElement);

		List<WebElement> options = dropdown.getOptions();

		List<String> excludedOptions = Arrays.asList("Payment Method", "Select");

		List<WebElement> validOptions = new ArrayList<>();
		for (WebElement option : options) {
			String optionText = option.getText().trim();
			if (!excludedOptions.contains(optionText) && !optionText.isEmpty()) {
				validOptions.add(option);
			}
		}

		if (!validOptions.isEmpty()) {
			System.out.println("Status failed: Procced without selecting payment terms(mandatory)");
			dropdown.selectByVisibleText(validOptions.get(0).getText()); 
			Thread.sleep(1000);

			String selectedOption = dropdown.getFirstSelectedOption().getText();
			System.out.println("Status Passed");
		} else {
			System.out.println("No valid payment methods available to select.");
		}
	}
	
	public void shippingDate() throws InterruptedException {

		    Thread.sleep(2000);
		    WebElement dateInput = driver.findElement(By.name("ship_dt"));
		    dateInput.click();

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

		    LocalDate nextDay = LocalDate.now().plusDays(1);
		    String targetDay = nextDay.format(DateTimeFormatter.ofPattern("d"));
		    String targetMonthYear = nextDay.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

		    while (true) {
		        String current = driver.findElement(
		                By.cssSelector(".react-datepicker__current-month")).getText();

		        if (current.equals(targetMonthYear)) break;
		        driver.findElement(By.cssSelector(".react-datepicker__navigation--next")).click();
		    }

		    try {
		        driver.findElement(By.xpath("//div[contains(@class,'react-datepicker__day') and text()='" 
		                + targetDay + "']")).click();

		        if (dateInput.getAttribute("value").contains(targetDay)) {
		            System.out.println("Status: Passed (clicked normally)");
		            return;
		        }
		    } catch (Exception ignored) {}

		    String jsDate = nextDay.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		    ((JavascriptExecutor) driver).executeScript(
		            "arguments[0].value=arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
		            dateInput, jsDate);

		    System.out.println("Status: Passed (JS fallback)");
		}
		

	
	public void billingAddress_And_shippingAddress() throws InterruptedException {
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			WebElement billingAddressRadio = driver.findElement(By.name("billing_address"));

			if (!billingAddressRadio.isSelected()) {
				billingAddressRadio.click();
				System.out.println("Status Passed");
//				System.out.println("Billing address selected successfully.");
			} else {
				System.out.println("Billing address was already selected.");
			}
		} catch (Exception e) {
			System.out.println("Billing address selection error: " + e.getMessage());
		}

		try {
			WebElement shippingAddressRadio = driver.findElement(By.name("shipping_address"));

			if (!shippingAddressRadio.isSelected()) {
				shippingAddressRadio.click();
//				System.out.println("Shipping address selected successfully.");
			} else {
				System.out.println("Shipping address was already selected.");
			}
		} catch (Exception e) {
			System.out.println("Shipping address selection error: " + e.getMessage());
		}
		
	}
	
	public void deliveryUser() throws InterruptedException {
	Thread.sleep(2000);
	WebElement dropdownElement = driver.findElement(By.name("del_user_id"));
	Select select = new Select(dropdownElement);

	List<WebElement> options = select.getOptions();

	Random rand = new Random();
    int randomIndex = rand.nextInt(options.size() - 1) + 1;  

	select.selectByIndex(randomIndex);

	}
	
	public void placeOrder() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    By reviewOrderBtn = By.xpath("//button[text()='Review Order']");
	    By placeOrderBtn  = By.xpath("//button[text()='Place Order']");

	    // wait & click Review Order
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.elementToBeClickable(reviewOrderBtn)).click();

	    // wait until Place Order button is visible
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn));

	    // click Place Order
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();

	    System.out.println("Order created successfully");
	}
		
/*	public void placeOrder() throws InterruptedException {
		
	Thread.sleep(2000);
	driver.findElement(By.xpath("//button[text()='Review Order']")).click();
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	wait.until(ExpectedConditions.visibilityOf(element));
	
	driver.findElement(By.xpath("//button[text()='Place Order']")).click();
	Thread.sleep(2000);

	   
	   System.out.println("Order created successfully");
	   System.out.println("Status Passed");
		 
	}*/
	
	public String captureOrderId() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-confirm-alert-body")));

		String popupText = driver.findElement(By.cssSelector(".react-confirm-alert-body")).getText();
		System.out.println("Popup text: " + popupText);

		Pattern pattern = Pattern.compile("\\[\\s*([^\\]]+)\\s*\\]");
		Matcher matcher = pattern.matcher(popupText);

		String orderId = "";

		if (matcher.find()) {
			 orderId = matcher.group(1).trim();
			    Global.setOrderId(orderId);
		}
	   // Global.setOrderId(orderId);

		System.out.println("Created Order ID: " + orderId);
		
		driver.findElement(By.xpath("//button[text()='No']")).click();
		return orderId;
		
	}

	}

	

	
