package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObject.Placeorder;
import utilities.BaseClass;

public class PlaceorderSteps {
	
public BaseClass setupclass;
	
	public PlaceorderSteps(BaseClass setupclass) {
		this.setupclass = setupclass;
	}
	
	@Given("User navigates to the Place Order page")
	public void user_navigates_to_the_place_order_page() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
        Order.clickgrid();
        Thread.sleep(1000);
        Order.clickorderoption();
        Thread.sleep(2000);
        Order.clickplaceorder();
	}

	@Given("User selects a business from the list")
	public void user_selects_a_business_from_the_list() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Thread.sleep(5000);
		Order.clickbusinessfield();
		Thread.sleep(8000);
		Order.selectBusiness();
	}
	
	@Given("User selects the products and enters product quantity")
	public void user_selects_the_products_and_enters_product_quantity() throws Exception {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Thread.sleep(2000);
		Order.selectProduct_And_Quantity();
	}

	@Then("User selects the payment method")
	public void user_selects_the_payment_method() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
	    Order.paymentTerms();
	}

	@Then("User selects the shipping date")
	public void user_selects_the_shipping_date() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Order.shippingDate();
	}

	@Then("User selects the billing address and shipping address")
	public void user_selects_the_billing_address_and_shipping_address() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Order.billingAddress_And_shippingAddress();
	}

	@Then("User enters the LPO number")
	public void user_enters_the_lpo_number() {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder(); 
		Order.LPOnumber();
	}

	@Then("User selects the Outlet \\(optional) and Delivery User \\(optional)")
	public void user_selects_the_outlet_optional_and_delivery_user_optional() {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Order.deliveryUser();
	}

	@Then("The order is created successfully")
	public void the_order_is_created_successfully() throws InterruptedException {
		Placeorder Order = setupclass.pageObjectManager.getplaceorder();
		Order.placeOrder();
		Thread.sleep(2000);
		 Placeorder.orderId = Order.captureOrderId(); 
		
	}


}
