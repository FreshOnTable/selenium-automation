package PageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

public class CreateLpo {
	
	private int getColumnIndex(String headerName) {

	    List<WebElement> headers = driver.findElements(
	        By.xpath("//table//th")
	    );

	    for (int i = 0; i < headers.size(); i++) {
	        String headerText = headers.get(i).getText().trim();

	        if (headerText.equalsIgnoreCase(headerName)) {
	            return i + 1; // XPath index starts from 1
	        }
	    }

	    throw new RuntimeException("? Column not found: " + headerName);
	}

	
	public WebDriver driver;			
	public WebDriverWait wait;
	
	public CreateLpo(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
	
	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickprocur = By.xpath("//ul//li//span[text()='Procurement']");
	By clickLpogrid = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/ul[5]/ul/li[3]/span/span");
	By clickwearhousefilter =  By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[1]/img");
	By wearhouseDropdown = By.name("del_hub_id");
	By Applyfilter = By.xpath("//button[text()='Apply Filters']");
	By selectproducts = By.xpath("//table//tbody//tr/td[1]");
	By NextButton = By.xpath("//button[normalize-space()='Next']");
	By SubmitButton = By.xpath("//button[text()='SUBMIT']");
	By ConfirmButton = By.xpath("//button[text()='Confirm']");
	By NoButton = By.xpath("//button[text()='No']");
	By CreateLpoVali = By.xpath("//button[text()='Create LPOs']");
	
	//filter functionality
	By deliveryHubDropdown = By.name("del_hub_id");
	By sellerInput = By.xpath("//input[@placeholder='Select Seller']");
	By approvalTypeDropdown = By.xpath("//select[contains(@class,'approval')]");
	By focCheckbox = By.xpath("//input[@type='checkbox']");
	By applyFiltersBtn =  By.xpath("//button[text()='Apply Filters']");

	// Table columns
	By sellerColumn = By.xpath("//table//tr/td[5]");
	By approvalColumn = By.xpath("//table//tr/td[?]"); // adjust index
	By focColumn = By.xpath("//table//tr/td[contains(text(),'FOC')]");
	
	// No records message
	By noRecordsMsg = By.xpath("//*[normalize-space()='No records to display']");

	// LPO product rows
	By productRows = By.xpath("//table/tbody/tr");

	// Next button
	By nextBtn = By.xpath("//button[normalize-space()='Next']");


	
	public void clickthreedot() throws InterruptedException {
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.findElement(clickgrid).click();
	}
	
	public void clickprocurement() {
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    driver.findElement(clickprocur).click();
	}
	
	public void clickLpo() {
		driver.findElement(clickLpogrid).click();
	}
	
	public void clickwearhousefitler() {
		driver.findElement(clickwearhousefilter).click();
		
	}
	
	public void selectDeliveryHub(String hubValue) {
	    WebElement dropdown = driver.findElement(deliveryHubDropdown);
	    Select select = new Select(dropdown);
	    select.selectByValue(hubValue);
	}
	
	
//	public void clickApplyFilters() {
//	    driver.findElement(applyFiltersBtn).click();
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

	
/*	public void selectproducts() {
		List<WebElement> checkboxes = driver.findElements(selectproducts);
 //       System.out.println(checkboxes.size());

        for (int i = 0; i < 3 && i < checkboxes.size(); i++) {
            WebElement checkbox = checkboxes.get(i); 
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            
        }
	}*/
	
	public void selectproducts() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    By checkboxesLocator =
	        By.xpath("//table//tbody//input[@type='checkbox']");

	    // Select first 3 products safely
	    for (int i = 1; i <= 2; i++) {

	        By checkboxBy = By.xpath("(//table//tbody//input[@type='checkbox'])[" + i + "]");

	        WebElement checkbox = wait.until(
	            ExpectedConditions.refreshed(
	                ExpectedConditions.elementToBeClickable(checkboxBy)
	            )
	        );

	        if (!checkbox.isSelected()) {
	            js.executeScript("arguments[0].click();", checkbox);
	        }
	    }
	}

	
	public void clicknextbutton() {
		driver.findElement(nextBtn).click();
	}
	
	
	public String getSelectedDeliveryHub() {

		By deliveryHubSelect = By.xpath(
		        "//label[normalize-space()='Delivery Hub']" +
		        "/ancestor::div[contains(@class,'fot-formGroup')]//select"
		    );

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    WebElement selectElement = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(deliveryHubSelect)
		    );

		    Select select = new Select(selectElement);
		    return select.getFirstSelectedOption().getText().trim();
		    		    
		}
	
	public boolean areProductsAvailable() {

	    // Case 1: "No records" message shown
	    if (!driver.findElements(noRecordsMsg).isEmpty()) {
	        System.out.println("INFO: No records to display");
	        return false;
	    }

	    // Case 2: Product rows exist
	    return !driver.findElements(productRows).isEmpty();
	}
	
	public void clickNextSafely() {
		
		
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    // 1. Remove / hide the blocking overlay
		    js.executeScript(
		        "var nav = document.querySelector('.lpo-nav-btn');" +
		        "if(nav){ nav.style.display='none'; }"
		    );

		    // 2. Wait for button
		    WebElement nextBtn = wait.until(
		            ExpectedConditions.elementToBeClickable(NextButton)
		    );

		    // 3. Scroll into view
		    js.executeScript(
		        "arguments[0].scrollIntoView({block:'center'});", nextBtn
		    );

		    // 4. Click
		    nextBtn.click();

		    System.out.println("Clicked Next button after removing overlay");
		}
	
	
// Scenario: seller filter
	By clickSellerInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[2]/div/div[5]/div/div/input");
	
//	Seller popup
	By sellerRows = By.xpath("//div[contains(@class,'modal')]//tbody/tr");
	By sellerNameCell = By.xpath(".//td[1]");
	By sellerCheckbox = By.xpath(".//input[@type='checkbox']");
	By sellerSelectBtn = By.xpath("//button[normalize-space()='Select']");
	
//	LPO validation
	By noRecordsText = By.xpath("//*[normalize-space()='No records to display']");
	By lpoSellerColumn = By.xpath("//table//tbody/tr/td[contains(@class,'seller')]");
	
	public String selectRandomSellerAndApplyFilter() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    // 1. Click Select Seller input
	    wait.until(ExpectedConditions.elementToBeClickable(clickSellerInput)).click();
	
//	     2. Wait for seller popup
	    List<WebElement> sellers = wait.until(
	        ExpectedConditions.visibilityOfAllElementsLocatedBy(sellerRows)
	    );

	    // 3. Pick random seller
	    Random random = new Random();
	    WebElement randomRow = sellers.get(random.nextInt(sellers.size()));

	    String selectedSeller =
	        randomRow.findElement(sellerNameCell).getText().trim();

	    randomRow.findElement(sellerCheckbox).click();

	    // 4. Click Select button
	    driver.findElement(sellerSelectBtn).click();

	    System.out.println("Selected Seller: " + selectedSeller);

	    // 5. Click Apply Filters
	    wait.until(ExpectedConditions.elementToBeClickable(applyFiltersBtn)).click();

	    return selectedSeller;
	}

	public void validateLpoResultsForSeller(String expectedSeller) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Case 1: No records
	    if (driver.findElements(noRecordsText).size() > 0) {
	        System.out.println(
	            "INFO: No records to display for seller: " + expectedSeller
	        );
	        Assert.assertTrue(true);
	        return;
	    }

	    // Case 2: Records exist
	    List<WebElement> sellers = wait.until(
	        ExpectedConditions.visibilityOfAllElementsLocatedBy(lpoSellerColumn)
	    );

	    for (WebElement seller : sellers) {
	        String actualSeller = seller.getText().trim();
	        System.out.println("Actual Seller in grid: " + actualSeller);

	        Assert.assertEquals(
	            actualSeller,
	            expectedSeller,
	            "Seller mismatch in LPO grid!"
	        );
	    }
	}


//only Foc order filter
By onlyFocCheckbox = By.xpath("//span[contains(@class,'MuiCheckbox')]//input[@type='checkbox']");//

By orderMarginColumn = By.xpath("(.//th[contains(@class,'custom-header-content text-center')])[16]");

public void clickFOCcheckbox() {

    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    WebElement applyBtn1 = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(@class,'MuiCheckbox')]//input[@type='checkbox']")
            )
    );

    // Scroll to center
    js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});", applyBtn1
    );

    // JS click (bypasses overlay)
    js.executeScript("arguments[0].click();", applyBtn1);

}


public boolean validateFocOrdersMarginZero() {

	
    // No records case
    if (!driver.findElements(noRecordsMsg).isEmpty()) {
        System.out.println("INFO: No records to display");
        return true;
    }

    int orderMarginColIndex = getColumnIndex("Order Margin (in %)");

    List<WebElement> rows = driver.findElements(
        By.xpath("//table/tbody/tr")
    );

    for (int i = 0; i < rows.size(); i++) {

        // Re-fetch row to avoid stale element
        WebElement row = driver.findElements(
            By.xpath("//table/tbody/tr")
        ).get(i);

        WebElement marginCell = row.findElement(
            By.xpath(".//td[" + orderMarginColIndex + "]")
        );

        String marginText = marginCell.getText()
                .replace("%", "")
                .trim();

        double marginValue = Double.parseDouble(marginText);

        if (marginValue != 0.0) {
            System.out.println(
                "? FOC validation failed at row " + (i + 1) +
                " | Margin = " + marginValue
            );
            return false;
        }
    }

    System.out.println("All FOC orders have zero order margin");
    return true;
}

//existing seller
private By sellerNameCells = By.xpath(
    "//table//tbody/tr/td[count(//th[normalize-space()='Seller Name']/preceding-sibling::th)+1]"
);

// Seller search input (filter section)
private By clickFiltersIcon = By.xpath("(//div[@class='custom-filter-icon'])[4]");
private By clickFiltersIcon1 = By.xpath("(//div[@class='custom-filter-icon'])[8]");
private By sellerSearchInput = By.xpath("(//input[@type='text'])[3]");

private By tableRows = By.xpath("//table//tbody/tr");

public String fetchRandomSellerNameFromTable() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOfElementLocated(sellerNameCells));

    List<WebElement> sellers = driver.findElements(sellerNameCells);

    Assert.assertTrue(sellers.size() > 0, "No seller names found in table");

    // Generate random index
    int randomIndex = new Random().nextInt(sellers.size());

    String sellerName = sellers.get(randomIndex).getText().trim();

    System.out.println("Fetched Random Seller Name from table: " + sellerName);

    return sellerName;
}

//Search seller using filter
public void searchBySellerName(String sellerName) throws InterruptedException {   
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        Thread.sleep(2000);
    driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[1]/div/div[1]/img")).click();
    
    wait.until(ExpectedConditions.elementToBeClickable(clickSellerInput)).click();
    driver.findElement(clickFiltersIcon1).click();
 //   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    WebElement input1 = wait.until(
        ExpectedConditions.visibilityOfElementLocated(sellerSearchInput)
    );
    input1.sendKeys(sellerName);
    
    driver.findElement(By.xpath("//button[text()='Apply']")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("(//table//tbody/tr[1]//input[@type='checkbox'])[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[normalize-space()='Select']")).click();

 //   driver.findElement(applyFiltersBtn).click();

    // wait for table refresh
    wait.until(ExpectedConditions.or(
        ExpectedConditions.visibilityOfElementLocated(tableRows),
        ExpectedConditions.visibilityOfElementLocated(noRecordsMsg)
    ));
}

 // Validate all displayed rows belong to searched seller
 
public void validateSellerFilterResults(String expectedSeller) {

    if (!driver.findElements(noRecordsMsg).isEmpty()) {
        Assert.fail("No records displayed for seller: " + expectedSeller);
    }

    List<WebElement> sellers = driver.findElements(sellerNameCells);

    Assert.assertTrue(sellers.size() > 0, "Seller column empty after filtering");

    for (WebElement seller : sellers) {
        String actualSeller = seller.getText().trim();
        System.out.println("Actual Seller: " + actualSeller);

        Assert.assertEquals(
            actualSeller,
            expectedSeller,
            "Seller mismatch in filtered results"
        );
    }

    System.out.println("Seller filter validation PASSED");
}

//Approval Types

public void selectApprovalType(String type) {

    Select approvalSelect = new Select(
            driver.findElement(By.xpath("//select[@name='approvalType']"))
    );

    approvalSelect.selectByVisibleText(type);
}


public String getStatusFromRowClass(WebElement row) {

    String rowClass = row.getAttribute("class");

    if (rowClass.contains("disabled-red")) {
        return "Rejected";
    }
    if (rowClass.contains("disabled-green")) {
        return "Approval Requested";
    }
    return "Approved";
}

public boolean validateRowsByRowClass(String expectedStatus) {

    List<WebElement> rows = driver.findElements(
        By.cssSelector("table tbody tr")
    );

    for (int i = 1; i <= rows.size(); i++) {

        WebElement row = driver.findElement(
            By.cssSelector("table tbody tr:nth-child(" + i + ")")
        );

        String actualStatus = getStatusFromRowClass(row);

        System.out.println("Row " + i + " Status : " + actualStatus);

        if (!actualStatus.equals(expectedStatus)) {
            return false;
        }
    }
    return true;
}

public String getSellerName() {

    // Wait for table
    WebElement table = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//table"))
    );

    // Find Seller Name column index
    List<WebElement> headers = table.findElements(By.xpath(".//thead//th"));
    int sellerIndex = -1;

    for (int i = 0; i < headers.size(); i++) {
        if (headers.get(i).getText().trim().equalsIgnoreCase("Seller Name")) {
            sellerIndex = i + 1;
            break;
        }
    }

    if (sellerIndex == -1) {
        throw new RuntimeException("Seller Name column not found");
    }

    // Read seller from first row
    WebElement sellerCell = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tbody//tr[1]/td[" + sellerIndex + "]"))
    );

    return sellerCell.getText().trim();
}

// ---------- Change Seller ----------
public String changeSellerAndGetNewSeller() {

    String oldSeller = getSellerName();
    System.out.println("Old Seller: " + oldSeller);

    // Click Change Seller
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='Change Seller']"))).click();

    // Wait for seller list
    List<WebElement> sellers = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//table//tbody//td[@style='cursor: pointer;']"))
    );

    WebElement selectedSeller = null;

    for (WebElement seller : sellers) {
        if (seller.getAttribute("class").contains("Mui-selected")) {
            selectedSeller = seller;
            break;
        }
    }

    // Select next seller
    for (WebElement seller : sellers) {
        if (!seller.equals(selectedSeller)) {
            seller.click();
            break;
        }
    }

    // Submit
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='Submit']"))).click();

    // Confirm
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='Confirm']"))).click();

    // Click OK
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='OK']"))).click();

    // Wait until seller value changes
    wait.until(ExpectedConditions.not(
            ExpectedConditions.textToBePresentInElementLocated(
                    By.xpath("//table//tbody//tr[1]"), oldSeller)));

    String newSeller = getSellerName();
    System.out.println("New Seller: " + newSeller);

    return newSeller;
}



//Scenario: validate 2nd page

By searchBox = By.xpath("//input[@placeholder='Search product...']");
By productRows1 = By.xpath("//tr[contains(@class,'custom-row')]");
By firstProductRow = By.xpath("//table//tbody//tr[1]");
By orderQtyInput = By.xpath("(//input[contains(@class,'order-qty') or @type='number'])[1]");
By nextButton = By.xpath("//button[normalize-space()='Next']");
By deliveryHubDropdown1 = By.xpath("//label[text()='Delivery Hub']/following-sibling::div//select");
By submitButton = By.xpath("//button[normalize-space()='SUBMIT']");
By confirmButton = By.xpath("//button[normalize-space()='Confirm']");
By NoBtn = By.xpath("//button[normalize-space()='No']");
By successToast = By.xpath("//*[contains(text(),'LPO created') or contains(text(),'successfully')]");
By productExpandArrows = By.xpath("//tr[contains(@class,'product-row')]//button[contains(@class,'expand') or .//i]");
By orderQtyInputs = By.xpath("//input[contains(@class,'order-qty') or @type='number']");
By deliveryHubField = By.xpath("//div[contains(@class,'delivery-hub') or .//label[text()='Delivery Hub']]");
By deliveryHubOptions = By.xpath("//div[contains(@class,'menu') or contains(@class,'dropdown')]//div[normalize-space()!='Select']");
By sellerAccordionArrow = By.xpath(
	    "//div[contains(@class,'accordion')]//button | //div[contains(@class,'caret')]"
	);

public String pickRandomProductName() {

    List<WebElement> rows = driver.findElements(productRows1);
    Assert.assertTrue(rows.size() > 0, "No products found");

    WebElement randomRow = rows.get(new Random().nextInt(rows.size()));

    // FIRST TD contains product name
    String productName = randomRow.findElement(By.xpath("./td[1]"))
                                  .getText()
                                  .trim();

    System.out.println("Random product picked: " + productName);
    return productName;
}

public void searchProductFromList(String productName) {

    WebElement search = driver.findElement(searchBox);
    search.clear();

    // Optional: partial search for safety
    String searchText = productName.split(" ")[0];
    search.sendKeys(searchText);

    List<WebElement> filteredRows =
            driver.findElements(By.xpath("//tr[contains(@class,'custom-row')]"));

    Assert.assertTrue(filteredRows.size() > 0,
            "Search did not filter any product");
}

public void expandProductRow() {

    WebElement arrow = driver.findElement(By.xpath(".//div[contains(@class,'caret')]"));

    if (arrow.isDisplayed()) {
        arrow.click();
    }

}

/*public int updateOrderQtyRandomly() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    Random random = new Random();

    int randomQty = random.nextInt(100) + 1; // 1–100

    WebElement qtyInput =
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderQtyInput));

    // Clear safely
    qtyInput.click();
    qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    qtyInput.sendKeys(Keys.BACK_SPACE);

    // Enter value slowly (IMPORTANT)
    for (char c : String.valueOf(randomQty).toCharArray()) {
        qtyInput.sendKeys(String.valueOf(c));
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
    }

    // Trigger JS events
    qtyInput.sendKeys(Keys.TAB);

    // Validate actual UI value
    String uiValue = qtyInput.getAttribute("value");
    System.out.println("Random Qty requested: " + randomQty);
    System.out.println("Qty accepted by UI: " + uiValue);

    return Integer.parseInt(uiValue);
}*/


public void updateOrderQtyForAllProducts() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Random random = new Random();

    List<WebElement> productRows = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//tr[contains(@class,'custom-row')]")
            )
    );

    System.out.println("Total products: " + productRows.size());

    for (WebElement row : productRows) {

        // Find caret inside THIS row
        WebElement caret = row.findElement(By.xpath(".//div[@class='caret']"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", caret);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", caret);

        // Qty input is in the NEXT row (expanded row)
        WebElement qtyInput = wait.until(
                ExpectedConditions.visibilityOf(
                        row.findElement(
                                By.xpath("following-sibling::tr[1]//input[@type='number']")
                        )
                )
        );

        int randomQty = random.nextInt(100) + 1;

        qtyInput.click();
        qtyInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        qtyInput.sendKeys(Keys.BACK_SPACE);

        // Slow typing fixes 56 → 6 issue
        for (char c : String.valueOf(randomQty).toCharArray()) {
            qtyInput.sendKeys(String.valueOf(c));
            try { Thread.sleep(80); } catch (InterruptedException ignored) {}
        }

        qtyInput.sendKeys(Keys.TAB);

        System.out.println("Qty set to: " + randomQty);
    }
}


public void clickNext() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
}

By accordionHeader = By.xpath("//div[contains(@class,'lpo-accordion-header')]");
By accordionExpanded = By.xpath(".//div[contains(@class,'lpo-accordion-body') and not(contains(@style,'display: none'))]");
By deliveryHubSelect = By.name("del_hub_id_select");

	
/*public void selectRandomDeliveryHub() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Locate dropdown
    WebElement deliveryHubDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.name("del_hub_id_select")
            )
    );

    // Click dropdown (important for UI validation)
    deliveryHubDropdown.click();

    Select select = new Select(deliveryHubDropdown);
    List<WebElement> options = select.getOptions();

    // Ignore first two options: "Delivery Hub" and "Select"
    if (options.size() <= 2) {
        throw new RuntimeException("No valid Delivery Hub options available");
    }

    // Pick random index from valid options
    int randomIndex = new Random().nextInt(options.size() - 2) + 2;

    // Select option
    select.selectByIndex(randomIndex);

    System.out.println(
        "Random Delivery Hub selected: " + options.get(randomIndex).getText()
    );
}*/

public void selectRandomDeliveryHub() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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

public void clickSubmit() {
	WebElement submitBtn = driver.findElement(submitButton);
	submitBtn.click();
}

public void clickConfirmBtn() {
	WebElement submitBtn = driver.findElement(confirmButton);
	submitBtn.click();
}
public void clickNoBtn() {
	WebElement submitBtn = driver.findElement(NoBtn);
	submitBtn.click();
}
public void validateLpoCreated() {

	    By successMsgLocator = By.xpath("//div[contains(text(),'Waiting For Acceptance')]");

	    WebElement successMsg = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(successMsgLocator)
	    );

	    String expected = "Waiting For Acceptance";
	    String actual = successMsg.getText().trim();

	    Assert.assertEquals(actual, expected, "❌ Expected and Actual messages do not match");

	    System.out.println("✅ Test case passed – LPO created successfully");
	}


}



