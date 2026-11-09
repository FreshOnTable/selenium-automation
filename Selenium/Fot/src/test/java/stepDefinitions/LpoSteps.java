package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObject.LpoPage;
import utilities.BaseClass;

public class LpoSteps {
	
	public BaseClass setupclass;

	public LpoSteps(BaseClass setupclass) {
		this.setupclass = setupclass;
	}

	@Given("User navigates to the Create LPO page")
	public void user_navigate_to_the_create_lpo_page() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(5000);
		Lpo.clickthreedot();
		Thread.sleep(2000);
		Lpo.clickprocurement();
		Thread.sleep(2000);
		Lpo.clickLpo();
	}

	@Given("User selects products which are already approved")
	public void select_products_which_is_already_approved() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(3000);
		Lpo.clickwearhousefitler();
		Thread.sleep(2000);
		Lpo.wearhousedropdown();
		Thread.sleep(2000);
		Lpo.clickApplyBtn();
		Thread.sleep(2000);
		Lpo.selectproducts();
	}

	@Given("User moves to the second page")
	public void got_to_second_page() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
		Lpo.clicknextbutton();
	}

	@Given("User moves to the third page")
	public void got_to_third_page_and_select_the_delivery_hub() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
		Lpo.clicknextbutton();
	}

	@Then("User clicks the Submit button and confirms")
	public void click_submit_button_and_click_confirm() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(5000);
		Lpo.clicksubmittbutton();
		Thread.sleep(1000);
		Lpo.ClickConfirmButton();
		Thread.sleep(1000);
		Lpo.clickNoButton();
	}

	@Then("the LPO is created successfully")
	public void lpo_is_successfully_created() throws InterruptedException {
		LpoPage Lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
//		Lpo.CreateLpoValidation();
		Thread.sleep(2000);
	}

	@Then("User changes any {int} LPOs to the {string} status")
	public void User_changes_any_lp_os_to_the_status(int count, String string) throws InterruptedException {
		LpoPage lpo = setupclass.pageObjectManager.getLpopage();
		Thread.sleep(2000);
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

}
