package StepDefinition;

import PageObject.ProductMaster;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class ProductMasterStepDefinition {
	
public Setuptest testsetup;

public static String createdProductLineName;
    
	public ProductMasterStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
	
		
	}
	
	
	@Given("the user is on the Product Master Page")
	public void the_user_is_on_the_product_master_page() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.productMasterPage();

		
	}

	
	@When("the user creates the Product Line of the product")
	public void the_user_creates_the_product_line_of_the_product() throws InterruptedException {

	    ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();

	    productMaster.productLine();

	    createdProductLineName = productMaster.productLineName;

	    System.out.println("Product Line Stored : " + createdProductLineName);
	}

	
	@When("the user creates the Product Category of the product")
	public void the_user_creates_the_product_category_of_the_product() throws InterruptedException {
     ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();

	    productMaster.productLineName = createdProductLineName;

	    System.out.println("Product Line Passed to Category : "+ productMaster.productLineName );

	    productMaster.productCategory();
	}
	
	
	@When("the user creates the Product Sub Category of the product")
	public void the_user_creates_the_product_sub_category_of_the_product() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		
		 productMaster.productLineName = createdProductLineName;

		    System.out.println("Product Line Passed to Category : "+ productMaster.productLineName );

		    productMaster.productSubCategory();
		}


	
	
	
	@When("the user updating the Description of the Product Line")
	public void the_user_updating_the_description_of_the_product_line() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.productLineDescriptionUpdation();
	}


	@When("the user updating the Description of the Product Category")
	public void the_user_updating_the_description_of_the_product_category() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.productCategoryDescriptionUpdation();
	  
	}

	
	@When("the user updating the Description of the Product Sub Category")
	public void the_user_updating_the_description_of_the_product_sub_category() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.productSubCategoryDescriptionUpdation();
	   
	}



	@When("the user updating the Description Temperature")
	public void the_user_updating_the_description_temperature() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.TemperatureDescriptionUpdation();
	  
	}
	

	
	@When("the user updating the Description UOM")
	public void the_user_updating_the_description_uom() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.UnitOfMeasurementDescriptionUpdation();
	  
	}

	
	@When("the user updating the Description Allergens")
	public void the_user_updating_the_description_allergens() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.AllergensDescriptionUpdation();
	  
	}

	
	@When("the user updating the Description Farm Technology")
	public void the_user_updating_the_description_farm_technology() throws InterruptedException {
		ProductMaster productMaster = testsetup.pageObjectManager.getProduct_Master();
		productMaster.FarmTechnologyDescriptionUpdation(); 
	}

}
