package PageObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


    public class ProductMaster {

        public WebDriver driver;
        public WebDriverWait wait;

        // Stores the Product Line created in productLine()
        public String productLineName;

        public ProductMaster(WebDriver driver) {

            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        // ============================================================
        // PRODUCT MASTER PAGE
        // ============================================================

        public void productMasterPage() throws InterruptedException {

            Thread.sleep(2000);

            driver.findElement(
                    By.xpath("//div[@class='menu-cont']//img[@alt='img']")
            ).click();

            Thread.sleep(3000);

            driver.findElement(
                    By.xpath("//ul//li//span[text()='Product']")
            ).click();

            Thread.sleep(3000);

            driver.findElement(
                    By.xpath("//ul//li//span[text()='Master']")
            ).click();

            Thread.sleep(3000);
        }

        // ============================================================
        // CREATE PRODUCT LINE
        // ============================================================

        public void productLine() throws InterruptedException {

            driver.findElement(
                    By.xpath(
                            "//div[@class='accordion-header']//span[normalize-space()='Product Line']"
                    )
            ).click();

            WebElement createButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='Create']")
                    )
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    createButton
            );

            createButton.click();

            Thread.sleep(2000);

            // Create unique Product Line name
            productLineName = "Automation testing_" +
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                    );

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "Product Line Created : " + productLineName
            );

            System.out.println(
                    "========================================="
            );

            // Enter Product Line name
            WebElement nameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@name='name']")
                    )
            );

            nameInput.sendKeys(productLineName);

            Thread.sleep(2000);

            // Submit
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='Submit']")
                    )
            ).click();

            Thread.sleep(2000);

            // Confirm
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='Confirm']")
                    )
            ).click();

            Thread.sleep(2000);

            // OK
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='OK']")
                    )
            ).click();

            Thread.sleep(2000);

            System.out.println(
                    "Product Line created successfully!"
            );
        }

        // ============================================================
        // UPDATE PRODUCT LINE DESCRIPTION
        // ============================================================

        public void productLineDescriptionUpdation() throws InterruptedException {
        	
        	  driver.findElement(
                      By.xpath(
                              "//div[@class='accordion-header']//span[normalize-space()='Product Line']"
                      )
              ).click();

            WebDriverWait wait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(10)
                    );

            // Find all active rows
            String activeRowsXpath =
                    "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]";

            List<WebElement> activeRows =
                    driver.findElements(
                            By.xpath(activeRowsXpath)
                    );

            if (activeRows.isEmpty()) {

                System.out.println(
                        "No rows with status 'Active' were found!"
                );

                return;
            }

            // Pick random active row
            Random random = new Random();

            int randomIndex =
                    random.nextInt(activeRows.size());

            WebElement randomRow =
                    activeRows.get(randomIndex);

            // Capture Description
            WebElement descriptionCell =
                    randomRow.findElement(
                            By.xpath("./td[2]")
                    );

            String capturedDescription =
                    descriptionCell.getText().trim();

            System.out.println(
                    "Selected Active Row Description: "
                            + capturedDescription
            );

            // Click More button
            WebElement moreButton =
                    randomRow.findElement(
                            By.xpath(
                                    ".//button[contains(@class, 'actions-dropdown')]"
                            )
                    );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            moreButton
                    )
            ).click();

            // Click Edit Product Master
            WebElement editOption =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//a[normalize-space()='Edit Product Master']"
                                    )
                            )
                    );

            editOption.click();

            // ========================================================
            // NAME UPDATE
            // ========================================================

            WebElement nameInput =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//input[@name='name']")
                            )
                    );

            // Existing name
            String existingName =
                    nameInput.getAttribute("value");

            if (existingName == null ||
                    existingName.trim().isEmpty()) {

                existingName = capturedDescription;
            }

            existingName = existingName.trim();

            // Add ONLY "1"
            String updatedName =
                    existingName + "1";

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "Before Update : " + existingName
            );

            System.out.println(
                    "After Update  : " + updatedName
            );

            System.out.println(
                    "========================================="
            );

            // Clear existing name
            nameInput.click();

            nameInput.sendKeys(
                    Keys.CONTROL,
                    "a"
            );

            nameInput.sendKeys(
                    Keys.BACK_SPACE
            );

            Thread.sleep(1000);

            // Enter updated name
            nameInput.sendKeys(updatedName);

            Thread.sleep(2000);

            // Submit
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[normalize-space()='Submit']"
                            )
                    )
            ).click();

            Thread.sleep(2000);

            // Confirm
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[normalize-space()='Confirm']"
                            )
                    )
            ).click();

            Thread.sleep(2000);

            // OK
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[normalize-space()='OK']"
                            )
                    )
            ).click();

            Thread.sleep(2000);

            System.out.println(
                    "Product Line updated successfully!"
            );
        }

        // ============================================================
        // CREATE PRODUCT CATEGORY
        // ============================================================

        public void productCategory() throws InterruptedException {

            driver.findElement(
                    By.xpath(
                            "//div[@class='accordion-header']//span[normalize-space()='Product Categories']"
                    )
            ).click();

            WebElement createButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[text()='Create']")
                            )
                    );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    createButton
            );

            createButton.click();

            Thread.sleep(2000);

            // ========================================================
            // CREATE PRODUCT CATEGORY NAME
            // ========================================================

            String productCategoryName =
                    "Automation testing_" +
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                    );

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "Product Category Created : "
                            + productCategoryName
            );

            System.out.println(
                    "========================================="
            );

            // Enter Product Category name
            WebElement nameInput =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//input[@name='name']")
                            )
                    );

            nameInput.sendKeys(productCategoryName);

            Thread.sleep(2000);

         // ============================================================
         // PRODUCT VALUE - SELECT CREATED PRODUCT LINE
         // ============================================================

         if (productLineName == null || productLineName.trim().isEmpty()) {

             throw new IllegalStateException(
                     "Product Line Name is NULL. productLine() must execute before productCategory()."
             );
         }

         System.out.println(
                 "Product Line to Select : " + productLineName
         );

         WebElement productValueDropdown = wait.until(
                 ExpectedConditions.presenceOfElementLocated(
                         By.xpath("//select[@name='productValue']")
                 )
         );

         // Scroll Product Value into view
         ((JavascriptExecutor) driver).executeScript(
                 "arguments[0].scrollIntoView({block: 'center'});",
                 productValueDropdown
         );

         Thread.sleep(1000);

         // Wait until enabled
         wait.until(driver -> productValueDropdown.isEnabled());

         // Create Select object using the DROPDOWN element
         Select productValue = new Select(productValueDropdown);

         // Select the Product Line created earlier
         productValue.selectByVisibleText(productLineName);

         System.out.println(
                 "Product Line Selected in Product Value : "
                         + productLineName
         );

         Thread.sleep(2000);

            // ========================================================
            // SUBMIT
            // ========================================================

            WebElement submitButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[text()='Submit']")
                            )
                    );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    submitButton
            );

            submitButton.click();

            Thread.sleep(2000);

            // Confirm
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='Confirm']")
                    )
            ).click();

            Thread.sleep(2000);

            // OK
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='OK']")
                    )
            ).click();

            Thread.sleep(2000);

            System.out.println(
                    "Product Category created successfully!"
            );
        }

        // ============================================================
        // UPDATE PRODUCT CATEGORY DESCRIPTION
        // ============================================================

        public void productCategoryDescriptionUpdation() throws InterruptedException {
        	
        	    driver.findElement(
        	            By.xpath(
        	                    "//div[@class='accordion-header']//span[normalize-space()='Product Categories']"
        	            )
        	    ).click();

        	    WebDriverWait wait =
        	            new WebDriverWait(
        	                    driver,
        	                    Duration.ofSeconds(10)
        	            );

        	    // ========================================================
        	    // FIND ALL ACTIVE ROWS
        	    // ========================================================

        	    String activeRowsXpath =
        	            "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]";

        	    List<WebElement> activeRows =
        	            driver.findElements(
        	                    By.xpath(activeRowsXpath)
        	            );

        	    if (activeRows.isEmpty()) {

        	        System.out.println(
        	                "No rows with status 'Active' were found!"
        	        );

        	        return;
        	    }

        	    // ========================================================
        	    // PICK RANDOM ACTIVE ROW
        	    // ========================================================

        	    Random random = new Random();

        	    int randomIndex =
        	            random.nextInt(activeRows.size());

        	    // Use row index instead of storing WebElement
        	    int selectedRowIndex =
        	            randomIndex + 1;

        	    String selectedRowXpath =
        	            "(" +
        	                    activeRowsXpath +
        	                    ")[" +
        	                    selectedRowIndex +
        	                    "]";

        	    // ========================================================
        	    // CAPTURE DESCRIPTION
        	    // ========================================================

        	    WebElement descriptionCell =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            By.xpath(
        	                                    selectedRowXpath + "/td[2]"
        	                            )
        	                    )
        	            );

        	    String capturedDescription =
        	            descriptionCell.getText().trim();

        	    System.out.println(
        	            "Selected Active Row Description: "
        	                    + capturedDescription
        	    );

        	    // ========================================================
        	    // CLICK MORE
        	    // ========================================================

        	    By moreButtonLocator =
        	            By.xpath(
        	                    selectedRowXpath +
        	                            "//button[contains(@class,'actions-dropdown')]"
        	            );

        	    boolean moreClicked = false;

        	    for (int attempt = 1; attempt <= 3; attempt++) {

        	        try {

        	            WebElement moreButton =
        	                    wait.until(
        	                            ExpectedConditions.elementToBeClickable(
        	                                    moreButtonLocator
        	                            )
        	                    );

        	            ((JavascriptExecutor) driver).executeScript(
        	                    "arguments[0].scrollIntoView({block:'center'});",
        	                    moreButton
        	            );

        	            Thread.sleep(500);

        	            moreButton.click();

        	            moreClicked = true;

        	            System.out.println(
        	                    "More option clicked successfully."
        	            );

        	            break;

        	        } catch (StaleElementReferenceException e) {

        	            System.out.println(
        	                    "More button became stale. Retry : "
        	                            + attempt
        	            );

        	            Thread.sleep(1000);
        	        }
        	    }

        	    if (!moreClicked) {

        	        throw new IllegalStateException(
        	                "Unable to click More option after 3 attempts."
        	        );
        	    }

        	    // ========================================================
        	    // EDIT PRODUCT MASTER
        	    // ========================================================

        	    By editProductMasterLocator =
        	            By.xpath(
        	                    "//a[normalize-space()='Edit Product Master']"
        	            );

        	    boolean editClicked = false;

        	    for (int attempt = 1; attempt <= 3; attempt++) {

        	        try {

        	            WebElement editOption =
        	                    wait.until(
        	                            ExpectedConditions.elementToBeClickable(
        	                                    editProductMasterLocator
        	                            )
        	                    );

        	            ((JavascriptExecutor) driver).executeScript(
        	                    "arguments[0].scrollIntoView({block:'center'});",
        	                    editOption
        	            );

        	            Thread.sleep(500);

        	            editOption.click();

        	            editClicked = true;

        	            System.out.println(
        	                    "Edit Product Master clicked successfully."
        	            );

        	            break;

        	        } catch (StaleElementReferenceException e) {

        	            System.out.println(
        	                    "Edit Product Master became stale. Retry : "
        	                            + attempt
        	            );

        	            Thread.sleep(1000);

        	            // Re-open More using a fresh element
        	            try {

        	                WebElement freshMoreButton =
        	                        wait.until(
        	                                ExpectedConditions.elementToBeClickable(
        	                                        moreButtonLocator
        	                                )
        	                        );

        	                ((JavascriptExecutor) driver).executeScript(
        	                        "arguments[0].scrollIntoView({block:'center'});",
        	                        freshMoreButton
        	                );

        	                Thread.sleep(500);

        	                freshMoreButton.click();

        	                Thread.sleep(1000);

        	            } catch (StaleElementReferenceException e2) {

        	                System.out.println(
        	                        "More button also became stale. Retrying..."
        	                );
        	            }
        	        }
        	    }

        	    if (!editClicked) {

        	        throw new IllegalStateException(
        	                "Unable to click Edit Product Master after 3 attempts."
        	        );
        	    }

        	    // ========================================================
        	    // NAME UPDATE
        	    // ========================================================

        	    WebElement nameInput =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            By.xpath("//input[@name='name']")
        	                    )
        	            );

        	    // Existing name
        	    String existingName =
        	            nameInput.getAttribute("value");

        	    if (existingName == null ||
        	            existingName.trim().isEmpty()) {

        	        existingName =
        	                capturedDescription;
        	    }

        	    existingName =
        	            existingName.trim();

        	    // Add ONLY "1"
        	    String updatedName =
        	            existingName + "1";

        	    System.out.println(
        	            "========================================="
        	    );

        	    System.out.println(
        	            "Before Update : "
        	                    + existingName
        	    );

        	    System.out.println(
        	            "After Update  : "
        	                    + updatedName
        	    );

        	    System.out.println(
        	            "========================================="
        	    );

        	    // ========================================================
        	    // CLEAR EXISTING NAME
        	    // ========================================================

        	    nameInput.click();

        	    nameInput.sendKeys(
        	            Keys.CONTROL,
        	            "a"
        	    );

        	    nameInput.sendKeys(
        	            Keys.BACK_SPACE
        	    );

        	    Thread.sleep(1000);

        	    // ========================================================
        	    // ENTER UPDATED NAME
        	    // ========================================================

        	    nameInput.sendKeys(
        	            updatedName
        	    );

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // PRODUCT VALUE
        	    // RANDOM SELECTION
        	    // EXCLUDE FIRST 2 OPTIONS
        	    // ========================================================

        	    By productValueLocator =
        	            By.xpath("//select[@name='productValue']");

        	    WebElement productValueDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productValueLocator
        	                    )
        	            );

        	    Select productValue =
        	            new Select(productValueDropdown);

        	    int optionCount =
        	            productValue.getOptions().size();

        	    if (optionCount <= 2) {

        	        throw new IllegalStateException(
        	                "Product Value dropdown does not have enough options."
        	        );
        	    }

        	    // Random index from option 2 onwards
        	    int randomProductValueIndex =
        	            new Random().nextInt(optionCount - 2) + 2;

        	    // Capture value and text immediately
        	    String selectedProductValue =
        	            productValue.getOptions()
        	                    .get(randomProductValueIndex)
        	                    .getAttribute("value");

        	    String selectedProductValueText =
        	            productValue.getOptions()
        	                    .get(randomProductValueIndex)
        	                    .getText()
        	                    .trim();

        	    System.out.println(
        	            "Selected Product Value : "
        	                    + selectedProductValueText
        	    );

        	    // Re-locate dropdown before selection
        	    productValueDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productValueLocator
        	                    )
        	            );

        	    productValue =
        	            new Select(productValueDropdown);

        	    productValue.selectByValue(
        	            selectedProductValue
        	    );

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // SUBMIT
        	    // ========================================================

        	    WebElement submitButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='Submit']"
        	                            )
        	                    )
        	            );

        	    ((JavascriptExecutor) driver).executeScript(
        	            "arguments[0].scrollIntoView({block: 'center'});",
        	            submitButton
        	    );

        	    Thread.sleep(500);

        	    submitButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // CONFIRM
        	    // ========================================================

        	    WebElement confirmButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='Confirm']"
        	                            )
        	                    )
        	            );

        	    confirmButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // OK
        	    // ========================================================

        	    WebElement okButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='OK']"
        	                            )
        	                    )
        	            );

        	    okButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // SUCCESS
        	    // ========================================================

        	    System.out.println(
        	            "Product Category updated successfully!"
        	    );
        	}
        
       
        
        public void productSubCategory() throws InterruptedException {

            driver.findElement(
                    By.xpath(
                            "//div[@class='accordion-header']//span[normalize-space()='Product Sub-Categories']"
                    )
            ).click();

            WebElement createButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[text()='Create']")
                            )
                    );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    createButton
            );

            createButton.click();

            Thread.sleep(2000);

            // ========================================================
            // CREATE PRODUCT CATEGORY NAME
            // ========================================================

            String productCategoryName =
                    "Automation testing_" +
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                    );

            System.out.println(
                    "========================================="
            );

            System.out.println(
                    "Product Sub Category Created : "
                            + productCategoryName
            );

            System.out.println(
                    "========================================="
            );

            // Enter Product Category name
            WebElement nameInput =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//input[@name='name']")
                            )
                    );

            nameInput.sendKeys(productCategoryName);

            Thread.sleep(2000);

         // ============================================================
         // PRODUCT VALUE - SELECT CREATED PRODUCT LINE
         // ============================================================

         if (productLineName == null || productLineName.trim().isEmpty()) {

             throw new IllegalStateException(
                     "Product Line Name is NULL. productLine() must execute before productCategory()."
             );
         }

         System.out.println(
                 "Product Line to Select : " + productLineName
         );

         WebElement productValueDropdown = wait.until(
                 ExpectedConditions.presenceOfElementLocated(
                         By.xpath("//select[@name='productValue']")
                 )
         );

         // Scroll Product Value into view
         ((JavascriptExecutor) driver).executeScript(
                 "arguments[0].scrollIntoView({block: 'center'});",
                 productValueDropdown
         );

         Thread.sleep(1000);

         // Wait until enabled
         wait.until(driver -> productValueDropdown.isEnabled());

         // Create Select object using the DROPDOWN element
         Select productValue = new Select(productValueDropdown);

         // Select the Product Line created earlier
         productValue.selectByVisibleText(productLineName);

         System.out.println(
                 "Product Line Selected in Product Value : "
                         + productLineName
         );

         Thread.sleep(2500);
         
         
      // ============================================================
      // PRODUCT CATEGORY - SELECT RANDOM PRODUCT CATEGORY
      // ============================================================

      WebElement productCategoryDropdown = wait.until(
              ExpectedConditions.visibilityOfElementLocated(
                      By.xpath("//select[@name='productCategory']")
              )
      );

      Select productCategory = new Select(productCategoryDropdown);

      // Get option count first
      int optionCount = productCategory.getOptions().size();

      if (optionCount <= 2) {
          throw new IllegalStateException(
                  "Product Category dropdown does not have enough options."
          );
      }

      // Exclude first 2 options
      int randomIndex = new Random().nextInt(optionCount - 2) + 2;

      // Get the value and text immediately
      String selectedCategoryValue =
              productCategory.getOptions()
                      .get(randomIndex)
                      .getAttribute("value");

      String selectedCategoryText =
              productCategory.getOptions()
                      .get(randomIndex)
                      .getText()
                      .trim();

      System.out.println(
              "Selected Product Category : " + selectedCategoryText
      );

      // Re-locate the dropdown before selecting
      productCategoryDropdown = wait.until(
              ExpectedConditions.visibilityOfElementLocated(
                      By.xpath("//select[@name='productCategory']")
              )
      );

      productCategory = new Select(productCategoryDropdown);

      // Select using the stored value
      productCategory.selectByValue(selectedCategoryValue);

      System.out.println(
              "Product Category Selected : " + selectedCategoryText
      );

      Thread.sleep(2000);
         
         
     	
     	Random random = new Random();

     	int wholesaleMargin = random.nextInt(100) + 1;
     	int retailMargin = random.nextInt(100) + 1;

     	WebElement wholesaleMarginField = wait.until(
     	        ExpectedConditions.visibilityOfElementLocated(
     	                By.name("wholeSaleMargin")
     	        )
     	);

     	wholesaleMarginField.clear();
     	wholesaleMarginField.sendKeys(String.valueOf(wholesaleMargin));

     	System.out.println("Wholesale Margin : " + wholesaleMargin);
     	
     	Thread.sleep(2000);


     	WebElement retailMarginField = wait.until(
     	        ExpectedConditions.visibilityOfElementLocated(
     	                By.name("retailMargin")
     	        )
     	);

     	retailMarginField.clear();
     	retailMarginField.sendKeys(String.valueOf(retailMargin));

     	System.out.println("Retail Margin : " + retailMargin);
         
         

            // ========================================================
            // SUBMIT
            // ========================================================

            WebElement submitButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[text()='Submit']")
                            )
                    );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    submitButton
            );

            submitButton.click();

            Thread.sleep(2000);

            // Confirm
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='Confirm']")
                    )
            ).click();

            Thread.sleep(2000);

            // OK
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[text()='OK']")
                    )
            ).click();

            Thread.sleep(2000);

            System.out.println(
                    "Product Category created successfully!"
            );
        }
        
        
        public void productSubCategoryDescriptionUpdation() throws InterruptedException {
        	

        	    driver.findElement(
        	            By.xpath(
        	                    "//div[@class='accordion-header']//span[normalize-space()='Product Sub-Categories']"
        	            )
        	    ).click();

        	    WebDriverWait wait =
        	            new WebDriverWait(
        	                    driver,
        	                    Duration.ofSeconds(10)
        	            );

        	    // ========================================================
        	    // FIND ALL ACTIVE ROWS
        	    // ========================================================

        	    String activeRowsXpath =
        	            "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]";

        	    List<WebElement> activeRows =
        	            driver.findElements(
        	                    By.xpath(activeRowsXpath)
        	            );

        	    if (activeRows.isEmpty()) {

        	        System.out.println(
        	                "No rows with status 'Active' were found!"
        	        );

        	        return;
        	    }

        	    // ========================================================
        	    // PICK RANDOM ACTIVE ROW
        	    // ========================================================

        	    Random random = new Random();

        	    int randomIndex =
        	            random.nextInt(activeRows.size());

        	    // IMPORTANT:
        	    // Do not store randomRow as WebElement
        	    int selectedRowIndex =
        	            randomIndex + 1;

        	    String selectedRowXpath =
        	            "(" +
        	                    activeRowsXpath +
        	                    ")[" +
        	                    selectedRowIndex +
        	                    "]";

        	    // ========================================================
        	    // CAPTURE DESCRIPTION
        	    // ========================================================

        	    WebElement descriptionCell =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            By.xpath(
        	                                    selectedRowXpath + "/td[2]"
        	                            )
        	                    )
        	            );

        	    String capturedDescription =
        	            descriptionCell.getText().trim();

        	    System.out.println(
        	            "Selected Active Row Description: "
        	                    + capturedDescription
        	    );

        	    // ========================================================
        	    // CLICK MORE
        	    // ========================================================

        	    By moreButtonLocator =
        	            By.xpath(
        	                    selectedRowXpath +
        	                            "//button[contains(@class,'actions-dropdown')]"
        	            );

        	    boolean moreClicked = false;

        	    for (int attempt = 1; attempt <= 3; attempt++) {

        	        try {

        	            WebElement moreButton =
        	                    wait.until(
        	                            ExpectedConditions.elementToBeClickable(
        	                                    moreButtonLocator
        	                            )
        	                    );

        	            ((JavascriptExecutor) driver).executeScript(
        	                    "arguments[0].scrollIntoView({block:'center'});",
        	                    moreButton
        	            );

        	            Thread.sleep(500);

        	            moreButton.click();

        	            moreClicked = true;

        	            System.out.println(
        	                    "More option clicked successfully."
        	            );

        	            break;

        	        } catch (StaleElementReferenceException e) {

        	            System.out.println(
        	                    "More button became stale. Retry : "
        	                            + attempt
        	            );

        	            Thread.sleep(1000);
        	        }
        	    }

        	    if (!moreClicked) {

        	        throw new IllegalStateException(
        	                "Unable to click More option after 3 attempts."
        	        );
        	    }

        	    // ========================================================
        	    // EDIT PRODUCT MASTER
        	    // ========================================================

        	    By editProductMasterLocator =
        	            By.xpath(
        	                    "//a[normalize-space()='Edit Product Master']"
        	            );

        	    boolean editClicked = false;

        	    for (int attempt = 1; attempt <= 3; attempt++) {

        	        try {

        	            WebElement editOption =
        	                    wait.until(
        	                            ExpectedConditions.elementToBeClickable(
        	                                    editProductMasterLocator
        	                            )
        	                    );

        	            ((JavascriptExecutor) driver).executeScript(
        	                    "arguments[0].scrollIntoView({block:'center'});",
        	                    editOption
        	            );

        	            Thread.sleep(500);

        	            editOption.click();

        	            editClicked = true;

        	            System.out.println(
        	                    "Edit Product Master clicked successfully."
        	            );

        	            break;

        	        } catch (StaleElementReferenceException e) {

        	            System.out.println(
        	                    "Edit Product Master became stale. Retry : "
        	                            + attempt
        	            );

        	            Thread.sleep(1000);

        	            // Re-open More using fresh element
        	            try {

        	                WebElement freshMoreButton =
        	                        wait.until(
        	                                ExpectedConditions.elementToBeClickable(
        	                                        moreButtonLocator
        	                                )
        	                        );

        	                ((JavascriptExecutor) driver).executeScript(
        	                        "arguments[0].scrollIntoView({block:'center'});",
        	                        freshMoreButton
        	                );

        	                Thread.sleep(500);

        	                freshMoreButton.click();

        	                Thread.sleep(1000);

        	            } catch (StaleElementReferenceException e2) {

        	                System.out.println(
        	                        "More button also became stale. Retrying..."
        	                );
        	            }
        	        }
        	    }

        	    if (!editClicked) {

        	        throw new IllegalStateException(
        	                "Unable to click Edit Product Master after 3 attempts."
        	        );
        	    }

        	    // ========================================================
        	    // NAME UPDATE
        	    // ========================================================

        	    WebElement nameInput =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            By.xpath("//input[@name='name']")
        	                    )
        	            );

        	    String existingName =
        	            nameInput.getAttribute("value");

        	    if (existingName == null ||
        	            existingName.trim().isEmpty()) {

        	        existingName =
        	                capturedDescription;
        	    }

        	    existingName =
        	            existingName.trim();

        	    // Add ONLY "1"
        	    String updatedName =
        	            existingName + "1";

        	    System.out.println(
                         "========================================="
        	    );

        	    System.out.println(
        	            "Before Update : " + existingName
        	    );

        	    System.out.println(
        	            "After Update  : " + updatedName
        	    );

        	    System.out.println(
        	            "========================================="
        	    );

        	    Thread.sleep(1500);

        	    nameInput.click();

        	    nameInput.sendKeys(
        	            Keys.CONTROL,
        	            "a"
        	    );

        	    nameInput.sendKeys(
        	            Keys.BACK_SPACE
        	    );

        	    Thread.sleep(1000);


        	    nameInput.sendKeys(
        	            updatedName
        	    );

        	    Thread.sleep(2000);
        	    

        	    By productValueLocator =
        	            By.xpath("//select[@name='productValue']");

        	    WebElement productValueDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productValueLocator
        	                    )
        	            );

        	    Select productValue =
        	            new Select(productValueDropdown);

        	    int optionCount =
        	            productValue.getOptions().size();

        	    if (optionCount <= 2) {

        	        throw new IllegalStateException(
        	                "Product Value dropdown does not have enough options."
        	        );
        	    }

        	    int randomProductValueIndex =
        	            new Random().nextInt(optionCount - 2) + 2;

        	    // Capture immediately to avoid stale element
        	    String selectedProductValue =
        	            productValue.getOptions()
        	                    .get(randomProductValueIndex)
        	                    .getAttribute("value");

        	    String selectedProductValueText =
        	            productValue.getOptions()
        	                    .get(randomProductValueIndex)
        	                    .getText()
        	                    .trim();

        	    System.out.println(
        	            "Selected Product Value : "
        	                    + selectedProductValueText
        	    );

        	    // Re-locate dropdown
        	    productValueDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productValueLocator
        	                    )
        	            );

        	    productValue =
        	            new Select(productValueDropdown);

        	    productValue.selectByValue(
        	            selectedProductValue
        	    );

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // PRODUCT CATEGORY
        	    // RANDOM SELECTION
        	    // EXCLUDE FIRST 2 OPTIONS
        	    // ========================================================

        	    By productCategoryLocator =
        	            By.xpath("//select[@name='productCategory']");

        	    WebElement productCategoryDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productCategoryLocator
        	                    )
        	            );

        	    Select productCategory =
        	            new Select(productCategoryDropdown);

        	    int categoryOptionCount =
        	            productCategory.getOptions().size();

        	    if (categoryOptionCount <= 2) {

        	        throw new IllegalStateException(
        	                "Product Category dropdown does not have enough options."
        	        );
        	    }

        	    int randomCategoryIndex =
        	            new Random().nextInt(categoryOptionCount - 2) + 2;

        	    // Capture value and text immediately
        	    String selectedCategoryValue =
        	            productCategory.getOptions()
        	                    .get(randomCategoryIndex)
        	                    .getAttribute("value");

        	    String selectedCategoryText =
        	            productCategory.getOptions()
        	                    .get(randomCategoryIndex)
        	                    .getText()
        	                    .trim();

        	    System.out.println(
        	            "Selected Product Sub Category : "
        	                    + selectedCategoryText
        	    );

        	    // Re-locate dropdown before selecting
        	    productCategoryDropdown =
        	            wait.until(
        	                    ExpectedConditions.visibilityOfElementLocated(
        	                            productCategoryLocator
        	                    )
        	            );

        	    productCategory =
        	            new Select(productCategoryDropdown);

        	    productCategory.selectByValue(
        	            selectedCategoryValue
        	    );

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // SUBMIT
        	    // ========================================================

        	    WebElement submitButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='Submit']"
        	                            )
        	                    )
        	            );

        	    ((JavascriptExecutor) driver).executeScript(
        	            "arguments[0].scrollIntoView({block:'center'});",
        	            submitButton
        	    );

        	    Thread.sleep(500);

        	    submitButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // CONFIRM
        	    // ========================================================

        	    WebElement confirmButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='Confirm']"
        	                            )
        	                    )
        	            );

        	    confirmButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // OK
        	    // ========================================================

        	    WebElement okButton =
        	            wait.until(
        	                    ExpectedConditions.elementToBeClickable(
        	                            By.xpath(
        	                                    "//button[normalize-space()='OK']"
        	                            )
        	                    )
        	            );

        	    okButton.click();

        	    Thread.sleep(2000);

        	    // ========================================================
        	    // SUCCESS
        	    // ========================================================

        	    System.out.println(
        	            "Product Sub Category updated successfully!"
        	    );
        	}
        
        
        
        
        public void TemperatureDescriptionUpdation() throws InterruptedException {

            driver.findElement(By.xpath(
                    "//div[@class='accordion-header']//span[normalize-space()='Temperature']"
            )).click();

            Thread.sleep(1000);

            List<WebElement> activeRows = driver.findElements(By.xpath(
                    "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]"
            ));

            WebElement randomRow = activeRows.get(
                    new Random().nextInt(activeRows.size())
            );

            // Click More
            randomRow.findElement(By.xpath(
                    ".//button[contains(@class,'actions-dropdown')]"
            )).click();

            Thread.sleep(500);

            // Edit Product Master
            driver.findElement(By.xpath(
                    "//a[normalize-space()='Edit Product Master']"
            )).click();

            Thread.sleep(1500);

            // ========================================================
            // NAME / DESCRIPTION UPDATE
            // ========================================================

            WebElement nameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@name='name']")
                    )
            );

            String existingName = nameInput.getAttribute("value");

            existingName = existingName == null ? "" : existingName.trim();

            String updatedName = existingName + "1";

            System.out.println("=========================================");
            System.out.println("Before Update : " + existingName);
            System.out.println("After Update  : " + updatedName);
            System.out.println("=========================================");

            Thread.sleep(1000);

            nameInput.click();

            nameInput.sendKeys(Keys.CONTROL, "a");
            nameInput.sendKeys(Keys.BACK_SPACE);

            Thread.sleep(500);

            nameInput.sendKeys(updatedName);

            Thread.sleep(1500);

            
            // ========================================================
            // SUBMIT
            // ========================================================

            WebElement submitButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Submit']")
                    )
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    submitButton
            );

            Thread.sleep(500);

            submitButton.click();

            Thread.sleep(2000);

            // ========================================================
            // CONFIRM
            // ========================================================

            WebElement confirmButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Confirm']")
                    )
            );

            confirmButton.click();

            Thread.sleep(2000);

            // ========================================================
            // OK
            // ========================================================

            WebElement okButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='OK']")
                    )
            );

            okButton.click();

            Thread.sleep(2000);

            // ========================================================
            // SUCCESS
            // ========================================================

            System.out.println(
                    "Temperature updated successfully!"
            );
        }
     
        
        public void UnitOfMeasurementDescriptionUpdation() throws InterruptedException {

            driver.findElement(By.xpath(
                    "//div[@class='accordion-header']//span[normalize-space()='Unit Of Measurement']"
            )).click();

            Thread.sleep(1000);

            List<WebElement> activeRows = driver.findElements(By.xpath(
                    "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]"
            ));

            WebElement randomRow = activeRows.get(
                    new Random().nextInt(activeRows.size())
            );

            // Click More
            randomRow.findElement(By.xpath(
                    ".//button[contains(@class,'actions-dropdown')]"
            )).click();

            Thread.sleep(500);

            // Edit Product Master
            driver.findElement(By.xpath(
                    "//a[normalize-space()='Edit Product Master']"
            )).click();

            Thread.sleep(1500);

            // ========================================================
            // NAME / DESCRIPTION UPDATE
            // ========================================================

            WebElement nameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@name='name']")
                    )
            );

            String existingName = nameInput.getAttribute("value");

            existingName = existingName == null ? "" : existingName.trim();

            String updatedName = existingName + "1";

            System.out.println("=========================================");
            System.out.println("Before Update : " + existingName);
            System.out.println("After Update  : " + updatedName);
            System.out.println("=========================================");

            Thread.sleep(1000);

            nameInput.click();

            nameInput.sendKeys(Keys.CONTROL, "a");
            nameInput.sendKeys(Keys.BACK_SPACE);

            Thread.sleep(500);

            nameInput.sendKeys(updatedName);

            Thread.sleep(1500);

            // ========================================================
            // SUBMIT
            // ========================================================

            WebElement submitButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Submit']")
                    )
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    submitButton
            );

            Thread.sleep(500);

            submitButton.click();

            Thread.sleep(2000);

            // ========================================================
            // CONFIRM
            // ========================================================

            WebElement confirmButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Confirm']")
                    )
            );

            confirmButton.click();

            Thread.sleep(2000);

            // ========================================================
            // OK
            // ========================================================

            WebElement okButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='OK']")
                    )
            );

            okButton.click();

            Thread.sleep(2000);

            // ========================================================
            // SUCCESS
            // ========================================================

            System.out.println(
                    "Unit Of Measurement updated successfully!"
            );
        }
        
        public void AllergensDescriptionUpdation() throws InterruptedException {

            driver.findElement(By.xpath(
                    "//div[@class='accordion-header']//span[normalize-space()='Allergens']"
            )).click();

            Thread.sleep(1000);

            List<WebElement> activeRows = driver.findElements(By.xpath(
                    "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]"
            ));

            WebElement randomRow = activeRows.get(
                    new Random().nextInt(activeRows.size())
            );

            // Click More
            randomRow.findElement(By.xpath(
                    ".//button[contains(@class,'actions-dropdown')]"
            )).click();

            Thread.sleep(500);

            // Edit Product Master
            driver.findElement(By.xpath(
                    "//a[normalize-space()='Edit Product Master']"
            )).click();

            Thread.sleep(1500);

            // NAME / DESCRIPTION UPDATE
            WebElement nameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@name='name']")
                    )
            );

            String existingName = nameInput.getAttribute("value");

            existingName = existingName == null ? "" : existingName.trim();

            String updatedName = existingName + "1";

            System.out.println("=========================================");
            System.out.println("Before Update : " + existingName);
            System.out.println("After Update  : " + updatedName);
            System.out.println("=========================================");

            Thread.sleep(1000);

            nameInput.click();

            nameInput.sendKeys(Keys.CONTROL, "a");
            nameInput.sendKeys(Keys.BACK_SPACE);

            Thread.sleep(500);

            nameInput.sendKeys(updatedName);

            Thread.sleep(1500);

            // SUBMIT
            WebElement submitButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Submit']")
                    )
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    submitButton
            );

            Thread.sleep(500);

            submitButton.click();

            Thread.sleep(2000);

            // CONFIRM
            WebElement confirmButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Confirm']")
                    )
            );

            confirmButton.click();

            Thread.sleep(2000);

            // OK
            WebElement okButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='OK']")
                    )
            );

            okButton.click();

            Thread.sleep(2000);

            System.out.println(
                    "Allergens updated successfully!"
            );
        }
        
        public void FarmTechnologyDescriptionUpdation() throws InterruptedException {

            driver.findElement(By.xpath(
                    "//div[@class='accordion-header']//span[normalize-space()='Farm Technology']"
            )).click();

            Thread.sleep(1000);

            List<WebElement> activeRows = driver.findElements(By.xpath(
                    "//div[@class='custom-grid-body']//tr[td[contains(normalize-space(), 'Active')]]"
            ));

            WebElement randomRow = activeRows.get(
                    new Random().nextInt(activeRows.size())
            );

            // Click More
            randomRow.findElement(By.xpath(
                    ".//button[contains(@class,'actions-dropdown')]"
            )).click();

            Thread.sleep(500);

            // Edit Product Master
            driver.findElement(By.xpath(
                    "//a[normalize-space()='Edit Product Master']"
            )).click();

            Thread.sleep(1500);

            // NAME / DESCRIPTION UPDATE
            WebElement nameInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@name='name']")
                    )
            );

            String existingName = nameInput.getAttribute("value");

            existingName = existingName == null ? "" : existingName.trim();

            String updatedName = existingName + "1";

            System.out.println("=========================================");
            System.out.println("Before Update : " + existingName);
            System.out.println("After Update  : " + updatedName);
            System.out.println("=========================================");

            Thread.sleep(1000);

            nameInput.click();

            nameInput.sendKeys(Keys.CONTROL, "a");
            nameInput.sendKeys(Keys.BACK_SPACE);

            Thread.sleep(500);

            nameInput.sendKeys(updatedName);

            Thread.sleep(1500);

            // SUBMIT
            WebElement submitButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Submit']")
                    )
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    submitButton
            );

            Thread.sleep(500);

            submitButton.click();

            Thread.sleep(2000);

            // CONFIRM
            WebElement confirmButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Confirm']")
                    )
            );

            confirmButton.click();

            Thread.sleep(2000);

            // OK
            WebElement okButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='OK']")
                    )
            );

            okButton.click();

            Thread.sleep(2000);

            System.out.println(
                    "Farm Technology updated successfully!"
            );
        }
        
    }
    

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
   


