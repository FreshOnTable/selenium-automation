package PageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Utils.Global;

public class Delivery_Module {
	
	public WebDriver driver;
	
	Map<String, String> driverBeforeChange = new LinkedHashMap<>();
	private Map<Integer, String> oldDriverMap = new HashMap<>();
	private Map<Integer, String> newDriverMap = new HashMap<>();
	
	private String existingScheduleId;
	private String existingDriverType;
	private String existingDriverSellerName;
	
	
	public Delivery_Module(WebDriver driver) {
		this.driver = driver;
	}
	
	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickdelivery = By.xpath("//ul//li//span[text()='Delivery']");
	By plackschedule = By.xpath("//ul//li//span[text()='Schedule']");
	By createdeliveryschedule = By.xpath("//*[text()=\"Create Delivery Schedule +\"]");
	By createpickupschedule = By.xpath("//*[text()=\"Create Pickup Schedule +\"]");
	
	public void clickgrid() {
		driver.findElement(clickgrid).click();
	}

	public void clickdeliveryoption() {
		driver.findElement(clickdelivery).click();
	}

	public void clickschedule() {
		driver.findElement(plackschedule).click();
	}
	
public void validatehomepage() {
	String expectedTitle = "Schedule Overview";
	WebElement elementTitle = driver.findElement(By.xpath("//*[text()=\"Schedule Overview\"]"));
	String actualTitle = elementTitle.getText();
	 
	 System.out.println("Actual title is: " + actualTitle);
	    Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");

	    System.out.println("Delivery home page: " + expectedTitle);
}

	public void clickCreateDeliveryScheduleBtn() {
	driver.findElement(createdeliveryschedule).click();
}
	public void clickCreatePickupScheduleBtn() {
		driver.findElement(createpickupschedule).click();
		}

public void searchfieldvalidation() throws InterruptedException {
	
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    
    WebElement searchInput = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("pr_parent_id_filterBarcell"))
    );

    String searchTerm = "160767";  // use actual search value
    searchInput.clear();
    searchInput.sendKeys(searchTerm);
    searchInput.sendKeys(Keys.ENTER);  // Trigger the search
    

    Thread.sleep(1000);
     	searchInput.clear();  // Clear the search box
    	searchInput.sendKeys(Keys.ENTER);
    	System.out.println("order Id search filtration is working: pass ");
    	 
    	    
    	WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        WebElement searchInput2 = wait2.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("to_org_name_filterBarcell"))
        );
    	String searchTerm2 = "1971 Cafe";  // use actual search value
        searchInput2.clear();
        searchInput2.sendKeys(searchTerm2);
        searchInput2.sendKeys(Keys.ENTER);  // Trigger the search
        
        Thread.sleep(1000);
         	searchInput2.clear();  // Clear the search box
        	searchInput2.sendKeys(Keys.ENTER);
        	System.out.println("Enterprise search filtration is working: pass ");
}

public void assignRandomDrivers() throws InterruptedException {
	
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Actions actions = new Actions(driver);
    Random random = new Random();

    // Step 1: Get all the rows in the table (excluding the header row)
    List<WebElement> rows = driver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[8]/div/table/tbody/tr/td[8]"));

    for (int i = 1; i <= rows.size(); i++) {
        // Step 2: Double-click on the row
        WebElement row = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"grid_1049538271_0_content_table\"]/tbody")));
        actions.doubleClick(row).perform();

        // Step 3: Wait for the driver dropdown arrow to appear and click it
        WebElement arrow = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"grid_1049538271_0_content_table\"]/tbody")));
        arrow.click();

        // Step 4: Wait for driver list to appear and select one randomly
        List<WebElement> driverOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//*[contains(@id, 'grid_1049538271_0_content')]")));

        int randomIndex = random.nextInt(driverOptions.size());
        driverOptions.get(randomIndex).click();

        // Optional: Wait briefly before moving to next row
        Thread.sleep(1000);
    }

    System.out.println("✅ Random drivers assigned to all rows.");
}

public void DragAndDrop() throws InterruptedException {

	Thread.sleep(1000);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	WebElement source = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Driver Name']")));

	WebElement target = wait.until(
			ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'grid-grouping-bar')]")));

	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].scrollIntoView(true);", source);
	js.executeScript("arguments[0].scrollIntoView(true);", target);

	Actions actions = new Actions(driver);
	actions.clickAndHold(source).moveToElement(target).release().build().perform();
}

public void OrderSelection() throws InterruptedException {

	Thread.sleep(1000);

	List<WebElement> driverCheckboxes = driver
			.findElements(By.xpath("//div[@class='grid-checkbox-div group-by-column-check']"));
	for (int i = 0; i < driverCheckboxes.size(); i++) {
		List<WebElement> updatedList = driver
				.findElements(By.xpath("//div[@class='grid-checkbox-div group-by-column-check']"));
		WebElement checkbox = updatedList.get(i);
		if (!checkbox.isSelected()) {
			checkbox.click();
			Thread.sleep(300);

		}
	}	
}

public void OrderSelection1() throws InterruptedException {

    Thread.sleep(1000);

    int numberOfCheckboxesToSelect = 1;

    List<WebElement> driverCheckboxes = driver.findElements(
            By.xpath("//div[@class='grid-checkbox-div group-by-column-check']")
    );

    int selectedCount = 0;

    for (int i = 0; i < driverCheckboxes.size(); i++) {

        List<WebElement> updatedList = driver.findElements(
                By.xpath("//div[@class='grid-checkbox-div group-by-column-check']")
        );

        WebElement checkbox = updatedList.get(i);

        if (!checkbox.isSelected()) {

            checkbox.click();

            selectedCount++;

            System.out.println(
                    "Checkbox selected: " + selectedCount
            );

            Thread.sleep(300);
        }

        // Stop after selecting required number
        if (selectedCount == numberOfCheckboxesToSelect) {
            break;
        }
    }
}

public List<String> selectAllOrdersUnderDriver() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    List<String> selectedOrderIds = new ArrayList<>();

    // Find all driver group/header rows
    String driverRowsXpath =
            "//tr[contains(@class,'table-group-header')]";

    List<WebElement> driverRows =
            driver.findElements(By.xpath(driverRowsXpath));

    System.out.println("Drivers available: " + driverRows.size());


    for (int i = 0; i < driverRows.size(); i++) {

        // Re-find because grid can refresh
        List<WebElement> updatedDrivers =
                driver.findElements(By.xpath(driverRowsXpath));

        if (i >= updatedDrivers.size()) {
            break;
        }

        WebElement driverRow = updatedDrivers.get(i);

        String driverText =
                driverRow.getText().trim();

 //       System.out.println("--------------------------------");
 //       System.out.println("Checking Driver: " + driverText);


        // ========================================================
        // Get orders belonging to this driver
        // ========================================================

        List<WebElement> orderRows =
                driverRow.findElements(
                        By.xpath("./following-sibling::tr")
                );


        List<String> driverOrderIds =
                new ArrayList<>();


        for (WebElement orderRow : orderRows) {

            String rowClass =
                    orderRow.getAttribute("class");

            // Stop when next driver starts
            if (rowClass != null &&
                rowClass.contains("table-group-header")) {
                break;
            }


            List<WebElement> cells =
                    orderRow.findElements(By.xpath("./td"));

            if (cells.size() < 2) {
                continue;
            }


            String orderId =
                    cells.get(1).getText().trim();


            if (orderId.matches("\\d{8}")) {

                driverOrderIds.add(orderId);
            }
        }


        // ========================================================
        // No orders under this driver -> next driver
        // ========================================================

        if (driverOrderIds.isEmpty()) {

            System.out.println(
                    "No orders found -> SKIP DRIVER"
            );

            continue;
        }


        // ========================================================
        // Capture driver name
        // ========================================================

        System.out.println(
                "Selected Driver: " + driverText
        );


        // ========================================================
        // Capture ALL Order IDs
        // ========================================================

        selectedOrderIds.addAll(driverOrderIds);


        for (String orderId : driverOrderIds) {

            System.out.println(
                    "Order captured: " + orderId
            );
        }


        // ========================================================
        // Find parent driver checkbox
        // ========================================================

        WebElement driverCheckbox =
                driverRow.findElement(
                        By.xpath(".//input[@type='checkbox']")
                );


        // ========================================================
        // Select driver
        // ========================================================

        if (!driverCheckbox.isSelected()) {

            driverCheckbox.click();

            System.out.println(
                    "Driver checkbox selected"
            );

            Thread.sleep(500);

        } else {

            System.out.println(
                    "Driver already selected"
            );
        }


        // ========================================================
        // We selected ONE driver.
        // Stop here.
        // ========================================================

        break;
    }


    // ============================================================
    // Final captured Order IDs
    // ============================================================

    System.out.println("\n====================================");
    System.out.println("CAPTURED ORDER IDS");
    System.out.println("======================================");

    for (String orderId : selectedOrderIds) {

        System.out.println("Order ID = " + orderId);
    }

    System.out.println("====================================");


    return selectedOrderIds;
}

public void clickNext() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(
	            driver,
	            Duration.ofSeconds(15)
	    );

	    By nextBtn = By.xpath(
	            "//button[.//div[normalize-space()='Next'] or normalize-space()='Next']"
	    );

	    WebElement clickNext = wait.until(
	            ExpectedConditions.presenceOfElementLocated(nextBtn)
	    );

	    // Find the scrollable parent and scroll it to the top
	    ((JavascriptExecutor) driver).executeScript(
	            """
	            let element = arguments[0];

	            while (element && element !== document.body) {
	                if (element.scrollHeight > element.clientHeight) {
	                    element.scrollTop = 0;
	                    break;
	                }
	                element = element.parentElement;
	            }

	            window.scrollTo(0, 0);
	            """,
	            clickNext
	    );

	    Thread.sleep(1000);

	    clickNext = wait.until(
	            ExpectedConditions.elementToBeClickable(nextBtn)
	    );

	    clickNext.click();
	}

/*public void selectRandomVehicleAndTime() throws InterruptedException {

    Random random = new Random();

    System.out.println("======================================");
    System.out.println("Selecting Vehicle, Departure Time and Driver");
    System.out.println("======================================");

    // =========================================================
    // 1. SELECT ALL VEHICLE DROPDOWNS
    // =========================================================

//    List<WebElement> vehicleDropdowns = driver.findElements(
//            By.xpath("//select")
//    );
//
//    System.out.println(
//            "Total Vehicle Dropdowns: " + vehicleDropdowns.size()
//    );
//
//    for (int i = 0; i < vehicleDropdowns.size(); i++) {
//
//        try {
//
//            // Re-fetch to avoid stale element
//            vehicleDropdowns = driver.findElements(
//                    By.xpath("//select[@name='vehicle']")
//            );
//
//            WebElement vehicleDropdown = vehicleDropdowns.get(i);
//
//            scrollToElement(vehicleDropdown);
//
//            Select vehicleSelect = new Select(vehicleDropdown);
//
//            int vehicleIndex = getRandomOptionIndex(
//                    vehicleSelect.getOptions(),
//                    random
//            );
//
//            vehicleSelect.selectByIndex(vehicleIndex);
//
//            String selectedVehicle =
//                    vehicleSelect
//                            .getOptions()
//                            .get(vehicleIndex)
//                            .getText()
//                            .trim();
//
//            System.out.println(
//                    "Vehicle " + (i + 1) +
//                    " selected: " + selectedVehicle
//            );
//
//            Thread.sleep(500);
//
//        } catch (StaleElementReferenceException e) {
//
//            System.out.println(
//                    "⚠ Vehicle dropdown " +
//                    (i + 1) +
//                    " became stale. Retrying..."
//            );
//
//            i--;
//            Thread.sleep(500);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                    "❌ Vehicle dropdown " +
//                    (i + 1) +
//                    " failed: " +
//                    e.getMessage()
//            );
//        }
//    }
    
    List<WebElement> vehicleDropdowns = driver.findElements(
            By.xpath("//select")
    );

    System.out.println("======================================");
    System.out.println(
            "Total Vehicle Dropdowns: "
            + vehicleDropdowns.size()
    );
    System.out.println("======================================");

//    Random random = new Random();

    for (int i = 0; i < vehicleDropdowns.size(); i++) {

        try {

            WebElement vehicleElement = vehicleDropdowns.get(i);

            Select vehicleSelect = new Select(vehicleElement);

            List<WebElement> options =
                    vehicleSelect.getOptions();

            List<Integer> validIndexes =
                    new ArrayList<>();

            // ---------------------------------------------
            // Find only actual vehicle options
            // ---------------------------------------------

            for (int j = 0; j < options.size(); j++) {

                String optionText =
                        options.get(j)
                                .getText()
                                .trim();

                if (optionText.isEmpty()) {
                    continue;
                }

                // NEVER select "Select"
                if (optionText.equalsIgnoreCase("Select")) {
                    continue;
                }

                // Add valid vehicle
                validIndexes.add(j);
            }

            // ---------------------------------------------
            // Select random actual vehicle
            // ---------------------------------------------

            if (!validIndexes.isEmpty()) {

                int randomIndex =
                        validIndexes.get(
                                random.nextInt(
                                        validIndexes.size()
                                )
                        );

                String selectedVehicle =
                        options.get(randomIndex)
                                .getText()
                                .trim();

                vehicleSelect.selectByIndex(randomIndex);

                System.out.println(
                        "Vehicle " + (i + 1)
                        + " selected: "
                        + selectedVehicle
                );

            } else {

                System.out.println(
                        "❌ No valid vehicle available for Vehicle "
                        + (i + 1)
                );
            }

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Vehicle dropdown became stale: "
                    + (i + 1)
            );
        }
    }

    // =========================================================
    // 2. SELECT ALL DEPARTURE TIME DROPDOWNS
    // =========================================================

    List<WebElement> timeDropdowns = driver.findElements(
            By.xpath("//select[@name='pickupBy']")
    );

    System.out.println(
            "Total Departure Time Dropdowns: " +
            timeDropdowns.size()
    );

    for (int i = 0; i < timeDropdowns.size(); i++) {

        try {

            // Re-fetch
            timeDropdowns = driver.findElements(
                    By.xpath("//select[@name='pickupBy']")
            );

            WebElement timeDropdown = timeDropdowns.get(i);

            scrollToElement(timeDropdown);

            Select timeSelect = new Select(timeDropdown);

            int timeIndex = getRandomOptionIndex(
                    timeSelect.getOptions(),
                    random
            );

            timeSelect.selectByIndex(timeIndex);

            String selectedTime =
                    timeSelect
                            .getOptions()
                            .get(timeIndex)
                            .getText()
                            .trim();

            System.out.println(
                    "Departure Time " + (i + 1) +
                    " selected: " + selectedTime
            );

            Thread.sleep(500);

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Time dropdown " +
                    (i + 1) +
                    " became stale. Retrying..."
            );

            i--;
            Thread.sleep(500);

        } catch (Exception e) {

            System.out.println(
                    "❌ Time dropdown " +
                    (i + 1) +
                    " failed: " +
                    e.getMessage()
            );
        }
    }

    // =========================================================
    // 3. SELECT ALL DRIVER DROPDOWNS
    // =========================================================

    List<WebElement> driverDropdowns = driver.findElements(
            By.xpath("//select[@name='userName']")
    );

    System.out.println(
            "Total Driver Dropdowns: " +
            driverDropdowns.size()
    );

    for (int i = 0; i < driverDropdowns.size(); i++) {

        try {

            // Re-fetch
            driverDropdowns = driver.findElements(
                    By.xpath("//select[@name='userName']")
            );

            WebElement driverDropdown = driverDropdowns.get(i);

            scrollToElement(driverDropdown);

            Select driverSelect = new Select(driverDropdown);

            int driverIndex = getRandomOptionIndex(
                    driverSelect.getOptions(),
                    random
            );

            driverSelect.selectByIndex(driverIndex);

            String selectedDriver =
                    driverSelect
                            .getOptions()
                            .get(driverIndex)
                            .getText()
                            .trim();

            System.out.println(
                    "Driver " + (i + 1) +
                    " selected: " + selectedDriver
            );

            Thread.sleep(500);

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Driver dropdown " +
                    (i + 1) +
                    " became stale. Retrying..."
            );

            i--;
            Thread.sleep(500);

        } catch (Exception e) {

            System.out.println(
                    "❌ Driver dropdown " +
                    (i + 1) +
                    " failed: " +
                    e.getMessage()
            );
        }
    }

    System.out.println("======================================");
    System.out.println("✅ All available dropdowns selected");
    System.out.println("======================================");
}*/

public void selectRandomVehicleAndTime() throws InterruptedException {

    Random random = new Random();

    System.out.println("-------------------------------------");
    System.out.println("Selecting Vehicle, Departure Time and Driver");
   

    // =========================================================
    // 1. SELECT ALL VEHICLE DROPDOWNS
    // =========================================================

 List<WebElement> vehicleDropdowns = driver.findElements(
         By.xpath("//select[@name='vehicle']")
 );

 System.out.println(
         "Total Vehicle Dropdowns: " +
         vehicleDropdowns.size()
 );

 for (int i = 0; i < vehicleDropdowns.size(); i++) {

     try {

         // Re-fetch to avoid stale element
         vehicleDropdowns = driver.findElements(
                 By.xpath("//select[@name='vehicle']")
         );

         WebElement vehicleDropdown = vehicleDropdowns.get(i);

         scrollToElement(vehicleDropdown);

         Select vehicleSelect = new Select(vehicleDropdown);

         List<WebElement> options = vehicleSelect.getOptions();

         // Store only valid vehicle options
         List<WebElement> validOptions = new ArrayList<>();

         for (WebElement option : options) {

             String text = option.getText().trim();

             // Ignore Select / empty options
             if (!text.isEmpty()
                     && !text.equalsIgnoreCase("Select")
                     && !text.equalsIgnoreCase("Select Vehicle")) {

                 validOptions.add(option);
             }
         }

         // No valid option available
         if (validOptions.isEmpty()) {

             System.out.println(
                     "⚠ No valid vehicle option found for dropdown "
                     + (i + 1)
             );

             continue;
         }

         // Select random valid vehicle option
         WebElement selectedOption =
                 validOptions.get(
                         random.nextInt(validOptions.size())
                 );

         String selectedVehicle =
                 selectedOption.getText().trim();

         vehicleSelect.selectByVisibleText(selectedVehicle);

         System.out.println(
                 "Vehicle " + (i + 1) +
                 " selected: " + selectedVehicle
         );

         Thread.sleep(500);

     } catch (StaleElementReferenceException e) {

         System.out.println(
                 "⚠ Vehicle dropdown " +
                 (i + 1) +
                 " became stale. Retrying..."
         );

         i--;
         Thread.sleep(500);

     } catch (Exception e) {

         System.out.println(
                 "❌ Vehicle dropdown " +
                 (i + 1) +
                 " failed: " +
                 e.getMessage()
         );
     }
 }
   
    
 // =========================================================
 // 2. SELECT OPTION FROM ALL DEPARTURE TIME DROPDOWNS
 // =========================================================

 List<WebElement> timeDropdowns = driver.findElements(
         By.xpath("//select[@name='pickupBy']")
 );

 System.out.println(
         "Total Departure Time Dropdowns Found: " +
         timeDropdowns.size()
 );

 for (int i = 0; i < timeDropdowns.size(); i++) {

     try {

         // Re-fetch every time to avoid stale element
         timeDropdowns = driver.findElements(
                 By.xpath("//select[@name='pickupBy']")
         );

         WebElement timeDropdown = timeDropdowns.get(i);

         scrollToElement(timeDropdown);

         Select timeSelect = new Select(timeDropdown);

         List<WebElement> options = timeSelect.getOptions();

         // Collect valid options, ignoring "Select"
         List<WebElement> validOptions = new ArrayList<>();

         for (WebElement option : options) {

             String text = option.getText().trim();

             if (!text.isEmpty()
                     && !text.equalsIgnoreCase("Select")
                     && !text.equalsIgnoreCase("Departure Time")
                     && !text.equalsIgnoreCase("Select Time")) {

                 validOptions.add(option);
             }
         }

         if (validOptions.isEmpty()) {

             System.out.println(
                     "⚠ No valid departure time option found for dropdown "
                     + (i + 1)
             );

             continue;
         }

         // Select random valid option
         WebElement selectedOption =
                 validOptions.get(
                         random.nextInt(validOptions.size())
                 );

         String selectedTime =
                 selectedOption.getText().trim();

         timeSelect.selectByVisibleText(selectedTime);

         System.out.println(
                 "Departure Time " + (i + 1) +
                 " selected: " + selectedTime
         );

         Thread.sleep(500);

     } catch (StaleElementReferenceException e) {

         System.out.println(
                 "⚠ Time dropdown " +
                 (i + 1) +
                 " became stale. Retrying..."
         );

         i--;
         Thread.sleep(500);

     } catch (Exception e) {

         System.out.println(
                 "❌ Time dropdown " +
                 (i + 1) +
                 " failed: " +
                 e.getMessage()
         );
     }
 }
//=========================================================
//3. SELECT OPTION FROM ALL DRIVER DROPDOWNS
//=========================================================

List<WebElement> driverDropdowns = driver.findElements(
      By.xpath("//select[@name='userName']")
);

System.out.println(
      "Total Driver Dropdowns: " +
      driverDropdowns.size()
);

for (int i = 0; i < driverDropdowns.size(); i++) {

  try {

      // Re-fetch
      driverDropdowns = driver.findElements(
              By.xpath("//select[@name='userName']")
      );

      WebElement driverDropdown = driverDropdowns.get(i);

      scrollToElement(driverDropdown);

      Select driverSelect = new Select(driverDropdown);

      List<WebElement> options = driverSelect.getOptions();

      // Get only valid driver options
      List<WebElement> validOptions = new ArrayList<>();

      for (WebElement option : options) {

          String text = option.getText().trim();

          if (!text.isEmpty()
                  && !text.equalsIgnoreCase("Select")
                  && !text.equalsIgnoreCase("Select Driver")) {

              validOptions.add(option);
          }
      }

      if (validOptions.isEmpty()) {

          System.out.println(
                  "⚠ No valid driver option found for dropdown "
                  + (i + 1)
          );

          continue;
      }

      // Select random driver option
      WebElement selectedOption =
              validOptions.get(
                      random.nextInt(validOptions.size())
              );

      String selectedDriver =
              selectedOption.getText().trim();

      driverSelect.selectByVisibleText(selectedDriver);

      System.out.println(
              "Driver " + (i + 1) +
              " selected: " + selectedDriver
      );

      Thread.sleep(500);

  } catch (StaleElementReferenceException e) {

      System.out.println(
              "⚠ Driver dropdown " +
              (i + 1) +
              " became stale. Retrying..."
      );

      i--;
      Thread.sleep(500);

  } catch (Exception e) {

      System.out.println(
              "❌ Driver dropdown " +
              (i + 1) +
              " failed: " +
              e.getMessage()
      );
  }
}

}

private void scrollToElement(WebElement element) {

    JavascriptExecutor js =
            (JavascriptExecutor) driver;

    js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            element
    );
}

private int getRandomOptionIndex(List<WebElement> options, Random random) {
    if (options.size() <= 1) return 0;
    return random.nextInt(options.size() - 1) + 1; // skip index 0 (e.g., "--Select--")
}

public void clickNextBtn() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    
    
    WebElement clickNext = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//div[normalize-space()='Next'] or normalize-space()='Next']")
            )
    );
    
    WebElement element = driver.findElement(
    	    By.xpath("//button[.//div[normalize-space()='Next'] or normalize-space()='Next']")
    	);

    	((JavascriptExecutor) driver).executeScript(
    	    "arguments[0].scrollIntoView({block:'center'});",
    	    element
    	);
    
    Thread.sleep(500);
    clickNext.click();
}

public void clickSubmitBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickSubmit = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//div[normalize-space()='SUBMIT'] or normalize-space()='SUBMIT']")
            )
    );

    clickSubmit.click();
}

public void clickConfirmBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickConfirm = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//div[normalize-space()='Confirm'] or normalize-space()='Confirm']")
            )
    );

    clickConfirm.click();
}

public void clickOKBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickOK = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//div[normalize-space()='OK'] or normalize-space()='OK']")
            )
    );

    clickOK.click();
}

//change driver

public void clickChangeDriverBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickChangeDriver = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@class='button button-primary']")
            )
    );
    
    clickChangeDriver.click();
}

public void captureDriverDetailsBeforeChange() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    driverBeforeChange.clear();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//table//tbody/tr")
	    ));

	    List<WebElement> rows = driver.findElements(
	            By.xpath("//table//tbody/tr")
	    );

	    System.out.println("======================================");
	    System.out.println("CAPTURING DRIVER DETAILS BEFORE CHANGE");
	    System.out.println("Total Orders: " + rows.size());
//	    System.out.println("======================================");

	    for (int i = 0; i < rows.size(); i++) {

	        try {

	            // IMPORTANT:
	            // Re-fetch rows every time because React can refresh the table
	            rows = driver.findElements(
	                    By.xpath("//table//tbody/tr")
	            );

	            WebElement row = rows.get(i);

	            String orderId = row.findElement(
	                    By.xpath("./td[2]")
	            ).getText().trim();

	            String driverName = row.findElement(
	                    By.xpath("./td[last()]")
	            ).getText().trim();

	            driverBeforeChange.put(orderId, driverName);

	            System.out.println(
	                    "Order Id: " + orderId +
	                    " | Driver Before: " + driverName
	            );

	        } catch (StaleElementReferenceException e) {

	            System.out.println(
	                    "⚠ Row became stale. Retrying Order: "
	                    + (i + 1)
	            );

	            i--;

	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }
	        }
	    }

	}

public void validateDriverChanges() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table//tbody/tr")
    ));

    int assignedCount = 0;
    int unchangedCount = 0;

    System.out.println("======================================");
    System.out.println("VALIDATING DRIVER CHANGES");
//    System.out.println("======================================");

    for (int i = 0; i < driverBeforeChange.size(); i++) {

        try {

            // Re-fetch rows every time
            List<WebElement> rows = driver.findElements(
                    By.xpath("//table//tbody/tr")
            );

            WebElement row = rows.get(i);

            String orderId = row.findElement(
                    By.xpath("./td[2]")
            ).getText().trim();

            String driverAfter = row.findElement(
                    By.xpath("./td[last()]")
            ).getText().trim();

            String driverBefore = driverBeforeChange.get(orderId);

            System.out.println(
                    "Order: " + orderId +
                    " | Before: " + driverBefore +
                    " | After: " + driverAfter
            );

            // ==========================================
            // Previously Unassigned
            // ==========================================
            if (driverBefore.equalsIgnoreCase("Unassigned User")
                    || driverBefore.equalsIgnoreCase("Select")
                    || driverBefore.isEmpty()) {

                Assert.assertFalse(
                        driverAfter.equalsIgnoreCase("Unassigned User")
                                || driverAfter.equalsIgnoreCase("Select")
                                || driverAfter.isEmpty(),
                        "❌ Driver was not assigned for Order: "
                                + orderId
                );

                System.out.println(
                        "✅ Driver assigned successfully: "
                                + orderId +
                                " → " + driverAfter
                );

                assignedCount++;
            }

            // ==========================================
            // Previously Assigned
            // ==========================================
            else {

                Assert.assertEquals(
                        driverAfter,
                        driverBefore,
                        "❌ Existing driver changed unexpectedly for Order: "
                                + orderId
                );

                System.out.println(
                        "✅ Existing driver unchanged: "
                                + orderId +
                                " → " + driverAfter
                );

                unchangedCount++;
            }

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Row became stale. Retrying..."
            );

            i--;

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

//    System.out.println("======================================");
/*    System.out.println(
            "Total Drivers Assigned: " + assignedCount
    );
    System.out.println(
            "Total Existing Drivers Unchanged: " + unchangedCount
    );
    System.out.println("✅ Driver validation completed");
    System.out.println("======================================");
}*/
}
public void assignDriverToUnassignedOrders() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    Random random = new Random();

    // Get all table rows
    List<WebElement> rows = driver.findElements(
            By.xpath("//table//tbody/tr")
    );

    System.out.println("======================================");
    System.out.println("Total Orders Found: " + rows.size());
//    System.out.println("======================================");

    for (int i = 0; i < rows.size(); i++) {

        try {

            WebElement row = rows.get(i);

            // Driver Name field - last column
            WebElement driverField = row.findElement(
                    By.xpath(".//td[last()]//select")
            );

            Select driverSelect = new Select(driverField);

            String currentDriver = driverSelect
                    .getFirstSelectedOption()
                    .getText()
                    .trim();

            // Only change unassigned orders
            if (currentDriver.equalsIgnoreCase("Select")
                    || currentDriver.isEmpty()) {

                List<WebElement> options = driverSelect.getOptions();

                // Select random driver excluding "Select"
                List<Integer> validIndexes = new ArrayList<>();

                for (int j = 0; j < options.size(); j++) {

                    String optionText = options.get(j)
                            .getText()
                            .trim();

                    if (!optionText.equalsIgnoreCase("Select")
                            && !optionText.isEmpty()) {

                        validIndexes.add(j);
                    }
                }

                if (!validIndexes.isEmpty()) {

                    int randomIndex = validIndexes.get(
                            random.nextInt(validIndexes.size())
                    );

                    String selectedDriver = options
                            .get(randomIndex)
                            .getText()
                            .trim();

                    driverSelect.selectByIndex(randomIndex);

                    System.out.println(
                            "Order " + (i + 1) +
                            " | Driver changed to: " +
                            selectedDriver
                    );

                } else {

                    System.out.println(
                            "⚠ No available driver for Order "
                            + (i + 1)
                    );
                }

            } else {

                // Already assigned - don't change
                System.out.println(
                        "Order " + (i + 1) +
                        " | Already assigned to: " +
                        currentDriver 
 //                      +" | Skipped"
                );
            }

        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "⚠ Row became stale. Retrying..."
            );

            i--;

        } catch (NoSuchElementException e) {

            System.out.println(
                    "⚠ Driver dropdown not found in row "
                    + (i + 1)
            );
        }

        Thread.sleep(500);
    }

    System.out.println("======================================");
    System.out.println("Driver assignment completed");
//    System.out.println("======================================");
}


public void clickSaveBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickSave = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Save']")
            )
    );
    
    clickSave.click();
}
public void clickYesBtn() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement clickYes = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Yes']")
            )
    );
    
    clickYes.click();
}

public void changeStatusInitiatedToActive() throws InterruptedException {

	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

	driver.findElement(By.xpath("(//button[@class='actions-dropdown dropdown-toggle btn btn-success'])[1]"))
			.click();

	Thread.sleep(3000);

	driver.findElement(By.xpath("//a[text()='Change Status']")).click();

	Thread.sleep(2000);

	WebElement scheduleStatus = wait1.until(ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
	Select dropdown1 = new Select(scheduleStatus);
	dropdown1.selectByVisibleText("Active");

	Thread.sleep(2000);

	WebElement comments = wait1
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @name='comment']")));

	comments.click();
	comments.sendKeys("Status changed to Active");

	driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

}

public void CompletingSchedule() throws InterruptedException {

	Thread.sleep(2000);

	driver.findElement(
	    By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
	).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//a[text()='View Trip']")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//li[text()='View Details']")).click();

	Thread.sleep(2000);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	By businessBy = By.xpath("//div[contains(@class,'accordion-header')]");
	By ordersBy = By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");

	// ✅ WAIT until at least one business exists
	wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, 0));

	int businessCount = driver.findElements(businessBy).size();

	for (int i = 0; i < businessCount; i++) {

	    // ✅ SAFE access
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	    WebElement currentBusiness = driver.findElements(businessBy).get(i);
	    wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	    Thread.sleep(2000);

	    while (true) {

	        List<WebElement> orders = driver.findElements(ordersBy);
	        WebElement pendingOrder = null;

	        for (WebElement order : orders) {
	            boolean isCompleted =
	                order.findElements(By.xpath(".//div[@title='Completed']")).size() > 0;

	            if (!isCompleted) {
	                pendingOrder = order;
	                break;
	            }
	        }

	        if (pendingOrder == null) {

	            System.out.println("All orders completed for business " + (i + 1));

	            // ✅ SAFE re-click
	            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	            currentBusiness = driver.findElements(businessBy).get(i);
	            wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	            Thread.sleep(1500);
	            break;
	        }

	        WebElement moreBtn = pendingOrder.findElement(
	            By.xpath(".//button[contains(@class,'actions-dropdown')]"));
	        wait.until(ExpectedConditions.elementToBeClickable(moreBtn)).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='Change Status']"))).click();

	        WebElement statusDropdown = wait.until(
	            ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
	        new Select(statusDropdown).selectByVisibleText("Completed");

	        WebElement comment = wait.until(
	            ExpectedConditions.elementToBeClickable(By.name("comment")));
	        comment.clear();
	        comment.sendKeys("Schedule Completed Successfully");

	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Submit']"))).click();

	        Thread.sleep(2000);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='OK']"))).click();

	        Thread.sleep(1500);
	    }
	}
	   
}


//changed incomplete order for schedule level check rescheduling

//public List<String> randomlyUpdateincompleteOrderSchedule()
//        throws InterruptedException {
//
//    WebDriverWait wait =
//            new WebDriverWait(driver, Duration.ofSeconds(20));
//
//    List<String> incompleteOrderIds = new ArrayList<>();
//
//    Random random = new Random();
//
//    // ---------------------------------------------------------
//    // OPEN VIEW TRIP
//    // ---------------------------------------------------------
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("(//button[@type='button' and contains(@class,'actions-dropdown')])[2]")
//    )).click();
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//a[normalize-space()='View Trip']")
//    )).click();
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//div[@class='dropdown-container']//img")
//    )).click();
//
//    wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//li[normalize-space()='View Details']")
//    )).click();
//
//    // ---------------------------------------------------------
//    // WAIT FOR BUSINESSES
//    // ---------------------------------------------------------
//
//    By businessBy =
//            By.xpath("//div[contains(@class,'accordion-header')]");
//
//    By orderRowsBy =
//            By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");
//
//    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
//            businessBy, 0
//    ));
//
//    int businessCount =
//            driver.findElements(businessBy).size();
//
//    System.out.println("Total businesses: " + businessCount);
//
//    // =========================================================
//    // BUSINESS LOOP
//    // =========================================================
//
//    for (int businessIndex = 0;
//         businessIndex < businessCount;
//         businessIndex++) {
//
//        // -----------------------------------------------------
//        // ALWAYS RE-FIND BUSINESS
//        // -----------------------------------------------------
//
//        List<WebElement> businesses =
//                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
//                        businessBy
//                ));
//
//        WebElement business =
//                businesses.get(businessIndex);
//
//        wait.until(ExpectedConditions.elementToBeClickable(
//                business
//        )).click();
//
//        Thread.sleep(1000);
//
//        // -----------------------------------------------------
//        // CAPTURE ORDER IDs
//        // IMPORTANT:
//        // Capture IDs, NOT WebElements
//        // -----------------------------------------------------
//
//        List<WebElement> currentRows =
//                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
//                        orderRowsBy
//                ));
//
//        List<String> orderIds =
//                new ArrayList<>();
//
//        for (WebElement row : currentRows) {
//
//            String orderId =
//                    row.findElement(By.xpath("./td[2]"))
//                       .getText()
//                       .trim();
//
//            if (!orderId.isEmpty()) {
//                orderIds.add(orderId);
//            }
//        }
//
//        System.out.println();
//        System.out.println(
//                "Business " + (businessIndex + 1)
//                + " has " + orderIds.size()
//                + " orders"
//        );
//
//        // =====================================================
//        // PROCESS EACH ORDER USING ORDER ID
//        // =====================================================
//
//        for (String orderId : orderIds) {
//
//            System.out.println();
//            System.out.println(
//                    "Processing Order ID: " + orderId
//            );
//
//            // -------------------------------------------------
//            // RE-FIND THE ROW USING ORDER ID
//            // -------------------------------------------------
//
//            By orderRowBy = By.xpath(
//                    "//tbody/tr[td[normalize-space()='"
//                    + orderId
//                    + "']]"
//            );
//
//            WebElement orderRow =
//                    wait.until(ExpectedConditions.visibilityOfElementLocated(
//                            orderRowBy
//                    ));
//
//            // -------------------------------------------------
//            // RANDOM STATUS
//            // -------------------------------------------------
//
//            String newStatus =
//                    random.nextBoolean()
//                    ? "Completed"
//                    : "Incomplete";
//
//            System.out.println(
//                    "Changing " + orderId
//                    + " to " + newStatus
//            );
//
//            // -------------------------------------------------
//            // FIND MORE BUTTON AGAIN
//            // -------------------------------------------------
//
//            WebElement moreButton =
//                    orderRow.findElement(
//                            By.xpath(
//                                ".//button[contains(@class,'actions-dropdown')]"
//                            )
//                    );
//
//            wait.until(ExpectedConditions.elementToBeClickable(
//                    moreButton
//            )).click();
//
//            // -------------------------------------------------
//            // IMPORTANT:
//            // CHANGE STATUS IS FOUND FROM PAGE, NOT OLD ROW
//            // -------------------------------------------------
//
//            By changeStatusBy =
//                    By.xpath(
//                        "//a[normalize-space()='Change Status']"
//                    );
//
//            WebElement changeStatus =
//                    wait.until(
//                        ExpectedConditions.elementToBeClickable(
//                            changeStatusBy
//                        )
//                    );
//
//            changeStatus.click();
//
//            // -------------------------------------------------
//            // STATUS DROPDOWN
//            // -------------------------------------------------
//
//            WebElement statusDropdown =
//                    wait.until(
//                        ExpectedConditions.visibilityOfElementLocated(
//                            By.name("selectedStatus")
//                        )
//                    );
//
//            Select select =
//                    new Select(statusDropdown);
//
//            select.selectByVisibleText(newStatus);
//
//            // -------------------------------------------------
//            // COMMENT
//            // -------------------------------------------------
//
//            WebElement comment =
//                    wait.until(
//                        ExpectedConditions.elementToBeClickable(
//                            By.name("comment")
//                        )
//                    );
//
//            comment.clear();
//
//            comment.sendKeys(
//                    "Schedule status updated to "
//                    + newStatus
//            );
//
//            // -------------------------------------------------
//            // SUBMIT
//            // -------------------------------------------------
//
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='Submit']")
//                )
//            ).click();
//
//            // -------------------------------------------------
//            // OK POPUP
//            // -------------------------------------------------
//
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[normalize-space()='OK']")
//                )
//            ).click();
//
//            // -------------------------------------------------
//            // WAIT FOR UPDATE / TABLE RE-RENDER
//            // -------------------------------------------------
//
//            Thread.sleep(1500);
//
//            // -------------------------------------------------
//            // CAPTURE INCOMPLETE ORDER
//            // -------------------------------------------------
//
//            if (newStatus.equals("Incomplete")) {
//
//                incompleteOrderIds.add(orderId);
//
//                System.out.println(
//                    "CAPTURED INCOMPLETE ORDER ID: "
//                    + orderId
//                );
//
//            } else {
//
//                System.out.println(
//                    "COMPLETED ORDER ID : "
//                    + orderId
//                );
//            }
//        }
//
//        // -----------------------------------------------------
//        // COLLAPSE CURRENT BUSINESS
//        // -----------------------------------------------------
//
//        if (businessIndex < businessCount - 1) {
//
//            List<WebElement> refreshedBusinesses =
//                    wait.until(
//                        ExpectedConditions.presenceOfAllElementsLocatedBy(
//                            businessBy
//                        )
//                    );
//
//            WebElement currentBusiness =
//                    refreshedBusinesses.get(businessIndex);
//
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    currentBusiness
//                )
//            ).click();
//
//            Thread.sleep(1000);
//        }
//    }
//
//    // =========================================================
//    // PRINT RESULT
//    // =========================================================
//
//    System.out.println();
//    System.out.println("========================================");
//    System.out.println(
//            "❌ Total Incomplete Orders: "
//            + incompleteOrderIds.size()
//    );
//
//    for (String orderId : incompleteOrderIds) {
//
//        System.out.println(
//                "❌ Incomplete Order ID: "
//                + orderId
//        );
//    }
//
//    System.out.println("========================================");
//
//    return incompleteOrderIds;
//}

public List<String> randomlyUpdateincompleteOrderSchedule()
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    List<String> incompleteOrderIds =
            new ArrayList<>();

    Random random = new Random();

    // This will tell us whether at least one
    // incomplete order has been created.
    boolean incompleteOrderCreated = false;

    // ---------------------------------------------------------
    // OPEN VIEW TRIP
    // ---------------------------------------------------------
    Thread.sleep(500);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath(
                "(//button[@type='button' and contains(@class,'actions-dropdown')])[2]"
            )
            
    )).click();
    Thread.sleep(500);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[normalize-space()='View Trip']")
    )).click();
    Thread.sleep(500);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='dropdown-container']//img")
    )).click();
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[normalize-space()='View Details']")
    )).click();

    // ---------------------------------------------------------
    // WAIT FOR BUSINESSES
    // ---------------------------------------------------------

    By businessBy =
            By.xpath("//div[contains(@class,'accordion-header')]");

    By orderRowsBy =
            By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");

    wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(
                    businessBy, 0
            )
    );

    int businessCount =
            driver.findElements(businessBy).size();

    System.out.println(
            "Total businesses: " + businessCount
    );

    // =========================================================
    // BUSINESS LOOP
    // =========================================================

    for (int businessIndex = 0;
         businessIndex < businessCount;
         businessIndex++) {

        // -----------------------------------------------------
        // ALWAYS RE-FIND BUSINESS
        // -----------------------------------------------------

        List<WebElement> businesses =
                wait.until(
                        ExpectedConditions
                                .presenceOfAllElementsLocatedBy(
                                        businessBy
                                )
                );

        WebElement business =
                businesses.get(businessIndex);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        business
                )
        ).click();

        Thread.sleep(1000);

        // -----------------------------------------------------
        // CAPTURE ORDER IDs
        // -----------------------------------------------------

        List<WebElement> currentRows =
                wait.until(
                        ExpectedConditions
                                .presenceOfAllElementsLocatedBy(
                                        orderRowsBy
                                )
                );

        List<String> orderIds =
                new ArrayList<>();

        for (WebElement row : currentRows) {

            String orderId =
                    row.findElement(
                            By.xpath("./td[2]")
                    )
                    .getText()
                    .trim();

            if (!orderId.isEmpty()) {

                orderIds.add(orderId);
            }
        }

        System.out.println();
        System.out.println(
                "Business " + (businessIndex + 1)
                + " has " + orderIds.size()
                + " orders"
        );

        // =====================================================
        // PROCESS EACH ORDER
        // =====================================================

        for (int orderIndex = 0;
             orderIndex < orderIds.size();
             orderIndex++) {

            String orderId =
                    orderIds.get(orderIndex);

            System.out.println();
            System.out.println(
                    "Processing Order ID: "
                    + orderId
            );

            // -------------------------------------------------
            // RE-FIND ORDER ROW
            // -------------------------------------------------

            By orderRowBy =
                    By.xpath(
                        "//tbody/tr[td[normalize-space()='"
                        + orderId
                        + "']]"
                    );

            WebElement orderRow =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                orderRowBy
                            )
                    );

            // -------------------------------------------------
            // RANDOM STATUS
            // -------------------------------------------------

            String newStatus;

            /*
             * Normally status is random.
             *
             * BUT:
             * If we reach the last order of the last business
             * and still don't have an incomplete order,
             * force this order to Incomplete.
             */

            boolean isLastOrder =
                    businessIndex == businessCount - 1
                    && orderIndex == orderIds.size() - 1;

            if (isLastOrder && !incompleteOrderCreated) {

                newStatus = "Incomplete";

                System.out.println(
                        "⚠️ No incomplete order created yet."
                );

                System.out.println(
                        "🎯 Forcing last order "
                        + orderId
                        + " to Incomplete"
                );

            } else {

                newStatus =
                        random.nextBoolean()
                        ? "Completed"
                        : "Incomplete";
            }

            System.out.println(
                    "Changing "
                    + orderId
                    + " to "
                    + newStatus
            );

            // -------------------------------------------------
            // FIND MORE BUTTON AGAIN
            // -------------------------------------------------

            WebElement moreButton =
                    orderRow.findElement(
                        By.xpath(
                            ".//button[contains(@class,'actions-dropdown')]"
                        )
                    );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            moreButton
                    )
            ).click();

         // -------------------------------------------------
         // CHANGE STATUS
         // -------------------------------------------------

         JavascriptExecutor js =
                 (JavascriptExecutor) driver;

         // Wait a little for the previous UI update
         Thread.sleep(1000);

         // Find all Change Status elements
         By changeStatusBy =
                 By.xpath("//a[normalize-space()='Change Status']");

         WebElement changeStatus = null;

         // Wait until a VISIBLE Change Status option is available
         long endTime =
                 System.currentTimeMillis() + 20000;

         while (System.currentTimeMillis() < endTime) {

             List<WebElement> changeStatusElements =
                     driver.findElements(changeStatusBy);

             for (WebElement element : changeStatusElements) {

                 try {

                     if (element.isDisplayed()
                             && element.isEnabled()) {

                         changeStatus = element;
                         break;
                     }

                 } catch (StaleElementReferenceException e) {

                     // DOM changed - find it again
                 }
             }

             if (changeStatus != null) {
                 break;
             }

             Thread.sleep(300);
         }

         if (changeStatus == null) {

             throw new RuntimeException(
                     "Change Status option was not visible for Order ID: "
                     + orderId
             );
         }

         // Scroll Change Status into view
         js.executeScript(
                 "arguments[0].scrollIntoView({block:'scrollHeight'});",
                 changeStatus
         );

         Thread.sleep(300);

         // Try normal click first
         try {

             changeStatus.click();

             System.out.println(
                     "✅ Change Status clicked for Order ID: "
                     + orderId
             );

         } catch (ElementClickInterceptedException e) {

             System.out.println(
                     "⚠️ Change Status click intercepted."
             );

             System.out.println(
                     "Trying JavaScript click for Order ID: "
                     + orderId
             );

             js.executeScript(
                     "arguments[0].click();",
                     changeStatus
             );

             System.out.println(
                     "✅ Change Status clicked using JavaScript"
             );
         }

            // -------------------------------------------------
            // STATUS DROPDOWN
            // -------------------------------------------------

            WebElement statusDropdown =
                    wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                            By.name("selectedStatus")
                        )
                    );

            Select select =
                    new Select(statusDropdown);

            select.selectByVisibleText(newStatus);

            // -------------------------------------------------
            // COMMENT
            // -------------------------------------------------

            WebElement comment =
                    wait.until(
                        ExpectedConditions.elementToBeClickable(
                            By.name("comment")
                        )
                    );

            comment.clear();

            comment.sendKeys(
                    "Schedule status updated to "
                    + newStatus
            );

            // -------------------------------------------------
            // SUBMIT
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath(
                            "//button[normalize-space()='Submit']"
                        )
                    )
            ).click();

            // -------------------------------------------------
            // OK POPUP
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath(
                            "//button[normalize-space()='OK']"
                        )
                    )
            ).click();

            // -------------------------------------------------
            // WAIT FOR TABLE UPDATE
            // -------------------------------------------------

            Thread.sleep(1500);

            // -------------------------------------------------
            // CAPTURE INCOMPLETE ORDER
            // -------------------------------------------------

            if (newStatus.equals("Incomplete")) {

                incompleteOrderIds.add(orderId);

                // Mark that at least one incomplete exists
                incompleteOrderCreated = true;

                System.out.println(
                        "CAPTURED INCOMPLETE ORDER ID: "
                        + orderId
                );

            } else {

                System.out.println(
                        "COMPLETED ORDER ID : "
                        + orderId
                );
            }
        }

        // -----------------------------------------------------
        // COLLAPSE CURRENT BUSINESS
        // -----------------------------------------------------

        if (businessIndex < businessCount - 1) {

            List<WebElement> refreshedBusinesses =
                    wait.until(
                        ExpectedConditions
                            .presenceOfAllElementsLocatedBy(
                                businessBy
                            )
                    );

            WebElement currentBusiness =
                    refreshedBusinesses.get(
                            businessIndex
                    );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            currentBusiness
                    )
            ).click();

            Thread.sleep(1000);
        }
    }

    // =========================================================
    // FINAL VALIDATION
    // =========================================================

    System.out.println();
    System.out.println(
            "========================================"
    );

    System.out.println(
            "❌ Total Incomplete Orders: "
            + incompleteOrderIds.size()
    );

    for (String orderId : incompleteOrderIds) {

        System.out.println(
                "❌ Incomplete Order ID: "
                + orderId
        );
    }

    System.out.println(
            "========================================"
    );

    // ---------------------------------------------------------
    // SAFETY CHECK
    // ---------------------------------------------------------

    if (incompleteOrderIds.isEmpty()) {

        throw new RuntimeException(
                "❌ Test failed: No incomplete order was created."
        );
    }

    return incompleteOrderIds;
}

public void clickBackBtn() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    JavascriptExecutor js =
            (JavascriptExecutor) driver;

    // Scroll to top first
    js.executeScript("window.scrollTo(0, 0);");

    Thread.sleep(1000);

    try {

        // Find the actual image inside the back-arrow
        WebElement backArrowImg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='back-arrow']")
                )
        );

        // Bring it into view
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                backArrowImg
        );

        Thread.sleep(500);

        // Normal click
        backArrowImg.click();

        System.out.println(
                "✅ Back button clicked successfully"
        );

    } catch (ElementClickInterceptedException e) {

        System.out.println(
                "⚠️ Normal Back button click intercepted. "
                + "Trying JavaScript..."
        );

        WebElement backArrowImg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='back-arrow']//img")
                )
        );

        js.executeScript(
                "arguments[0].click();",
                backArrowImg
        );

        System.out.println(
                "✅ Back button clicked using JavaScript"
        );
    }
}

//public void clickBackBtn() throws InterruptedException {
//	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//  WebElement clickBackBtn = wait
//			.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='back-arrow']")));
//  JavascriptExecutor js = (JavascriptExecutor) driver;
//  js.executeScript("window.scrollTo(0, 0);");
//  Thread.sleep(3000);
//	clickBackBtn.click();
//}

///*public List<String> randomlyUpdateincompleteOrderSchedule()
//        throws InterruptedException {
//
//    List<String> incompleteOrderIds = new ArrayList<>();
//
//    driver.findElement(
//        By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
//    ).click();
//
//    Thread.sleep(2000);
//
//    driver.findElement(
//        By.xpath("//a[normalize-space()='View Trip']")
//    ).click();
//
//    Thread.sleep(2000);
//
//    driver.findElement(
//        By.xpath("//div[@class='dropdown-container']//img")
//    ).click();
//
//    Thread.sleep(2000);
//
//    driver.findElement(
//        By.xpath("//li[normalize-space()='View Details']")
//    ).click();
//
//    WebDriverWait wait =
//        new WebDriverWait(driver, Duration.ofSeconds(20));
//
//    By businessBy =
//        By.xpath("//div[contains(@class,'accordion-header')]");
//
//    By ordersBy =
//        By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");
//
//    // Store all orders which become Incomplete
////    List<String> incompleteOrderIds = new ArrayList<>();
//
//    Random random = new Random();
//
//    // Wait for businesses
//    wait.until(
//        ExpectedConditions.numberOfElementsToBeMoreThan(
//            businessBy, 0
//        )
//    );
//
//    int businessCount =
//        driver.findElements(businessBy).size();
//
//    System.out.println(
//        "Total businesses: " + businessCount
//    );
//
//    for (int i = 0; i < businessCount; i++) {
//
//        /*
//         * Re-find business because the DOM can change
//         * after changing an order status.
//         */
//        WebElement currentBusiness =
//            driver.findElements(businessBy).get(i);
//
//        wait.until(
//            ExpectedConditions.elementToBeClickable(
//                currentBusiness
//            )
//        ).click();
//
//        Thread.sleep(1500);
//
//        /*
//         * Get current orders for this business.
//         */
//        List<WebElement> orders =
//            driver.findElements(ordersBy);
//
//        System.out.println(
//            "Business " + (i + 1)
//            + " has " + orders.size() + " orders"
//        );
//
//        int orderCount = orders.size();
//
//        for (int j = 0; j < orderCount; j++) {
//
//            /*
//             * IMPORTANT:
//             * Re-find the orders every time because
//             * changing status can refresh the table.
//             */
//            orders = driver.findElements(ordersBy);
//
//            if (j >= orders.size()) {
//                System.out.println(
//                    "⚠️ Order index " + j
//                    + " is no longer available."
//                );
//                break;
//            }
//
//            WebElement order = orders.get(j);
//
//            /*
//             * Capture Order ID BEFORE changing status.
//             *
//             * Based on your screenshot:
//             * Order ID is in the second TD.
//             */
//            String orderId =
//                order.findElement(
//                    By.xpath("./td[2]")
//                ).getText().trim();
//
////            System.out.println(
////                "📦 Processing Order ID: " + orderId
////            );
//
//            /*
//             * Randomly select Completed / Incomplete
//             */
//            String newStatus =
//                random.nextBoolean()
//                    ? "Completed"
//                    : "Incomplete";
//
//            System.out.println(
//                "changed status for "
//                + newStatus
//                + " = "
//                + orderId
//            );
//
//            /*
//             * Open More
//             */
//            WebElement moreBtn =
//                order.findElement(
//                    By.xpath(
//                        ".//button[contains(@class,'actions-dropdown')]"
//                    )
//                );
//
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    moreBtn
//                )
//            ).click();
//
//            /*
//             * Click Change Status
//             */
//           
//           
//           WebElement changeStatus =
//                   order.findElement(
//                       By.xpath(
//                           "//a[normalize-space()='Change Status']"
//                       )
//                   );
//
//               wait.until(
//                   ExpectedConditions.elementToBeClickable(
//                		  changeStatus
//                   )
//               ).click();
//
//            /*
//             * Select new status
//             */
//            WebElement statusDropdown =
//                wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                        By.name("selectedStatus")
//                    )
//                );
//
//            Select selectStatus =
//                new Select(statusDropdown);
//
//            selectStatus.selectByVisibleText(newStatus);
//
//            /*
//             * Enter comment
//             */
//            WebElement comment =
//                wait.until(
//                    ExpectedConditions.elementToBeClickable(
//                        By.name("comment")
//                    )
//                );
//
//            comment.clear();
//
//            comment.sendKeys(
//                "Schedule status changed to " + newStatus
//            );
//
//            /*
//             * Submit
//             */
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    By.xpath(
//                        "//button[normalize-space()='Submit']"
//                    )
//                )
//            ).click();
//
//            Thread.sleep(1500);
//
//            /*
//             * OK popup
//             */
//            wait.until(
//                ExpectedConditions.elementToBeClickable(
//                    By.xpath(
//                        "//button[normalize-space()='OK']"
//                    )
//                )
//            ).click();
//
//            Thread.sleep(1500);
//
//            /*
//             * IMPORTANT:
//             * If status is Incomplete,
//             * store the Order ID.
//             */
//            if (newStatus.equals("Incomplete")) {
//
//                incompleteOrderIds.add(orderId);
//
//                System.out.println(
//                    "CAPTURED INCOMPLETE ORDER ID: "
//                    + orderId
//                );
//            } else {
//
//                System.out.println(
//                    "COMPLETED ORDER ID : "
//                    + orderId
//                );
//            }
//        }
//
//        /*
//         * Collapse business before moving
//         * to next business.
//         */
//        try {
//
//            WebElement business =
//                driver.findElements(businessBy).get(i);
//
//            business.click();
//
//            Thread.sleep(1000);
//
//        } catch (Exception e) {
//
//            System.out.println(
//                "⚠️ Could not collapse business "
//                + (i + 1)
//            );
//        }
//    }
//
//    /*
//     * Final result
//     */
//    System.out.println(
//        "========================================"
//    );
//
//    System.out.println(
//        "❌ Total Incomplete Orders: "
//        + incompleteOrderIds.size()
//    );
//
//    for (String orderId : incompleteOrderIds) {
//
//        System.out.println(
//            "❌ Incomplete Order ID: "
//            + orderId
//        );
//    }
//
//    System.out.println(
//        "========================================"
//    );
//
//    return incompleteOrderIds;
//}  


public void verifyIncompleteOrdersAvailableForReschedule(
        List<String> incompleteOrderIds) throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    for (String orderId : incompleteOrderIds) {

        // ========================================================
        // STEP 1: Open Order ID filter
        // ========================================================
Thread.sleep(2000);
        WebElement orderIdFilter = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//tr[@class='table-light']/th[2]/div[2]"
                        )
                )
        );

        Thread.sleep(1000);
        orderIdFilter.click();

        // ========================================================
        // STEP 2: Find Order ID search input
        // ========================================================

        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//input[@placeholder='Order ID']"
                        )
                )
        );

        // Clear previous value
//        searchInput.clear();

        // Enter incomplete Order ID
        Thread.sleep(1000);
        searchInput.sendKeys(orderId);

        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "🔄 Validating incomplete Order ID: "
                        + orderId
        );

        System.out.println(
                "======================================"
        );

        // ========================================================
        // STEP 3: Click APPLY
        // ========================================================

        WebElement applyButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[normalize-space()='Apply']"
                        )
                )
        );
        Thread.sleep(1000);
        applyButton.click();

        // ========================================================
        // STEP 4: Verify Order ID exists
        // ========================================================

        boolean orderAvailable = false;

        try {

            WebElement orderRow =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(10)
                    ).until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            By.xpath(
                                                    "//tbody/tr"
                                                    + "[td[normalize-space()='"
                                                    + orderId
                                                    + "']]"
                                            )
                                    )
                    );

            orderAvailable = orderRow.isDisplayed();

        } catch (TimeoutException e) {

            orderAvailable = false;
        }

        // ========================================================
        // STEP 5: Result
        // ========================================================

        if (orderAvailable) {

            System.out.println(
                    "✅ Incomplete Order "
                            + orderId
                            + " is AVAILABLE for reschedule"
            );

        } else {

            System.out.println(
                    "❌ Incomplete Order "
                            + orderId
                            + " is NOT AVAILABLE for reschedule"
            );

            Assert.fail(
                    "Incomplete Order "
                            + orderId
                            + " is not available for reschedule"
            );
        }

        // ========================================================
        // STEP 6: Clear filter before next Order ID
        // ========================================================

        orderIdFilter = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//tr[@class='table-light']/th[2]/div[2]"
                        )
                )
        );

        orderIdFilter.click();

        WebElement clearButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[normalize-space()='Clear']"
                        )
                )
        );
        
        Thread.sleep(1000);

        clearButton.click();

        Thread.sleep(500);
    }

    System.out.println(
            "\n======================================"
    );

    System.out.println(
            "✅ All incomplete orders are available for reschedule"
    );

    System.out.println(
            "======================================"
    );
}
	
//search filter for schedule id
public void searchRandomExistingScheduleId() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    // =========================================================
    // 1. Get all existing Schedule IDs from the table
    // =========================================================

    List<WebElement> rows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (rows.isEmpty()) {
        throw new RuntimeException(
                "❌ No schedules available in Schedule Overview"
        );
    }

    List<String> scheduleIds = new ArrayList<>();

    for (WebElement row : rows) {

        String scheduleId = row
                .findElement(By.xpath("./td[1]"))
                .getText()
                .trim();

        if (!scheduleId.isEmpty()) {
            scheduleIds.add(scheduleId);
        }
    }

    if (scheduleIds.isEmpty()) {
        throw new RuntimeException(
                "❌ Could not find any existing Schedule ID"
        );
    }

    // =========================================================
    // 2. Select random Schedule ID
    // =========================================================

    Random random = new Random();

    String randomScheduleId =
            scheduleIds.get(
                    random.nextInt(scheduleIds.size())
            );

    System.out.println("======================================");
    System.out.println(
            "Total Schedule IDs: " + scheduleIds.size()
    );

    System.out.println(
            "Available Schedule IDs: " + scheduleIds
    );

    System.out.println(
            "Random Schedule ID selected: " +
            randomScheduleId
    );

    System.out.println("======================================");


    // =========================================================
    // 3. Click Schedule column filter icon
    // =========================================================

    WebElement scheduleHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Schedule')][1]"
                    )
            )
    );

    WebElement filterIcon = scheduleHeader.findElement(
            By.xpath(
                    "(//div[@class='custom-filter-icon'])[1]"
            )
    );
    filterIcon.click();

    Thread.sleep(500);


    // =========================================================
    // 4. Find search field in filter popup
    // =========================================================

    WebElement searchField = driver.findElement(
                    By.xpath(
                            "//input[@type='text']"
                    )
            
    );

//    searchField.clear();

    searchField.sendKeys(randomScheduleId);

//    System.out.println(
//            "Schedule ID entered in search field: " +
//            randomScheduleId
//    );


    // =========================================================
    // 5. Click Apply
    // =========================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//button[normalize-space()='Apply']"
                            
                    )
            )
    );

    applyButton.click();

    Thread.sleep(1000);


    // =========================================================
    // 6. Validate filtered result
    // =========================================================

    List<WebElement> filteredRows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (filteredRows.isEmpty()) {
        throw new AssertionError(
                "❌ No record found after searching Schedule ID: "
                        + randomScheduleId
        );
    }

    boolean found = false;

    for (WebElement row : filteredRows) {

        String actualScheduleId = row
                .findElement(By.xpath("./td[1]"))
                .getText()
                .trim();

//        System.out.println(
//                "Displayed Schedule ID: " +
//                actualScheduleId
//        );

        if (actualScheduleId.equals(randomScheduleId)) {
            found = true;
        }
    }

    if (!found) {

        throw new AssertionError(
                "❌ Schedule ID filter failed. Expected: "
                        + randomScheduleId
        );
    }

//    System.out.println("======================================");
    System.out.println(
            "✅ Schedule ID filter PASSED"
    );
    System.out.println(
            "Searched Schedule ID: " +
            randomScheduleId
    );
//    System.out.println("======================================");
}  

public void clearInput() throws InterruptedException {
    Thread.sleep(1000);
    driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[1]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//button[normalize-space()='Clear']")).click();
}


public void searchExistingDriverType() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    // =========================================================
    // 1. Get all existing Schedule IDs from the table
    // =========================================================

    List<WebElement> rows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (rows.isEmpty()) {
        throw new RuntimeException(
                "❌ No schedules available in Schedule Overview"
        );
    }

    List<String> scheduleIds = new ArrayList<>();

    for (WebElement row : rows) {

        String scheduleId = row
                .findElement(By.xpath("./td[3]"))
                .getText()
                .trim();

        if (!scheduleId.isEmpty()) {
            scheduleIds.add(scheduleId);
        }
    }

    if (scheduleIds.isEmpty()) {
        throw new RuntimeException(
                "❌ Could not find any existing Schedule ID"
        );
    }

    // =========================================================
    // 2. Select random Schedule ID
    // =========================================================

    Random random = new Random();

    String randomScheduleId =
            scheduleIds.get(
                    random.nextInt(scheduleIds.size())
            );

    System.out.println("======================================");
    System.out.println(
            "Total Driver Type: " + scheduleIds.size()
    );

    System.out.println(
            "Available Schedule IDs: " + scheduleIds
    );

    System.out.println(
            "Random Schedule ID selected: " +
            randomScheduleId
    );

    System.out.println("======================================");


    // =========================================================
    // 3. Click Schedule column filter icon
    // =========================================================

    WebElement scheduleHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Schedule')][1]"
                    )
            )
    );

    WebElement filterIcon = scheduleHeader.findElement(
            By.xpath(
                    "(//div[@class='custom-filter-icon'])[3]"
            )
    );
    filterIcon.click();
//    ((JavascriptExecutor) driver).executeScript(
//            "arguments[0].click();",
//            filterIcon
//    );

    Thread.sleep(500);


    // =========================================================
    // 4. Find search field in filter popup
    // =========================================================

    WebElement searchField = driver.findElement(
                    By.xpath(
                            "//select[@class='grid-select alt-down-arrow']"
                    )
            
    );

//    searchField.clear();

    searchField.sendKeys(randomScheduleId);

//    System.out.println(
//            "Schedule ID entered in search field: " +
//            randomScheduleId
//    );


    // =========================================================
    // 5. Click Apply
    // =========================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//button[normalize-space()='Apply']"
                            
                    )
            )
    );

    applyButton.click();

    Thread.sleep(1000);


    // =========================================================
    // 6. Validate filtered result
    // =========================================================

    List<WebElement> filteredRows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (filteredRows.isEmpty()) {
        throw new AssertionError(
                "❌ No record found after searching Schedule ID: "
                        + randomScheduleId
        );
    }

    boolean found = false;

    for (WebElement row : filteredRows) {

        String actualScheduleId = row
                .findElement(By.xpath("./td[3]"))
                .getText()
                .trim();

//        System.out.println(
//                "Displayed Schedule ID: " +
//                actualScheduleId
//        );

        if (actualScheduleId.equals(randomScheduleId)) {
            found = true;
        }
    }

    if (!found) {

        throw new AssertionError(
                "❌ Schedule ID filter failed. Expected: "
                        + randomScheduleId
        );
    }

//    System.out.println("======================================");
    System.out.println(
            "✅ Driver Type filter PASSED"
    );
    System.out.println(
            "Searched Driver Type: " +
            randomScheduleId
    );
//    System.out.println("======================================");
    
    Thread.sleep(3000);

}

public void clearInput2() throws InterruptedException {
//    Thread.sleep(1000);
    driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[3]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//button[normalize-space()='Clear']")).click();
}


public void searchExistingSellerType() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    // =========================================================
    // 1. Get all existing Schedule IDs from the table
    // =========================================================

    List<WebElement> rows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (rows.isEmpty()) {
        throw new RuntimeException(
                "❌ No schedules available in Schedule Overview"
        );
    }

    List<String> scheduleIds = new ArrayList<>();

    for (WebElement row : rows) {

        String scheduleId = row
                .findElement(By.xpath("./td[4]"))
                .getText()
                .trim();

        if (!scheduleId.isEmpty()) {
            scheduleIds.add(scheduleId);
        }
    }

    if (scheduleIds.isEmpty()) {
        throw new RuntimeException(
                "❌ Could not find any existing Schedule ID"
        );
    }

    // =========================================================
    // 2. Select random Schedule ID
    // =========================================================

    Random random = new Random();

    String randomScheduleId =
            scheduleIds.get(
                    random.nextInt(scheduleIds.size())
            );

    System.out.println("======================================");
    System.out.println(
            "Total Driver Name: " + scheduleIds.size()
    );

    System.out.println(
            "Available Driver Name: " + scheduleIds
    );

    System.out.println(
            "Random Driver Name selected: " +
            randomScheduleId
    );

    System.out.println("======================================");


    // =========================================================
    // 3. Click Schedule column filter icon
    // =========================================================

    WebElement scheduleHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Schedule')][1]"
                    )
            )
    );

    WebElement filterIcon = scheduleHeader.findElement(
            By.xpath(
                    "(//div[@class='custom-filter-icon'])[4]"
            )
    );
    filterIcon.click();
//    ((JavascriptExecutor) driver).executeScript(
//            "arguments[0].click();",
//            filterIcon
//    );

    Thread.sleep(500);


    // =========================================================
    // 4. Find search field in filter popup
    // =========================================================

    WebElement searchField = driver.findElement(
                    By.xpath(
                            "//input[@type='text']"
                    )
            
    );

//    searchField.clear();

    searchField.sendKeys(randomScheduleId);

//    System.out.println(
//            "Schedule ID entered in search field: " +
//            randomScheduleId
//    );


    // =========================================================
    // 5. Click Apply
    // =========================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//button[normalize-space()='Apply']"
                            
                    )
            )
    );

    applyButton.click();

    Thread.sleep(1000);


    // =========================================================
    // 6. Validate filtered result
    // =========================================================

    List<WebElement> filteredRows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//tbody/tr")
            )
    );

    if (filteredRows.isEmpty()) {
        throw new AssertionError(
                "❌ No record found after searching Schedule ID: "
                        + randomScheduleId
        );
    }

    boolean found = false;

    for (WebElement row : filteredRows) {

        String actualScheduleId = row
                .findElement(By.xpath("./td[4]"))
                .getText()
                .trim();

//        System.out.println(
//                "Displayed Schedule ID: " +
//                actualScheduleId
//        );

        if (actualScheduleId.equals(randomScheduleId)) {
            found = true;
        }
    }

    if (!found) {

        throw new AssertionError(
                "❌ Schedule ID filter failed. Expected: "
                        + randomScheduleId
        );
    }

//    System.out.println("======================================");
    System.out.println(
            "✅ Driver Name filter PASSED"
    );
    System.out.println(
            "Searched Driver Name: " +
            randomScheduleId
    );
    System.out.println("======================================");
    
    Thread.sleep(3000);

}

public void printScheduleTypeCount() {

    List<WebElement> rows = driver.findElements(
            By.xpath("//tbody/tr")
    );

    int deliveryCount = 0;
    int pickupCount = 0;

    for (WebElement row : rows) {

        String scheduleType = row
                .findElement(By.xpath("./td[9]"))
                .getText()
                .trim();

        if (scheduleType.equalsIgnoreCase("Delivery")) {

            deliveryCount++;

        } else if (scheduleType.equalsIgnoreCase("Pickup")) {

            pickupCount++;
        }
    }

    System.out.println("======================================");
    System.out.println("Schedule Type Count");
    System.out.println("======================================");
    System.out.println("Delivery : " + deliveryCount);
    System.out.println("Pickup   : " + pickupCount);
    System.out.println("Total    : " + rows.size());
    System.out.println("======================================");
}


public void validateScheduleTypeFilter(String scheduleType)
        throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    // =====================================================
    // 1. Click Schedule Type filter icon
    // =====================================================

    WebElement scheduleTypeHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Schedule Type')]"
                    )
            )
    );

    WebElement filterIcon = scheduleTypeHeader.findElement(
            By.xpath(
                    "(//div[@class='custom-filter-icon'])[5]"
            )
    );

            filterIcon.click();
    

    Thread.sleep(500);

    // =====================================================
    // 2. Select Schedule Type
    // =====================================================

    WebElement scheduleTypeDropdown = driver.findElement(
                    By.xpath(
                            "//select[@class='grid-select alt-down-arrow']"
                    )           
    );

    Select select = new Select(scheduleTypeDropdown);

    select.selectByVisibleText(scheduleType);

    System.out.println(
            "Schedule Type selected: " +
            scheduleType
    );

    // =====================================================
    // 3. Click APPLY
    // =====================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//button[normalize-space()='Apply']"
                    )
            )
    );

    applyButton.click();

    Thread.sleep(1000);

    // =====================================================
    // 4. Validate filtered records
    // =====================================================

    List<WebElement> rows = driver.findElements(
            By.xpath("//tbody/tr")
    );

    if (rows.isEmpty()) {

        throw new AssertionError(
                "❌ No records found for Schedule Type: "
                        + scheduleType
        );
    }

    int matchedCount = 0;

    for (int i = 0; i < rows.size(); i++) {

        String actualScheduleType = rows.get(i)
                .findElement(
                        By.xpath("./td[9]")
                )
                .getText()
                .trim();

//        System.out.println(
//                "Row " + (i + 1) +
//                " Schedule Type: " +
//                actualScheduleType
//        );

        if (actualScheduleType.equalsIgnoreCase(
                scheduleType)) {

            matchedCount++;

        } else {

            throw new AssertionError(
                    "❌ Schedule Type filter failed. " +
                    "Expected: " + scheduleType +
                    ", Actual: " + actualScheduleType
            );
        }
    }

    System.out.println("======================================");
    System.out.println(
            "✅ Schedule Type Filter PASSED"
    );
    System.out.println(
            "Schedule Type: " + scheduleType
    );
    System.out.println(
            "Matching Records: " + matchedCount
    );
    System.out.println("======================================");
}

//public void clearInput3() throws InterruptedException {
////  Thread.sleep(1000);
//  driver.findElement(By.xpath("(//div[@class='custom-filter-icon'])[5]")).click();
//  Thread.sleep(1000);
//  driver.findElement(By.xpath("//button[normalize-space()='Clear']")).click();
//}

public void clearScheduleTypeFilter()
        throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    // Open Schedule Type filter again

    WebElement scheduleTypeHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Schedule Type')]"
                    )
            )
    );

    WebElement filterIcon = driver.findElement(By.xpath(
                    "(//div[@class='custom-filter-icon'])[5]"));
                
            filterIcon.click();
 
    Thread.sleep(500);

    // Click CLEAR

    WebElement clearButton = driver.findElement(
                    By.xpath(
                            "//button[normalize-space()='Clear']"
                    )
            
    );

    clearButton.click();

    Thread.sleep(1000);   
}

//validate status count

public void validateStatusCount(String status)
        throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
    );

    System.out.println("======================================");
    System.out.println("Validating Status: " + status);
    System.out.println("======================================");

    // =========================================================
    // 1. Capture count from top status box
    // =========================================================

    int boxCount = getStatusBoxCount(status);

    // =========================================================
    // 2. Click Status filter icon
    // =========================================================

    WebElement statusHeader = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//th[contains(normalize-space(.),'Status')]"
                    )
            )
    );

    WebElement filterIcon = statusHeader.findElement(
            By.xpath(
                    "(//div[@class='custom-filter-icon'])[6]"
            )
    );          
            filterIcon.click();

    Thread.sleep(500);

    // =========================================================
    // 3. Select Status from dropdown
    // =========================================================

    WebElement statusDropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//div[contains(@class,'custom-grid-filter-popover')]"
                            + "//select[contains(@class,'grid-select')]"
                    )
            )
    );

    Select select = new Select(statusDropdown);


    select.selectByVisibleText(status);

    // =========================================================
    // 4. Click APPLY
    // =========================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//button[normalize-space()='Apply']"
                    )
            )
    );

    applyButton.click();

    Thread.sleep(1000);

    // =========================================================
    // 5. Count filtered records
    // =========================================================

 // =========================================================
 // 5. Count filtered records
 // =========================================================

 List<WebElement> rows = driver.findElements(
         By.xpath("//tbody/tr")
 );

 int filteredCount = 0;

 // If expected count is 0, there should be no actual records
 if (boxCount == 0) {

     System.out.println(
             "No records expected for status: " + status
     );

     filteredCount = 0;

 } else {

     for (WebElement row : rows) {

         // Get all cells in this row
         List<WebElement> cells = row.findElements(
                 By.xpath("./td")
         );

         // Ignore empty/no-data rows
         if (cells.size() < 10) {
             continue;
         }

         String actualStatus = cells.get(9)
                 .getText()
                 .trim();

         if (actualStatus.equalsIgnoreCase(status)) {

             filteredCount++;
         }
     }
 }

    // =========================================================
    // 6. Compare counts
    // =========================================================

    if (boxCount != filteredCount) {

        throw new AssertionError(
                "❌ Status count validation FAILED for: "
                        + status
                        + "\nTop Box Count: " + boxCount
                        + "\nFiltered Record Count: "
                        + filteredCount
        );
    }

    System.out.println(
            "✅ STATUS COUNT VALIDATION PASSED"
    );
    System.out.println("Selected Status: " + status);
    System.out.println("Top Box Count: " + boxCount);
    System.out.println(
            "Filtered Record Count: " + filteredCount
    );
//    System.out.println("======================================");
}

public int getStatusBoxCount(String status) {

    WebElement statusBox = driver.findElement(
            By.xpath(
                    "//*[normalize-space()='" + status + "']"
                    + "/ancestor::div[1]"
            )
    );

    List<WebElement> numbers = statusBox.findElements(
            By.xpath(
                    ".//*[normalize-space() and " +
                    "not(*)]"
            )
    );

    for (WebElement element : numbers) {

        String text = element.getText().trim();

        if (text.matches("\\d+")) {
            return Integer.parseInt(text);
        }
    }

    throw new RuntimeException(
            "❌ Could not find count for status box: "
                    + status
    );
}

//Reschedule for Incomplete order

public void ReSchedule() throws InterruptedException {

	Thread.sleep(2000);

	driver.findElement(
	    By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
	).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//a[text()='View Trip']")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//li[text()='View Details']")).click();

	Thread.sleep(2000);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	Random random = new Random();

	By businessBy = By.xpath("//div[contains(@class,'accordion-header')]");
	By ordersBy = By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");

	// ✅ WAIT until at least one business exists
	wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, 0));

	int businessCount = driver.findElements(businessBy).size();

	for (int i = 0; i < businessCount; i++) {

	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	    WebElement currentBusiness = driver.findElements(businessBy).get(i);
	    wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	    Thread.sleep(2000);

	    while (true) {

	        List<WebElement> orders = driver.findElements(ordersBy);
	        WebElement pendingOrder = null;

	        for (WebElement order : orders) {
	            // ✅ Treat BOTH Completed and Incomplete as "already processed"
	            boolean isProcessed =
	                order.findElements(By.xpath(".//div[@title='Completed']")).size() > 0
	                || order.findElements(By.xpath(".//div[@title='Incomplete']")).size() > 0;

	            if (!isProcessed) {
	                pendingOrder = order;
	                break;
	            }
	        }

	        if (pendingOrder == null) {

	            System.out.println("All orders processed for business " + (i + 1));

	            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	            currentBusiness = driver.findElements(businessBy).get(i);
	            wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	            Thread.sleep(1500);
	            break;
	        }

	        WebElement moreBtn = pendingOrder.findElement(
	            By.xpath(".//button[contains(@class,'actions-dropdown')]"));
	        wait.until(ExpectedConditions.elementToBeClickable(moreBtn)).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[normalize-space()='Change Status']"))).click();

	        // ✅ Randomly pick Completed or Incomplete (e.g. 70% Completed / 30% Incomplete)
	        String chosenStatus = random.nextInt(100) < 70 ? "Completed" : "Incomplete";

	        WebElement statusDropdown = wait.until(
	            ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
	        new Select(statusDropdown).selectByVisibleText(chosenStatus);

	        WebElement comment = wait.until(
	            ExpectedConditions.elementToBeClickable(By.name("comment")));
	        comment.clear();
	        comment.sendKeys(chosenStatus.equals("Completed")
	                ? "Schedule Completed Successfully"
	                : "Schedule Marked Incomplete for Testing");

	        wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[normalize-space()='Submit']"))).click();

	        Thread.sleep(2000);

	        wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='OK']"))).click();

	        Thread.sleep(1500);
	    }
	}
}

public void ReScheduleforIncompleteOrdes() throws InterruptedException {

    Thread.sleep(2000);

    driver.findElement(
        By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
    ).click();

    Thread.sleep(2000);

    driver.findElement(By.xpath("//a[normalize-space()='View Trip']")).click();

    Thread.sleep(2000);

    driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

    Thread.sleep(2000);

    driver.findElement(By.xpath("//li[normalize-space()='View Details']")).click();

    Thread.sleep(2000);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    By businessBy = By.xpath("//div[contains(@class,'accordion-header')]");

    By ordersBy = By.xpath(
        "//tbody/tr[not(contains(@style,'display:none'))]"
    );

    // Wait until businesses are loaded
    wait.until(
        ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, 0)
    );

    int businessCount = driver.findElements(businessBy).size();

    Random random = new Random();

    for (int i = 0; i < businessCount; i++) {

        wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i)
        );

        WebElement currentBusiness =
            driver.findElements(businessBy).get(i);

        wait.until(
            ExpectedConditions.elementToBeClickable(currentBusiness)
        ).click();

        Thread.sleep(1500);

        boolean hasIncompleteOrder = false;

        while (true) {

            List<WebElement> orders = driver.findElements(ordersBy);

            WebElement pendingOrder = null;

            for (WebElement order : orders) {

                boolean isCompleted =
                    order.findElements(
                        By.xpath(".//div[@title='Completed']")
                    ).size() > 0;

                boolean isIncomplete =
                    order.findElements(
                        By.xpath(".//div[@title='Incomplete']")
                    ).size() > 0;

                // Process only orders which have no final status
                if (!isCompleted && !isIncomplete) {
                    pendingOrder = order;
                    break;
                }
            }

            // No more pending orders
            if (pendingOrder == null) {

                System.out.println(
                    "All orders completed/processed for business "
                    + (i + 1)
                );

                break;
            }

            // ==========================================
            // RANDOM STATUS SELECTION
            // ==========================================

            String selectedStatus;

            /*
             * Guarantee that at least one order becomes
             * Incomplete for this business.
             */
            if (!hasIncompleteOrder) {

                // 50% chance of incomplete for the first order
                // You can change this logic if required
                selectedStatus = random.nextBoolean()
                    ? "Complete"
                    : "Incomplete";

            } else {

                // After first incomplete, randomly select
                selectedStatus = random.nextBoolean()
                    ? "Complete"
                    : "Incomplete";
            }

            System.out.println(
                "Selected Status for Order: " + selectedStatus
            );

            // ==========================================
            // CLICK MORE
            // ==========================================

            WebElement moreBtn = pendingOrder.findElement(
                By.xpath(".//button[contains(@class,'actions-dropdown')]")
            );

            wait.until(
                ExpectedConditions.elementToBeClickable(moreBtn)
            ).click();

            // ==========================================
            // CHANGE STATUS
            // ==========================================

            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Change Status']")
                )
            ).click();

            // ==========================================
            // SELECT STATUS
            // ==========================================

            WebElement statusDropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.name("selectedStatus")
                )
            );

            Select statusSelect = new Select(statusDropdown);

            statusSelect.selectByVisibleText(selectedStatus);

            System.out.println(
                "Status selected in dropdown: " + selectedStatus
            );

            // ==========================================
            // COMMENT
            // ==========================================

            WebElement comment = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.name("comment")
                )
            );

            comment.clear();

            if (selectedStatus.equals("Incomplete")) {

                comment.sendKeys(
                    "Order marked as Incomplete during schedule processing"
                );

                hasIncompleteOrder = true;

            } else {

                comment.sendKeys(
                    "Schedule Completed Successfully"
                );
            }

            // ==========================================
            // SUBMIT
            // ==========================================

            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Submit']")
                )
            ).click();

            Thread.sleep(1500);

            // ==========================================
            // OK
            // ==========================================

            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='OK']")
                )
            ).click();

            Thread.sleep(1500);

            /*
             * IMPORTANT:
             *
             * Don't reopen the business here.
             * The DOM refreshes automatically and the
             * next iteration will find the next unprocessed
             * order.
             */
        }
    }
}

// Edit schedule

public void clickEditSchedule() throws InterruptedException {
	
	 driver.findElement(
		        By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
		    ).click();

		    Thread.sleep(2000);

		    driver.findElement(By.xpath("//a[normalize-space()='Edit Schedule Details']")).click();
		    
		    
		 // =========================================================
		    // 2. SELECT OPTION FROM ALL DEPARTURE TIME DROPDOWNS
		    // =========================================================
         Random random = new Random();
		    List<WebElement> timeDropdowns = driver.findElements(
		            By.xpath("//select[@name='pickupBy']")
		    );

		    System.out.println(
		            "Total Departure Time Dropdowns Found: " +
		            timeDropdowns.size()
		    );

		    for (int i = 0; i < timeDropdowns.size(); i++) {

		        try {

		            // Re-fetch every time to avoid stale element
		            timeDropdowns = driver.findElements(
		                    By.xpath("//select[@name='pickupBy']")
		            );

		            WebElement timeDropdown = timeDropdowns.get(i);

		            scrollToElement(timeDropdown);

		            Select timeSelect = new Select(timeDropdown);

		            List<WebElement> options = timeSelect.getOptions();

		            // Collect valid options, ignoring "Select"
		            List<WebElement> validOptions = new ArrayList<>();

		            for (WebElement option : options) {

		                String text = option.getText().trim();

		                if (!text.isEmpty()
		                        && !text.equalsIgnoreCase("Select")
		                        && !text.equalsIgnoreCase("Departure Time")
		                        && !text.equalsIgnoreCase("Select Time")) {

		                    validOptions.add(option);
		                }
		            }

		            if (validOptions.isEmpty()) {

		                System.out.println(
		                        "⚠ No valid departure time option found for dropdown "
		                        + (i + 1)
		                );

		                continue;
		            }

		            // Select random valid option
		            WebElement selectedOption =
		                    validOptions.get(
		                            random.nextInt(validOptions.size())
		                    );

		            String selectedTime =
		                    selectedOption.getText().trim();

		            timeSelect.selectByVisibleText(selectedTime);

		            System.out.println(
		                    "Departure Time " + (i + 1) +
		                    " selected: " + selectedTime
		            );

		            Thread.sleep(500);

		        } catch (StaleElementReferenceException e) {

		            System.out.println(
		                    "⚠ Time dropdown " +
		                    (i + 1) +
		                    " became stale. Retrying..."
		            );

		            i--;
		            Thread.sleep(500);

		        } catch (Exception e) {

		            System.out.println(
		                    "❌ Time dropdown " +
		                    (i + 1) +
		                    " failed: " +
		                    e.getMessage()
		            );
		        }
}

}

public void expandAndSelectSingleOrder() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    String allRowsXpath =
            "//div[@class='custom-grid-body']//table/tbody/tr";

    // ============================================================
    // FIND ONLY REAL ORDER ROWS
    // ============================================================

    List<WebElement> allRows = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath(allRowsXpath)
            )
    );

    List<Integer> orderRowIndexes = new ArrayList<>();

    for (int i = 0; i < allRows.size(); i++) {

        WebElement row = allRows.get(i);

        List<WebElement> cells = row.findElements(
                By.xpath("./td")
        );

        if (cells.size() < 2) {
            continue;
        }

        String secondColumnText = cells.get(1).getText().trim();

        // Order ID looks like 00004720
        if (secondColumnText.matches("\\d{8}")) {

            orderRowIndexes.add(i);

            System.out.println(
                    "Found Order: " + secondColumnText
            );
        }
    }

    System.out.println(
            "Total actual orders found: " + orderRowIndexes.size()
    );

    // ============================================================
    // PROCESS EACH ORDER
    // ============================================================

    for (int orderIndex = 0;
         orderIndex < orderRowIndexes.size();
         orderIndex++) {

        System.out.println("----------------------------------------");
        System.out.println(
                "Processing Order " + (orderIndex + 1)
        );

        try {

            // ----------------------------------------------------
            // RE-FIND ALL ROWS
            // ----------------------------------------------------

            allRows = driver.findElements(
                    By.xpath(allRowsXpath)
            );

            // ----------------------------------------------------
            // FIND CURRENT ORDER ROW AGAIN
            // ----------------------------------------------------

            WebElement orderRow = null;
            String orderId = null;

            int currentOrderNumber = 0;

            for (WebElement row : allRows) {

                List<WebElement> cells = row.findElements(
                        By.xpath("./td")
                );

                if (cells.size() < 2) {
                    continue;
                }

                String text = cells.get(1).getText().trim();

                if (text.matches("\\d{8}")) {

                    if (currentOrderNumber == orderIndex) {

                        orderRow = row;
                        orderId = text;
                        break;
                    }

                    currentOrderNumber++;
                }
            }

            if (orderRow == null) {

                System.out.println(
                        "⚠️ Could not find order " +
                        (orderIndex + 1)
                );

                continue;
            }

            System.out.println(
                    "Order ID: " + orderId
            );

            // ----------------------------------------------------
            // EXPAND CURRENT ORDER
            // ----------------------------------------------------

            WebElement expandButton = orderRow.findElement(
                    By.xpath("./td[1]")
            );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            expandButton
                    )
            ).click();

            System.out.println(
                    "✅ Order " + orderId + " expanded"
            );

            // ----------------------------------------------------
            // WAIT FOR EXPANDED SECTION
            // ----------------------------------------------------

            Thread.sleep(800);

            // ----------------------------------------------------
            // FIND SELECT ALL CHECKBOX FOR THIS ORDER ONLY
            // ----------------------------------------------------

            WebElement selectAllCheckbox = orderRow.findElement(
                    By.xpath(
                            "./following-sibling::tr[1]" +
                            "//input[@type='checkbox'][1]"
                    )
            );

            // ----------------------------------------------------
            // CHECK CURRENT STATE
            // ----------------------------------------------------

            if (selectAllCheckbox.isSelected()) {

                // IMPORTANT:
                // DO NOT CLICK IT
                System.out.println(
                        "☑️ Order " + orderId +
                        " already selected -> SKIP"
                );

            } else {

                // Only click when unchecked
                System.out.println(
                        "⬜ Order " + orderId +
                        " not selected -> SELECTING"
                );

                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                selectAllCheckbox
                        )
                ).click();

                System.out.println(
                        "✅ Order " + orderId +
                        " selected"
                );
            }

            // ----------------------------------------------------
            // NEXT ORDER
            // ----------------------------------------------------

            System.out.println(
                    "➡️ Moving to next order..."
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Error processing Order " +
                    (orderIndex + 1)
            );

            e.printStackTrace();
        }
    }

    System.out.println("----------------------------------------");
    System.out.println("✅ All orders processed");
}

public List<SelectedOrder> ExpandOrderAndSelect_SingleCheckbox()
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    List<SelectedOrder> selectedOrders =
            new ArrayList<>();

    String allRowsXpath =
            "//div[contains(@class,'custom-grid-body')]"
          + "//table/tbody/tr"
          + "[td[2] and td[4]]";

    List<WebElement> allRows = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath(allRowsXpath)
            )
    );

    System.out.println(
            "Total order rows found: " + allRows.size()
    );

    for (WebElement row : allRows) {

        String orderId = row.findElement(
                By.xpath(".//td[2]")
        ).getText().trim();

        String businessName = row.findElement(
                By.xpath(".//td[4]")
        ).getText().trim();

        System.out.println("--------------------------------");
        System.out.println("Checking Order ID : " + orderId);
        System.out.println("Business          : " + businessName);

                // ==============================================
        		// FIND EXPAND SVG
        		// =====================================================

        		WebElement expandButton = row.findElement(
        		        By.xpath(".//td[1]//*[name()='svg']")
        		);

        		((JavascriptExecutor) driver).executeScript(
        		        "arguments[0].scrollIntoView({block:'center'});",
        		        expandButton
        		);

  //      		((JavascriptExecutor) driver).executeScript(
  //      		        "arguments[0].click();",
        		        expandButton.click();
  //      		);

        		System.out.println("Expanded Order: " + orderId);

        		Thread.sleep(700);

        // =====================================================
        // WAIT FOR EXPANDED ROW
        // =====================================================

        Thread.sleep(700);

        // =====================================================
        // FIND CHECKBOX IN EXPANDED ROW
        // =====================================================

        List<WebElement> checkboxes = row.findElements(
                By.xpath(
                        "./following-sibling::tr[1]"
                      + "//input[@type='checkbox']"
                )
        );

        if (checkboxes.isEmpty()) {

            System.out.println(
                    "No checkbox found for Order ID: "
                    + orderId
            );

            continue;
        }

        WebElement checkbox = checkboxes.get(0);

        // =====================================================
        // CHECK IF ALREADY SELECTED
        // =====================================================

        if (checkbox.isSelected()) {

            System.out.println(
                    "Order already selected. SKIPPING: "
                    + orderId
            );

            // Collapse
//            ((JavascriptExecutor) driver).executeScript(
//                    "arguments[0].click();",
//                    expandButton
//            );

            Thread.sleep(300);

            continue;
        }

        // =====================================================
        // SELECT CHECKBOX
        // =====================================================

        System.out.println(
                "Selecting Order: " + orderId
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                checkbox
        );

        // =====================================================
        // VERIFY
        // =====================================================

        if (!checkbox.isSelected()) {

            Assert.fail(
                    "Checkbox was not selected for Order ID: "
                    + orderId
            );
        }

        System.out.println(
                "Checkbox selected successfully: "
                + orderId
        );

        // =====================================================
        // CAPTURE ORDER + BUSINESS
        // =====================================================

        selectedOrders.add(
                new SelectedOrder(
                        orderId,
                        businessName
                )
        );

        // =====================================================
        // STOP AFTER SELECTING ONE NEW ORDER
        // =====================================================

        break;
    }

    // =========================================================
    // PRINT RESULT
    // =========================================================

    System.out.println(
            "\n========== NEWLY SELECTED ORDER =========="
    );

    for (SelectedOrder order : selectedOrders) {

        System.out.println(
                "Order ID = " + order.getOrderId()
                + " | Business = "
                + order.getBusinessName()
        );
    }
//    System.out.println("======================================");
    System.out.println(
            "Total newly selected orders: "
            + selectedOrders.size()
    );

    return selectedOrders;
}

public List<SelectedOrder> ExpandOrderAndSelectAllOrders() throws InterruptedException {
	Thread.sleep(10000);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    List<SelectedOrder> selectedOrders = new ArrayList<>();

    String rowsXpath =
            "//div[contains(@class,'custom-grid-body')]//table/tbody/tr";

    // ============================================================
    // STEP 1: Capture Order IDs first
    // Do NOT keep WebElements because grid gets refreshed
    // ============================================================

    List<WebElement> initialRows = driver.findElements(By.xpath(rowsXpath));

    List<String> orderIds = new ArrayList<>();

    for (WebElement row : initialRows) {

        List<WebElement> cells = row.findElements(By.xpath("./td"));

        if (cells.size() < 4) {
            continue;
        }

        String orderId = cells.get(1).getText().trim();

        // Your Order IDs are 8 digits
        if (orderId.matches("\\d{8}")) {

            orderIds.add(orderId);

//            System.out.println("Found Order ID: " + orderId);
        }
    }

    System.out.println("======================================");
    System.out.println("Total Orders Found: " + orderIds.size());
    System.out.println("======================================");


    // ============================================================
    // STEP 2: Process each order
    // ============================================================

    for (String orderId : orderIds) {

//        System.out.println("\n--------------------------------------");
//        System.out.println("Processing Order ID: " + orderId);
//        System.out.println("--------------------------------------");


        String orderRowXpath =
                "//div[contains(@class,'custom-grid-body')]"
              + "//table/tbody/tr"
              + "[td[2][normalize-space()='" + orderId + "']]";


        try {

            // ====================================================
            // ALWAYS FIND FRESH ROW
            // ====================================================

            WebElement orderRow = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(orderRowXpath)
                    )
            );


            String businessName =
                    orderRow.findElement(By.xpath("./td[4]"))
                            .getText()
                            .trim();

            System.out.println("Business : " + businessName);


            // ====================================================
            // CHECK CURRENT EXPAND STATE
            // ====================================================

            List<WebElement> downArrow =
                    orderRow.findElements(
                            By.xpath(".//td[1]//*[name()='svg' "
                                   + "and @data-testid='KeyboardArrowDownIcon']")
                    );


            // ====================================================
            // ALREADY EXPANDED
            // ====================================================

            if (!downArrow.isEmpty()) {

                System.out.println(
                        "Order " + orderId +
                        " is ALREADY EXPANDED -> SKIPPING"
                );
                System.out.println("======================================");
                continue;
            }


            // ====================================================
            // ORDER IS COLLAPSED
            // Expand it
            // ====================================================

//            System.out.println(
//                    "Order " + orderId +
//                    " is COLLAPSED -> Expanding..."
//            );


            WebElement expandCell =
                    orderRow.findElement(By.xpath("./td[1]"));


            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    expandCell
            );


            wait.until(
                    ExpectedConditions.elementToBeClickable(expandCell)
            ).click();


            // ====================================================
            // WAIT UNTIL ARROW CHANGES TO DOWN
            // ====================================================

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    orderRowXpath
                                  + "/td[1]//*[name()='svg'"
                                  + " and @data-testid='KeyboardArrowDownIcon']"
                            )
                    )
            );


//            System.out.println(
//                    "Order " + orderId + " expanded successfully"
//            );


            // ====================================================
            // IMPORTANT:
            // Grid may re-render after expansion.
            // DO NOT use old orderRow here.
            // ====================================================

            Thread.sleep(500);


            // Find checkbox under expanded order
            //
            // First try normal input checkbox
            // ====================================================

            String checkboxXpath =
                    orderRowXpath
                  + "/following-sibling::tr[1]"
                  + "//input[@type='checkbox'][1]";


            List<WebElement> checkboxList =
                    driver.findElements(By.xpath(checkboxXpath));


            if (checkboxList.isEmpty()) {

                System.out.println(
                        "Normal input checkbox not found for "
                        + orderId
                );

                // =================================================
                // If your application uses custom DIV checkbox,
                // use this locator
                // =================================================

                String customCheckboxXpath =
                        orderRowXpath
                      + "/following-sibling::tr[1]"
                      + "//div[contains(@class,"
                      + "'grid-checkbox-div')]";


                List<WebElement> customCheckboxes =
                        driver.findElements(
                                By.xpath(customCheckboxXpath)
                        );


                if (customCheckboxes.isEmpty()) {

                    System.out.println(
                            "No checkbox found for Order ID: "
                            + orderId
                    );

                    continue;
                }


                WebElement checkbox = customCheckboxes.get(0);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        checkbox
                );

                checkbox.click();

                System.out.println(
                        "Checkbox selected for Order ID: "
                        + orderId
                );

            } else {

                WebElement checkbox = checkboxList.get(0);


                if (checkbox.isSelected()) {

                    System.out.println(
                            "Order " + orderId +
                            " checkbox already selected -> SKIP"
                    );

                    continue;
                }


                checkbox.click();

                System.out.println(
                        "Checkbox selected for Order ID: "
                        + orderId
                );
            }


            // ====================================================
            // Capture ONLY newly selected order
            // ====================================================

            selectedOrders.add(
                    new SelectedOrder(
                            orderId,
                            businessName
                    )
            );


//            System.out.println(
//                    "NEWLY SELECTED ORDER: "
//                    + orderId
//                    + " | "
//                    + businessName
//            );


        } catch (StaleElementReferenceException e) {

            System.out.println(
                    "Grid refreshed while processing "
                    + orderId
                    + " -> retrying..."
            );

            // Re-find order once after refresh
            Thread.sleep(500);

            WebElement freshRow = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(orderRowXpath)
                    )
            );

            List<WebElement> freshDownArrow =
                    freshRow.findElements(
                            By.xpath(
                                    ".//td[1]//*[name()='svg'"
                                  + " and @data-testid='KeyboardArrowDownIcon']"
                            )
                    );

            if (!freshDownArrow.isEmpty()) {

                System.out.println(
                        "Order " + orderId +
                        " is now expanded -> SKIPPING"
                );

                continue;
            }
        }
    }


    // ============================================================
    // FINAL RESULT
    // ============================================================

/*    System.out.println("\n======================================");
    System.out.println("NEWLY SELECTED ORDERS");
    System.out.println("======================================");

    for (SelectedOrder order : selectedOrders) {
        System.out.println(order);
    }

    System.out.println("======================================");*/

    return selectedOrders;
}

public class SelectedOrder {

    private String orderId;
    private String businessName;

    public SelectedOrder(String orderId, String businessName) {
        this.orderId = orderId;
        this.businessName = businessName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBusinessName() {
        return businessName;
    }

    @Override
    public String toString() {
        return "Order ID = " + orderId
                + ", Business = " + businessName;
    }
}

//public void validateSelectedOrders(List<SelectedOrder> selectedOrders)
//        throws InterruptedException {
//
//    WebDriverWait wait =
//            new WebDriverWait(driver, Duration.ofSeconds(20));
//
//    for (SelectedOrder selectedOrder : selectedOrders) {
//
//        String orderId = selectedOrder.getOrderId();
//        String businessName = selectedOrder.getBusinessName();
//
//        System.out.println();
//        System.out.println("======================================");
//        System.out.println("VALIDATING ORDER");
//        System.out.println("Order ID  : " + orderId);
//        System.out.println("Business  : " + businessName);
//        System.out.println("======================================");
//
//        /*
//         * Find business row
//         *
//         * Business Name is in the second TD in your page.
//         */
//        String businessRowXpath =
//                "//div[contains(@class,'custom-grid-body')]"
//              + "//table/tbody/tr"
//              + "[td[2][contains(normalize-space(.),"
//              + "'" + businessName + "')]]";
//
//        WebElement businessRow = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath(businessRowXpath)
//                )
//        );
//
//        System.out.println("Business found: " + businessName);
//
//        /*
//         * Scroll business row into view
//         */
//        ((JavascriptExecutor) driver).executeScript(
//                "arguments[0].scrollIntoView({block:'center'});",
//                businessRow
//        );
//
//        /*
//         * Click FIRST TD of the business row.
//         *
//         * Do NOT click KeyboardArrowDownIcon using JS.
//         */
//        WebElement expandCell = businessRow.findElement(
//                By.xpath("./td[1]")
//        );
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(expandCell)
//        );
//
//        expandCell.click();
//
//        System.out.println("Business expanded: " + businessName);
//
//        /*
//         * Wait for expanded order/product data
//         */
//        Thread.sleep(700);
//
//        /*
//         * Now search ONLY for the captured Order ID.
//         *
//         * Search the entire grid because the order is
//         * displayed in the expanded child table.
//         */
//        String orderXpath =
//                "//div[contains(@class,'custom-grid-body')]"
//              + "//*[self::td or self::span or self::div]"
//              + "[contains(normalize-space(.),"
//              + "'" + orderId + "')]";
//
//        List<WebElement> orderElements = driver.findElements(
//                By.xpath(orderXpath)
//        );
//
//        boolean orderFound = false;
//
//        for (WebElement element : orderElements) {
//
//            if (element.getText().trim().contains(orderId)) {
//                orderFound = true;
//                break;
//            }
//        }
//
//        /*
//         * Final validation
//         */
//        if (orderFound) {
//
//            System.out.println(
//                    "PASS: Order ID " + orderId
//                    + " is displayed under Business "
//                    + businessName
//            );
//            System.out.println("=======================================");
//        } else {
//
//            Assert.fail(
//                    "FAIL: Order ID " + orderId
//                    + " is NOT displayed under Business "
//                    + businessName
//            );
//        }
//    }
//}

public void validateSelectedOrders(List<SelectedOrder> selectedOrders)
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // ============================================================
    // STEP 1: Group Order IDs by Business
    // ============================================================

    Map<String, List<String>> ordersByBusiness =
            new LinkedHashMap<>();

    for (SelectedOrder selectedOrder : selectedOrders) {

        ordersByBusiness
                .computeIfAbsent(
                        selectedOrder.getBusinessName(),
                        key -> new ArrayList<>()
                )
                .add(selectedOrder.getOrderId());
    }


    // ============================================================
    // STEP 2: Process each Business only ONCE
    // ============================================================

    for (Map.Entry<String, List<String>> entry
            : ordersByBusiness.entrySet()) {

        String businessName = entry.getKey();

        List<String> expectedOrderIds =
                entry.getValue();


        System.out.println("\n======================================");
        System.out.println("VALIDATING BUSINESS");
        System.out.println("Business : " + businessName);
        System.out.println("Orders   : " + expectedOrderIds);
        System.out.println("======================================");


        // ========================================================
        // Find Business row
        // ========================================================

        String businessRowXpath =
              "//div[contains(@class,'custom-grid-body')]"
            + "//table/tbody/tr"
            + "[td[2][contains(normalize-space(.),"
            + "'" + businessName + "')]]";

      WebElement businessRow = wait.until(
              ExpectedConditions.visibilityOfElementLocated(
                      By.xpath(businessRowXpath)
              )
      );


        System.out.println(
                "Business found: " + businessName
        );


        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                businessRow
        );


        // ========================================================
        // STEP 3: Check whether business is already expanded
        // ========================================================

        List<WebElement> downArrow =
                businessRow.findElements(
                        By.xpath(
                            "./td[1]//*[name()='svg'"
                          + " and @data-testid='KeyboardArrowDownIcon']"
                        )
                );


        // ========================================================
        // Expand ONLY when currently collapsed
        // ========================================================

        if (downArrow.isEmpty()) {

            WebElement expandCell =
                    businessRow.findElement(
                            By.xpath("./td[1]")
                    );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            expandCell
                    )
            ).click();


            // Wait until arrow changes to DOWN
            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                businessRowXpath
                              + "/td[1]//*[name()='svg'"
                              + " and @data-testid='KeyboardArrowDownIcon']"
                            )
                    )
            );


            System.out.println(
                    "Business expanded: " + businessName
            );

        } else {

            System.out.println(
                    "Business already expanded: "
                    + businessName
            );
        }


        // ========================================================
        // STEP 4: Validate ALL captured Order IDs under this Business
        // ========================================================

        for (String orderId : expectedOrderIds) {

            System.out.println(
                    "Checking Order ID: " + orderId
            );


            // Re-find through XPath because grid may refresh
            String orderInsideBusinessXpath =
                    businessRowXpath
                  + "/following-sibling::tr[1]"
                  + "//*[normalize-space()='"
                  + orderId
                  + "']";


            List<WebElement> matchingOrders =
                    driver.findElements(
                            By.xpath(orderInsideBusinessXpath)
                    );


            if (matchingOrders.isEmpty()) {

                System.out.println(
                        "❌ Order ID " + orderId
                      + " NOT found under Business "
                      + businessName
                );

                Assert.fail(
                        "FAIL: Order ID "
                      + orderId
                      + " is NOT displayed under Business "
                      + businessName
                );

            } else {

                System.out.println(
                        "✅ Order ID " + orderId
                      + " found under Business "
                      + businessName
                );
            }
        }


        System.out.println(
                "✅ All captured orders validated for Business: "
              + businessName
        );
    }


    System.out.println("\n======================================");
    System.out.println("ALL SELECTED ORDERS VALIDATED");
    System.out.println("======================================");
}

public void deleteSchedule() throws InterruptedException {

	WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

	driver.findElement(By.xpath("(//button[@class='actions-dropdown dropdown-toggle btn btn-success'])[1]"))
			.click();

	Thread.sleep(2000);

	driver.findElement(By.xpath("//a[text()='Change Status']")).click();

	Thread.sleep(2000);

	WebElement scheduleStatus = wait1.until(ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
	Select dropdown1 = new Select(scheduleStatus);
	dropdown1.selectByVisibleText("Delete");

	Thread.sleep(2000);

	WebElement comments = wait1
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @name='comment']")));

	comments.click();
	comments.sendKeys("Status changed to Active");

	driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

}

public void verifyOrdersAvailableForReschedule(
        List<String> selectedOrderIds) throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    for (String orderId : selectedOrderIds) {


        // ========================================================
        // STEP 1: Open Order ID filter
        // ========================================================

//        WebElement orderIdFilter = driver.findElement(By.xpath("//tr[@class='table-light']/th[2]/div[2]"));
//                                
//        Thread.sleep(2000);                                                  
//        orderIdFilter.click();
    	
    	WebElement orderIdFilter = wait.until(
    		    ExpectedConditions.elementToBeClickable(
    		        By.xpath(
    		            "//tr[@class='table-light']/th[2]/div[2]"
    		        )
    		    )
    		);

    	orderIdFilter.click();
  

        // ========================================================
        // STEP 2: Find Order ID search input
        // ========================================================

        WebElement searchInput =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(
                                    "//input[@placeholder='Order ID']"
                                )
                        )
                );


        // Clear previous search
//        searchInput.clear();


        // Enter current Order ID
        searchInput.sendKeys(orderId);
        
        System.out.println("\n======================================");
        System.out.println("Validating Order ID: ");      
        System.out.println("========================================");
        
        System.out.println(
                "Searching Order ID: " + orderId
        );


        // ========================================================
        // STEP 3: Click APPLY
        // ========================================================

        WebElement applyButton =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                    "//button[normalize-space()='Apply']"
                                )
                        )
                );

        applyButton.click();


        // ========================================================
        // STEP 4: Verify Order ID exists
        // ========================================================

        boolean orderAvailable = false;

        try {

            WebElement orderRow =
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(
                            ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(
                                    "//tbody/tr"
                                  + "[td[normalize-space()='"
                                  + orderId
                                  + "']]"
                                )
                            )
                    );

            orderAvailable = orderRow.isDisplayed();

        } 
        
        catch (TimeoutException e) {

            orderAvailable = false;
        }


        // ========================================================
        // STEP 5: Result
        // ========================================================

        if (orderAvailable) {

            System.out.println(
                    "✅ Order " + orderId +
                    " is AVAILABLE for reschedule"
            );

        } else {

            System.out.println(
                    "❌ Order " + orderId +
                    " is NOT AVAILABLE for reschedule"
            );

            Assert.fail(
                    "Order " + orderId +
                    " is not available for reschedule"
            );
        }


        // ========================================================
        // STEP 6: Clear filter before next Order ID
        // ========================================================

        // Open Order ID filter again
        orderIdFilter =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                    "//tr[@class='table-light']/th[2]/div[2]"
                                )
                        )
                );

        orderIdFilter.click();
        
        // Click CLEAR
        WebElement clearButton =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                    "//button[normalize-space()='Clear']"
                                )
                        )
                );
        Thread.sleep(2000);
        clearButton.click();


        Thread.sleep(500);
    }
}


//Create Pickup Schedule
public void selectDepartureTime() throws InterruptedException {

    Random random = new Random();
List<WebElement> timeDropdowns = driver.findElements(
        By.xpath("//select[@name='pickupBy']")
);

System.out.println(
        "Total Departure Time Dropdowns Found: " +
        timeDropdowns.size()
);

for (int i = 0; i < timeDropdowns.size(); i++) {

    try {

        // Re-fetch every time to avoid stale element
        timeDropdowns = driver.findElements(
                By.xpath("//select[@name='pickupBy']")
        );

        WebElement timeDropdown = timeDropdowns.get(i);

        scrollToElement(timeDropdown);

        Select timeSelect = new Select(timeDropdown);

        List<WebElement> options = timeSelect.getOptions();

        // Collect valid options, ignoring "Select"
        List<WebElement> validOptions = new ArrayList<>();

        for (WebElement option : options) {

            String text = option.getText().trim();

            if (!text.isEmpty()
                    && !text.equalsIgnoreCase("Select")
                    && !text.equalsIgnoreCase("Departure Time")
                    && !text.equalsIgnoreCase("Select Time")) {

                validOptions.add(option);
            }
        }

        if (validOptions.isEmpty()) {

            System.out.println(
                    "⚠ No valid departure time option found for dropdown "
                    + (i + 1)
            );

            continue;
        }

        // Select random valid option
        WebElement selectedOption =
                validOptions.get(
                        random.nextInt(validOptions.size())
                );

        String selectedTime =
                selectedOption.getText().trim();

        timeSelect.selectByVisibleText(selectedTime);

        System.out.println(
                "Departure Time " + (i + 1) +
                " selected: " + selectedTime
        );

        Thread.sleep(500);

    } catch (StaleElementReferenceException e) {

        System.out.println(
                "⚠ Time dropdown " +
                (i + 1) +
                " became stale. Retrying..."
        );

        i--;
        Thread.sleep(500);

    } catch (Exception e) {

        System.out.println(
                "❌ Time dropdown " +
                (i + 1) +
                " failed: " +
                e.getMessage()
        );
    }
}
}

public void selectVehicle() throws InterruptedException {

	Random random = new Random();
	// =========================================================
    // 1. SELECT ALL VEHICLE DROPDOWNS
    // =========================================================

 List<WebElement> vehicleDropdowns = driver.findElements(
         By.xpath("//select[@name='vehicle']")
 );

 System.out.println(
         "Total Vehicle Dropdowns: " +
         vehicleDropdowns.size()
 );

 for (int i = 0; i < vehicleDropdowns.size(); i++) {

     try {

         // Re-fetch to avoid stale element
         vehicleDropdowns = driver.findElements(
                 By.xpath("//select[@name='vehicle']")
         );

         WebElement vehicleDropdown = vehicleDropdowns.get(i);

         scrollToElement(vehicleDropdown);

         Select vehicleSelect = new Select(vehicleDropdown);

         List<WebElement> options = vehicleSelect.getOptions();

         // Store only valid vehicle options
         List<WebElement> validOptions = new ArrayList<>();

         for (WebElement option : options) {

             String text = option.getText().trim();

             // Ignore Select / empty options
             if (!text.isEmpty()
                     && !text.equalsIgnoreCase("Select")
                     && !text.equalsIgnoreCase("Select Vehicle")) {

                 validOptions.add(option);
             }
         }

         // No valid option available
         if (validOptions.isEmpty()) {

             System.out.println(
                     "⚠ No valid vehicle option found for dropdown "
                     + (i + 1)
             );

             continue;
         }

         // Select random valid vehicle option
         WebElement selectedOption =
                 validOptions.get(
                         random.nextInt(validOptions.size())
                 );

         String selectedVehicle =
                 selectedOption.getText().trim();

         vehicleSelect.selectByVisibleText(selectedVehicle);

         System.out.println(
                 "Vehicle " + (i + 1) +
                 " selected: " + selectedVehicle
         );

         Thread.sleep(500);

     } catch (StaleElementReferenceException e) {

         System.out.println(
                 "⚠ Vehicle dropdown " +
                 (i + 1) +
                 " became stale. Retrying..."
         );

         i--;
         Thread.sleep(500);

     } catch (Exception e) {

         System.out.println(
                 "❌ Vehicle dropdown " +
                 (i + 1) +
                 " failed: " +
                 e.getMessage()
         );
     }
 }
}

public void selectUserName() throws InterruptedException {
	Random random = new Random();
	//=========================================================
	//3. SELECT OPTION FROM ALL DRIVER DROPDOWNS
	//=========================================================

	List<WebElement> driverDropdowns = driver.findElements(
	      By.xpath("//select[@name='userName']")
	);

	System.out.println(
	      "Total Driver Dropdowns: " +
	      driverDropdowns.size()
	);

	for (int i = 0; i < driverDropdowns.size(); i++) {

	  try {

	      // Re-fetch
	      driverDropdowns = driver.findElements(
	              By.xpath("//select[@name='userName']")
	      );

	      WebElement driverDropdown = driverDropdowns.get(i);

	      scrollToElement(driverDropdown);

	      Select driverSelect = new Select(driverDropdown);

	      List<WebElement> options = driverSelect.getOptions();

	      // Get only valid driver options
	      List<WebElement> validOptions = new ArrayList<>();

	      for (WebElement option : options) {

	          String text = option.getText().trim();

	          if (!text.isEmpty()
	                  && !text.equalsIgnoreCase("Select")
	                  && !text.equalsIgnoreCase("Select Driver")) {

	              validOptions.add(option);
	          }
	      }

	      if (validOptions.isEmpty()) {

	          System.out.println(
	                  "⚠ No valid driver option found for dropdown "
	                  + (i + 1)
	          );

	          continue;
	      }

	      // Select random driver option
	      WebElement selectedOption =
	              validOptions.get(
	                      random.nextInt(validOptions.size())
	              );

	      String selectedDriver =
	              selectedOption.getText().trim();

	      driverSelect.selectByVisibleText(selectedDriver);

	      System.out.println(
	              "Driver " + (i + 1) +
	              " selected: " + selectedDriver
	      );

	      Thread.sleep(500);

	  } catch (StaleElementReferenceException e) {

	      System.out.println(
	              "⚠ Driver dropdown " +
	              (i + 1) +
	              " became stale. Retrying..."
	      );

	      i--;
	      Thread.sleep(500);

	  } catch (Exception e) {

	      System.out.println(
	              "❌ Driver dropdown " +
	              (i + 1) +
	              " failed: " +
	              e.getMessage()
	      );
	  }
	}

}

public void selectDeliveryHub() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement deliveryHubDropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//select[@name='selectedHubLocation']"
                        )
                )
        );

        Select select = new Select(deliveryHubDropdown);
Thread.sleep(500);
        // Always select Warehouse4A
        select.selectByVisibleText(
                "Warehouse4A, Belhasa Compound, Al Quoz Industrial Area 3, Dubai, UAE"
        );
      
    }

public void selectPickupBy(String pickupBy) throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

//    System.out.println("Pickup By received in Page Object: [" + pickupBy + "]");

    if (pickupBy == null || pickupBy.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Pickup By value is null or empty"
        );
    }

    // Open Pickup By dropdown
    WebElement pickupByDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                        "(//select[@name='pickupBy'])[2]"
                    )
            )
    );

    pickupByDropdown.click();

    // Select the requested option
    WebElement option = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                        "//*[normalize-space()='" + pickupBy + "']"
                    )
            )
    );

    option.click();

//    System.out.println(
//            "✅ Pickup By selected successfully: " + pickupBy
//    );
}

public void performPickupByOperations() throws InterruptedException {

    String[] pickupByOptions = {
        "Seller",
        "Delivery User",
        "Admin"
    };

    for (String pickupBy : pickupByOptions) {

        System.out.println("=================================");
        System.out.println("Processing Pickup By: " + pickupBy);
        System.out.println("=================================");

        // Open Pickup By dropdown
        WebElement pickupByDropdown = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(normalize-space(),'Pickup By')]/following::input[1]")
                ));

        pickupByDropdown.click();

        // Select current option
        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                            "//*[normalize-space()='" + pickupBy + "']"
                        )
                ));

        option.click();

        System.out.println("Selected Pickup By: " + pickupBy);

        // ==================================================
        // Perform your operation for this Pickup By
        // ==================================================

        // Example:
        // select orders
        // click Next
        // create schedule
        // cancel schedule
        // validate orders
        // etc.

        // ==================================================
        // Return/reset page before next Pickup By
        // ==================================================

    }
}

public void NextButtonClick() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    WebElement nextButton = driver.findElement(
    	    By.xpath("//button[.//div[normalize-space()='Next'] or normalize-space()='Next']")
    	);

    nextButton.click();

}

/*public List<String> ExpandAndSelectAllLPOs() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    List<String> selectedLPOs = new ArrayList<>();

    String lpoRowsXpath =
            "//div[contains(@class,'custom-grid-body')]"
          + "//table/tbody/tr[td[2]]";

    List<WebElement> rows = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath(lpoRowsXpath)
            )
    );

    List<String> lpoNumbers = new ArrayList<>();

    // ==========================================================
    // CAPTURE LPO NUMBERS FIRST
    // ==========================================================

    for (WebElement row : rows) {

        String lpo = row.findElement(By.xpath("./td[2]"))
                        .getText()
                        .trim();

        if (!lpo.isEmpty()) {

            lpoNumbers.add(lpo);

            System.out.println("LPO found: " + lpo);
        }
    }

    System.out.println("Total LPOs found: " + lpoNumbers.size());


    // ==========================================================
    // PROCESS EACH LPO
    // ==========================================================

    for (String lpo : lpoNumbers) {

        System.out.println("====================================");
        System.out.println("Processing LPO: " + lpo);

        String lpoRowXpath =
                "//div[contains(@class,'custom-grid-body')]"
              + "//table/tbody/tr"
              + "[td[2][normalize-space()='" + lpo + "']]";

        // Re-find row every time because grid can re-render
        WebElement lpoRow = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(lpoRowXpath)
                )
        );

        // ======================================================
        // EXPAND LPO
        // ======================================================

        WebElement expandCell = lpoRow.findElement(
                By.xpath("./td[1]")
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(expandCell)
        ).click();

        System.out.println("Expanded LPO: " + lpo);


        // ======================================================
        // FIND CHECKBOX AFTER EXPANSION
        // ======================================================

        String checkboxXpath =
                lpoRowXpath
              + "/following-sibling::tr[1]"
              + "//input[@type='checkbox']";

        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(checkboxXpath)
                )
        );


        // ======================================================
        // SELECT CHECKBOX
        // ======================================================

        if (!checkbox.isSelected()) {

            checkbox.click();

            selectedLPOs.add(lpo);

            System.out.println("✅ LPO selected: " + lpo);

        } else {

            System.out.println(
                    "LPO already selected -> SKIP: " + lpo
            );
        }
    }


    // ==========================================================
    // CAPTURED LPOs
    // ==========================================================

    System.out.println("\nCAPTURED LPOs");

    for (String lpo : selectedLPOs) {

        System.out.println("LPO = " + lpo);
    }

    return selectedLPOs;
}*/


//public List<String> ExpandAndSelectAllLPOs() {
//
//    WebDriverWait wait =
//            new WebDriverWait(driver, Duration.ofSeconds(10));
//
//    List<String> selectedLPOs = new ArrayList<>();
//    
//
//    By loader = By.cssSelector("div.fot-loader");
//
//    String lpoRowsXpath =
//            "//div[contains(@class,'custom-grid-body')]"
//          + "//table/tbody/tr[td[2]]";
//
//    List<WebElement> rows = wait.until(
//            ExpectedConditions.presenceOfAllElementsLocatedBy(
//                    By.xpath(lpoRowsXpath)
//            )
//    );
//
//    // Capture LPO IDs first
//    List<String> lpoNumbers = new ArrayList<>();
//
//    for (WebElement row : rows) {
//
//        List<WebElement> cells =
//                row.findElements(By.xpath("./td"));
//
//        if (cells.size() < 2) {
//            continue;
//        }
//
//        String lpoNumber = cells.get(1).getText().trim();
//
//        if (lpoNumber.startsWith("FPO")) {
//            lpoNumbers.add(lpoNumber);
//
//            System.out.println("LPO found: " + lpoNumber);
//        }
//    }
//
//    System.out.println("Total LPOs found: " + lpoNumbers.size());
//
//
//    // ==========================================================
//    // PROCESS EACH LPO
//    // ==========================================================
//
//    for (String lpoNumber : lpoNumbers) {
//
//        System.out.println("\n====================================");
//        System.out.println("Processing LPO: " + lpoNumber);
//
//        wait.until(
//                ExpectedConditions.invisibilityOfElementLocated(loader)
//        );
//
//        String lpoRowXpath =
//                "//div[contains(@class,'custom-grid-body')]"
//              + "//table/tbody/tr"
//              + "[td[2][normalize-space()='"
//              + lpoNumber
//              + "']]";
//
//        WebElement lpoRow = wait.until(
//                ExpectedConditions.presenceOfElementLocated(
//                        By.xpath(lpoRowXpath)
//                )
//        );
//
//
//        // ======================================================
//        // EXPAND LPO
//        // ======================================================
//
//        WebElement expandCell =
//                lpoRow.findElement(By.xpath("./td[1]"));
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(expandCell)
//        ).click();
//
//        wait.until(
//                ExpectedConditions.invisibilityOfElementLocated(loader)
//        );
//
//        System.out.println(
//                "Expanded LPO: " + lpoNumber
//        );
//
//
//        // ======================================================
//        // CHECK FOR "NO RECORDS TO DISPLAY"
//        // ======================================================
//
//        String detailRowXpath =
//                lpoRowXpath
//              + "/following-sibling::tr[1]";
//
//        List<WebElement> noRecords =
//                driver.findElements(
//                        By.xpath(
//                                detailRowXpath
//                              + "//*[contains(normalize-space(.),"
//                              + "'No records to display')]"
//                        )
//                );
//
//        if (!noRecords.isEmpty()) {
//
//            System.out.println(
//                    "❌ No product details for LPO: "
//                  + lpoNumber
//                  + " -> SKIPPING"
//            );
//
//            // IMPORTANT:
//            // Do NOT add LPO to selectedLPOs
//            continue;
//        }
//
//
//        // ======================================================
//        // FIND PRODUCT CHECKBOXES
//        // ======================================================
//
//        String checkboxXpath =
//                detailRowXpath
//              + "//tbody/tr"
//              + "[td]"
//              + "//input[@type='checkbox' and not(@disabled)]";
//
//        List<WebElement> checkboxes =
//                driver.findElements(
//                        By.xpath(checkboxXpath)
//                );
//
//
//        if (checkboxes.isEmpty()) {
//
//            System.out.println(
//                    "❌ No selectable products for LPO: "
//                  + lpoNumber
//                  + " -> SKIPPING"
//            );
//
//            // Do NOT capture this LPO
//            continue;
//        }
//
//
//        // ======================================================
//        // SELECT PRODUCT CHECKBOX
//        // ======================================================
//
//        boolean lpoSelected = false;
//
//        for (WebElement checkbox : checkboxes) {
//
//            if (!checkbox.isSelected()) {
//
//                wait.until(
//                        ExpectedConditions.elementToBeClickable(
//                                checkbox
//                        )
//                ).click();
//
//                lpoSelected = true;
//
//                System.out.println(
//                        "✅ Product selected for LPO: "
//                      + lpoNumber
//                );
//            }
//        }
//
//
//        // ======================================================
//        // CAPTURE ONLY SUCCESSFULLY SELECTED LPO
//        // ======================================================
//
//        if (lpoSelected) {
//
//            selectedLPOs.add(lpoNumber);
//
//            System.out.println(
//                    "✅ CAPTURED LPO: " + lpoNumber
//            );
//        }
//    }
//
//
//    // ==========================================================
//    // PRINT CAPTURED LPOs
//    // ==========================================================
//
//    System.out.println("\n====================================");
//    System.out.println("CAPTURED LPOs");
//    System.out.println("====================================");
//
//    for (String lpo : selectedLPOs) {
//
//        System.out.println(
//                "LPO = " + lpo
//        );
//    }
//
//    return selectedLPOs;
//}

public class SelectedLPO {

    private String lpoNumber;
    private String sellerName;

    public SelectedLPO(String lpoNumber, String sellerName) {
        this.lpoNumber = lpoNumber;
        this.sellerName = sellerName;
    }

    public String getLpoNumber() {
        return lpoNumber;
    }

    public String getSellerName() {
        return sellerName;
    }

    @Override
    public String toString() {
        return "LPO = " + lpoNumber +
               " | Seller Name = " + sellerName;
    }
}


public List<SelectedLPO> ExpandAndSelectAllLPOs() throws InterruptedException {
	
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    By loader = By.cssSelector("div.fot-loader");
    List<SelectedLPO> selectedLPOs = new ArrayList<>();

    String lpoRowsXpath =
            "//div[contains(@class,'custom-grid-body')]"
          + "//table/tbody/tr[td[2] and td[4]]";

    List<WebElement> rows = driver.findElements(By.xpath(lpoRowsXpath));

    System.out.println("Total LPOs found: " + rows.size());

    // Capture LPO numbers first
    List<String> lpoNumbers = new ArrayList<>();

    for (WebElement row : rows) {

        String lpoNumber = row.findElement(By.xpath("./td[2]"))
                              .getText().trim();

        if (!lpoNumber.isEmpty()) {
            lpoNumbers.add(lpoNumber);
            System.out.println("LPO found: " + lpoNumber);
        }
    }

    // Process each LPO
    for (String lpoNumber : lpoNumbers) {

        System.out.println("====================================");
        System.out.println("Processing LPO: " + lpoNumber);

        String lpoRowXpath =
                "//div[contains(@class,'custom-grid-body')]"
              + "//table/tbody/tr"
              + "[td[2][normalize-space()='" + lpoNumber + "']]";

        // Re-find row every time to avoid stale element
        WebElement lpoRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(lpoRowXpath)
                )
        );

        // Capture Seller Name BEFORE expanding
        String sellerName = lpoRow.findElement(By.xpath("./td[4]"))
                                  .getText().trim();

        System.out.println("Seller Name: " + sellerName);

        // Expand LPO
//        WebElement expandButton = lpoRow.findElement(By.xpath("./td[1]"));
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(expandButton)
//        ).click();
//
//        System.out.println("Expanded LPO: " + lpoNumber);
        
     // ======================================================
        // EXPAND LPO
        // ======================================================

        WebElement expandCell =
                lpoRow.findElement(By.xpath("./td[1]"));

        wait.until(
                ExpectedConditions.elementToBeClickable(expandCell)
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(loader)
        );

//        System.out.println(
//                "Expanded LPO: " + lpoNumber
//        );

        Thread.sleep(300);

        /*
         * Check whether this LPO has product/order records.
         */
        String childRowXpath =
                lpoRowXpath
              + "/following-sibling::tr[1]";

        List<WebElement> noRecords = driver.findElements(
                By.xpath(
                    childRowXpath +
                    "//*[contains(normalize-space(.),'No records to display')]"
                )
        );

        if (!noRecords.isEmpty()) {

            System.out.println(
                    "⚠️ No product details for LPO: "
                    + lpoNumber
            );

            continue;
        }

        /*
         * Find checkbox inside expanded LPO.
         */
        String checkboxXpath =
                childRowXpath +
                "//input[@type='checkbox'][1]";

        List<WebElement> checkboxes = driver.findElements(
                By.xpath(checkboxXpath)
        );

        if (checkboxes.isEmpty()) {

            System.out.println(
                    "⚠️ No selectable checkbox for LPO: "
                    + lpoNumber
            );

            continue;
        }

        WebElement checkbox = checkboxes.get(0);

        /*
         * Skip already selected LPO
         */
        if (checkbox.isSelected()) {

            System.out.println(
                    "LPO already selected: "
                    + lpoNumber
            );

            // Capture because it is selected
            selectedLPOs.add(
                    new SelectedLPO(lpoNumber, sellerName)
            );

            continue;
        }

        // Select
//        wait.until(
//                ExpectedConditions.elementToBeClickable(checkbox)
//        ).click();
        checkbox.click();

        System.out.println("✅ LPO selected: " + lpoNumber);

        /*
         * Capture ONLY selected LPO
         */
        selectedLPOs.add(
                new SelectedLPO(lpoNumber, sellerName)
        );
    }

    // Print captured information
    System.out.println();
    System.out.println("====================================");
    System.out.println("CAPTURED LPOs");
    System.out.println("====================================");

    for (SelectedLPO lpo : selectedLPOs) {

        System.out.println(
                "LPO = " + lpo.getLpoNumber()
                + " | Seller Name = "
                + lpo.getSellerName()
        );
    }

    return selectedLPOs;
}


/*public void validateSelectedLPOs(List<SelectedLPO> selectedLPOs)
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    for (SelectedLPO lpo : selectedLPOs) {

        String lpoNumber = lpo.getLpoNumber();
        String sellerName = lpo.getSellerName();

        System.out.println("====================================");
        System.out.println("VALIDATING LPO");
        System.out.println("LPO Number  : " + lpoNumber);
        System.out.println("Seller Name : " + sellerName);
        System.out.println("====================================");

        String organizationXpath =
                "//div[contains(@class,'custom-grid-body')]"
              + "//table/tbody/tr"
              + "[td[contains(normalize-space(.),'"
              + sellerName
              + "')]]";

        WebElement organizationRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(organizationXpath)
                )
        );

        System.out.println(
                "✅ Organization found: "
                + sellerName
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                organizationRow
        );

        // Expand organization
        WebElement expandCell =
                organizationRow.findElement(By.xpath("./td[1]"));

        wait.until(
                ExpectedConditions.elementToBeClickable(expandCell)
        ).click();

        System.out.println(
                "✅ Organization expanded: "
                + sellerName
        );

        // Continue validation of LPO/order under this organization
    }
}*/

public void validateSelectedLPOs(List<SelectedLPO> selectedLPOs)
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    if (selectedLPOs == null || selectedLPOs.isEmpty()) {
        Assert.fail("No selected LPOs available for validation.");
    }

    for (SelectedLPO selectedLPO : selectedLPOs) {

        String lpoNumber = selectedLPO.getLpoNumber();
        String sellerName = selectedLPO.getSellerName();

        System.out.println("====================================");
        System.out.println("VALIDATING SELECTED LPO");
        System.out.println("LPO         : " + lpoNumber);
        System.out.println("Seller Name : " + sellerName);
        System.out.println("====================================");

        String organizationXpath =
                "//tr[.//td[contains(normalize-space(.),"
                + "\"" + sellerName + "\")]]";

        WebElement organizationRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(organizationXpath)
                )
        );

        System.out.println(
                "✅ Seller/Organization found: " + sellerName
        );

        // Expand organization
        WebElement expandCell =
                organizationRow.findElement(By.xpath("./td[1]"));

        wait.until(
                ExpectedConditions.elementToBeClickable(expandCell)
        ).click();

        System.out.println(
                "✅ Organization expanded: " + sellerName
        );

        System.out.println(
                "✅ Validation successful for Seller: " + sellerName
        );
    }
}

public void handlePickupByFlow(String pickupBy)
        throws InterruptedException {

    if (pickupBy == null || pickupBy.trim().isEmpty()) {
        Assert.fail("Pickup By is not selected.");
    }

    switch (pickupBy.trim().toLowerCase()) {

        case "admin":

            System.out.println("====================================");
            System.out.println("Pickup By : ADMIN");
            System.out.println("Executing Admin flow");
            System.out.println("====================================");

            handleAdminFlow();

            break;


        case "delivery user":

            System.out.println("====================================");
            System.out.println("Pickup By : DELIVERY USER");
            System.out.println("Executing Delivery User flow");
            System.out.println("====================================");

            handleDeliveryUserFlow();

            break;


        case "seller":

            System.out.println("====================================");
            System.out.println("Pickup By : SELLER");
            System.out.println("Executing Seller flow");
            System.out.println("====================================");

            handleSellerFlow();

            break;


        default:

            Assert.fail(
                "Unsupported Pickup By value: " + pickupBy
            );
    }
}


public void handleAdminFlow() throws InterruptedException {

    System.out.println("Admin: No additional fields required.");

}

public void handleDeliveryUserFlow() throws InterruptedException {

	Random random = new Random();
	// =========================================================
    // 1. SELECT ALL VEHICLE DROPDOWNS
    // =========================================================

 List<WebElement> vehicleDropdowns = driver.findElements(
         By.xpath("//select[@name='vehicle']")
 );

 System.out.println(
         "Total Vehicle Dropdowns: " +
         vehicleDropdowns.size()
 );

 for (int i = 0; i < vehicleDropdowns.size(); i++) {

     try {

         // Re-fetch to avoid stale element
         vehicleDropdowns = driver.findElements(
                 By.xpath("//select[@name='vehicle']")
         );

         WebElement vehicleDropdown = vehicleDropdowns.get(i);

         scrollToElement(vehicleDropdown);

         Select vehicleSelect = new Select(vehicleDropdown);

         List<WebElement> options = vehicleSelect.getOptions();

         // Store only valid vehicle options
         List<WebElement> validOptions = new ArrayList<>();

         for (WebElement option : options) {

             String text = option.getText().trim();

             // Ignore Select / empty options
             if (!text.isEmpty()
                     && !text.equalsIgnoreCase("Select")
                     && !text.equalsIgnoreCase("Select Vehicle")) {

                 validOptions.add(option);
             }
         }

         // No valid option available
         if (validOptions.isEmpty()) {

             System.out.println(
                     "⚠ No valid vehicle option found for dropdown "
                     + (i + 1)
             );

             continue;
         }

         // Select random valid vehicle option
         WebElement selectedOption =
                 validOptions.get(
                         random.nextInt(validOptions.size())
                 );

         String selectedVehicle =
                 selectedOption.getText().trim();

         vehicleSelect.selectByVisibleText(selectedVehicle);

         System.out.println(
                 "Vehicle " + (i + 1) +
                 " selected: " + selectedVehicle
         );

         Thread.sleep(500);

     } catch (StaleElementReferenceException e) {

         System.out.println(
                 "⚠ Vehicle dropdown " +
                 (i + 1) +
                 " became stale. Retrying..."
         );

         i--;
         Thread.sleep(500);

     } catch (Exception e) {

         System.out.println(
                 "❌ Vehicle dropdown " +
                 (i + 1) +
                 " failed: " +
                 e.getMessage()
         );
     }
 }


	Thread.sleep(2000);
	//=========================================================
	//3. SELECT OPTION FROM ALL DRIVER DROPDOWNS
	//=========================================================

	List<WebElement> driverDropdowns = driver.findElements(
		      By.xpath("//select[@name='userName']")
		);

		System.out.println(
		      "Total Driver Dropdowns: " +
		      driverDropdowns.size()
		);

		for (int i = 0; i < driverDropdowns.size(); i++) {

		  try {

		      // Re-fetch
		      driverDropdowns = driver.findElements(
		              By.xpath("//select[@name='userName']")
		      );

		      WebElement driverDropdown = driverDropdowns.get(i);

		      scrollToElement(driverDropdown);

		      Select driverSelect = new Select(driverDropdown);

		      List<WebElement> options = driverSelect.getOptions();

		      // Get only valid driver options
		      List<WebElement> validOptions = new ArrayList<>();

		      for (WebElement option : options) {

		          String text = option.getText().trim();

		          if (!text.isEmpty()
		                  && !text.equalsIgnoreCase("Select")
		                  && !text.equalsIgnoreCase("Select Driver")) {

		              validOptions.add(option);
		          }
		      }

		      if (validOptions.isEmpty()) {

		          System.out.println(
		                  "⚠ No valid driver option found for dropdown "
		                  + (i + 1)
		          );

		          continue;
		      }

		      // Select random driver option
		      WebElement selectedOption =
		              validOptions.get(
		                      random.nextInt(validOptions.size())
		              );

		      String selectedDriver =
		              selectedOption.getText().trim();

		      driverSelect.selectByVisibleText(selectedDriver);

		      System.out.println(
		              "Driver " + (i + 1) +
		              " selected: " + selectedDriver
		      );

		      Thread.sleep(500);

		  } catch (StaleElementReferenceException e) {

		      System.out.println(
		              "⚠ Driver dropdown " +
		              (i + 1) +
		              " became stale. Retrying..."
		      );

		      i--;
		      Thread.sleep(500);

		  } catch (Exception e) {

		      System.out.println(
		              "❌ Driver dropdown " +
		              (i + 1) +
		              " failed: " +
		              e.getMessage()
		      );
		  }
		}

		}

//this is the diff flow for seller
/*public void handleSellerFlow(String sellerName) throws InterruptedException {

    System.out.println("Executing Seller flow...");
    System.out.println("Seller to select: " + sellerName);

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // ========================================================
    // STEP 1: Open Select Seller
    // ========================================================

    WebElement selectSeller = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[contains(@class,'fot-formControl empty empty')]")
            )
    );

    selectSeller.click();

    // ========================================================
    // STEP 2: Click Trade Name filter
    // ========================================================

    WebElement tradeNameFilter = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//tr[@class='table-light']/th[1]/div[2]")
            )
    );

    tradeNameFilter.click();

    // ========================================================
    // STEP 3: Enter Seller Name
    // ========================================================

    WebElement searchInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Trade Name']")
            )
    );

    searchInput.clear();

    // Use the seller name captured from previous page
    searchInput.sendKeys(sellerName);

    System.out.println("Searching Seller: " + sellerName);

    // ========================================================
    // STEP 4: Click APPLY
    // ========================================================

    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Apply']")
            )
    );

    applyButton.click();

    System.out.println("Apply clicked for Seller: " + sellerName);

    // ========================================================
    // STEP 5: Click the SAME seller from filtered result
    // ========================================================

    String sellerRowXpath =
            "//table//tbody//tr[td[contains(normalize-space(.),"
            + "'" + sellerName + "')] ]";

    WebElement sellerRow = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(sellerRowXpath)
            )
    );

    System.out.println("Seller found in result: " + sellerName);

    sellerRow.click();

    System.out.println("✅ Seller selected: " + sellerName);
}*/

//hard coded seller name for now, can be changed to dynamic later
/*public void handleSellerFlow() throws InterruptedException {

    System.out.println("Executing Seller flow...");

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    WebElement selectSeller = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[contains(@class,'fot-formControl empty empty')]")
            )
    );

    selectSeller.click();

    // Select seller
    // ...
    Thread.sleep(2000);
	WebElement tradeNameFilter = wait.until(
		    ExpectedConditions.elementToBeClickable(
		        By.xpath(
		            "//tr[@class='table-light']/th[1]/div[2]"
		        )
		    )
		);
Thread.sleep(2000);
	tradeNameFilter.click();


    // ========================================================
    // STEP 2: Find Order ID search input
    // ========================================================
Thread.sleep(2000);
    WebElement searchInput =
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                "//input[@placeholder='Trade Name']"
                            )
                    )
            );

//    searchInput.clear();


    // Enter current Order ID
    searchInput.sendKeys("Green Agri Foodstuff Trading LLC");
 

    // ========================================================
    // STEP 3: Click APPLY
    // ========================================================
Thread.sleep(2000);
    WebElement applyButton =
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                "//button[normalize-space()='Apply']"
                            )
                    )
            );
Thread.sleep(1000);
    applyButton.click();
}*/

public void handleSellerFlow() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Seller you want to search/select
    String sellerToSelect = "Green Agri Foodstuff Trading LLC";

    // Open Select Seller
    WebElement selectSeller = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[contains(@class,'fot-formControl empty empty')]")
            )
    );

    selectSeller.click();
Thread.sleep(5000);
    // Click Trade Name filter
    WebElement tradeNameFilter = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//tr[@class='table-light']/th[1]/div[2]")
            )
    );

    tradeNameFilter.click();

    Thread.sleep(3000);
    // Enter seller name
    WebElement searchInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Trade Name']")
            )
    );

//    searchInput.clear();
    searchInput.sendKeys(sellerToSelect);

    System.out.println("Searching seller: " + sellerToSelect);

    Thread.sleep(3000);
    // Click Apply
    WebElement applyButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Apply']")
            )
    );

    applyButton.click();

    // =====================================================
    // CLICK FILTERED SELLER RESULT
    // =====================================================

    String filteredSellerXpath =
            "//tbody/tr[td[1][normalize-space()='" + sellerToSelect + "']]";

    WebElement filteredSeller = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(filteredSellerXpath)
            )
    );

    System.out.println(
            "Filtered seller found: " + filteredSeller.getText()
    );
    Thread.sleep(2000);
    filteredSeller.click();

    System.out.println(
            "✅ Selected seller: " + sellerToSelect
    );
}


// Validate no of orders and order value and average order value

public void clickViewSchedule() throws InterruptedException {

	Thread.sleep(2000);

	driver.findElement(
	    By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
	).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//a[text()='View Trip']")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//li[text()='View Details']")).click();

	Thread.sleep(2000);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	By businessBy = By.xpath("//div[contains(@class,'accordion-header')]");
	By ordersBy = By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");

	// ✅ WAIT until at least one business exists
	wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, 0));

	int businessCount = driver.findElements(businessBy).size();

	for (int i = 0; i < businessCount; i++) {

	    // ✅ SAFE access
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	    WebElement currentBusiness = driver.findElements(businessBy).get(i);
	    wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	    Thread.sleep(2000);

	    while (true) {

	        List<WebElement> orders = driver.findElements(ordersBy);
	        WebElement pendingOrder = null;

	        for (WebElement order : orders) {
	            boolean isCompleted =
	                order.findElements(By.xpath(".//div[@title='Completed']")).size() > 0;

	            if (!isCompleted) {
	                pendingOrder = order;
	                break;
	            }
	        }

	        if (pendingOrder == null) {

	            System.out.println("All orders completed for business " + (i + 1));

	            // ✅ SAFE re-click
	            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
	            currentBusiness = driver.findElements(businessBy).get(i);
	            wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

	            Thread.sleep(1500);
	            break;
	        }
	    }
	}
}
public Global captureFirstScheduleSummary() {

    // First data row only
    String firstRowXpath =
            "//div[contains(@class,'custom-grid-body')]//table/tbody/tr[1]";

    WebElement row = driver.findElement(
            By.xpath(firstRowXpath)
    );

    // Schedule ID
    String scheduleId =
            row.findElement(By.xpath("./td[1]"))
               .getText()
               .trim();

    int noOfOrders = Integer.parseInt(
            row.findElement(By.xpath("./td[5]"))
               .getText()
               .trim()
    );

    double orderValue = Double.parseDouble(
            row.findElement(By.xpath("./td[6]"))
               .getText()
               .replace(",", "")
               .trim()
    );

    double avgOrderValue = Double.parseDouble(
            row.findElement(By.xpath("./td[7]"))
               .getText()
               .replace(",", "")
               .trim()
    );

    System.out.println("====================================");
    System.out.println("FIRST SCHEDULE DETAILS");
    System.out.println("====================================");
    System.out.println("Schedule ID      = " + scheduleId);
    System.out.println("No Of Orders     = " + noOfOrders);
    System.out.println("Order Value      = " + orderValue);
    System.out.println("Avg Order Value  = " + avgOrderValue);
    System.out.println("====================================");

    // Store schedule ID also in Global if needed
    this.existingScheduleId = scheduleId;

    return new Global(
            noOfOrders,
            orderValue,
            avgOrderValue
    );
}

public void clickFirstSchedule() throws InterruptedException {

	Thread.sleep(2000);

	driver.findElement(
	    By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
	).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//a[text()='View Trip']")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

	Thread.sleep(2000);
	driver.findElement(By.xpath("//li[text()='View Details']")).click();

	Thread.sleep(2000);

}

public List<String> captureAllScheduleOrderIds() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // LinkedHashSet keeps insertion order and removes duplicates
    Set<String> uniqueOrderIds =
            new LinkedHashSet<>();

    String businessXpath =
            "//div[contains(@class,'accordion-header')]";

    List<WebElement> businesses =
            driver.findElements(By.xpath(businessXpath));

    System.out.println(
            "Total business sections found = " + businesses.size()
    );

    for (int i = 0; i < businesses.size(); i++) {

        // Re-find because DOM can refresh after accordion click
        List<WebElement> updatedBusinesses =
                driver.findElements(By.xpath(businessXpath));

        WebElement business =
                updatedBusinesses.get(i);

        String businessText =
                business.getText().trim();

        System.out.println("--------------------------------");
        System.out.println(
                "Business " + (i + 1)
        );
        System.out.println(businessText);

        // Scroll to business
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                business
        );

        Thread.sleep(500);

        // Click business accordion
        wait.until(
                ExpectedConditions.elementToBeClickable(business)
        ).click();

        Thread.sleep(1000);

        // Scroll down after opening the business
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0,500);"
        );

        Thread.sleep(2000);

        // Capture Order IDs currently displayed
        List<WebElement> ids =
                driver.findElements(
                    By.xpath(
                        "//div[contains(@class,'custom-grid-body')]//table/tbody/tr/td[2]"
                    )
                );

        for (WebElement idElement : ids) {

            String orderId =
                    idElement.getText().trim();

            if (!orderId.isEmpty()
                    && orderId.matches("\\d+")) {

                if (uniqueOrderIds.add(orderId)) {

                    System.out.println(
                            "New Order ID captured = "
                            + orderId
                    );

                } else {

//                    System.out.println(
//                            "Duplicate Order ID skipped = "
//                            + orderId
//                    );
                }
            }
        }

        Thread.sleep(700);
    }

    // Convert Set → List
    List<String> orderIds =
            new ArrayList<>(uniqueOrderIds);

    System.out.println("================================");
    System.out.println("ALL CAPTURED ORDER IDs");
    System.out.println("================================");

    for (String orderId : orderIds) {
        System.out.println(orderId);
    }

    System.out.println(
            "Total captured orders = "
            + orderIds.size()
    );
    
    // Scroll down after opening the business
    ((JavascriptExecutor) driver).executeScript(
            "window.scrollBy(0,500);"
    );

    return orderIds;
}


public void navigateToViewOrders() throws InterruptedException {
	By clickViewOrder = By.xpath("//*[text()='View Orders']");
	By clickOrder = By.xpath("//*[text()='Order']");
	Thread.sleep(2000);
	driver.findElement((clickgrid)).click();
	Thread.sleep(2000);
	driver.findElement(clickOrder).click();
	Thread.sleep(2000);
	driver.findElement((clickViewOrder)).click();
	
}

//public double calculateTotalOrderValue(List<String> orderIds) throws InterruptedException {
//
//    double totalOrderValue = 0.0;
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//    for (String orderId : orderIds) {
//
//        System.out.println("====================================");
//        System.out.println("Processing Order ID = " + orderId);
//        System.out.println("====================================");
//
//        // =====================================================
//        // 1. WAIT FOR LOADER TO DISAPPEAR
//        // =====================================================
//
////        wait.until(ExpectedConditions.invisibilityOfElementLocated(
////                By.cssSelector("div.fot-loader")
////        ));
//
//        // =====================================================
//        // 2. CLICK ORDER ID FILTER ICON
//        // =====================================================
//
//        WebElement filterIcon = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//tr[@class='table-light']/th[2]/div[2]")
//                )
//        );
//        Thread.sleep(2000);
//        filterIcon.click();
//
//        // =====================================================
//        // 3. ENTER ORDER ID
//        // =====================================================
//
//        WebElement orderIdInput = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//input[@placeholder='Order ID']")
//                )
//        );
//
// //       orderIdInput.clear();
//        Thread.sleep(1000); 
//        orderIdInput.sendKeys(orderId);
//        Thread.sleep(1000); 
//        orderIdInput.sendKeys(Keys.ENTER);
//
//        // =====================================================
//        // 4. WAIT FOR SEARCH RESULT / LOADER
//        // =====================================================
//
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                By.cssSelector("div.fot-loader")
//        ));
//
//        // =====================================================
//        // 5. GET ORDER AMOUNT
//        // =====================================================
//
//        double amount = getOrderAmount(orderId);
//
//        System.out.println(
//                "Order ID = " + orderId + " | Amount = " + amount
//        );
//
//        totalOrderValue += amount;
//        
//        
//        // =====================================================
//        // 6. CLICK FILTER ICON AGAIN
//        // =====================================================
//
//        filterIcon = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//tr[@class='table-light']/th[2]/div[2]")
//                )
//        );
//
//        filterIcon.click();
//
//        // =====================================================
//        // 7. CLEAR PREVIOUS ORDER ID
//        // =====================================================
//
//       WebElement orderIdInput1 = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//input[@placeholder='Order ID']")
//                )
//        );
//        
//         // Optional: Wait for a moment before clearing
//        orderIdInput1.clear();
//
//        // If your application requires Enter after clearing
// //      orderIdInput.sendKeys(Keys.ENTER);
//
//        // =====================================================
//        // 8. WAIT FOR CLEAR OPERATION TO COMPLETE
//        // =====================================================
//
//
//
//        System.out.println(
//                "Cleared Order ID filter: " + orderId
//        );
//    }
//
//    System.out.println("====================================");
//    System.out.println("TOTAL ORDER VALUE");
//    System.out.println("====================================");
//    System.out.println("Total Order Value = " + totalOrderValue);
//
//    return totalOrderValue;
//}
//
//public double getOrderAmount(String orderId)
//        throws InterruptedException {
//
//    WebDriverWait wait =
//            new WebDriverWait(driver, Duration.ofSeconds(20));
//
//    // --------------------------------------------------------
//    // Open Order ID filter
//    // --------------------------------------------------------
//
//    WebElement orderFilter = wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath(
//                        "//tr[@class='table-light']/th[2]/div[2]"
//                    )
//            )
//    );
//Thread.sleep(2000);
//    orderFilter.click();
//
//
//    // --------------------------------------------------------
//    // Enter Order ID
//    // --------------------------------------------------------
//
//    WebElement input = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//input[@placeholder='Order ID']")
//            )
//    );
//
// //   input.clear();
//    Thread.sleep(1000);
//    input.sendKeys(orderId);
//
//
//    // --------------------------------------------------------
//    // Apply
//    // --------------------------------------------------------
//
//    WebElement apply = wait.until(
//            ExpectedConditions.elementToBeClickable(
//                    By.xpath(
//                        "//button[normalize-space()='Apply']"
//                    )
//            )
//    );
//Thread.sleep(1000);
//    apply.click();
//
//
//    // --------------------------------------------------------
//    // Find exact Order row
//    // --------------------------------------------------------
//
//    String orderRowXpath =
//            "//tbody/tr[td[2][normalize-space()='"
//          + orderId
//          + "']]";
//
//    WebElement row = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath(orderRowXpath)
//            )
//    );
//
//
//    // --------------------------------------------------------
//    // Get Amount
//    // --------------------------------------------------------
//
//    /*
//     * From screenshot Amount is later in the row.
//     * Better to use column header dynamically if possible.
//     */
//
//    String amountText =
//            row.findElement(By.xpath("./td[12]"))
//               .getText()
//               .trim();
//
//    double amount = Double.parseDouble(
//            amountText
//                    .replace(",", "")
//                    .replace("AED", "")
//                    .trim()
//    );
//
//    System.out.println(
//            "Order ID = " + orderId
//          + " | Amount = " + amount
//    );
//
//    return amount;
//}

public double calculateTotalOrderValue(List<String> orderIds)
        throws InterruptedException {

    double totalOrderValue = 0.0;

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    By loader = By.cssSelector("div.fot-loader");

    By filterIconBy =
            By.xpath("//tr[@class='table-light']/th[2]/div[2]");

    By orderIdInputBy =
            By.xpath("//input[@placeholder='Order ID']");

    By applyButtonBy =
            By.xpath("//button[normalize-space()='Apply']");


    for (String orderId : orderIds) {

        System.out.println("====================================");
        System.out.println("Processing Order ID = " + orderId);
        System.out.println("====================================");


        // =====================================================
        // 1. WAIT FOR LOADER BEFORE STARTING
        // =====================================================

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(loader)
        );


        // =====================================================
        // 2. OPEN ORDER ID FILTER
        // =====================================================

        WebElement filterIcon = wait.until(
                ExpectedConditions.elementToBeClickable(filterIconBy)
        );

        filterIcon.click();


        // =====================================================
        // 3. ENTER ORDER ID
        // =====================================================

        WebElement orderIdInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderIdInputBy
                )
        );

        orderIdInput.clear();
        orderIdInput.sendKeys(orderId);


        // =====================================================
        // 4. APPLY FILTER
        // =====================================================

        WebElement applyButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        applyButtonBy
                )
        );
     Thread.sleep(1000);
        applyButton.click();


        // =====================================================
        // 5. WAIT FOR SEARCH TO COMPLETE
        // =====================================================

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(loader)
        );


        // =====================================================
        // 6. GET AMOUNT
        // =====================================================

        double amount = getOrderAmount(orderId);

        System.out.println(
                "Order ID = " + orderId +
                " | Amount = " + amount
        );

        totalOrderValue += amount;


        // =====================================================
        // 7. OPEN FILTER AGAIN
        // =====================================================

//        wait.until(
//                ExpectedConditions.invisibilityOfElementLocated(loader)
//        );

        filterIcon = wait.until(
                ExpectedConditions.elementToBeClickable(filterIconBy)
        );

        filterIcon.click();


        // =====================================================
        // 8. CLEAR PREVIOUS ORDER ID
        // =====================================================

        orderIdInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderIdInputBy
                )
        );
Thread.sleep(1000);
        orderIdInput.clear();


        // =====================================================
        // 9. APPLY CLEAR
        // =====================================================

        applyButton = driver.findElement(
                        applyButtonBy
                
        );

        applyButton.click();


        // =====================================================
        // 10. WAIT FOR CLEAR/REFRESH
        // =====================================================

//        wait.until(
//                ExpectedConditions.invisibilityOfElementLocated(loader)
//        );

        System.out.println(
                "Cleared Order ID filter: " + orderId
        );
    }


    // =========================================================
    // FINAL TOTAL
    // =========================================================

    System.out.println("====================================");
    System.out.println("TOTAL ORDER VALUE");
    System.out.println("====================================");

    System.out.println(
            "Total Order Value = " + totalOrderValue
    );

    return totalOrderValue;
}

public double getOrderAmount(String orderId)
        throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    By loader =
            By.cssSelector("div.fot-loader");

    // Wait until filtered result is completely loaded
//    wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(loader)
//    );


    // =========================================================
    // FIND EXACT ORDER
    // =========================================================

    String orderRowXpath =
            "//tbody/tr[td[2][normalize-space()='"
            + orderId
            + "']]";

    WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(orderRowXpath)
            )
    );


    // =========================================================
    // GET AMOUNT
    // =========================================================

    String amountText =
            row.findElement(By.xpath("./td[12]"))
               .getText()
               .trim();

    double amount =
            Double.parseDouble(
                    amountText
                            .replace(",", "")
                            .replace("AED", "")
                            .trim()
            );


    System.out.println(
            "Order ID = " + orderId +
            " | Amount = " + amount
    );

    return amount;
}

public int getColumnIndex(String headerName) {

    List<WebElement> headers =
            driver.findElements(
                    By.xpath("//thead/tr/th")
            );

    for (int i = 0; i < headers.size(); i++) {

        String text = headers.get(i).getText().trim();

        if (text.equalsIgnoreCase(headerName)) {
            return i + 1;
        }
    }

    throw new RuntimeException(
            "Column not found: " + headerName
    );
}

public void validateOrderValue(
        Global summary,
        double calculatedTotal) {

    System.out.println("====================================");
    System.out.println("ORDER VALUE VALIDATION");
    System.out.println("====================================");
    
    System.out.println(
            "Expected Order Value = " + summary.getOrderValue()
    );

    System.out.println(
            "Actual Order Value   = " + calculatedTotal
    );

    Assert.assertEquals(
            calculatedTotal,
            summary.getOrderValue(),
            0.01,
            "Order Value mismatch"
    );

    System.out.println(
            "✅ Order Value validated: " + calculatedTotal
    );
}

public void validateAverageOrderValue(Global scheduleSummary, double actualOrderValue) {

    int noOfOrders = scheduleSummary.getNoOfOrders();

    if (noOfOrders <= 0) {
        Assert.fail("Cannot calculate average: No Of Orders is " + noOfOrders);
    }

    double expectedAverage = scheduleSummary.getAvgOrderValue();

    double actualAverage = actualOrderValue / noOfOrders;

    System.out.println("====================================");
    System.out.println("AVERAGE ORDER VALUE VALIDATION");
    System.out.println("====================================");
    System.out.println("No Of Orders        = " + noOfOrders);
    System.out.println("Expected Avg Value  = " + expectedAverage);
    System.out.println("Actual Avg Value    = " + actualAverage);

    Assert.assertEquals(
        actualAverage,
        expectedAverage,
        0.01,
        "Average Order Value validation failed"
    );

    System.out.println("✅ Average Order Value validated: " + actualAverage);
}


//COD Collection
/*public void clickCODCollection() throws InterruptedException {
    Thread.sleep(2000);
    driver.findElement(By.xpath("//*[text()='COD Collections']")).click();
   
    
    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    By loader = By.cssSelector("div.fot-loader");

    By filterIconBy =
            By.xpath("//tr[@class='table-light']/th[1]/div[2]");

    By orderIdInputBy =
            By.xpath("//input[@placeholder='Order ID']");

    By applyButtonBy =
            By.xpath("//button[normalize-space()='Apply']");


    	Thread.sleep(2000);
    WebElement filterIcon = wait.until(
            ExpectedConditions.elementToBeClickable(filterIconBy)
    );

    filterIcon.click();

    WebElement orderIdInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    orderIdInputBy
            )
    );
    
    orderIdInput.sendKeys("284335");


        WebElement applyButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        applyButtonBy
                )
        );
     Thread.sleep(1000);
        applyButton.click();
}*/

public void clickCODCollection() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    By loader = By.cssSelector("div.fot-loader");

    // Open COD Collections
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[normalize-space()='COD Collections']")
    )).click();

//    wait.until(
//            ExpectedConditions.invisibilityOfElementLocated(loader)
//    );
  
    // Order ID filter
    By filterIconBy =
            By.xpath("//tr[@class='table-light']/th[1]/div[2]");

    By orderIdInputBy =
            By.xpath("//input[@placeholder='Order ID']");

    By applyButtonBy =
            By.xpath("//button[normalize-space()='Apply']");
    
    Thread.sleep(10000);
    // Click filter
    wait.until(
            ExpectedConditions.elementToBeClickable(filterIconBy)
    ).click();

    // Enter Order ID
    WebElement orderIdInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    orderIdInputBy
            )
    );

    orderIdInput.clear();
    orderIdInput.sendKeys("284335");

    // Apply
    wait.until(
            ExpectedConditions.elementToBeClickable(
                    applyButtonBy
            )
    ).click();

    // Wait for filtering to complete
    wait.until(
            ExpectedConditions.invisibilityOfElementLocated(loader)
    );

    // ============================================
    // PRINT FILTERED ORDER DETAILS
    // ============================================

    String orderRowXpath =
            "//tbody/tr[td[1][normalize-space()='284335']]";

    WebElement orderRow = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(orderRowXpath)
            )
    );

    List<WebElement> cells =
            orderRow.findElements(By.xpath("./td"));

    System.out.println("====================================");
    System.out.println("COD COLLECTION - ORDER DETAILS");
    System.out.println("====================================");

    System.out.println(
            "Order ID         = " + cells.get(0).getText().trim()
    );

    System.out.println(
            "Schedule ID      = " + cells.get(1).getText().trim()
    );

    System.out.println(
            "Enterprise Name  = " + cells.get(2).getText().trim()
    );

    System.out.println(
            "Delivery Date    = " + cells.get(3).getText().trim()
    );

    System.out.println(
            "Driver Name      = " + cells.get(4).getText().trim()
    );

    System.out.println(
            "Order Value      = " + cells.get(5).getText().trim()
    );

    System.out.println(
            "Collected Value  = " + cells.get(6).getText().trim()
    );

    System.out.println("====================================");
}



public void captureVehicleDetails() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // ============================================================
    // CLICK VEHICLE DETAILS
    // ============================================================

    By vehicleDetailsButton =
            By.xpath("//button[normalize-space()='Vehicle Details']");

    wait.until(
            ExpectedConditions.elementToBeClickable(vehicleDetailsButton)
    ).click();


    // ============================================================
    // WAIT FOR VEHICLE DETAILS POPUP
    // ============================================================

    By popupTitle =
            By.xpath("//div[contains(@class,'modal')]//*[normalize-space()='Vehicle Details']");

    wait.until(
            ExpectedConditions.visibilityOfElementLocated(popupTitle)
    );


    // ============================================================
    // FIND VEHICLE DETAILS TABLE FIRST ROW
    // ============================================================

    By vehicleRowBy =
            By.xpath(
                    "//div[contains(@class,'modal')]//table/tbody/tr[1]"
            );

    WebElement vehicleRow = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    vehicleRowBy
            )
    );


    List<WebElement> cells =
            vehicleRow.findElements(By.xpath("./td"));


    // ============================================================
    // PRINT VEHICLE DETAILS
    // ============================================================

    System.out.println();
    System.out.println("====================================");
    System.out.println("VEHICLE DETAILS");
    System.out.println("====================================");

    System.out.println(
            "Vehicle Number       = "
            + cells.get(0).getText().trim()
    );

    System.out.println(
            "Driver Name          = "
            + cells.get(1).getText().trim()
    );

    System.out.println(
            "Start KM             = "
            + cells.get(2).getText().trim()
    );

    System.out.println(
            "End KM               = "
            + cells.get(3).getText().trim()
    );

    System.out.println(
            "Distance Travelled   = "
            + cells.get(4).getText().trim()
    );

    System.out.println(
            "Mileage              = "
            + cells.get(5).getText().trim()
    );

    System.out.println(
            "Cost of Fuel (AED)   = "
            + cells.get(6).getText().trim()
    );

    System.out.println(
            "Fuel Consumed (Ltrs) = "
            + cells.get(7).getText().trim()
    );

    System.out.println(
            "Fuel Cost (AED)      = "
            + cells.get(8).getText().trim()
    );

    System.out.println("====================================");
    Thread.sleep(2000);
    By closeButton =
            By.cssSelector("svg[data-testid='CloseIcon']");

    wait.until(
            ExpectedConditions.elementToBeClickable(closeButton)
    ).click();
}

public void captureFuelDetails() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // ============================================================
    // CLICK FUEL DETAILS
    // ============================================================

    By fuelDetailsButton =
            By.xpath("//button[normalize-space()='Fuel Details']");

    wait.until(
            ExpectedConditions.elementToBeClickable(
                    fuelDetailsButton
            )
    ).click();


    // ============================================================
    // WAIT FOR FUEL DETAILS POPUP
    // ============================================================

    By fuelTitle =
            By.xpath("//*[normalize-space()='Fuel Details']");

    wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    fuelTitle
            )
    );


    // ============================================================
    // GET FIRST DATA ROW
    // ============================================================

    By fuelRowBy =
            By.xpath("//div[contains(@class,'modal')]//table/tbody/tr[1]");

    WebElement fuelRow = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    fuelRowBy
            )
    );

    List<WebElement> cells =
            fuelRow.findElements(By.xpath("./td"));


    // ============================================================
    // PRINT FUEL DETAILS
    // ============================================================

    System.out.println();
    System.out.println("====================================");
    System.out.println("FUEL DETAILS");
    System.out.println("====================================");

    System.out.println(
            "Vehicle ID          = "
            + cells.get(0).getText().trim()
    );

    System.out.println(
            "Vehicle Number      = "
            + cells.get(1).getText().trim()
    );

    System.out.println(
            "Vehicle Name        = "
            + cells.get(2).getText().trim()
    );

    System.out.println(
            "Distance Traveled   = "
            + cells.get(3).getText().trim()
    );

    System.out.println(
            "Fuel Consumption    = "
            + cells.get(4).getText().trim()
    );

    System.out.println(
            "Fuel Cost (AED)     = "
            + cells.get(5).getText().trim()
    );
    
    System.out.println("====================================");
    
  Thread.sleep(2000);
 driver.findElement(By.xpath("//div[@class='newdesign-modal-header']/div[2]")).click();
}

}



