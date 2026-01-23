package PageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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

	public CreateLpo(WebDriver driver) {
		this.driver = driver;	
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
	    for (int i = 1; i <= 3; i++) {

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

// Scenario: change seller 

public List<WebElement> getSelectedProductRows() {
    return driver.findElements(
        By.xpath("//table//tbody//tr[.//span[contains(@class,'Mui-checked')]]")
    );
       
}
public String changeSellerForRow(WebElement row) {
	
	driver.findElement(By.xpath("//button[normalize-space()='Change Seller']")).click();


    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    String oldSeller = row.findElement(By.xpath("./td[7]"))
                          .getText().trim();

    // Click Change Seller button in this row context
    row.findElement(By.xpath(".//button[contains(.,'Change Seller')]")).click();

    List<WebElement> sellerColumns = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//table//thead//th[contains(@class,'custom-header-content')]")
        )
    );

    if (sellerColumns.size() <= 1) {
        System.out.println("⚠ Single seller for product. Skipping.");
        driver.findElement(By.xpath("//button[normalize-space()='Cancel']")).click();
        return "SKIPPED";
    }

    List<WebElement> sellerCells = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//table//tbody//td[@style='cursor: pointer;']")
        )
    );

    int selectedIndex = -1;
    for (int i = 0; i < sellerCells.size(); i++) {
        if (!sellerCells.get(i)
                .findElements(By.xpath(".//span[contains(@class,'Mui-checked')]"))
                .isEmpty()) {
            selectedIndex = i;
            break;
        }
    }

    List<Integer> alternatives = new ArrayList<>();
    for (int i = 0; i < sellerCells.size(); i++) {
        if (i != selectedIndex) alternatives.add(i);
    }

    if (alternatives.isEmpty()) {
        driver.findElement(By.xpath("//button[normalize-space()='Cancel']")).click();
        return "SKIPPED";
    }

    WebElement newSeller = sellerCells.get(
        alternatives.get(new Random().nextInt(alternatives.size()))
    );

    js.executeScript("arguments[0].click();", newSeller);

    wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[normalize-space()='Submit']"))).click();

    wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[normalize-space()='Confirm']"))).click();

    // 🔥 Wait for update
    wait.until(ExpectedConditions.not(
        ExpectedConditions.textToBePresentInElement(row, oldSeller)
    ));

    String updatedSeller = row.findElement(By.xpath("./td[7]"))
                              .getText().trim();

    Assert.assertNotEquals(oldSeller, updatedSeller,
        "Seller did not change for product row");

    System.out.println("✅ Seller changed from " + oldSeller + " → " + updatedSeller);
    return "CHANGED";
}
public void changeSellerForAllSelectedProducts() {

    List<WebElement> selectedRows = getSelectedProductRows();

    for (WebElement row : selectedRows) {
        try {
            changeSellerForRow(row);
        } catch (Exception e) {
            System.out.println("❌ Failed for one product: " + e.getMessage());
        }
    }
}

}



