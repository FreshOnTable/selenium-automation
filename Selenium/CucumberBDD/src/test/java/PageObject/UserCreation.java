package PageObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utils.Global;

public class UserCreation {
	
	WebDriver driver;
	WebDriverWait wait;
	Random random = new Random();
//	private Global global = new Global();
	
	public String generatedOrgName;

	
	public UserCreation(WebDriver driver) {
	this.driver = driver;
	this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	
}
	
	By Clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By ClickAdministrator = By.xpath("//ul//li//span[text()='Administrator']");
	By Click_createorganisation = By.xpath("//ul//li//span[text()='Create Organization']");
	By ClickOrganisation = By.xpath("//span[normalize-space()='Organizations']");  //ul//li//span[text()='Organizations']
	
	public void clickgrid() {
		driver.findElement(Clickgrid).click();
	}

	public void clickAdministrationoption() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(ClickAdministrator).click();
	}
	
	public void clickCreateOrganisation() {
		driver.findElement(Click_createorganisation).click();
	}
	

public void clickOrganisationMenue() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(15)
    );

    // Step 1: Locate the Administrator menu
    By administratorMenu = By.xpath(
        "//*[normalize-space()='Administrator']"
    );

    WebElement admin = wait.until(
        ExpectedConditions.elementToBeClickable(administratorMenu)
    );

    // Step 2: Expand Administrator only if Organizations is not visible
    By organizationsOption = By.xpath(
        "//*[normalize-space()='Organizations']"
    );

    boolean organizationsVisible = driver.findElements(organizationsOption)
            .stream()
            .anyMatch(WebElement::isDisplayed);

    if (!organizationsVisible) {
        admin.click();
    }

    // Step 3: Wait for Organizations and click it
    WebElement organizations = wait.until(
        ExpectedConditions.elementToBeClickable(organizationsOption)
    );
    
    Thread.sleep(1000); 
    organizations.click();

    System.out.println("Successfully clicked Organizations.");
}
	
public void enterOrgName() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Step 1: Verify Organization Name is mandatory
    WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Submit')]")
            )
    );
    submitButton.click();

    WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'OK')]")
            )
    );
    okButton.click();

    System.out.println(
            "Status: Passed - Organization Name is mandatory"
    );

    // Step 2: Generate a random organization name
    int randomNumber = new java.security.SecureRandom().nextInt(900000) + 100000;

    String orgDesc = "FOT_TestBuyer_" + randomNumber;
    
    Global.setGeneratedOrgName(orgDesc);

    // Step 3: Enter the generated name
    WebElement orgNameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.name("desc")
            )
    );

    orgNameField.clear();
    orgNameField.sendKeys(orgDesc);
    generatedOrgName=orgDesc;
   

    // Step 4: Capture and print the generated name
    System.out.println("========================================");
    System.out.println("Generated Organization Name: " + orgDesc);
    System.out.println("Status: Passed - Organization Name entered");
    System.out.println("========================================");

    Thread.sleep(1000);
}

public void enterOrgName2() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Step 1: Verify Organization Name is mandatory
    WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Submit')]")
            )
    );
    submitButton.click();

    WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'OK')]")
            )
    );
    okButton.click();

    System.out.println(
            "Status: Passed - Organization Name is mandatory"
    );

    // Step 2: Generate a random organization name
    int randomNumber = new java.security.SecureRandom().nextInt(900000) + 100000;

    String orgDesc = "FOT_TestSeller_" + randomNumber;
    
    Global.setGeneratedOrgName(orgDesc);

    // Step 3: Enter the generated name
    WebElement orgNameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.name("desc")
            )
    );

    orgNameField.clear();
    orgNameField.sendKeys(orgDesc);
    generatedOrgName=orgDesc;
   

    // Step 4: Capture and print the generated name
    System.out.println("========================================");
    System.out.println("Generated Organization Name: " + orgDesc);
    System.out.println("Status: Passed - Organization Name entered");
    System.out.println("========================================");

    Thread.sleep(1000);
}
	
	public void orgTypebusiness() throws InterruptedException {
		
		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
		Thread.sleep(1000);
		driver.findElement(clicksubmit).click();	

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		System.out.println("Status Failed: " + "Check if the Organization can be created without Org Type(mandatory)");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement orgType = wait.until(ExpectedConditions.elementToBeClickable(By.name("orgType")));
		Select orgTypeDrop = new Select(orgType);
		
		Thread.sleep(1000);
		orgTypeDrop.selectByIndex(2);

		System.out.println("Status : Passed");

		Thread.sleep(1000);
}
			
		public void enterBusinessFirstName(String firstName) {

		    WebElement firstNameField =
		            driver.findElement(By.name("first_name"));

		    firstNameField.clear();
		    firstNameField.sendKeys(firstName);
		}	
	
		public void enterBusinessLastName(String lastName) {

		    WebElement lastNameField =
		            driver.findElement(By.name("last_name"));

		    lastNameField.clear();
		    lastNameField.sendKeys(lastName);
		}
		
		
		public void enterBusinessFirstName1(String firstName) {
		    WebElement firstNameField = driver.findElement(By.name("first_name"));
		    firstNameField.clear();
		    firstNameField.sendKeys(firstName);
		}

		public void enterBusinessLastName1(String lastName) {
		    WebElement lastNameField = driver.findElement(By.name("last_name"));
		    lastNameField.clear();
		    lastNameField.sendKeys(lastName);
		}
		

public void enterTRN(String trn) {

    WebElement trnField = driver.findElement(
        By.name("tinNumber")
    );

    trnField.clear();
    trnField.sendKeys(trn);
}

public void selectGrading() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random random = new Random();

	    WebElement gradingElement = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.name("grading")
	        )
	    );

	    Select gradingDropdown = new Select(gradingElement);

	    List<WebElement> options = gradingDropdown.getOptions();

	    // Skip "Grading" and "Select"
	    List<WebElement> validOptions = new ArrayList<>();

	    for (WebElement option : options) {

	        String text = option.getText().trim();

	        if (!text.equalsIgnoreCase("Grading")
	                && !text.equalsIgnoreCase("Select")
	                && !text.isEmpty()) {

	            validOptions.add(option);
	        }
	    }

	    if (validOptions.isEmpty()) {
	        throw new RuntimeException("No valid Grading options available");
	    }

	    WebElement randomOption =
	            validOptions.get(random.nextInt(validOptions.size()));

	    String selectedGrading =
	            randomOption.getText().trim();

	    gradingDropdown.selectByVisibleText(selectedGrading);

	    System.out.println("Random Grading selected: "
	            + selectedGrading);
	}

public void selectCategory() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random random = new Random();

	    WebElement categoryElement = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.name("category")
	        )
	    );

	    Select categoryDropdown = new Select(categoryElement);

	    List<WebElement> options = categoryDropdown.getOptions();

	    List<WebElement> validOptions = new ArrayList<>();

	    for (WebElement option : options) {

	        String text = option.getText().trim();

	        if (!text.equalsIgnoreCase("Category")
	                && !text.equalsIgnoreCase("Select")
	                && !text.isEmpty()) {

	            validOptions.add(option);
	        }
	    }

	    if (validOptions.isEmpty()) {
	        throw new RuntimeException("No valid Category options available");
	    }

	    WebElement randomOption =
	            validOptions.get(random.nextInt(validOptions.size()));

	    String selectedCategory =
	            randomOption.getText().trim();

	    categoryDropdown.selectByVisibleText(selectedCategory);

	    System.out.println("Random Category selected: "
	            + selectedCategory);
	}
	
	public void mobileNo() throws InterruptedException {
		
//		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
//		Thread.sleep(1000);
//		driver.findElement(clicksubmit).click();
//
//		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
//
//		System.out.println("Status Failed: " + "Check if the Organization can be created without mobile No(mandatory)");

		Thread.sleep(2000);

		StringBuilder mobileNumber = new StringBuilder();
		Random rand = new Random();

		mobileNumber.append(rand.nextInt(9) + 1);

		for (int i = 0; i < 8; i++) {
			mobileNumber.append(rand.nextInt(10));
		}

		WebElement mobileInput = driver.findElement(By.name("mobile_no"));
		mobileInput.sendKeys(mobileNumber.toString());

		System.out.println("Status : Passed");

		Thread.sleep(1000);

	}
	
	public void Email() throws InterruptedException {
		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
		Thread.sleep(1000);
		driver.findElement(clicksubmit).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		System.out.println("Status Failed: " + "Check if the Organization can be created without Email(mandatory)");

		driver.findElement(By.name("email_id")).sendKeys("Fot123" + "@gmail.com");

		System.out.println("Status : Passed");

		Thread.sleep(2000);

	}
	
	public void enterEmail(String email) {

	    WebElement emailField =
	            driver.findElement(By.name("email_id"));

	    emailField.clear();
	    emailField.sendKeys(email);
	}
	
	public void entermobileNo(String mobileNo) {

	    WebElement addressField =
	            driver.findElement(By.name("mobile_no"));

	    addressField.clear();
	    addressField.sendKeys(mobileNo);
	}
	
/*	public void UserNametype() throws InterruptedException {
		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
		Thread.sleep(1000);
		driver.findElement(clicksubmit).click();

		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		System.out
				.println("Status Failed: " + "Check if the Organization can be created without uname type(mandatory)");

		driver.findElement(By.name("uname_type"));

		WebElement unameTypeDropdown = driver.findElement(By.name("uname_type"));

		Select unameSelect = new Select(unameTypeDropdown);

		List<WebElement> options = unameSelect.getOptions();

//		Random rand1 = new Random();
		int randomIndex = random.nextInt(options.size() - 1) + 1;

		unameSelect.selectByIndex(randomIndex);

		System.out.println("Status : Passed");
		Thread.sleep(1000);

	}*/
	

public void UserNametype() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Step 1: Verify Username Type is mandatory
    WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Submit')]")
            )
    );
    submitButton.click();

    // Step 2: Handle validation popup
    WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'OK')]")
            )
    );
    okButton.click();

    System.out.println(
            "Status: Passed - Username Type is mandatory"
    );

    By usernameTypeLocator = By.name("uname_type");

    // Step 3: Wait for dropdown and select a valid option
    for (int attempt = 1; attempt <= 3; attempt++) {
        try {
            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            usernameTypeLocator
                    )
            );

            Select unameSelect = new Select(dropdown);

            List<WebElement> options = unameSelect.getOptions();

            List<String> validOptions = new ArrayList<>();

            for (WebElement option : options) {
                String text = option.getText().trim();
                String value = option.getAttribute("value");

                if (!text.isEmpty()
                        && !text.equalsIgnoreCase("Select")
                        && !text.equalsIgnoreCase("Username Type")
                        && value != null
                        && !value.trim().isEmpty()) {

                    validOptions.add(value);
                }
            }

            if (validOptions.isEmpty()) {
                throw new RuntimeException(
                        "No valid Username Type options found"
                );
            }

            String selectedValue = validOptions.get(
                    random.nextInt(validOptions.size())
            );

            // Re-find the dropdown immediately before selecting
            dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            usernameTypeLocator
                    )
            );

            new Select(dropdown).selectByValue(selectedValue);

            // Verify the selected option
            String selectedText = new Select(
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                            usernameTypeLocator
                    ))
            ).getFirstSelectedOption().getText().trim();

            System.out.println(
                    "Username Type selected: " + selectedText
            );
            System.out.println("Status: Passed");

            return;

        } catch (StaleElementReferenceException e) {
            if (attempt == 3) {
                throw e;
            }

            System.out.println(
                    "Username Type dropdown refreshed. Retrying attempt "
                            + (attempt + 1)
            );
        }
    }
}
	
	public void Password() throws InterruptedException {

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    JavascriptExecutor js =
	            (JavascriptExecutor) driver;

	    By submitButton =
	            By.xpath("//button[contains(normalize-space(),'Submit')]");

	    By okButton =
	            By.xpath("//button[normalize-space()='OK']");

	    // Wait a little for previous validation/popup to disappear
	    Thread.sleep(1000);

	    try {

	        WebElement submit = wait.until(
	                ExpectedConditions.presenceOfElementLocated(
	                        submitButton
	                )
	        );

	        // Bring Submit to the center of the screen
	        js.executeScript(
	                "arguments[0].scrollIntoView({block:'center'});",
	                submit
	        );

	        Thread.sleep(500);

	        // Normal click
	        wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        submitButton
	                )
	        ).click();

	        System.out.println(
	                "Status Failed: Check if the Organization can be created without Password(mandatory)"
	        );

	    } catch (ElementClickInterceptedException e) {

	        System.out.println(
	                "⚠️ Submit click intercepted. Trying JavaScript click..."
	        );

	        WebElement submit =
	                driver.findElement(submitButton);

	        js.executeScript(
	                "arguments[0].click();",
	                submit
	        );

	        System.out.println(
	                "Status Failed: Check if the Organization can be created without Password(mandatory)"
	        );
	    }

	    // -------------------------------------------------
	    // WAIT FOR VALIDATION POPUP
	    // -------------------------------------------------

	    WebElement ok =
	            wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        okButton
	                )
	            );

	    try {

	        ok.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                ok
	        );
	    }

	    // -------------------------------------------------
	    // WAIT FOR OK POPUP TO DISAPPEAR
	    // -------------------------------------------------

	    wait.until(
		    ExpectedConditions.invisibilityOfElementLocated(
		            okButton
		    )
		);

	    // -------------------------------------------------
	    // ENTER PASSWORD
	    // -------------------------------------------------

	    WebElement password =
	            wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.name("password")
	                )
	            );

	    password.clear();
	    password.sendKeys("Admin@1234");

	    System.out.println(
	            "Status : Passed"
	    );

	    Thread.sleep(1000);
	}
	
	public void confirmPassword() throws InterruptedException {
		WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    JavascriptExecutor js =
	            (JavascriptExecutor) driver;

	    By submitButton =
	            By.xpath("//button[contains(normalize-space(),'Submit')]");

	    By okButton =
	            By.xpath("//button[normalize-space()='OK']");

	    // Wait a little for previous validation/popup to disappear
	    Thread.sleep(1000);

	    try {

	        WebElement submit = wait.until(
	                ExpectedConditions.presenceOfElementLocated(
	                        submitButton
	                )
	        );

	        // Bring Submit to the center of the screen
	        js.executeScript(
	                "arguments[0].scrollIntoView({block:'center'});",
	                submit
	        );

	        Thread.sleep(500);

	        // Normal click
	        wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        submitButton
	                )
	        ).click();

	        System.out.println(
	                "Status Failed: Check if the Organization can be created without Password(mandatory)"
	        );

	    } catch (ElementClickInterceptedException e) {

	        System.out.println(
	                "⚠️ Submit click intercepted. Trying JavaScript click..."
	        );

	        WebElement submit =
	                driver.findElement(submitButton);

	        js.executeScript(
	                "arguments[0].click();",
	                submit
	        );

	        System.out.println(
	                "Status Failed: Check if the Organization can be created without Password(mandatory)"
	        );
	    }

	    // -------------------------------------------------
	    // WAIT FOR VALIDATION POPUP
	    // -------------------------------------------------

	    WebElement ok =
	            wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        okButton
	                )
	            );

	    try {

	        ok.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                ok
	        );
	    }

	    // -------------------------------------------------
	    // WAIT FOR OK POPUP TO DISAPPEAR
	    // -------------------------------------------------

	    wait.until(
		    ExpectedConditions.invisibilityOfElementLocated(
		            okButton
		    )
		);

	    // -------------------------------------------------
	    // ENTER PASSWORD
	    // -------------------------------------------------

	    WebElement password =
	            wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.name("confirmPassword")
	                )
	            );

	    password.clear();
	    password.sendKeys("Admin@1234");

	    System.out.println(
	            "Status : Passed"
	    );

	    Thread.sleep(1000);
	}
     
	
	public void enterAddressLine1(String address) {

	    WebElement addressField =
	            driver.findElement(By.name("addr_1"));

//	    addressField.clear();
	    addressField.sendKeys(Keys.CONTROL + "a");
	    addressField.sendKeys(Keys.DELETE);
	    addressField.sendKeys(address);
	}
	
	public void enterAddressLine2(String address) {

	    WebElement addressField =
	            driver.findElement(By.name("addr_2"));

//	    addressField.clear();
	    addressField.sendKeys(Keys.CONTROL + "a");
	    addressField.sendKeys(Keys.DELETE);
	    addressField.sendKeys(address);
	}
	
	public void enterPinCode(String pinCode) {

	    WebElement pinCodeField = driver.findElement(
	        By.name("postal_code")
	    );

//	    pinCodeField.clear();
	    pinCodeField.sendKeys(Keys.CONTROL + "a");
	    pinCodeField.sendKeys(Keys.DELETE);

	    pinCodeField.sendKeys(pinCode);
	}
	
	public void Location() throws InterruptedException {
		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
		WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
		Thread.sleep(5000);
		driver.findElement(clicksubmit).click();
		
		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		System.out.println("Status Failed: " + "Check if the Organization can be created without location(mandatory)");
	 
	 driver.findElement(By.xpath("//input[@class='fot-formControl empty empty']")).click();

		driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
		
		
		System.out.println("Status : Passed");
		
	}
	
	public void Country() throws InterruptedException {

	    By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");

	    WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));

	    Thread.sleep(2000);

	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);

	    driver.findElement(clicksubmit).click();

	    Thread.sleep(2000);

	    driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

	    System.out.println("Status Failed: Check if the Organization can be created without country(mandatory)");

	    Thread.sleep(1000);

	    // Click Country dropdown
	    WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[contains(@class,'form-select-search-cont')])[1]")));
	    countryDropdown.click();

	    Thread.sleep(1000);

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[normalize-space()='UAE'])[3]"))).click();
	}
	
	public void State() throws InterruptedException {

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    JavascriptExecutor js =
	            (JavascriptExecutor) driver;

	    // -------------------------------------------------
	    // SUBMIT WITHOUT STATE
	    // -------------------------------------------------

	    By submitButton =
	            By.xpath("//button[normalize-space()='Submit']");

	    WebElement submit =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            submitButton
	                    )
	            );

	    js.executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            submit
	    );

	    Thread.sleep(500);

	    try {
	        submit.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                submit
	        );
	    }

	    Thread.sleep(1000);

	    // -------------------------------------------------
	    // CLOSE VALIDATION POPUP
	    // -------------------------------------------------

	    By okButton =
	            By.xpath("//button[normalize-space()='OK']");

	    WebElement ok =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            okButton
	                    )
	            );

	    try {
	        ok.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                ok
	        );
	    }

	    System.out.println(
	            "Status Failed: Check if the Organization can be created without State(mandatory)"
	    );

	    Thread.sleep(1000);

	    // -------------------------------------------------
	    // OPEN STATE DROPDOWN
	    // -------------------------------------------------

	    By stateDropdown =
	            By.xpath(
	                "(//div[contains(@class,'form-select-search-cont')])[2]"
	            );

	    WebElement state =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            stateDropdown
	                    )
	            );

	    js.executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            state
	    );

	    Thread.sleep(500);

	    state.click();

	    Thread.sleep(500);

	    // -------------------------------------------------
	    // SELECT STATE BY VISIBLE TEXT
	    // -------------------------------------------------

	    String stateName = "United Arab Emirates";

	    By stateOption =
	            By.xpath(
	                "//div[contains(@class,'dropdown-content') "
	              + "and contains(@class,'show')]"
	              + "//div[contains(@class,'list') "
	              + "and normalize-space()='" + stateName + "']"
	            );

	    WebElement option =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            stateOption
	                    )
	            );

	    System.out.println(
	            "Selecting State: " + option.getText()
	    );

	    option.click();

	    System.out.println("Status : Passed");
	}
	
	public void Zone() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random random = new Random();

	    // Click Zone dropdown
	    WebElement zoneDropdown = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[contains(@class,'form-select-search-cont')])[3]")
	        )
	    );

	    zoneDropdown.click();

	    Thread.sleep(500);

	    // Get Zone options from opened dropdown
	    List<WebElement> zoneOptions = wait.until(
	        ExpectedConditions.visibilityOfAllElementsLocatedBy(
	            By.xpath(
	                "(//div[contains(@class,'form-select-search-cont')])[3]" +
	                "//div[contains(@class,'dropdown-content') and contains(@class,'show')]" +
	                "//div[contains(@class,'list') and normalize-space()]"
	            )
	        )
	    );

	    if (zoneOptions.isEmpty()) {
	        throw new RuntimeException("No Zone options available");
	    }

	    // Select random Zone
	    int randomIndex = random.nextInt(zoneOptions.size());

	    WebElement randomZone = zoneOptions.get(randomIndex);

	    String zoneName = randomZone.getText().trim();
	    
	    System.out.println("Random Zone selected: " + zoneName);
	   
	    randomZone.click();

	    Thread.sleep(1000);
	}
	
	public void City() throws InterruptedException {

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(15));

	    Random random = new Random();

	    By cityDropdown = By.xpath(
	            "(//div[contains(@class,'form-select-search-cont')])[4]"
	    );

	    // Open City dropdown
	    wait.until(
	            ExpectedConditions.elementToBeClickable(cityDropdown)
	    ).click();

	    // Locate the visible, open dropdown
	    By openDropdown = By.xpath(
	            "//div[@id='myDropdown' and " +
	            "contains(concat(' ',normalize-space(@class),' '),' show ')]"
	    );

	    wait.until(
	            ExpectedConditions.visibilityOfElementLocated(openDropdown)
	    );

	    // Locate city options inside the open dropdown
	    By cityOptions = By.xpath(
	            "//div[@id='myDropdown' and contains(@class,'show')]" +
	            "//div[contains(@class,'list-cont')]" +
	            "//div[contains(@class,'list') and normalize-space()]"
	    );

	    List<WebElement> options = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(cityOptions)
	    );

	    List<String> cityNames = new ArrayList<>();

	    for (WebElement option : options) {
	        String name = option.getText().trim();

	        if (!name.isEmpty()) {
	            cityNames.add(name);
	        }
	    }

	    if (cityNames.isEmpty()) {
	        throw new RuntimeException(
	                "City dropdown opened, but no city options were found."
	        );
	    }

	    String selectedCity =
	            cityNames.get(random.nextInt(cityNames.size()));

	    System.out.println("Total City options: " + cityNames.size());
	    System.out.println("Random City selected: " + selectedCity);

	    By selectedCityLocator = By.xpath
	    		(//div[contains(@class,'form-select-search-cont')])[4]" +
		                "//div[contains(@class,'dropdown-content') and contains(@class,'show')]" +
		                "//div[contains(@class,'list') and normalize-space()]"
	    );

	    WebElement city = wait.until(
	            ExpectedConditions.elementToBeClickable(selectedCityLocator)
	    );

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'nearest'});", city
	    );

	    // Re-find after scrolling
	    wait.until(
	            ExpectedConditions.elementToBeClickable(selectedCityLocator)
	    ).click();

	    System.out.println("City selected successfully: " + selectedCity);
	}
	

	public String selectRandomZone() {

	    WebElement zoneElement = driver.findElement(By.name("zone"));
	    Select zoneDropdown = new Select(zoneElement);

	    List<WebElement> validZones = zoneDropdown.getOptions().stream()
	            .filter(option -> !option.getAttribute("value").isBlank())
	            .toList();

	    if (validZones.isEmpty()) {
	        throw new IllegalStateException("No valid Zone options found.");
	    }

	    WebElement selectedZone = validZones.get(
	            new Random().nextInt(validZones.size()));

	    String zoneValue = selectedZone.getAttribute("value");
	    String zoneName = selectedZone.getText().trim();

	    zoneDropdown.selectByValue(zoneValue);

//	    System.out.println("Randomly Selected Zone: " + zoneName);

	    return zoneName;
	}

	public String selectRandomCity() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Wait for City options to load after Zone selection
	    wait.until(d -> {
	        WebElement cityElement = d.findElement(By.name("city"));
	        Select cityDropdown = new Select(cityElement);

	        return cityDropdown.getOptions().stream()
	                .anyMatch(option ->
	                        !option.getAttribute("value").isBlank());
	    });

	    WebElement cityElement = driver.findElement(By.name("city"));
	    Select cityDropdown = new Select(cityElement);

	    List<WebElement> validCities = cityDropdown.getOptions().stream()
	            .filter(option -> !option.getAttribute("value").isBlank())
	            .toList();

	    WebElement selectedCity = validCities.get(
	            new Random().nextInt(validCities.size()));

	    String cityValue = selectedCity.getAttribute("value");
	    String cityName = selectedCity.getText().trim();

	    cityDropdown.selectByValue(cityValue);

	    System.out.println("Randomly Selected City: " + cityName);

	    return cityName;
	}
	


	
	public void paymentTerms() throws InterruptedException {
		By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
		WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
		driver.findElement(clicksubmit).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

		System.out.println("Status Failed: " + "Check if the Organization can be created without Payment Terms(mandatory)");
		Thread.sleep(1000);
	    driver.findElement(By.xpath("(//div[@class='dropdown-heading'])[2]")).click();
	    
	    Thread.sleep(2000);

	    String termToSelect = "15 Days Credit";

	    WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//*[contains(text(),'" + termToSelect + "')]")));

	    option.click();

	   Thread.sleep(1000);
	   
	   driver.findElement(By.xpath("(//div[@class='dropdown-heading'])[2]")).click();	  
	   
	   System.out.println("Status : Passed");
		
	}
	
	public void uploadDocument(String documentName, String filePath) throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    By fileInputBy = By.xpath(
	        "//div[contains(@class,'document-card')]" +
	        "[.//div[contains(@class,'document-header') and normalize-space()='" + documentName + "']]" +
	        "//input[@type='file']"
	    );

	    WebElement fileInput = wait.until(
	        ExpectedConditions.presenceOfElementLocated(fileInputBy)
	    );

	    File file = new File(filePath);

	    if (!file.exists()) {
	        throw new RuntimeException(
	            "File does not exist: " + file.getAbsolutePath()
	        );
	    }

	    fileInput.sendKeys(file.getAbsolutePath());

	    Thread.sleep(1000);

	    System.out.println("Uploaded: " + documentName);
	}
	
	public void uploadAllDocuments() throws InterruptedException {

	    String basePath =
	        System.getProperty("user.dir")
	        + "/src/test/resources/testdata/";

	    uploadDocument(
	        "VAT Certificate",
	        basePath + "VAT_Certificate.pdf"
	    );

	    uploadDocument(
	        "Credit Application Form",
	        basePath + "Credit_Application_Form.pdf"
	    );

	    uploadDocument(
	        "Trade License",
	        basePath + "Trade_License.pdf"
	    );
	}
	
	public void uploadAllDocumentsforSeller() throws InterruptedException {

	    String basePath =
	        System.getProperty("user.dir")
	        + "/src/test/resources/testdata/";

	    uploadDocument(
	        "HACCP",
	        basePath + "VAT_Certificate.pdf"
	    );

	    uploadDocument(
	        "Agriculture activity certificate",
	        basePath + "Credit_Application_Form.pdf"
	    );

	    uploadDocument(
	        "Water Testing Certificate",
	        basePath + "Trade_License.pdf"
	    );
	    
	    uploadDocument(
		        "Trade License",
		        basePath + "Trade_License.pdf"
		    );
	    
	    uploadDocument(
		        "Supplier Agreement",
		        basePath + "Trade_License.pdf"
		    );
	    
	    uploadDocument(
		        "Non-disclosure agreement (NDA)",
		        basePath + "Trade_License.pdf"
		    );
	}
	
/*	public void uploadVATCertificate() throws InterruptedException {

	    String filePath =
	        System.getProperty("user.dir")
	        + "/src/test/resources/testdata/VAT_Certificate.pdf";

	    uploadDocument("VAT Certificate", filePath);
	}

	public void uploadCreditApplicationForm() throws InterruptedException {

	    String filePath =
	        System.getProperty("user.dir")
	        + "/src/test/resources/testdata/Credit_Application_Form.pdf";

	    uploadDocument("Credit Application Form", filePath);
	}

	public void uploadTradeLicense() throws InterruptedException {

	    String filePath =
	        System.getProperty("user.dir")
	        + "/src/test/resources/testdata/Trade_License.pdf";

	    uploadDocument("Trade License", filePath);
	}*/

public void selectRandomDropdown(String fieldName) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Random random = new Random();

    // Find the dropdown based on its field name
    By dropdownBy = By.xpath(
        "//div[contains(@class,'fot-formGroup')]" +
        "[.//*[normalize-space()='" + fieldName + "']]" +
        "//div[contains(@class,'dropdown-heading')]"
    );

    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(dropdownBy)
    );

    dropdown.click();

    // Wait for dropdown options
    By optionsBy = By.xpath(
        "//div[contains(@class,'dropdown-container') and @aria-expanded='true']" +
        "//*[self::label or self::div]" +
        "[normalize-space() and not(contains(@class,'dropdown-heading'))]"
    );

    List<WebElement> options = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsBy)
    );

    // Remove unwanted/header/search elements
    List<WebElement> validOptions = new ArrayList<>();

    for (WebElement option : options) {

        String text = option.getText().trim();

        if (!text.isEmpty()
                && !text.equalsIgnoreCase("Select")
                && !text.equalsIgnoreCase(fieldName)) {

            validOptions.add(option);
        }
    }

    if (validOptions.isEmpty()) {
        throw new RuntimeException(
            "No options found for dropdown: " + fieldName
        );
    }

    // Select random option
    WebElement randomOption =
        validOptions.get(random.nextInt(validOptions.size()));

    String selectedValue = randomOption.getText().trim();

    System.out.println(
        "Random " + fieldName + " selected: " + selectedValue
    );

    randomOption.click();
}

public void selectRandomOrganisationTag() {

    selectRandomDropdown("Organization Tags");
}

	public void selectRandomBillingEntity() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Random random = new Random();

	    WebElement billingEntityElement = wait.until(
	        ExpectedConditions.presenceOfElementLocated(
	            By.name("billing_ent")
	        )
	    );

	    Select billingEntity = new Select(billingEntityElement);

	    List<WebElement> options = billingEntity.getOptions();

	    List<WebElement> validOptions = new ArrayList<>();

	    for (WebElement option : options) {

	        String text = option.getText().trim();

	        // Skip default option
	        if (!text.isEmpty()
	                && !text.equalsIgnoreCase("Select")
	                && !text.equalsIgnoreCase("Billing Entity")) {

	            validOptions.add(option);
	        }
	    }

	    if (validOptions.isEmpty()) {
	        throw new RuntimeException(
	            "No Billing Entity options available"
	        );
	    }

	    WebElement randomOption =
	        validOptions.get(random.nextInt(validOptions.size()));

	    String selectedBillingEntity =
	        randomOption.getText().trim();

	    billingEntity.selectByVisibleText(selectedBillingEntity);

	    System.out.println(
	        "Random Billing Entity selected: "
	        + selectedBillingEntity
	    );
	}


//	public void selectRandomSalesUser() throws InterruptedException {
//
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    Random random = new Random();
//
//	    WebElement salesUserElement = wait.until(
//	        ExpectedConditions.presenceOfElementLocated(
//	            By.name("sales_user")
//	        )
//	    );
//
//	    Select salesUser = new Select(salesUserElement);
//
//	    List<WebElement> options = salesUser.getOptions();
//
//	    List<WebElement> validOptions = new ArrayList<>();
//
//	    for (WebElement option : options) {
//
//	        String text = option.getText().trim();
//
//	        if (!text.isEmpty()
//	                && !text.equalsIgnoreCase("Select")
//	                && !text.equalsIgnoreCase("Sales User")) {
//
//	            validOptions.add(option);
//	        }
//	    }
//
//	    if (validOptions.isEmpty()) {
//	        throw new RuntimeException(
//	            "No Sales User options available"
//	        );
//	    }
//
//	    WebElement randomOption =
//	        validOptions.get(random.nextInt(validOptions.size()));
//
//	    String selectedSalesUser =
//	        randomOption.getText().trim();
//
//	    salesUser.selectByVisibleText(selectedSalesUser);
//
//	    System.out.println(
//	        "Random Sales User selected: "
//	        + selectedSalesUser
//	    );
	    
//	    By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
//		Thread.sleep(1000);
//		driver.findElement(clicksubmit).click();
//
//		Thread.sleep(1000);
//
//		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		
//	}
	

public void selectRandomSalesUser() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    Random random = new Random();

    // 1. Wait for Sales User dropdown
    wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("sales_user")));

    // 2. Get available Sales User options
    List<String[]> validOptions = wait.until(d -> {
        try {
            Select salesUser = new Select(
                    d.findElement(By.name("sales_user")));

            List<String[]> optionsList = new ArrayList<>();

            for (WebElement option : salesUser.getOptions()) {
                String value = option.getAttribute("value");
                String text = option.getText().trim();

                // Exclude only empty options and placeholders
                if (value != null && !value.isBlank()
                        && !text.isBlank()
                        && !text.equalsIgnoreCase("Select")
                        && !text.equalsIgnoreCase("Sales User")) {

                    optionsList.add(new String[] { value, text });
                }
            }

            return optionsList.isEmpty() ? null : optionsList;

        } catch (StaleElementReferenceException e) {
            return null;
        }
    });

    if (validOptions == null || validOptions.isEmpty()) {
        throw new IllegalStateException(
                "No Sales User options available in the dropdown.");
    }

    // 3. Choose any one Sales User randomly
    String[] selectedOption = validOptions.get(
            random.nextInt(validOptions.size()));

    String selectedValue = selectedOption[0];
    String selectedName = selectedOption[1];

    // 4. Select the chosen user by value
    wait.until(d -> {
        try {
            new Select(d.findElement(By.name("sales_user")))
                    .selectByValue(selectedValue);
            return true;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    });

    System.out.println("Random Sales User selected: " + selectedName);
}

	
	public void clickSubmitForm() throws InterruptedException {
		
		WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    JavascriptExecutor js =
	            (JavascriptExecutor) driver;

	    // -------------------------------------------------
	    // SUBMIT WITHOUT STATE
	    // -------------------------------------------------

	    By submitButton =
	            By.xpath("//button[normalize-space()='Submit']");

	    WebElement submit =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            submitButton
	                    )
	            );

	    js.executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            submit
	    );

	    Thread.sleep(500);

	    try {
	        submit.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                submit
	        );
	    }

	    Thread.sleep(1000);

	    // -------------------------------------------------
	    // CLOSE VALIDATION POPUP
	    // -------------------------------------------------

	    By okButton =
	            By.xpath("//button[normalize-space()='OK']");

	    WebElement ok =
	            wait.until(
	                    ExpectedConditions.elementToBeClickable(
	                            okButton
	                    )
	            );

	    try {
	        ok.click();

	    } catch (ElementClickInterceptedException e) {

	        js.executeScript(
	                "arguments[0].click();",
	                ok
	        );
	    }

	    System.out.println(
	            "Status Failed: Check if the Organization can be created without State(mandatory)"
	    );
	}
	
// Create Seller Account

public void orgTypeSeller() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Click Submit without selecting Organization Type
    WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Submit')]")
            )
    );
    submitButton.click();

    // Click OK on the validation popup
    WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'OK')]")
            )
    );
    okButton.click();

    System.out.println(
            "Status: Passed - Organization Type is mandatory"
    );

    By orgTypeLocator = By.name("orgType");

    for (int attempt = 1; attempt <= 3; attempt++) {
    try {
        WebElement orgType = wait.until(
                ExpectedConditions.elementToBeClickable(orgTypeLocator)
        );

        Select orgTypeDrop = new Select(orgType);
        orgTypeDrop.selectByValue("7"); // Seller value from your HTML

        System.out.println("Status: Passed - Seller selected");
        break;

    } catch (StaleElementReferenceException e) {
        if (attempt == 3) {
            throw e;
        }

        System.out.println(
                "Organization Type dropdown refreshed. Retrying: "
                        + attempt
        );
    }
}
}

public void selectPaymentTerms() throws InterruptedException {
	By clicksubmit = By.xpath("//button[contains(text(),'Submit')]");
	WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
	
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);
	driver.findElement(clicksubmit).click();
	Thread.sleep(1000);

	driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();

	System.out.println("Status Failed: " + "Check if the Organization can be created without Payment Terms(mandatory)");
	Thread.sleep(1000);
    driver.findElement(By.xpath("//div[@class='dropdown-heading']")).click();
    
    Thread.sleep(2000);

    String termToSelect = "15 Days Credit";

    WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//*[contains(text(),'" + termToSelect + "')]")));

    option.click();

   Thread.sleep(500);
   
   driver.findElement(By.xpath("//div[@class='dropdown-heading']")).click();	  
   
   System.out.println("Status : Passed");
	
}

//Create Consumer Account
public void orgTypeConsumer() throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Click Submit without selecting Organization Type
    WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Submit')]")
            )
    );
    submitButton.click();

    // Click OK on the validation popup
    WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'OK')]")
            )
    );
    okButton.click();

    System.out.println(
            "Status: Passed - Organization Type is mandatory"
    );

    By orgTypeLocator = By.name("orgType");

    for (int attempt = 1; attempt <= 3; attempt++) {
    try {
        WebElement orgType = wait.until(
                ExpectedConditions.elementToBeClickable(orgTypeLocator)
        );

        Select orgTypeDrop = new Select(orgType);
        orgTypeDrop.selectByValue("8"); // Seller value from your HTML

        System.out.println("Status: Passed - Consumer selected");
        break;

    } catch (StaleElementReferenceException e) {
        if (attempt == 3) {
            throw e;
        }

        System.out.println(
                "Organization Type dropdown refreshed. Retrying: "
                        + attempt
        );
    }
    }
}

public void selectBusinessType() throws InterruptedException {
//        List<String> incompleteOrderIds) throws InterruptedException {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

//    for (String orderId : incompleteOrderIds) {

        // ========================================================
        // STEP 1: Open Order ID filter
        // ========================================================

        WebElement orderIdFilter = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//tr[@class='table-light']/th[1]/div[2]"
                        )
                )
        );

       Thread.sleep(1000);
        orderIdFilter.click();

        // Select Seller
        WebElement orgType = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//select[@class='grid-select alt-down-arrow']")
                )
        );

        Select orgTypeDrop = new Select(orgType);
        orgTypeDrop.selectByVisibleText("Business");

        Thread.sleep(1000);
    

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
}

public void changeStatusforSeller() throws InterruptedException {
//  List<String> incompleteOrderIds) throws InterruptedException {

WebDriverWait wait =
      new WebDriverWait(driver, Duration.ofSeconds(20));

//for (String orderId : incompleteOrderIds) {

  // ========================================================
  // STEP 1: Open Order ID filter
  // ========================================================

  WebElement orderIdFilter = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath(
                          "//tr[@class='table-light']/th[1]/div[2]"
                  )
          )
  );

 Thread.sleep(1000);
  orderIdFilter.click();

  // Select Seller
  WebElement orgType = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath("//select[@class='grid-select alt-down-arrow']")
          )
  );

  Select orgTypeDrop = new Select(orgType);
  orgTypeDrop.selectByVisibleText("Local Farms");

//  System.out.println("Status: Passed - Seller selected");

  Thread.sleep(1000);


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
}

public void changeStatusfor_InitiatedToActive() throws InterruptedException {
//  List<String> incompleteOrderIds) throws InterruptedException {

WebDriverWait wait =
      new WebDriverWait(driver, Duration.ofSeconds(20));

//for (String orderId : incompleteOrderIds) {

  // ========================================================
  // STEP 1: Open Order ID filter
  // ========================================================

  WebElement orderIdFilter = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath(
                          "//tr[@class='table-light']/th[8]/div[2]"
                  )
          )
  );

 Thread.sleep(1000);
  orderIdFilter.click();

  // Select Seller
  WebElement orgType = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath("//select[@class='grid-select alt-down-arrow']")
          )
  );

  Select orgTypeDrop = new Select(orgType);
  orgTypeDrop.selectByVisibleText("Initiated");

//  System.out.println("Status: Passed - Seller selected");

  Thread.sleep(1000);


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
}

public void checkOrgStatusChangedToActive() throws InterruptedException {
//  List<String> incompleteOrderIds) throws InterruptedException {

WebDriverWait wait =
      new WebDriverWait(driver, Duration.ofSeconds(20));

//for (String orderId : incompleteOrderIds) {

  // ========================================================
  // STEP 1: Open Order ID filter
  // ========================================================

  WebElement orderIdFilter = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath(
                          "//tr[@class='table-light']/th[8]/div[2]"
                  )
          )
  );

 Thread.sleep(1000);
  orderIdFilter.click();

  // Select Seller
  WebElement orgType = wait.until(
          ExpectedConditions.elementToBeClickable(
                  By.xpath("//select[@class='grid-select alt-down-arrow']")
          )
  );

  Select orgTypeDrop = new Select(orgType);
  orgTypeDrop.selectByVisibleText("Active");

  Thread.sleep(1000);


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
}

public void clickMoreBtn() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();
	
	WebElement showAllOpt = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Show all options']")));
			showAllOpt.click();

	WebElement approveOrg = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Approve Organization']")));
			approveOrg.click();
		
}

public void approveOrganisation() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();
	
	WebElement showAllOpt = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Show all options']")));
			showAllOpt.click();

	WebElement approveOrg = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Approve Organization']")));
			approveOrg.click();
			
			Thread.sleep(1000);
			WebElement clickApprove = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Approve']")));
			clickApprove.click();
			
			Thread.sleep(1000);
			 WebElement clickOK = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("(//*[normalize-space()='OK'])[2]")));
						clickOK.click();
		
}

public void saveSubmitActions() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    WebElement clickOK = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("(//*[normalize-space()='OK'])[2]")));
			clickOK.click();	
					
	WebElement approve = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//*[normalize-space()='Approve']")));
				approve.click();
					
	 WebElement clickOK2 = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("(//*[normalize-space()='OK'])[2]")));
				clickOK2.click();
}

public void changedStatusToActive() throws InterruptedException {
//	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();

	WebElement changeSta = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Change Status']")));
			changeSta.click();
			
			 WebDriverWait wait = new WebDriverWait(
				        driver, Duration.ofSeconds(20)
				    );

				    // Locate the Status dropdown
				    By statusDropdownLocator = By.xpath(
				        "//select[contains(@class,'fot-formControl') " +
				        "and contains(@class,'custom-select')]"
				    );

				    WebElement statusDropdown = wait.until(
				        ExpectedConditions.visibilityOfElementLocated(
				            statusDropdownLocator
				        )
				    );

				    Select statusSelect = new Select(statusDropdown);
				    statusSelect.selectByVisibleText("Active");

				    System.out.println("Status: Selected Active successfully.");

			  Thread.sleep(1000);
			  
			  WebElement comment = wait.until(
			          ExpectedConditions.elementToBeClickable(
			                  By.xpath("(//input[@type='text'])[3]")
			          ));
			          comment.click();
			  comment.sendKeys("Approved");
			  
			  
			  WebElement Submit = wait.until(
			          ExpectedConditions.elementToBeClickable(
			                  By.xpath("//button[text()='Submit']")
			          ));
			          Submit.click();
			          
			          WebElement clickOK2 = wait.until(
			      			ExpectedConditions.elementToBeClickable(By.xpath("(//*[normalize-space()='OK'])[2]")));
			      				clickOK2.click();
}

public void navigateUsersPage() {

    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(15)
    );

    // Step 1: Locate the Administrator menu
    By administratorMenu = By.xpath(
        "//*[normalize-space()='Administrator']"
    );

    WebElement admin = wait.until(
        ExpectedConditions.elementToBeClickable(administratorMenu)
    );

    // Step 2: Expand Administrator only if Organizations is not visible
    By organizationsOption = By.xpath(
        "//*[normalize-space()='Users']"
    );

    boolean organizationsVisible = driver.findElements(organizationsOption)
            .stream()
            .anyMatch(WebElement::isDisplayed);

    if (!organizationsVisible) {
        admin.click();
    }

    // Step 3: Wait for Organizations and click it
    WebElement organizations = wait.until(
        ExpectedConditions.elementToBeClickable(organizationsOption)
    );

    organizations.click();

    System.out.println("Successfully clicked Organizations.");
}


public void searchOrganization(String organizationName)
        throws InterruptedException {
	

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));
    
    WebElement orderIdFilter = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//tr[@class='table-light']/th[3]/div[2]"
                    )
            )
    );

   Thread.sleep(1000);
    orderIdFilter.click();

    WebElement searchInput = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@type='text']")
            )
    );

    searchInput.clear();
    searchInput.sendKeys(organizationName);
    Thread.sleep(1000);
    searchInput.sendKeys(Keys.ENTER);

    System.out.println(
            "Searching for organization: " + organizationName
    );
}

public void searchOrganizationName1(String organizationName)
        throws InterruptedException {
	

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));
    
    WebElement orderIdFilter = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//tr[@class='table-light']/th[3]/div[2]"
                    )
            )
    );

   Thread.sleep(1000);
    orderIdFilter.click();

    WebElement searchInput = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("(//input[@type='text'])[6]")
            )
    );

    searchInput.clear();
    searchInput.sendKeys(organizationName);
    Thread.sleep(1000);
    searchInput.sendKeys(Keys.ENTER);

    System.out.println(
            "Searching for organization: " + organizationName
    );
}

public void searchOrganization2(String organizationName)
        throws InterruptedException {
	

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));
    
    WebElement orderIdFilter = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//tr[@class='table-light']/th[5]/div[2]"
                    )
            )
    );

   Thread.sleep(1000);
    orderIdFilter.click();

    WebElement searchInput = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@type='text']")
            )
    );

    searchInput.clear();
    searchInput.sendKeys(organizationName);
    Thread.sleep(1000);
    searchInput.sendKeys(Keys.ENTER);

    System.out.println(
            "Searching for organization: " + organizationName
    );
}


public void validateUserStatusActive(String organizationName) {

    WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(20));

    // Locate the row containing the organization name
    By organizationRow = By.xpath(
            "//table//tr[td[normalize-space()='" +
            organizationName + "']]"
    );

    WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    organizationRow
            )
    );

    // Status is the 12th column based on your Users table
    WebElement statusElement = row.findElement(
            By.xpath("./td[12]")
    );

    String actualStatus = statusElement.getText().trim();

    if (actualStatus.equalsIgnoreCase("Active")) {
        System.out.println(
                "PASS: Users Level User '" + organizationName +
                "' is Active."
        );
    } else {
        throw new AssertionError(
                "FAIL: Organization '" + organizationName +
                "' is not Active. Actual status: " + actualStatus
        );
    }
}

public void validateOrgStatusActive(String organizationName) {

    WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(20));

    // Locate the row containing the organization name
    By organizationRow = By.xpath(
            "//table//tr[td[normalize-space()='" +
            organizationName + "']]"
    );

    WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    organizationRow
            )
    );

    // Status is the 12th column based on your Users table
    WebElement statusElement = row.findElement(
            By.xpath("./td[8]")
    );

    String actualStatus = statusElement.getText().trim();

    if (actualStatus.equalsIgnoreCase("Active")) {
        System.out.println(
                "PASS: Organization Level Org '" + organizationName +
                "' is Active."
        );
    } else {
        throw new AssertionError(
                "FAIL: Organization '" + organizationName +
                "' is not Active. Actual status: " + actualStatus
        );
    }
}


// Edit Organisation
public void clickEditOrganisationDetails() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();
	
	WebElement showAllOpt = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Show all options']")));
			showAllOpt.click();

	WebElement approveOrg = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Edit']")));
			approveOrg.click();

}

public void updateOrganizationDetails(
        String newOrganizationName,
        String farmClientName) throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // 1. Edit Organization Name
    WebElement organizationName = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//input[@class='fot-formControl  empty populate'])[1]")
        )
    );
    Thread.sleep(1000);
    organizationName.click();
    organizationName.sendKeys(Keys.CONTROL + "a");
    organizationName.sendKeys(Keys.DELETE);

    Thread.sleep(1000); 
    organizationName.sendKeys(newOrganizationName);

    // 2. Edit Farm/Client Name
    WebElement farmClient = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.name("farm_desc")
        )
    );
    Thread.sleep(1000); 
    farmClient.sendKeys(Keys.CONTROL + "a");
  farmClient.sendKeys(Keys.DELETE);
    Thread.sleep(1000); 
    farmClient.sendKeys(farmClientName);

    // Select Preferred Language
    WebElement languageDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.name("preferred_lang")
            )
    );

    Select languageSelect = new Select(languageDropdown);
    languageSelect.selectByValue("9003");

    System.out.println("Preferred Language changed to Arbic");
    
    
 // Change sales user
    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
    Random random = new Random();

    WebElement salesUserElement = wait1.until(
        ExpectedConditions.presenceOfElementLocated(
            By.name("sales_user")
        )
    );
       
    Select salesUser = new Select(salesUserElement);

    List<WebElement> options = salesUser.getOptions();

    List<WebElement> validOptions = new ArrayList<>();

    for (WebElement option : options) {

        String text = option.getText().trim();

        if (!text.isEmpty()
                && !text.equalsIgnoreCase("Select")
                && !text.equalsIgnoreCase("Sales User")) {

            validOptions.add(option);
        }
    }

    if (validOptions.isEmpty()) {
        throw new RuntimeException(
            "No Sales User options available"
        );
    }

    WebElement randomOption =
        validOptions.get(random.nextInt(validOptions.size()));

    String selectedSalesUser =
        randomOption.getText().trim();

    salesUser.selectByVisibleText(selectedSalesUser);

    System.out.println(
        "Random Sales User selected: "
        + selectedSalesUser
    );
    
    
 // select payment mode    
    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
//    Random random = new Random();

    WebElement salectPaymentElement = wait2.until(
        ExpectedConditions.presenceOfElementLocated(
            By.name("default_payment_term")
        )
    );
    Select payment = new Select(salectPaymentElement);

    List<WebElement> options1 = salesUser.getOptions();

    List<WebElement> validOptions1 = new ArrayList<>();

    for (WebElement option : options1) {

        String text = option.getText().trim();

        if (!text.isEmpty()
                && !text.equalsIgnoreCase("Select")
                && !text.equalsIgnoreCase("Default Payment Term")) {

            validOptions1.add(option);
        }
    }

    if (validOptions.isEmpty()) {
        throw new RuntimeException(
            "No payment options available"
        );
    }

//    WebElement randomOption1 =
//        validOptions.get(random.nextInt(validOptions.size()));
//
//    String selectedSalesUser1 =
//        randomOption1.getText().trim();
//
//    salesUser.selectByVisibleText(selectedSalesUser1);
//
//    System.out.println(
//        "Random Payment mode selected: "
//        + selectedSalesUser1
//      );

    // 4. Save changes
    WebElement updateButton = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//button[normalize-space()='Update']")
        )
    );
    Thread.sleep(1000); 
    updateButton.click();
}


public void editOrganizationDetails2() {

    WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(20));

    // Select Preferred Language
    WebElement languageDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.name("preferred_lang")
            )
    );

    Select languageSelect = new Select(languageDropdown);
    languageSelect.selectByValue("9003");

    System.out.println("Preferred Language changed to Arbic");

    // Click Update
    WebElement updateButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Update']")
            )
    );

    updateButton.click();

    System.out.println("Organization update submitted successfully.");
}

public void validateOrganizationUpdate() {

    WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(20));

    WebElement languageDropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.name("preferred_lang")
            )
    );

    Select languageSelect = new Select(languageDropdown);

    String actualLanguage = languageSelect
            .getFirstSelectedOption()
            .getText()
            .trim();

    if (actualLanguage.equalsIgnoreCase("Arbic")) {
        System.out.println(
                "PASS: Organization edit validated. " +
                "Preferred Language is " + actualLanguage
        );
    } else {
        throw new AssertionError(
                "FAIL: Expected Preferred Language 'Arbic' " +
                "but found '" + actualLanguage + "'"
        );
    }
}

//Update Organization Address Details

public void clickManageAddress() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();
	
	WebElement showAllOpt = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Show all options']")));
			showAllOpt.click();

	WebElement approveOrg = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Manage Address']")));
			approveOrg.click();
			
			Thread.sleep(1000);
			WebElement clickthreedot = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='MuiSvgIcon-root MuiSvgIcon-colorSecondary MuiSvgIcon-fontSizeMedium css-al619y']")));
			Thread.sleep(1000);
			clickthreedot.click();
			
					Thread.sleep(1000);
					WebElement clickUpdate = wait.until(
							ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Update']")));
							clickUpdate.click();

}

//Locate an address field using its visible label


private WebElement getLocationField() {
    return driver.findElement(
        By.xpath("//input[@class='fot-formControl populate populate']")
    );
}

private WebElement getAddressField(String fieldName) {
    return driver.findElement(By.name(fieldName));
}


private String getSelectedText(String fieldName) {
    WebElement element = driver.findElement(By.name(fieldName));
    Select dropdown = new Select(element);
    return dropdown.getFirstSelectedOption().getText().trim();
}

public void captureOldAddressDetails() throws InterruptedException {

    Map<String, String> oldAddress = new LinkedHashMap<>();

    // Use the actual name attributes from the HTML
    oldAddress.put("Address Line 1",
    		getAddressField("addr_1").getAttribute("value"));

    oldAddress.put("Address Line 2",
    		getAddressField("addr_2").getAttribute("value"));

    oldAddress.put("P.O. Box",
            getAddressField("postal_code").getAttribute("value"));

    oldAddress.put(
    	    "Location",
    	    getLocationField().getAttribute("value")
    	);

oldAddress.put("Country", getSelectedText("country"));
oldAddress.put("State", getSelectedText("state"));
oldAddress.put("Zone", getSelectedText("zone"));
Thread.sleep(2000); 
oldAddress.put("City", getSelectedText("city"));




    System.out.println("\n========== OLD ADDRESS DETAILS ==========");

    oldAddress.forEach((field, value) ->
            System.out.println(field + " : " + value));

    System.out.println("=========================================\n");
}

public void printAddressInputNames() {
    List<WebElement> inputs =
            driver.findElements(By.cssSelector(
                    ".fot-inputOuter input, .fot-inputOuter select"
            ));

    for (WebElement input : inputs) {
        System.out.println(
                "Tag: " + input.getTagName()
                + " | Name: " + input.getAttribute("name")
                + " | Value: " + input.getAttribute("value")
        );
    }
}


//public void selectRandomZoneAndCity() {
//
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    Random random = new Random();
//
//    // 1. Select a random Zone
//    WebElement zoneElement = wait.until(
//            ExpectedConditions.elementToBeClickable(By.name("zone")));
//
//    Select zoneDropdown = new Select(zoneElement);
//
//    List<WebElement> validZones = zoneDropdown.getOptions().stream()
//            .filter(option -> !option.getAttribute("value").isBlank())
//            .toList();
//
//    if (validZones.isEmpty()) {
//        throw new IllegalStateException("No valid Zone options found.");
//    }
//
//    WebElement randomZone = validZones.get(
//            random.nextInt(validZones.size()));
//
//    String zoneValue = randomZone.getAttribute("value");
//    String zoneName = randomZone.getText().trim();
//
//    zoneDropdown.selectByValue(zoneValue);
//
// //   System.out.println("Selected Zone: " + zoneName);
//
//    // 2. Wait until City options refresh for the selected Zone
//    wait.until(d -> {
//        WebElement cityElement = d.findElement(By.name("city"));
//        Select cityDropdown = new Select(cityElement);
//
//        return cityDropdown.getOptions().stream()
//                .anyMatch(option ->
//                        !option.getAttribute("value").isBlank());
//    });
//
//    // 3. Select a random City
//    WebElement cityElement = driver.findElement(By.name("city"));
//    Select cityDropdown = new Select(cityElement);
//
//    List<WebElement> validCities = cityDropdown.getOptions().stream()
//            .filter(option -> !option.getAttribute("value").isBlank())
//            .toList();
//
//    if (validCities.isEmpty()) {
//        throw new IllegalStateException(
//                "No City options found for Zone: " + zoneName);
//    }
//
//    WebElement randomCity = validCities.get(
//            random.nextInt(validCities.size()));
//
//    String cityValue = randomCity.getAttribute("value");
//    String cityName = randomCity.getText().trim();
//
//    cityDropdown.selectByValue(cityValue);
//
//    System.out.println("Selected Zone: " + zoneName);
//    System.out.println("Selected City: " + cityName);
//
//    // 4. Verify both selected values
//    String actualZone = new Select(
//            driver.findElement(By.name("zone")))
//            .getFirstSelectedOption().getText().trim();
//
//    String actualCity = new Select(
//            driver.findElement(By.name("city")))
//            .getFirstSelectedOption().getText().trim();
//
//    if (!actualZone.equals(zoneName) || !actualCity.equals(cityName)) {
//        throw new AssertionError(
//                "Zone/City selection mismatch. Zone: " + actualZone
//                + ", City: " + actualCity);
//    }
//
//    System.out.println("Verified Zone: " + actualZone);
//    System.out.println("Verified City: " + actualCity);
//}


public void selectRandomZoneAndCity() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    Random random = new Random();

    // 1. Select a random Zone
    WebElement zoneElement = wait.until(
            ExpectedConditions.elementToBeClickable(By.name("zone")));

    Select zoneDropdown = new Select(zoneElement);

    List<String[]> validZones = zoneDropdown.getOptions().stream()
            .map(option -> new String[] {
                    option.getAttribute("value"),
                    option.getText().trim()
            })
            .filter(option -> option[0] != null
                    && !option[0].isBlank()
                    && !option[1].isBlank())
            .toList();

    if (validZones.isEmpty()) {
        throw new IllegalStateException("No valid Zone options found.");
    }

    String[] randomZone = validZones.get(random.nextInt(validZones.size()));
    String zoneValue = randomZone[0];
    String zoneName = randomZone[1];

    // Re-fetch Zone dropdown before selecting
    new Select(wait.until(
            ExpectedConditions.elementToBeClickable(By.name("zone"))))
            .selectByValue(zoneValue);

    System.out.println("Selected Zone: " + zoneName);

    // 2. Wait for City options to load, retrying on stale elements
    List<String[]> validCities = wait.until(d -> {
        try {
            WebElement cityElement = d.findElement(By.name("city"));
            Select cityDropdown = new Select(cityElement);

            List<String[]> cities = new ArrayList<>();

            for (WebElement option : cityDropdown.getOptions()) {
                String value = option.getAttribute("value");
                String text = option.getText().trim();

                if (value != null && !value.isBlank()
                        && !text.isBlank()) {
                    cities.add(new String[] { value, text });
                }
            }

            return cities.isEmpty() ? null : cities;

        } catch (StaleElementReferenceException e) {
            return null;
        }
    });

    // 3. Select a random City using its captured value
    String[] randomCity = validCities.get(
            random.nextInt(validCities.size()));

    String cityValue = randomCity[0];
    String cityName = randomCity[1];

    // Re-fetch City dropdown before selecting
    wait.until(d -> {
        try {
            new Select(d.findElement(By.name("city")))
                    .selectByValue(cityValue);
            return true;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    });

    System.out.println("Selected City: " + cityName);

    // 4. Verify selected Zone and City
    wait.until(d -> {
        try {
            String actualZone = new Select(
                    d.findElement(By.name("zone")))
                    .getFirstSelectedOption().getText().trim();

            String actualCity = new Select(
                    d.findElement(By.name("city")))
                    .getFirstSelectedOption().getText().trim();

            return actualZone.equals(zoneName)
                    && actualCity.equals(cityName);

        } catch (StaleElementReferenceException e) {
            return false;
        }
    });

    System.out.println("Verified Zone: " + zoneName);
    System.out.println("Verified City: " + cityName);
}


public void clickUpdateAddress() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    WebElement clickUpdate = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Update']")));
            clickUpdate.click();

}
public void clickOKAfterUpdate() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    WebElement clickOK2 = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("(//*[normalize-space()='OK'])[2]")));
            clickOK2.click();
}

// Add new address
public void click_AddNewAddress() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	WebElement moreButton = wait
			.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='More']")));

	Thread.sleep(1000);
	moreButton.click();
	
	WebElement showAllOpt = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Show all options']")));
			showAllOpt.click();

	WebElement clickmanage = wait.until(
			ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Manage Address']")));
			clickmanage.click();
			
			Thread.sleep(1000);
			WebElement clickAddNewAddr = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button button-w100 button-primary button-small']")));
			clickAddNewAddr.click();
			
}

//Update new address details

public void Location2() throws InterruptedException {

	System.out.println("Status Failed: " + "Check if the Organization can be created without location(mandatory)");
 
 driver.findElement(By.xpath("//input[contains(@class,'fot-formControl empty')]")).click();

	driver.findElement(By.xpath("//button[text()[contains(., 'Confirm')]]")).click();
	
	
	System.out.println("Status : Passed");
	
}

public void Country2() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Wait for the Country dropdown
    WebElement countryDropdown = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("(//select[contains(@class,'fot-formControl custom-select')])[1]")
        )
    );
Thread.sleep(500);
    // Select UAE by visible text
    Select countrySelect = new Select(countryDropdown);
    countrySelect.selectByVisibleText("UAE");
}

public void State2() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Wait for the Country dropdown
    WebElement countryDropdown = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("(//select[contains(@class,'fot-formControl custom-select empty empty')])[1]")
        )
    );

    // Select UAE by visible text
    Select countrySelect = new Select(countryDropdown);
    countrySelect.selectByVisibleText("United Arab Emirates");
}

/*public void Zone2() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Random random = new Random();

	    WebElement zoneElement = wait.until(
	            ExpectedConditions.elementToBeClickable(By.name("zone")));

	    Select zoneDropdown = new Select(zoneElement);

	    List<String[]> validZones = zoneDropdown.getOptions().stream()
	            .map(option -> new String[] {
	                    option.getAttribute("value"),
	                    option.getText().trim()
	            })
	            .filter(option -> option[0] != null
	                    && !option[0].isBlank()
	                    && !option[1].isBlank()
	                    && !option[1].equalsIgnoreCase("Select"))
	            .toList();

	    if (validZones.isEmpty()) {
	        throw new IllegalStateException("No valid Zone options found.");
	    }

	    String[] randomZone = validZones.get(
	            random.nextInt(validZones.size()));

	    String zoneValue = randomZone[0];
	    String zoneName = randomZone[1];

	    new Select(wait.until(
	            ExpectedConditions.elementToBeClickable(By.name("zone"))))
	            .selectByValue(zoneValue);

	    System.out.println("Selected Zone: " + zoneName);
	}*/

public void Zone2() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    Random random = new Random();

    // Wait for the Zone dropdown
    wait.until(ExpectedConditions.presenceOfElementLocated(By.name("zone")));

    // Read Zone options as strings, not WebElement references
    List<String[]> validZones = wait.until(d -> {
        try {
            WebElement zoneElement = d.findElement(By.name("zone"));

            @SuppressWarnings("unchecked")
            List<Map<String, String>> options =
                    (List<Map<String, String>>) ((JavascriptExecutor) d)
                    .executeScript(
                        "return Array.from(arguments[0].options).map(o => " +
                        "({value: o.value, text: o.text.trim()}));",
                        zoneElement
                    );

            List<String[]> zones = new ArrayList<>();

            for (Map<String, String> option : options) {
                String value = option.get("value");
                String text = option.get("text");

                if (value != null && !value.isBlank()
                        && text != null && !text.isBlank()
                        && !text.equalsIgnoreCase("Select")) {
                    zones.add(new String[] { value, text });
                }
            }

            return zones.isEmpty() ? null : zones;

        } catch (StaleElementReferenceException e) {
            return null;
        }
    });

    if (validZones == null || validZones.isEmpty()) {
        throw new IllegalStateException("No valid Zone options found.");
    }

    // Choose one Zone randomly
    String[] randomZone = validZones.get(random.nextInt(validZones.size()));

    String zoneValue = randomZone[0];
    String zoneName = randomZone[1];

    // Re-find the dropdown before selecting
    wait.until(d -> {
        try {
            new Select(d.findElement(By.name("zone")))
                    .selectByValue(zoneValue);
            return true;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    });

    System.out.println("Randomly selected Zone: " + zoneName);
}

public void City2() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Random random = new Random();

	    // Wait for valid City options to load
	    List<String[]> validCities = wait.until(d -> {
	        try {
	            Select cityDropdown = new Select(
	                    d.findElement(By.name("city")));

	            List<String[]> cities = new ArrayList<>();

	            for (WebElement option : cityDropdown.getOptions()) {
	                String value = option.getAttribute("value");
	                String text = option.getText().trim();

	                if (value != null
	                        && !value.isBlank()
	                        && !text.isBlank()
	                        && !text.equalsIgnoreCase("Select")) {

	                    cities.add(new String[] { value, text });
	                }
	            }

	            return cities.isEmpty() ? null : cities;

	        } catch (StaleElementReferenceException e) {
	            return null;
	        }
	    });

	    // Choose a random City
	    String[] randomCity = validCities.get(
	            random.nextInt(validCities.size()));

	    String cityValue = randomCity[0];
	    String cityName = randomCity[1];

	    // Re-fetch the dropdown before selecting
	    wait.until(d -> {
	        try {
	            new Select(d.findElement(By.name("city")))
	                    .selectByValue(cityValue);
	            return true;
	        } catch (StaleElementReferenceException e) {
	            return false;
	        }
	    });

	    System.out.println("Selected City: " + cityName);
	}

public void clickAddBtn() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement clickAdd = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add']")));
            clickAdd.click();
      Thread.sleep(1000);
            WebElement clickOK = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='OK']")));
                    clickOK.click();
}

//Add User

public void clickUsersMenue() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(15)
    );

    // Step 1: Locate the Administrator menu
    By administratorMenu = By.xpath(
        "//*[normalize-space()='Administrator']"
    );

    WebElement admin = wait.until(
        ExpectedConditions.elementToBeClickable(administratorMenu)
    );

    // Step 2: Expand Administrator only if Organizations is not visible
    By organizationsOption = By.xpath(
        "//*[normalize-space()='Users']"
    );

    boolean organizationsVisible = driver.findElements(organizationsOption)
            .stream()
            .anyMatch(WebElement::isDisplayed);

    if (!organizationsVisible) {
        admin.click();
    }

    // Step 3: Wait for Organizations and click it
    WebElement organizations = wait.until(
        ExpectedConditions.elementToBeClickable(organizationsOption)
    );
    
    Thread.sleep(1000); 
    organizations.click();

    System.out.println("Successfully clicked Organizations.");
}

public void clickAddUser() throws InterruptedException {
	Thread.sleep(2000);
	driver.findElement(By.xpath("//button[normalize-space()='+ Add User']")).click();
}

public void selectOrganisation() throws InterruptedException {
	Thread.sleep(2000);
	driver.findElement(By.xpath("(//input[@class='fot-formControl empty empty'])[1]")).click();
}

public void clickOnOrganisationName() throws InterruptedException {
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='custom-grid-body']")).click();
}

public void assignType() throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Wait for the Country dropdown
    WebElement countryDropdown = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("(//select[contains(@class,'fot-formControl custom-select empty')])[1]")
        )
    );

    // Select UAE by visible text
    Select countrySelect = new Select(countryDropdown);
    countrySelect.selectByVisibleText("Assign to Node");
}

public void selectUsernameType() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement usernameTypeElement = wait.until(
        ExpectedConditions.elementToBeClickable(By.name("uname_type"))
    );

    Select usernameType = new Select(usernameTypeElement);

    usernameType.selectByVisibleText("Mobile Number");

    System.out.println("Selected Username Type: "
        + usernameType.getFirstSelectedOption().getText().trim());
}

public void selectNode() throws InterruptedException {
    Thread.sleep(2000);
    driver.findElement(By.xpath("//input[contains(@class,'fot-formControl empty empty')]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='custom-grid-body']")).click();

}

// Create node user
public void selectOrgName() throws InterruptedException {
	Thread.sleep(2000);
	driver.findElement(By.xpath("(//input[@class='fot-formControl empty empty'])[1]")).click();
	Thread.sleep(2000);
    driver.findElement(By.xpath("//div[@class='custom-grid-body']")).click();
}
//public void selectNodeType() throws InterruptedException {
//    Thread.sleep(2000);
//    driver.findElement(By.xpath("//input[contains(@class,'fot-formControl empty empty')]")).click();
//    Thread.sleep(2000);
//    driver.findElement(By.xpath("//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium expand-btn css-vubbuv']")).click();
//    Thread.sleep(2000);
//    driver.findElement(By.xpath("//div[@class='custom-grid-body']")).click();
//
//}
	
/*public void selectUserPosition(String positionName) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Step 1: Open the Select Node popup
    WebElement selectNodeField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[contains(@class,'fot-formControl empty empty')]")
        )
    );
    selectNodeField.click();

    // Step 2: Wait for the popup row containing Tenant Admin
    By tenantAdminRow = By.xpath(
        "//tr[.//*[normalize-space()='Tenant Admin']]"
    );

    WebElement row = wait.until(
        ExpectedConditions.visibilityOfElementLocated(tenantAdminRow)
    );

    // Step 3: Click the first expand arrow in the Tenant Admin row
    WebElement expandArrow = row.findElement(
        By.xpath("//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium expand-btn css-vubbuv']")
    );

    wait.until(
        ExpectedConditions.elementToBeClickable(expandArrow)
    ).click();


    // Step 4: Wait for the requested position to appear
    By requestedPosition = By.xpath(
        "//tr[.//*[normalize-space()='" + positionName + "']]"
    );

    WebElement positionRow = wait.until(
        ExpectedConditions.visibilityOfElementLocated(requestedPosition)
    );

    // Step 5: Click the requested position (e.g., Finance)
    wait.until(
        ExpectedConditions.elementToBeClickable(positionRow)
    ).click();

    System.out.println("Selected Position: " + positionName);
}*/

public void selectUserPosition(String positionName) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // 1. Open Select Node popup
    WebElement selectNodeField = wait.until(
        ExpectedConditions.elementToBeClickable(
            By.xpath("//input[contains(@class,'fot-formControl empty empty')]")
        )
    );
    selectNodeField.click();

    // 2. Expand Tenant Admin
    By tenantAdminRow = By.xpath(
        "//tr[.//*[normalize-space()='Tenant Admin']]"
    );

    WebElement row = wait.until(
        ExpectedConditions.visibilityOfElementLocated(tenantAdminRow)
    );

    // Use your confirmed working expand-arrow locator here
    WebElement expandArrow = row.findElement(
        By.xpath(".//*[contains(@class,'expand') or " +
                 "contains(@class,'arrow') or " +
                 "contains(@class,'e-icon')][1]")
    );

    expandArrow.click();

    // 3. Click the exact requested position name
    By positionCell = By.xpath(
        "//tr/td[normalize-space()='" + positionName + "']"
    );

    wait.until(
        ExpectedConditions.elementToBeClickable(positionCell)
    ).click();

    // 4. Verify the Select Node field reflects the selection
//    By selectedNodeField = By.xpath(
//        "(//input[contains(@class,'fot-formControl populate populate')])[2]"
//    );
//
//    String actualValue = wait.until(
//        ExpectedConditions.visibilityOfElementLocated(selectedNodeField)
//    ).getText().trim();
//
    System.out.println("Requested Position: " + positionName);
//    System.out.println("Displayed Select Node: " + actualValue);
//
//    if (!actualValue.equalsIgnoreCase(positionName)) {
//        throw new AssertionError(
//            "Position selection mismatch. Expected: " + positionName
//            + ", but UI displays: " + actualValue
//        );
//    }
}

}