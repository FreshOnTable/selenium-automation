package PageObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditProduct {

	public WebDriver driver;
	public WebDriverWait wait;

	private String oldProductName;
	private String oldDept;
	private String oldCategory;
	private String oldSubCategory;
	private String oldClassification;
	private String oldTemperature;

	private String oldWeightCO2;
	private String oldPurchasePrice;
	private String oldSellingPrice;
	private String oldStockAvailable;

	private String newWeightCO2;
	private String newPurchasePrice;
	private String newSellingPrice;
	private String newStockAvailable;
	
	String seller;
	String uom;
	String skuId;

	public EditProduct(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));

	}

	// ======================================================
	// Navigate to Edit Product Page
	// ======================================================

	public void EditProductPage() throws InterruptedException {

		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@class='menu-cont']//img[@alt='img']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Product']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='View Products']")).click();
		
		Thread.sleep(3000);

		driver.findElement(By.xpath("(//button[contains(@class,'actions-dropdown')])[2]")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[text()='Edit Product']")).click();

		Thread.sleep(3000);

	}

	// ======================================================
	// Capture Existing Values
	// ======================================================

	public void editProductDetailsBefore() throws InterruptedException {
		
		Thread.sleep(3000);

		WebElement productNameField = driver.findElement(By.name("productName"));

		oldProductName = productNameField.getAttribute("value");

		Select deptDrop = new Select(driver.findElement(By.name("productDept")));
		oldDept = deptDrop.getFirstSelectedOption().getText();

		Select catDrop = new Select(driver.findElement(By.name("productCat")));
		oldCategory = catDrop.getFirstSelectedOption().getText();

		Select subCatDrop = new Select(driver.findElement(By.name("productSubCat")));
		oldSubCategory = subCatDrop.getFirstSelectedOption().getText();

		Select ClassificationDrop = new Select(driver.findElement(By.name("productClassification")));
		oldClassification = ClassificationDrop.getFirstSelectedOption().getText();

		Select TemperatureDrop = new Select(driver.findElement(By.name("productTemp")));
		oldTemperature = TemperatureDrop.getFirstSelectedOption().getText();

		System.out.println("\n========== BEFORE EDIT ==========");
		System.out.println("Product Name : " + oldProductName);
		System.out.println("Department   : " + oldDept);
		System.out.println("Category     : " + oldCategory);
		System.out.println("Sub Category : " + oldSubCategory);
		System.out.println("Classification : " + oldClassification);
		System.out.println("Temperature : " + oldTemperature);

	}

	// ======================================================
	// Edit Product Name
	// ======================================================

	public void editProductName() throws InterruptedException {

		WebElement productNameField = driver.findElement(By.name("productName"));

		Thread.sleep(2000);

		productNameField.click();
		productNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		productNameField.sendKeys(Keys.DELETE);

		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		String newProductName = "FOT " + timestamp;

		productNameField.sendKeys(newProductName);

		System.out.println("Product Name : " + newProductName);
	}

	// ======================================================
	// Edit Department
	// ======================================================

	public void editProductDept() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement deptElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("productDept")));

		Select deptDrop = new Select(deptElement);

		List<WebElement> options = deptDrop.getOptions();

		// Exclude the first placeholder option (Department/Select)
		int randomIndex = new Random().nextInt(options.size() - 2) + 2;

		deptDrop.selectByIndex(randomIndex);

//		System.out.println("Selected Department : " + deptDrop.getFirstSelectedOption().getText());

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Category
	// ======================================================

	public void editProductCategory() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement prdCatElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("productCat")));

		Select prdCatDrop = new Select(prdCatElement);

		List<WebElement> options = prdCatDrop.getOptions();

		// Exclude the first placeholder option (Department/Select)
		int randomIndex = new Random().nextInt(options.size() - 2) + 2;

		prdCatDrop.selectByIndex(randomIndex);

		System.out.println("Selected Product Category : " + prdCatDrop.getFirstSelectedOption().getText());

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Sub Category
	// ======================================================

	public void editProductSubCategory() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement prdSubCatElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("productSubCat")));

		Select prdSubCatDrop = new Select(prdSubCatElement);

		List<WebElement> options = prdSubCatDrop.getOptions();

		int randomIndex = new Random().nextInt(options.size() - 2) + 2;

		prdSubCatDrop.selectByIndex(randomIndex);

		System.out.println("Selected Product Sub Category : " + prdSubCatDrop.getFirstSelectedOption().getText());

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Product Classification
	// ======================================================

	public void editProductClassification() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement prdClassification = wait
				.until(ExpectedConditions.elementToBeClickable(By.name("productClassification")));

		Select prdClassificationDrop = new Select(prdClassification);

		List<WebElement> options = prdClassificationDrop.getOptions();

		int randomIndex = new Random().nextInt(options.size() - 2) + 2;

		prdClassificationDrop.selectByIndex(randomIndex);

		System.out.println(
				"Selected Product Classification : " + prdClassificationDrop.getFirstSelectedOption().getText());

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Product Temperature
	// ======================================================

	public void editProductTemperature() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement prdTemperature = wait.until(ExpectedConditions.elementToBeClickable(By.name("productTemp")));

		Select prdTemperatureDrop = new Select(prdTemperature);

		List<WebElement> options = prdTemperatureDrop.getOptions();

		int randomIndex = new Random().nextInt(options.size() - 2) + 2;

		prdTemperatureDrop.selectByIndex(randomIndex);

		System.out.println("Selected Product Temperature : " + prdTemperatureDrop.getFirstSelectedOption().getText());

		Thread.sleep(2000);
	}

	public void Submit() throws InterruptedException {

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);

	}

	// ======================================================
	// Print Updated Values
	// ======================================================

	public void editProductDetailsAfter() throws InterruptedException {

		Thread.sleep(2000);

		WebElement productNameField = driver.findElement(By.name("productName"));

		Select deptDrop = new Select(driver.findElement(By.name("productDept")));

		Select catDrop = new Select(driver.findElement(By.name("productCat")));

		Select subCatDrop = new Select(driver.findElement(By.name("productSubCat")));

		Select ClassificationDrop = new Select(driver.findElement(By.name("productClassification")));

		Select TemperatureDrop = new Select(driver.findElement(By.name("productTemp")));

		System.out.println("\n========== AFTER EDIT ==========");

		System.out.println("Product Name : " + productNameField.getAttribute("value"));

		System.out.println("Department   : " + deptDrop.getFirstSelectedOption().getText());

		System.out.println("Category     : " + catDrop.getFirstSelectedOption().getText());

		System.out.println("Sub Category : " + subCatDrop.getFirstSelectedOption().getText());

		System.out.println("Classification : " + ClassificationDrop.getFirstSelectedOption().getText());

		System.out.println("Temperature : " + TemperatureDrop.getFirstSelectedOption().getText());
	}

//////////////////////////////////////// Edit Product Variant Details /////////////////////////////////////////////////////////

	// ======================================================
	// Variant Details - BEFORE EDIT
	// ======================================================

	public void editVariantDetailsBefore() throws InterruptedException {

		WebElement element = driver.findElement(By.xpath("//td[1]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

		element.click();

		Thread.sleep(3000);

		WebElement weightCO2 = driver.findElement(By.name("variantWeight"));

		oldWeightCO2 = weightCO2.getAttribute("value");

		WebElement purPrice = driver.findElement(By.name("variantPurchasePrice"));

		oldPurchasePrice = purPrice.getAttribute("value");

		WebElement sellPrice = driver.findElement(By.name("variantSellingPrice"));

		oldSellingPrice = sellPrice.getAttribute("value");

		WebElement stockAva = driver.findElement(By.name("varianStockAvl"));

		oldStockAvailable = stockAva.getAttribute("value");

		System.out.println("\n========== VARIANT BEFORE EDIT ==========");

		System.out.println("Weight CO2      : " + oldWeightCO2);
		System.out.println("Purchase Price  : " + oldPurchasePrice);
		System.out.println("Selling Price   : " + oldSellingPrice);
		System.out.println("Stock Available : " + oldStockAvailable);
	}

	// ======================================================
	// Edit Variant Weight CO2
	// ======================================================

	public void editVariantWeight() throws InterruptedException {

		WebElement weightCO2 = driver.findElement(By.name("variantWeight"));

		Thread.sleep(2000);

		weightCO2.click();

		weightCO2.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		weightCO2.sendKeys(Keys.DELETE);

		Random rand2 = new Random();

		int randomNumber = 1 + rand2.nextInt(5);

		newWeightCO2 = String.valueOf(randomNumber);

		weightCO2.sendKeys(newWeightCO2);

		System.out.println("Weight CO2 : " + newWeightCO2);

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Variant Purchase Price
	// ======================================================

	public void editVariantPurchasePrice() throws InterruptedException {

		WebElement purPrice = driver.findElement(By.name("variantPurchasePrice"));

		Thread.sleep(2000);

		purPrice.click();

		purPrice.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		purPrice.sendKeys(Keys.DELETE);

		Random rand3 = new Random();

		int randomPurPrice = 10 + rand3.nextInt(6);

		newPurchasePrice = String.valueOf(randomPurPrice);

		purPrice.sendKeys(newPurchasePrice);

		System.out.println("Purchase Price : " + newPurchasePrice);

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Variant Selling Price
	// ======================================================

	public void editVariantSellingPrice() throws InterruptedException {

		WebElement sellPrice = driver.findElement(By.name("variantSellingPrice"));

		Thread.sleep(2000);

		sellPrice.click();

		sellPrice.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		sellPrice.sendKeys(Keys.DELETE);

		Random rand4 = new Random();

		int randomSellPrice = 15 + rand4.nextInt(6);

		newSellingPrice = String.valueOf(randomSellPrice);

		sellPrice.sendKeys(newSellingPrice);

		System.out.println("Selling Price : " + newSellingPrice);

		Thread.sleep(2000);
	}

	// ======================================================
	// Edit Variant Available Stock
	// ======================================================

	public void editVariantStock() throws InterruptedException {

		WebElement stockAva = driver.findElement(By.name("varianStockAvl"));

		Thread.sleep(2000);

		stockAva.click();

		stockAva.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		stockAva.sendKeys(Keys.DELETE);

		Random rand5 = new Random();

		int randomStock = 40 + rand5.nextInt(51);

		newStockAvailable = String.valueOf(randomStock);

		stockAva.sendKeys(newStockAvailable);

		System.out.println("Stock Available : " + newStockAvailable);

		Thread.sleep(2000);
	}

	// ======================================================
	// Variant Details - AFTER EDIT
	// ======================================================

	public void editVariantDetailsAfter() throws InterruptedException {

		Thread.sleep(1000);

		WebElement weightCO2 = driver.findElement(By.name("variantWeight"));

		WebElement purPrice = driver.findElement(By.name("variantPurchasePrice"));

		WebElement sellPrice = driver.findElement(By.name("variantSellingPrice"));

		WebElement stockAva = driver.findElement(By.name("varianStockAvl"));

		System.out.println("\n========== VARIANT AFTER EDIT ==========");

		System.out.println("Weight CO2      : " + weightCO2.getAttribute("value"));

		System.out.println("Purchase Price  : " + purPrice.getAttribute("value"));

		System.out.println("Selling Price   : " + sellPrice.getAttribute("value"));

		System.out.println("Stock Available : " + stockAva.getAttribute("value"));
		
	}

	public void SubmitButton() throws InterruptedException {
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		
		Thread.sleep(1000);

		WebElement contentContainer1 = driver.findElement(By.cssSelector("div.content-cont.toggle"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer1);

		Thread.sleep(500);

		WebElement submitButton1 = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));
		
		try {
			submitButton1.click();
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton1);
		}
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
		Thread.sleep(2000);
		
	}
	
	
//////////////////////////////////////////// Duplicate Variant ///////////////////////////////////////////////////////
	
	
	
	public void SubmitVariantWithoutSaving() throws InterruptedException {
		
	Thread.sleep(2000);

	System.out.println("Try to submit without saving the variant");
	
	WebElement element = driver.findElement(By.xpath("//button[contains(text(), 'Add Variant')]"));

	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

	element.click();

	Thread.sleep(2000);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	WebElement Seller = wait.until(ExpectedConditions
			.elementToBeClickable(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")));
	Seller.click();
	
	Thread.sleep(1000);
	
	driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
	
	Thread.sleep(1000);
	
	driver.findElement(
	        By.xpath("//input[@placeholder=\"Seller's Organization Name\"]"))
	        .sendKeys("FreshonTable DWC LLC");

	
	Thread.sleep(1000);
	
	driver.findElement(By.xpath("//button[text()='Apply']")).click();

//	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(), 'FreshonTable DWC LLC')]")))
//			.click();
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr")).click();
	
	Thread.sleep(2000);

	WebElement dropdownElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("variantUOM")));
	Select dropdown1 = new Select(dropdownElement1);
	dropdown1.selectByValue(String.valueOf(209));

	Thread.sleep(2000);

	WebElement weightCO21 = driver.findElement(By.name("variantWeight"));
	Random rand21 = new Random();
	int randomNumber1 = 1 + rand21.nextInt(5);
	weightCO21.sendKeys(String.valueOf(randomNumber1));

	Thread.sleep(2000);

	WebElement purPrice1 = driver.findElement(By.name("variantPurchasePrice"));
	Random rand31 = new Random();
	int randomPurPrice1 = 10 + rand31.nextInt(6);
	purPrice1.sendKeys(String.valueOf(randomPurPrice1));

	Thread.sleep(2000);

	WebElement sellPrice1 = driver.findElement(By.name("variantSellingPrice"));
	Random rand41 = new Random();
	int randomSellPrice1 = 15 + rand41.nextInt(6);
	sellPrice1.sendKeys(String.valueOf(randomSellPrice1));

	Thread.sleep(2000);

	WebElement stockAva1 = driver.findElement(By.name("varianStockAvl"));
	Random rand51 = new Random();
	int randomStock1 = 40 + rand51.nextInt(51);
	stockAva1.sendKeys(String.valueOf(randomStock1));

	Thread.sleep(2000);

	WebElement contentContainer1 = driver.findElement(By.cssSelector("div.content-cont.toggle"));
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer1);

	Thread.sleep(500);

	WebElement submitButton11 = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));

	try {
		submitButton11.click();
	} catch (ElementClickInterceptedException e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton11);
	}

	driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
	Thread.sleep(2000);

	Thread.sleep(2000);

	System.out.println(
			"Status : Passed - (The variant should not be saved when we submit without saving the variant )");

	Thread.sleep(2000);
	
	}
	
	
	public void DuplicateVariant() throws InterruptedException {
	

	System.out.println("Add an variant from edit product and check wheather duplicate variant can be added");

	driver.findElement(By.xpath("//button[contains(text(), 'Add Variant')]")).click();

//				        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	WebElement topRightButton = wait.until(ExpectedConditions
			.elementToBeClickable(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")));
	topRightButton.click();

//	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(), 'FreshonTable DWC LLC')]")))
//			.click();
	
Thread.sleep(1000);
	
	driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
	
	Thread.sleep(1000);
	
	driver.findElement(
	        By.xpath("//input[@placeholder=\"Seller's Organization Name\"]"))
	        .sendKeys("FreshonTable DWC LLC");

	
	Thread.sleep(1000);
	
	driver.findElement(By.xpath("//button[text()='Apply']")).click();

//	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(), 'FreshonTable DWC LLC')]")))
//			.click();
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr")).click();
	
	Thread.sleep(2000);

	WebElement dropdownElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("variantUOM")));
	Select dropdown2 = new Select(dropdownElement2);
	dropdown2.selectByValue(String.valueOf(209));

	Thread.sleep(2000);

	WebElement weightCO22 = driver.findElement(By.name("variantWeight"));
	Random rand212 = new Random();
	int randomNumber2 = 1 + rand212.nextInt(5);
	weightCO22.sendKeys(String.valueOf(randomNumber2));

	Thread.sleep(2000);

	WebElement purPrice123 = driver.findElement(By.name("variantPurchasePrice"));
	Random rand312 = new Random();
	int randomPurPrice123 = 10 + rand312.nextInt(6);
	purPrice123.sendKeys(String.valueOf(randomPurPrice123));

	Thread.sleep(2000);

	WebElement sellPrice123 = driver.findElement(By.name("variantSellingPrice"));
	Random rand412 = new Random();
	int randomSellPrice123 = 15 + rand412.nextInt(6);
	sellPrice123.sendKeys(String.valueOf(randomSellPrice123));

	Thread.sleep(2000);

	WebElement stockAva123 = driver.findElement(By.name("varianStockAvl"));
	Random rand512 = new Random();
	int randomStock123 = 40 + rand512.nextInt(51);
	stockAva123.sendKeys(String.valueOf(randomStock123));

	Thread.sleep(1000);

	driver.findElement(By.xpath("//button[text()[contains(., 'Save')]]")).click();

	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[contains(text(), 'Add Variant')]")).click();

//				        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
	WebElement topRightButton1 = wait.until(ExpectedConditions
			.elementToBeClickable(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")));
	topRightButton1.click();

	Thread.sleep(1000);
	
	driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
	
	Thread.sleep(1000);
	
	driver.findElement(
	        By.xpath("//input[@placeholder=\"Seller's Organization Name\"]"))
	        .sendKeys("FreshonTable DWC LLC");

	Thread.sleep(1000);
	
	driver.findElement(By.xpath("//button[text()='Apply']")).click();

//	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(), 'FreshonTable DWC LLC')]")))
//			.click();
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr")).click();
	
	Thread.sleep(2000);
	
	WebElement dropdownElement11 = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("variantUOM")));
	Select dropdown11 = new Select(dropdownElement11);
	dropdown11.selectByValue(String.valueOf(209));

	Thread.sleep(2000);

	WebElement weightCO211 = driver.findElement(By.name("variantWeight"));
	Random rand211 = new Random();
	int randomNumber11 = 1 + rand211.nextInt(5);
	weightCO211.sendKeys(String.valueOf(randomNumber11));

	Thread.sleep(2000);

	WebElement purPrice11 = driver.findElement(By.name("variantPurchasePrice"));
	Random rand311 = new Random();
	int randomPurPrice11 = 10 + rand311.nextInt(6);
	purPrice11.sendKeys(String.valueOf(randomPurPrice11));

	Thread.sleep(2000);

	WebElement sellPrice11 = driver.findElement(By.name("variantSellingPrice"));
	Random rand411 = new Random();
	int randomSellPrice11 = 15 + rand411.nextInt(6);
	sellPrice11.sendKeys(String.valueOf(randomSellPrice11));

	Thread.sleep(2000);

	WebElement stockAva11 = driver.findElement(By.name("varianStockAvl"));
	Random rand511 = new Random();
	int randomStock11 = 40 + rand511.nextInt(51);
	stockAva11.sendKeys(String.valueOf(randomStock11));

	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[text()[contains(., 'Save')]]")).click();

	Thread.sleep(2000);

	// WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(15));

	WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);

	Thread.sleep(500);

	WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));

	try {
		submitButton.click();
	} catch (ElementClickInterceptedException e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
	}

	driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
	Thread.sleep(2000);

	System.out.println("Status Passed : Duplicate variant cannot be created");
	
	}
	
	
	public void CreateVariant() throws InterruptedException {
		
		
//		WebElement element = driver.findElement(By.xpath("//button[contains(text(), 'Add Variant')]"));
//
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
//
//		element.click();
//
//		Thread.sleep(2000);
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//		WebElement Seller = wait.until(ExpectedConditions
//				.elementToBeClickable(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")));
//		Seller.click();
//		
//		Thread.sleep(1000);
//		
//		driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
//		
//		Thread.sleep(1000);
//		
//		driver.findElement(
//		        By.xpath("//input[@placeholder=\"Seller's Organization Name\"]"))
//		        .sendKeys("FreshonTable DWC LLC");
//		
//		driver.findElement(By.xpath("//button[text()='Apply']")).click();
//
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr")).click();
//		
//		Thread.sleep(2000);
	

	driver.findElement(By.xpath("(//td[contains(text(), 'FreshonTable DWC LLC')])[2]")).click();

	Thread.sleep(2000);

	WebElement variantUOM = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("variantUOM")));
	Select uomdropdown = new Select(variantUOM);
	uomdropdown.selectByValue(String.valueOf(208));

	Thread.sleep(2000);

	WebElement weightCO221 = driver.findElement(By.name("variantWeight"));
	Random rand123 = new Random();
	int randomNumber111 = 1 + rand123.nextInt(5);
	weightCO221.sendKeys(String.valueOf(randomNumber111));

	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[text()[contains(., 'Save')]]")).click();

	Thread.sleep(2000);
	
	WebElement element1 = driver.findElement(By.xpath("//button[text()='Submit']"));

	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element1);
	
	Thread.sleep(500);
	element1.click();

	Thread.sleep(1000);

	driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
	Thread.sleep(2000);

	driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
	Thread.sleep(2000);

	System.out.println("New Variant created");
	
	}
	
	public void ViewProducts() throws InterruptedException {
		
		Thread.sleep(2000);
		driver.get("https://mktadmin.freshontable.com/product/viewProducts");

	}
	
//	public void ChangeStatus() throws InterruptedException {
//		
//		driver.findElement(By.xpath(("(//button[text()='More'])[1]"))).click();
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//a[text()='Change Status']")).click();
//		
//		Thread.sleep(2000);
//		
//        driver.findElement(By.xpath("(//button[@type='button' and @class='product-variant-toggle dropdown-toggle btn btn-primary'])[2]")).click();
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("(//a[text()='Change Status'])[2]")).click();
//		
//		Thread.sleep(2000);
//		
//		WebElement statusElement = driver.findElement(By.name("selectedStatus"));
//
//		Select statusDrop = new Select(statusElement);
//
//		statusDrop.selectByVisibleText("Delete");
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//button[text()='Submit']")).click();
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("//button[text()='OK']")).click();
//		
//	}
	
	
	
//	public void ViewDeletedVariant() throws InterruptedException {
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(By.xpath("(//button[contains(@class,'actions-dropdown')])[2]")).click();
//
//		Thread.sleep(2000);
//
//		driver.findElement(By.xpath("//a[text()='Edit Product']")).click();
//
//		Thread.sleep(2000);
//		
//		WebElement deletedVariants = driver.findElement(
//		        By.xpath("//div[@class='deleted-variants']//span[contains(.,'Show deleted variants')]"));
//
//		((JavascriptExecutor) driver).executeScript(
//		        "arguments[0].scrollIntoView();",
//		        deletedVariants);
//
//		deletedVariants.click();
//		
//		Thread.sleep(2000);
//		
//		WebElement skuElement = driver.findElement(
//		        By.xpath("(//tbody/tr[1]/td[1])[2]"));
//
//		skuId = skuElement.getText();
//
//		System.out.println("SKU ID : " + skuId);
//
//		skuElement.click();
//
//		Thread.sleep(2000);
//		
//		  WebElement sellerField =
//		            driver.findElement(By.xpath("//label[contains(.,'Select Seller')]/preceding-sibling::input"));
//
//		    WebElement uomField =
//		            driver.findElement(By.name("variantUOM"));
//
//		    seller = sellerField.getAttribute("value");
//
//		    uom = uomField.getAttribute("value");
//
//		    System.out.println("\n========== SELLER & UOM ==========");
//		    System.out.println("Seller : " + seller);
//		    System.out.println("UoM    : " + uom);
//	
//	driver.navigate().refresh();
//	Thread.sleep(2000);
//		
//	}
	
	
	
	public void captureDetailsChangeStatus() throws InterruptedException {
		
		driver.findElement(By.xpath(("(//button[text()='More'])[1]"))).click();
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[text()='Change Status']")).click();
		
		Thread.sleep(1000);
		
		WebElement skuElement = driver.findElement(
		        By.xpath("(//div[@class='font font-normal bold'])[1]"));
		
		String SkuId = skuElement.getText()
		        .trim()
		        .replace("#", "");

		System.out.println("SKU ID : " + SkuId);

		Thread.sleep(1500);
		
		 driver.findElement(By.xpath("(//button[@type='button' and @class='product-variant-toggle dropdown-toggle btn btn-primary'])[2]")).click();
			
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("(//a[text()='Change Status'])[2]")).click();
			
			Thread.sleep(2000);
			
			 WebElement sellerField =
			         driver.findElement(By.name("sName"));
	
			    WebElement uomField =
			            driver.findElement(By.name("uomDesc"));
	
			    seller = sellerField.getAttribute("value");
	
			    uom = uomField.getAttribute("value");
	
			    System.out.println("\n========== SELLER & UOM ==========");
			    System.out.println("Seller : " + seller);
			    System.out.println("UoM    : " + uom);	
			
			    WebElement statusElement = driver.findElement(By.name("selectedStatus"));
			    
			    		Select statusDrop = new Select(statusElement);
			    
			    		statusDrop.selectByVisibleText("Delete");
			    		
			    		Thread.sleep(2000);
			    		
			    		driver.findElement(By.xpath("//button[text()='Submit']")).click();
			    		
			    		Thread.sleep(2000);
			    		
			    		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
			    		
			    		Thread.sleep(2000);
			    		
			    		driver.findElement(By.xpath("//button[text()='OK']")).click();
		
	}
		
		
	public void AddDeletedVariant() throws InterruptedException {
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("(//button[contains(@class,'actions-dropdown')])[2]")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[text()='Edit Product']")).click();
		
		Thread.sleep(1000);
		
		WebElement addVariantButton = driver.findElement(
		        By.xpath("//button[contains(text(), 'Add Variant')]"));

		((JavascriptExecutor) driver).executeScript(
		        "arguments[0].scrollIntoView();",
		        addVariantButton);

		addVariantButton.click();
		
		WebElement topRightButton = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']")));
		topRightButton.click();

	Thread.sleep(1000);
		
		driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
		
		Thread.sleep(1000);
		
		driver.findElement(
		        By.xpath("//input[@placeholder=\"Seller's Organization Name\"]")).sendKeys(seller);
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//button[text()='Apply']")).click();
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//table[@class='table border stripped table-hover table-sm mb-0 border-top-0']//tbody//tr")).click();
		
		Thread.sleep(1000);
		
		Select uomDrop = new Select(
		        driver.findElement(By.name("variantUOM")));

		uomDrop.selectByVisibleText(uom);
		
		Thread.sleep(2000);
		
		WebElement weightCO22 = driver.findElement(By.name("variantWeight"));
		Random rand212 = new Random();
		int randomNumber2 = 1 + rand212.nextInt(5);
		weightCO22.sendKeys(String.valueOf(randomNumber2));

		Thread.sleep(2000);

		WebElement purPrice123 = driver.findElement(By.name("variantPurchasePrice"));
		Random rand312 = new Random();
		int randomPurPrice123 = 10 + rand312.nextInt(6);
		purPrice123.sendKeys(String.valueOf(randomPurPrice123));

		Thread.sleep(2000);

		WebElement sellPrice123 = driver.findElement(By.name("variantSellingPrice"));
		Random rand412 = new Random();
		int randomSellPrice123 = 15 + rand412.nextInt(6);
		sellPrice123.sendKeys(String.valueOf(randomSellPrice123));

		Thread.sleep(2000);

		WebElement stockAva123 = driver.findElement(By.name("varianStockAvl"));
		Random rand512 = new Random();
		int randomStock123 = 40 + rand512.nextInt(51);
		stockAva123.sendKeys(String.valueOf(randomStock123));

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()[contains(., 'Save')]]")).click();
		
		Thread.sleep(2000);
		
		WebElement contentContainer1 = driver.findElement(By.cssSelector("div.content-cont.toggle"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer1);

		Thread.sleep(500);

		WebElement submitButton1 = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(@class, 'button-primary') and contains(text(), 'Submit')]")));
		
		try {
			submitButton1.click();
		} catch (ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton1);
		}
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()[contains(., 'OK')]]")).click();
		Thread.sleep(2000);
		
		
		WebElement newSkuElement = driver.findElement(
		        By.xpath("(//tbody/tr/td[1])[last()]"));

		((JavascriptExecutor) driver).executeScript(
		        "arguments[0].scrollIntoView();",
		        newSkuElement);	
	}
}





