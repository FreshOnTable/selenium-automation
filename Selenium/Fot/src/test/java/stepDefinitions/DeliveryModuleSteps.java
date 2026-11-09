package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObject.DeliveryModule;
import utilities.BaseClass;

public class DeliveryModuleSteps {
	
public BaseClass setupclass;
	
	public DeliveryModuleSteps(BaseClass setupclass) {
		this.setupclass = setupclass;
	}
	
	@Given("the user navigates to the Delivery module")
	public void the_user_navigates_to_the_delivery_module() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Order.clickgrid();
		Thread.sleep(1000);
		Order.clickdeliveryoption();
		Thread.sleep(1000);
		Order.clickschedule();
		Thread.sleep(2000);	    
	}

	@Then("the user clicks on the {string} button")
	public void the_user_clicks_on_the_button(String string) throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule(); 
		Thread.sleep(1000);
     	Order.clickcreatedeliveryschedule();
	}

	@Then("the user filters the order by Order ID and updates the driver")
	public void the_user_filters_the_order_by_order_id_and_updates_the_driver() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Thread.sleep(2000);
		Order.OrderFilteration();
	}

	@Then("the user saves the changes and performs drag-and-drop")
	public void the_user_saves_the_changes_and_performs_drag_and_drop() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Thread.sleep(3000);
		Order.DragAndDrop();
	}

	@Then("the user selects the order and proceeds to the next step")
	public void the_user_selects_the_order_and_proceeds_to_the_next_step() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Order.OrderSelection();
	}

	@Then("the user selects the departure time and vehicle")
	public void the_user_selects_the_departure_time_and_vehicle() {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Order.selectVehicleAndTime();
	}

	@Then("the user verifies that all selected orders are displayed and clicks Submit")
	public void the_user_verifies_that_all_selected_orders_are_displayed_and_clicks_submit() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Order.Submit();
	}

	@Then("the delivery schedule is created successfully")
	public void the_delivery_schedule_is_created_successfully() throws InterruptedException {
		DeliveryModule Order = setupclass.pageObjectManager.getdeliveryModule();
		Order.scheduleCreated();
	    Order.CompletingSchedule();
	}


}
