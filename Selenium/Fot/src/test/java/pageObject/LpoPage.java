package pageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LpoPage {
	
	public WebDriver driver;
	

	public LpoPage(WebDriver driver) {
		this.driver = driver;
	}

	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickprocur = By.xpath("//ul//li//span[text()='Procurement']");
	By clickLpogrid = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/ul[5]/ul/li[3]/span/span");
	By clickfilterbutton = By
			.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[1]/img");
	By selectwearhouse = By.name("del_hub_id");
	By Applyfilter = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[2]/div/div[7]/div/div[2]/button");//button[text()='Apply Filters']
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

	public void clickApplyBtn() throws InterruptedException {
	    WebElement applyBtn = driver.findElement(Applyfilter);

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView(true);", applyBtn);
	    Thread.sleep(2000); 
	    applyBtn.click();
	}

	public void selectproducts() throws InterruptedException {

//		driver.findElement(
//				By.xpath("(//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium filter-icon  css-vubbuv'])[5]"))
//				.click();
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
////		WebElement searchBox = wait.until(ExpectedConditions
////				.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[3]")));
//		WebElement searchBox = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
//		Thread.sleep(1000);
//		String orderId = PlaceOrder.orderId;
//		System.out.println("Order ID = " + orderId);
//		searchBox.sendKeys(orderId);
//		Thread.sleep(500);
//		searchBox.sendKeys(Keys.ENTER);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // click filter icon
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium filter-icon  css-vubbuv'])[5]")
	    )).click();

	    // stable locator using placeholder
	    By orderIdInput = By.xpath("//input[@placeholder='Order ID']");

	    WebElement searchBox = wait.until(
	        ExpectedConditions.elementToBeClickable(orderIdInput)
	    );

	    searchBox.click();
	    searchBox.clear();

	    String orderId = PlaceOrder.orderId;
	    System.out.println("Order ID = " + orderId);
Thread.sleep(2000);
	    searchBox.sendKeys(orderId);
	    searchBox.sendKeys(Keys.ENTER);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".react-confirm-alert-overlay")));

		List<WebElement> checkboxes = driver.findElements(selectproducts);
		System.out.println(checkboxes.size());

		for (int i = 0; i < 3 && i < checkboxes.size(); i++) {
			WebElement checkbox = checkboxes.get(i);
			if (!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}

	public void clicknextbutton() {
//		driver.findElement(NextButton).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		wait.until(ExpectedConditions.elementToBeClickable(NextButton)).click();
	}

	public void clicksubmittbutton() {		
//		driver.findElement(SubmitButton).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		wait.until(ExpectedConditions.elementToBeClickable(SubmitButton)).click();
	}

	public void ClickConfirmButton() {
//		driver.findElement(ConfirmButton).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		wait.until(ExpectedConditions.elementToBeClickable(ConfirmButton)).click();
	}

	public void clickNoButton() {
//		driver.findElement(NoButton).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		wait.until(ExpectedConditions.elementToBeClickable(NoButton)).click();
	}

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
		veryfyAndApprove(rowIndex);
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

	public void veryfyAndApprove(int rowIndex) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		moreBtn.click();

		WebElement approve = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Verify')]")));
		approve.click();

		driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
	}


}
