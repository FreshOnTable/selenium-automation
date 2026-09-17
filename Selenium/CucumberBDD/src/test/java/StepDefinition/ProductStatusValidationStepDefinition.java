package StepDefinition;

import PageObject.ProductStatusValidation;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class ProductStatusValidationStepDefinition {
	
public Setuptest testsetup;
    
	public ProductStatusValidationStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
		
	}
	
	@Given("the user is on the View Product page")
	public void the_user_is_on_the_view_product_page() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.ViewProductPage();
	}

	@When("the user changes the status of the product from Active to Inactive")
	public void the_user_changes_the_status_of_the_product_from_active_to_inactive() throws InterruptedException {
      ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
      productStatusValidation.ProductStatusCheckBeforeActivetoInactive();
      productStatusValidation.ProductStatusActivetoInactive();
      productStatusValidation.ProductStatusCheckAfterActivetoInactive();

		
	}

	@When("the user changes the status of the product from Inactive to Active")
	public void the_user_changes_the_status_of_the_product_from_Inactive_to_active() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.ProductStatusCheckBeforeInactivetoActive();
		productStatusValidation.ProductStatusInactivetoActive();
		productStatusValidation.ProductStatusCheckAfterInactivetoActive();
		
	}

	@When("the user changes the status of the variant from Active to Inactive")
	public void the_user_changes_the_status_of_the_variant_from_active_to_inactive() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.VariantStatusActivetoInactive();
	}

	@When("the user changes the status of the variant from Inactive to Active")
	public void the_user_changes_the_status_of_the_variant_from_inactive_to_active() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.VariantStatusInactivetoActive();
	}

	@When("the user changes the status of the variant from Active to Deleted")
	public void the_user_changes_the_status_of_the_variant_from_active_to_deleted() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.VariantStatusActivetoDelete();
	}

	@When("the user changes the status of the variant from Inactive to Deleted")
	public void the_user_changes_the_status_of_the_variant_from_inactive_to_deleted() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.VariantStatusInactivetoDelete();
	}
	
	@Given("the user changes the status of the variant using Bulk status change from Active to Inactive")
	public void the_user_changes_the_status_of_the_variant_using_bulk_status_change_from_active_to_inactive() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.bulkStatusChangeOfVariantActivetoInactive();
		
	}

	@Given("the user changes the status of the variant using Bulk status change from Inactive to Active")
	public void the_user_changes_the_status_of_the_variant_using_bulk_status_change_from_inactive_to_active() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.bulkStatusChangeOfVariantInactivetoActive();
		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                      // PRODUCT VARIANT //
	
	
	@Given("the user is on the Product Variant page")
	public void the_user_is_on_the_product_variant_page() throws InterruptedException {
	ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
	productStatusValidation.ViewproductVariants();
	productStatusValidation.selectSeller();
	    
	}

	@When("the user changes the status of the product seller-wise from Active to Inactive")
	public void the_user_changes_the_status_of_the_product_seller_wise_from_active_to_inactive() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.changeActiveProductToInactive();
	}

	@When("the user changes the status of the product seller-wise from Inactive to Active")
	public void the_user_changes_the_status_of_the_product_seller_wise_from_inactive_to_active() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.changeInactiveProductToActive();
	}

	@When("the user changes the status of the product seller-wise from Active to Delete")
	public void the_user_changes_the_status_of_the_product_seller_wise_from_active_to_delete() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.changeActiveProductToDeleted();
	}

	@When("the user changes the status of the product seller-wise from Inactive to Delete")
	public void the_user_changes_the_status_of_the_product_seller_wise_from_inactive_to_delete() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.changeInactiveProductToDeleted();
	}
	
	@Given("the user changes the status of the product using bulk status change from Active to Inactive")
	public void the_user_changes_the_status_of_the_product_using_bulk_status_change_from_active_to_inactive() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.bulkStatusChangefromActivetoInactive();
	}

	@Given("the user changes the status of the product using bulk status change from Inactive to Active")
	public void the_user_changes_the_status_of_the_product_using_bulk_status_change_from_inactive_to_active() throws InterruptedException {
		ProductStatusValidation productStatusValidation = testsetup.pageObjectManager.getProduct_Status_Validation();
		productStatusValidation.bulkStatusChangefromInactivetoActive();
	}

}


