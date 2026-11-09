package stepDefinitions;

import java.util.concurrent.TimeoutException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.OrderCompilation;
import utilities.BaseClass;

public class OrderCompilationSteps {
	
public BaseClass setupclass;
	
	public OrderCompilationSteps(BaseClass setupclass) {
		this.setupclass = setupclass;
	}
		
	@Given("User navigates to the Order Compilation page")
	public void user_navigates_to_the_order_compilation_page() throws InterruptedException {
		OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
		Thread.sleep(2000);
		Order.clickgrid();
		Thread.sleep(1000);
		Order.clickorderProcessing();
		Thread.sleep(2000);
		Order.orderCompilation();
		Thread.sleep(1000);
	}
	
	@And("Search the order Id")
	public void Search_the_order_Id() {
	
	}
	@Then("Clicks more button and Reserve Batch")
	public void clicks_more_button_and_Reserve_batch() {

	}

	@When("the user reserves the stock")
	public void the_user_reserves_the_stock() throws InterruptedException {
		OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
		Thread.sleep(2000);
		Order.stockReserving();
	}
	
	@And("Check details and Submit it")
	public void check_details_and_submit_It() {
	
	}

	@Then("the stock is reserved successfully")
	public void the_stock_is_reserved_successfully() throws InterruptedException{
		OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
		Thread.sleep(2000);
		Order.stockReserve();
	}
	

	//Invoice generation steps
	
@Given("User navigates to the View Order page")
public void User_navigates_to_the_view_order_page() throws InterruptedException {
	OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	Thread.sleep(2000);
	Order.clickgrid();
	Thread.sleep(1000);
	Order.clickOrderOption();
	Thread.sleep(2000);
	Order.clickViewOrder();
	
}

@And("User searches for the Order ID")
public void user_search_the_order_Id() throws InterruptedException {
	 OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	 Thread.sleep(2000);
	 Order.stockReserving();
}
@Then("User clicks the More button and selects Edit Order Details")
public void user_clicks_more_button_and_selects_the_edit_order_details() throws InterruptedException {
	 OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	 Thread.sleep(3000);
//	 Order.clickgrid();
//	 Thread.sleep(3000);
//	 Order.clickOrderOption();
//	 Thread.sleep(3000);
//     Order.clickViewOrder();
//     Thread.sleep(2000);
	 Order.clickMoreButton();
	 Thread.sleep(2000);
	 Order.clickShowAllOptions();
	 Thread.sleep(2000);
	 Order.clickEditOrderDetails1();
}
@Then("User verifies the order details and clicks the {string} button")
public void user_verifies_the_order_details_and_clicks_the(String string) throws InterruptedException {
	OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	Thread.sleep(3000);
	    Order.clickGenerateInvoiceButton();
}
@Then("User gets a confirmation popup and clicks OK")
public void user_gets_a_confirmation_popup_and_clicks_ok() throws InterruptedException {
	OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	Thread.sleep(2000);
	Order.invoiceConfirmation();
   
}
@Then("Order is invoiced successfully")
public void the_order_is_invoiced_successfully() throws InterruptedException {

}

//Order completion steps
@Then("User verifies the order status is {string}")
public void user_verifies_the_order_status_is(String expectedStatus) {

    OrderCompilation order = setupclass.pageObjectManager.getOrderCompilation();

}
@And("User go to more button and click {string} option")
    public void user_go_to_more_button_and_click_option(String option) throws InterruptedException, TimeoutException {
	OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	Thread.sleep(2000);
	 Order.waitUntilAwaitingPaymentAndClickMore();
	 Thread.sleep(2000);
	 Order.clickMoreOption();
	
}
@Then("User select the order and enters the Amount,Transaction id and comments")
    public void user_select_the_order_and_enters_the_amount_transaction_id_and_comments() throws InterruptedException {
	OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
	Order.clickPaymentAgainstInvoice();
	
    }

	@Then("Order is updated to {string} status successfully")
	public void order_is_updated_to_status_successfully(String string) throws InterruptedException {
		OrderCompilation Order = setupclass.pageObjectManager.getOrderCompilation();
        Order.completeOrder();
	}

}
