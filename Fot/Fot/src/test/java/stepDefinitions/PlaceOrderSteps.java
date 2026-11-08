package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObject.ObjectManager;
import pageObject.PlaceOrder;
import utilities.BaseClass;

public class PlaceOrderSteps {
	
//public BaseClass setupclass;
//	
//	public PlaceOrderSteps(BaseClass setupclass) {
//		this.setupclass = setupclass;
//	}
	BaseClass base;
    PlaceOrder order;

    public PlaceOrderSteps(BaseClass base) {
        this.base = base;
        this.order = base.pageObjectManager.getplaceorder();
    }
	
    @Given("User navigates to the Place Order page")
    public void user_navigates_to_the_place_order_page() throws InterruptedException {

    	Thread.sleep(3000);
        order.clickgrid();
        Thread.sleep(2000);
        order.clickorderoption();
        Thread.sleep(2000);
        order.clickplaceorder();
    }

    @And("User selects a business from the list")
    public void user_selects_a_business_from_the_list() throws InterruptedException {
    	Thread.sleep(2000);
        order.clickbusinessfield();
        Thread.sleep(2000);
        order.selectBusiness();
    }

    @And("User selects the products and enters product quantity")
    public void user_selects_the_products_and_enters_product_quantity() throws Exception {
    	Thread.sleep(2000);
        order.selectProduct_And_Quantity();
    }

    @Then("User selects the payment method")
    public void user_selects_the_payment_method() throws InterruptedException {
    	Thread.sleep(1000);
        order.paymentTerms();
    }

    @And("User selects the shipping date")
    public void user_selects_the_shipping_date() throws InterruptedException {
    	
        order.shippingDate();
    }

    @And("User selects the billing address and shipping address")
    public void user_selects_the_billing_address_and_shipping_address() throws InterruptedException {

        order.billingAddress_And_shippingAddress();
    }

    @Then("User selects the Outlet \\(optional) and Delivery User \\(optional)")
    public void user_selects_the_outlet_optional_and_delivery_user_optional() throws InterruptedException {
//        order.deliveryUser();
    }

    @Then("User enters the LPO number")
    public void user_enters_the_lpo_number() throws InterruptedException {
    	Thread.sleep(2000);
        order.LPOnumber();
    }

    @Then("The order is created successfully")
    public void the_order_is_created_successfully() throws InterruptedException {
    	Thread.sleep(2000);
        order.placeOrder();
        Thread.sleep(2000);
        PlaceOrder.orderId = order.captureOrderId();
    }
}



