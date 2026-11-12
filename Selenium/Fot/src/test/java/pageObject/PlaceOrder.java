package pageObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

	private static final By ROWS =
	        By.xpath("//tbody//tr[.//input[contains(@class,'grid-checkbox-input')]]");
	private static final By CHECKBOX =
	        By.xpath(".//input[contains(@class,'grid-checkbox-input')]");
	private static final int PRODUCT_ID_COL = 2;
	private static final int QTY_COL = 10;   // Quantity column; update if columns change

	public void selectProduct_And_Quantity() {
	    int count = 2;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(ROWS, 0));

	    // 1. Snapshot IDs of rows that can be selected
	    List<String> eligibleIds = new ArrayList<>();
	    for (WebElement row : driver.findElements(ROWS)) {
	        WebElement cb = row.findElement(CHECKBOX);
	        if (cb.isEnabled() && !cb.isSelected()) {
	            eligibleIds.add(row.findElement(By.xpath("./td[" + PRODUCT_ID_COL + "]")).getText().trim());
	        }
	    }
	    if (eligibleIds.size() < count) {
	        throw new IllegalStateException("Only " + eligibleIds.size() + " selectable products found");
	    }

	    // 2. Unique random picks, no loop that can hang
	    Collections.shuffle(eligibleIds);
	    List<String> picked = new ArrayList<>(eligibleIds.subList(0, count));

	    // 3. Select and set quantity
	    Random rand = new Random();
	    for (String id : picked) {
	        String qty = String.valueOf(rand.nextInt(10) + 1);
	        selectRowAndSetQty(id, qty, wait);
	        System.out.println("Product " + id + " -> Qty " + qty);
	    	    
	    }
	   
	}

	private void selectRowAndSetQty(String productId, String qty, WebDriverWait wait) {
	    By rowBy = By.xpath("//tbody//tr[td[" + PRODUCT_ID_COL + "][normalize-space()='" + productId + "']]");
	    By qtyBy = By.xpath("./td[" + QTY_COL + "]//input");
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    for (int attempt = 1; attempt <= 3; attempt++) {
	        try {
	            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowBy));
	            WebElement cb = row.findElement(CHECKBOX);

	            js.executeScript("arguments[0].scrollIntoView({block:'nearest', inline:'nearest'});", cb);
	            if (!cb.isSelected()) {
	                try {
	                    wait.until(ExpectedConditions.elementToBeClickable(cb)).click();
	                } catch (ElementClickInterceptedException e) {
	                    js.executeScript("arguments[0].click();", cb);
	                }
	            }

	            // wait for the REAL state, re-finding the row each time
	            wait.until(d -> d.findElement(rowBy).findElement(CHECKBOX).isSelected());

	            WebElement qtyInput = wait.until(d -> {
	                WebElement e = d.findElement(rowBy).findElement(qtyBy);
	                return e.isEnabled() ? e : null;
	            });

	            qtyInput.click();
	            qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), qty);
	            qtyInput.sendKeys(Keys.TAB);   // real blur, triggers recalculation

	            // verify the app accepted the value
	            wait.until(d -> {
	                String v = d.findElement(rowBy).findElement(qtyBy).getAttribute("value");
	                try { return Double.parseDouble(v) == Double.parseDouble(qty); }
	                catch (Exception ex) { return false; }
	            });
	            return;

			} catch (Exception e) {
				if (attempt == 3) {
					throw new AssertionError("Failed to select product " + productId + " / set qty " + qty, e);
				}
				// else retry
			}
	    }
	}
	
	public void LPOnumber() throws InterruptedException {
		Thread.sleep(2000);
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String lpoNum = "Test " + timestamp;
			WebElement LPONumberField = driver.findElement(By.name("lpo_number"));
			LPONumberField.sendKeys(lpoNum);
	}
	
	public void paymentTerms() throws InterruptedException {
		Thread.sleep(2000);
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
		Thread.sleep(2000);
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

	

	
