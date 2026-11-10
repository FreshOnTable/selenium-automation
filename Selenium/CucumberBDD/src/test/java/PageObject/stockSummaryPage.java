package PageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utils.PageObjectUtils;
public class stockSummaryPage {
	WebDriver driver;
	PageObjectUtils utils;
	public stockSummaryPage(WebDriver driver) {
		this.driver = driver;
		this.utils = new PageObjectUtils(driver);

    }
	
	//LPO stock validation	
			private By loader = By.cssSelector(".fot-loader");
			private By stockMenu = By.xpath("//span[text()='Stock']");
			private By stockSummaryMenu = By.xpath("//span[text()='Stock Summary']");
			private By productNameFilterIcon = By.xpath("//tr[@class='table-light']/th[4]/div[2]");
			private By productNameFilterInput = By.xpath("//input[@placeholder='Product Name/ Seller Name']");
			private By uomFilterIcon = By.xpath("//tr[@class='table-light']/th[6]/div[2]");
			
			private By uomFilterInput = By.xpath("//input[@placeholder='UOM']");
			private By productNameList = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[4]");
			private By expandRowIcon = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[1]");
			private By sellerNameList = By
					.xpath("//div[@class='custom-grid-body']/table/tbody/tr[not(td[1]//*[name()='svg' or self::i])]/td[4]");
			private By batchDetailsdropdown = By.xpath("//div[@class='px-2']/div/div/div/div[4]/select");
			private By referenceFilterIcon = By.xpath("//tr[@class='table-light']/th[4]/div[2]");
			private By referenceFilterInput = By.xpath("//input[@placeholder='Reference']");
			private By performedActionsColumn = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[5]");
			private By quantityColumn = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[6]");
			private By batchIdColumn = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[4]");
			private By expiryDateColumn = By.xpath("//div[@class='custom-grid-body']/table/tbody/tr/td[7]");

			

			public void clickStockMenu() {
//				utils.click(stockMenu);
				driver.findElement(stockMenu).click();
			}

			public void clickStockSummaryMenu() {
				utils.scrollToElement(stockSummaryMenu);
				utils.click(stockSummaryMenu);
			}

			public void clickProductNameFilterIcon() {
				utils.waitForLoaderToDisappear(loader);				
				utils.click(productNameFilterIcon);
			}

			public void clickUomFilterIcon() {
				utils.waitForElementToAppear(uomFilterIcon);
				driver.findElement(uomFilterIcon).click();
				
			}
			public void expandRowIcon() {
				utils.waitForElementToAppear(expandRowIcon);
				driver.findElement(expandRowIcon).click();
				
			}
						
			public void enterProductNameInFilterInput(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			    WebElement input = wait.until(
			            ExpectedConditions.elementToBeClickable(productNameFilterInput)
			    );

			    input.clear();
			    input.sendKeys(productName);
			    input.sendKeys(Keys.ENTER);
			}

			public void enterUomInFilterInput(String productUOM) throws InterruptedException {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			    WebElement input = driver.findElement(
			            (uomFilterInput)
			    );

			    input.clear();
			    input.sendKeys(productUOM);
			    Thread.sleep(1000); // Optional: Wait for suggestions to appear
			    input.sendKeys(Keys.ENTER);
			}
			
			public void selectproduct(String productName) {
				List<WebElement> rows = driver.findElements(productNameList);
				for (WebElement product : rows) {
					if (product.getText().equalsIgnoreCase(productName)) {
						product.findElement(By.xpath("../td[1]")).click();
						driver.findElement(expandRowIcon).click();
						product.click();
						break;
					}

				}
			}
			

			public void clickSeller(String sellerName) throws InterruptedException {
               WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			    if (sellerName == null || sellerName.trim().isEmpty()) {
			        throw new IllegalArgumentException(
			                "Seller name is NULL or EMPTY"
			        );
			    }

			    sellerName = sellerName.trim();

			    System.out.println("Searching seller: " + sellerName);

			    By sellerLocator = By.xpath(
			            "//tr[.//td[normalize-space()='" + sellerName + "']]"
			    );

			    WebElement sellerRow = wait.until(
			            ExpectedConditions.visibilityOfElementLocated(sellerLocator)
			    );

			    System.out.println("Seller row found: " + sellerName);

			    WebElement sellerCell = sellerRow.findElement(
			            By.xpath(
			                    ".//td[normalize-space()='" + sellerName + "']"
			            )
			    );

			    wait.until(
			            ExpectedConditions.elementToBeClickable(sellerCell)
			    );
                 Thread.sleep(1000); // Optional: Wait for any animations or transitions
			    sellerCell.click();

			    System.out.println(" Seller clicked: " + sellerName);
			}
			
			public void selectBatchDetails() throws InterruptedException {
				Thread.sleep(2000); // Optional: Wait for the dropdown to be ready
			    WebElement detailsType = driver.findElement(
			        By.cssSelector("select.details-type-select")
			    );

			    Select select = new Select(detailsType);
                 Thread.sleep(1000); // Optional: Wait for the dropdown to be ready
			    select.selectByVisibleText("Batch Details");
			}
			
			public void validateStockEntryQuantity(String expectedRecQty) {

			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			    System.out.println("======================================");
			    System.out.println("Stock Entry Validation");
			    System.out.println("Expected LPO Rec Qty : " + expectedRecQty);

			    List<WebElement> rows = wait.until(
			        ExpectedConditions.presenceOfAllElementsLocatedBy(
			            By.xpath("//tbody/tr")
			        )
			    );

			    boolean stockEntryFound = false;

			    for (WebElement row : rows) {

			        List<WebElement> cells = row.findElements(By.tagName("td"));

			        if (cells.size() < 6) {
			            continue;
			        }

			        String performedAction = cells.get(4).getText().trim();
			        String quantity = cells.get(5).getText().trim();

			        System.out.println("Performed Action : " + performedAction);
			        System.out.println("Stock In Quantity : " + quantity);

			        // Check Stock Entry
			        if (performedAction.equalsIgnoreCase("Stock Entry")) {

			            stockEntryFound = true;

			            // Convert quantities to numbers
			            double expectedQty = Double.parseDouble(expectedRecQty);
			            double actualQty = Double.parseDouble(quantity);

			            System.out.println("Expected Quantity : " + expectedQty);
			            System.out.println("Actual Quantity   : " + actualQty);

			            // Validate quantity
			            if (Double.compare(expectedQty, actualQty) == 0) {

			                System.out.println("======================================");
			                System.out.println("✅ TEST CASE PASSED");
			                System.out.println("Performed Action : Stock Entry");
			                System.out.println("LPO Rec Qty      : " + expectedQty);
			                System.out.println("Stock In Quantity   : " + actualQty);
			                System.out.println("Quantity matched successfully.");
			                System.out.println("======================================");

			                return;

			            } else {

			                System.out.println("======================================");
			                System.out.println("❌ TEST CASE FAILED");
			                System.out.println("Performed Action : Stock Entry");
			                System.out.println("LPO Rec Qty      : " + expectedQty);
			                System.out.println("Stock Quantity   : " + actualQty);
			                System.out.println("Quantity mismatch.");
			                System.out.println("======================================");

			                throw new AssertionError(
			                    "Stock Entry quantity mismatch. Expected: "
			                    + expectedQty + ", Actual: " + actualQty
			                );
			            }
			        }
			    }

			    // No Stock Entry found
			    if (!stockEntryFound) {

			        System.out.println("======================================");
			        System.out.println("❌ TEST CASE FAILED");
			        System.out.println("Stock Entry transaction not found.");
			        System.out.println("======================================");

			        throw new AssertionError(
			            "Stock Entry transaction was not found in Batch Details."
			        );
			    }
			}

			public double getQuatity() {
				utils.waitForElementToAppear(quantityColumn);
				List<WebElement> rows = driver.findElements(quantityColumn);
//			List<WebElement> QuantityColumn = driver.findElements(By.xpath("./td[6]"));
				double totalUsedQuantity = 0.0;
				for (WebElement row : rows) {
					String qtyText = row.getText();
					double qty = Math.abs(Double.parseDouble(qtyText.trim()));
					totalUsedQuantity += qty;
					System.out.println("Quantity in row: " + totalUsedQuantity);
				}
				return totalUsedQuantity;

			}		

}
