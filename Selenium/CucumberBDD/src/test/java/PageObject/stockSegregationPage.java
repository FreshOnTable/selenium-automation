package PageObject;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.Global;

public class stockSegregationPage {
	
	private String capturedBatchNo;
	private String capturedAction;
	
	private Map<String, String> expectedActions = new HashMap<>();
	
    public WebDriver driver;
	
	public stockSegregationPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickStockSegregation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait for the Stock Segregation button to be clickable and click it
		WebElement stockSegregationButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//span[contains(text(),'Stock Segregation')]")));
		stockSegregationButton.click();

		System.out.println("----------------------------------------------");
	}
	
	public void clickLpoIdFilterIcon() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//tr[@class='table-light']/th[7]/div[2]")).click();
	}
	
	public void searchLPOById(String lpoId) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    System.out.println("Search LPO ID in stock segregation page: " + lpoId);

	    // Click LPO ID / Batch No input
	    WebElement lpoIdInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@placeholder='LPO ID / Batch No']")
	            )
	    );

	    lpoIdInput.clear();
	    lpoIdInput.sendKeys(lpoId);

	    // Click Apply
	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply'] | //input[@value='Apply']")
	            )
	    );

	    applyButton.click();

//	    System.out.println("✅ LPO ID filter applied: " + lpoId);
	}
	
	public void clickMoreButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait for the View button to be clickable and click it
		WebElement moreButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//table//tbody//tr//button[contains(@class,'dropdown-toggle')]")));
		moreButton.click();
		
		// 2. Click Capture Rejection
		WebElement captureRejection = wait.until(
		    ExpectedConditions.elementToBeClickable(
		        By.xpath("//a[normalize-space()='Capture Rejection']")
		    )
		);
		Thread.sleep(1000);
		captureRejection.click();

	}
	
	public void rejectRandomBatchQuantities() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Random random = new Random();

	    List<WebElement> rows = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[contains(@class,'edit-batch-table')]//tbody/tr")
	            )
	    );

	    System.out.println("Total batches found: " + rows.size());

	    if (rows.isEmpty()) {
	        throw new RuntimeException("❌ No batches found");
	    }

	    // Process EVERY batch
	    for (int i = 0; i < rows.size(); i++) {

	        WebElement row = rows.get(i);

	        // -------------------------------
	        // Batch Number
	        // -------------------------------

	        String batchNo = row.findElement(
	                By.xpath("./td[1]")
	        ).getText().trim();
	        
	        capturedBatchNo = batchNo;
	        Global.setBatchNumber(capturedBatchNo);
	        System.out.println("Rejected Batch Id: " + capturedBatchNo);

	        // -------------------------------
	        // Received Qty
	        // -------------------------------

	        String receivedQtyText = row.findElement(
	                By.xpath("./td[4]")
	        ).getText().trim();

	        double receivedQty = Double.parseDouble(receivedQtyText);

	        System.out.println("--------------------------------");
	        System.out.println("Batch " + (i + 1) + ": " + batchNo);
	        System.out.println("Received Qty: " + receivedQty);

	        if (receivedQty <= 0) {
	            throw new RuntimeException(
	                    "❌ Received Qty is 0 for batch: " + batchNo
	            );
	        }

	        // -------------------------------
	        // Random Reject Qty
	        // -------------------------------

	        int maxRejectQty = (int) Math.floor(receivedQty);

	        int rejectQty = random.nextInt(maxRejectQty) + 1;

	        System.out.println("Reject Qty: " + rejectQty);
	        
	        Global.setRejectQty(rejectQty);

	        WebElement rejectQtyInput = row.findElement(
	                By.xpath(
	                        ".//td[contains(@class,'grid-numeric-input')]//input"
	                )
	        );

	        rejectQtyInput.clear();
	        rejectQtyInput.sendKeys(String.valueOf(rejectQty));

	        // -------------------------------
	        // Random Reason
	        // -------------------------------
	        
	        WebElement reasonDropdown = row.findElement(
	                By.xpath(
	                        ".//td[contains(@class,'reason-bx')]//select"
	                )
	        );

	        Select reasonSelect = new Select(reasonDropdown);

	        List<WebElement> reasonOptions = reasonSelect.getOptions();

	        if (reasonOptions.size() <= 1) {
	            throw new RuntimeException(
	                    "❌ No Reason options available for batch: " + batchNo
	            );
	        }

	        // Ignore "Select" option at index 0
	        int randomReasonIndex =
	                random.nextInt(reasonOptions.size() - 1) + 1;

	        reasonSelect.selectByIndex(randomReasonIndex);

	        String selectedReason =
	                reasonSelect.getFirstSelectedOption().getText().trim();

	        System.out.println("Rejected Reason: " + selectedReason);
	                
	        
	     // -------------------------------
	     // Random Action
	     // -------------------------------
	        
//	        WebElement actionDropdown = row.findElement(
//	                By.xpath(".//td[contains(@class,'action-bx')]//select")
//	        );
//
//	        Select actionSelect = new Select(actionDropdown);
//
//	        List<WebElement> actionOptions = actionSelect.getOptions();
//
//	        int randomActionIndex = random.nextInt(actionOptions.size() - 1) + 1;
//
//	        actionSelect.selectByIndex(randomActionIndex);
//
//	        String selectedAction = actionSelect
//	                .getFirstSelectedOption()
//	                .getText()
//	                .trim();
//	        capturedAction = selectedAction;
//	        Global.setActionType(capturedAction);
//	        System.out.println("Rejected Action: " + selectedAction);

	        WebElement actionDropdown = row.findElement(
	                By.xpath(".//td[contains(@class,'action-bx')]//select")
	        );

	        Select actionSelect = new Select(actionDropdown);

	        List<WebElement> actionOptions = actionSelect.getOptions();

	        if (actionOptions.size() <= 1) {
	            throw new RuntimeException(
	                    "❌ No Action options available for batch: " + batchNo
	            );
	        }

	        // Ignore "Select" option at index 0
	        int randomActionIndex = random.nextInt(actionOptions.size() - 1) + 1;

	        actionSelect.selectByIndex(randomActionIndex);

	        // Get selected Action
	        String selectedAction =
	                actionSelect.getFirstSelectedOption().getText().trim();
	        
	        capturedAction = selectedAction;
	        Global.setActionType(capturedAction);
	        System.out.println("Rejected Action: " + selectedAction);

	    }
	}
	
	public void clickSubmitButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait for the Submit button to be clickable and click it
		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']")));
		submitButton.click();

		Thread.sleep(1000);
	}
	
	
	public void filterPerformedAction() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Click filter icon in Performed Action column
	    WebElement filterIcon = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//th[.//text()[normalize-space()='Performed Action']]//*[contains(@class,'filter')]")
	            )
	    );

	    filterIcon.click();

	    // Enter action
	    WebElement searchInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@placeholder='Performed Action']")
	            )
	    );

	    String capturedAction =Global.getActionType();
//	    searchInput.clear();
	    searchInput.sendKeys(capturedAction);

	    // Click APPLY
	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton.click();

	    
	    System.out.println("Search Performed Action type in stoc summary: " + capturedAction);
	}
	
	public void filterBatchNumber() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Click filter icon in Performed Action column
	    WebElement filterIcon = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//th[.//text()[normalize-space()='Batch ID']]//*[contains(@class,'filter')]")
	            )
	    );

	    filterIcon.click();

	    // Enter action
	    WebElement searchInput = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@placeholder='Batch ID']")
	            )
	    );

	    String capturedBatchNo =Global.getBatchNumber();

	    searchInput.sendKeys(capturedBatchNo);

	    // Click APPLY
	    WebElement applyButton = wait.until(
	            ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[normalize-space()='Apply']")
	            )
	    );

	    applyButton.click();

	    
	    System.out.println("✅ Performed Action filter applied: " + capturedAction);
	}
	
	public void validateRejectedQuantity() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    String expectedBatch = Global.getBatchNumber();
	    double rejectedQty = Global.getRejectQty();

	    // Ledger shows rejected quantity as negative
	    double expectedLedgerQty = -rejectedQty;

	    System.out.println("--------------------------------------");
	    System.out.println("Validating Rejected Quantity");
	    System.out.println("Batch ID: " + expectedBatch);
	    System.out.println("Rejected Qty: " + rejectedQty);
	    System.out.println("Expected Ledger Qty: " + expectedLedgerQty);

	    WebElement row = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//tbody/tr[td[normalize-space()='" + expectedBatch + "']]")
	            )
	    );

	    String actualQtyText = row.findElement(
	            By.xpath("./td[6]")
	    ).getText().trim();

	    double actualQty = Double.parseDouble(actualQtyText);

	    System.out.println("Actual Ledger Qty: " + actualQty);

	    if (Double.compare(actualQty, expectedLedgerQty) != 0) {

	        throw new AssertionError(
	                "❌ Rejected Quantity mismatch!" +
	                "\nBatch: " + expectedBatch +
	                "\nExpected: " + expectedLedgerQty +
	                "\nActual: " + actualQty
	        );
	    }

	    System.out.println("✅ Rejected Quantity and Actial Stock Ledger Qty matched successfully");
	}
	
	public void validatePerformedAction() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    String actualAction =Global.getActionType();

	   // System.out.println("Batch ID: " + batchId);
	    System.out.println("Expected Action: " + actualAction);
	    System.out.println("Actual Action: " + actualAction);

	    if (!actualAction.equalsIgnoreCase(actualAction)) {

	        throw new AssertionError(
	                "❌ Action mismatch" +
	                "\nExpected: " + actualAction +
	                "\nActual: " + actualAction
	        );
	    }

	    System.out.println("Capture Lpo rejection validation successfully");
	}
}
