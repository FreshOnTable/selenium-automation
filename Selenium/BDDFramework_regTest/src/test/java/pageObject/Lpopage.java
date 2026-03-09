package pageObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Lpopage {

	public WebDriver driver;

	public Lpopage(WebDriver driver) {
		this.driver = driver;
	}

	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickprocur = By.xpath("//ul//li//span[text()='Procurement']");
	By clickLpogrid = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/ul[5]/ul/li[3]/span/span");
	By clickfilterbutton = By
			.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[1]/img");
	By selectwearhouse = By.name("del_hub_id");
	By Applyfilter = By.xpath("//button[text()='Apply Filters']");
	By selectproducts = By.xpath("//table//tbody//tr/td[1]");
	By NextButton = By.xpath("//div[text()='Next ']");
	By SubmitButton = By.xpath("//button[text()='SUBMIT']");
	By ConfirmButton = By.xpath("//button[text()='Confirm']");
	By NoButton = By.xpath("//button[text()='No']");
	By Validation = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div[1]/div/div[2]/button");

	public void clickthreedot() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(clickgrid).click();
	}

	public void clickprocurement() {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(clickprocur).click();
	}

	public void clickLpo() {
		driver.findElement(clickLpogrid).click();
	}

	public void clickwearhousefitler() {
		driver.findElement(clickfilterbutton).click();
	}

	public void wearhousedropdown() throws InterruptedException {
		// driver.findElement(wearhouseDropdown).click();
		WebElement warehouseDropdown = driver.findElement(selectwearhouse);
		warehouseDropdown.click();

		Select select = new Select(warehouseDropdown);
		select.selectByValue("50127"); // 50465 - Abu Dhabi
		// select.selectByIndex(1);
	}

//	public void selectwearouse() {
//		driver.findElement(Applyfilter).click();
//	}
	
	public void applyFiltersSafely() {

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    WebElement applyBtn = wait.until(
	            ExpectedConditions.presenceOfElementLocated(
	                    By.xpath("//button[normalize-space()='Apply Filters']")
	            )
	    );

	    // Scroll to center
	    js.executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", applyBtn
	    );

	    // JS click (bypasses overlay)
	    js.executeScript("arguments[0].click();", applyBtn);

	    System.out.println("Apply Filters clicked");
	}

	public void selectproducts() throws InterruptedException {

		driver.findElement(
				By.xpath("(//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium filter-icon  css-vubbuv'])[5]"))
				.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		WebElement searchBox = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=\"filter-popover-ord_id\"]/div[3]/div/div[1]/input")));
		Thread.sleep(1000);
		String orderId = Placeorder.orderId;
		System.out.println("Order ID = " + orderId);
		searchBox.sendKeys(orderId);
		searchBox.sendKeys(Keys.ENTER);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".react-confirm-alert-overlay")));

		List<WebElement> checkboxes = driver.findElements(selectproducts);
		System.out.println(checkboxes.size());

		for (int i = 0; i < 4 && i < checkboxes.size(); i++) {
			WebElement checkbox = checkboxes.get(i);
			if (!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}

	public void clicknextbutton() {
		driver.findElement(NextButton).click();
	}

	public void clicksubmittbutton() {
		driver.findElement(SubmitButton).click();
	}

	public void ClickConfirmButton() {
		driver.findElement(ConfirmButton).click();
	}

	public void clickNoButton() {
		driver.findElement(NoButton).click();
	}

	/*
	 * public void CreateLpoValidation() { WebElement element =
	 * driver.findElement(By.xpath(
	 * "/html/body/div[1]/div/div[2]/div[2]/div[1]/div/div[2]/button")); String
	 * expectedValue = "Create LPOs"; String actualValue = element.getText();
	 * Assert.assertEquals(actualValue, expectedValue, "The values don't match!");
	 * System.out.println("Lpo created sucessfully : " + actualValue); }
	 */

	public void processTopWaitingForAcceptanceLpos(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processSingleLpo(i);
			processed++;
		}
	}

	private void processSingleLpo(int rowIndex) throws InterruptedException {

		changeStatus(rowIndex, "Accepted But Unfulfilled");
		changeStatus(rowIndex, "LPO At Hub");
		Lpofulfillment(rowIndex);
		enterInvoiceDetails(rowIndex);
//		veryfyAndApprove(rowIndex);
	}

	private void changeStatus(int rowIndex, String statusText) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
		changeStatus.click();

		// Select new status
		Select select = new Select(
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
		select.selectByVisibleText(statusText);

		driver.findElement(By.xpath("//textarea[@name='sName']")).sendKeys("Status updated automatically");

		driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		// driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@id='react-confirm-alert']//button[normalize-space()='OK']")));
			okBtn.click();
			System.out.println("OK popup clicked successfully");

			System.out.println("Row " + rowIndex + " → Status changed to: " + statusText);
		} catch (Exception e) {
			System.out.println("No OK popup displayed.");
		}
	}

	public void Lpofulfillment(int rowIndex) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
		option.click();
	}

	public void enterInvoiceDetails(int rowIndex) throws InterruptedException {
		Random random = new Random();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		// ----------------------------
		// 1️⃣ INVOICE NUMBER – random 10 digits
		// ----------------------------
		String invoiceNumber = String.valueOf(1000000000L + random.nextInt(900000000));
		WebElement invoiceNo = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='sup_inv_no']")));
		invoiceNo.sendKeys(invoiceNumber);

	
		// 2️⃣ INVOICE DATE – pick any valid visible date
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

		/* 1️⃣ Open invoice date picker */
		WebElement invoiceDateInput = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath("//input[@name='supplier_inv_dt']")
		        )
		);
		invoiceDateInput.click();

		/* 2️⃣ Wait for datepicker to be visible */
		wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.className("react-datepicker")
		));

		/* 3️⃣ Click NEXT DAY (tomorrow) */
		WebElement nextDay = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath(
		                        "//div[contains(@class,'react-datepicker__day--today')]" +
		                        "/following-sibling::div[not(contains(@class,'react-datepicker__day--disabled'))][1]"
		                )
		        )
		);

		nextDay.click();

		System.out.println("Invoice date selected: Next day (Tomorrow)");


		 Thread.sleep(2000);
		// ----------------------------
		// 3️⃣ INVOICE VALUE – random 4 digits
		// ----------------------------
		String invoiceValue = String.valueOf(1000 + random.nextInt(9000));
		WebElement invoiceVal = driver.findElement(By.xpath("//input[@name='sup_inv_val']"));
		invoiceVal.sendKeys(invoiceValue);
		Thread.sleep(2000);

		// ----------------------------
		// 4️⃣ RATING – random star click (1 to 5 stars)
		// ----------------------------
		int star = new Random().nextInt(5) + 1;

		WebElement ratingStar = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//label[contains(@class,'css-ykqdxu')])[" + star + "]")));

		ratingStar.click();
		Thread.sleep(1000);

		// ----------------------------
		// 5️⃣ SUPPLIER UNIT PRICE – 2-digit random value
		// ----------------------------
		Thread.sleep(2000);
		String supplierPrice = String.valueOf(10 + random.nextInt(90));
		WebElement supplierUnitPrice = driver.findElement(By.xpath("(//input[@type='number'])[3]"));
		supplierUnitPrice.sendKeys(supplierPrice);

		try {
			WebElement popupMsg = new WebDriverWait(driver, Duration.ofSeconds(3))
					.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[contains(text(),'Supplier Unit Price entered is higher')]")));

			WebElement okBtn = new WebDriverWait(driver, Duration.ofSeconds(3))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='OK']")));

			okBtn.click();
			System.out.println("Supplier price popup handled successfully.");

		} catch (Exception e) {
			System.out.println("No Supplier Price popup displayed.");
		}

		// 6️⃣ EXPIRY DATE – pick any FUTURE date from current month

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		Random random2 = new Random();

		/* 1️⃣ Open expiry date picker */
		WebElement expiryIcon = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,'e-date-icon')])[1]")));
		expiryIcon.click();

		/* 2️⃣ Calculate date range */
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.plusDays(1); // ⬅ exclude today
		LocalDate maxDate = today.plusDays(7);

		/* 3️⃣ Pick RANDOM future date between today and +7 */
		long daysBetween = ChronoUnit.DAYS.between(startDate, maxDate);
		LocalDate targetDate = startDate.plusDays(random.nextInt((int) daysBetween + 1));

		int targetDay = targetDate.getDayOfMonth();
		Month targetMonth = targetDate.getMonth();
		Month currentMonth = today.getMonth();

		/* 4️⃣ Move to next month if required */
		if (!targetMonth.equals(currentMonth)) {
			WebElement nextMonthBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'e-next')]")));
			nextMonthBtn.click();
		}

		/* 5️⃣ Click target date */
		WebElement targetDateElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'e-day') and "
						+ "not(contains(@class,'e-disabled')) and " + "text()='" + targetDay + "']")));
		targetDateElement.click();

		System.out.println("Expiry date selected: " + targetDate);

		// 4️⃣ Click Submit
		WebElement submitBtn = wait2
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='SUBMIT']")));
		submitBtn.click();

		// 5️⃣ Click Confirm
		WebElement confirmBtn = wait2
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Confirm']")));
		confirmBtn.click();

		// click ok
		WebElement ok = wait2
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='OK']")));
		ok.click();
	}
	
	
////////////////////////////////////////////////////////////////////////////	
	//received zero qty and complete lpo manuvally
	
	By productCard = By.xpath("//div[contains(@class,'product-card')]");

	By recQtyInput = By.xpath(".//input[contains(@name,'received_qty')]");
	By accQtyText = By.xpath(".//span[contains(@class,'accepted-qty')]");

	By receivingCommentDropdown =
	        By.xpath(".//select[contains(@class,'receiving-comment')]");

	By submitButton = By.xpath("//button[normalize-space()='Submit']");
	By moreButton = By.xpath("//button[normalize-space()='More']");

	By moreOptions =
	        By.xpath("//div[contains(@class,'dropdown-menu')]//a");
	

	public void searchSeller() throws InterruptedException {
		Thread.sleep(2000);
	    // Click seller filter icon
	    driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div[2]/div[1]/table/thead/tr/th[7]/div[2]")).click(); //(//div[@class='custom-filter-icon'])[4]
	    
        Thread.sleep(1000);
	    // Type seller name and search
	    WebElement sellerInput = driver.findElement(By.xpath("(//input[@type='text'])[3]"));

//	    sellerInput.clear();
	    sellerInput.sendKeys("Natural Green Farm LLC");
	    sellerInput.sendKeys(Keys.ENTER);
	}
/*	public void selectRandomproducts() {

	    List<WebElement> checkboxes =
	            driver.findElements(By.xpath("//table//tbody//input[@type='checkbox']"));

	    int randomIndex = new Random().nextInt(checkboxes.size());

	    WebElement checkbox = checkboxes.get(randomIndex);

	    if (!checkbox.isSelected()) {
	        checkbox.click();
	    }
	}*/
	
	public void selectAllProducts() {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<WebElement> checkboxes =
	            driver.findElements(By.xpath("//table//tbody//input[@type='checkbox']"));

	    for (WebElement checkbox : checkboxes) {

	        if (!checkbox.isSelected()) {

	            // Scroll checkbox into view
	            js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);

	            // Click using JS to avoid interception
	            js.executeScript("arguments[0].click();", checkbox);
	        }
	    }
	}

	
	public void selectRandomDeliveryHub() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Random random = new Random();

	    int lpoCount = driver.findElements(
	            By.xpath("//div[contains(@class,'lpo-accordion')]")
	    ).size();

	    System.out.println("Total LPOs found: " + lpoCount);

	    for (int i = 0; i < lpoCount; i++) {

	        WebElement accordion = driver.findElements(
	                By.xpath("//div[contains(@class,'lpo-accordion')]")
	        ).get(i);

	        js.executeScript(
	                "arguments[0].scrollIntoView({block:'center'});" +
	                "window.scrollBy(0,-120);",
	                accordion
	        );

	        if (i > 0) {
	            WebElement accordionHeader = accordion.findElement(
	                    By.xpath(".//div[contains(@class,'accordion-top')]")
	            );

	            js.executeScript("arguments[0].click();", accordionHeader);
	        }

	        WebElement deliveryHubDropdown = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//div[contains(@class,'lpo-accordion')])[" + (i + 1) + "]//select[@name='del_hub_id_select']")
	                )
	        );

	        deliveryHubDropdown.click();

	        Select select = new Select(deliveryHubDropdown);
	        List<WebElement> options = select.getOptions();

	        if (options.size() <= 2) {
	            throw new RuntimeException("No valid Delivery Hub options in LPO " + (i + 1));
	        }

	        int randomIndex = random.nextInt(options.size() - 2) + 2;
	        select.selectByIndex(randomIndex);

	        System.out.println(
	                "LPO " + (i + 1) + " → Selected Delivery Hub: " +
	                select.getFirstSelectedOption().getText()
	        );

	    }
	}
	
	public void processTopWaitingForAcceptanceLpos1(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processSingleLpo1(i);
			processed++;
		}
	}

	private void processSingleLpo1(int rowIndex) throws InterruptedException {

		changeStatus1(rowIndex, "Accepted But Unfulfilled");
		changeStatus1(rowIndex, "LPO At Hub");
		Lpofulfillment1(rowIndex);
		enterInvoiceDetails1(rowIndex);
//		veryfyAndApprove1(rowIndex);
	}

	private void changeStatus1(int rowIndex, String statusText) throws InterruptedException  {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
		changeStatus.click();

		// Select new status
	
		Select select = new Select(
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
		select.selectByVisibleText(statusText);

		driver.findElement(By.xpath("//textarea[@name='sName']")).sendKeys("Status updated automatically");

		driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		Thread.sleep(3000);
		 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
//	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
//		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
	/*	try {
			WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@id='react-confirm-alert']//button[normalize-space()='OK']")));
			Thread.sleep(1000);
			okBtn.click();
			System.out.println("OK popup clicked successfully");

			System.out.println("Row " + rowIndex + " → Status changed to: " + statusText);
		} catch (Exception e) {
			System.out.println("No OK popup displayed.");
		}
	}*/
		

	}
	public void Lpofulfillment1(int rowIndex) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
//		Thread.sleep(1000);
		moreBtn.click();

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
		option.click();
	}
	

	public void enterInvoiceDetails1(int rowIndex) throws InterruptedException {
		Random random = new Random();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		// ----------------------------
		// 1️⃣ INVOICE NUMBER – random 10 digits
		// ----------------------------
		String invoiceNumber = String.valueOf(1000000000L + random.nextInt(900000000));
		WebElement invoiceNo = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='sup_inv_no']")));
		invoiceNo.sendKeys(invoiceNumber);

	
		// 2️⃣ INVOICE DATE – pick any valid visible date
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

		/* 1️⃣ Open invoice date picker */
		WebElement invoiceDateInput = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath("//input[@name='supplier_inv_dt']")
		        )
		);
		invoiceDateInput.click();

		/* 2️⃣ Wait for datepicker to be visible */
		wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.className("react-datepicker")
		));

		/* 3️⃣ Click NEXT DAY (tomorrow) */
		WebElement nextDay = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath(
		                        "//div[contains(@class,'react-datepicker__day--today')]" +
		                        "/following-sibling::div[not(contains(@class,'react-datepicker__day--disabled'))][1]"
		                )
		        )
		);

		nextDay.click();

		System.out.println("Invoice date selected: Next day");


		 Thread.sleep(2000);
		// ----------------------------
		// 3️⃣ INVOICE VALUE – random 4 digits
		// ----------------------------
		String invoiceValue = String.valueOf(1000 + random.nextInt(9000));
		WebElement invoiceVal = driver.findElement(By.xpath("//input[@name='sup_inv_val']"));
		invoiceVal.sendKeys(invoiceValue);
		Thread.sleep(2000);
	}	        
	
	public void receiveProductQtyzeroAndVeryfyDN() throws InterruptedException {

	    Random random = new Random();
	    int zeroQtyProducts = random.nextInt(2) + 1;
	    int zeroQtyCounter = 0;

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<WebElement> accordions = driver.findElements(
	            By.xpath("//div[contains(@class,'lpoqc-accordion shadow-sm')]")
	    );

	    for (int i = 0; i < accordions.size(); i++) {

	        // 🔁 Re-fetch accordion every loop
	        WebElement accordion = driver.findElements(
	                By.xpath("//div[contains(@class,'lpoqc-accordion shadow-sm')]")
	        ).get(i);

	        js.executeScript("arguments[0].scrollIntoView(true);", accordion);
	        accordion.click();

	        // ⭐ Rating
	        int rating = random.nextInt(5) + 1;
	        accordion.findElements(
	                By.xpath(".//label[contains(@class,'css-ykqdxu')]")
	        ).get(rating - 1).click();
	        

	        // ================= SHIPPING / EXPIRY DATE (SYNCFUSION FIX) =================
	        WebElement expiryInput = accordion.findElement(
	                By.xpath(".//input[contains(@class,'e-datepicker')]")
	        );

	        js.executeScript("arguments[0].scrollIntoView(true);", expiryInput);

	        // Focus input
	        expiryInput.click();
	        Thread.sleep(300);

	        // Open calendar
	        WebElement calendarIcon = accordion.findElement(
	                By.xpath(".//span[contains(@class,'e-date-icon')]")
	        );
	        calendarIcon.click();
	        Thread.sleep(300);

	        // Select a FUTURE enabled date
	        List<WebElement> enabledDays = driver.findElements(
	        	    By.xpath("//span[contains(@class,'e-day') and not(contains(@class,'e-disabled'))]")
	        	);

	        	boolean dateSelected = false;

	        	for (WebElement day : enabledDays) {

	        	    String dayText = day.getText().trim();

	        	    // ❗ Skip empty or non-numeric cells
	        	    if (dayText.isEmpty() || !dayText.matches("\\d+")) {
	        	        continue;
	        	    }

	        	    int dayNum = Integer.parseInt(dayText);

	        	    if (dayNum > LocalDate.now().getDayOfMonth()) {
	        	        day.click();
	        	        dateSelected = true;
	        	        break;
	        	    }
	        	}

	        	if (!dateSelected) {
	        	    throw new RuntimeException("❌ No valid future shipping date found");
	        	}	        
	                

	        // Allow Syncfusion to commit date internally
	        Thread.sleep(500);

	        // Blur to trigger validation + green tick
	        js.executeScript(
	                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
	                "arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));",
	                expiryInput
	        );

	        // Final safety check
	        String committedDate = expiryInput.getAttribute("value");
	        if (committedDate == null || committedDate.trim().isEmpty()) {
	            throw new RuntimeException("❌ Shipping / Expiry date NOT committed");
	        }

	        System.out.println("✅ Shipping date selected: " + committedDate);
	        // ==========================================================================

	        // 📦 Quantity
	        WebElement qtyInput = accordion.findElement(
	                By.xpath(".//input[contains(@class,'fullfill_rec_qty')]")
	        );

	        // 💰 Supplier Unit Price
	        WebElement supplierPriceInput = accordion.findElement(
	                By.xpath(".//div[contains(@class,'supplierUnitPrice')]//input")
	        );

	        if (zeroQtyCounter < zeroQtyProducts) {

	            qtyInput.clear();
	            qtyInput.sendKeys("0");

	            js.executeScript(
	                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
	                    qtyInput
	            );

	            supplierPriceInput.clear();
	            supplierPriceInput.sendKeys("0");

	            js.executeScript(
	                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
	                    supplierPriceInput
	            );

	            // Mandatory receiving comment
	            Select comment = new Select(
	                    accordion.findElement(
	                            By.xpath(".//div[contains(@class,'receiving-comments-select')]//select")
	                    )
	            );
	            comment.selectByIndex(1);

	            zeroQtyCounter++;

	        } else {

	            WebElement lpoUnitPriceInput = accordion.findElement(
	                    By.xpath(".//div[contains(@class,'unit-price-cont')]//input[@type='number']")
	            );

	            double lpoUnitPrice = Double.parseDouble(
	                    lpoUnitPriceInput.getAttribute("value")
	            );

	            double supplierPrice = random.nextInt(100) + 1;

	            supplierPriceInput.clear();
	            supplierPriceInput.sendKeys(String.valueOf(supplierPrice));

	            js.executeScript(
	                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
	                    "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
	                    supplierPriceInput
	            );

	            // Popup validation
	            if (supplierPrice > lpoUnitPrice) {

	                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	                wait.until(
	                        ExpectedConditions.visibilityOfElementLocated(
	                                By.xpath("//div[contains(text(),'Supplier Unit Price entered is higher')]")
	                        )
	                );

	                driver.findElement(By.xpath("//button[text()='OK']")).click();
	            }
	        }
	    }

	    // ================= SUBMIT FLOW =================
	    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
	    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
	}
	
	public void processTopPartiallyFulfilledAndAwaitingForApproval_status(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Partially Fulfilled And Awaiting For Approval")) {
				continue;
			}

			processSingleLpo2(i);
			processed++;
		}
	}
	
	private void processSingleLpo2(int rowIndex) throws InterruptedException {

		changeStatus2(rowIndex, "Partially Fulfilled And Awaiting For Approval");
		completeLPO(rowIndex, "Completed");
		downloadDN(rowIndex, "Download Debit Note");
	}
	
	private void changeStatus2(int rowIndex, String statusText) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

	    // Click More
	    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	    moreBtn.click();

	    // Click Verify & Approve
	    WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Verify & Approve')]")));
	    changeStatus.click();

	    // 🔥 QC REJECTION LOGIC
	    performRandomQcRejection();

	    //================= SUBMIT QC =================
	    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
	    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
	}
	
	public void performRandomQcRejection() throws InterruptedException {

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    Random random = new Random();

	    List<WebElement> accordions = driver.findElements(
	            By.xpath("//div[contains(@class,'lpoqc-accordion shadow-sm')]")
	    );

	    int productsToReject = random.nextInt(2) + 1; // reject 1 or 2 products
	    int rejectedCount = 0;

	    for (int i = 0; i < accordions.size(); i++) {

	        if (rejectedCount >= productsToReject) {
	            break;
	        }

	        // 🔁 Always refetch
	        WebElement accordion = driver.findElements(
	                By.xpath("//div[contains(@class,'lpoqc-accordion shadow-sm')]")
	        ).get(i);

	        js.executeScript("arguments[0].scrollIntoView(true);", accordion);
	        accordion.click();
	        Thread.sleep(1000);

	        // ================= READ RECEIVED QTY =================
	        WebElement recQtyInput = accordion.findElement(
	                By.xpath(".//input[contains(@class,'ful')]")
	        );

	        String recQtyVal = recQtyInput.getAttribute("value");
	        if (recQtyVal == null || recQtyVal.trim().isEmpty()) {
	            continue;
	        }

	        double receivedQty = Double.parseDouble(recQtyVal);

	        if (receivedQty <= 0) {
	            continue; // skip zero qty products
	        }

	        // ================= REJECT HALF QTY =================
	        double rejectQty = Math.max(1, receivedQty / 2);

	        WebElement rejectQtyInput = accordion.findElement(
	                By.xpath(".//input[contains(@class,'rejected_qty')]")
	        );

	        js.executeScript("arguments[0].scrollIntoView(true);", rejectQtyInput);
	        rejectQtyInput.click();
	        Thread.sleep(300);

	        rejectQtyInput.clear();
	        rejectQtyInput.sendKeys(String.valueOf((int) rejectQty));

	        // 🔥 COMMIT REJECT QTY (THIS ENABLES DROPDOWNS)
	        js.executeScript(
	                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
	                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
	                "arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));",
	                rejectQtyInput
	        );

	        // ================= WAIT FOR REASON DROPDOWN =================
	        
	     // Open dropdown
	        WebElement reasonDropdown = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        accordion.findElement(By.xpath(".//div[contains(@class,'dropdown-heading')]"))
	                )
	        );
	        reasonDropdown.click();

	        Thread.sleep(500);

	        // Get all checkbox options
	        List<WebElement> reasons = wait.until(
	                ExpectedConditions.presenceOfAllElementsLocatedBy(
	                        By.xpath("//div[contains(@class,'dropdown-content')]//input[@type='checkbox']")
	                )
	        );

	        // Select one randomly
//	        Random random = new Random();
	        WebElement randomReason = reasons.get(random.nextInt(reasons.size()));

	        if (!randomReason.isSelected()) {
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomReason);
	        }

	        System.out.println("Random rejection reason selected");
	        

	        // ================= COMMENT =================
	        WebElement commentInput = accordion.findElement(
	                By.xpath(".//input[@name='sName']")
	        );
	        commentInput.sendKeys("Auto QC rejection");

	        rejectedCount++;
	    }

	}
	
	private void completeLPO(int rowIndex, String statusText) throws InterruptedException  {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
		changeStatus.click();

		// Select new status
	
		Select select = new Select(
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
		select.selectByVisibleText(statusText);

		driver.findElement(By.xpath("//textarea[@name='sName']")).sendKeys("Status updated automatically");

		driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		Thread.sleep(3000);
		 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
}
	private void downloadDN(int rowIndex, String statusText) throws InterruptedException  {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Download Debit Note')]")));
		changeStatus.click();
	}
}