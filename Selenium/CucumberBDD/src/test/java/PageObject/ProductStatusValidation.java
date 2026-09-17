package PageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProductStatusValidation {

	String seller;
	String uom;
	String skuId;

	public WebDriver driver;
	public WebDriverWait wait;

	public ProductStatusValidation(WebDriver driver) {
		this.driver = driver;
	}

	public void ViewProductPage() throws InterruptedException {

		Thread.sleep(1000);

		driver.findElement(By.xpath("//div[@class='menu-cont']//img[@alt='img']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Product']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='View Products']")).click();

		Thread.sleep(3000);

	}

	public void ProductStatusCheckBeforeActivetoInactive() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement statusElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]//td[10]")));

		String actualStatus = statusElement.getText().trim();

		System.out.println("Product Status: " + actualStatus);

		if (actualStatus.equalsIgnoreCase("Active")) {
			System.out.println("Status verified successfully.");
		} else {
			throw new AssertionError("Expected Status: Active, but found: " + actualStatus);
		}
	}

	public void ProductStatusActivetoInactive() throws InterruptedException {

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[1]"))
				.click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Inactive");

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

	}

	public void ProductStatusCheckAfterActivetoInactive() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement statusElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]//td[10]")));

		String actualStatus = statusElement.getText().trim();

		System.out.println("Product Status from Table: [" + actualStatus + "]");

		if (actualStatus.equalsIgnoreCase("Inactive")) {
			System.out.println("Status verified successfully.");
		} else {
			throw new AssertionError("Expected Status: Inactive, but found: " + actualStatus);
		}

		Thread.sleep(2000);
		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement status = wait1
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='status']")));

		String productStatus = status.getText().trim();

		System.out.println("Product Status in Details: [" + productStatus + "]");

		if (productStatus.equalsIgnoreCase("Inactive")) {

			WebElement activeTab = wait1.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(@class,'common-tab') and contains(@class,'selected')]")));

			String activeText = activeTab.getText().trim();

//		    System.out.println("Active Tab Text: [" + activeText + "]");

			String activeCount = activeText.replaceAll("[^0-9]", "");

			System.out.println("Active Status: [" + activeCount + "]");

			Assert.assertEquals(activeCount, "0", "Expected Active status to be 0, but found: [" + activeCount + "]");

			System.out.println("Product is Inactive, so Active status is [0]");
		}

	}

	public void ProductStatusCheckBeforeInactivetoActive() throws InterruptedException {

		driver.navigate().refresh();
		Thread.sleep(1000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement statusElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]//td[10]")));

		String actualStatus = statusElement.getText().trim();

		System.out.println("Product Status: " + actualStatus);

		if (actualStatus.equalsIgnoreCase("Inactive")) {
			System.out.println("Status verified successfully.");
		} else {
			throw new AssertionError("Expected Status: Inactive, but found: " + actualStatus);
		}
	}

	public void ProductStatusInactivetoActive() throws InterruptedException {

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[1]"))
				.click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Active");

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

	}

	public void ProductStatusCheckAfterInactivetoActive() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Verify status from table
		WebElement statusElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]//td[10]")));

		String actualStatus = statusElement.getText().trim();

		System.out.println("Product Status from Table: [" + actualStatus + "]");

		if (actualStatus.equalsIgnoreCase("Active")) {
			System.out.println("Status verified successfully.");
		} else {
			throw new AssertionError("Expected Status: Active, but found: " + actualStatus);
		}

		WebElement firstRow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table//tbody//tr[1]")));

		firstRow.click();

		WebElement status = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='status'])[1]")));

		wait.until(driver -> !status.getText().trim().isEmpty());

		String productStatus = status.getText().trim();

		System.out.println("Product Status in Details: [" + productStatus + "]");

		// Active tab
		WebElement activeTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(@class,'common-tab') and contains(@class,'selected')]")));

		wait.until(driver -> !activeTab.getText().trim().isEmpty());

		String activeText = activeTab.getText().trim();

//    System.out.println("Active Tab Text: [" + activeText + "]");

		String activeCount = activeText.replaceAll("[^0-9]", "");

		System.out.println("Active Status: [" + activeCount + "]");

		// Inactive tab
		WebElement inactiveTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//div[contains(@class,'common-tab') and contains(@class,'false')])[1]")));

		wait.until(driver -> !inactiveTab.getText().trim().isEmpty());

		String inactiveText = inactiveTab.getText().trim();

//    System.out.println("Inactive Tab Text: [" + inactiveText + "]");

		String inactiveCount = inactiveText.replaceAll("[^0-9]", "");

		System.out.println("Inactive Status: [" + inactiveCount + "]");

		System.out.println("Product is Active");
	}

//==================================================================================================================
//VARIANT STATUS CHANGE FROM ACTIVE TO INACTIVE
//==================================================================================================================

	public void VariantStatusActivetoInactive() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeBefore = text;
			} else if (text.startsWith("Inactive")) {
				inactiveBefore = text;
			} else if (text.startsWith("Deleted")) {
				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");
		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

		// =====================================================
		// CAPTURE SKU ID
		// =====================================================

		WebElement skuElement = driver.findElement(By.xpath("(//div[@class='font font-normal bold'])[1]"));

		String SkuId = skuElement.getText().trim().replace("#", "").replace("\"", "").trim();

		System.out.println("SKU ID : " + SkuId);

		Thread.sleep(1000);

		// =====================================================
		// CLICK VARIANT DROPDOWN
		// =====================================================

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[2]"))
				.click();

		Thread.sleep(1000);

		// =====================================================
		// CLICK CHANGE STATUS
		// =====================================================

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(1000);

		// =====================================================
		// CAPTURE SELLER & UOM
		// =====================================================

		WebElement sellerField = driver.findElement(By.name("sName"));

		WebElement uomField = driver.findElement(By.name("uomDesc"));

		seller = sellerField.getAttribute("value");
		uom = uomField.getAttribute("value");

		System.out.println("\n========== SELLER & UOM ==========");
		System.out.println("Seller : " + seller);
		System.out.println("UoM    : " + uom);

		Thread.sleep(1000);

		// =====================================================
		// CHANGE STATUS TO INACTIVE
		// =====================================================

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Inactive");

		Thread.sleep(1000);

		// =====================================================
		// SUBMIT
		// =====================================================

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(1000);

		// =====================================================
		// CONFIRM
		// =====================================================

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(1000);

		// =====================================================
		// OK
		// =====================================================

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE
		// CLICK PRODUCT AGAIN
		// =====================================================

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeAfter = text;
			} else if (text.startsWith("Inactive")) {
				inactiveAfter = text;
			} else if (text.startsWith("Deleted")) {
				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");
		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

		// =====================================================
		// CLICK INACTIVE TAB
		// =====================================================

		WebElement inactiveButton = driver.findElement(By.xpath("(//div[@class='common-tab false'])[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", inactiveButton);

		inactiveButton.click();

		Thread.sleep(1000);

		// =====================================================
		// FIND THE SAME SKU IN INACTIVE LIST
		// =====================================================

		WebElement inactiveSku = driver
				.findElement(By.xpath("//div[@class='font font-normal bold' and contains(.,'" + SkuId + "')]"));

		Thread.sleep(1000);

		// =====================================================
		// VERIFY SKU IS INACTIVATED SUCCESSFULLY
		// =====================================================

		if (inactiveSku.isDisplayed()) {

			System.out.println("\n======================================");

			System.out.println("Inactive SKU : #" + SkuId);

			System.out.println("SKU ID #" + SkuId + " has been inactivated successfully.");

			System.out.println("======================================");

		} else {

			System.out.println("\n======================================");

			System.out.println("SKU ID #" + SkuId + " was not found in the Inactive list.");

			System.out.println("======================================");
		}
	}

// ==================================================================================================================
// VARIANT STATUS CHANGE FROM INACTIVE TO ACTIVE
// ==================================================================================================================

	public void VariantStatusInactivetoActive() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeBefore = text;
			} else if (text.startsWith("Inactive")) {
				inactiveBefore = text;
			} else if (text.startsWith("Deleted")) {
				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");
		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

		// =====================================================
		// CLICK INACTIVE TAB
		// =====================================================

		driver.findElement(By.xpath("(//div[@class='common-tab false'])[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// CAPTURE SKU ID
		// =====================================================

		WebElement skuElement = driver.findElement(By.xpath("(//div[@class='font font-normal bold'])[1]"));

		String SkuId = skuElement.getText().trim().replace("#", "");

		System.out.println("SKU ID : " + SkuId);

		Thread.sleep(1500);

		// =====================================================
		// CLICK VARIANT DROPDOWN
		// =====================================================

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[2]"))
				.click();

		Thread.sleep(1000);

		// =====================================================
		// CLICK CHANGE STATUS
		// =====================================================

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		// =====================================================
		// CAPTURE SELLER & UOM
		// =====================================================

		WebElement sellerField = driver.findElement(By.name("sName"));

		WebElement uomField = driver.findElement(By.name("uomDesc"));

		seller = sellerField.getAttribute("value");
		uom = uomField.getAttribute("value");

		System.out.println("\n========== SELLER & UOM ==========");
		System.out.println("Seller : " + seller);
		System.out.println("UoM    : " + uom);

		Thread.sleep(1000);

		// =====================================================
		// CHANGE STATUS TO ACTIVE
		// =====================================================

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Active");

		Thread.sleep(1000);

		// =====================================================
		// SUBMIT
		// =====================================================

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(1000);

		// =====================================================
		// OK
		// =====================================================

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE
		// CLICK PRODUCT AGAIN
		// =====================================================

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeAfter = text;
			} else if (text.startsWith("Inactive")) {
				inactiveAfter = text;
			} else if (text.startsWith("Deleted")) {
				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");
		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

		// =====================================================
		// CLICK ACTIVE TAB
		// =====================================================

		WebElement ActiveButton = driver.findElement(By.xpath("//div[@class='common-tab selected']"));

		ActiveButton.click();

		Thread.sleep(2000);

		// =====================================================
		// FIND THE SAME SKU IN ACTIVE LIST
		// =====================================================

		WebElement ActiveSku = driver
				.findElement(By.xpath("//div[@class='font font-normal bold' and contains(.,'" + SkuId + "')]"));

		// =====================================================
		// VERIFY SKU IS ACTIVATED SUCCESSFULLY
		// =====================================================

		if (ActiveSku.isDisplayed()) {

			System.out.println("\n======================================");

			System.out.println("Active SKU : #" + SkuId);

			System.out.println("SKU ID #" + SkuId + " has been Activated successfully.");

			System.out.println("======================================");

		} else {

			System.out.println("\n======================================");

			System.out.println("SKU ID #" + SkuId + " was not found in the Active list.");

			System.out.println("======================================");
		}
	}

//==================================================================================================================
//VARIANT STATUS CHANGE FROM ACTIVE TO DELETE
//==================================================================================================================

	public void VariantStatusActivetoDelete() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeBefore = text;
			} else if (text.startsWith("Inactive")) {
				inactiveBefore = text;
			} else if (text.startsWith("Deleted")) {
				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");
		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

//
// // =====================================================
// // CLICK INACTIVE TAB
// // =====================================================
//
// driver.findElement(
//         By.xpath("(//div[@class='common-tab false'])[1]")
// ).click();
//
// Thread.sleep(1000);

		// =====================================================
		// CAPTURE SKU ID
		// =====================================================

		WebElement skuElement = driver.findElement(By.xpath("(//div[@class='font font-normal bold'])[1]"));

		String SkuId = skuElement.getText().trim().replace("#", "");

		System.out.println("SKU ID : " + SkuId);

		Thread.sleep(1500);

		// =====================================================
		// CLICK VARIANT DROPDOWN
		// =====================================================

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[2]"))
				.click();

		Thread.sleep(1000);

		// =====================================================
		// CLICK CHANGE STATUS
		// =====================================================

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		// =====================================================
		// CAPTURE SELLER & UOM
		// =====================================================

		WebElement sellerField = driver.findElement(By.name("sName"));

		WebElement uomField = driver.findElement(By.name("uomDesc"));

		seller = sellerField.getAttribute("value");
		uom = uomField.getAttribute("value");

		System.out.println("\n========== SELLER & UOM ==========");
		System.out.println("Seller : " + seller);
		System.out.println("UoM    : " + uom);

		Thread.sleep(1000);

		// =====================================================
		// CHANGE STATUS TO ACTIVE
		// =====================================================

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Delete");

		Thread.sleep(1000);

		// =====================================================
		// SUBMIT
		// =====================================================

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE
		// CLICK PRODUCT AGAIN
		// =====================================================

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// =====================================================
		// AFTER STATUS CHANGE - DISPLAY TAB COUNTS
		// =====================================================

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeAfter = text;
			} else if (text.startsWith("Inactive")) {
				inactiveAfter = text;
			} else if (text.startsWith("Deleted")) {
				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");
		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

		// =====================================================
		// CLICK ACTIVE TAB
		// =====================================================

		WebElement DeleteButton = driver.findElement(By.xpath("(//div[@class='common-tab false'])[2]"));

		DeleteButton.click();

		Thread.sleep(2000);

		// =====================================================
		// FIND THE SAME SKU IN ACTIVE LIST
		// =====================================================

		WebElement DeleteSku = driver
				.findElement(By.xpath("//div[@class='font font-normal bold' and contains(.,'" + SkuId + "')]"));

		// =====================================================
		// VERIFY SKU IS ACTIVATED SUCCESSFULLY
		// =====================================================

		if (DeleteSku.isDisplayed()) {

			System.out.println("\n======================================");

			System.out.println("Deleted SKU : #" + SkuId);

			System.out.println("SKU ID #" + SkuId + " has been Deleted successfully.");

			System.out.println("======================================");

		} else {

			System.out.println("\n======================================");

			System.out.println("SKU ID #" + SkuId + " was not found in the Delete list.");

			System.out.println("======================================");
		}
	}

//==================================================================================================================
//VARIANT STATUS CHANGE FROM INACTIVE TO DELETE
//==================================================================================================================

	public void VariantStatusInactivetoDelete() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

// =====================================================
// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS
// =====================================================

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeBefore = text;
			} else if (text.startsWith("Inactive")) {
				inactiveBefore = text;
			} else if (text.startsWith("Deleted")) {
				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");
		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

// =====================================================
// CLICK INACTIVE TAB
// =====================================================

		driver.findElement(By.xpath("(//div[@class='common-tab false'])[1]")).click();

		Thread.sleep(1000);

// =====================================================
// CAPTURE SKU ID
// =====================================================

		WebElement skuElement = driver.findElement(By.xpath("(//div[@class='font font-normal bold'])[1]"));

		String SkuId = skuElement.getText().trim().replace("#", "");

		System.out.println("SKU ID : " + SkuId);

		Thread.sleep(1500);

// =====================================================
// CLICK VARIANT DROPDOWN
// =====================================================

		driver.findElement(By.xpath(
				"(//div[contains(@class,'product-variant-dropdown')]//button[contains(@class,'product-variant-toggle')])[2]"))
				.click();

		Thread.sleep(1000);

// =====================================================
// CLICK CHANGE STATUS
// =====================================================

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

// =====================================================
// CAPTURE SELLER & UOM
// =====================================================

		WebElement sellerField = driver.findElement(By.name("sName"));

		WebElement uomField = driver.findElement(By.name("uomDesc"));

		seller = sellerField.getAttribute("value");
		uom = uomField.getAttribute("value");

		System.out.println("\n========== SELLER & UOM ==========");
		System.out.println("Seller : " + seller);
		System.out.println("UoM    : " + uom);

		Thread.sleep(1000);

// =====================================================
// CHANGE STATUS TO DELETE
// =====================================================

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Delete");

		Thread.sleep(1000);

// =====================================================
// SUBMIT
// =====================================================

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(1000);

// =====================================================
// OK
// =====================================================

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);

// =====================================================
// AFTER STATUS CHANGE
// CLICK PRODUCT AGAIN
// =====================================================

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

// =====================================================
// AFTER STATUS CHANGE - DISPLAY TAB COUNTS
// =====================================================

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {
				activeAfter = text;
			} else if (text.startsWith("Inactive")) {
				inactiveAfter = text;
			} else if (text.startsWith("Deleted")) {
				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");
		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

// =====================================================
// CLICK ACTIVE TAB
// =====================================================

		WebElement DeleteButton = driver.findElement(By.xpath("(//div[@class='common-tab false'])[2]"));

		DeleteButton.click();

		Thread.sleep(2000);

// =====================================================
// FIND THE SAME SKU IN ACTIVE LIST
// =====================================================

		WebElement DeleteSku = driver
				.findElement(By.xpath("//div[@class='font font-normal bold' and contains(.,'" + SkuId + "')]"));

// =====================================================
// VERIFY SKU IS ACTIVATED SUCCESSFULLY
// =====================================================

		if (DeleteSku.isDisplayed()) {

			System.out.println("\n======================================");

			System.out.println("Deleted SKU : #" + SkuId);

			System.out.println("SKU ID #" + SkuId + " has been Deleted successfully.");

			System.out.println("======================================");

		} else {

			System.out.println("\n======================================");

			System.out.println("SKU ID #" + SkuId + " was not found in the Delete list.");

			System.out.println("======================================");
		}
	}

//=====================================================================================================================
//  BULK STATUS CHANGE FROM ACTIVE TO INACTIVE
//=====================================================================================================================

	public void bulkStatusChangeOfVariantActivetoInactive() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {

				activeBefore = text;

			} else if (text.startsWith("Inactive")) {

				inactiveBefore = text;

			} else if (text.startsWith("Deleted")) {

				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");

		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

		// CLICK BULK STATUS CHANGE

		WebElement bulkStatusButton = driver.findElement(By.xpath("//button[text()='Bulk Status Change']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", bulkStatusButton);

		Thread.sleep(500);

		bulkStatusButton.click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(4000);

		// FIND CHECKBOXES

		List<WebElement> checkboxes = driver.findElements(By.xpath(
				"//input[@type='checkbox' " + "and contains(@class,'form-check-input') " + "and not(@disabled)]"));

		Random random = new Random();

		// VARIABLES TO STORE SELECTED SKU DETAILS

		String skuId1 = "";
		String skuId2 = "";

		if (checkboxes.size() == 0) {

			System.out.println("No checkboxes available.");
		}

		// =====================================================
		// ONLY ONE CHECKBOX
		// =====================================================

		else if (checkboxes.size() == 1) {

			WebElement checkbox = checkboxes.get(0);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

			Thread.sleep(500);

			WebElement skuElement = checkbox.findElement(By.xpath("./following::div[contains(@class,'font') "
					+ "and contains(@class,'bold') " + "and contains(normalize-space(.),'#')][1]"));

			skuId1 = skuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			System.out.println("\nSKU ID 1 : " + skuId1);
		}

		else {

			// RANDOM FIRST CHECKBOX

			int firstIndex = random.nextInt(checkboxes.size());

			// RANDOM SECOND CHECKBOX

			int secondIndex;

			do {

				secondIndex = random.nextInt(checkboxes.size());

			} while (secondIndex == firstIndex);

			// FIRST CHECKBOX

			WebElement firstCheckbox = checkboxes.get(firstIndex);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
					firstCheckbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstCheckbox);

			Thread.sleep(500);

			// GET SKU BELONGING TO FIRST CHECKBOX

			WebElement firstSkuElement = firstCheckbox.findElement(By.xpath("./following::div[contains(@class,'font') "
					+ "and contains(@class,'bold') " + "and contains(normalize-space(.),'#')][1]"));

			skuId1 = firstSkuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			// SECOND CHECKBOX

			WebElement secondCheckbox = checkboxes.get(secondIndex);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
					secondCheckbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondCheckbox);

			Thread.sleep(500);

			// GET SKU BELONGING TO SECOND CHECKBOX

			WebElement secondSkuElement = secondCheckbox
					.findElement(By.xpath("./following::div[contains(@class,'font') " + "and contains(@class,'bold') "
							+ "and contains(normalize-space(.),'#')][1]"));

			skuId2 = secondSkuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			// =================================================
			// PRINT SELECTED SKUS
			// =================================================

			System.out.println("\n========== SELECTED SKUS ==========");

			System.out.println("SKU ID 1 : " + skuId1);
			System.out.println("SKU ID 2 : " + skuId2);

			Thread.sleep(1000);
		}

		// CLICK BULK STATUS CHANGE

		WebElement bulkStatusButton2 = driver.findElement(By.xpath("//button[text()='Bulk Status Change']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
				bulkStatusButton2);

		Thread.sleep(500);

		bulkStatusButton2.click();

		Thread.sleep(1000);

		// CHANGE STATUS TO INACTIVE

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Inactive");

		Thread.sleep(1000);

		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submitButton);

		Thread.sleep(500);

		submitButton.click();

		Thread.sleep(1000);

		WebElement confirmButton = driver.findElement(By.xpath("//button[text()='Confirm']"));

		confirmButton.click();

		Thread.sleep(1000);

		WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));

		okButton.click();

		Thread.sleep(1000);

		// AFTER STATUS CHANGE
		// CLICK PRODUCT AGAIN

		WebElement firstProductRow = driver.findElement(By.xpath("//table//tbody//tr[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", firstProductRow);

		Thread.sleep(500);

		firstProductRow.click();

		Thread.sleep(1000);

		// AFTER STATUS CHANGE - DISPLAY TAB COUNTS

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]" + "//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {

				activeAfter = text;

			} else if (text.startsWith("Inactive")) {

				inactiveAfter = text;

			} else if (text.startsWith("Deleted")) {

				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");

		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

		// CLICK INACTIVE TAB

		WebElement inactiveButton = driver.findElement(By.xpath("(//div[@class='common-tab false'])[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", inactiveButton);

		Thread.sleep(500);

		inactiveButton.click();

		Thread.sleep(1000);

		// =====================================================
		// VERIFY SKU 1
		// =====================================================

		System.out.println("\n========== AFTER INACTIVE SKUS ==========");

		if (!skuId1.isEmpty()) {

			List<WebElement> inactiveSku1 = driver.findElements(
					By.xpath("//div[@class='font font-normal bold' " + "and contains(.,'" + skuId1 + "')]"));

			if (!inactiveSku1.isEmpty()) {

				System.out.println(
						"Inactive SKU : #" + skuId1 + "\nSKU ID #" + skuId1 + " has been inactivated successfully.\n");

			} else {

				System.out.println("SKU ID #" + skuId1 + " was not found in the Inactive list.\n");
			}
		}

		// =====================================================
		// VERIFY SKU 2
		// =====================================================

		if (!skuId2.isEmpty()) {

			List<WebElement> inactiveSku2 = driver.findElements(
					By.xpath("//div[@class='font font-normal bold' " + "and contains(.,'" + skuId2 + "')]"));

			if (!inactiveSku2.isEmpty()) {

				System.out.println(
						"Inactive SKU : #" + skuId2 + "\nSKU ID #" + skuId2 + " has been inactivated successfully.\n");

			} else {

				System.out.println("SKU ID #" + skuId2 + " was not found in the Inactive list.\n");
			}
		}
	}

//============================================================================================================================
//  BULK STATUS CHANGE FROM INACTIVE TO ACTIVE
//============================================================================================================================

	public void bulkStatusChangeOfVariantInactivetoActive() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//table//tbody//tr[1]")).click();

		Thread.sleep(1000);

		// BEFORE STATUS CHANGE - DISPLAY TAB COUNTS

		List<WebElement> tabsBefore = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]//div[contains(@class,'common-tab')]"));

		String activeBefore = "";
		String inactiveBefore = "";
		String deletedBefore = "";

		for (WebElement tab : tabsBefore) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {

				activeBefore = text;

			} else if (text.startsWith("Inactive")) {

				inactiveBefore = text;

			} else if (text.startsWith("Deleted")) {

				deletedBefore = text;
			}
		}

		System.out.println("\n========== BEFORE STATUS CHANGE ==========");

		System.out.println(activeBefore + "    " + inactiveBefore + "    " + deletedBefore);

		Thread.sleep(1000);

		// CLICK BULK STATUS CHANGE

		driver.findElement(By.xpath("(//div[@class='common-tab false'])[1]")).click();

		Thread.sleep(1000);

		WebElement bulkStatusButton = driver.findElement(By.xpath("//button[text()='Bulk Status Change']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", bulkStatusButton);

		Thread.sleep(500);

		bulkStatusButton.click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(4000);

		// FIND CHECKBOXES

		List<WebElement> checkboxes = driver.findElements(By.xpath(
				"//input[@type='checkbox' " + "and contains(@class,'form-check-input') " + "and not(@disabled)]"));

		Random random = new Random();

		// VARIABLES TO STORE SELECTED SKU DETAILS

		String skuId1 = "";
		String skuId2 = "";

		if (checkboxes.size() == 0) {

			System.out.println("No checkboxes available.");
		}

		// =====================================================
		// ONLY ONE CHECKBOX
		// =====================================================

		else if (checkboxes.size() == 1) {

			WebElement checkbox = checkboxes.get(0);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

			Thread.sleep(500);

			WebElement skuElement = checkbox.findElement(By.xpath("./following::div[contains(@class,'font') "
					+ "and contains(@class,'bold') " + "and contains(normalize-space(.),'#')][1]"));

			skuId1 = skuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			System.out.println("\nSKU ID 1 : " + skuId1);
		}

		else {

			// RANDOM FIRST CHECKBOX

			int firstIndex = random.nextInt(checkboxes.size());

			// RANDOM SECOND CHECKBOX

			int secondIndex;

			do {

				secondIndex = random.nextInt(checkboxes.size());

			} while (secondIndex == firstIndex);

			// FIRST CHECKBOX

			WebElement firstCheckbox = checkboxes.get(firstIndex);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
					firstCheckbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstCheckbox);

			Thread.sleep(500);

			// GET SKU BELONGING TO FIRST CHECKBOX

			WebElement firstSkuElement = firstCheckbox.findElement(By.xpath("./following::div[contains(@class,'font') "
					+ "and contains(@class,'bold') " + "and contains(normalize-space(.),'#')][1]"));

			skuId1 = firstSkuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			// SECOND CHECKBOX

			WebElement secondCheckbox = checkboxes.get(secondIndex);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
					secondCheckbox);

			Thread.sleep(500);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondCheckbox);

			Thread.sleep(500);

			// GET SKU BELONGING TO SECOND CHECKBOX

			WebElement secondSkuElement = secondCheckbox
					.findElement(By.xpath("./following::div[contains(@class,'font') " + "and contains(@class,'bold') "
							+ "and contains(normalize-space(.),'#')][1]"));

			skuId2 = secondSkuElement.getText().trim().replace("#", "").replace("\"", "").trim();

			// =================================================
			// PRINT SELECTED SKUS
			// =================================================

			System.out.println("\n========== SELECTED SKUS ==========");

			System.out.println("SKU ID 1 : " + skuId1);
			System.out.println("SKU ID 2 : " + skuId2);

			Thread.sleep(1000);
		}

		// CLICK BULK STATUS CHANGE

		WebElement bulkStatusButton2 = driver.findElement(By.xpath("//button[text()='Bulk Status Change']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
				bulkStatusButton2);

		Thread.sleep(500);

		bulkStatusButton2.click();

		Thread.sleep(1000);

		// CHANGE STATUS TO ACTIVE

		WebElement statusElement = driver.findElement(By.name("selectedStatus"));

		Select statusDrop = new Select(statusElement);

		statusDrop.selectByVisibleText("Active");

		Thread.sleep(1000);

		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submitButton);

		Thread.sleep(500);

		submitButton.click();

		Thread.sleep(1000);

		WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));

		okButton.click();

		Thread.sleep(1000);

		// AFTER STATUS CHANGE
		// CLICK PRODUCT AGAIN

		WebElement firstProductRow = driver.findElement(By.xpath("//table//tbody//tr[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", firstProductRow);

		Thread.sleep(500);

		firstProductRow.click();

		Thread.sleep(1000);

		// AFTER STATUS CHANGE - DISPLAY TAB COUNTS

		List<WebElement> tabsAfter = driver.findElements(
				By.xpath("//div[contains(@class,'common-tab-cont')]" + "//div[contains(@class,'common-tab')]"));

		String activeAfter = "";
		String inactiveAfter = "";
		String deletedAfter = "";

		for (WebElement tab : tabsAfter) {

			String text = tab.getText().trim();

			if (text.startsWith("Active")) {

				activeAfter = text;

			} else if (text.startsWith("Inactive")) {

				inactiveAfter = text;

			} else if (text.startsWith("Deleted")) {

				deletedAfter = text;
			}
		}

		System.out.println("\n========== AFTER STATUS CHANGE ==========");

		System.out.println(activeAfter + "    " + inactiveAfter + "    " + deletedAfter);

		Thread.sleep(1000);

		// CLICK ACTIVE TAB

		WebElement activeButton = driver.findElement(By.xpath("(//div[@class='common-tab selected'])[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", activeButton);

		Thread.sleep(500);

		activeButton.click();

		Thread.sleep(1000);

		// =====================================================
		// VERIFY SKU 1
		// =====================================================

		System.out.println("\n========== AFTER ACTIVE SKUS ==========");

		if (!skuId1.isEmpty()) {

			List<WebElement> activeSku1 = driver.findElements(
					By.xpath("//div[@class='font font-normal bold' " + "and contains(.,'" + skuId1 + "')]"));

			if (!activeSku1.isEmpty()) {

				System.out.println(
						"Inactive SKU : #" + skuId1 + "\nSKU ID #" + skuId1 + " has been activated successfully.\n");

			} else {

				System.out.println("SKU ID #" + skuId1 + " was not found in the Active list.\n");
			}
		}

		// =====================================================
		// VERIFY SKU 2
		// =====================================================

		if (!skuId2.isEmpty()) {

			List<WebElement> activeSku2 = driver.findElements(
					By.xpath("//div[@class='font font-normal bold' " + "and contains(.,'" + skuId2 + "')]"));

			if (!activeSku2.isEmpty()) {

				System.out.println(
						"Active SKU : #" + skuId2 + "\nSKU ID #" + skuId2 + " has been activated successfully.\n");

			} else {

				System.out.println("SKU ID #" + skuId2 + " was not found in the Active list.\n");
			}
		}
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	                                // PRODUCT VARIANT //
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void ViewproductVariants() throws InterruptedException {

		Thread.sleep(1000);

		driver.findElement(By.xpath("//div[@class='menu-cont']//img[@alt='img']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Product']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Product Variants']")).click();

		Thread.sleep(3000);

	}
	
	public void selectSeller() throws InterruptedException {
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")).click();

		
		driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
		
		Thread.sleep(2000);
		
		driver.findElement(
		        By.xpath("//input[@placeholder='Trade Name']"))
		        .sendKeys("Najmat Al Manal vegitables & Fruits Trading LLC");

		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[text()='Apply']")).click();

		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr)[3]")).click();
		
		Thread.sleep(2000);
	}
	
	

////////////////////////////////////// ACTIVE TO INACTIVE  ///////////////////////////////////////////////////////////
	
	
	public void changeActiveProductToInactive() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Random random = new Random();

	    WebElement statusFilterIcon = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    statusFilterIcon.click();

	    Thread.sleep(500);

	    WebElement statusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[@class='grid-select-cont']//select")
	            )
	    );

	    Select statusDrop = new Select(statusElement);

	    statusDrop.selectByVisibleText("Active");

	    Thread.sleep(500);

	    System.out.println("Active selected.");

	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton.click();

	    Thread.sleep(1000);

	    System.out.println("Active status filter applied.");

	    List<WebElement> rows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    if (rows.isEmpty()) {

	        System.out.println("No Active products found.");
	        return;
	    }

	    System.out.println("Total Active rows found: " + rows.size());

	    WebElement selectedRow = rows.get(
	            random.nextInt(rows.size())
	    );

	    String productId = selectedRow.findElement(
	            By.xpath("./td[2]")
	    ).getText().trim();

	    productId = productId.replace("#", "").trim();

	    String skuId = selectedRow.findElement(
	            By.xpath("./td[3]")
	    ).getText().trim();

	    skuId = skuId.replace("#", "").trim();


	    String oldStatus = selectedRow.findElement(
	            By.xpath("./td[12]")
	    ).getText().trim();

	    System.out.println("======================================");
	    System.out.println("SELECTED PRODUCT");
	    System.out.println("======================================");
	    System.out.println("Product ID : " + productId);
	    System.out.println("SKU ID     : " + skuId);
	    System.out.println("Old Status : " + oldStatus);


	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            selectedRow
	    );

	    Thread.sleep(500);

	    WebElement moreDropdown = selectedRow.findElement(
	            By.xpath(
	                    ".//div[contains(@class,'grid-actions-dropdown') " +
	                    "and contains(@class,'dropdown')]"
	            )
	    );

	    wait.until(
	            ExpectedConditions.elementToBeClickable(moreDropdown)
	    );

	    moreDropdown.click();

	    Thread.sleep(500);



	    WebElement changeStatus = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//a[contains(@class,'dropdown-item') " +
	                            "and normalize-space()='Change Status']"
	                    )
	            )
	    );

	    changeStatus.click();

	    Thread.sleep(500);


	    WebElement changeStatusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.name("selectedStatus")
	            )
	    );

	    Select changeStatusDrop = new Select(changeStatusElement);

	    changeStatusDrop.selectByVisibleText("Inactive");

	    Thread.sleep(500);


	    WebElement submitButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Submit']")
	            )
	    );

	    submitButton.click();

	    Thread.sleep(1000);


	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='OK']")
	            )
	    );

	    okButton.click();

	    Thread.sleep(2500);
	    
		System.out.print("Status Changed from Active To Inactive");
	    
	    
	    
	    

	 // ============================================================
	 // SELECT INACTIVE
	 // ============================================================
	    WebElement tableRow = driver.findElement(
	            By.xpath("//tr[@class='table-light']")
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block: 'center'});",
	            tableRow
	    );
	    
	    Thread.sleep(2000);

	    WebElement inactiveStatusContainer = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    inactiveStatusContainer.click();
	    
	 Thread.sleep(1500);
	 
	 

	 WebElement inactiveStatusElement = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//select[contains(@class,'grid-select') and contains(@class,'alt-down-arrow')]")
		        )
		);

		((JavascriptExecutor) driver).executeScript(
		        "arguments[0].scrollIntoView({block:'center'});",
		        inactiveStatusElement
		);

		Select inactiveStatusDrop = new Select(inactiveStatusElement);

		inactiveStatusDrop.selectByVisibleText("Inactive");

		Thread.sleep(500);

		System.out.println(
		        "Selected Status : " +
		        inactiveStatusDrop.getFirstSelectedOption()
		        .getText()
		        .trim()
		);
		

		WebElement applyButton1 = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath("//button[normalize-space()='Apply']")
		        )
		);

		applyButton1.click();
		

		Thread.sleep(1000);


		
		

	    List<WebElement> inactiveRows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    boolean productFound = false;

	    for (WebElement row : inactiveRows) {

	        String currentProductId = row.findElement(
	                By.xpath("./td[2]")
	        ).getText().trim().replace("#", "").trim();

	        String currentSkuId = row.findElement(
	                By.xpath("./td[3]")
	        ).getText().trim().replace("#", "").trim();

	        if (currentProductId.equals(productId)
	                && currentSkuId.equals(skuId)) {

	            productFound = true;

	            String newStatus = row.findElement(
	                    By.xpath("./td[12]")
	            ).getText().trim();

	            // ====================================================
	            // FINAL VERIFICATION
	            // ====================================================

	            System.out.println();
	            System.out.println("======================================");
	            System.out.println("STATUS CHANGE VERIFICATION");
	            System.out.println("======================================");
	            System.out.println("Product ID : " + productId);
	            System.out.println("SKU ID     : " + skuId);
	            System.out.println("Old Status : " + oldStatus);
	            System.out.println("New Status : " + newStatus);
	            System.out.println("======================================");

	            if (newStatus.equalsIgnoreCase("Inactive")) {

	                System.out.println(
	                        "SUCCESS: Product ID " + productId
	                        + " and SKU ID " + skuId
	                        + " changed from Active to Inactive successfully."
	                );

	            } else {

	                System.out.println(
	                        "FAILED: Expected status Inactive but found "
	                        + newStatus
	                );
	            }

	            break;
	        }
	    }

	    if (!productFound) {

	        System.out.println(
	                "Product ID " + productId
	                + " and SKU ID " + skuId
	                + " were not found in the Inactive list."
	        );
	    }
	}
	
////////////////////////////////////////////////Inactive to Active///////////////////////////////////////////////////	
	
	public void changeInactiveProductToActive() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Random random = new Random();

	    // ============================================================
	    // SELECT INACTIVE FILTER
	    // ============================================================

//	    WebElement statusFilterIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
//	            )
//	    );
//
//	    statusFilterIcon.click();
//	    Thread.sleep(500);
//
//	    WebElement statusElement = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//div[@class='grid-select-cont']//select")
//	            )
//	    );
//
//	    Select statusDrop = new Select(statusElement);
//
//	    // Starting filter is INACTIVE
//	    statusDrop.selectByVisibleText("Inactive");
//
//	    Thread.sleep(500);
//
//	    System.out.println("Inactive selected.");
//
//	    WebElement applyButton = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[normalize-space()='Apply']")
//	            )
//	    );
//
//	    applyButton.click();
//	    Thread.sleep(1500);
//
//	    System.out.println("Inactive status filter applied.");

	    List<WebElement> rows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    if (rows.isEmpty()) {
	        System.out.println("No Inactive products found.");
	        return;
	    }

	    System.out.println("Total Inactive rows found: " + rows.size());


	    WebElement selectedRow = rows.get(
	            random.nextInt(rows.size())
	    );

	    String productId = selectedRow.findElement(
	            By.xpath("./td[2]")
	    ).getText().trim();

	    productId = productId.replace("#", "").trim();

	    String skuId = selectedRow.findElement(
	            By.xpath("./td[3]")
	    ).getText().trim();

	    skuId = skuId.replace("#", "").trim();

	    String oldStatus = selectedRow.findElement(
	            By.xpath("./td[12]")
	    ).getText().trim();

	    System.out.println("======================================");
	    System.out.println("SELECTED PRODUCT");
	    System.out.println("======================================");
	    System.out.println("Product ID : " + productId);
	    System.out.println("SKU ID     : " + skuId);
	    System.out.println("Old Status : " + oldStatus);


	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            selectedRow
	    );

	    Thread.sleep(500);


	    WebElement moreDropdown = selectedRow.findElement(
	            By.xpath(
	                    ".//div[contains(@class,'grid-actions-dropdown') " +
	                    "and contains(@class,'dropdown')]"
	            )
	    );

	    wait.until(
	            ExpectedConditions.elementToBeClickable(moreDropdown)
	    );

	    moreDropdown.click();
	    Thread.sleep(500);

	    WebElement changeStatus = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//a[contains(@class,'dropdown-item') " +
	                            "and normalize-space()='Change Status']"
	                    )
	            )
	    );

	    changeStatus.click();
	    Thread.sleep(500);

	    WebElement changeStatusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.name("selectedStatus")
	            )
	    );

	    Select changeStatusDrop = new Select(changeStatusElement);

	    changeStatusDrop.selectByVisibleText("Active");

	    Thread.sleep(500);


	    WebElement submitButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Submit']")
	            )
	    );

	    submitButton.click();
	    Thread.sleep(1000);


	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='OK']")
	            )
	    );

	    okButton.click();
	    Thread.sleep(2500);

	    System.out.println("Status Changed from Inactive To Active");



	    WebElement activeStatusContainer = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    activeStatusContainer.click();
	    Thread.sleep(1000);

	    WebElement activeStatusElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "//select[contains(@class,'grid-select') " +
	                            "and contains(@class,'alt-down-arrow')]"
	                    )
	            )
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            activeStatusElement
	    );

	    Select activeStatusDrop = new Select(activeStatusElement);

	    activeStatusDrop.selectByVisibleText("Active");

	    Thread.sleep(500);

	    System.out.println(
	            "Selected Status : " +
	            activeStatusDrop.getFirstSelectedOption()
	                    .getText()
	                    .trim()
	    );


	    WebElement applyButton1 = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton1.click();

	    Thread.sleep(1500);


	    List<WebElement> activeRows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    boolean productFound = false;

	    for (WebElement row : activeRows) {

	        String currentProductId = row.findElement(
	                By.xpath("./td[2]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        String currentSkuId = row.findElement(
	                By.xpath("./td[3]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        if (currentProductId.equals(productId)
	                && currentSkuId.equals(skuId)) {

	            productFound = true;

	            String newStatus = row.findElement(
	                    By.xpath("./td[12]")
	            ).getText().trim();

	      
	            System.out.println();
	            System.out.println("======================================");
	            System.out.println("STATUS CHANGE VERIFICATION");
	            System.out.println("======================================");
	            System.out.println("Product ID : " + productId);
	            System.out.println("SKU ID     : " + skuId);
	            System.out.println("Old Status : " + oldStatus);
	            System.out.println("New Status : " + newStatus);
	            System.out.println("======================================");

	            if (newStatus.equalsIgnoreCase("Active")) {

	                System.out.println(
	                        "SUCCESS: Product ID " + productId
	                        + " and SKU ID " + skuId
	                        + " changed from Inactive to Active successfully."
	                );

	            } else {

	                System.out.println(
	                        "FAILED: Expected status Active but found "
	                        + newStatus
	                );
	            }

	            break;
	        }
	    }
	    

	    if (!productFound) {

	        System.out.println(
	                "Product ID " + productId
	                + " and SKU ID " + skuId
	                + " were not found in the Active list."
	        );
	    }
	}
	
	
	
//////////////////////////////////  Active to Delete /////////////////////////////////////////////////////////////////
	
	
	public void changeActiveProductToDeleted() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Random random = new Random();

	    List<WebElement> rows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    if (rows.isEmpty()) {
	        System.out.println("No Active products found.");
	        return;
	    }

	    System.out.println("Total Active rows found: " + rows.size());

	    WebElement selectedRow = rows.get(
	            random.nextInt(rows.size())
	    );

	    String productId = selectedRow.findElement(
	            By.xpath("./td[2]")
	    ).getText().trim();

	    productId = productId.replace("#", "").trim();

	    String skuId = selectedRow.findElement(
	            By.xpath("./td[3]")
	    ).getText().trim();

	    skuId = skuId.replace("#", "").trim();

	    String oldStatus = selectedRow.findElement(
	            By.xpath("./td[12]")
	    ).getText().trim();

	    System.out.println("======================================");
	    System.out.println("SELECTED PRODUCT");
	    System.out.println("======================================");
	    System.out.println("Product ID : " + productId);
	    System.out.println("SKU ID     : " + skuId);
	    System.out.println("Old Status : " + oldStatus);

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            selectedRow
	    );

	    Thread.sleep(500);

	    WebElement moreDropdown = selectedRow.findElement(
	            By.xpath(
	                    ".//div[contains(@class,'grid-actions-dropdown') " +
	                    "and contains(@class,'dropdown')]"
	            )
	    );

	    wait.until(
	            ExpectedConditions.elementToBeClickable(moreDropdown)
	    );

	    moreDropdown.click();

	    Thread.sleep(500);

	    WebElement changeStatus = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//a[contains(@class,'dropdown-item') " +
	                            "and normalize-space()='Change Status']"
	                    )
	            )
	    );

	    changeStatus.click();

	    Thread.sleep(500);

	    WebElement changeStatusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.name("selectedStatus")
	            )
	    );

	    Select changeStatusDrop = new Select(changeStatusElement);

	    // ============================================================
	    // CHANGE ACTIVE TO DELETED
	    // ============================================================

	    changeStatusDrop.selectByVisibleText("Deleted");

	    Thread.sleep(500);

	    WebElement submitButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Submit']")
	            )
	    );

	    submitButton.click();

	    Thread.sleep(1000);

	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='OK']")
	            )
	    );

	    okButton.click();

	    Thread.sleep(2500);

	    System.out.println("Status Changed from Active To Deleted");


	    // ============================================================
	    // SELECT DELETED FILTER
	    // ============================================================

	    WebElement deletedStatusContainer = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    deletedStatusContainer.click();

	    Thread.sleep(1000);

	    WebElement deletedStatusElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "//select[contains(@class,'grid-select') " +
	                            "and contains(@class,'alt-down-arrow')]"
	                    )
	            )
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            deletedStatusElement
	    );

	    Select deletedStatusDrop = new Select(deletedStatusElement);

	    deletedStatusDrop.selectByVisibleText("Deleted");

	    Thread.sleep(500);

	    System.out.println(
	            "Selected Status : " +
	            deletedStatusDrop.getFirstSelectedOption()
	                    .getText()
	                    .trim()
	    );


	    // ============================================================
	    // APPLY DELETED FILTER
	    // ============================================================

	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton.click();

	    Thread.sleep(1500);


	    // ============================================================
	    // GET DELETED ROWS
	    // ============================================================

	    List<WebElement> deletedRows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    boolean productFound = false;

	    for (WebElement row : deletedRows) {

	        String currentProductId = row.findElement(
	                By.xpath("./td[2]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        String currentSkuId = row.findElement(
	                By.xpath("./td[3]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        if (currentProductId.equals(productId)
	                && currentSkuId.equals(skuId)) {

	            productFound = true;

	            String newStatus = row.findElement(
	                    By.xpath("./td[12]")
	            ).getText().trim();

	            System.out.println();
	            System.out.println("======================================");
	            System.out.println("STATUS CHANGE VERIFICATION");
	            System.out.println("======================================");
	            System.out.println("Product ID : " + productId);
	            System.out.println("SKU ID     : " + skuId);
	            System.out.println("Old Status : " + oldStatus);
	            System.out.println("New Status : " + newStatus);
	            System.out.println("======================================");

	            if (newStatus.equalsIgnoreCase("Deleted")) {

	                System.out.println(
	                        "SUCCESS: Product ID " + productId
	                        + " and SKU ID " + skuId
	                        + " changed from Active to Deleted successfully."
	                );

	            } else {

	                System.out.println(
	                        "FAILED: Expected status Deleted but found "
	                        + newStatus
	                );
	            }

	            break;
	        }
	    }

	    if (!productFound) {

	        System.out.println(
	                "Product ID " + productId
	                + " and SKU ID " + skuId
	                + " were not found in the Deleted list."
	        );
	    }
	}
	
	
/////////////////////////////////// Inactive to Delete //////////////////////////////////////////////////////////////////
	
	
	
	public void changeInactiveProductToDeleted() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Random random = new Random();

	    // ============================================================
	    // GET INACTIVE PRODUCTS
	    // ============================================================

	    List<WebElement> rows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    if (rows.isEmpty()) {
	        System.out.println("No Inactive products found.");
	        return;
	    }

	    System.out.println("Total Inactive rows found: " + rows.size());

	    // ============================================================
	    // SELECT RANDOM INACTIVE PRODUCT
	    // ============================================================

	    WebElement selectedRow = rows.get(
	            random.nextInt(rows.size())
	    );

	    String productId = selectedRow.findElement(
	            By.xpath("./td[2]")
	    ).getText().trim().replace("#", "").trim();

	    String skuId = selectedRow.findElement(
	            By.xpath("./td[3]")
	    ).getText().trim().replace("#", "").trim();

	    String oldStatus = selectedRow.findElement(
	            By.xpath("./td[12]")
	    ).getText().trim();

	    System.out.println("======================================");
	    System.out.println("SELECTED PRODUCT");
	    System.out.println("======================================");
	    System.out.println("Product ID : " + productId);
	    System.out.println("SKU ID     : " + skuId);
	    System.out.println("Old Status : " + oldStatus);

	    // ============================================================
	    // SCROLL TO SELECTED ROW
	    // ============================================================

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            selectedRow
	    );

	    Thread.sleep(500);

	    // ============================================================
	    // CLICK MORE / ACTION DROPDOWN
	    // ============================================================

	    WebElement moreDropdown = selectedRow.findElement(
	            By.xpath(
	                    ".//div[contains(@class,'grid-actions-dropdown') " +
	                    "and contains(@class,'dropdown')]"
	            )
	    );

	    wait.until(
	            ExpectedConditions.elementToBeClickable(moreDropdown)
	    );

	    moreDropdown.click();

	    Thread.sleep(500);

	    // ============================================================
	    // CLICK CHANGE STATUS
	    // ============================================================

	    WebElement changeStatus = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//a[contains(@class,'dropdown-item') " +
	                            "and normalize-space()='Change Status']"
	                    )
	            )
	    );

	    changeStatus.click();

	    Thread.sleep(500);

	    // ============================================================
	    // CHANGE INACTIVE TO DELETED
	    // ============================================================

	    WebElement changeStatusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.name("selectedStatus")
	            )
	    );

	    Select changeStatusDrop = new Select(changeStatusElement);

	    changeStatusDrop.selectByVisibleText("Deleted");

	    Thread.sleep(500);

	    // ============================================================
	    // SUBMIT
	    // ============================================================

	    WebElement submitButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Submit']")
	            )
	    );

	    submitButton.click();

	    Thread.sleep(1000);

	    // ============================================================
	    // CLICK OK
	    // ============================================================

	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='OK']")
	            )
	    );

	    okButton.click();

	    Thread.sleep(2500);

	    System.out.println("Status Changed from Inactive To Deleted");


	    // ============================================================
	    // SELECT DELETED FILTER
	    // ============================================================

	    WebElement deletedStatusContainer = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    deletedStatusContainer.click();

	    Thread.sleep(1000);

	    WebElement deletedStatusElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "//select[contains(@class,'grid-select') " +
	                            "and contains(@class,'alt-down-arrow')]"
	                    )
	            )
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            deletedStatusElement
	    );

	    Select deletedStatusDrop = new Select(deletedStatusElement);

	    deletedStatusDrop.selectByVisibleText("Deleted");

	    Thread.sleep(500);

	    System.out.println(
	            "Selected Status : " +
	            deletedStatusDrop.getFirstSelectedOption()
	                    .getText()
	                    .trim()
	    );

	    // ============================================================
	    // APPLY DELETED FILTER
	    // ============================================================

	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton.click();

	    Thread.sleep(1500);


	    // ============================================================
	    // GET DELETED ROWS
	    // ============================================================

	    List<WebElement> deletedRows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    boolean productFound = false;

	    // ============================================================
	    // VERIFY SAME PRODUCT + SKU IN DELETED LIST
	    // ============================================================

	    for (WebElement row : deletedRows) {

	        String currentProductId = row.findElement(
	                By.xpath("./td[2]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        String currentSkuId = row.findElement(
	                By.xpath("./td[3]")
	        ).getText().trim()
	         .replace("#", "")
	         .trim();

	        if (currentProductId.equals(productId)
	                && currentSkuId.equals(skuId)) {

	            productFound = true;

	            String newStatus = row.findElement(
	                    By.xpath("./td[12]")
	            ).getText().trim();

	            // ====================================================
	            // FINAL VERIFICATION
	            // ====================================================

	            System.out.println();
	            System.out.println("======================================");
	            System.out.println("STATUS CHANGE VERIFICATION");
	            System.out.println("======================================");
	            System.out.println("Product ID : " + productId);
	            System.out.println("SKU ID     : " + skuId);
	            System.out.println("Old Status : " + oldStatus);
	            System.out.println("New Status : " + newStatus);
	            System.out.println("======================================");

	            if (newStatus.equalsIgnoreCase("Deleted")) {

	                System.out.println(
	                        "SUCCESS: Product ID " + productId
	                        + " and SKU ID " + skuId
	                        + " changed from Inactive to Deleted successfully."
	                );

	            } else {

	                System.out.println(
	                        "FAILED: Expected status Deleted but found "
	                        + newStatus
	                );
	            }

	            break;
	        }
	    }

	    // ============================================================
	    // PRODUCT NOT FOUND
	    // ============================================================

	    if (!productFound) {

	        System.out.println(
	                "Product ID " + productId
	                + " and SKU ID " + skuId
	                + " were not found in the Deleted list."
	        );
	    }
	}


////////////////////////////////// BULK STATUS CHANGE FROM ACTIVE TO INACTIVE ///////////////////////////////////
	
	public void bulkStatusChangefromActivetoInactive() throws InterruptedException {
		

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    Random random = new Random();

		    // ============================================================
		    // SELECT ACTIVE FILTER
		    // ============================================================

		    WebElement statusFilterIcon = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
		            )
		    );

		    statusFilterIcon.click();
		    Thread.sleep(500);

		    WebElement statusElement = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//div[@class='grid-select-cont']//select")
		            )
		    );

		    Select statusDrop = new Select(statusElement);

		    statusDrop.selectByVisibleText("Active");

		    Thread.sleep(1000);

		    System.out.println("Active selected.");

		    // ============================================================
		    // APPLY ACTIVE FILTER
		    // ============================================================

		    WebElement applyButton = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//button[normalize-space()='Apply']")
		            )
		    );

		    applyButton.click();

		    Thread.sleep(1500);

		    System.out.println("Active status filter applied.");

		    // ============================================================
		    // GET ACTIVE CHECKBOXES
		    // ============================================================

		    List<WebElement> checkboxes = wait.until(
		            ExpectedConditions.presenceOfAllElementsLocatedBy(
		                    By.xpath(
		                            "//table//tbody//tr//input[@type='checkbox' " +
		                            "and contains(@class,'grid-checkbox-input')]"
		                    )
		            )
		    );

		    if (checkboxes.isEmpty()) {

		        System.out.println("No Active products found.");
		        return;
		    }

		    System.out.println(
		            "Total Active checkboxes found: " + checkboxes.size()
		    );

		    // ============================================================
		    // SELECT 2 OR 3 CHECKBOXES RANDOMLY
		    // ============================================================

		    int numberToSelect;

		    if (checkboxes.size() == 1) {

		        numberToSelect = 1;

		    } else if (checkboxes.size() == 2) {

		        numberToSelect = 2;

		    } else {

		        // Randomly select either 2 or 3
		        numberToSelect = 2 + random.nextInt(2);
		    }

		    // Shuffle the checkbox list
		    Collections.shuffle(checkboxes, random);

		    // ============================================================
		    // STORE SELECTED PRODUCT IDs AND SKU IDs
		    // ============================================================

		    List<String> selectedProductIds = new ArrayList<>();
		    List<String> selectedSkuIds = new ArrayList<>();

		    System.out.println();
		    System.out.println("======================================");
		    System.out.println("SELECTED PRODUCTS");
		    System.out.println("======================================");

		    for (int i = 0; i < numberToSelect; i++) {

		        WebElement checkbox = checkboxes.get(i);

		        // Get the row containing this checkbox
		        WebElement row = checkbox.findElement(
		                By.xpath("./ancestor::tr")
		        );

		        // ========================================================
		        // CAPTURE PRODUCT ID
		        // ========================================================

		        String productId = row.findElement(
		                By.xpath("./td[2]")
		        ).getText().trim();

		        productId = productId.replace("#", "").trim();

		        // ========================================================
		        // CAPTURE SKU ID
		        // ========================================================

		        String skuId = row.findElement(
		                By.xpath("./td[3]")
		        ).getText().trim();

		        skuId = skuId.replace("#", "").trim();

		        selectedProductIds.add(productId);
		        selectedSkuIds.add(skuId);

		        // ========================================================
		        // SCROLL TO CHECKBOX
		        // ========================================================

		        ((JavascriptExecutor) driver).executeScript(
		                "arguments[0].scrollIntoView({block:'center'});",
		                checkbox
		        );

		        Thread.sleep(1000);

		        // ========================================================
		        // CLICK CHECKBOX
		        // ========================================================

		        ((JavascriptExecutor) driver).executeScript(
		                "arguments[0].click();",
		                checkbox
		        );

		        Thread.sleep(300);

		        System.out.println(
		                "Product ID : " + productId +
		                " | SKU ID : " + skuId
		        );
		    }

		    System.out.println("======================================");
		    System.out.println(
		            "Total Products Selected : " + numberToSelect
		    );
		    System.out.println("======================================");

		    // ============================================================
		    // CLICK BULK STATUS CHANGE
		    // ============================================================

		    WebElement bulkStatusChangeButton = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath(
		                            "//button[normalize-space()='Bulk Status Change']"
		                    )
		            )
		    );

		    ((JavascriptExecutor) driver).executeScript(
		            "arguments[0].scrollIntoView({block:'center'});",
		            bulkStatusChangeButton
		    );

		    Thread.sleep(1000);

		    bulkStatusChangeButton.click();

		    Thread.sleep(1000);

		    System.out.println("Bulk Status Change clicked.");

		    // ============================================================
		    // SELECT INACTIVE STATUS
		    // ============================================================

		    WebElement changeStatusElement = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.name("selectedStatus")
		            )
		    );

		    Select changeStatusDrop = new Select(changeStatusElement);

		    changeStatusDrop.selectByVisibleText("Inactive");

		    Thread.sleep(1000);

		    System.out.println(
		            "Bulk Status Selected : " +
		            changeStatusDrop.getFirstSelectedOption()
		                    .getText()
		                    .trim()
		    );

		    // ============================================================
		    // ENTER COMMENT
		    // ============================================================

		    WebElement commentField = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//input[@name='comment']")
		            )
		    );

		    commentField.clear();
		    commentField.sendKeys("TEST");

		    Thread.sleep(1000);

		    System.out.println("Comment entered : TEST");

		    // ============================================================
		    // CLICK SUBMIT
		    // ============================================================

		    WebElement submitButton = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//button[normalize-space()='Submit']")
		            )
		    );

		    submitButton.click();

		    Thread.sleep(1000);

		    System.out.println("Submit clicked.");

		    // ============================================================
		    // CLICK OK
		    // ============================================================

		    WebElement okButton = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//button[normalize-space()='OK']")
		            )
		    );

		    okButton.click();

		    Thread.sleep(2500);

		    System.out.println("OK clicked.");
		    System.out.println("Status Changed from Active To Inactive");


		    // ============================================================
		    // SELECT INACTIVE FILTER FOR VERIFICATION
		    // ============================================================

		    WebElement inactiveStatusContainer = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
		            )
		    );

		    inactiveStatusContainer.click();

		    Thread.sleep(1000);

		    // ============================================================
		    // SELECT INACTIVE
		    // ============================================================

		    WebElement inactiveStatusElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath(
		                            "//select[contains(@class,'grid-select') " +
		                            "and contains(@class,'alt-down-arrow')]"
		                    )
		            )
		    );

		    ((JavascriptExecutor) driver).executeScript(
		            "arguments[0].scrollIntoView({block:'center'});",
		            inactiveStatusElement
		    );

		    Select inactiveStatusDrop = new Select(inactiveStatusElement);

		    inactiveStatusDrop.selectByVisibleText("Inactive");

		    Thread.sleep(500);

		    System.out.println(
		            "Selected Status : " +
		            inactiveStatusDrop.getFirstSelectedOption()
		                    .getText()
		                    .trim()
		    );

		    // ============================================================
		    // APPLY INACTIVE FILTER
		    // ============================================================

		    WebElement applyButton1 = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//button[normalize-space()='Apply']")
		            )
		    );

		    applyButton1.click();

		    Thread.sleep(1500);

		    System.out.println("Inactive status filter applied.");


		    // ============================================================
		    // GET INACTIVE ROWS
		    // ============================================================

		    List<WebElement> inactiveRows = wait.until(
		            ExpectedConditions.presenceOfAllElementsLocatedBy(
		                    By.xpath("//table//tbody//tr")
		            )
		    );

		    // ============================================================
		    // VERIFY EACH SELECTED PRODUCT
		    // ============================================================

		    System.out.println();
		    System.out.println("======================================");
		    System.out.println("BULK STATUS CHANGE VERIFICATION");
		    System.out.println("======================================");

		    for (int i = 0; i < selectedProductIds.size(); i++) {

		        String expectedProductId = selectedProductIds.get(i);
		        String expectedSkuId = selectedSkuIds.get(i);

		        boolean productFound = false;

		        for (WebElement row : inactiveRows) {

		            // ====================================================
		            // CURRENT PRODUCT ID
		            // ====================================================

		            String currentProductId = row.findElement(
		                    By.xpath("./td[2]")
		            ).getText().trim();

		            currentProductId = currentProductId
		                    .replace("#", "")
		                    .trim();

		            // ====================================================
		            // CURRENT SKU ID
		            // ====================================================

		            String currentSkuId = row.findElement(
		                    By.xpath("./td[3]")
		            ).getText().trim();

		            currentSkuId = currentSkuId
		                    .replace("#", "")
		                    .trim();

		            // ====================================================
		            // MATCH PRODUCT + SKU
		            // ====================================================

		            if (currentProductId.equals(expectedProductId)
		                    && currentSkuId.equals(expectedSkuId)) {

		                productFound = true;

		                // =================================================
		                // GET NEW STATUS
		                // =================================================

		                String newStatus = row.findElement(
		                        By.xpath("./td[12]")
		                ).getText().trim();

		                System.out.println();
		                System.out.println("--------------------------------------");
		                System.out.println("Product ID : " + expectedProductId);
		                System.out.println("SKU ID     : " + expectedSkuId);
		                System.out.println("New Status : " + newStatus);

		                // =================================================
		                // VERIFY STATUS
		                // =================================================

		                if (newStatus.equalsIgnoreCase("Inactive")) {

		                    System.out.println(
		                            "SUCCESS: Product ID " +
		                            expectedProductId +
		                            " and SKU ID " +
		                            expectedSkuId +
		                            " changed from Active to Inactive successfully."
		                    );

		                } else {

		                    System.out.println(
		                            "FAILED: Product ID " +
		                            expectedProductId +
		                            " and SKU ID " +
		                            expectedSkuId +
		                            " expected Inactive but found " +
		                            newStatus
		                    );
		                }

		                System.out.println("--------------------------------------");

		                break;
		            }
		        }

		        // ========================================================
		        // PRODUCT NOT FOUND IN INACTIVE LIST
		        // ========================================================

		        if (!productFound) {

		            System.out.println();
		            System.out.println(
		                    "FAILED: Product ID " +
		                    expectedProductId +
		                    " and SKU ID " +
		                    expectedSkuId +
		                    " was NOT found in the Inactive list."
		            );
		        }
		    }
		}
	
	
////////////////////////////// BULK STATUS CHANGE INACTIVE TO ACTIVE /////////////////////////////////////////////////
	
	public void bulkStatusChangefromInactivetoActive() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Random random = new Random();

	    // ============================================================
	    // SELECT INACTIVE FILTER
	    // ============================================================

//	    WebElement statusFilterIcon = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
//	            )
//	    );
//
//	    statusFilterIcon.click();
//	    Thread.sleep(500);
//
//	    WebElement statusElement = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//div[@class='grid-select-cont']//select")
//	            )
//	    );
//
//	    Select statusDrop = new Select(statusElement);
//
//	    // START WITH INACTIVE
//	    statusDrop.selectByVisibleText("Inactive");
//
//	    Thread.sleep(1000);
//
//	    System.out.println("Inactive selected.");
//
//	    // ============================================================
//	    // APPLY INACTIVE FILTER
//	    // ============================================================
//
//	    WebElement applyButton = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//button[normalize-space()='Apply']")
//	            )
//	    );
//
//	    applyButton.click();
//
//	    Thread.sleep(1500);
//
//	    System.out.println("Inactive status filter applied.");

	    // ============================================================
	    // GET INACTIVE CHECKBOXES
	    // ============================================================

	    List<WebElement> checkboxes = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath(
	                            "//table//tbody//tr//input[@type='checkbox' " +
	                            "and contains(@class,'grid-checkbox-input')]"
	                    )
	            )
	    );

	    if (checkboxes.isEmpty()) {

	        System.out.println("No Inactive products found.");
	        return;
	    }

	    System.out.println(
	            "Total Inactive checkboxes found: " + checkboxes.size()
	    );

	    // ============================================================
	    // SELECT 2 OR 3 CHECKBOXES RANDOMLY
	    // ============================================================

	    int numberToSelect;

	    if (checkboxes.size() == 1) {

	        numberToSelect = 1;

	    } else if (checkboxes.size() == 2) {

	        numberToSelect = 2;

	    } else {

	        // Randomly select either 2 or 3
	        numberToSelect = 2 + random.nextInt(2);
	    }

	    // Shuffle checkboxes randomly
	    Collections.shuffle(checkboxes, random);

	    // ============================================================
	    // STORE SELECTED PRODUCT IDs AND SKU IDs
	    // ============================================================

	    List<String> selectedProductIds = new ArrayList<>();
	    List<String> selectedSkuIds = new ArrayList<>();

	    System.out.println();
	    System.out.println("======================================");
	    System.out.println("SELECTED PRODUCTS");
	    System.out.println("======================================");

	    for (int i = 0; i < numberToSelect; i++) {

	        WebElement checkbox = checkboxes.get(i);

	        // ========================================================
	        // GET ROW OF CHECKBOX
	        // ========================================================

	        WebElement row = checkbox.findElement(
	                By.xpath("./ancestor::tr")
	        );

	        // ========================================================
	        // CAPTURE PRODUCT ID
	        // ========================================================

	        String productId = row.findElement(
	                By.xpath("./td[2]")
	        ).getText().trim();

	        productId = productId.replace("#", "").trim();

	        // ========================================================
	        // CAPTURE SKU ID
	        // ========================================================

	        String skuId = row.findElement(
	                By.xpath("./td[3]")
	        ).getText().trim();

	        skuId = skuId.replace("#", "").trim();

	        // ========================================================
	        // SCROLL TO CHECKBOX
	        // ========================================================

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({block:'center'});",
	                checkbox
	        );

	        Thread.sleep(1000);

	        // ========================================================
	        // CLICK CHECKBOX
	        // ========================================================

	        if (!checkbox.isSelected()) {

	            ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].click();",
	                    checkbox
	            );

	            Thread.sleep(500);
	        }

	        // ========================================================
	        // VERIFY CHECKBOX IS SELECTED
	        // ========================================================

	        if (checkbox.isSelected()) {

	            selectedProductIds.add(productId);
	            selectedSkuIds.add(skuId);

	            System.out.println(
	                    "Product ID : " + productId +
	                    " | SKU ID : " + skuId
	            );

	        } else {

	            System.out.println(
	                    "WARNING: Checkbox was not selected for Product ID : "
	                    + productId
	            );
	        }
	    }

	    // ============================================================
	    // VERIFY ACTUAL NUMBER SELECTED
	    // ============================================================

	    System.out.println("======================================");

	    System.out.println(
	            "Total Products Selected : "
	            + selectedProductIds.size()
	    );

	    System.out.println("======================================");

	    if (selectedProductIds.isEmpty()) {

	        System.out.println("No products were selected.");
	        return;
	    }

	    // ============================================================
	    // CLICK BULK STATUS CHANGE
	    // ============================================================

	    WebElement bulkStatusChangeButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath(
	                            "//button[normalize-space()='Bulk Status Change']"
	                    )
	            )
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            bulkStatusChangeButton
	    );

	    Thread.sleep(1000);

	    bulkStatusChangeButton.click();

	    Thread.sleep(1000);

	    System.out.println("Bulk Status Change clicked.");

	    // ============================================================
	    // SELECT ACTIVE STATUS
	    // ============================================================

	    WebElement changeStatusElement = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.name("selectedStatus")
	            )
	    );

	    Select changeStatusDrop = new Select(changeStatusElement);

	    // CHANGE INACTIVE TO ACTIVE
	    changeStatusDrop.selectByVisibleText("Active");

	    Thread.sleep(1000);

	    System.out.println(
	            "Bulk Status Selected : " +
	            changeStatusDrop.getFirstSelectedOption()
	                    .getText()
	                    .trim()
	    );

	    // ============================================================
	    // ENTER COMMENT
	    // ============================================================

	    WebElement commentField = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@name='comment']")
	            )
	    );

	    commentField.clear();
	    commentField.sendKeys("TEST");

	    Thread.sleep(1000);

	    System.out.println("Comment entered : TEST");

	    // ============================================================
	    // CLICK SUBMIT
	    // ============================================================

	    WebElement submitButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Submit']")
	            )
	    );

	    submitButton.click();

	    Thread.sleep(1000);

	    System.out.println("Submit clicked.");

	    // ============================================================
	    // CLICK OK
	    // ============================================================

	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='OK']")
	            )
	    );

	    okButton.click();

	    Thread.sleep(2500);

	    System.out.println("OK clicked.");

	    System.out.println(
	            "Status Changed from Inactive To Active"
	    );

	    // ============================================================
	    // SELECT ACTIVE FILTER FOR VERIFICATION
	    // ============================================================

	    WebElement activeStatusContainer = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//div[@class='custom-filter-icon'])[8]")
	            )
	    );

	    activeStatusContainer.click();

	    Thread.sleep(1000);

	    // ============================================================
	    // SELECT ACTIVE
	    // ============================================================

	    WebElement activeStatusElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(
	                            "//select[contains(@class,'grid-select') " +
	                            "and contains(@class,'alt-down-arrow')]"
	                    )
	            )
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            activeStatusElement
	    );

	    Select activeStatusDrop = new Select(activeStatusElement);

	    activeStatusDrop.selectByVisibleText("Active");

	    Thread.sleep(500);

	    System.out.println(
	            "Selected Status : " +
	            activeStatusDrop.getFirstSelectedOption()
	                    .getText()
	                    .trim()
	    );

	    // ============================================================
	    // APPLY ACTIVE FILTER
	    // ============================================================

	    WebElement applyButton1 = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton1.click();

	    Thread.sleep(1500);

	    System.out.println("Active status filter applied.");

	    // ============================================================
	    // GET ACTIVE ROWS
	    // ============================================================

	    List<WebElement> activeRows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    // ============================================================
	    // VERIFY EACH SELECTED PRODUCT
	    // ============================================================

	    System.out.println();
	    System.out.println("======================================");
	    System.out.println("BULK STATUS CHANGE VERIFICATION");
	    System.out.println("======================================");

	    for (int i = 0; i < selectedProductIds.size(); i++) {

	        String expectedProductId = selectedProductIds.get(i);
	        String expectedSkuId = selectedSkuIds.get(i);

	        boolean productFound = false;

	        for (WebElement row : activeRows) {

	            // ====================================================
	            // CURRENT PRODUCT ID
	            // ====================================================

	            String currentProductId = row.findElement(
	                    By.xpath("./td[2]")
	            ).getText().trim();

	            currentProductId = currentProductId
	                    .replace("#", "")
	                    .trim();

	            // ====================================================
	            // CURRENT SKU ID
	            // ====================================================

	            String currentSkuId = row.findElement(
	                    By.xpath("./td[3]")
	            ).getText().trim();

	            currentSkuId = currentSkuId
	                    .replace("#", "")
	                    .trim();

	            // ====================================================
	            // MATCH PRODUCT ID + SKU ID
	            // ====================================================

	            if (currentProductId.equals(expectedProductId)
	                    && currentSkuId.equals(expectedSkuId)) {

	                productFound = true;

	                // =================================================
	                // GET NEW STATUS
	                // =================================================

	                String newStatus = row.findElement(
	                        By.xpath("./td[12]")
	                ).getText().trim();

	                // =================================================
	                // PRINT VERIFICATION
	                // =================================================

	                System.out.println();
	                System.out.println(
	                        "--------------------------------------"
	                );

	                System.out.println(
	                        "Product ID : " + expectedProductId
	                );

	                System.out.println(
	                        "SKU ID     : " + expectedSkuId
	                );

	                System.out.println(
	                        "New Status : " + newStatus
	                );

	                // =================================================
	                // VERIFY ACTIVE
	                // =================================================

	                if (newStatus.equalsIgnoreCase("Active")) {

	                    System.out.println(
	                            "SUCCESS: Product ID "
	                            + expectedProductId
	                            + " and SKU ID "
	                            + expectedSkuId
	                            + " changed from Inactive to Active successfully."
	                    );

	                } else {

	                    System.out.println(
	                            "FAILED: Product ID "
	                            + expectedProductId
	                            + " and SKU ID "
	                            + expectedSkuId
	                            + " expected Active but found "
	                            + newStatus
	                    );
	                }

	                System.out.println(
	                        "--------------------------------------"
	                );

	                break;
	            }
	        }

	        // ========================================================
	        // PRODUCT NOT FOUND IN ACTIVE LIST
	        // ========================================================

	        if (!productFound) {

	            System.out.println();

	            System.out.println(
	                    "FAILED: Product ID "
	                    + expectedProductId
	                    + " and SKU ID "
	                    + expectedSkuId
	                    + " was NOT found in the Active list."
	            );
	        }
	    }
	}

	}