package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.File;

import PageObject.Delivery_Module;
import PageObject.ReportModule;
import Utils.DownloadUtils;
import Utils.ExcelUtils;
import Utils.Setuptest;

public class ReportModuleStepDefinition {

    public Setuptest testsetup;

    private ReportModule reportModule;
    
    private String referenceId;

    public ReportModuleStepDefinition(Setuptest testsetup) {

        this.testsetup = testsetup;      
    }
    
    @Given("User navigates to Report Module")
    public void user_navigates_to_report_module()
            throws InterruptedException {
    	
    	this.reportModule = testsetup.pageObjectManager.getReportModule();

        Delivery_Module schedule =
                testsetup.pageObjectManager.getDelivery_Module();

        schedule.clickgrid();

        Thread.sleep(1000);

        reportModule.clickReportsOption();

        Thread.sleep(1000);

        reportModule.clicksOrderReport();

        Thread.sleep(2000);

        System.out.println(
                "✅ User navigated to Report Module"
        );
    }

    @When("User selects {string}")
    public void user_selects_report(String reportName) throws InterruptedException {
    	Thread.sleep(1000);
        reportModule.selectReport(reportName);
        
        Thread.sleep(2000);
        reportModule.clickGoButton();
    }


    @When("User enters report start date {string}")
    public void user_enters_start_date(String date) throws InterruptedException {
    	Thread.sleep(2000);
        reportModule.enterStartDate(date);
    }


    @When("User enters report end date {string}")
    public void user_enters_end_date(String date) throws InterruptedException {
    	Thread.sleep(2000);
        reportModule.enterEndDate(date);
    }


    @When("User selects download type {string}")
    public void user_selects_download_type(String type) throws InterruptedException {
    	Thread.sleep(2000);
        reportModule.selectDownloadType(type);
    }


    @When("User selects date type {string}")
    public void user_selects_date_type(String type) throws InterruptedException {
    	Thread.sleep(2000);
        reportModule.selectDateType(type);
    }
    
    @When("User selects status {string}")
    public void user_selects_status(String statuses) throws InterruptedException {

        ReportModule reportModule = testsetup.pageObjectManager.getReportModule();

        reportModule.selectStatuses(statuses);
    }

    @When("User clicks Submit")
    public void user_clicks_submit() throws InterruptedException {
    	Thread.sleep(2000);
        reportModule.clickSubmit();
    }


    @Then("Order Exception Report should be downloaded and validated")
    public void report_should_be_downloaded_and_validated()throws Exception {
    	
    	 ReportModule reportModule =
    	            testsetup.pageObjectManager.getReportModule();

    	    referenceId = reportModule.captureFreferenceId();

//        reportModule.downloadAndValidateOrderExceptionReport();
    }
    
    @When("User navigates to Download Reports")
    public void user_navigates_to_download_reports() throws InterruptedException {
    	ReportModule reportModule = testsetup.pageObjectManager.getReportModule();
    	
    	Delivery_Module schedule =
                testsetup.pageObjectManager.getDelivery_Module();
    	
    	Thread.sleep(1000);
    	schedule.clickgrid();

        Thread.sleep(1000);

        reportModule.clickReportsOption();

        Thread.sleep(1000);

        reportModule.clicksDownloadReport();

        if (referenceId == null || referenceId.trim().isEmpty()) {
            throw new RuntimeException(
                    "Reference ID is null or empty. Cannot search Download Reports."
            );
        }

        reportModule.searchReferenceId(referenceId);
    }
    @When("User searches the captured reference id")
    public void user_searches_the_captured_reference_id() {
    	ReportModule reportModule = testsetup.pageObjectManager.getReportModule();
    }
    @When("User waits for report status to be {string}")
    public void user_waits_for_report_status_to_be(String string) {
    	ReportModule reportModule = testsetup.pageObjectManager.getReportModule();
    }
//    @When("User downloads the report")
//    public void user_downloads_the_report() {
//    	ReportModule reportModule = testsetup.pageObjectManager.getReportModule();
//    }
    
 /*   @Then("User downloads and validates the report")
    public void user_downloads_and_validates_the_report()
            throws Exception {

        ReportModule reportModule =
                testsetup.pageObjectManager.getReportModule();

        String downloadPath =
                System.getProperty("user.dir")
                + "\\Downloads";

        DownloadUtils.clearDownloads(downloadPath);

        reportModule.downloadReportFromMore();

        File downloadedFile =
                DownloadUtils.waitForDownload(
                        downloadPath,
                        ".xlsx",
                        30
                );

        System.out.println(
                "✅ Report downloaded: "
                + downloadedFile.getAbsolutePath()
        );

        ExcelUtils.readExcel(downloadedFile);

        // DB validation will come here
    }*/
    
    @When("User downloads and validates the report")
    public void user_downloads_and_validates_the_report() throws Exception {

        // Same folder configured in Basetest
        String downloadPath =
                System.getProperty("user.home") + "\\Downloads\\SeleniumReports";

        System.out.println("📁 Download path: " + downloadPath);

        // Clear old downloads
        DownloadUtils.clearDownloadFolder(downloadPath);

        System.out.println("🧹 Download folder cleared");
        System.out.println("📁 Waiting for report download...");

        // Click More -> Download Reports
        reportModule.clickMoreAndDownload();

        // Wait for actual Excel file
        File downloadedFile =
                DownloadUtils.waitForDownload(
                        downloadPath,
                        120
                );

        System.out.println(
                "✅ Download completed: "
                        + downloadedFile.getAbsolutePath()
        );

        // Next step
     //    ExcelUtils.validateReportAgainstDB(downloadedFile);
    }
    
    @Then("Order Exception Report should be validated")
    public void order_exception_report_should_be_validated() {
    	ReportModule reportModule = testsetup.pageObjectManager.getReportModule();
    }
}