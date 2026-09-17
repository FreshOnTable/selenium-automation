package StepDefinition;

import PageObject.EditProduct;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditProductStepDefinition {

	public Setuptest testsetup;

	public EditProductStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;

	}

	@Given("the user is on the {string} page")
	public void the_user_is_on_the_page(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.EditProductPage();

	}

	@When("the user notes the existing product details")
	public void the_user_notes_the_existing_product_details() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductDetailsBefore();
	}

	@When("the user changes the product name to {string}")
	public void the_user_changes_the_product_name_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductName();
	}

	@When("the user changes the department to {string}")
	public void the_user_changes_the_department_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductDept();
	}

	@When("the user changes the product category to {string}")
	public void the_user_changes_the_product_category_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductCategory();
	}

	@When("the user changes the product subcategory to {string}")
	public void the_user_changes_the_product_subcategory_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductSubCategory();
	}

	@When("the user changes the product classification to {string}")
	public void the_user_changes_the_product_classification_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductClassification();
	}

	@When("the user changes the product temperature to {string}")
	public void the_user_changes_the_product_temperature_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editProductTemperature();
	}

	@Then("the updated product details should be displayed successfully")
	public void the_updated_product_details_should_be_displayed_successfully() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.Submit();
		product.editProductDetailsAfter();
	}

	///////////////////////////////// Edit Product Variant Details   ///////////////////////////////////////////////////

	@When("the user notes the existing product variant details")
	public void the_user_notes_the_existing_product_variant_details() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantDetailsBefore();
	}

	@When("the user changes the weight to {string}")
	public void the_user_changes_the_weight_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantWeight();
	}

	@When("the user changes the purchase price to {string}")
	public void the_user_changes_the_purchase_price_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantPurchasePrice();
	}

	@When("the user changes the selling price to {string}")
	public void the_user_changes_the_selling_price_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantSellingPrice();
	}

	@When("the user changes the stock availability to {string}")
	public void the_user_changes_the_stock_availability_to(String string) throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantStock();
	}

	@Then("the updated product variant details should be displayed successfully")
	public void the_updated_product_variant_details_should_be_displayed_successfully() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.editVariantDetailsAfter();
		product.SubmitButton();
	}

////////////////////////////////////// Duplicate Variant //////////////////////////////////////////////////
	
	@When("the user tries to submit the variant without saving it")
	public void the_user_tries_to_submit_the_variant_without_saving_it() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.SubmitVariantWithoutSaving();
	   
	}

	@When("the user tries to add a duplicate variant")
	public void the_user_tries_to_add_a_duplicate_variant() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.DuplicateVariant();
	    
	}
	
	@Given("the user create the new variant")
	public void the_user_create_the_new_variant() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.CreateVariant();
	}

	@Given("the user deletes the variant and creates the same variant again; the deleted variant should be reactivated.")
	public void the_user_deletes_the_variant_and_creates_the_same_variant_again_the_deleted_variant_should_be_reactivated() throws InterruptedException {
		EditProduct product = testsetup.pageObjectManager.getEdit_Product();
		product.ViewProducts();
		product.captureDetailsChangeStatus();
		product.AddDeletedVariant();
	}
	
	
}

