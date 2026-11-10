package PageObject;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ExcelReader;
import Utils.ExcelRowData;
import Utils.ExcelValidator;
import Utils.ReportDownloadUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportModule {

    private WebDriver driver;

    private WebDriverWait wait;

    private String downloadDirectory;
    
    private String referenceId;

    public ReportModule(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(20)
                );

        this.downloadDirectory =
                System.getProperty("user.dir")
                        + File.separator
                        + "downloads";
    }
    
    By clickReport = By.xpath("//ul//li//span[text()='Reports']");
    By plackorderReport = By.xpath("//ul//li//span[text()='Order Report']");
    By downloadReport = By.xpath("//ul//li//span[text()='Download Reports']");
    
    public void clickReportsOption() {
		driver.findElement(clickReport).click();
	}

	public void clicksOrderReport() {
		driver.findElement(plackorderReport).click();
	}
	
	public void clicksDownloadReport() {
		driver.findElement(downloadReport).click();
	}

    // ============================================================
    // REPORT NAME
    // ============================================================

    private By reportNameInput =
            By.xpath(
                    "//select[contains(@name,'reportName')]"
                            
            );

    private By reportOption(
            String reportName) {

        return By.xpath(
                "//*[normalize-space()='"
                        + reportName
                        + "']"
        );
    }

    // ============================================================
    // DATE FIELDS
    // ============================================================

    private By startDateInput =
            By.xpath(
                    ""
            );

    private By endDateInput =
            By.xpath(
                    "//label[contains(normalize-space(),'Start Date')]/following::input[1]"
            );

    // ============================================================
    // DOWNLOAD TYPE
    // ============================================================

    private By downloadType =
            By.xpath(
                    "(//select[contains(@name,'reportType')])[1]"
            );

    // ============================================================
    // DATE TYPE
    // ============================================================

    private By dateType =
            By.xpath(
                    "(//select[contains(@name,'reportType')])[1]"
            );

    // ============================================================
    // SUBMIT
    // ============================================================

    private By submitButton =
            By.xpath(
                    "//button[text()='Submit']"
            );

    // ============================================================
    // SELECT REPORT
    // ============================================================

    public void selectReport(String reportName) {

        WebElement input =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        reportNameInput
                                )
                );

        input.click();

 //       input.clear();

        input.sendKeys(
                reportName
        );

        WebElement option =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        reportOption(
                                                reportName
                                        )
                                )
                );

        option.click();

        System.out.println(
                "✅ Report selected: "
                        + reportName
        );
    }

    public void clickGoButton() {
        WebElement goButton =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        By.xpath("//button[contains(text(),'Go')]")
                                )
                );

        goButton.click();      
    }
   
    // ============================================================
    // START DATE
    // ============================================================
    
    public void enterStartDate(String date) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By startDateLocator = By.xpath(
                "(//input[contains(@placeholder,'Select Date')])[1]"
        );

        WebElement startDate = driver.findElement(startDateLocator);


        ((JavascriptExecutor) driver).executeScript(
                """
                const input = arguments[0];
                const value = arguments[1];

                const setter = Object.getOwnPropertyDescriptor(
                    HTMLInputElement.prototype,
                    'value'
                ).set;

                setter.call(input, value);

                input.dispatchEvent(new Event('input', { bubbles: true }));
                input.dispatchEvent(new Event('change', { bubbles: true }));
                """,
                startDate,
                date
        );
        System.out.println("✅ Start Date entered: " + date);
    }

    // ============================================================
    // END DATE
    // ============================================================
   

/*    public void enterEndDate(
            String date) {

        WebElement element =
                driver.findElement(endDateInput);

        element.click();

        element.sendKeys(date);

        System.out.println(
                "End Date: " + date
        );
    }*/
    
    public void enterEndDate(String date) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By endDateLocator = By.xpath(
                "//label[contains(normalize-space(),'Start Date')]/following::input[1]"
        );

        WebElement endDate = driver.findElement(endDateLocator);


        ((JavascriptExecutor) driver).executeScript(
                """
                const input = arguments[0];
                const value = arguments[1];

                const setter = Object.getOwnPropertyDescriptor(
                    HTMLInputElement.prototype,
                    'value'
                ).set;

                setter.call(input, value);

                input.dispatchEvent(new Event('input', { bubbles: true }));
                input.dispatchEvent(new Event('change', { bubbles: true }));
                """,
                endDate,
                date
        );

        System.out.println("✅ End Date entered: " + date);
    }
    
    // ============================================================
    // DOWNLOAD TYPE
    // ============================================================

    public void selectDownloadType(
            String type) {

        WebElement element =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        downloadType
                                )
                );

        element.click();

        By option =
                By.xpath(
                        "//*[normalize-space()='"
                                + type
                                + "']"
                );

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                option
                        )
        ).click();

        System.out.println(
                "Download Type: "
                        + type
        );
    }

    // ============================================================
    // DATE TYPE
    // ============================================================

    public void selectDateType(
            String type) {

        WebElement element =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        dateType
                                )
                );

        element.click();

        By option =
                By.xpath(
                        "//*[normalize-space()='"
                                + type
                                + "']"
                );

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                option
                        )
        ).click();

        System.out.println(
                "Date Type: "
                        + type
        );
    }
    
    
    public void selectStatuses(String statuses) throws InterruptedException {

        // Split multiple statuses
        String[] statusList = statuses.split(",");

        // Open Status dropdown
        WebElement statusDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[normalize-space()='Status']/preceding::div[@class='dropdown-heading'][1]")
                )
        );
        Thread.sleep(1000); // Wait for the dropdown to be fully rendered
        statusDropdown.click();

        for (String status : statusList) {

            status = status.trim();

            System.out.println("Selecting Status: " + status);

            WebElement statusCheckbox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                "//*[normalize-space()='" + status + "']" +
                                "/ancestor::*[.//input[@type='checkbox']][1]" +
                                "//input[@type='checkbox']"
                            )
                    )
            );

            if (!statusCheckbox.isSelected()) {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        statusCheckbox
                );
            }

            System.out.println("✅ Status selected: " + status);
        }
    }

    // ============================================================
    // SUBMIT
    // ============================================================

    public void clickSubmit() {

        WebElement submit = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Submit']")
                )
        );

        submit.click();

        System.out.println("✅ Submit clicked");
    }
    
    
    public String captureFreferenceId() {

        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Submit']")
                )
        );

        submitButton.click();

        System.out.println("✅ Submit clicked");

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(normalize-space(),'Request Accepted with reference id')]")
                )
        );

        String messageText = message.getText();

        Pattern pattern = Pattern.compile(
                "reference id\\s+(\\d+)",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(messageText);

        if (!matcher.find()) {
            throw new RuntimeException(
                    "Reference ID not found: " + messageText
            );
        }

        String referenceId = matcher.group(1);

        System.out.println(
                "✅ Reference ID captured: " + referenceId
        );

        WebElement okButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='OK']")
                )
        );

        okButton.click();

        System.out.println("✅ Popup closed");

        return referenceId;
    }
    
    public void searchReferenceId(String referenceId) throws InterruptedException {
    	
    	Thread.sleep(2000);
    	WebElement filterIcon = driver.findElement(
                By.xpath(
                        "(//div[@class='custom-filter-icon'])[1]"
                )
        );
        filterIcon.click();

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='text']")
                )
        );

//        searchBox.clear();
        searchBox.sendKeys(referenceId);

        System.out.println(
                "✅ Searching Reference ID: " + referenceId
        );
        
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
    }
    
    // ============================================================
    // DOWNLOAD + VALIDATE
    // ============================================================

    public void downloadAndValidateOrderExceptionReport()
            throws Exception {

        File downloadedFile =
                ReportDownloadUtil
                        .waitForExcelDownload(
                                downloadDirectory,
                                120
                        );

        ExcelReader reader =
                new ExcelReader();

        List<ExcelRowData> excelRows =
                reader.readOrderExceptionReport(
                        downloadedFile
                                .getAbsolutePath()
                );

        if (excelRows.isEmpty()) {

            throw new AssertionError(
                    "❌ Excel report contains no data rows"
            );
        }

        ExcelValidator validator =
                new ExcelValidator();

        validator.validateOrderExceptionReport(
                excelRows
        );
    }
    
    public void clickMoreAndDownload() throws InterruptedException {
    	
        WebElement moreButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(normalize-space(),'More')]")
                )
        );

        moreButton.click();

        WebElement downloadReports = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@role='button']")
                )
        );
        Thread.sleep(1000); 
        downloadReports.click();

        System.out.println("✅ Download Reports clicked");
    }
}
