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
		Thread.sleep(2000);
		Lpo.clickthreedot();
		Thread.sleep(2000);
		Lpo.clickprocurement();
		Thread.sleep(2000);
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

	    lpo.clickNextSafely(); // Review page
	    lpo.clickNextSafely(); // Generate LPO page

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
public void user_selects_approval_type(String approvalType) {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    lpoPage.selectApprovalType(approvalType);
}

@When("User clicks on Apply Filters button")
public void user_clicks_apply_filters() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
    lpoPage.applyFiltersSafely();
}

@Then("Only {string} products should be displayed in LPO list")
public void validate_products_by_approval_type(String approvalType) {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	boolean result = lpoPage.validateRowsByRowClass(approvalType);
    Assert.assertTrue(result, approvalType + " rows validation failed");
}
//Scenario: Change seller

@When("User select random products")
public void select_random_products() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	Thread.sleep(2000);
	lpoPage.selectproducts();
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
	lpoPage.getSellerName();
	lpoPage.changeSellerAndGetNewSeller();
//	lpoPage.selectRandomSellerAndSave();
//	lpoPage.selectNextSellerAndSave();
	
}
@And("Seller should be updated successfully in LPO page")
public void validate_the_changed_seller_in_first_page() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo(); 
//	lpoPage.changeSellerForAllSelectedProducts();
}


//Scenario: Validate 2nd page

@When("User creates LPO with random product qty and delivery hub")
public void create_lpo_randomly() throws InterruptedException {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	lpoPage.selectproducts();
	Thread.sleep(1000);
	lpoPage.clickNext();
    Thread.sleep(1000);

    
//	lpoPage.updateOrderQtyRandomly();
    lpoPage.updateOrderQtyForAllProducts();
    lpoPage.clickNext();


}

@Then("User selects random delivery hub")
public void user_selects_random_delivery_hub() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
	
	String hub = lpoPage.selectRandomDeliveryHub();
    Assert.assertFalse(hub.isEmpty(),
            "❌ Delivery Hub not selected");
//	 lpoPage.selectWarehouseForAllSuppliers();
}

    
@Then("LPO should be created successfully")
public void validate_lpo_created() {
	CreateLpo lpoPage = testsetup.pageObjectManager.getCreateLpo();
//  lpoPage.clickSubmit();
	lpoPage.validateLpoCreated();
}

}
