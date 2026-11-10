package PageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import org.openqa.selenium.support.ui.Select;

public class CreateLpo {
	
    private static double rejectedQtyFromQc;
     String createdLpoId;
     
     private double beforeEditTotalAmount;
     private double afterEditTotalAmount;
    
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
	
	public void clickwearhousefitler() throws InterruptedException {
		Thread.sleep(5000);
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

//	    System.out.println("Apply Filters clicked");
	}
	
	public void selectOnlyApprovedProducts(int numberOfProducts) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    int selectedCount = 0;

	    // Find table rows directly
	    List<WebElement> rows = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                    By.xpath("//tbody/tr")
	            )
	    );

//	    System.out.println("Total rows found: " + rows.size());

	    for (int i = 0; i < rows.size(); i++) {

	        if (selectedCount >= numberOfProducts) {
	            break;
	        }

	        try {

	          
	            List<WebElement> currentRows = driver.findElements(
	                    By.xpath("//tbody/tr")
	            );

	            if (i >= currentRows.size()) {
	                break;
	            }

	            WebElement row = currentRows.get(i);

	            
	            String productName = "";

	            try {

	                WebElement productCell = row.findElement(
	                        By.xpath("./td[3]")
	                );

	                productName = productCell.getText().trim();

	            } catch (Exception e) {

	                productName = "Unknown Product";
	            }

//	            System.out.println(
//	                    "Row " + (i + 1) +
//	                    " | Product: " + productName
//	            );

	            // ---------------------------------------------------
	            // Skip if Product name is blank
	            // ---------------------------------------------------

	            if (productName.isEmpty()) {

	                System.out.println(
	                        "⏭ Skipping row " + (i + 1) +
	                        " because Product name is blank."
	                );

	                continue;
	            }

	            // ---------------------------------------------------
	            // Find ONLY the required checkbox
	            //
	            // HTML:
	            //
	            // <input class="grid-checkbox-input cursor-pointer"
	            //        type="checkbox">
	            //
	            // ---------------------------------------------------

	            List<WebElement> checkboxList = row.findElements(
	                    By.xpath(
	                            ".//input[contains(@class,'grid-checkbox-input') " +
	                            "and contains(@class,'cursor-pointer')]"
	                    )
	            );

	            // ---------------------------------------------------
	            // Skip if required checkbox not found
	            // ---------------------------------------------------

	            if (checkboxList.isEmpty()) {

	                System.out.println(
	                        "⏭ Skipping " + productName +
	                        " because required checkbox was not found."
	                );

	                continue;
	            }

	            // Get the actual checkbox
	            WebElement checkbox = checkboxList.get(0);

	            // ---------------------------------------------------
	            // Find actual background color of the row/cells
	            // ---------------------------------------------------

	            String backgroundColor = (String) js.executeScript(
	                    """
	                    function getRealBackground(element) {

	                        while (element && element.tagName !== 'TABLE') {

	                            var color = window.getComputedStyle(element).backgroundColor;

	                            if (color &&
	                                color !== 'rgba(0, 0, 0, 0)' &&
	                                color !== 'transparent') {

	                                return color;
	                            }

	                            element = element.parentElement;
	                        }

	                        return 'transparent';
	                    }

	                    var cells = arguments[0].querySelectorAll('td');

	                    for (var i = 0; i < cells.length; i++) {

	                        var color = getRealBackground(cells[i]);

	                        if (color !== 'transparent') {
	                            return color;
	                        }
	                    }

	                    return getRealBackground(arguments[0]);
	                    """,
	                    row
	            );

	            // ---------------------------------------------------
	            // ONLY WHITE ROWS ARE ALLOWED
	            // ---------------------------------------------------

	            boolean isWhiteRow =
	                    backgroundColor.equalsIgnoreCase("rgb(255, 255, 255)")
	                    || backgroundColor.equalsIgnoreCase("rgba(255, 255, 255, 1)")
	                    || backgroundColor.equalsIgnoreCase("rgb(249, 249, 249)")
	                    || backgroundColor.equalsIgnoreCase("rgba(249, 249, 249, 1)");

	            if (!isWhiteRow) {

//	                System.out.println(
//	                        "⏭ Skipping " + productName +
//	                        " because this product is not Approved."
//	                );

	                continue;
	            }

	            // ---------------------------------------------------
	            // Skip if checkbox is disabled
	            // ---------------------------------------------------

	            if (!checkbox.isEnabled()) {

	                System.out.println(
	                        "⏭ Skipping " + productName +
	                        " because checkbox is disabled."
	                );

	                continue;
	            }

	            // ---------------------------------------------------
	            // Skip if already selected
	            // ---------------------------------------------------

	            if (checkbox.isSelected()) {

	                System.out.println(
	                        "⏭ Skipping " + productName +
	                        " because already selected."
	                );

	                continue;
	            }

	            // ---------------------------------------------------
	            // Scroll to checkbox
	            // ---------------------------------------------------

	            js.executeScript(
	                    "arguments[0].scrollIntoView({block:'center'});",
	                    checkbox
	            );

	            Thread.sleep(300);

	            // ---------------------------------------------------
	            // Click checkbox
	            // ---------------------------------------------------

	            try {

	                checkbox.click();

	            } catch (ElementClickInterceptedException e) {

	                System.out.println(
	                        "Normal click intercepted. Using JavaScript click."
	                );

	                js.executeScript(
	                        "arguments[0].click();",
	                        checkbox
	                );
	            }

	            selectedCount++;

	            
	            Thread.sleep(500);

	        } catch (StaleElementReferenceException e) {

	            System.out.println(
	                    "⚠ Row " + (i + 1) +
	                    " became stale. Re-reading the table."
	            );

	            // Continue to next row
	            continue;
	        }
	    }

//	    if (selectedCount < numberOfProducts) {
//
//	        throw new RuntimeException(
//	                "❌ Could select only " +
//	                selectedCount +
//	                " white products, but " +
//	                numberOfProducts +
//	                " were required."
//	        );
//	    }
	}
	
	public void selectAllproducts() throws InterruptedException {
		
		Thread.sleep(500);
		WebElement selectAll = driver.findElement(By.xpath("//table//thead//input[@type='checkbox']"));
		selectAll.click();
		
//		List<WebElement> checkboxes = driver.findElements(selectproducts);
 //       System.out.println(checkboxes.size());		

//        for (int i = 0; i < 3 && i < checkboxes.size(); i++) {
//            WebElement checkbox = checkboxes.get(i); 
//            if (!checkbox.isSelected()) {
//                checkbox.click();
//            }    
//        }
		
	}
	
/*	public void selectproducts() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    By checkboxesLocator =
	        By.xpath("//table//tbody//input[@type='checkbox']");

	    // Select first 3 products safely
	    for (int i = 1; i <= 1; i++) {

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
	}*/
	
	public void selectproducts() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Locate all table rows
	    List<WebElement> rows = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//table//tbody//tr")
	            )
	    );

	    int productsToSelect = 1;   // change to 3 if needed
	    int selectedCount = 0;

	    for (WebElement row : rows) {

	        if (selectedCount == productsToSelect)
	            break;

	        // Checkbox inside the same row
	        WebElement checkbox = row.findElement(
	                By.xpath(".//input[@type='checkbox']")
	        );

	        // Product name (2nd column)
	        WebElement productNameCell = row.findElement(
	                By.xpath(".//td[3]")
	        );

	        String productName = productNameCell.getText().trim();

	        if (!checkbox.isSelected()) {
	            js.executeScript("arguments[0].click();", checkbox);
	            System.out.println("✅ Selected Product: " + productName);
	            selectedCount++;
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
//	    wait.until(ExpectedConditions.elementToBeClickable(applyFiltersBtn)).click();
	    
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));

		    WebElement applyBtn = wait1.until(
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
	    

	    return selectedSeller;
	}

	public void validateLpoResultsForSeller(String expectedSeller) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Case 1: No records
	    if (driver.findElements(noRecordsText).size() > 0) {
	        System.out.println(
	            "INFO: No records to display: " + expectedSeller
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

/*public void selectApprovalType(String type) {

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
}*/

//Select Approval Type
public void selectApprovalType(String approvalType) {

 Select approvalSelect = new Select(
         driver.findElement(By.xpath("//select[@name='approvalType']")));

 approvalSelect.selectByVisibleText(approvalType);
}

//Get status based on row class
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


//Validate rows and print count
public boolean validateRowsByRowClass(String expectedStatus) {

    List<WebElement> noData = driver.findElements(
            By.xpath("//td[contains(text(),'No records to display')]"));

    if (!noData.isEmpty()) {

        System.out.println("Approval Type : " + expectedStatus);
        System.out.println("Total Products : 0");

        return true;
    }

    List<WebElement> rows = driver.findElements(
            By.cssSelector("table tbody tr"));

    System.out.println("Approval Type : " + expectedStatus);
    System.out.println("Total Products : " + rows.size());

    for (WebElement row : rows) {

        String actualStatus = getStatusFromRowClass(row);

        if (!actualStatus.equals(expectedStatus)) {
            return false;
        }
    }

    return true;
}

//---------- Change Seller ----------------

//public void changeMultipleSellers() throws InterruptedException {
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//    Random random = new Random();
//
//    // =========================================================
//    // 1. Click Change Seller
//    // =========================================================
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//button[normalize-space()='Change Seller']")))
//            .click();
//
//    Thread.sleep(1500);
//
//    // =========================================================
//    // 2. Find all product accordions
//    // =========================================================
//
//    List<WebElement> productAccordions = wait.until(
//            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//                    By.xpath(
//                            "//div[contains(@class,'MuiAccordion-root')]"
//                    )
//            )
//    );
//
//    System.out.println("Total Product Accordions: "
//            + productAccordions.size());
//
//    if (productAccordions.isEmpty()) {
//        throw new RuntimeException("No product accordions found.");
//    }
//
//    // =========================================================
//    // 3. Decide how many products to change
//    //
//    // If 1 product -> select 1
//    // If 2 products -> select 2
//    // If 3+ products -> randomly select 2 or 3
//    // =========================================================
//
//    int totalProducts = productAccordions.size();
//
//    int numberToChange;
//
//    if (totalProducts == 1) {
//        numberToChange = 1;
//    } else if (totalProducts == 2) {
//        numberToChange = 2;
//    } else {
//        numberToChange = random.nextInt(2) + 2; // 2 or 3
//        numberToChange = Math.min(numberToChange, totalProducts);
//    }
//
//    System.out.println(
//            "Products selected for seller change: "
//                    + numberToChange
//    );
//
//    // =========================================================
//    // 4. Generate UNIQUE random product indexes
//    // =========================================================
//
//    List<Integer> selectedProductIndexes = new ArrayList<>();
//
//    while (selectedProductIndexes.size() < numberToChange) {
//
//        int index = random.nextInt(totalProducts);
//
//        if (!selectedProductIndexes.contains(index)) {
//            selectedProductIndexes.add(index);
//        }
//    }
//
//    System.out.println(
//            "Selected Product Indexes: "
//                    + selectedProductIndexes
//    );
//
//    // =========================================================
//    // 5. Process selected products
//    // =========================================================
//
//    for (Integer productIndex : selectedProductIndexes) {
//
//        try {
//
//            // Re-find accordions to avoid stale elements
//            productAccordions = driver.findElements(
//                    By.xpath("//div[contains(@class,'MuiAccordion-root')]")
//            );
//
//            WebElement accordion =
//                    productAccordions.get(productIndex);
//
//            // -------------------------------------------------
//            // Product name
//            // -------------------------------------------------
//
//            String productName = "";
//
//            try {
//                productName = accordion.findElement(
//                        By.xpath(
//                                ".//*[contains(@class,'MuiAccordionSummary-content')]"
//                        )
//                ).getText().trim();
//            } catch (Exception e) {
//                productName = "Product " + (productIndex + 1);
//            }
//
//            System.out.println("--------------------------------------");
//            System.out.println(
//                    "Processing Product: " + productName
//            );
//
//            // -------------------------------------------------
//            // Scroll to accordion
//            // -------------------------------------------------
//
//            js.executeScript(
//                    "arguments[0].scrollIntoView({block:'center'});",
//                    accordion
//            );
//
//            Thread.sleep(500);
//
//            // -------------------------------------------------
//            // Check whether accordion is already expanded
//            // -------------------------------------------------
//
//            WebElement summary = accordion.findElement(
//                    By.xpath(".//*[contains(@class,'MuiAccordionSummary-root')]")
//            );
//
//            String expanded = summary.getAttribute("aria-expanded");
//
//            if (!"true".equalsIgnoreCase(expanded)) {
//
//                js.executeScript(
//                        "arguments[0].click();",
//                        summary
//                );
//
//                Thread.sleep(800);
//
//                System.out.println(
//                        "✅ Expanded: " + productName
//                );
//            } else {
//
//                System.out.println(
//                        "Already expanded: " + productName
//                );
//            }
//
//            // =================================================
//            // 6. Find seller cells ONLY inside this accordion
//            // =================================================
//
//            List<WebElement> sellerCells = accordion.findElements(
//                    By.xpath(
//                            ".//td[@style='cursor: pointer;']"
//                    )
//            );
//
//            System.out.println(
//                    "Sellers available for product: "
//                            + sellerCells.size()
//            );
//
//            if (sellerCells.size() < 2) {
//
//                System.out.println(
//                        "⚠ Less than 2 sellers available. Skipping product."
//                );
//
//                continue;
//            }
//
//            // =================================================
//            // 7. Find currently selected seller
//            // =================================================
//
//            WebElement currentSeller = null;
//
//            for (WebElement seller : sellerCells) {
//
//                String className =
//                        seller.getAttribute("class");
//
//                if (className != null &&
//                        className.contains("Mui-selected")) {
//
//                    currentSeller = seller;
//                    break;
//                }
//            }
//
//            String oldSeller = "";
//
//            if (currentSeller != null) {
//
//                oldSeller = currentSeller.getAttribute("innerText").trim();
//
//                if (oldSeller.isEmpty()) {
//                    oldSeller = currentSeller.getAttribute("textContent").trim();
//                }
//
//                System.out.println("Current Seller: " + oldSeller);
//            }
//
//            // =================================================
//            // 8. Select a DIFFERENT random seller
//            // =================================================
//
//            List<WebElement> availableSellers =
//                    new ArrayList<>();
//
//            for (WebElement seller : sellerCells) {
//
//                if (currentSeller == null ||
//                        !seller.equals(currentSeller)) {
//
//                    availableSellers.add(seller);
//                }
//            }
//
//            if (availableSellers.isEmpty()) {
//
//                System.out.println(
//                        "⚠ No different seller available."
//                );
//
//                continue;
//            }
//
//            // Random seller
//            WebElement newSeller =
//                    availableSellers.get(
//                            random.nextInt(
//                                    availableSellers.size()
//                            )
//                    );
//
//            String newSellerName =
//                    newSeller.getText().trim();
//
//            js.executeScript(
//                    "arguments[0].scrollIntoView({block:'center'});",
//                    newSeller
//            );
//
//            Thread.sleep(300);
//
//            js.executeScript(
//                    "arguments[0].click();",
//                    newSeller
//            );
//
//            Thread.sleep(500);
//
//            System.out.println(
//                    "✅ Seller changed: "
//                            + oldSeller
//                            + " → "
//                            + newSellerName
//            );
//
//        } catch (StaleElementReferenceException e) {
//
//            System.out.println(
//                    "⚠ Product became stale. Skipping this product."
//            );
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    "❌ Failed to change seller for product: "
//                            + e.getMessage()
//            );
//        }
//    }
//
//    // =========================================================
//    // 9. Submit all seller changes
//    // =========================================================
//
//    System.out.println("--------------------------------------");
//    System.out.println("Submitting seller changes...");
//
//    wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='Submit']")
//            )
//    ).click();
//
//    Thread.sleep(1000);
//
//    // =========================================================
//    // 10. Confirm
//    // =========================================================
//
//    wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='Confirm']")
//            )
//    ).click();
//
//    Thread.sleep(1000);
//
//    // =========================================================
//    // 11. OK
//    // =========================================================
//
//    wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='OK']")
//            )
//    ).click();
//
//    System.out.println(
//            "✅ Multiple seller changes submitted successfully."
//    );
//}

public void changeSellerForAllProducts() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Random random = new Random();

    int totalProducts = 0;
    int changedProducts = 0;
    int skippedProducts = 0;

    List<String> changeDetails = new ArrayList<>();

    
    // 1. CLICK CHANGE SELLER
    // =========================================================

    WebElement changeSellerBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Change Seller']")
            )
    );

    js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            changeSellerBtn
    );

    Thread.sleep(700);

    changeSellerBtn.click();

    Thread.sleep(2000);

    
    // 2. PRODUCT ACCORDION XPATH

    String productAccordionXpath =
            "//div[contains(@class,'MuiAccordion-root')]"
          + "//div[contains(@class,'MuiAccordionSummary-root')]"
          + "[.//div[contains(@class,'product-name')]]";

    List<WebElement> productHeaders =
            driver.findElements(By.xpath(productAccordionXpath));

    totalProducts = productHeaders.size();

    
    System.out.println("TOTAL PRODUCTS FOUND : " + totalProducts);

   
    // 3. PROCESS EVERY PRODUCT
   
    for (int i = 0; i < totalProducts; i++) {

        try {

            productHeaders = driver.findElements(
                    By.xpath(productAccordionXpath)
            );

            if (i >= productHeaders.size()) {

                System.out.println(
                        "⚠ Product index not available : " + i
                );

                skippedProducts++;
                continue;
            }

            WebElement productHeader = productHeaders.get(i);

            // =================================================
            // PRODUCT NAME
            // =================================================

            String productName;

            try {

                productName = productHeader.findElement(
                        By.xpath(
                                ".//div[contains(@class,'product-name')]"
                        )
                ).getText().trim();

            } catch (Exception e) {

                productName = "Product " + (i + 1);
            }

            System.out.println(
                    "----------------------------------------------"
            );        
            System.out.println(
                    "PRODUCT " +
                    (i + 1)                  
            );
            // =================================================
            // SCROLL PRODUCT
            // =================================================

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    productHeader
            );

            Thread.sleep(500);

            // =================================================
            // GET ACCORDION ROOT
            // =================================================

            WebElement accordionRoot =
                    productHeader.findElement(
                            By.xpath(
                                    "./ancestor::div[contains(@class,'MuiAccordion-root')][1]"
                            )
                    );

            // =================================================
            // CHECK ACCORDION
            // =================================================

            String expanded =
                    productHeader.getAttribute("aria-expanded");

            if ("false".equalsIgnoreCase(expanded)) {

                WebElement toggle =
                        productHeader.findElement(
                                By.xpath(
                                        ".//div[contains(@class,'MuiAccordionSummary-expandIconWrapper')]"
                                )
                        );

                js.executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        toggle
                );

                Thread.sleep(500);

                toggle.click();

                Thread.sleep(1200);
            }

            // =================================================
            // FIND SELLER TABLE
            // =================================================

            WebElement sellerTable;

            try {

                sellerTable =
                        accordionRoot.findElement(
                                By.xpath(".//table")
                        );

            } catch (Exception e) {

                System.out.println(
                        "⚠ Seller table not found : " +
                        productName
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // GET SELLER HEADERS
            // =================================================

            List<WebElement> sellerHeaders =
                    sellerTable.findElements(
                            By.xpath(".//thead/tr/th")
                    );

            int sellerCount =
                    sellerHeaders.size() - 1;

            // =================================================
            // NEED AT LEAST 2 SELLERS
            // =================================================

            if (sellerCount < 2) {

                System.out.println(
                        "⚠ Olny 1 sellers available for this product : " +
                        productName
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // PRINT ALL SELLERS
            // =================================================

            for (int j = 1; j < sellerHeaders.size(); j++) {

                String sellerName =
                        sellerHeaders.get(j)
                                .getText()
                                .replaceAll("\\s+", " ")
                                .trim();

 //               System.out.println(
 //                       "SELLER[" + j + "] : " +
 //                       sellerName
 //               );
            }

            // =================================================
            // GET FIRST DATA ROW
            // =================================================

            WebElement firstRow =
                    sellerTable.findElement(
                            By.xpath(".//tbody/tr[1]")
                    );

            List<WebElement> cells =
                    firstRow.findElements(
                            By.xpath("./th | ./td")
                    );

            if (cells.size() < 2) {

                System.out.println(
                        "⚠ Seller cells not found"
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // IMPORTANT
            //
            // FIND ACTUAL OLD SELLER USING QUANTITY
            //
            // The selected checkbox itself is not reliable.
            // The actual seller is the seller column where
            // quantity exists.
            // =================================================

            int oldSellerIndex = -1;
            String oldSeller = "";
            String oldQuantity = "";

            for (int j = 1;
                 j < cells.size() && j < sellerHeaders.size();
                 j++) {

                WebElement cell = cells.get(j);

                String cellText =
                        cell.getText()
                                .replaceAll("\\s+", " ")
                                .trim();

                String sellerName =
                        sellerHeaders.get(j)
                                .getText()
                                .replaceAll("\\s+", " ")
                                .trim();

//                System.out.println(
//                        "SELLER[" + j + "] = " +
//                        sellerName +
//                        " | CELL = " +
//                        cellText
//                );

                // -------------------------------------------------
                // IMPORTANT:
                // Ignore empty cells and Total row.
                // A real seller cell contains quantity such as:
                //
                // 0.01 (Kg)
                // 3.01 (Pieces)
                // 12.03 (Kg)
                // -------------------------------------------------

                if (!cellText.isEmpty()
                        && cellText.matches(
                                ".*\\d+(\\.\\d+)?\\s*\\(.*\\).*"
                        )) {

                    oldSellerIndex = j;
                    oldSeller = sellerName;
                    oldQuantity = cellText;

                    break;
                }
            }

            // =================================================
            // OLD SELLER NOT FOUND
            // =================================================

            if (oldSellerIndex == -1) {

                System.out.println(
                        "❌ Actual old seller could not be found"
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // FIND RANDOM NEW SELLER
            //
            // IMPORTANT:
            // Do NOT select old seller.
            // Select any random seller from the remaining
            // seller columns.
            // =================================================

            List<Integer> availableSellerIndexes =
                    new ArrayList<>();

            for (int j = 1;
                 j < sellerHeaders.size() && j < cells.size();
                 j++) {

                // Skip OLD seller
                if (j == oldSellerIndex) {
                    continue;
                }

                String sellerName =
                        sellerHeaders.get(j)
                                .getText()
                                .replaceAll("\\s+", " ")
                                .trim();

                if (!sellerName.isEmpty()) {

                    availableSellerIndexes.add(j);
                }
            }

            if (availableSellerIndexes.isEmpty()) {

                System.out.println(
                        "⚠ No other seller available"
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // RANDOM SELLER
            // =================================================

            int randomPosition =
                    random.nextInt(
                            availableSellerIndexes.size()
                    );

            int newSellerIndex =
                    availableSellerIndexes.get(randomPosition);

            String newSeller =
                    sellerHeaders.get(newSellerIndex)
                            .getText()
                            .replaceAll("\\s+", " ")
                            .trim();

            
/*            System.out.println(
                    "----------------------------------------------"
            );

            System.out.println(
                    "OLD SELLER : " +
                    oldSeller
            );
            
            System.out.println(
                    "NEW SELLER : " +
                    newSeller
            );         

            System.out.println(
                    "----------------------------------------------"
            );*/

         
            // =================================================
            // RE-FIND NEW SELLER CELL
            // =================================================

            sellerTable =
                    accordionRoot.findElement(
                            By.xpath(".//table")
                    );

            firstRow =
                    sellerTable.findElement(
                            By.xpath(".//tbody/tr[1]")
                    );

            cells =
                    firstRow.findElements(
                            By.xpath("./th | ./td")
                    );

            WebElement newSellerCell =
                    cells.get(newSellerIndex);

            // =================================================
            // HIGHLIGHT NEW SELLER
            // =================================================

            js.executeScript(
                    "arguments[0].style.border='3px solid blue';" +
                    "arguments[0].style.backgroundColor='lightblue';",
                    newSellerCell
            );

            Thread.sleep(500);

            // =================================================
            // SCROLL NEW SELLER
            // =================================================

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                    newSellerCell
            );

            Thread.sleep(700);

            // =================================================
            // FIND MUI CHECKBOX
            // =================================================

            WebElement newSellerCheckbox;

            try {

                newSellerCheckbox =
                        newSellerCell.findElement(
                                By.xpath(
                                        ".//input[@type='checkbox']"
                                )
                        );

            } catch (Exception e) {

                System.out.println(
                        "❌ Checkbox not found for NEW seller : " +
                        newSeller
                );

                skippedProducts++;
                continue;
            }

            // =================================================
            // FIND MUI CHECKBOX CONTAINER
            // =================================================

            WebElement newSellerContainer = null;

            try {

                newSellerContainer =
                        newSellerCell.findElement(
                                By.xpath(
                                        ".//span[contains(@class,'MuiButtonBase-root')]"
                                )
                        );

            } catch (Exception e) {

                try {

                    newSellerContainer =
                            newSellerCell.findElement(
                                    By.xpath(
                                            ".//span[contains(@class,'MuiCheckbox-root')]"
                                    )
                            );

                } catch (Exception ex) {

                    System.out.println(
                            "⚠ MUI checkbox container not found"
                    );
                }
            }

            // =================================================
            // CLICK NEW SELLER
            // =================================================

            if (newSellerContainer != null) {

                try {

                    newSellerContainer.click();

                } catch (Exception e) {

                    System.out.println(
                            "⚠ Normal click failed. Trying Actions click."
                    );

                    new org.openqa.selenium.interactions.Actions(driver)
                            .moveToElement(newSellerContainer)
                            .click()
                            .perform();
                }

            } else {

                System.out.println(
                        "👉 Clicking checkbox input"
                );

                try {

                    newSellerCheckbox.click();

                } catch (Exception e) {

                    js.executeScript(
                            "arguments[0].click();",
                            newSellerCheckbox
                    );
                }
            }

            // =================================================
            // WAIT FOR REACT
            // =================================================

            Thread.sleep(1500);

            // =================================================
            // RE-FIND TABLE AFTER REACT UPDATE
            // =================================================

            sellerTable =
                    accordionRoot.findElement(
                            By.xpath(".//table")
                    );

            firstRow =
                    sellerTable.findElement(
                            By.xpath(".//tbody/tr[1]")
                    );

            cells =
                    firstRow.findElements(
                            By.xpath("./th | ./td")
                    );

            // =================================================
            // GET FINAL OLD CHECKBOX
            // =================================================

            WebElement latestOldCheckbox =
                    cells.get(oldSellerIndex).findElement(
                            By.xpath(
                                    ".//input[@type='checkbox']"
                            )
                    );

            // =================================================
            // GET FINAL NEW CHECKBOX
            // =================================================

            WebElement latestNewCheckbox =
                    cells.get(newSellerIndex).findElement(
                            By.xpath(
                                    ".//input[@type='checkbox']"
                            )
                    );

            // =================================================
            // VALIDATE
            // =================================================

//            System.out.println(
//                    "CHANGE SELLER VALIDATION"
//            );

 //          System.out.println(
 //                   "----------------------------------------------"
 //           );

            System.out.println(
                    "PRODUCT NAME  : " +
                    productName
            );

            System.out.println(
                    "Old Seller    : " +
                    oldSeller
            );         

            System.out.println(
                    "New Seller    : " +
                    newSeller
            );

//            System.out.println(
//                    "----------------------------------------------"
//            );  


            if (!latestOldCheckbox.isSelected()
                    && latestNewCheckbox.isSelected()) {

                System.out.println();
                System.out.println(
                        "✅ SELLER CHANGED SUCCESSFULLY"
                );

                System.out.println(
                        oldSeller +
                        "  --->  " +
                        newSeller
                );

                changedProducts++;

                changeDetails.add(
                        "Product: " +
                        productName +
                        " | OLD SELLER: " +
                        oldSeller +
                        " | NEW SELLER: " +
                        newSeller
                );

            } else {

                skippedProducts++;
            }

            Thread.sleep(1000);

        } catch (StaleElementReferenceException e) {

            System.out.println();
            System.out.println(
                    "⚠ Product " +
                    (i + 1) +
                    " became stale."
            );

            System.out.println(
                    "⚠ React refreshed the DOM."
            );

            skippedProducts++;

            Thread.sleep(700);

        } catch (Exception e) {

            System.out.println(
                    "Product : " +
                    productNameSafe(i)
            );

            System.out.println(
                    "Reason : " +
                    e.getMessage()
            );

            skippedProducts++;

            Thread.sleep(700);
        }
    }

    // =========================================================
    // FINAL SUMMARY
    // =========================================================

    for (String detail : changeDetails) {

        System.out.println(
                "✅ " +
                detail
        );
    }

    System.out.println("================================================");

    // =========================================================
    // SUBMIT ONLY IF ACTUAL CHANGES EXIST
    // =========================================================

    if (changedProducts > 0) {

        System.out.println();
        System.out.println(
                "👉 Submitting " +
                changedProducts +
                " seller changes..."
        );

        WebElement submitBtn =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                        "//button[normalize-space()='Submit']"
                                )
                        )
                );

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                submitBtn
        );

        Thread.sleep(700);

        submitBtn.click();

        System.out.println(
                "✅ Submit clicked"
        );

        Thread.sleep(1500);

        // =====================================================
        // CONFIRM
        // =====================================================

        try {

            WebElement confirmBtn =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//button[normalize-space()='Confirm']"
                                    )
                            )
                    );

            confirmBtn.click();

            System.out.println(
                    "✅ Confirm clicked"
            );

            Thread.sleep(1500);

        } catch (Exception e) {

            System.out.println(
                    "⚠ Confirm button not displayed"
            );
        }

        // =====================================================
        // OK
        // =====================================================

        try {

            WebElement okBtn =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//button[normalize-space()='OK']"
                                    )
                            )
                    );

            okBtn.click();

            System.out.println(
                    "✅ OK clicked"
            );

            Thread.sleep(1000);

        } catch (Exception e) {

            System.out.println(
                    "⚠ OK button not displayed"
            );
        }

        System.out.println();
        System.out.println(
                "✅ SELLER CHANGE COMPLETED SUCCESSFULLY"
        );

    } else {

//        System.out.println();
//        System.out.println(
//                "⚠ NO SELLER CHANGES WERE MADE."
//        );
    }
}
private String productNameSafe(int index) {

    try {

        List<WebElement> products = driver.findElements(
                By.xpath(
                        "//div[contains(@class,'MuiAccordion-root')]"
                      + "//div[contains(@class,'MuiAccordionSummary-root')]"
                      + "[.//div[contains(@class,'product-name')]]"
                )
        );

        if (index < products.size()) {

            return products.get(index)
                    .findElement(
                            By.xpath(
                                    ".//div[contains(@class,'product-name')]"
                            )
                    )
                    .getText()
                    .trim();
        }

    } catch (Exception e) {
        // Ignore
    }

    return "Product " + (index + 1);
}

public void submitActions() throws InterruptedException {

wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//button[normalize-space()='Submit']")
      )
).click();

wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//button[normalize-space()='Confirm']")
      )
).click();

wait.until(
      ExpectedConditions.elementToBeClickable(
              By.xpath("//button[normalize-space()='OK']")
      )
).click();

}



//public String getSellerName() {
//
//    // Wait for table
//    WebElement table = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(By.xpath("//table"))
//    );
//
//    // Find Seller Name column index
//    List<WebElement> headers = table.findElements(By.xpath(".//thead//th"));
//    int sellerIndex = -1;
//
//    for (int i = 0; i < headers.size(); i++) {
//        if (headers.get(i).getText().trim().equalsIgnoreCase("Seller Name")) {
//            sellerIndex = i + 1;
// //           break;
//        }
//    }
//
//    if (sellerIndex == -1) {
//        throw new RuntimeException("Seller Name column not found");
//    }
//
//    // Read seller from first row
//    WebElement sellerCell = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//table//tbody//tr[1]/td[" + sellerIndex + "]"))
//    );
//
//    return sellerCell.getText().trim();
//}
//
//public String changeSellerAndGetNewSeller() {
//
//    String oldSeller = getSellerName();
//    System.out.println("Old Seller: " + oldSeller);
//
//    // Click Change Seller
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//button[normalize-space()='Change Seller']"))).click();
//
//    // Wait for seller list
//    List<WebElement> sellers = wait.until(
//            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//                    By.xpath("//table//tbody//td[@style='cursor: pointer;']"))
//    );
//
//    WebElement selectedSeller = null;
//
//    for (WebElement seller : sellers) {
//        if (seller.getAttribute("class").contains("Mui-selected")) {
//            selectedSeller = seller;
//            break;
//        }
//    }
//
//    // Select next seller
//    for (WebElement seller : sellers) {
//        if (!seller.equals(selectedSeller)) {
//            seller.click();
//            break;
//        }
//    }
//
//    // Submit
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//button[normalize-space()='Submit']"))).click();
//
//    // Confirm
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//button[normalize-space()='Confirm']"))).click();
//
//    // Click OK
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//button[normalize-space()='OK']"))).click();
//
//    // Wait until seller value changes
//    wait.until(ExpectedConditions.not(
//            ExpectedConditions.textToBePresentInElementLocated(
//                    By.xpath("//table//tbody//tr[1]"), oldSeller)));
//
//    String newSeller = getSellerName();
//    System.out.println("New Seller: " + newSeller);
//
//    return newSeller;
//}



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

//Add product on 2nd page.
public void addMoreProductOn2ndPage() throws InterruptedException {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	WebElement addProduct = driver.findElement(By.xpath("//button[normalize-space()='Add More Products']"));
	Thread.sleep(500);
	addProduct.click();
	
	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(3));

	WebElement dropdown = wait1.until(
	        ExpectedConditions.elementToBeClickable(
	                By.xpath("//select[contains(@class,'custom-select')]")
	        )
	);

	Select select = new Select(dropdown);
	select.selectByVisibleText("Default Fresh On Table");
//	select.selectByValue("0");
	Thread.sleep(2000);
}

//select product
public void selectRandomProduct() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // 1️⃣ Wait for loader to disappear
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//svg[contains(@class,'circular')]")
    ));

    // 2️⃣ Open dropdown using JS (important)
    WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='select-cont']")
            )
    );
    js.executeScript("arguments[0].click();", dropdown);

    // 3️⃣ Wait until options are present
    List<WebElement> products = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@id='myDropdown']//div[not(.//input) and normalize-space()]")
            )
    );

    // 4️⃣ Pick random product
    WebElement randomProduct =
            products.get(new Random().nextInt(products.size()));

    System.out.println("Add more product: " + randomProduct.getText());

    // 5️⃣ JS click (THIS FIXES YOUR ERROR)
    js.executeScript("arguments[0].click();", randomProduct);
}
	
//select seller
	    public void selectRandomSeller() throws InterruptedException {

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	        WebElement sellerDropdown = wait.until(
	                ExpectedConditions.presenceOfElementLocated(
	                        By.name("addmoreproducts-seller")));

	        Select select = new Select(sellerDropdown);

	        List<WebElement> options = select.getOptions();

	        // Remove "Seller" / "Select"
	        options.removeIf(opt ->
	                opt.getText().equalsIgnoreCase("Seller")
	                        || opt.getText().equalsIgnoreCase("Select"));

	        if (options.isEmpty()) {
	            throw new RuntimeException("❌ No sellers available");
	        }
            Thread.sleep(1000);
	        Random random = new Random();
	        WebElement randomSeller = options.get(random.nextInt(options.size()));
	        Thread.sleep(1000);
	        select.selectByVisibleText(randomSeller.getText());
	    
	}
	    public void selectRandomUOM() throws InterruptedException {

	        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));

	        WebElement uomDropdown = wait1.until(
	                ExpectedConditions.presenceOfElementLocated(
	                        By.name("addmoreproducts-uom")));

	        Select select = new Select(uomDropdown);

	        List<WebElement> options1 = select.getOptions();

	        // Remove "Seller" / "Select"
	        options1.removeIf(opt ->
	                opt.getText().equalsIgnoreCase("UoM")
	                        || opt.getText().equalsIgnoreCase("Select"));

	        if (options1.isEmpty()) {
	            throw new RuntimeException("❌ No sellers available");
	        }

	        Random random = new Random();
	        WebElement randomUOM = options1.get(random.nextInt(options1.size()));

	        select.selectByVisibleText(randomUOM.getText());	        
	//Enter qty
	        Thread.sleep(2000);
	        WebElement qtyInput = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.name("addmoreproducts-prdqty"))); // update locator if needed

	        // Generate random qty between 1 and 100
	        int randomQty = new Random().nextInt(100) + 1;

	        qtyInput.clear();
	        qtyInput.sendKeys(String.valueOf(randomQty));

	        System.out.println("✅ Quantity entered: " + randomQty);
	        
	        Thread.sleep(2000);
	        WebElement clickSbtBtn = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
	        clickSbtBtn.click();
	        Thread.sleep(1000);
	        WebElement clickOKBtn = driver.findElement(By.xpath("//button[normalize-space()='OK']"));
	        clickOKBtn.click();
	        
	    
	}

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

public void selectRandomDeliveryHub() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Random random = new Random();

    int lpoCount = driver.findElements(
            By.xpath("//div[contains(@class,'lpo-accordion')]")
    ).size();

    System.out.println("Total LPOs Created: " + lpoCount);

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

//        System.out.println(
//                "LPO " + (i + 1) + " → Selected Delivery Hub: " +
//                select.getFirstSelectedOption().getText()
//        );
    }
    }
    
    public void selectSingleDeliveryHub() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int lpoCount = driver.findElements(
                By.xpath("//div[contains(@class,'lpo-accordion')]")
        ).size();

        System.out.println("Total LPOs Created: " + lpoCount);

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
                            By.xpath(
                                    "(//div[contains(@class,'lpo-accordion')])[" +
                                    (i + 1) +
                                    "]//select[@name='del_hub_id_select']"
                            )
                    )
            );

            Select select = new Select(deliveryHubDropdown);

            // Always select Warehouse4A
            select.selectByVisibleText(
                    "Warehouse4A, Belhasa Compound, Al Quoz Industrial Area 3, Dubai, UAE"
            );

            System.out.println(
                    "LPO " + (i + 1) +
                    " → Selected Delivery Hub: " +
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
	
//	createdLpoId = captureLPOId();
//
//	System.out.println("Created LPO ID: " + createdLpoId);
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

public List<String> captureLPOIds() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement popup = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.react-confirm-alert-body")
            )
    );

    String popupText = popup.getText();

    // Capture ALL LPO IDs
    Pattern pattern = Pattern.compile("FPO\\d+");
    Matcher matcher = pattern.matcher(popupText);

    List<String> lpoIds = new ArrayList<>();

    while (matcher.find()) {
        lpoIds.add(matcher.group());
    }

    if (lpoIds.isEmpty()) {
        throw new RuntimeException(
                "❌ Could not capture any LPO ID from popup: " + popupText
        );
    }

//    System.out.println("======================================");
//    System.out.println("Total LPOs Captured: " + lpoIds.size());
//
//    for (String lpoId : lpoIds) {
//        System.out.println("Captured LPO ID: " + lpoId);
//    }
//
//    System.out.println("======================================");

    return lpoIds;
}


  public String captureLPOId() {
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.react-confirm-alert-body")));
	
	String popupText = driver.findElement(By.cssSelector("div.react-confirm-alert-body")).getText();

 //   String popupText = popup.getText();

    Pattern pattern = Pattern.compile("\\[\\s*(FPO\\d+)\\s*\\]");
    Matcher matcher = pattern.matcher(popupText);

    if (!matcher.find()) {
        throw new RuntimeException(
                "❌ Could not capture LPO ID from popup: " + popupText
        );
    }

    String lpoId = matcher.group(1);

    
    System.out.println("Captured LPO ID: " + lpoId);

    return lpoId;
}


// Change LPO status or Accepted But Unfulfilled
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

		processSingleLpo1(i);
		processed++;
	}
}

private void processSingleLpo1(int rowIndex) throws InterruptedException {

	changeStatus1(rowIndex, "Accepted But Unfulfilled");
	changeStatus1(rowIndex, "LPO At Hub");
	Lpofulfillment1(rowIndex);
	enterInvoiceDetails1(rowIndex);
//	veryfyAndApprove(rowIndex);
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

}
public void Lpofulfillment1(int rowIndex) throws InterruptedException {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

	WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
//	Thread.sleep(1000);
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
	                        "//div[contains(@class,'react-datepicker__day--today')]" 
	                )
	        )
	); //div[contains(@class,'react-datepicker__day--today')]" +
    //"/following-sibling::div[not(contains(@class,'react-datepicker__day--disabled'))][1]"
Thread.sleep(1000);
	nextDay.click();

	System.out.println("LPO Invoice date selected: Next day");


	 Thread.sleep(2000);

	 // generate 2-digit supplier unit price (10–99)
	 int supplierUnitPrice = random.nextInt(90) + 10;

	 WebElement supplierPriceInput = driver.findElement(
	         By.xpath(".//div[contains(@class,'supplierUnitPrice')]//input")
	 );
	 supplierPriceInput.clear();
	 supplierPriceInput.sendKeys(String.valueOf(supplierUnitPrice));

	 // trigger UI commit
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript(
	         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	         supplierPriceInput
	 );
	 try {

	        WebElement popup = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//div[contains(text(),'Supplier Unit Price entered is higher')]")
	                )
	        );

	        // Click OK button
	        popup.findElement(
	                By.xpath(".//button[normalize-space()='OK']")
	        ).click();


	    } catch (TimeoutException e) {
	        // Popup did not appear → continue
	        System.out.println("✅ supplier price is less then Lpo price No popup");
	    }

	 // ================= READ QUANTITY =================
	 WebElement qtyInput = driver.findElement(
	         By.xpath("//input[@type='number' and contains(@class,'qty')]")
	 );

	 double quantity = Double.parseDouble(qtyInput.getAttribute("value"));

	 // ================= CALCULATE INVOICE VALUE =================
	 double invoiceValue = supplierUnitPrice * quantity;

	 // round to 2 decimals (safe for currency)
	 invoiceValue = Math.round(invoiceValue * 100.0) / 100.0;

	 // ================= ENTER INVOICE VALUE =================
	 WebElement invoiceValueInput = driver.findElement(
	         By.xpath("//input[contains(@name,'sup_inv_val')]")
	 );

	 invoiceValueInput.clear();
	 invoiceValueInput.sendKeys(String.valueOf(invoiceValue));

	 // commit invoice value
	 js.executeScript(
	         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	         invoiceValueInput
	 );

	 System.out.println("✅ Supplier Unit Price: " + supplierUnitPrice);
	 System.out.println("✅ Quantity: " + quantity);
	 System.out.println("✅ Invoice Value Entered: " + invoiceValue);
	 
	 
	 
	// 4️⃣ RATING – random star click (1 to 5 stars)
			// ----------------------------
			int star = new Random().nextInt(5) + 1;

			WebElement ratingStar = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//label[contains(@class,'css-ykqdxu')])[" + star + "]")));

			ratingStar.click();
			Thread.sleep(1000);
			
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
			Thread.sleep(2000);
			/* 5️⃣ Click target date */
			WebElement targetDateElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'e-day') and "
							+ "not(contains(@class,'e-disabled')) and " + "text()='" + targetDay + "']")));
			targetDateElement.click();

			System.out.println("Expiry date selected for batch: " + targetDate);
			
			driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
			driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
			Thread.sleep(1000);
			 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
}	        
	public void completeLPO(int rowIndex, String statusText) throws InterruptedException  {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	// Click correct row "More"
	WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	Thread.sleep(500);
	moreBtn.click();

	// Click Change Status from SAME row
	WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Verify & Approve')]")));
	changeStatus.click();

	driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
	driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	Thread.sleep(1000);
	 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
}
	
	
/////Partially Accepted	
	public void partially_Accepted(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processLPO(i);
			processed++;
		}
	}
	
	private void processLPO(int rowIndex) throws InterruptedException {

		changeStatus(rowIndex, "Partially Accepted");
		changeStatus(rowIndex, "LPO At Hub");
		fulfillLPO(rowIndex);
		processFulfillmentLPO(rowIndex);
//		veryfyAndApprove(rowIndex);
	}
	
	private void changeStatus(int rowIndex, String statusText) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Click "More"
	    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	    moreBtn.click();

	    // Click Change Status
	    WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
	    changeStatus.click();

	    // Select status
	    Select select = new Select(wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
	    select.selectByVisibleText(statusText);

	    driver.findElement(By.xpath("//textarea[@name='sName']"))
	            .sendKeys("Status updated automatically");

	    // ================= ONLY FOR PARTIALLY ACCEPTED =================
	    if (statusText.equalsIgnoreCase("Partially Accepted")) {

	        // Wait for modal
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'status-change-modal')]")
	        ));

	        List<WebElement> qtyInputs = wait.until(
	                ExpectedConditions.presenceOfAllElementsLocatedBy(
	                        By.xpath("//div[contains(@class,'status-change-modal')]//input[@name='prdQty']")
	                )
	        );

	        for (WebElement input : qtyInputs) {

	            js.executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	            String value = input.getAttribute("value");
	            if (value == null || value.isEmpty()) continue;

	            double existingQty = Double.parseDouble(value);

	            double newQty = existingQty > 1
	                    ? existingQty - 1
	                    : existingQty * 0.5;

	            input.click();
	            input.sendKeys(Keys.CONTROL + "a");
	            input.sendKeys(Keys.BACK_SPACE);
	            input.sendKeys(String.valueOf(newQty));

	            // 🔥 trigger backend
	            js.executeScript(
	                    "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	                    "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	                    "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	                    input
	            );
	        }
	    }

	    // ================= SUBMIT =================
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Submit']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Confirm']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[text()='OK']"))).click();
	    Thread.sleep(2000);
	}

	
	public void fulfillLPO(int rowIndex) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
//		Thread.sleep(1000);
		moreBtn.click();

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
		option.click();
		
	}


	public void processFulfillmentLPO(int rowIndex) throws InterruptedException {
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
		// ================= SUPPLIER UNIT PRICE =================


		 // generate 2-digit supplier unit price (10–99)
		 int supplierUnitPrice = random.nextInt(90) + 10;

		 WebElement supplierPriceInput = driver.findElement(
		         By.xpath(".//div[contains(@class,'supplierUnitPrice')]//input")
		 );
		 supplierPriceInput.clear();
		 supplierPriceInput.sendKeys(String.valueOf(supplierUnitPrice));

		 // trigger UI commit
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript(
		         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
		         supplierPriceInput
		 );
		 try {

		        WebElement popup = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//div[contains(text(),'Supplier Unit Price entered is higher')]")
		                )
		        );

		        // Click OK button
		        popup.findElement(
		                By.xpath(".//button[normalize-space()='OK']")
		        ).click();


		    } catch (TimeoutException e) {
		        // Popup did not appear → continue
//		        System.out.println("✅ No supplier price popup");
		    }

		 // ================= READ QUANTITY =================
		 WebElement qtyInput = driver.findElement(
		         By.xpath("//input[@type='number' and contains(@class,'qty')]")
		 );

		 double quantity = Double.parseDouble(qtyInput.getAttribute("value"));

		 // ================= CALCULATE INVOICE VALUE =================
		 double invoiceValue = supplierUnitPrice * quantity;

		 // round to 2 decimals (safe for currency)
		 invoiceValue = Math.round(invoiceValue * 100.0) / 100.0;

		 // ================= ENTER INVOICE VALUE =================
		 WebElement invoiceValueInput = driver.findElement(
		         By.xpath("//input[contains(@name,'sup_inv_val')]")
		 );

		 invoiceValueInput.clear();
		 invoiceValueInput.sendKeys(String.valueOf(invoiceValue));

		 // commit invoice value
		 js.executeScript(
		         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
		         invoiceValueInput
		 );

		 System.out.println("✅ Supplier Unit Price: " + supplierUnitPrice);
		 System.out.println("✅ Quantity: " + quantity);
		 System.out.println("✅ Invoice Value Entered: " + invoiceValue);
		 
		 
		 
		// 4️⃣ RATING – random star click (1 to 5 stars)
				// ----------------------------
				int star = new Random().nextInt(5) + 1;

				WebElement ratingStar = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//label[contains(@class,'css-ykqdxu')])[" + star + "]")));

				ratingStar.click();
				Thread.sleep(1000);
				
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
				Thread.sleep(2000);
				/* 5️⃣ Click target date */
				WebElement targetDateElement = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'e-day') and "
								+ "not(contains(@class,'e-disabled')) and " + "text()='" + targetDay + "']")));
				Thread.sleep(500);
				targetDateElement.click();

				System.out.println("Expiry date selected: " + targetDate);
				
				 // Mandatory receiving comment
	            Select comment = new Select(
	                    driver.findElement(
	                            By.xpath(".//div[contains(@class,'receiving-comments-select')]//select")
	                    )
	            );
	            comment.selectByIndex(1);
	            
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
				driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
				Thread.sleep(1000);
				 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
	}	  
	
	///Rjected LPO
	public void rejectedLPO(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processLPO_Rejected(i);
			processed++;
		}
	}
	
	private void processLPO_Rejected(int rowIndex) throws InterruptedException {

		changeLpoStatus_Rejected(rowIndex, "Rejected");

	}
	
	private void changeLpoStatus_Rejected(int rowIndex, String statusText) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Click "More"
	    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	    moreBtn.click();

	    // Click Change Status
	    WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
	    changeStatus.click();

	    // Select status
	    Select select = new Select(wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
	    select.selectByVisibleText(statusText);

	    driver.findElement(By.xpath("//textarea[@name='sName']"))
	            .sendKeys("Status updated automatically");
	    
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Submit']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Confirm']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[text()='OK']"))).click();
	}
	
	public void CancelledLPO(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processLPO_Cancelled(i);
			processed++;
		}
	}
	
	private void processLPO_Cancelled(int rowIndex) throws InterruptedException {

		changeLpoStatus_Cancelled(rowIndex, "Cancelled");

	}
	
	private void changeLpoStatus_Cancelled(int rowIndex, String statusText) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Click "More"
	    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	    moreBtn.click();

	    // Click Change Status
	    WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
	    changeStatus.click();

	    // Select status
	    Select select = new Select(wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
	    select.selectByVisibleText(statusText);

	    driver.findElement(By.xpath("//textarea[@name='sName']"))
	            .sendKeys("Status updated automatically");
	    
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Submit']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Confirm']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[text()='OK']"))).click();
	}
	
	private String selectedProductName;

	public String getSelectedProductName() {
	    return selectedProductName;
	}
	
//public String selectproducts_inLPOpage() {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//
//	    List<WebElement> rows = wait.until(
//	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
//	                    By.xpath("//table//tbody//tr")
//	            )
//	    );
//
//	    for (WebElement row : rows) {
//
//	        WebElement checkbox = row.findElement(By.xpath("//input[@class='grid-checkbox-input  cursor-pointer']"));
//	        WebElement productCell = row.findElement(By.xpath(".//td[3]"));
//	        String productName = productCell.getText().trim();
//
//	        if (!checkbox.isSelected()) {
//	            js.executeScript("arguments[0].click();", checkbox);
//	            System.out.println("✅ Selected Product: " + productName);
//	            return productName; // 🔥 RETURN
//	        }
//	    }
//
//	    throw new RuntimeException("❌ No product selected");
//	}
	public String selectproducts_inLPOpage() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    By rowsLocator = By.xpath("//table/tbody/tr");

	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator));

	    int rowCount = driver.findElements(rowsLocator).size();

	    for (int i = 1; i <= rowCount; i++) {

	        try {

	            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("(//table/tbody/tr)[" + i + "]//input[@type='checkbox']")));

	            WebElement productCell = driver.findElement(
	                    By.xpath("(//table/tbody/tr)[" + i + "]/td[3]"));

	            String productName = productCell.getText().trim();

	            if (!checkbox.isSelected()) {

	                js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
	                js.executeScript("arguments[0].click();", checkbox);

	                System.out.println("Selected Product : " + productName);

	                return productName;
	            }

	        } catch (StaleElementReferenceException e) {

	            System.out.println("Row refreshed. Retrying row " + i);

	            i--; // retry same row
	        }
	    }

	    throw new RuntimeException("No product found.");
	}
	
	public void searchProductAndValidate(String productName) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    driver.findElement(By.xpath("//button[normalize-space()='Create LPOs']")).click();

	    By productFilterIcon = By.xpath("(//div[@class='custom-filter-icon'])[1]");
	    By productSearchInput = By.xpath("(//input[@type='text'])[3]");

//	    wait.until(ExpectedConditions.elementToBeClickable(productFilterIcon)).click();
	    Thread.sleep(1000);
	    driver.findElement(productFilterIcon).click();

	    WebElement searchBox = driver.findElement(productSearchInput
	    );

//	    searchBox.clear();
	    searchBox.sendKeys(productName);
	    searchBox.sendKeys(Keys.ENTER);

	    System.out.println("🔍 Searching product: " + productName);
	}
	
	public void validateProductSearchResult(String productName) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    List<WebElement> productCells = driver.findElements(By.xpath("//table//tbody//tr//td[3]")
	            
	    );

	    boolean found = productCells.stream()
	            .anyMatch(e -> e.getText().trim().equalsIgnoreCase(productName));

	    Assert.assertTrue(found,
	            "❌ Product NOT found in list after search: " + productName);

	    System.out.println("✅ Product found in list: " + productName);
	}
	
	
	
////Add more batch after quality check

	public void processAddMore_Batches(int maxCount) throws InterruptedException {

		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
		int processed = 0;

		for (int i = 1; i <= rows.size(); i++) {

			if (processed == maxCount)
				break;

			String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

			if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
				continue;
			}

			processAddMore(i);
			processed++;
		}
	}

	private void processAddMore(int rowIndex) throws InterruptedException {

		changeStatusforLPO(rowIndex, "Accepted But Unfulfilled");
		changeStatusforLPO(rowIndex, "LPO At Hub");
		LpofulfillmentProcess(rowIndex);
		enterInvoiceDetailsCompletly(rowIndex);
		veryfyAndApprove(rowIndex);
		addMoreBatches(rowIndex);
		
				
	}

	private void changeStatusforLPO(int rowIndex, String statusText) throws InterruptedException  {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		Thread.sleep(2000);
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Change Status')]")));
		changeStatus.click();

		// Select new status

		Select select = new Select(
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("selectedStatus"))));
		select.selectByVisibleText(statusText);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//textarea[@name='sName']")).sendKeys("Status updated automatically");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		Thread.sleep(1000);
		 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();

	}
	public void LpofulfillmentProcess(int rowIndex) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
		Thread.sleep(1000);
		moreBtn.click();

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
		Thread.sleep(1000);
		option.click();
	}

	public void enterInvoiceDetailsCompletly(int rowIndex) throws InterruptedException {
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

		 // generate 2-digit supplier unit price (10–99)
		 int supplierUnitPrice = random.nextInt(90) + 10;

		 WebElement supplierPriceInput = driver.findElement(
		         By.xpath(".//div[contains(@class,'supplierUnitPrice')]//input")
		 );
		 supplierPriceInput.clear();
		 supplierPriceInput.sendKeys(String.valueOf(supplierUnitPrice));

		 // trigger UI commit
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript(
		         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
		         supplierPriceInput
		 );
		 try {

		        WebElement popup = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//div[contains(text(),'Supplier Unit Price entered is higher')]")
		                )
		        );

		        // Click OK button
		        popup.findElement(
		                By.xpath(".//button[normalize-space()='OK']")
		        ).click();


		    } catch (TimeoutException e) {
		        // Popup did not appear → continue
		        System.out.println("✅ No supplier price popup");
		    }

		 // ================= READ QUANTITY =================
		 WebElement qtyInput = driver.findElement(
		         By.xpath("//input[@type='number' and contains(@class,'qty')]")
		 );

		 double quantity = Double.parseDouble(qtyInput.getAttribute("value"));

		 // ================= CALCULATE INVOICE VALUE =================
		 double invoiceValue = supplierUnitPrice * quantity;

		 // round to 2 decimals (safe for currency)
		 invoiceValue = Math.round(invoiceValue * 100.0) / 100.0;

		 // ================= ENTER INVOICE VALUE =================
		 WebElement invoiceValueInput = driver.findElement(
		         By.xpath("//input[contains(@name,'sup_inv_val')]")
		 );

		 invoiceValueInput.clear();
		 invoiceValueInput.sendKeys(String.valueOf(invoiceValue));

		 // commit invoice value
		 js.executeScript(
		         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
		         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
		         invoiceValueInput
		 );

		 System.out.println("✅ Supplier Unit Price: " + supplierUnitPrice);
		 System.out.println("✅ Quantity: " + quantity);
		 System.out.println("✅ Invoice Value Entered: " + invoiceValue);
		 
		 
		 
		// 4️⃣ RATING – random star click (1 to 5 stars)
				// ----------------------------
				int star = new Random().nextInt(5) + 1;

				WebElement ratingStar = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("(//label[contains(@class,'css-ykqdxu')])[" + star + "]")));

				ratingStar.click();
				Thread.sleep(1000);
				
	 // 6️⃣ EXPIRY DATE – pick any FUTURE date from current month

				WebElement expiryInput = driver.findElement(
		                By.xpath(".//input[contains(@class,'e-datepicker')]")
		        );
				 JavascriptExecutor js1 = (JavascriptExecutor) driver;
		        js1.executeScript("arguments[0].scrollIntoView(true);", expiryInput);

		        // Focus input
		        expiryInput.click();
		        Thread.sleep(300);

		        // Open calendar
		        WebElement calendarIcon = driver.findElement(
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
		        
				// 4️⃣ Click Submit
				WebElement submitBtn = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='SUBMIT']")));
				submitBtn.click();

				// 5️⃣ Click Confirm
				WebElement confirmBtn = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Confirm']")));
				confirmBtn.click();

				// click ok
				WebElement ok = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='OK']")));
				ok.click();
				
				Thread.sleep(3000);
	}
	
	
	
	private void veryfyAndApprove(int rowIndex) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    
	    // Click More
	    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	    moreBtn.click();
	    Thread.sleep(2000);
	    // Click Verify & Approve
	    WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Verify & Approve')]")));
	    changeStatus.click();

	    // 🔥 QC REJECTION LOGIC
	    randomProductQcRejection();

	    //================= SUBMIT QC =================
	    Thread.sleep(1000);
	    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
	}
	
	
//	public void randomProductQcRejection() throws InterruptedException {
//
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
////	    WebElement accordion = wait.until(
////	            ExpectedConditions.visibilityOfElementLocated(
////	                    By.xpath("//div[contains(@class,'lpoqc-accordion shadow-sm')]")
////	            )
////	    );
////
////	    accordion.click();
//	    Thread.sleep(500);
//
//	    // Read received qty
//	    WebElement recQtyInput = driver.findElement(
//	            By.xpath(".//input[contains(@class,'ful')]")
//	    );
//
//	    double receivedQty = Double.parseDouble(recQtyInput.getAttribute("value"));
//
//	    // Reject half
//	    rejectedQtyFromQc = Math.max(1, receivedQty / 2);
//
//	    WebElement rejectQtyInput = driver.findElement(
//	            By.xpath(".//input[contains(@class,'rejected_qty')]")
//	    );
//
////	    js.executeScript("arguments[0].value='';", rejectQtyInput);
////	    rejectQtyInput.sendKeys(String.valueOf((int) rejectedQtyFromQc));
////
////	    // Commit value (important for Syncfusion)
////	    js.executeScript(
////	            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
////	            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
////	            "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
////	            rejectQtyInput
////	    );
//
////	    js.executeScript(
////	        "arguments[0].value = arguments[1];" +
////	        "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
////	        "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
////	        "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
////	        rejectQtyInput, (int) rejectedQtyFromQc
////	    );
//	    int rejectQty = (int) rejectedQtyFromQc;
//
//	    typeSyncfusionNumber(rejectQtyInput, String.valueOf(rejectQty));
//
//	    // Select rejection reason
//	    WebElement reasonDropdown = driver.findElement(
//	            By.xpath(".//div[contains(@class,'dropdown-heading')]")
//	    );
//	    reasonDropdown.click();
//
//	    WebElement firstReason = wait.until(
//	            ExpectedConditions.elementToBeClickable(
//	                    By.xpath("//div[contains(@class,'dropdown-content')]//input[@type='checkbox']")
//	            )
//	    );
//	    js.executeScript("arguments[0].click();", firstReason);
//
//	    // Comment
//	    driver.findElement(By.xpath(".//input[@name='sName']"))
//	            .sendKeys("Auto QC rejection");
//
//	    System.out.println("✅ Rejected Qty Stored: " + rejectedQtyFromQc);
//	}
	
	

	public void randomProductQcRejection() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Read received qty
	    WebElement recQtyInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[contains(@class,'ful')]")
	            )
	    );

	    double receivedQty = Double.parseDouble(recQtyInput.getAttribute("value"));

	    // Reject half (minimum 1)
	    
	    int rejectQty = (int) Math.max(1, Math.floor(receivedQty / 2));
	    CreateLpo.setRejectedQtyFromQc(rejectQty);

	    // Rejected Qty input
	    WebElement rejectQtyInput = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//input[contains(@class,'rejected_qty')]")
	            )
	    );

	    typeSyncfusionNumber(rejectQtyInput, String.valueOf(rejectQty));

	    // ✅ ASSERT to confirm correct value
	    String finalValue = rejectQtyInput.getAttribute("value");
	    if (!finalValue.equals(String.valueOf(rejectQty))) {
	        throw new RuntimeException(
	                "Rejected Qty not committed. Expected: " + rejectQty + ", Found: " + finalValue
	        );
	    }

	    // Select rejection reason
	    WebElement reasonDropdown = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[contains(@class,'dropdown-heading')]")
	            )
	    );
	    reasonDropdown.click();

	    WebElement firstReason = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[contains(@class,'dropdown-content')]//input[@type='checkbox']")
	            )
	    );
	    js.executeScript("arguments[0].click();", firstReason);

	    // Comment
	    WebElement commentInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@name='sName']")
	            )
	    );
	    commentInput.sendKeys("Auto QC rejection");

	    System.out.println("✅ Rejected Qty Stored Successfully: " + rejectedQtyFromQc);
	}
	
	private void typeSyncfusionNumber(WebElement input, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    input.click();

	    // Clear existing value safely
	    input.sendKeys(Keys.CONTROL + "a");
	    input.sendKeys(Keys.DELETE);

	    // Type each digit slowly
	    for (char ch : value.toCharArray()) {
	        input.sendKeys(String.valueOf(ch));
	        try { Thread.sleep(120); } catch (InterruptedException ignored) {}
	    }

	    // Commit value
	    js.executeScript(
	        "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	        "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	        "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	        input
	    );
	}
	
	public void addMoreBatches(int rowIndex) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    Thread.sleep(3000);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")
	    )).click();
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[contains(text(),'Add More Batches')]")
	    )).click();
	}
	
	public void enterInvoiceDetailsForAdditionalBatch() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    Random random = new Random();
	    Thread.sleep(1000);
	    String invoiceNumber = String.valueOf(100000 + random.nextInt(900000));

	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//input[@name='sup_inv_no'])[2]")
	    )).sendKeys(invoiceNumber);

	    // Invoice Date → Tomorrow
	    WebElement invoiceDate = driver.findElement(
	            By.xpath("(//input[@name='supplier_inv_dt'])[2]")
	    );
	    invoiceDate.click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(@class,'react-datepicker__day--today')]/following-sibling::div[1]")
	    )).click();

	    // Invoice Value = rejectedQty * LPO Unit Price (auto-calculated by UI is OK too)
	    WebElement invoiceValue = driver.findElement(
	            By.xpath("(//input[contains(@name,'sup_inv_val')])[2]")
	    );
	    invoiceValue.clear();
	    invoiceValue.sendKeys("0"); // UI recalculates later
	}
	
	public void addBatchWithRejectedQty() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Thread.sleep(1000);
	    // Click + Add Batch
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//*[text()='+ Add Batch']")
	    )).click();
	    
	 // 6️⃣ EXPIRY DATE – pick any FUTURE date from current month
        Thread.sleep(1000);
	    WebElement expiryInput = driver.findElement(
                By.xpath("(.//input[contains(@class,'e-datepicker')])[2]")
        );
		 JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("arguments[0].scrollIntoView(true);", expiryInput);

        // Focus input
        expiryInput.click();
        Thread.sleep(300);

        // Open calendar
        WebElement calendarIcon = driver.findElement(
                By.xpath("(.//span[contains(@class,'e-date-icon')])[2]")
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

 // Enter rejected qty
	    WebElement qtyInput = driver.findElement(
	            By.xpath("(//input[contains(@class,'qty')])[2]")
	    );
	   // int rejectQty = (int) rejectedQtyFromQc;
	    int rejectQty = (int) CreateLpo.getRejectedQtyFromQc();
	    typeSyncfusionNumber(qtyInput, String.valueOf(rejectQty));

//	    js.executeScript("arguments[0].value='';", qtyInput);
//	    qtyInput.sendKeys(String.valueOf((int) rejectedQtyFromQc));

	    System.out.println("✅ Entered Rejected Qty in New Batch: " + rejectedQtyFromQc);
	}
	
	public void submitAdditionalBatch() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='SUBMIT']")
	    )).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Confirm']")
	    )).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='OK']")
	    )).click();

	    System.out.println("✅ Additional batch submitted successfully");
	}
	 public static void setRejectedQtyFromQc(double qty) {
	        rejectedQtyFromQc = qty;
	    }
	 
	 public static double getRejectedQtyFromQc() {
	        return rejectedQtyFromQc;
	    }

//}
	 
 
	 public void veryfyAndApprove_AdditionBatch () throws InterruptedException {

		    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		    
		    // Click More
		    WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("(//table//tbody//tr//button[contains(@class,'dropdown-toggle')])[1]")));
		    moreBtn.click();
		    Thread.sleep(2000);
		    // Click Verify & Approve
		    WebElement changeStatus12 = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//table//tbody//tr//a[contains(text(),'Verify & Approve')]")));
		    changeStatus12.click();

		    Thread.sleep(2000);
		    //================= SUBMIT QC =================
		    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		}
	 
//Generate CN and DN 
	 public void searchSeller() throws InterruptedException {
			Thread.sleep(5000);
		    // Click seller filter icon
		    driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[2]/div/div/div[2]/div[1]/table/thead/tr/th[7]/div[2]")).click(); //(//div[@class='custom-filter-icon'])[4]
		    
	        Thread.sleep(1000);
		    // Type seller name and search
		    WebElement sellerInput = driver.findElement(By.xpath("(//input[@type='text'])[3]"));

//		    sellerInput.clear();
		    sellerInput.sendKeys("Trovec General Trading LLC"); //becouse of multiple product rejection more than 2products
		    sellerInput.sendKeys(Keys.ENTER);
		}
	 
	 public void processTopWaitingForAcceptanceLpos3(int maxCount) throws InterruptedException {

			List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
			int processed = 0;

			for (int i = 1; i <= rows.size(); i++) {

				if (processed == maxCount)
					break;

				String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

				if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
					continue;
				}

				processSingleLpo3(i);
				processed++;
			}
		}

		private void processSingleLpo3(int rowIndex) throws InterruptedException {

			changeStatus3(rowIndex, "Accepted But Unfulfilled");
			changeStatus3(rowIndex, "LPO At Hub");
			Lpofulfillment3(rowIndex);
			enterInvoiceDetails3(rowIndex);
//			veryfyAndApprove1(rowIndex);
		}

		private void changeStatus3(int rowIndex, String statusText) throws InterruptedException  {

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
			Thread.sleep(2000);
			 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
			

		}
		public void Lpofulfillment3(int rowIndex) throws InterruptedException {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

			WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
//			Thread.sleep(1000);
			moreBtn.click();

			WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
			option.click();
		}
		

		public void enterInvoiceDetails3(int rowIndex) throws InterruptedException {
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
			                        "//div[contains(@class,'react-datepicker__day--today')]"
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

//		        System.out.println("✅ Shipping date selected: " + committedDate);
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
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		    Thread.sleep(1000);
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
			completeLPOforDN(rowIndex, "Completed");
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
		    randomProductQcRejectionforDN();

		    //================= SUBMIT QC =================
		    driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
		    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
		    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		}
		
		public void randomProductQcRejectionforDN() throws InterruptedException {

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

		        Thread.sleep(2000);

		        // Get all checkbox options
		        List<WebElement> reasons = wait.until(
		                ExpectedConditions.presenceOfAllElementsLocatedBy(
		                        By.xpath("//div[contains(@class,'dropdown-content')]//input[@type='checkbox']")
		                )
		        );

		        // Select one randomly
//		        Random random = new Random();
		        WebElement randomReason = reasons.get(random.nextInt(reasons.size()));

		        if (!randomReason.isSelected()) {
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomReason);
		        }

		        System.out.println("Random rejection reason selected for rejecting product");
		        

		        // ================= COMMENT =================
		        WebElement commentInput = accordion.findElement(
		                By.xpath(".//input[@name='sName']")
		        );
		        commentInput.sendKeys("Auto QC rejection");

		        rejectedCount++;
		    }

		}
		
		public void completeLPOforDN(int rowIndex, String statusText) throws InterruptedException  {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10 ));

			// Click correct row "More"
			WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
			Thread.sleep(1000);
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
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
			driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
			Thread.sleep(2000);
			 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
		}
		
		
// Edit LPO		
		public void updateRequestedQtyForAllProducts() throws InterruptedException {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    
			// Click correct row "More"
			WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//table//tbody//tr//button[contains(@class,'dropdown-toggle')]")));
			moreBtn.click();
			Thread.sleep(1000);
			// Click Change Status from SAME row
			WebElement changeLpoStatus = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//table//tbody//tr//a[contains(text(),'Edit LPO')]")));
			Thread.sleep(2000);
			changeLpoStatus.click();
			
			beforeEditTotalAmount = getBeforeEditTotalAmount();

			System.out.println("---------------------------------------");
//			System.out.println("Total Amount BEFORE Edit: " + beforeEditTotalAmount);
			

		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    Random random = new Random();

		    // Total products
		    int totalProducts = driver.findElements(
		            By.xpath("//table/tbody/tr[td[2][normalize-space()!='']]"))
		            .size();


		    for (int i = 1; i <= totalProducts; i++) {

		        try {

		            
		        	List<WebElement> productRows = driver.findElements(
		        	        By.xpath(
		        	                "//tbody/tr[td[1]//*[name()='svg' " +
		        	                "or contains(@class,'arrow') " +
		        	                "or contains(@class,'icon')]]"
		        	        )
		        	);

		            if (i > productRows.size()) {
		                break;
		            }

		            WebElement productRow = productRows.get(i - 1);

		            // Get product name for debugging
		            String productName = productRow
		                    .findElement(By.xpath("./td[4]"))
		                    .getText()
		                    .trim();

		           


		            
		            // Find EXPAND button ONLY inside current product row
		           
		            List<WebElement> expandButtons = productRow.findElements(
		                    By.xpath(
		                            "./td[1]//*[name()='svg' " +
		                            "or contains(@class,'arrow') " +
		                            "or contains(@class,'icon')]"
		                    )
		            );

		            if (expandButtons.isEmpty()) {

		                System.out.println(
		                        "⚠ Expand button not found for: " + productName
		                );

		                continue;
		            }

		            WebElement expandBtn = expandButtons.get(0);

		            js.executeScript(
		                    "arguments[0].scrollIntoView({block:'center'});",
		                    expandBtn
		            );

		            Thread.sleep(500);

		            // Click current product's expand button
		            expandBtn.click();


		            
		         // Get order rows ONLY for the current product
		        
		         List<WebElement> orderRows = productRow.findElements(
		                 By.xpath(
		                         "./following-sibling::tr[" +
		                         "not(td[2][normalize-space()!=''])" +
		                         "]"
		                 )
		         );


		         // ---------------------------------------------------------
		         // Update quantity for current product's orders
		         // ---------------------------------------------------------
		         for (int j = 0; j < orderRows.size(); j++) {

		             try {



		            	 List<WebElement> currentProductRows = driver.findElements(
		            			    By.xpath(
		            			        "//tbody/tr[td[1]//*[name()='svg' " +
		            			        "or contains(@class,'arrow') " +
		            			        "or contains(@class,'icon')]]"
		            			    )
		            			);
		                 WebElement currentProductRow = currentProductRows.get(i - 1);

		                 // Get child/order row of current product
		                 List<WebElement> currentOrderRows = currentProductRow.findElements(
		                         By.xpath(
		                                 "./following-sibling::tr[" +
		                                 "not(td[2][normalize-space()!=''])" +
		                                 "]"
		                         )
		                 );

		                 if (j >= currentOrderRows.size()) {
		                     break;
		                 }

		                 WebElement orderRow = currentOrderRows.get(j);

		                 // Find quantity input ONLY inside this order row
		                 WebElement qty = orderRow.findElement(
		                         By.xpath(".//input[@type='number']")
		                 );

		                 js.executeScript(
		                         "arguments[0].scrollIntoView({block:'center'});",
		                         qty
		                 );

		                 Thread.sleep(300);

		                 // Random quantity 1-100
		                 int randomQty = random.nextInt(100) + 1;


		                 qty.click();

		                 Thread.sleep(300);

		                 qty.sendKeys(Keys.CONTROL, "a");

		                 Thread.sleep(200);

		                 qty.sendKeys(Keys.BACK_SPACE);

		                 Thread.sleep(300);

		                 qty.sendKeys(String.valueOf(randomQty));

		                 qty.sendKeys(Keys.TAB);

		                 Thread.sleep(500);
		                
		                 System.out.println(		                         
		                         "Order " + (j + 1) +
		                         " | Edited Req Qty = " + randomQty
		                 );
		                 
		                 		                	 		        
		                 Thread.sleep(500);

		             } catch (StaleElementReferenceException e) {

		                 System.out.println(
		                         "⚠ Order row became stale. Retrying..."
		                 );

		                 j--;
		             }
		         }

		        } catch (StaleElementReferenceException e) {

		            System.out.println(
		                    "⚠ Product " + i +
		                    " became stale. Re-reading product rows."
		            );

		            i--;
		            Thread.sleep(500);
		        }
		    }
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
		}
		
	public double getBeforeEditTotalAmount() {

		    WebDriverWait wait =
		            new WebDriverWait(driver, Duration.ofSeconds(10));


		    WebElement totalAmountElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath(
		                            "//tr[contains(@class,'custom-row')]/td[12]"
		                    )
		            )
		    );


		    String amountText = totalAmountElement
		            .getText()
		            .replace("AED", "")
		            .replace(",", "")
		            .trim();

		    return Double.parseDouble(amountText);
		}


		// ============================================================
		// GET TOTAL AMOUNT FROM CONFIRMATION POPUP
		// ============================================================
		
		
		public double getConfirmationTotalAmount() throws InterruptedException {
		    
		    WebDriverWait wait =
		            new WebDriverWait(driver, Duration.ofSeconds(15));

		    
		    // Wait until confirmation popup Total Amount is visible

		    WebElement totalAmountElement = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath(
		                            "//td[normalize-space()='Total Amount'] "+"/following-sibling::td[1]"
		                    )
		            )
		    );


		    String amountText = totalAmountElement
		            .getText()
		            .replace("AED", "")
		            .replace(",", "")
		            .trim();


//		    System.out.println(
//		            "Confirmation Popup Total Amount: "
//		                    + amountText
//		    );


		    return Double.parseDouble(amountText);
		}
		
		public void saveActions() throws InterruptedException {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


		    // ========================================================
		    // STEP 2 - WAIT FOR CONFIRMATION POPUP
		    // ========================================================

		    Thread.sleep(1500);


		    // ========================================================
		    // STEP 3 - GET AFTER EDIT TOTAL AMOUNT
		    // ========================================================

		    afterEditTotalAmount  = getConfirmationTotalAmount();
		    
		    beforeEditTotalAmount = getBeforeEditTotalAmount();


		    System.out.println("======================================");
		    System.out.println("Edit LPO Validation");
		    System.out.println(
		            "Total Amount BEFORE Edit : "
		                    + beforeEditTotalAmount   
		    );

		    System.out.println(
		            "Total Amount AFTER Edit  : "
		                    + afterEditTotalAmount
		    );

		    System.out.println("======================================");


		    // ========================================================
		    // STEP 4 - ASSERTION
		    // ========================================================

		    Assert.assertNotEquals(
		            afterEditTotalAmount,
		            beforeEditTotalAmount,
		            "❌ Total Amount was not updated after editing Requested Qty"
		    );


		    // ========================================================
		    // STEP 5 - SECOND SAVE FROM CONFIRMATION POPUP
		    // ========================================================

		    WebElement secondSave = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath(
		                            "(//button[normalize-space()='Save'])[2]"
		                    )
		            )
		    );

		    Thread.sleep(500);
		    secondSave.click();

		    WebElement okButton = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath(
		                            "//button[normalize-space()='OK']"
		                    )
		            )
		    );

		    Thread.sleep(1000);
		    okButton.click();
		}
		
//public void saveActions() throws InterruptedException {
//        Thread.sleep(3000); // slight delay for UI to catch up
//        driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
//        
//	    Thread.sleep(2000);
//	    driver.findElement(By.xpath("(//button[normalize-space()='Save'])[2]")).click();
//	    Thread.sleep(1000);
//	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
//}

		public void addProcurementCommentsForTwoProducts() throws InterruptedException {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    Random random = new Random();

		    // =========================================================
		    // 1. Get MAIN PRODUCT rows only
		    // =========================================================

		    List<WebElement> productRows = driver.findElements(
		            By.xpath("//table//tbody/tr[td[2][normalize-space()!='']]")
		    );

		    System.out.println("======================================");
		    System.out.println("Total Products Available: " + productRows.size());
		    System.out.println("======================================");

		    if (productRows.size() < 2) {
		        throw new RuntimeException(
		                "❌ Less than 2 products available. Available: "
		                        + productRows.size()
		        );
		    }

		    // =========================================================
		    // 2. Select exactly 2 RANDOM product indexes
		    // =========================================================

		    List<Integer> selectedIndexes = new ArrayList<>();

		    while (selectedIndexes.size() < 2) {

		        int randomIndex = random.nextInt(productRows.size());

		        if (!selectedIndexes.contains(randomIndex)) {
		            selectedIndexes.add(randomIndex);
		        }
		    }

		    System.out.println(
		            "Selected Product Indexes: " + selectedIndexes
		    );

		    int commentsAdded = 0;

		    // =========================================================
		    // 3. Process only 2 products
		    // =========================================================

		    for (int index : selectedIndexes) {

		        try {

		            // Re-read rows every time because DOM can change
		            productRows = driver.findElements(
		                    By.xpath("//table//tbody/tr[td[2][normalize-space()!='']]")
		            );

		            WebElement productRow = productRows.get(index);

		            // =====================================================
		            // Get Product Name
		            // =====================================================

		            String productName = productRow
		                    .findElement(By.xpath("./td[4]"))
		                    .getText()
		                    .trim();

		            if (productName.isEmpty()) {

		                System.out.println(
		                        "⚠ Product name is blank for index: " + index
		                );

		                // Try another column if your application structure changes
		                productName = productRow
		                        .findElement(By.xpath("./td[contains(@class,'product')]"))
		                        .getText()
		                        .trim();
		            }

		            System.out.println(
		                    "======================================"
		            );

		            System.out.println(
		                    "Processing Product: " + productName
		            );

		            // =====================================================
		            // Find MORE button ONLY inside current product row
		            // =====================================================

		            List<WebElement> moreButtons = productRow.findElements(
		                    By.xpath(
		                            ".//button[contains(@class,'dropdown-toggle') " +
		                            "or contains(@class,'more') " +
		                            "or .//*[contains(@class,'more')] ]"
		                    )
		            );

		            if (moreButtons.isEmpty()) {

		                System.out.println(
		                        "⚠ More button not found for: " + productName
		                );

		                continue;
		            }

		            WebElement moreButton = moreButtons.get(0);

		            js.executeScript(
		                    "arguments[0].scrollIntoView({block:'center'});",
		                    moreButton
		            );

		            Thread.sleep(500);

		            wait.until(
		                    ExpectedConditions.elementToBeClickable(moreButton)
		            );

		            moreButton.click();

		            Thread.sleep(500);

		            // =====================================================
		            // Click Procurement Comment option
		            // =====================================================

		            WebElement procurementComment = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            By.xpath(
		                                    "//a[contains(@class,'dropdown-item') " +
		                                    "and contains(normalize-space(.)," +
		                                    "'Add/View/Edit Procurement Comment')]"
		                            )
		                    )
		            );

		            procurementComment.click();

		            Thread.sleep(1000);

		            // =====================================================
		            // Select random procurement comment
		            // =====================================================

		            List<WebElement> commentOptions = wait.until(
		                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
		                            By.xpath(
		                                    "//select//option[" +
		                                    "normalize-space(.)!='' " +
		                                    "and not(@disabled)" +
		                                    "]"
		                            )
		                    )
		            );

		            if (commentOptions.isEmpty()) {

		                throw new RuntimeException(
		                        "❌ No procurement comments available for: "
		                                + productName
		                );
		            }

		            WebElement randomCommentOption =
		                    commentOptions.get(
		                            random.nextInt(commentOptions.size())
		                    );

		            String selectedComment =
		                    randomCommentOption.getText().trim();

		            System.out.println(
		                    "Selected Comment: " + selectedComment
		            );

		            // =====================================================
		            // Select comment
		            // =====================================================

		            Select commentSelect = new Select(
		                    randomCommentOption.findElement(
		                            By.xpath("./parent::select")
		                    )
		            );

		            commentSelect.selectByVisibleText(selectedComment);

		            Thread.sleep(500);

		            // =====================================================
		            // Click Save inside procurement comment popup
		            // =====================================================

		            WebElement saveComment = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            By.xpath(
		                                    "//button[normalize-space()='Save']"
		                            )
		                    )
		            );

		            saveComment.click();

		            Thread.sleep(1000);

		            commentsAdded++;

		            System.out.println(
		                    "✅ Comment saved for: " + productName
		            );

		            System.out.println(
		                    "Comment: " + selectedComment
		            );

		            System.out.println(
		                    "Products with comments: " + commentsAdded
		            );

		            System.out.println(
		                    "======================================"
		            );

		            // =====================================================
		            // STOP AFTER EXACTLY 2 PRODUCTS
		            // =====================================================

		            if (commentsAdded == 2) {

		                System.out.println(
		                        "======================================"
		                );

		                System.out.println(
		                        "✅ Procurement comments added to exactly 2 products."
		                );

		                System.out.println(
		                        "Total Products Updated: " + commentsAdded
		                );

		                System.out.println(
		                        "======================================"
		                );

		                break;
		            }

		        } catch (StaleElementReferenceException e) {

		            System.out.println(
		                    "⚠ Product row became stale. Skipping this product."
		            );

		        } catch (TimeoutException e) {

		            System.out.println(
		                    "❌ Procurement comment menu/button not found for product index: "
		                            + index
		            );

		            System.out.println(
		                    "Error: " + e.getMessage()
		            );
		        }
		    }

		    // =========================================================
		    // Final validation
		    // =========================================================

		    if (commentsAdded != 2) {

		        throw new RuntimeException(
		                "❌ Could add procurement comments to only "
		                        + commentsAdded
		                        + " products. Required: 2"
		        );
		    }

		    System.out.println(
		            "✅ Test case passed - procurement comments added to 2 products."
		    );
		}
		
/*public void addRandomProcurementComments() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Random random = new Random();

    // =========================================================
    // 1. Find all rows which have a "More" button
    // =========================================================

    List<WebElement> productRows = driver.findElements(
            By.xpath("//tbody/tr[.//button[contains(@class,'dropdown-toggle')]]")
    );

    int totalProducts = productRows.size();

    System.out.println("--------------------------------------");
    System.out.println("Total Products Available: " + totalProducts);

    // No products available
    if (totalProducts == 0) {
        System.out.println("⚠ No products with More button available.");
        return;
    }

    // =========================================================
    // 2. Decide how many products to process
    //
    // 1 product  -> 1
    // 2 products -> 2
    // 3 products -> random 2 or 3
    // 4+ products -> random 2 or 3
    // =========================================================

    int productsToProcess;

    if (totalProducts == 1) {

        productsToProcess = 1;

    } else if (totalProducts == 2) {

        productsToProcess = 2;

    } else {

        // Randomly select either 2 or 3
        productsToProcess = random.nextInt(2) + 2;
    }


    // =========================================================
    // 3. Generate UNIQUE random product indexes
    // =========================================================

    List<Integer> randomIndexes = new ArrayList<>();

    while (randomIndexes.size() < productsToProcess) {

        int randomIndex = random.nextInt(totalProducts);

        if (!randomIndexes.contains(randomIndex)) {
            randomIndexes.add(randomIndex);
        }
    }


    // =========================================================
    // 4. Process selected products
    // =========================================================

    for (int x = 0; x < randomIndexes.size(); x++) {

        int productIndex = randomIndexes.get(x);

        try {

            // -----------------------------------------------------
            // Re-find product rows every time
            // -----------------------------------------------------

            productRows = driver.findElements(
                    By.xpath(
                            "//tbody/tr[.//button[contains(@class,'dropdown-toggle')]]"
                    )
            );

            if (productIndex >= productRows.size()) {

                System.out.println(
                        "⚠ Product index no longer available: "
                                + productIndex
                );

                continue;
            }

            WebElement productRow = productRows.get(productIndex);


            // -----------------------------------------------------
            // Product name
            // -----------------------------------------------------

            String productName = "";

            try {

                productName = productRow
                        .findElement(By.xpath("./td[4]"))
                        .getText()
                        .trim();

            } catch (Exception e) {

                productName = "Product Index " + (productIndex + 1);
            }


            // -----------------------------------------------------
            // Find More button inside CURRENT row
            // -----------------------------------------------------

            WebElement moreButton = productRow.findElement(
                    By.xpath(
                            ".//button[contains(@class,'dropdown-toggle')]"
                    )
            );

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    moreButton
            );

            Thread.sleep(500);

            wait.until(
                    ExpectedConditions.elementToBeClickable(moreButton)
            );

            moreButton.click();


            // =====================================================
            // 5. Click Add/View/Edit ProcurementComment
            //
            // IMPORTANT:
            // Dropdown menu is outside the product <tr>
            // =====================================================

            WebElement procurementComment = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//a[contains(@class,'dropdown-item')" +
                                    " and contains(normalize-space()," +
                                    "'Add/View/Edit ProcurementComment')]"
                            )
                    )
            );

            procurementComment.click();

            Thread.sleep(1000);


            // =====================================================
            // 6. Find procurement comment dropdown
            // =====================================================

            WebElement commentDropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//select[.//option[contains(" +
                                    "normalize-space(),'Available In stock')]]"
                            )
                    )
            );

            Select select = new Select(commentDropdown);

            List<WebElement> options = select.getOptions();


            // -----------------------------------------------------
            // Make sure comments are available
            // -----------------------------------------------------

            if (options.size() <= 1) {

                System.out.println(
                        "⚠ No procurement comments available for: "
                                + productName
                );

                // Close popup if required
                continue;
            }


            // =====================================================
            // 7. Select random comment
            // =====================================================

            // Skip first "Select" option
            int randomCommentIndex =
                    random.nextInt(options.size() - 1) + 1;

            String selectedComment =
                    options.get(randomCommentIndex)
                            .getText()
                            .trim();

            select.selectByIndex(randomCommentIndex);

            System.out.println(
                    "Selected Comment: " + selectedComment
            );


            // =====================================================
            // 8. Click Save
            // =====================================================

            WebElement saveButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[normalize-space()='Save']"
                            )
                    )
            );

            saveButton.click();

            System.out.println(
                    "✅ Comment saved for: " + productName
            );
            Thread.sleep(500);
    	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

            Thread.sleep(1000);


        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Product became stale. Skipping product."
            );

        } catch (Exception e) {

//            System.out.println(
//                    "❌ Failed for product: "
//                            + productNameSafe(productIndex)
//            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
   }
   
} */

//Request for Approval
public void requestApprovalForRedProducts() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    int totalProducts = 0;
    int redProducts = 0;
    int approvalRequested = 0;

    
    System.out.println("REQUEST FOR APPROVAL - PRODUCTS");
   

    // ---------------------------------------------------------
    // Find all product rows which contain RED color
    // ---------------------------------------------------------

    List<WebElement> redRows = driver.findElements(
            By.xpath(
                    "//tbody/tr[td[contains(@class,'disabled-red')]]"
            )
    );

    totalProducts = driver.findElements(
            By.xpath("//tbody/tr")
    ).size();

    redProducts = redRows.size();

    System.out.println("Total Products Found : " + totalProducts);
    System.out.println("Approval Request Products Found   : " + redProducts);

    if (redRows.isEmpty()) {

        System.out.println(
                "⚠ No Approval Request products found. Request for Approval not required."
        );

        return;
    }

    // ---------------------------------------------------------
    // Process every RED product
    // ---------------------------------------------------------

    for (int i = 0; i < redProducts; i++) {

        try {

            // -------------------------------------------------
            // Re-find red rows every time
            // This helps avoid StaleElementReferenceException
            // -------------------------------------------------

            redRows = driver.findElements(
                    By.xpath(
                            "//tbody/tr[td[contains(@class,'disabled-red')]]"
                    )
            );

            if (i >= redRows.size()) {
                break;
            }

            WebElement redRow = redRows.get(i);

            // -------------------------------------------------
            // Product name
            // -------------------------------------------------

            String productName = "";

            try {

                productName = redRow
                        .findElement(By.xpath("./td[4]"))
                        .getText()
                        .trim();

            } catch (Exception e) {

                productName = "Red Product " + (i + 1);
            }

            // -------------------------------------------------
            // Find More button ONLY inside current red row
            // -------------------------------------------------

            WebElement moreButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            redRow.findElement(
                                    By.xpath(
                                            ".//button[contains(@class,'dropdown-toggle')]"
                                    )
                            )
                    )
            );

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    moreButton
            );

            Thread.sleep(300);

            moreButton.click();

            Thread.sleep(500);

            // -------------------------------------------------
            // Request for Approval option
            //
            // Dropdown is outside the TR, so locate globally
            // -------------------------------------------------

            WebElement requestApproval = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//a[contains(@class,'dropdown-item')" +
                                    " and normalize-space()='Request for Approval']"
                            )
                    )
            );

            requestApproval.click();

            System.out.println(
                    "✅ Request for Approval Product: "
                    + productName
            );
            Thread.sleep(500);
    	    driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
    	    Thread.sleep(1000);
    	    driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();

            approvalRequested++;

            Thread.sleep(700);

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Red product became stale. Retrying..."
            );

            i--;

            Thread.sleep(500);

        } catch (Exception e) {

            System.out.println(
                    "❌ Failed for red product: "
                    + e.getMessage()
            );
        }
    }

    // ---------------------------------------------------------
    // Final Summary
    // ---------------------------------------------------------
    System.out.println(
            "Total Approval Request Products : " + redProducts
    );

    System.out.println(
            "Approval Requests Sent   : " + approvalRequested
    );

    System.out.println(
            "Approval Requests Failed   : " +
            (redProducts - approvalRequested)
    );

}

//LPO stock entry validation

public void processTopWaitingForAcceptanceLpos4(int maxCount) throws InterruptedException {

	List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
	int processed = 0;

	for (int i = 1; i <= rows.size(); i++) {

		if (processed == maxCount)
			break;

		String status = driver.findElement(By.xpath("//table//tbody//tr[" + i + "]/td[11]")).getText().trim();

		if (!status.equalsIgnoreCase("Waiting For Acceptance")) {
			continue;
		}

		processSingleLpo4(i);
		processed++;
	}
}

private void processSingleLpo4(int rowIndex) throws InterruptedException {

	changeStatus4(rowIndex, "Accepted But Unfulfilled");
	changeStatus4(rowIndex, "LPO At Hub");
	Lpofulfillment4(rowIndex);
	enterInvoiceDetails4(rowIndex);
//	veryfyAndApprove1(rowIndex);
}

private void changeStatus4(int rowIndex, String statusText) throws InterruptedException  {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	
	// Wait until page loader disappears
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.cssSelector(".fot-loader")));

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
	Thread.sleep(500);
	driver.findElement(By.xpath("//textarea[@name='sName']")).sendKeys("Status updated automatically");

	// Submit
	Thread.sleep(500);
	driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
	Thread.sleep(500);
	driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	Thread.sleep(2000);
	 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
	 
}
	public void SaveActions() {
		
	
    WebElement submit = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Submit']")));

    submit.click();

    // Wait for Confirm
    WebElement confirm = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Confirm']")));

    confirm.click();

    // Wait for loader after status update
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.cssSelector(".fot-loader")));

    // Final alert/confirmation close button
    WebElement closeButton = wait.until(
            ExpectedConditions.elementToBeClickable(
            		By.xpath("//button[normalize-space()='OK']")));
//                    By.xpath("//*[@id='react-confirm-alert']/div/div/div/div/button")));

    closeButton.click();

}
public void Lpofulfillment4(int rowIndex) throws InterruptedException {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	// Wait until page loader disappears
//    wait.until(ExpectedConditions.invisibilityOfElementLocated(
//            By.cssSelector(".fot-loader")));

	WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	Thread.sleep(1000);
	moreBtn.click();

	WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Fullfill LPO')]")));
	option.click();
}

public void enterInvoiceDetails4(int rowIndex) throws InterruptedException {
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
	                        "//div[contains(@class,'react-datepicker__day--today')]" 
	                )
	        )
	); //div[contains(@class,'react-datepicker__day--today')]" +
    //"/following-sibling::div[not(contains(@class,'react-datepicker__day--disabled'))][1]"
Thread.sleep(1000);
	nextDay.click();

	System.out.println("Invoice date selected: Next day");


	 Thread.sleep(2000);

	 // generate 2-digit supplier unit price (10–99)
	 int supplierUnitPrice = random.nextInt(90) + 10;

	 WebElement supplierPriceInput = driver.findElement(
	         By.xpath(".//div[contains(@class,'supplierUnitPrice')]//input")
	 );
	 supplierPriceInput.clear();
	 supplierPriceInput.sendKeys(String.valueOf(supplierUnitPrice));

	 // trigger UI commit
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript(
	         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	         supplierPriceInput
	 );
	 try {

	        WebElement popup = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//div[contains(text(),'Supplier Unit Price entered is higher')]")
	                )
	        );

	        // Click OK button
	        popup.findElement(
	                By.xpath(".//button[normalize-space()='OK']")
	        ).click();


	    } catch (TimeoutException e) {
	        // Popup did not appear → continue
	        System.out.println("✅ No supplier price popup");
	    }

	 // ================= READ QUANTITY =================
	 WebElement qtyInput = driver.findElement(
	         By.xpath("//input[@type='number' and contains(@class,'qty')]")
	 );

	 double quantity = Double.parseDouble(qtyInput.getAttribute("value"));

	 // ================= CALCULATE INVOICE VALUE =================
	 double invoiceValue = supplierUnitPrice * quantity;

	 // round to 2 decimals (safe for currency)
	 invoiceValue = Math.round(invoiceValue * 100.0) / 100.0;

	 // ================= ENTER INVOICE VALUE =================
	 WebElement invoiceValueInput = driver.findElement(
	         By.xpath("//input[contains(@name,'sup_inv_val')]")
	 );

	 invoiceValueInput.clear();
	 invoiceValueInput.sendKeys(String.valueOf(invoiceValue));

	 // commit invoice value
	 js.executeScript(
	         "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
	         "arguments[0].dispatchEvent(new Event('blur',{bubbles:true}));",
	         invoiceValueInput
	 );

	 System.out.println("Supplier Unit Price Entered: " + supplierUnitPrice);
	 System.out.println("LPO Rec Quantity: " + quantity);
	 System.out.println("Total LPO Invoice Value: " + invoiceValue);
	 
	 
	 
	// 4️⃣ RATING – random star click (1 to 5 stars)
			// ----------------------------
			int star = new Random().nextInt(5) + 1;

			WebElement ratingStar = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//label[contains(@class,'css-ykqdxu')])[" + star + "]")));

			ratingStar.click();
			Thread.sleep(1000);
			
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
			Thread.sleep(2000);
			/* 5️⃣ Click target date */
			WebElement targetDateElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'e-day') and "
							+ "not(contains(@class,'e-disabled')) and " + "text()='" + targetDay + "']")));
			targetDateElement.click();

			System.out.println("Expiry date selected: " + targetDate);
			
			driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
			driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
			Thread.sleep(1000);
			 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
}	        
	public void completeLPO4(int rowIndex, String statusText) throws InterruptedException  {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	// Wait until page loader disappears
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
          By.cssSelector(".fot-loader")));
	
	// Click correct row "More"
	WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//button[contains(@class,'dropdown-toggle')]")));
	Thread.sleep(1000);
	moreBtn.click();

	// Click Change Status from SAME row
	WebElement changeStatus = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//table//tbody//tr[" + rowIndex + "]//a[contains(text(),'Verify & Approve')]")));
	changeStatus.click();

	driver.findElement(By.xpath("//button[normalize-space()='SUBMIT']")).click();
	driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
	Thread.sleep(1000);
	 driver.findElement(By.xpath("//*[@id=\"react-confirm-alert\"]/div/div/div/div/button")).click();
}
	
//LPO stock validation
	
	public void viewLPO() throws InterruptedException {
	
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
		// Click correct row "More"
		WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr//button[contains(@class,'dropdown-toggle')]")));
		Thread.sleep(1000);
		moreBtn.click();

		// Click Change Status from SAME row
		WebElement ViewLPO = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//table//tbody//tr//a[contains(text(),'View LPO')]")));
		Thread.sleep(2000);
		ViewLPO.click();
		
}
	
	public String getPrdName() {
	    String ProductName    = driver.findElement(By.xpath("//tr[contains(@class,'custom-row')]/td[4]")).getText();
	            return ProductName;
	    }
		
	public String getUOM() {
		 String ProductUOM    = driver.findElement(By.xpath("//tr[contains(@class,'custom-row')]/td[5]")).getText();
		            return ProductUOM;
		}
	
	public String getSellerName() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    By sellerLocator = By.name("Seller");

	    WebElement sellerInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(sellerLocator)
	    );

	    String sellerName = sellerInput.getAttribute("value");

//	    System.out.println("Seller Name captured: [" + sellerName + "]");

	    return sellerName.trim();
	}
	
	public String getRecQty() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    WebElement recQtyElement = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//tr[contains(@class,'custom-row')]/td[10]")
	        )
	    );

	    String recQty = recQtyElement.getText().trim();

//	    System.out.println("Captured LPO Rec Qty : " + recQty);

	    return recQty;
	}
		
		public void clickBackBtn() throws InterruptedException {
		     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement clickBackBtn = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//div[@class='text-ve-modal label']")));

			clickBackBtn.click();

}
}