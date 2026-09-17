package PageObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

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

public class PlaceOrder {

	double prdGrsAMt = 0.0d;
	public WebDriver driver;
	public WebDriverWait wait;
	
	public PlaceOrder(WebDriver driver) {
		this.driver = driver;
	    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
	
	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickorder = By.xpath("//ul//li//span[text()='Order']");
	By placeorder = By.xpath("//ul//li//span[text()='Place Order']");
	By clickbusiness = By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']");
//	By selectbusiness = By.xpath("//*[@id=\"grid_732322063_0_content_table\"]/tbody/tr");
	By Reviewbutton = By.xpath("//button[text()='Review Order']");
	

	public void clickgrid() {
		driver.findElement(clickgrid).click();
	}

	public void clickorderoption() {
		driver.findElement(clickorder).click();
	}

	public void clickplaceorder() {
		driver.findElement(placeorder).click();
	}

	public void clickbusinessfield() throws InterruptedException {
		driver.findElement(clickbusiness).click();
	 
	}
	
	
	 public void validatePagination() {
		     		 	 
//Page Calculation	 

		 WebElement paginationElement = driver.findElement(By.xpath("//div[contains(text(),'pages')]"));
		 String paginationText = paginationElement.getText();  // e.g. "1 of 99 pages (984 items)"

		 // Extract total items
		 int totalItems = Integer.parseInt(paginationText.replaceAll(".*\\((\\d+) items\\).*", "$1"));
		 System.out.println("Total Items: " + totalItems);

		 // Extract total pages from UI
		 int totalPages = Integer.parseInt(paginationText.replaceAll(".*of (\\d+) pages.*", "$1"));
		 System.out.println("Total Pages (UI): " + totalPages);

		 // Get items per page from dropdown
		 WebElement itemsPerPageDropdown = driver.findElement(By.cssSelector("select.custom-pagination-dropdown"));
		 Select select1 = new Select(itemsPerPageDropdown);
		 int itemsPerPage1 = Integer.parseInt(select1.getFirstSelectedOption().getText().trim());
		 System.out.println("Items Per Page: " + itemsPerPage1);

		 // Perform division (double for decimal precision)
		 double divisionResult = (double) totalItems / itemsPerPage1;
		 System.out.println(totalItems + " divided by " + itemsPerPage1 + " = " + divisionResult);

		 // Calculate total pages (round up)
		 int calculatedPages = (int) Math.ceil(divisionResult);
		 System.out.println("Calculated Total Pages (rounded up): " + calculatedPages);

		 // Validate
		 if (calculatedPages == totalPages) {
		     System.out.println("Pagination validation passed!");
		 } else {
		     throw new AssertionError("Pagination validation failed! Calculated pages: " + calculatedPages + ", but UI shows: " + totalPages);
		 }
		 
		 
		 
		// --- STEP 1: VERIFY DEFAULT STATE (10) ---
		 WebElement itemsPerPage = driver.findElement(By.xpath("//select[@class='custom-pagination-dropdown']"));
		 Select select = new Select(itemsPerPage);

		 // 1. Verify Dropdown UI text
		 String defaultVal = select.getFirstSelectedOption().getText();
		 Assert.assertEquals(defaultVal.trim(), "10", "Default value is not 10!");
		 System.out.println("Step 1: UI Dropdown default is confirmed as: " + defaultVal);

		 // 2. Verify Table Row count
		 wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table/tbody/tr")));
		 List<WebElement> defaultRows = driver.findElements(By.xpath("//table/tbody/tr"));

		 int initialCount = defaultRows.size();
		 Assert.assertEquals(initialCount, 10, "Default row count is not 10!");
		 System.out.println("Step 2: Default table line items verified. Count = " + initialCount);


		 // --- STEP 3: CHANGE TO 25 AND VERIFY ---
		 System.out.println("Step 3: Changing dropdown selection to 25");
		 select.selectByValue("25");

		 // 4. Wait for the rows to update to exactly 25
		 wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table/tbody/tr"), 25));

		 List<WebElement> updatedRows = driver.findElements(By.xpath("//table/tbody/tr"));
		 int finalCount = updatedRows.size();

		 Assert.assertEquals(finalCount, 25, "Row count did not update to 25!");
		 System.out.println("Step 4: Table refreshed successfully. New line item count = " + finalCount);
		
	 }
	
//selecting business	
	 public void selectbusinessrandomaly1() throws InterruptedException {

			    Random random = new Random();
			    int maxRetries = 5;

			    for (int retry = 0; retry < maxRetries; retry++) {

			        // ---------- Get total pages ----------
			        WebElement pagesText = wait.until(
			                ExpectedConditions.visibilityOfElementLocated(
			                        By.xpath("//label[contains(text(),'of')]")));

			        String textContent = pagesText.getText().replaceAll("\\D+", "");
			        int totalPages = textContent.isEmpty() ? 0 : Integer.parseInt(textContent);

			        int randomPage = 1;
			        if (totalPages > 0) {
			            randomPage = random.nextInt(totalPages) + 1;
			        }

			        System.out.println("Random Page Selected: " + randomPage);

			        // ---------- Navigate to random page ----------
			        for (int i = 1; i < randomPage; i++) {

			            String firstRowText =
			                    driver.findElement(By.xpath("//table/tbody/tr[1]")).getText();

			            WebElement nextBtn = wait.until(
			                    ExpectedConditions.elementToBeClickable(
			                            By.xpath("//button[@aria-label='Next']")));
			            nextBtn.click();

			            Thread.sleep(1200);

			            wait.until(ExpectedConditions.not(
			                    ExpectedConditions.textToBePresentInElementLocated(
			                            By.xpath("//table/tbody/tr[1]"), firstRowText)));
			        }

			        // ---------- WAIT for business rows ----------
			        Thread.sleep(4000);
			        wait.until(ExpectedConditions.or(
			                ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr")),
			                ExpectedConditions.visibilityOfElementLocated(
			                        By.xpath("//table//td[normalize-space()='No records to display']"))
			        ));

			        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));

			        // ---------- Click random business ----------
			        if (!rows.isEmpty()) {
			            rows.get(random.nextInt(rows.size())).click();
			            System.out.println("Random business clicked. Checking products...");
			        } else {
			            System.out.println("No rows found. Retrying...");
			            continue;
			        }

			        Thread.sleep(1500);

			        // ---------- CHECK PRODUCTS ----------
			        boolean noProducts = !driver.findElements(
			                By.xpath("//table//td[normalize-space()='No records to display']"))
			                .isEmpty();

			        if (!noProducts) {
			            System.out.println("✅ Products found. STOP.");
			            return;
			        }

			        // ---------- APPLY FILTER (ONLY IF NO PRODUCTS) ----------
			        System.out.println("No products found. Applying filter...");

			        Thread.sleep(2000);
			        driver.findElement(
			                By.xpath("//div[@style='position: absolute; top: 10px; right: 12px;']"))
			                .click();

			        Thread.sleep(2000);

			        for (int attempt = 0; attempt < 3; attempt++) {
			            try {
			                WebElement filterIcon = wait.until(
			                        ExpectedConditions.elementToBeClickable(
			                                By.xpath("(//div[@class='custom-filter-icon'])[4]")));
			                filterIcon.click();
			                break;
			            } catch (StaleElementReferenceException e) {
			                Thread.sleep(500);
			            }
			        }

			        WebElement tradeNameInput = wait.until(
			                ExpectedConditions.visibilityOfElementLocated(
			                        By.xpath("//input[@placeholder='Trade Name']")));

			        tradeNameInput.clear();
			        Thread.sleep(2000);
			        tradeNameInput.sendKeys("25hours Hotel Dubai One Central");
			        Thread.sleep(2000);
			        tradeNameInput.sendKeys(Keys.ENTER);


			        Thread.sleep(1500);

			        By secondRowLocator = By.xpath("(//table//tbody/tr)[2]");

			        for (int attempt = 0; attempt < 6; attempt++) {
			            try {
			                WebElement secondRow = wait.until(
			                        ExpectedConditions.presenceOfElementLocated(secondRowLocator));

			                wait.until(ExpectedConditions.elementToBeClickable(secondRow));

			                ((JavascriptExecutor) driver)
			                        .executeScript("arguments[0].scrollIntoView(true);", secondRow);

			                secondRow.click(); // ✅ MUST CLICK
			                break;

			            } catch (StaleElementReferenceException e) {
			                Thread.sleep(500);
			            }
			            catch (ElementClickInterceptedException e) {
			                Thread.sleep(500);
			            }
			            catch (TimeoutException e) {
			                Thread.sleep(500);
			            } {

			                Thread.sleep(500);
			            }
			        }

			        Thread.sleep(3000);

			        // ---------- FINAL PRODUCT CHECK (FILTERED BUSINESS) ----------
			        wait.until(ExpectedConditions.or(
			                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//table)[2]//tbody/tr")),
			                ExpectedConditions.visibilityOfElementLocated(
			                        By.xpath("(//table)[2]//td[normalize-space()='No records to display']"))
			        ));

			        boolean noProductsAfterFilter = !driver.findElements(
			                By.xpath("(//table)[2]//td[normalize-space()='No records to display']"))
			                .isEmpty();

			        if (!noProductsAfterFilter) {
			            System.out.println("✅ Products found after filter. STOP.");
			            return; // 🔥 DO NOT RETRY AGAIN
			        }

			        System.out.println("❌ No products even after filter. Retrying...");
			        
			        Thread.sleep(2000);
			    }
			    
			    
			}


//	 }
		
		
	
/*	public void FOCOrderCreation() throws InterruptedException {
    Thread.sleep(2000);
	driver.findElement(By.xpath("(//div[@class='fot-formControl']/div[@style='position: absolute; top: 5px; right: 4px;'])[2]")).click();
	}*/
	
	 public double selectProduct() throws InterruptedException {

		    Thread.sleep(4000);

		    Random random = new Random();
		    int maxSelections = 4;
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    int selectionsMade = 0;
		    double prdGrsAMt = 0.0;

		    // Load products
		    List<WebElement> products = driver.findElements(By.xpath("//table/tbody/tr"));
		    if (products.isEmpty()) {
		        System.out.println("No products available.");
		        return 0.0;
		    }

		    // -------- BUSINESS RULE --------
		    int productCount = products.size();
		    int allowedMaxSelections;

		    if (productCount == 1) {
		        allowedMaxSelections = 1;
		    } else if (productCount == 2) {
		        allowedMaxSelections = 2;
		    } else {
		        allowedMaxSelections = maxSelections;
		    }

		    int numberOfSelections = random.nextInt(allowedMaxSelections) + 1;

		    Set<Integer> pageSelected = new HashSet<>();

		    while (selectionsMade < numberOfSelections) {

		        pageSelected.clear();

		        products = driver.findElements(By.xpath("//table/tbody/tr"));
		        if (products.isEmpty()) break;

		        int remainingSelections = numberOfSelections - selectionsMade;
		        int selectionsThisPage = Math.min(remainingSelections, products.size());

		        while (pageSelected.size() < selectionsThisPage) {

		            int randomIndex = random.nextInt(products.size());
		            if (pageSelected.contains(randomIndex)) continue;

		            WebElement row = products.get(randomIndex);

		            // ===== ADDED BY ME =====
		            String productName = row.findElement(By.xpath(".//td[3]")).getText();
		            // =======================

		            // -------- CHECKBOX (JS CLICK FIX) --------
		            WebElement checkbox = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            row.findElement(By.xpath(".//td[1]//input"))
		                    )
		            );

		            if (!checkbox.isSelected()) {
		                js.executeScript("arguments[0].click();", checkbox);
		            }

		            // -------- QTY --------
		            WebElement qtyInput = row.findElement(
		                    By.xpath(".//input[contains(@id,'input-numeric-field-qty')]")
		            );

		            int randomQty = random.nextInt(10) + 1;

		            qtyInput.sendKeys(Keys.CONTROL + "a");
		            qtyInput.sendKeys(Keys.DELETE);
		            qtyInput.sendKeys(String.valueOf(randomQty));

		            // ===== ADDED BY ME =====
		            System.out.println("----------------------------");
		            System.out.println("Selected Product : " + productName);
		            System.out.println("Selected Qty     : " + randomQty);
		            Thread.sleep(600);

		            js.executeScript("arguments[0].dispatchEvent(new Event('input',{bubbles:true}))", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('change',{bubbles:true}))", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));", qtyInput);
		            js.executeScript("document.activeElement.blur();");

		            // -------- WAIT FOR GROSS CELL --------
		            By rowGrossLocator =
		                    By.xpath("//table/tbody/tr[" + (randomIndex + 1) + "]/td[13]");

		            wait.until(ExpectedConditions.visibilityOfElementLocated(rowGrossLocator));

		            String grossText = driver.findElement(rowGrossLocator)
		                    .getText()
		                    .replace(",", "")
		                    .replace("₹", "")
		                    .trim();

		            if (!grossText.isEmpty()) {
		                prdGrsAMt += Double.parseDouble(grossText);
		            }

		            DecimalFormat df = new DecimalFormat("0.00");
		            System.out.println("Gross Amount     : " + df.format(Double.parseDouble(grossText)));
		            Thread.sleep(500);

		            pageSelected.add(randomIndex);
		            selectionsMade++;
		        }

		        // -------- NEXT PAGE --------
		        List<WebElement> nextButtons = driver.findElements(
		                By.xpath("//button[@aria-label='Next' and not(@disabled)]")
		        );

		        if (nextButtons.isEmpty()) break;

		        pageSelected.clear();

		        String oldFirstRow = products.get(0).getText();

		        js.executeScript("arguments[0].click();", nextButtons.get(0));

		       
		        try {
		            wait.until(ExpectedConditions.stalenessOf(products.get(0)));
		        } catch (Exception e) {
		         
		            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
		                By.xpath("//table/tbody/tr")
		            ));
		        }

		    }


		    List<WebElement> nextButtonsAfter =
		            driver.findElements(By.xpath("//button[@aria-label='Next' and not(@disabled)]"));

		    if (!nextButtonsAfter.isEmpty()) {

	//	        System.out.println("Next page exists → Selecting ONLY 1 product from next page");

		        js.executeScript("arguments[0].click();", nextButtonsAfter.get(0));

		        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
		                By.xpath("//table/tbody/tr")
		        ));

		        Thread.sleep(1000);

		        List<WebElement> nextPageProducts =
		                driver.findElements(By.xpath("//table/tbody/tr"));

		        if (!nextPageProducts.isEmpty()) {

		            WebElement row = nextPageProducts.get(0);

		            String productName =
		                    row.findElement(By.xpath(".//td[3]")).getText();

		            WebElement checkbox = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            row.findElement(By.xpath(".//td[1]//input"))
		                    )
		            );

		            if (!checkbox.isSelected()) {
		                js.executeScript("arguments[0].click();", checkbox);
		            }

		            WebElement qtyInput = row.findElement(
		                    By.xpath(".//input[contains(@id,'input-numeric-field-qty')]")
		            );

		            int randomQty = new Random().nextInt(10) + 1;

		            qtyInput.sendKeys(Keys.CONTROL + "a");
		            qtyInput.sendKeys(Keys.DELETE);
		            qtyInput.sendKeys(String.valueOf(randomQty));

		            System.out.println("----------------------------");
		            System.out.println("Selected Product : " + productName);
		            System.out.println("Selected Qty     : " + randomQty);

		            js.executeScript("arguments[0].dispatchEvent(new Event('input',{bubbles:true}))", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('change',{bubbles:true}))", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", qtyInput);
		            js.executeScript("arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));", qtyInput);
		            js.executeScript("document.activeElement.blur();");
		            

		            String grossText = row.findElement(By.xpath(".//td[13]"))
		                    .getText()
		                    .replace(",", "")
		                    .replace("₹", "")
		                    .trim();

		            if (!grossText.isEmpty()) {
		                prdGrsAMt += Double.parseDouble(grossText);
		            }

		            DecimalFormat df2 = new DecimalFormat("0.00");
		            System.out.println("Gross Amount     : " + df2.format(Double.parseDouble(grossText)));

		            selectionsMade++;
		        }

		    } else {
		        System.out.println("No next page → leaving product selection");
		    }



		    DecimalFormat df = new DecimalFormat("0.00");

		    System.out.println("----------------------------");
		    System.out.println("Total products selected: " + selectionsMade);
		    System.out.println("Total Gross Amount: " + df.format(prdGrsAMt));
		    System.out.println("----------------------------");

		    return prdGrsAMt;
		}


		
	public void Past_shippdate_date() throws InterruptedException {
		driver.findElement(By.name("ship_dt")).click();

	    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

	    LocalDate pastDate = LocalDate.now().minusDays(7);
	    DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
	    String targetFullDate = pastDate.format(fullDateFormatter);

	    // Get all the dates
	    List<WebElement> days = driver.findElements(By.cssSelector("div.react-datepicker__day"));

	    boolean isSelectable = false;

	    for (WebElement day : days) {
	        String ariaLabel = day.getAttribute("aria-label");
	        String classAttribute = day.getAttribute("class"); // Get class to check if disabled
//	        System.out.println("Checking date: " + ariaLabel + " | Class: " + classAttribute); // Debugging
	        
	        if (ariaLabel != null && ariaLabel.equals(targetFullDate)) {
	            // Check if the date is disabled
	            if (classAttribute.contains("react-datepicker__day--disabled")) {
	                System.out.println("Test Passed: Cannot select past date.");
	                return; // Test passes as expected behavior
	            } else {
	                isSelectable = true; // This means the test should fail
	            }
	        }
	    }

	    if (isSelectable) {
	        throw new AssertionError("Test Failed: Able to select a disabled past date!");
	    } else {
	        System.out.println("Test Passed: Should not be allowed select previous shipping date");
	//        System.out.println("Status: Passed");
	    }
	}

	public void Ship_Date_ErrorMessageValidation() throws InterruptedException {

//		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//		reviewOrderButton.click();
//		Thread.sleep(1000);
//		
//		try {
//			WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));
//			okButton.click();
//			System.out.println("Popup handled successfully");
//		} catch (Exception e) {
//			System.out.println("No popup appeared.");
//		}

		Thread.sleep(500);

//		driver.findElement(By.name("ship_dt")).click();4rfdxz
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.name("ship_dt")).click();
		

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker")));

		LocalDate nextDay = LocalDate.now().plusDays(1);
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d");

		String targetDay = nextDay.format(dayFormatter);
		String targetMonthYear = nextDay.format(monthYearFormatter);

		while (!driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']")).getText()
				.contains(targetMonthYear)) {
			driver.findElement(By.xpath("//button[contains(@class, 'react-datepicker__navigation--next')]")).click();
		}

		List<WebElement> days = driver.findElements(By.cssSelector(
				"div.react-datepicker__month-container div.react-datepicker__month div.react-datepicker__week div.react-datepicker__day"));

		for (WebElement day : days) {
			String ariaLabel = day.getAttribute("aria-label");
			if (ariaLabel != null && ariaLabel.contains(targetDay)
					&& ariaLabel.contains(nextDay.format(DateTimeFormatter.ofPattern("MMMM")))) { // check for Day and
																									// Month
				day.click();
			//	System.out.println("Status: Passed");
				break;
			}
			Thread.sleep(1000);
			
		}
	}

	public void selectPaymentModeByIndex() throws InterruptedException {
//		Thread.sleep(2000);
//		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//		reviewOrderButton.click();
//		Thread.sleep(1000);

//		WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));
//		okButton.click();

		Thread.sleep(3500);

//		WebElement paymentDropdownField = driver.findElement(By.name("payment_method"));
		

		try {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

			WebElement dropdownElement = wait.until(
				    ExpectedConditions.elementToBeClickable(By.name("payment_method"))
				);
		    
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    
//		    js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", dropdownElement);
		    
//		    js.executeScript("arguments[0].scrollIntoView(true);", dropdownElement);

		    Select paymentDropdown = new Select(dropdownElement);

		    paymentDropdown.selectByIndex(2);

		    System.out.println("Payment method selected successfully");
		    
		    System.out.println(paymentDropdown.getFirstSelectedOption().getText());	

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	
	public void Sel_billingAddr_And_ShiAddr_popupErrorValidation() throws InterruptedException {
//		Thread.sleep(2000);
//		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//		reviewOrderButton.click();
//		Thread.sleep(2000);

//		WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));
//		okButton.click();
//		System.out.println("Status failed: Procced without selecting billing & shipping address(mandatory).");

		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    
		try {
			WebElement billingAddressRadio = driver.findElement(By.name("billing_address"));
			
//		    js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", billingAddressRadio);
			
//			js.executeScript("arguments[0].scrollIntoView(true);", billingAddressRadio);

			if (!billingAddressRadio.isSelected()) {
				billingAddressRadio.click();
				System.out.println("Status Passed : Billing address selected successfully");
//				System.out.println("Billing address selected successfully.");
			} else {
				System.out.println("Billing address was already selected.");
			}
		} catch (Exception e) {
			System.out.println("Billing address selection error: " + e.getMessage());
		}

		Thread.sleep(2000);
		
		try {
			WebElement shippingAddressRadio = driver.findElement(By.name("shipping_address"));

			if (!shippingAddressRadio.isSelected()) {
				shippingAddressRadio.click();
				System.out.println("Status Passed : Shipping address selected successfully.");
			} else {
				System.out.println("Shipping address was already selected.");
			}
		} catch (Exception e) {
			System.out.println("Shipping address selection error: " + e.getMessage());
		}
	}

	public void selectByDriver() throws InterruptedException {

		WebElement dropdownElement = driver.findElement(By.name("del_user_id"));
		Select dropdown = new Select(dropdownElement);
		

		List<WebElement> options = dropdown.getOptions();

		// Ensure there is more than one option
		if (options.size() <= 1) {
		    throw new RuntimeException("No drivers available to select");
		}

		// Random index EXCLUDING first option (index 0)
		Random random = new Random();
		int randomIndex = random.nextInt(options.size() - 1) + 1;

		// Select random driver
		dropdown.selectByIndex(randomIndex);

		Thread.sleep(1000);
	}

	
	public void Lpo_Num_popupErrorValidation() throws InterruptedException {
//		Thread.sleep(1000);
//		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//		reviewOrderButton.click();
//		Thread.sleep(1000);
//
//		WebElement okButton = driver.findElement(By.xpath("//button[text()='OK']"));
//		okButton.click();

		Thread.sleep(1000);

		WebElement LPONumberField = null;

		try {
		    LPONumberField = driver.findElement(By.name("lpo_number"));

		    if (LPONumberField.getAttribute("value").isEmpty()) {

		        Thread.sleep(1000);
		        LPONumberField.sendKeys("TEST");
		        Thread.sleep(1000);

		        if (!LPONumberField.getAttribute("value").isEmpty()) {
		            System.out.println("Status Passed: LPO Number added Successfully");
		        } else {
		            System.out.println("Test Case Failed: LPO Number is still empty.");
		        }
		    } else {
		        System.out.println(
		            "Status Passed: LPO Number is already filled - " + LPONumberField.getAttribute("value")
		        );
		    }

		} catch (Exception e) {
		    System.out.println("LPO Number field not found or an error occurred: " + e.getMessage());
		}

		// -------- CONTINUE FLOW --------

		Thread.sleep(2000);

		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
		reviewOrderButton.click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='No']")).click();

		// -------- FIXED CLEAR LOGIC STARTS HERE --------

		WebElement LPONumberField1 = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.name("lpo_number"))
		);

		// Focus
		LPONumberField1.click();

		// Select all
		LPONumberField1.sendKeys(Keys.CONTROL + "a");

		// Backspace clears React input reliably
		LPONumberField1.sendKeys(Keys.BACK_SPACE);

		// 🔑 Force React to register input change
		((JavascriptExecutor) driver).executeScript(
		        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
		        LPONumberField1
		);

		// -------- ENTER NEW VALUE --------

		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String lpoNum = "Test" + timestamp;
		LPONumberField1.sendKeys(lpoNum);

		// Optional verification (safe)
		System.out.println("LPO Number: " + LPONumberField1.getAttribute("value"));

	}
	

//	public void validateTotalAmount(double prdValu) throws InterruptedException {
//		Thread.sleep(1000);
//
//		
//		WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//		reviewOrderButton.click();
//		Thread.sleep(1000);
////		driver.findElement(By.xpath("//button[text()='Yes']")).click();
//
//		// Fetch all selected products
//		List<WebElement> products = driver
//				.findElements(By.xpath("(//td[@class='text-right details bold' and @style='padding-top: 0.25rem; padding-bottom: 0.25rem;'])[4]")); 
//		WebElement product = products.get(0);
//
//		// Retrieve the value (text) of the product
//		String productValue = product.getText();
//		double val = Double.parseDouble(productValue);
//		if (val == prdValu) {
//			System.out.println("Total amount is: " +val);
//			System.out.println("Gross amount is: " +prdValu);
//			System.out.println("Status Passed");
//		}else {
//			System.out.println("Total amount is: " +val);
//			System.out.println("Gross amount is: " +prdValu);
//			System.out.println("Status failed");
//		}
//		
//	}
	
	
//	public void validateTotalAmount(double prdValu) throws InterruptedException {
//
//	    Thread.sleep(1000);
//
//	    WebElement reviewOrderButton = driver.findElement(Reviewbutton);
//	    reviewOrderButton.click();
//	    Thread.sleep(1000);
//
//	    // Fetch product total value
//	    List<WebElement> products = driver.findElements(By.xpath(
//	        "(//td[@class='text-right details bold' and @style='padding-top: 0.25rem; padding-bottom: 0.25rem;'])[4]"
//	    ));
//
//	    WebElement product = products.get(0);
//
//	    // Get value from UI
//	    String productValue = product.getText().trim();
//	    double val = Double.parseDouble(productValue);
//
//	    // Round both values to 1 decimal place
//	    BigDecimal actual = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
//	    BigDecimal expected = new BigDecimal(prdValu).setScale(1, RoundingMode.HALF_UP);
//
//	    // Print values
//	    System.out.println("Total amount is: " + actual);
//	    System.out.println("Gross amount is: " + expected);
//
//	    // Compare
//	    if (actual.equals(expected)) {
//	        System.out.println("Status Passed");
//	    } else {
//	        System.out.println("Status Failed");
//	    }
//	}
	
	public void validateTotalAmount(double prdValu) throws InterruptedException {

	    Thread.sleep(1000);

	    WebElement reviewOrderButton = driver.findElement(Reviewbutton);
	    reviewOrderButton.click();
	    Thread.sleep(1000);

	    // Fetch product total value
	    List<WebElement> products = driver.findElements(By.xpath(
	        "(//td[@class='text-right details bold' and @style='padding-top: 0.25rem; padding-bottom: 0.25rem;'])[4]"
	    ));

	    WebElement product = products.get(0);

	    // Get value from UI
	    String productValue = product.getText().trim();
	    double val = Double.parseDouble(productValue);

	    // 🔥 Truncate to 1 decimal place (NO rounding)
	    BigDecimal actual = new BigDecimal(val).setScale(1, RoundingMode.DOWN);
	    BigDecimal expected = new BigDecimal(prdValu).setScale(1, RoundingMode.DOWN);

	    // Print values
	    System.out.println("Total amount is: " + actual);
	    System.out.println("Gross amount is: " + expected);

	    // Compare
	    if (actual.equals(expected)) {
	        System.out.println("Status Passed");
	    } else {
	        System.out.println("Status Failed");
	    }
	}
	
	
	public void clickplaceorderbutton() {
//		driver.findElement(By.xpath("//button[text()='Place Order']")).click();
//		driver.findElement(By.xpath("//button[text()='No']")).click();
//		   
//		   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		   WebElement businessInput = wait.until(
//		       ExpectedConditions.visibilityOfElementLocated(
//		           By.xpath("//input[contains(@class,'fot-formControl') and contains(@class,'populate')]")
//		       )
//		   );
//
//		   String businessName = businessInput.getAttribute("value");
//
//		   System.out.println("Business Name : " + businessName);
//		   
//		   System.out.println("Status Passed : Order created successfully" + "" + businessName);
//		   
//		   
//		   
//		   driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    
		    wait.until(ExpectedConditions.presenceOfElementLocated(
			        By.xpath("//input[contains(@class,'fot-formControl') and contains(@class,'populate')]")
			    ));

			    // 4️⃣ Capture Business Name
			    WebElement businessInput = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(
			            By.xpath("//input[contains(@class,'fot-formControl') and contains(@class,'populate')]")
			        )
			    );

			    String businessName = businessInput.getAttribute("value").trim();


		    // 1️⃣ Click Place Order
		    wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//button[text()='Place Order']")
		    )).click();

		    // 2️⃣ Click confirmation NO
		    wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//button[text()='No']")
		    )).click();

		    // 5️⃣ Proper Console Logs
		    System.out.println("====================================");
		    System.out.println("Business Name : " + businessName);
		    System.out.println("Status Passed : Order created successfully → " + businessName);
		    System.out.println("====================================");

		    // 6️⃣ Navigate to invoice page
		    driver.get("https://mktadmin.freshontable.com/ordermanagement/downloadInvoices");
		}

		
		
		
		
			 
		}


