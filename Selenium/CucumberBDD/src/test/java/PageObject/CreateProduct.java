package PageObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProduct {

	public WebDriver driver;
	public WebDriverWait wait;

	public CreateProduct(WebDriver driver) {
		this.driver = driver;
	}

	public void CreateProductPage() throws InterruptedException {

		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='menu-cont' ] //img[@alt='img']")).click();// menu
		Thread.sleep(3000);

		driver.findElement(By.xpath("//ul//li//span[text()='Product']")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//ul//li//span[text()='Create Product']")).click();

	}

	public void CreateProductValidation() throws InterruptedException {

		Thread.sleep(10000);

		WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
		submitButton.click();
		Thread.sleep(1000);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		// System.out.println("Status Failed: " + "Check if the product can be created
		// without Product name(mandatory)");

		System.out.println("Status Failed: " + "The product cannot be created without mandatory fields");

		Thread.sleep(2000);

	}

//				submitButton.click();    //Product Images
//			    Thread.sleep(1000);
//			    
//			    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//			    System.out.println("Status Failed: " + "Check if the product can be created without Product Image(mandatory)");

//				Thread.sleep(2000);

	public void AddImage() throws InterruptedException {

		WebElement fileInput = driver.findElement(By.cssSelector("input[type='file'][accept='image/*'][hidden]"));

		// fileInput.sendKeys("D:/FOT _APP/fot image/fot images.jpeg");

		fileInput.sendKeys("D:/Fot_Image/FotImage/images.jpg");

		WebElement cropSelection = driver.findElement(By.xpath("//div[@class='ReactCrop__crop-selection']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(cropSelection, 210, 210).clickAndHold().moveByOffset(50, 50).release().perform();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.width = '200px';", cropSelection);
		js.executeScript("arguments[0].style.height = '200px';", cropSelection);

		WebElement cropButton = driver.findElement(By.xpath("//button[text()[contains(., 'Crop')]]"));
		cropButton.click();

		System.out.println("Status : Passed");

		Thread.sleep(3000);

	}

//				submitButton.click();  //Product Depart
//			    Thread.sleep(1000);
//			    
//			    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//			    System.out.println("Status Failed: " + "Check if the product can be created without Product Department(mandatory)");
//		
//			    Thread.sleep(1000);

	public void ProductCreationMandatory() throws InterruptedException {

		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String productName = "FOT " + timestamp;

		WebElement productNameField = driver.findElement(By.name("productName"));

		productNameField.sendKeys(productName);

		System.out.println("Status : Passed");

		Thread.sleep(2000);

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(25));

		WebElement dept = wait1.until(ExpectedConditions.presenceOfElementLocated(By.name("productDept")));
		Select deptDrop = new Select(dept);
		List<WebElement> deptoptions = deptDrop.getOptions();

		List<WebElement> validOptions1 = new ArrayList<>();
		for (WebElement option : deptoptions) {
			String optionText = option.getText().trim();
			if (!optionText.equals("Department") && !optionText.equals("Select")) {
				validOptions1.add(option);
			}
		}

		if (!validOptions1.isEmpty()) {
			Random deptRand = new Random();
			int randomIndex1 = deptRand.nextInt(validOptions1.size());
			deptDrop.selectByIndex(deptoptions.indexOf(validOptions1.get(randomIndex1)));
			System.out.println("Status : Passed");
		}

		else {
			System.out.println("Status:failed");
		}

		Thread.sleep(3000);

//					submitButton.click();  //Product category
//				    Thread.sleep(1000);
//				    
//				    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				    System.out.println("Status Failed: " + "Check if the product can be created without Product Category(mandatory)");
//			
//				    Thread.sleep(2000);

		WebElement prdCat = wait1.until(ExpectedConditions.elementToBeClickable(By.name("productCat")));
		Select prdCatDrop = new Select(prdCat);

		prdCatDrop.selectByIndex(2);

		System.out.println("Status : Passed");

		Thread.sleep(3000);

//					 submitButton.click();  //Product sub category
//					    Thread.sleep(1000);
//					    
//					    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//					    System.out.println("Status Failed: " + "Check if the product can be created without Product Sub-Category(mandatory)");
//				
//					    Thread.sleep(2000);

		WebElement prdCatSub = wait1.until(ExpectedConditions.elementToBeClickable(By.name("productSubCat")));
		Select prdSubDrop = new Select(prdCatSub);

		prdSubDrop.selectByIndex(2);

		System.out.println("Status : Passed");

		Thread.sleep(3000);

		WebElement classiDown = wait1.until(ExpectedConditions.elementToBeClickable(By.name("productClassification"))); // Product
																														// class
		Select Classidropdown = new Select(classiDown);
		List<WebElement> classiOptions = Classidropdown.getOptions();
		List<WebElement> validOptions = new ArrayList<>();
		for (WebElement option : classiOptions) {
			String optionText = option.getText().trim();
			if (!optionText.equals("Classification") && !optionText.equals("Select")) {
				validOptions.add(option);
			}
		}
		Random classirand1 = new Random();
		if (!validOptions.isEmpty()) {
			int randomIndex = classirand1.nextInt(validOptions.size());
			Classidropdown.selectByIndex(classiOptions.indexOf(validOptions.get(randomIndex)));
		}

		Thread.sleep(3000);

		WebElement dropDown = wait1.until(ExpectedConditions.elementToBeClickable(By.name("productTemp"))); // Product
																											// Temp
		Select Tempdropdown = new Select(dropDown);
		List<WebElement> tempOptions = Tempdropdown.getOptions();
		List<WebElement> tempdropOptions = new ArrayList<>();
		for (WebElement option : tempOptions) {
			String optionText = option.getText().trim();
			if (!optionText.equals("Temperature") && !optionText.equals("Select")) {
				tempdropOptions.add(option);
			}
		}

		Random temprand1 = new Random();
		if (!tempdropOptions.isEmpty()) {
			int randomIndex = temprand1.nextInt(tempdropOptions.size());
			Tempdropdown.selectByIndex(tempOptions.indexOf(tempdropOptions.get(randomIndex)));
		}

		Thread.sleep(3000);

//						submitButton.click();    //Product Origin
//					    Thread.sleep(1000);
//					    
//					    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//					    System.out.println("Status Failed: " + "Check if the product can be created without Product Origin(mandatory)");
//				
//					    Thread.sleep(2000);

		driver.findElement(By.xpath("(//input[@class=\"fot-formControl empty empty\"])[1]")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();

		System.out.println("Status : Passed");

		Thread.sleep(2000);

		WebElement taxDropdown = driver.findElement(By.xpath("//select[@name='productTax']"));

		Select select = new Select(taxDropdown);
		select.selectByVisibleText("5%");

		Thread.sleep(2000);

//						    System.out.println("The Maintain Batch toggle need to enabled (Mandatory)");
//							driver.findElement(By.xpath("//div[@title='Maintain Batch']"));
//							System.out.println("Status : Passed");
//							
//							Thread.sleep(2000);
//							
//							 System.out.println("The Manage Inventory toggle need to enabled (Mandatory)");
//							driver.findElement(By.xpath("//div[@title='Manage Inventory']"));
//							System.out.println("Status : Passed");

//							Thread.sleep(2000);

//							submitButton.click();   //Seller
//						    Thread.sleep(5000);
//						    
//						    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//						    System.out.println("Status Failed: " + "Check if the product can be created without Seller(mandatory)");
//					

	}

//	public void VariantDetails() throws InterruptedException {
//
//		Thread.sleep(4000);
//
//		try {
//			driver.findElement(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")).click();
//
//			Thread.sleep(4000);
//
//			WebElement paginationContainer = driver.findElement(By.xpath("//button[@type='button' and @aria-label='Last']"));
//
//			List<WebElement> pageNumbers = paginationContainer.findElements(By.xpath("//button[@type='button' and @aria-label=\"Next\"]"));
//
//			if (pageNumbers.isEmpty()) {
//				System.out.println("Status: Failed - No pagination elements found");
//				return;
//			}
//
//			Random rand = new Random();
//			int randomIndex111 = rand.nextInt(pageNumbers.size());
//			WebElement randomPage = pageNumbers.get(randomIndex111);
//			randomPage.click();
//
//			Thread.sleep(3000);
//
//			List<WebElement> rows = driver
//					.findElements(By.xpath("/html/body/div[5]/div[3]/div[2]/div/div/div[2]/div[2]/table/tbody/tr"));
//
//			if (rows.isEmpty()) {
//				System.out.println("Status : Failed - No rows found in the table");
//				return;
//			}
//
//			int randomIndex1 = rand.nextInt(rows.size());
//			rows.get(randomIndex1).click();
//
//			System.out.println("Status : Passed");
//
//		} catch (Exception e) {
//			System.out.println("Status : Failed");
//		}
//
//		Thread.sleep(2500);

	public void VariantDetails() throws InterruptedException {

		Thread.sleep(4000);

		try {

			driver.findElement(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")).click();

			Thread.sleep(4000);

			Random rand = new Random();

			// Click Next button randomly (if enabled)
			List<WebElement> nextButtons = driver
					.findElements(By.xpath("//button[@type='button' and @aria-label='Next']"));

			if (!nextButtons.isEmpty() && nextButtons.get(0).isEnabled()) {
				nextButtons.get(0).click();
				Thread.sleep(3000);
			}

			// Get all rows from the current page
			List<WebElement> rows = driver
					.findElements(By.xpath("/html/body/div[5]/div[3]/div[2]/div/div/div[2]/div[2]/table/tbody/tr"));

			if (rows.isEmpty()) {
				System.out.println("Status : Failed - No rows found in the table");
				return;
			}

			// Select a random row/seller
			int randomIndex = rand.nextInt(rows.size());
			WebElement randomRow = rows.get(randomIndex);

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomRow);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomRow);

			System.out.println("Status : Passed");

		} catch (Exception e) {
			System.out.println("Status : Failed");
			e.printStackTrace();
		}

		Thread.sleep(2500);

//				WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//
//				Thread.sleep(500); 
//
//				WebElement submitButton1 = wait1.until(ExpectedConditions.elementToBeClickable(
//				    By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));
//
//			
//				try {
//				    submitButton1.click();
//				} catch (ElementClickInterceptedException e) {
//				    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton1);
//				}
//				
//		       Thread.sleep(1000);
//
//				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				System.out.println("Status Failed: " + "Check if the product can be created without Variant UOM(mandatory)");
//
//				Thread.sleep(2000);

		WebElement dropdownElement = driver.findElement(By.name("variantUOM"));
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdownElement);

		Thread.sleep(1000);

		Select dropdown = new Select(dropdownElement);

		List<WebElement> uomOptions = dropdown.getOptions();

		if (uomOptions.size() > 1) {
			Random uomRand1 = new Random();
			int randomUom = 1 + uomRand1.nextInt(uomOptions.size() - 1);

			dropdown.selectByIndex(randomUom);

			if (!dropdown.getFirstSelectedOption().getText().equals("Select")) {
				System.out.println("Status : Passed");
			} else {
				System.out.println("Status Failed: UOM selection did not change from default.");
			}
		} else {
			System.out.println("Status Failed: No UOM options available to select.");
		}

		Thread.sleep(2000);

//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//				submitButton1.click();
//				Thread.sleep(2000);
//
//				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				System.out.println("Status Failed: " + "Check if the product can be created without Variant Weight(mandatory)");
//
//				Thread.sleep(2000);

		WebElement weightCO2 = driver.findElement(By.name("variantWeight")); // Random Weight Co2

		Random rand2 = new Random();
		int randomNumber = 1 + rand2.nextInt(5);

		weightCO2.sendKeys(String.valueOf(randomNumber));

		System.out.println("Status : passed");

		Thread.sleep(3000);

//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//				submitButton1.click();
//				Thread.sleep(2000);
//
//				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				System.out.println("Status Failed: " + "Check if the product can be created without Variant Purchase Price(mandatory)");
//
//				Thread.sleep(2000);

		WebElement purPrice = driver.findElement(By.name("variantPurchasePrice")); // range from 10-15 purchase price

		Random rand3 = new Random();
		int randomPurPrice = 10 + rand3.nextInt(6);

		purPrice.sendKeys(String.valueOf(randomPurPrice));

		System.out.println("Status : passed");

		Thread.sleep(3000);

//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//				submitButton1.click();
//				Thread.sleep(2000);
//
//				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				System.out.println("Status Failed: " + "Check if the product can be created without Variant Selling Price(mandatory)");
//
//				Thread.sleep(2000);	    

		WebElement sellPrice = driver.findElement(By.name("variantSellingPrice")); // range from 15-20 purchase price

		Random rand4 = new Random();
		int randomSellPrice = 15 + rand4.nextInt(6);

		sellPrice.sendKeys(String.valueOf(randomSellPrice));

		System.out.println("Status : Passed");

		Thread.sleep(2000);

//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//				submitButton1.click();
//				Thread.sleep(2000);
//
//				driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//				System.out.println("Status Failed: " + "Check if the product can be created without Variant Stock Available(mandatory)");
//
//				Thread.sleep(2000);	

		WebElement stockAva = driver.findElement(By.name("varianStockAvl")); // range from 40 - 100 Available stock

		Random rand5 = new Random();
		int randomStock = 40 + rand5.nextInt(51);

		stockAva.sendKeys(String.valueOf(randomStock));

		Thread.sleep(1000);

		System.out.println("Status : Passed");

		WebElement MOQ = driver.findElement(By.name("variantMOQ"));
		MOQ.sendKeys("1");

		Thread.sleep(1000);

		WebElement incValue = driver.findElement(By.name("variantIncValue"));
		incValue.sendKeys("1");

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()[contains(., 'Save')]]")).click();

		Thread.sleep(1000);

//		       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
//				submitButton1.click();

	}

	public void SubmitButton() throws InterruptedException {

		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(25));

		WebElement submitButton1 = wait2.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));

		try {
			submitButton1.click();
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton1);
		}

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
	}

	// --------------------------Product created
	// successfully----------------------------

	// -------------------------Change Product status--------------------------

//			By changestatusdropdown  =By.xpath("(//select[@class='grid-select'])[2]");

//	public void changeStatusToInitiated() {
//
//	    By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[6]");
//
//	    By searchBoxLocator = By.xpath("//select[@class='grid-select alt-down-arrow']");
//
//	    wait.until(ExpectedConditions.elementToBeClickable(filterIcon)).click();
//
//	    WebElement statusDropdown = wait.until(
//	            ExpectedConditions.elementToBeClickable(searchBoxLocator));
//
//	    statusDropdown.click();
//
//	    Select select = new Select(statusDropdown);
//	    select.selectByVisibleText("Initiated");
//	}

//		public void ClickStatusdropdown() throws InterruptedException {   
//			    	   Thread.sleep(3000);	       
//					WebElement status = driver.findElement(changestatusdropdown);
//
//					Select statusSelect = new Select(status);
//
//					statusSelect.selectByVisibleText("Initiated");
//		}

	public void ViewProduct() throws InterruptedException {

		Thread.sleep(2500);
		driver.get("https://mktadmin.freshontable.com/product/viewProducts");

	}

	public void StatusCheckInitiated() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement statusElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]//td[10]")));

		String actualStatus = statusElement.getText().trim();

		System.out.println("Product Status: " + actualStatus);

		if (actualStatus.equalsIgnoreCase("Initiated")) {
			System.out.println("Status verified successfully.");
		} else {
			throw new AssertionError("Expected Status: Initiated, but found: " + actualStatus);
		}
	}

	public void clickmorebutton() throws InterruptedException {

		By morebutton = By
				.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]");
		Thread.sleep(2000);

		driver.findElement(morebutton).click();
		Thread.sleep(2000);

	}

	public void StatusChange() throws InterruptedException {

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='product-variant-dropdown dropdown']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//a[text()='Change Status'])[2]")).click();
		Thread.sleep(2000);

		WebElement changeStatus = driver.findElement(By.name("selectedStatus"));

		Select statusSelect1 = new Select(changeStatus);

		statusSelect1.selectByVisibleText("Active");

		Thread.sleep(2000);

	}

	public void Submit() throws InterruptedException {

		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
	}

	public void StatusCheckActive() throws InterruptedException {

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
}
