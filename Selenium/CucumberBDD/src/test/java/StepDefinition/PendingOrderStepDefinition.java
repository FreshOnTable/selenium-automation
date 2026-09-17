package StepDefinition;

import java.util.List;

import PageObject.PendingOrder;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


public class PendingOrderStepDefinition {
	
public Setuptest testsetup;
    
	public PendingOrderStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
	}
	
	@Given("the user selects a business from the list")
	public void the_user_selects_a_business_from_the_list() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Thread.sleep(2000);
		Order.clickgrid();
		Thread.sleep(1000);
		Order.clickorderoption();
		Thread.sleep(2000);
		Order.clickplaceorder();
		Thread.sleep(2000);
		Order.clickbusinessfield();
		Thread.sleep(2000);
		Order.selectBusiness();	
	}

	@Given("the user selects products and enters product quantities")
	public void the_user_selects_products_and_enters_product_quantities() throws Exception {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.selectProduct_And_Quantity();
	}

	@Then("the user selects a payment method")
	public void the_user_selects_a_payment_method() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.paymentTerms();
	}

	@Then("the user selects a shipping date")
	public void the_user_selects_a_shipping_date() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.shippingDate();
	}

	@Then("the user selects the billing and shipping addresses")
	public void the_user_selects_the_billing_and_shipping_addresses() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.billingAddress_And_shippingAddress();
	}

	@Then("the user enters the LPO number")
	public void the_user_enters_the_lpo_number() {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.LPOnumber();
	}
	
	@Then("the user selects the delivery user")
	public void the_user_selects_the_delivery_user() {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.deliveryUser();
	}

	@Then("the order is created successfully")
	public void the_order_is_created_successfully() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.placeOrder();
		PendingOrder.orderId = Order.captureOrderId(); 
	}

	@Then("the page navigates to the View Order page")
	public void the_page_navigates_to_the_view_order_page() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.ViewOrders();
		Order.SearchFilteration();
	}
	
	@Then("the page navigates to the Edit Order page")
	public void the_page_navigates_to_the_edit_order_page() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.clickmore();
		Order.clickalloptions();
		Order.clickedit();
	}
	

	@Then("the user edits the product quantities")
	public void the_user_edits_the_product_quantities() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.editOrder();
		
	}

	@Then("the user reserves the stock")
	public void the_user_reserves_the_stock() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.OrderCompilation();
		Order.SearchFilteration1();
		Order.stockReserve();
		
	}
	
	
	@Then("the user generates the invoice")
	public void the_user_generates_the_invoice() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.ViewOrders1();
		Order.SearchFilteration2();
		Order.clickMoreButton();
		Order.clickShowAllOptions();
		Order.clickEditOrderDetails1();
		Order.clickGenerateInvoiceButton();
		Order.invoiceConfirmation();
	}

	@Then("the user capture the qty in the pending order")
	public void the_user_capture_the_qty_in_the_pending_order() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.ViewOrders2();
		Order.SearchFilteration3();
		Order.clickMoreButton1();
		Order.clickShowAllOptions1();
		Order.clickPendingOrder();
	    Order.qtyCapture();
	}
	
	@Then("the user delivers the order")
	public void the_user_delivers_the_order() throws Exception {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.ViewOrders3();
		Order.SearchFilteration4();
		Order.clickMoreButton2();
		Order.clickShowAllOptions2();
		Order.clickDeliverOrder();
		Order.clickVehicleSelect();
		Order.clickDriverSelect();
		Order.uploadFile();
	
	}
	
	@Then("the user change the status to awaiting payment")
	public void the_user_change_the_status_to_awaiting_payment() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.waitUntilAwaitingPaymentAndClickMore();
		
	}
	
	@Then("the user process the pending Order")
	public void the_user_process_the_pending_Order() throws InterruptedException {
		PendingOrder Order = testsetup.pageObjectManager.getPending_Order();
		Order.SearchFilteration5();
		Order.pendingOrderProcess();
	}
	
	
	
	
}
