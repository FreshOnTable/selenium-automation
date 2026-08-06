package StepDefinition;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import PageObject.CreateLpo;
import PageObject.LoginSnoc;
import Utils.Setuptest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class Create_LpoStepDefinition {
	private String selectedProduct;
	LocalDate fromDate;
    LocalDate toDate;
	
//	static WebDriver driver;
	public Setuptest testsetup;

	public Create_LpoStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
	}
	
	@Given("User navigates to Create LPO page")
	public void User_navigates_Create_LPO_page() throws InterruptedException {
		CreateLpo Lpo = testsetup.pageObjectManager.getCreateLpo();
		Thread.sleep(1000);
		Lpo.clickthreedot();
		Thread.sleep(1000);
		Lpo.clickprocurement();
		Thread.sleep(1000);
		Lpo.clickLpo();
		Thread.sleep(2000);
	    Lpo.clickwearhousefitler();
	}
	
	@When("User applies Delivery Hub filter {string}")
	public void user_applies_delivery_hub_filter(String hubValue) throws InterruptedException {
		CreateLpo Lpo = testsetup.pageObjectManager.getCreateLpo();
	    Thread.sleep(1000);
	    Lpo.selectDeliveryHub(hubValue);
	    Thread.sleep(2000);
	    Lpo.applyFiltersSafely();
	    Thread.sleep(1000);
		Lpo.selectproducts();
		Thread.sleep(1000);
		Lpo.clicknextbutton();
		Lpo.clicknextbutton();
		Thread.sleep(2000);
	}
	
	@Then("Only orders from Delivery Hub {string} should be displayed")
	public void only_orders_from_delivery_hub_should_be_displayed(String expectedHub) {

	    CreateLpo lpo = testsetup.pageObjectManager.getCreateLpo();

	    // Step 1: Validate Delivery Hub on Create LPO page
	    String actualHub = lpo.getSelectedDeliveryHub();

	    String expectedNormalized = expectedHub.trim().replaceAll("\\s+", " ");
	    String actualNormalized   = actualHub.trim().replaceAll("\\s+", " ");

	    System.out.println("Expected Delivery Hub : " + expectedNormalized);
	    System.out.println("Actual Delivery Hub   : " + actualNormalized);

	    Assert.assertEquals(
	        actualNormalized,
	        expectedNormalized,
	        "Delivery Hub mismatch on Create LPO page");
	        System.out.println("delivery hub matched"
	    );

	    // Step 2: Product availability check
	    if (!lpo.areProductsAvailable()) {
	        System.out.println(
	            "INFO: No records to display for Delivery Hub: " + actualNormalized
	        );
	        return; //  STOP HERE � DO NOT CLICK NEXT
	    }

	    // Step 3: Navigate forward only if products exist

//	    lpo.clickNextSafely(); // Review page
//	    lpo.clickNextSafely(); // Generate LPO page

	    // Step 4 (Optional but recommended): Re-validate hub
	    String hubOnGeneratePage = lpo.getSelectedDeliveryHub();
	    Assert.assertEquals(
	        hubOnGeneratePage.trim().replaceAll("\\s+", " "),
	        expectedNormalized,
	        "Delivery Hub mismatch on Generate LPO page"
	    );

	    System.out.println("PASSED � Delivery Hub validated successfully");
	    
	}

	
	@Then("Only products of selected seller should be displayed after applying filters")
	public void validate_products_after_applying_filters() throws InterruptedException {

	    CreateLpo Lpo = testsetup.pageObjectManager.getCreateLpo();
	    
//	    Lpo.clickwearhousefitler();

	    String selectedSeller = Lpo.selectRandomSellerAndApplyFilter();

	    Lpo.validateLpoResultsForSeller(selectedSeller);
	}
	
	@Then("only FOC orders should be displayed with zero order margin")
	public void validate_only_foc_orders() throws InterruptedException {

	    CreateLpo lpo = testsetup.pageObjectManager.getCreateLpo();

	    lpo.clickFOCcheckbox();
	    lpo.applyFiltersSafely();

	    boolean isValid = lpo.validateFocOrdersMarginZero();

	    if (isValid) {
	        System.out.println("PASSED - FOC Orders validation successful");
	    } else {
	        System.out.println("FAILED - Non-zero margin found in FOC Orders");
	    }

	    Assert.assertTrue(
	        isValid,
	        "FOC Orders validation failed: Order Margin is not zero"
	    );
	}
	
	//existing seller
	
	@Given("User is on Create LPO page")
	public void User_navigates_Create_LPO_page1() throws InterruptedException {
		CreateLpo Lpo = testsetup.pageObjectManager.getCreateLpo();
		Thread.sleep(2000);
		Lpo.clickthreedot();
		Thread.sleep(2000);
		Lpo.clickprocurement();
		Thread.sleep(2000);
		Lpo.clickLpo();
	}
	
	@Then("User filters LPOs by seller name from table")
	public void user_filters_lpos_by_seller_name_from_table() throws InterruptedException {

	    CreateLpo lpo = testsetup.pageObjectManager.getCreateLpo();

	    // Step 1: Fetch seller name dynamically
	    String sellerName = lpo.fetchRandomSellerNameFromTable();

	    // Step 2: Search using seller filter
	    lpo.searchBySellerName(sellerName);
	    Thread.sleep(2000);
	    lpo.applyFiltersSafely();
//	    lpo.clickwearhousefitler();

	    // Step 3: Validate results
	    lpo.validateSellerFilterResults(sellerName);
	}


// Validate Approval Types

@When("User selects {string} from Approval Type filter")
public void user_selects_approval_type(String approvalType) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
//    lpoPage.selectApprovalType(approvalType);
	Thread.sleep(2000);
	lpoPage.selectApprovalType(approvalType);
}

@When("User clicks on Apply Filters button")
public void user_clicks_apply_filters() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    lpoPage.applyFiltersSafely();
	Thread.sleep(2000);
//	lpoPage.clickApplyFilters();
}

@Then("Only {string} products should be displayed in LPO list")
public void validate_products_by_approval_type(String approvalType) {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
//	boolean result = lpoPage.validateRowsByRowClass(approvalType);
//    Assert.assertTrue(result, approvalType + " rows validation failed");
	boolean result = lpoPage.validateRowsByRowClass(approvalType);

 //   Assert.assertTrue(result,
 //           "Products displayed do not belong to " + approvalType);
}


//Scenario: Change seller
@When("User select random products")
public void select_random_products() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
	lpoPage.selectAllproducts();
}
@Then("User clicks on change seller button")
public void click_change_seller_button() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
//	lpoPage.getOldSeller();
//	lpoPage.clickChangeSeller();
//	lpoPage.captureOldSellers();
	
}
@And("User selects a different seller and submits")
public void change_seller_randomly_and_save_changes() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	//lpoPage.getSellerName();
	//lpoPage.changeSellerAndGetNewSeller();
	//lpoPage.changeMultipleSellers();
	lpoPage.changeSellerForAllProducts();
	Thread.sleep(2000);
	lpoPage.submitActions();
//	lpoPage.selectRandomSellerAndSave();
//	lpoPage.selectNextSellerAndSave();
	
}
@And("Seller should be updated successfully in LPO page")
public void validate_the_changed_seller_in_first_page() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo(); 
//	lpoPage.changeSellerForAllSelectedProducts();
}



//Scenario: Validate 2nd page and Add more products on the second page
@When("User creates LPO with random product qty and delivery hub")
public void create_lpo_randomly() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
	lpoPage.selectproducts();
	Thread.sleep(1000);
	lpoPage.clickNext();
    Thread.sleep(1000);
	lpoPage.addMoreProductOn2ndPage();
	Thread.sleep(1000);
	lpoPage.selectRandomProduct();
	Thread.sleep(1000);
	lpoPage.selectRandomSeller();
	Thread.sleep(1000);
	lpoPage.selectRandomUOM();
	Thread.sleep(1000);
    lpoPage.updateOrderQtyForAllProducts();
    lpoPage.clickNext();
    


}

@Then("User selects random delivery hub")
public void user_selects_random_delivery_hub() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	lpoPage.selectRandomDeliveryHub();
   
}

    
@Then("LPO should be created successfully")
public void validate_lpo_created() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(1000);
    lpoPage.clickSubmit();
    Thread.sleep(1000);
    lpoPage.clickConfirmBtn();
    Thread.sleep(1000);
    lpoPage.clickNoBtn();
    Thread.sleep(1000);
	lpoPage.validateLpoCreated();
}

// Change LPO Satus or Accepting LPO
@When("User changes any {int} LPO to the {string} status")
public void User_changes_any_lp_os_to_the_status(int count, String string) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	Thread.sleep(1000);
//	lpoPage.selectproducts();
	lpoPage.selectproducts_inLPOpage();
	Thread.sleep(1000);
	lpoPage.clickNext();
    Thread.sleep(1000);
    lpoPage.updateOrderQtyForAllProducts();
    lpoPage.clickNext();
    lpoPage.selectRandomDeliveryHub();
    Thread.sleep(1000);
    lpoPage.clickSubmit();
    Thread.sleep(1000);
    lpoPage.clickConfirmBtn();
    Thread.sleep(1000);
    lpoPage.clickNoBtn();
    Thread.sleep(1000);
	lpoPage.validateLpoCreated();
//	lpoPage.processTopWaitingForAcceptanceLpos(count);

}

@When("User changes the LPO status to {string} with value {string}")
public void user_changes_the_lpo_status_to_with_value(String status, String value)
        throws InterruptedException {

    CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    int count = 1; // since you always process 1 LPO

    Thread.sleep(1000);

    switch (status.trim()) {

        case "Accepted But Unfulfilled":
            lpoPage.processTopWaitingForAcceptanceLpos(count);
            lpoPage.completeLPO(count, value);
            break;

        case "Partially Accepted":
            lpoPage.partially_Accepted(count);
            lpoPage.completeLPO(count, value);
            break;

        case "Rejected":
            lpoPage.rejectedLPO(count);
            break;

        case "Cancelled":
            lpoPage.CancelledLPO(count);
            break;

        default:
            throw new IllegalArgumentException(
                    "Unsupported LPO status: " + status
            );
    }
   

}

@Then("LPO status should be updated to {string}")
public void lpo_status_should_be_updated_to(String string) throws InterruptedException {
	CreateLpo LPOPage = testsetup.pageObjectManager.getCreateLpo();
//	LPOPage.clickCreateLPOsbtn();
	Thread.sleep(1000);
//	LPOPage.searchProductAndValidate(selectedProduct);
//    LPOPage.validateProductSearchResult(selectedProduct);
	
}
@And("User completes the LPO process")
public void user_completes_the_lpo_process() {
	CreateLpo LPOPage = testsetup.pageObjectManager.getCreateLpo();
	
}

//Add more batches

@When("User changes any {int} LPO status to the {string}")
public void User_changes_any_lp_os_status_to_the_status(int count, String string) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
//	lpoPage.selectproducts();
	lpoPage.selectproducts_inLPOpage();
	Thread.sleep(1000);
	lpoPage.clickNext();
    Thread.sleep(1000);
    lpoPage.updateOrderQtyForAllProducts();
    lpoPage.clickNext();
    lpoPage.selectRandomDeliveryHub();
    Thread.sleep(1000);
    lpoPage.clickSubmit();
    Thread.sleep(1000);
    lpoPage.clickConfirmBtn();
    Thread.sleep(1000);
    lpoPage.clickNoBtn();
    Thread.sleep(1000);
	lpoPage.validateLpoCreated();
	lpoPage.processAddMore_Batches(count);
	Thread.sleep(1000);

}

@When("User rejects some qty in QC check")
public void user_rejects_some_qty_in_qc_check() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();	
	Thread.sleep(1000);	
	lpoPage.enterInvoiceDetailsForAdditionalBatch();
	Thread.sleep(1000);
	lpoPage.addBatchWithRejectedQty();
	Thread.sleep(1000);
	lpoPage.submitAdditionalBatch();
	lpoPage.veryfyAndApprove_AdditionBatch();
    
}
@Then("User will add batches for rejected qty")
public void user_will_add_batches_for_rejected_qty() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    
}
@Then("User will do Verify and Approve batches")
public void user_will_do_verify_and_approve_batches() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    
}
@Then("Fullfullment is completed")
public void fullfullment_is_completed() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    
}
@Then("LPO status should updated as LPO Completed")
public void lpo_status_should_updated_as_lpo_completed() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    
}

//Credit note and Debit note 
@Then("User changes any {int} LPO to the {string} status1")
public void User_changes_any_lp_os_to_the_status1(int count, String string) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	Thread.sleep(3000);
	lpoPage.searchSeller();
	Thread.sleep(3000);
	lpoPage.selectAllproducts();
	Thread.sleep(1000);
	lpoPage.clickNext();
	Thread.sleep(1000);
	lpoPage.updateOrderQtyForAllProducts();
	lpoPage.clickNext();
	Thread.sleep(2000);
	lpoPage.selectRandomDeliveryHub();
	lpoPage.clickSubmit();
	Thread.sleep(1000);
	lpoPage.clickConfirmBtn();
	Thread.sleep(1000);
	lpoPage.clickNoBtn();
	lpoPage.validateLpoCreated();
	Thread.sleep(3000);
	lpoPage.processTopWaitingForAcceptanceLpos3(count);
	Thread.sleep(1000);
}

@When("User completes LPO with zero received qty")
public void user_completes_lpo_with_received_qty_zero() throws InterruptedException {

	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	lpoPage.receiveProductQtyzeroAndVeryfyDN();
	Thread.sleep(2000);
	
}

@Then("User changes any {int} LPO to the {string} Lpo status")
public void User_changes_any_lp_os_to_the_Lpo_status(int count, String string) throws InterruptedException {

	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	lpoPage.processTopPartiallyFulfilledAndAwaitingForApproval_status(count);
}


// Edit LPO
@When("Select some existing products")
public void select_some_existing_products() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo(); 
	Thread.sleep(1000);	
	lpoPage.searchSeller();
	Thread.sleep(3000);
	lpoPage.selectAllproducts();	
	Thread.sleep(1000);
	lpoPage.clickNext();
	Thread.sleep(1000);
	lpoPage.updateOrderQtyForAllProducts();
	lpoPage.clickNext();
}
@And("Select delivery hub for all products")
public void select_delivery_hub_for_all_products() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	Thread.sleep(2000);
	lpoPage.selectRandomDeliveryHub();
	lpoPage.clickSubmit();
	Thread.sleep(1000);
	lpoPage.clickConfirmBtn();
	Thread.sleep(1000);
	lpoPage.clickNoBtn();
	
}
@When("Lpo created with {string} status")
public void lpo_created_with_status(String string) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(3000);
	lpoPage.validateLpoCreated();
	
}
@Then("Edit the Lpo with random qty")
public void edit_the_lpo_with_random_qty() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
//	lpoPage.editLPO();
	Thread.sleep(2000);
	lpoPage.updateRequestedQtyForAllProducts();
}
@Then("Save the changes")
public void save_the_changes() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	lpoPage.saveActions();
    }


// Add procurement comments

@When("Click more button for available products in Lpo page")
public void click_more_button_for_available_products_in_lpo_page() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
	lpoPage.addRandomProcurementComments();
}
@When("Click add procurement comments")
public void click_add_procurement_comments() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
}
@Then("Select suitable procurement comment")
public void select_suitable_procurement_comment() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
}
@Then("Save the comments")
public void save_the_comments() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
}


//Request for Approval
@When("Select the product for an approval request")
public void select_the_product_for_an_approval_request() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
	lpoPage.requestApprovalForRedProducts();
    
}
@When("Click more button and send approval request")
public void click_more_button_and_send_approval_request() {
    
}
@Then("Confirm the approval request sent")
public void confirm_the_approval_request_sent() {
    
}


// LPO stock entry validation
@When("User changes any {int} LPO to the {string} status for completing LPO")
public void user_changes_any_lpo_to_the_status_for_completing_lpo(
        int count, String value) throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(1000);
	lpoPage.selectAllproducts();
//	lpoPage.selectproducts();
//	lpoPage.selectOnlyApprovedProducts(2);
	Thread.sleep(1000);
	lpoPage.clickNext();
    Thread.sleep(1000);
    lpoPage.updateOrderQtyForAllProducts();
    lpoPage.clickNext();
    lpoPage.selectRandomDeliveryHub();
    Thread.sleep(1000);
    lpoPage.clickSubmit();
    Thread.sleep(1000);
    lpoPage.clickConfirmBtn();
    Thread.sleep(1000);
    lpoPage.clickNoBtn();
    Thread.sleep(1000);
	lpoPage.validateLpoCreated();
	Thread.sleep(1000);
	lpoPage.processTopWaitingForAcceptanceLpos4(count);
	Thread.sleep(1000);
	lpoPage.completeLPO4(count, value);
    
}
@Then("Open the stock summary page and search the product")
public void open_the_stock_summary_page_and_search_the_product() {
    
}
@And("validate the stock entry is happend or not")
public void validate_the_stock_entry_is_happend_or_not() {
    
}
}
