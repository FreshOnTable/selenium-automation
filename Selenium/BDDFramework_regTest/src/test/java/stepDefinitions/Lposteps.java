package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.Lpopage;
import utilities.BaseClass;

public class Lposteps {

	public BaseClass setupclass;

	public Lposteps(BaseClass setupclass) {
		this.setupclass = setupclass;
	}

	@Given("User navigates to the Create LPO page")
	public void user_navigate_to_the_create_lpo_page() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(3000);
		Lpo.clickthreedot();
		Thread.sleep(2000);
		Lpo.clickprocurement();
		Thread.sleep(2000);
		Lpo.clickLpo();
	}

	@Given("User selects products which are already approved")
	public void select_products_which_is_already_approved() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(3000);
		Lpo.clickwearhousefitler();
		Thread.sleep(2000);
		Lpo.wearhousedropdown();
		Thread.sleep(2000);
		Lpo.applyFiltersSafely();
		Thread.sleep(2000);
		Lpo.selectproducts();
	}

	@Given("User moves to the second page")
	public void got_to_second_page() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
		Lpo.clicknextbutton();
	}

	@Given("User moves to the third page")
	public void got_to_third_page_and_select_the_delivery_hub() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
		Lpo.clicknextbutton();
	}

	@Then("User clicks the Submit button and confirms")
	public void click_submit_button_and_click_confirm() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(5000);
		Lpo.clicksubmittbutton();
		Thread.sleep(1000);
		Lpo.ClickConfirmButton();
		Thread.sleep(1000);
		Lpo.clickNoButton();
	}

	@Then("the LPO is created successfully")
	public void lpo_is_successfully_created() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
//		Lpo.CreateLpoValidation();
		
	}

	@Then("User changes any {int} LPOs to the {string} status")
	public void User_changes_any_lp_os_to_the_status(int count, String string) throws InterruptedException {
		Lpopage lpo = setupclass.pageObjectManager.getLpopage();
		lpo.processTopWaitingForAcceptanceLpos(count);
	}

	@Then("LPO fulfillment Process")
	public void the_user_performs_lpo_fulfillment() {

	}

	@Then("User changes the LPO status to {string}")
	public void the_user_changes_the_lpo_status_to(String string) {

	}

	@Then("User fills all mandatory fields to {string}")
	public void User_fills_all_mandatory_fields_to_Fulfill_LPO(String string) {

	}

	@Then("User verifies and approves the LPO")
	public void the_user_verifies_and_approves_the_lpo() {

	}

	@Then("LPO is completed successfully")
	public void the_lpo_is_completed_successfully() {

	}
	
	@Then("User changes any {int} LPO to the {string} status1")
	public void User_changes_any_lp_os_to_the_status1(int count, String string) throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(3000);
		Lpo.searchSeller();
		Thread.sleep(3000);
		Lpo.selectAllProducts();
		Thread.sleep(1000);
		Lpo.clicknextbutton();
		Thread.sleep(1000);
		Lpo.clicknextbutton();
		Thread.sleep(2000);
		Lpo.selectRandomDeliveryHub();
		Lpo.clicksubmittbutton();
		Thread.sleep(1000);
		Lpo.ClickConfirmButton();
		Thread.sleep(1000);
		Lpo.clickNoButton();
		Thread.sleep(3000);
		Lpo.processTopWaitingForAcceptanceLpos1(count);
	}
	
	@When("User completes LPO with zero received qty")
	public void user_completes_lpo_with_received_qty_zero() throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		
		Lpo.receiveProductQtyzeroAndVeryfyDN();
		Thread.sleep(2000);
		
	}
	
	@Then("User changes any {int} LPO to the {string} Lpo status")
	public void User_changes_any_lp_os_to_the_Lpo_status(int count, String string) throws InterruptedException {
		Lpopage Lpo = setupclass.pageObjectManager.getLpopage();
		
		Lpo.processTopPartiallyFulfilledAndAwaitingForApproval_status(count);
}
}
