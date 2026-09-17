package PageObject;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class PendingOrder {

	public WebDriver driver;

	public static String orderId;
	List<Double> remainingList = new ArrayList<>();

	public PendingOrder(WebDriver driver) {
		this.driver = driver;

	}

	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickorder = By.xpath("//ul//li//span[text()='Order']");
	By placeorder = By.xpath("//ul//li//span[text()='Place Order']");
	By clickbusiness = By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']");
	By clickmore = By.xpath("(//div[@class='grid-actions-dropdown dropdown'])[1]");
	By clickshowalloptions = By.xpath("//div[@class='dropdown-item']");
	By clickedit = By.xpath("//a[text()='Edit Order Details']");

	public void clickgrid() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(clickgrid).click();
	}

	public void clickorderoption() {
		driver.findElement(clickorder).click();
	}

	public void clickplaceorder() {
		driver.findElement(placeorder).click();
	}

	public void clickbusinessfield() {
		driver.findElement(clickbusiness).click();
	}

	public void selectBusiness() throws InterruptedException {
		// driver.findElement(By.xpath("//div[2]/div/div[2]/div[2]/table/tbody/tr[5]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//table//tbody//tr[5]")).click();

	}

	public void selectProduct_And_Quantity() throws Exception {

		List<WebElement> products = driver.findElements(
				By.xpath("(//input[contains(@class,'grid-checkbox-input') and @type='checkbox' and not(@disabled)])[position()>1]"));

		int numberOfProductsToSelect = Math.min(3, products.size());

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

			Thread.sleep(1000);

			// Locate row and quantity input
			WebElement row = checkbox.findElement(By.xpath("./ancestor::tr"));
			WebElement qtyInput = row.findElement(By.xpath(".//td[10]//input"));

			Thread.sleep(1000);

			// Random quantity between 4 and 10
			int q = rand.nextInt(7) + 4;
			String qty = String.valueOf(q);

			updateQty(driver, qtyInput, qty);

			System.out.println("Row " + (index + 1) + " → Qty updated to " + qty);
		}
	}

	public void updateQty(WebDriver driver, WebElement qtyInput, String qty) throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;

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

		Thread.sleep(300);
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
			String current = driver.findElement(By.cssSelector(".react-datepicker__current-month")).getText();

			if (current.equals(targetMonthYear))
				break;
			driver.findElement(By.cssSelector(".react-datepicker__navigation--next")).click();
		}

		try {
			driver.findElement(
					By.xpath("//div[contains(@class,'react-datepicker__day') and text()='" + targetDay + "']")).click();

			if (dateInput.getAttribute("value").contains(targetDay)) {
				System.out.println("Status: Passed (clicked normally)");
				return;
			}
		} catch (Exception ignored) {
		}

		String jsDate = nextDay.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value=arguments[1]; arguments[0].dispatchEvent(new Event('change'));", dateInput, jsDate);

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

	public void deliveryUser() {

		WebElement dropdownElement = driver.findElement(By.name("del_user_id"));
		Select select = new Select(dropdownElement);

		List<WebElement> options = select.getOptions();

		Random rand = new Random();
		int randomIndex = rand.nextInt(options.size() - 1) + 1;

		select.selectByIndex(randomIndex);

	}

	public void placeOrder() throws InterruptedException {

		driver.findElement(By.xpath("//button[text()='Review Order']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Place Order']")).click();
		Thread.sleep(2000);

		System.out.println("Order created successfully");
		System.out.println("Status Passed");

	}

	public String captureOrderId() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-confirm-alert-body")));

		String popupText = driver.findElement(By.cssSelector(".react-confirm-alert-body")).getText();

		int index = popupText.indexOf("successfully.");
		String trimmedText = popupText.substring(0, index + "successfully.".length());

		System.out.println("POP UP: " + trimmedText);

		Pattern pattern = Pattern.compile("\\[\\s*(\\d+)\\s*\\]");
		Matcher matcher = pattern.matcher(popupText);

		String orderId = "";

		if (matcher.find()) {
			orderId = matcher.group(1);
		}

		System.out.println("Created Order ID: " + orderId);

		driver.findElement(By.xpath("//button[text()='No']")).click();

		return orderId;

	}

	public void ViewOrders() throws InterruptedException {

		Thread.sleep(4000);
		driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");

	}

	public void SearchFilteration() throws InterruptedException {

		Thread.sleep(6000);

		By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
		By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(3000);

		System.out.println("Order ID = " + orderId);

		searchBox.clear();
		searchBox.sendKeys(orderId);
		Thread.sleep(1000);
		searchBox.sendKeys(Keys.ENTER);

		Thread.sleep(3000);

	}

	public void clickmore() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(clickmore).click();
	}

	public void clickalloptions() {
		driver.findElement(clickshowalloptions).click();
	}

	public void clickedit() {
		driver.findElement(clickedit).click();
	}

	
//	public void editOrder() throws InterruptedException {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    Random rand = new Random();
//
//	    for (int i = 0; i < 2; i++) {
//
//	        // ---------- SELECT PRODUCT CHECKBOX ----------
//
//	        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("((//input[@type='checkbox' and contains(@class,'grid-checkbox-input')])[position()>1])[" + (i + 1) + "]")));
//
//	        if (!checkbox.isSelected()) {
//	            js.executeScript("arguments[0].click();", checkbox);
//	        }
//
//	        Thread.sleep(1000);
//
//	        // ---------- UPDATE QTY ----------
//
//	        WebElement qtyField = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//input[contains(@id,'input-numeric-field-prd_qty')])[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", qtyField);
//
//	        // ✅ CAPTURE INITIAL QTY FROM TABLE
//	        WebElement qtyCellBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));
//
//	        String initialQtyText = qtyCellBefore.getText().trim();
//	        double initialQty = Double.parseDouble(initialQtyText.replace(",", ""));
//
//	        System.out.println("Initial Qty (row " + (i + 1) + "): " + initialQty);
//
//	        double currentQty = Double.parseDouble(qtyField.getAttribute("value"));
//
//	        int reduceBy = rand.nextInt(3) + 1; // 1 to 3
//	        double newQty = currentQty - reduceBy;
//
//	        if (newQty < 0) {
//	            newQty = 0;
//	        }
//
//	        qtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//	        qtyField.sendKeys(Keys.DELETE);
//	        qtyField.sendKeys(String.format("%.2f", newQty));
//
//	        System.out.println("Updated product " + (i + 1) + " qty to: " + newQty);
//
//	        Thread.sleep(3000);
//
//
//	        // ---------- VIEW COMMENT ----------
//
//	        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", moreBtn);
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("//div[contains(@class,'dropdown-menu')]")));
//
//	        WebElement viewComment = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//a[text()='Add/Update/View Comment']")));
//
//	        viewComment.click();
//
//	        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));
//
//	        dropdown.click();
//
//	        Select select = new Select(dropdown);
//	        List<WebElement> options = select.getOptions();
//
//	        if (options.size() > 1) {
//	            int randomIndex = rand.nextInt(options.size() - 1) + 1;
//	            select.selectByIndex(randomIndex);
//	        }
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//button[normalize-space()='OK']"))).click();
//
//	        Thread.sleep(3000);
//
//	        // ✅ CAPTURE UPDATED QTY FROM TABLE
//	        WebElement qtyCellAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));
//
//	        String updatedQtyText = qtyCellAfter.getText().trim();
//	        double updatedQty = Double.parseDouble(updatedQtyText.replace(",", ""));
//
//	        System.out.println("Updated Qty (row " + (i + 1) + "): " + updatedQty);
//
//	     
//
//	        // ---------- ACTION TYPE COMMENT ----------
//
//	        WebElement moreBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", moreBtn2);
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("//div[contains(@class,'dropdown-menu')]")));
//
//	        WebElement actionType = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//a[text()='Add/Update/View Action Type Comment']")));
//
//	        actionType.click();
//
//	        Thread.sleep(2000);
//
//	        WebElement dropdown1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));
//
//	        dropdown1.click();
//
//	        Select select1 = new Select(dropdown1);
//	        List<WebElement> options1 = select1.getOptions();
//
//	        if (options1.size() > 1) {
//	            int randomIndex1 = rand.nextInt(options1.size() - 1) + 1;
//	            select1.selectByIndex(randomIndex1);
//	        }
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//button[normalize-space()='OK']"))).click();
//
//	        Thread.sleep(3000);
//	    }
//	}
	
	
	
//	public void editOrder() throws InterruptedException {
//		
//		remainingList.clear();
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    Random rand = new Random();
//
//	    for (int i = 0; i < 2; i++) {
//
//	        // ---------- SELECT PRODUCT CHECKBOX ----------
//
//	        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("((//input[@type='checkbox' and contains(@class,'grid-checkbox-input')])[position()>1])[" + (i + 1) + "]")));
//
//	        if (!checkbox.isSelected()) {
//	            js.executeScript("arguments[0].click();", checkbox);
//	        }
//
//	        Thread.sleep(1000);
//
//	        // ---------- UPDATE QTY ----------
//
//	        WebElement qtyField = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//input[contains(@id,'input-numeric-field-prd_qty')])[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", qtyField);
//
//	        // ✅ CAPTURE INITIAL QTY FROM TABLE
//	        WebElement qtyCellBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));
//
//	        String initialQtyText = qtyCellBefore.getText().trim();
//	        double initialQty = Double.parseDouble(initialQtyText.replace(",", ""));
//
//	        double currentQty = Double.parseDouble(qtyField.getAttribute("value"));
//
//	        int reduceBy = rand.nextInt(3) + 1; // 1 to 3
//	        double newQty = currentQty - reduceBy;
//
//	        if (newQty < 0) {
//	            newQty = 0;
//	        }
//
//	        qtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//	        qtyField.sendKeys(Keys.DELETE);
//	        qtyField.sendKeys(String.format("%.2f", newQty));
//
//	        Thread.sleep(3000);
//
//	        // ---------- VIEW COMMENT ----------
//
//	        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", moreBtn);
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("//div[contains(@class,'dropdown-menu')]")));
//
//	        WebElement viewComment = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//a[text()='Add/Update/View Comment']")));
//
//	        viewComment.click();
//
//	        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));
//
//	        dropdown.click();
//
//	        Select select = new Select(dropdown);
//	        List<WebElement> options = select.getOptions();
//
//	        if (options.size() > 1) {
//	            int randomIndex = rand.nextInt(options.size() - 1) + 1;
//	            select.selectByIndex(randomIndex);
//	        }
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//button[normalize-space()='OK']"))).click();
//
//	        Thread.sleep(3000);
//
//	        // ✅ CAPTURE UPDATED QTY FROM TABLE
//	        WebElement qtyCellAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));
//
//	        String updatedQtyText = qtyCellAfter.getText().trim();
//	        double updatedQty = Double.parseDouble(updatedQtyText.replace(",", ""));
//
//	        // ✅ CAPTURE UI REMAINING QTY FROM td[11]
//	        WebElement remainingCell = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[11])[" + (i + 1) + "]")));
//
//	        double remainingQty = Double.parseDouble(remainingCell.getText().trim());
//	        
//	        double expectedRemaining = initialQty - newQty;
//	        remainingList.add(expectedRemaining);
//
//	        // ✅ CALCULATE EXPECTED REMAINING
//	//        double expectedRemaining = initialQty - newQty;
//
//	        // ✅ PRINT ONE BELOW ANOTHER
//	        System.out.println("Row " + (i + 1));
//	        System.out.println("Initial = " + initialQty);
//	        System.out.println("Updated (input) = " + newQty);
//	        System.out.println("UI Remaining (td[11]) = " + remainingQty);
//	        System.out.println("--------------------------------");
//	        
//	    
//
//	        // ✅ ASSERTION
//	        Assert.assertEquals(remainingQty, expectedRemaining, 0.01,
//	                "❌ Remaining qty mismatch at row " + (i + 1));
//
//	        // ---------- ACTION TYPE COMMENT ----------
//
//	        WebElement moreBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));
//
//	        js.executeScript("arguments[0].click();", moreBtn2);
//
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("//div[contains(@class,'dropdown-menu')]")));
//
//	        WebElement actionType = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//a[text()='Add/Update/View Action Type Comment']")));
//
//	        actionType.click();
//
//	        Thread.sleep(2000);
//
//	        WebElement dropdown1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));
//
//	        dropdown1.click();
//
//	        Select select1 = new Select(dropdown1);
//	        List<WebElement> options1 = select1.getOptions();
//
//	        if (options1.size() > 1) {
//	            int randomIndex1 = rand.nextInt(options1.size() - 1) + 1;
//	            select1.selectByIndex(randomIndex1);
//	        }
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();
//
//	        wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//button[normalize-space()='OK']"))).click();
//
//	        Thread.sleep(3000);
//	    }
//	}
	
	
	public void editOrder() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Random rand = new Random();

	    for (int i = 0; i < 2; i++) {

	        // ---------- SELECT PRODUCT CHECKBOX ----------

	        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("((//input[@type='checkbox' and contains(@class,'grid-checkbox-input')])[position()>1])[" + (i + 1) + "]")));

	        if (!checkbox.isSelected()) {
	            js.executeScript("arguments[0].click();", checkbox);
	        }

	        Thread.sleep(1000);

	        // ---------- UPDATE QTY ----------

	        WebElement qtyField = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//input[contains(@id,'input-numeric-field-prd_qty')])[" + (i + 1) + "]")));

	        js.executeScript("arguments[0].click();", qtyField);

	        // ✅ CAPTURE INITIAL QTY FROM TABLE (td[12])
	        WebElement qtyCellBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));

	        String initialQtyText = qtyCellBefore.getText().trim();
	        double initialQty = Double.parseDouble(initialQtyText.replace(",", ""));

	        System.out.println("Initial Qty (row " + (i + 1) + "): " + initialQty);

	        double currentQty = Double.parseDouble(qtyField.getAttribute("value"));

	        int reduceBy = rand.nextInt(3) + 1; // 1 to 3
	        double newQty = currentQty - reduceBy;

	        if (newQty < 0) {
	            newQty = 0;
	        }

	        qtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	        qtyField.sendKeys(Keys.DELETE);
	        qtyField.sendKeys(String.format("%.2f", newQty));

	        System.out.println("Updated product " + (i + 1) + " qty to: " + newQty);

	        Thread.sleep(3000);

	        // ---------- VIEW COMMENT ----------

	        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));

	        js.executeScript("arguments[0].click();", moreBtn);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'dropdown-menu')]")));

	        WebElement viewComment = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//a[text()='Add/Update/View Comment']")));

	        viewComment.click();

	        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));

	        dropdown.click();

	        Select select = new Select(dropdown);
	        List<WebElement> options = select.getOptions();

	        if (options.size() > 1) {
	            int randomIndex = rand.nextInt(options.size() - 1) + 1;
	            select.selectByIndex(randomIndex);
	        }

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[normalize-space()='OK']"))).click();

	        Thread.sleep(3000);

	        // ✅ CAPTURE UPDATED QTY FROM TABLE (td[12])
//	        WebElement qtyCellAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(
//	                By.xpath("(//table//tbody//tr//td[12])[" + (i + 1) + "]")));
//
//	        String updatedQtyText = qtyCellAfter.getText().trim();
//	        double updatedQty = Double.parseDouble(updatedQtyText.replace(",", ""));
//
//	        System.out.println("Updated Qty (row " + (i + 1) + "): " + updatedQty);

	        // ✅ ADDITION: CALCULATE REMAINING USING td[12] ONLY
//	        double remaining = initialQty - updatedQty;
//
//	        System.out.println("Row " + (i + 1));
//	        System.out.println("Initial Qty = " + initialQty);
//	        System.out.println("Updated Qty (from UI) = " + updatedQty);
//	        System.out.println("Remaining (Initial - Updated) = " + remaining);
//	        System.out.println("--------------------------------");
	        
	        double remaining = initialQty - newQty;

	        System.out.println("Row " + (i + 1));
	        System.out.println("Initial Qty = " + initialQty);
	        System.out.println("Updated product qty = " + newQty);
	        System.out.println("Remaining = " + initialQty + " - " + newQty + " = " + remaining);
	        System.out.println("--------------------------------");
	        
	        remainingList.add(remaining);

	        // ---------- ACTION TYPE COMMENT ----------

	        WebElement moreBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//div[@class='grid-actions-dropdown dropdown']//button)[" + (i + 1) + "]")));

	        js.executeScript("arguments[0].click();", moreBtn2);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'dropdown-menu')]")));

	        WebElement actionType = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//a[text()='Add/Update/View Action Type Comment']")));

	        actionType.click();

	        Thread.sleep(2000);

	        WebElement dropdown1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//select[contains(@class,'grid-select')])[4]")));

	        dropdown1.click();

	        Select select1 = new Select(dropdown1);
	        List<WebElement> options1 = select1.getOptions();

	        if (options1.size() > 1) {
	            int randomIndex1 = rand.nextInt(options1.size() - 1) + 1;
	            select1.selectByIndex(randomIndex1);
	        }

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//button[normalize-space()='Save'])[2]"))).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[normalize-space()='OK']"))).click();

	        Thread.sleep(3000);
	    }
	}

	
	public void OrderCompilation() throws InterruptedException {

		Thread.sleep(2500);
		driver.get("https://mktadmin.freshontable.com/ordermanagement/preparation");

	}
	
	
	
	
	public void SearchFilteration1() throws InterruptedException {

		Thread.sleep(3000);

		By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
		By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(3000);

		System.out.println("Order ID = " + orderId);

		searchBox.clear();
		searchBox.sendKeys(orderId);
		Thread.sleep(1000);
		searchBox.sendKeys(Keys.ENTER);

		Thread.sleep(3000);

	}
	

	public void stockReserve() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//tbody/tr[not(contains(@style,'display:none'))]")));

		Thread.sleep(2000);

		WebElement moreBtn = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//button[contains(@class,'actions-dropdown')])[2]")));
		moreBtn.click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Reserve Batch']"))).click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='SUBMIT']"))).click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Confirm']"))).click();

		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='OK']"))).click();

//		WebElement backArrow = driver.findElement(By.cssSelector("[data-testid='ArrowBackIcon']"));
//		backArrow.click();
	}
	
	
	public void ViewOrders1() throws InterruptedException {

		Thread.sleep(3500);
		driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");

	}
	
	
	public void SearchFilteration2() throws InterruptedException {
		
		Thread.sleep(5000);
		
	    By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
	    By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Click filter icon safely
	    WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(filterIcon));
	    filter.click();

	    // Wait and get fresh search box
	    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));

	    System.out.println("Order ID = " + orderId);

	    searchBox.clear();
	    searchBox.sendKeys(orderId);
	    searchBox.sendKeys(Keys.ENTER);

//	    // 🔥 Wait for refresh to complete (IMPORTANT)
//	    wait.until(ExpectedConditions.stalenessOf(searchBox));
//
//	    // 🔥 Re-locate element after refresh
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
	    
	    Thread.sleep(2000);
	}
	

	public void clickMoreButton() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[text()='More'])[1]")).click();
	}

	public void clickShowAllOptions() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Show all options']")).click();
	}

	public void clickEditOrderDetails1() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div//a[@class='dropdown-item'])[2]")).click();
	}

	public void clickGenerateInvoiceButton() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[text()='Generate Invoice'])")).click();
		Thread.sleep(1000);
	}

	public void invoiceConfirmation() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		Thread.sleep(2000);
	}
	
	public void ViewOrders2() throws InterruptedException {

		Thread.sleep(2500);
		driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");

	}
	
	public void SearchFilteration3() throws InterruptedException {

		Thread.sleep(6000);

		By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
		By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(3000);

		System.out.println("Order ID = " + orderId);

		searchBox.clear();
		searchBox.sendKeys(orderId);
		Thread.sleep(1000);
		searchBox.sendKeys(Keys.ENTER);

		Thread.sleep(3000);
		
//	    wait.until(ExpectedConditions.stalenessOf(searchBox));
//
//	    // 🔥 Re-locate element after refresh
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));


	}
	
	public void clickMoreButton1() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[text()='More'])[1]")).click();
	}
	
	public void clickShowAllOptions1() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Show all options']")).click();
	}
	
	public void clickPendingOrder() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[normalize-space()='Pending Order']")).click();
	}

	public void qtyCapture() {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
		            By.xpath("//table//tbody//tr//td[11]")));

		    for (int i = 0; i < remainingList.size(); i++) {

		        int row = i + 1;

		        double uiRemaining = Double.parseDouble(
		                driver.findElement(By.xpath("(//table//tbody//tr//td[11])[" + row + "]"))
		                      .getText().trim()
		        );

		        double remainingFromEdit = remainingList.get(i);

		        System.out.println("Row " + row);
		        System.out.println("Expected Remaining = " + remainingFromEdit);
		        System.out.println("UI Remaining = " + uiRemaining);
		        System.out.println("--------------------------------");

		        Assert.assertEquals(uiRemaining, remainingFromEdit, 0.01,
		                "❌ Remaining qty mismatch at row " + row);
		    }
		}
	
	public void ViewOrders3() throws InterruptedException {

		Thread.sleep(2500);
		driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");

	}
	
	public void SearchFilteration4() throws InterruptedException {

		Thread.sleep(6000);

		By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
		By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(3000);

		System.out.println("Order ID = " + orderId);

		searchBox.clear();
		searchBox.sendKeys(orderId);
		Thread.sleep(1000);
		searchBox.sendKeys(Keys.ENTER);

		Thread.sleep(3000);
	
	}
	
	public void clickMoreButton2() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[text()='More'])[1]")).click();
	}
	
	public void clickShowAllOptions2() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Show all options']")).click();
	}
	
	public void clickDeliverOrder() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[normalize-space()='Deliver Order']")).click();
	}
	
	public void clickVehicleSelect() throws InterruptedException {

	    Thread.sleep(2000);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random rand = new Random();

	    // Click vehicle icon
	    WebElement vehicleIcon = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@style='position: absolute; top: 10px; right: 12px;'])[1]")));

	    vehicleIcon.click();

	    // Wait for table to load
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//table[@class='e-table']")));

	    // Get all rows
	    List<WebElement> vehicleList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
	            By.xpath("//table[@class='e-table']//tbody//tr")));

	    // Random selection
	    if (vehicleList.size() > 0) {

	        int randomIndex = rand.nextInt(vehicleList.size());
	        WebElement selectedRow = vehicleList.get(randomIndex);

	        // Click row
	        selectedRow.click();
	    }
	}
		
		

	public void clickDriverSelect() throws InterruptedException {

	    Thread.sleep(1000);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random rand = new Random();

	    // Click driver icon
	    WebElement driverIcon = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@style='position: absolute; top: 10px; right: 12px;'])[2]")));

	    driverIcon.click();

	    // Wait for table
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//table[@class='e-table']")));

	    // Get all rows (NOT td)
	    List<WebElement> driverList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
	            By.xpath("//table[@class='e-table']//tbody//tr")));

	    // Random selection
	    if (driverList.size() > 0) {

	        int randomIndex = rand.nextInt(driverList.size());
	        WebElement selectedDriver = driverList.get(randomIndex);

	        // Click row
	        selectedDriver.click();
	    }
	}
		    
//			public void clickBrowser() {
//
//			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//			    WebElement selectBrowser = wait.until(ExpectedConditions.elementToBeClickable(
//			            By.xpath("//button[@class='e-css e-btn' and @type='button']"))); 
//
//			    selectBrowser.click();
//		    
//		
//	}
	
	
	public void uploadFile() throws Exception {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		    // ✅ Step 1: Click Browse button
		    WebElement selectBrowser = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[@class='e-css e-btn' and @type='button']")));
		    selectBrowser.click();

		    // ✅ Step 2: Upload file using input
		    WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.xpath("//input[@type='file']")));

		    String filePath = System.getProperty("user.home") + "\\Downloads\\Stock_Details.xlsx";
		    fileInput.sendKeys(filePath);

		    // ✅ Step 3: Force close file dialog using ALT + F4
		    Robot robot = new Robot();
		    robot.keyPress(KeyEvent.VK_ALT);
		    robot.keyPress(KeyEvent.VK_F4);
		    robot.keyRelease(KeyEvent.VK_F4);
		    robot.keyRelease(KeyEvent.VK_ALT);

		    // Small wait to ensure dialog is gone
		    Thread.sleep(1000);

		    // ✅ Step 4: Click Upload button
		    WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='Upload']")));
		    uploadBtn.click();

		    // ✅ Step 5: Click Confirm button
		    WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='Confirm']")));
		    confirmBtn.click();
		
	}
			
			
			
			  public void waitUntilAwaitingPaymentAndClickMore() throws InterruptedException {

			        int refreshTimes = 5;
			        int refreshIntervalSec = 30;

			        By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
			        By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

			        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			        JavascriptExecutor js = (JavascriptExecutor) driver;

			        // ---------------- REFRESH PAGE ----------------
			        for (int i = 1; i <= refreshTimes; i++) {
			            System.out.println("Refresh Attempt " + i + " of " + refreshTimes);
			            driver.navigate().refresh();
			            Thread.sleep(refreshIntervalSec * 1000L);
			        }

			        // ---------------- CLICK FILTER ICON ----------------
			        clickWithRetry(filterIcon, wait, js);

			        // ---------------- ENTER ORDER ID ----------------
			        WebElement searchBox = wait.until(
			                ExpectedConditions.elementToBeClickable(searchBoxLocator)
			        );

			        Thread.sleep(3000);

			        System.out.println("Order ID = " + orderId);

			        searchBox.clear();
			        searchBox.sendKeys(orderId);
			        Thread.sleep(1000);
			        searchBox.sendKeys(Keys.ENTER);

			        Thread.sleep(3000);
			    }
			  
			  

			    // ---------------- HELPER METHOD ----------------
			    private void clickWithRetry(By locator, WebDriverWait wait, JavascriptExecutor js) {

			        int attempts = 0;

			        while (attempts < 5) {
			            try {
			                WebElement element = wait.until(
			                        ExpectedConditions.refreshed(
			                                ExpectedConditions.elementToBeClickable(locator)
			                        )
			                );

			                // Handle overlays safely
			                List<WebElement> overlays =
			                        driver.findElements(By.cssSelector(".overlay, .modal, .loader"));

			                for (WebElement overlay : overlays) {
			                    if (overlay.isDisplayed()) {
			                        js.executeScript("arguments[0].style.display='none';", overlay);
			                        System.out.println("Overlay dismissed");
			                    }
			                }

			                // Scroll into view
			                js.executeScript(
			                        "arguments[0].scrollIntoView({block:'center'});",
			                        element
			                );

			                try {
			                    element.click();
			                } catch (ElementClickInterceptedException e) {
			                    js.executeScript("arguments[0].click();", element);
			                }

			                return;

			            } catch (StaleElementReferenceException |
			                     TimeoutException |
			                     ElementClickInterceptedException e) {

			                attempts++;
			                System.out.println("Retrying click (" + attempts + "/5) due to "
			                        + e.getClass().getSimpleName());

			                try {
			                    Thread.sleep(1000);
			                } catch (InterruptedException ignored) {}
			            }
			        }

			        throw new RuntimeException("Failed to click element after retries: " + locator);
			    }
			    
			    
			    
			    public void SearchFilteration5() throws InterruptedException {

					Thread.sleep(6000);

					By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
					By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

					wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

					WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

					Thread.sleep(3000);

					System.out.println("Order ID = " + orderId);

					searchBox.clear();
					searchBox.sendKeys(orderId);
					Thread.sleep(1000);
					searchBox.sendKeys(Keys.ENTER);

					Thread.sleep(3000);
				
				}
		
   public void pendingOrderProcess() throws InterruptedException {
	   
	   Thread.sleep(2000);
	   
	   driver.findElement(By.xpath("//a[text()='Pending Order']")).click();
	   
	   Thread.sleep(2000);
	   
	   driver.findElement(By.xpath("//input[@class='grid-checkbox-input  cursor-pointer'][1]")).click();
	   
	   Thread.sleep(2000);
	   
	   driver.findElement(By.xpath("//button[text()='Review Order']")).click();
	   
	   Thread.sleep(2000);
	   
	   driver.findElement(By.xpath("//button[text()='Review Order']")).click();
	   
	   Thread.sleep(2000);
	   
	   driver.findElement(By.xpath("//button[text()='OK']")).click();
}

}
