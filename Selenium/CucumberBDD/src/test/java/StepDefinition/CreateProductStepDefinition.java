package StepDefinition;

import PageObject.CreateProduct;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateProductStepDefinition {
	
public Setuptest testsetup;
    
	public CreateProductStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
		
	}

@Given("The user is on the {string} page.")
public void the_user_is_on_the_page(String string) throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.CreateProductPage();
}

@When("Click submit button without filling mandatory fields and handle the popup message.")
public void click_submit_button_without_filling_mandatory_fields_and_handle_the_popup_message() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.CreateProductValidation();
}

@Then("Add an product images.")
public void add_an_product_images() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.AddImage();
}

@Then("fill all mandatory field which are there in products creation page.")
public void fill_all_mandatory_field_which_are_there_in_products_creation_page() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.ProductCreationMandatory();
}

@Then("Add atleast one variant of the product and fill all the mandatory fields and save the details.")
public void add_atleast_one_variant_of_the_product_and_fill_all_the_mandatory_fields_and_save_the_details() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.VariantDetails();
}


@Then("Click submit button validate all mandatory fields and the product is created successfully.")
public void click_submit_validate_all_mandatory_fields_and_the_product_is_created_successfully() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.SubmitButton();
}

//-------------------------change product status steps-----------------------------------------------------------

@Given("user to click on the view products grid")
public void user_to_click_on_the_view_products_grid() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.ViewProduct();
}

@When("after creating a product its in under initiated status")
public void after_creating_a_product_its_in_under_initiated_ststus() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.StatusCheckInitiated();
}

//@Then("select initiated status option in the status filtration")
//public void select_initiated_status_option_in_the_status_filtration() {
//	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
//}

@Then("click change status option in more button")
public void click_change_status_option_in_more_button() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.clickmorebutton();
}

@Then("change the product status as active")
public void change_the_product_status_as_active() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.StatusChange();
}

@Then("click submit button")
public void click_submit_button() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.Submit();
}

@Then("product status changed successfully")
public void product_status_changed_successfully() throws InterruptedException {
	CreateProduct product = testsetup.pageObjectManager.getProduct_Module();
	product.StatusCheckActive();
}

}
