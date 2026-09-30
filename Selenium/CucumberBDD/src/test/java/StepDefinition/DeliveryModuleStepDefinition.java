package StepDefinition;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import PageObject.CreateLpo;
import PageObject.Delivery_Module;
import PageObject.Delivery_Module.SelectedLPO;
import PageObject.Delivery_Module.SelectedOrder;
import PageObject.PlaceOrder;
import PageObject.stockSummaryPage;
import Utils.Setuptest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.Duration;
import Utils.Global;

public class DeliveryModuleStepDefinition {
	
public Setuptest testsetup;

private String existingScheduleId;
private String existingDriverType;
private String existingDriverSellerName;

private List<SelectedOrder> selectedOrders;
private String ExpandOrderAndSelectCheckbox;
private String validateSelectedOrders;

private List<String> selectedOrderIds;
private String pickupBy;
private List<SelectedLPO> selectedOrders1;
private String ExpandAndSelectAllLPOs;
private String validateSelectedLPOs;
private List<SelectedLPO> SelectedLPOs;

private List<SelectedLPO> selectedLPOs;
public String orderId;

private Global scheduleSummary;
private List<String> scheduleOrderIds;
private double totalOrderValue;
private String captureScheduleSummary;
private List<String> incompleteOrderIds;


	public DeliveryModuleStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
		WebDriver driver;
	}
	@Given("Navigate to delivery module")
	public void navigate_to_delivery_module() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.clickgrid();
		Thread.sleep(1000);
		schedule.clickdeliveryoption();
		Thread.sleep(1000);
		schedule.clickschedule();
		Thread.sleep(2000);
	}
	@When("validate the one components atleast on home page")
	public void validate_the_one_components_atleast_on_home_page() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.validatehomepage();
		
	}
	@Then("if user wants to create delivery schedule then they need to click {string} button")
	public void if_user_wants_to_create_delivery_schedule_then_they_need_to_click_button(String string) throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(3000);
		schedule.clickCreateDeliveryScheduleBtn();
	}
	@Then("check order id and enterprise name search field working\\/not")
	public void check_order_id_and_enterprise_name_search_field_working_not() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
//		order.searchfieldvalidation();
	}
	@Then("if users wants to change the drivers need to double click on the driver name so that they will get dropdown for driver list")
	public void if_users_wants_to_change_the_drivers_need_to_double_click_on_the_driver_name_so_that_they_will_get_dropdown_for_driver_list() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
//		order.changedrivers();
//		order.assignRandomDrivers();
	}
	@Then("save the changes and do the drag and drop so orders are rearranging based on drivers")
	public void save_the_changes_and_do_the_drag_and_drop_so_orders_are_rearranging_based_on_drivers() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(5000);
//		order.draganddrop();
		schedule.DragAndDrop();
		schedule.OrderSelection();
		Thread.sleep(2000);
		schedule.clickNext();
		Thread.sleep(3000);
		schedule.selectRandomVehicleAndTime();
	}
	@Then("select the orders with respect to dreiveres and go to the next step")
	public void select_the_orders_with_respect_to_dreiveres_and_go_to_the_next_step() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		schedule.clickNextBtn();
	}
	@Then("2nd step select the departure time and vehicle")
	public void nd_step_select_the_departure_time_and_vehicle() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
	}
	@Then("check all the selected orders are visible in this step and click submit.")
	public void check_all_the_selected_orders_are_visible_in_this_step_and_click_submit() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.changeStatusInitiatedToActive();
	}
	@Then("check the delivery schedule created successfully.")
	public void check_the_delivery_schedule_created_successfully() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.CompletingSchedule();
	}
	
	
//Incomplete orders for schedule level
	
	@When("Create delivery schedule with initiated status")
	public void create_delivery_schedule_with_initiated_status1() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
	}
	@When("Use can change the schedule status as Initiated status to Active")
	public void use_can_change_the_schedule_status_as_initiated_status_to_active() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.clickCreateDeliveryScheduleBtn();
		Thread.sleep(5000);
		schedule.DragAndDrop();
		schedule.OrderSelection();
		Thread.sleep(2000);
		schedule.clickNext();
		Thread.sleep(3000);
		schedule.selectRandomVehicleAndTime();
		Thread.sleep(2000);
		schedule.clickNextBtn();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
		Thread.sleep(1000);
		schedule.changeStatusInitiatedToActive();
	}
	
	@When("User go to view trip details page and update the order status complete\\/incomplete")
	public void user_go_to_view_trip_details_page_and_update_the_order_status_complete_incomplete() throws InterruptedException {
	    
	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    Thread.sleep(1000);

	    incompleteOrderIds =
	            schedule.randomlyUpdateincompleteOrderSchedule();

	}
	
	@Then("User can mantain list for incompleted orders and verifys those order are available for reschedules")
	public void user_can_mantain_list_for_incompleted_orders_and_verifys_those_order_are_available_for_reschedules() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		schedule.clickBackBtn();
	}
	@Then("check and verify the incompleted orders available in create schedule page")
	public void check_and_verify_the_incompleted_orders_available_in_create_schedule_page() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		
//		List<String> incompleteOrderIds =  schedule.randomlyUpdateincompleteOrderSchedule();
		
		Thread.sleep(3000);
		schedule.clickCreateDeliveryScheduleBtn();

		schedule.verifyIncompleteOrdersAvailableForReschedule(incompleteOrderIds);
		
	}
	

	
//Change driver
	
	@When("User can select the unassigned orders for driver")
	public void user_can_select_the_unassigned_orders_for_driver() throws InterruptedException {
		Delivery_Module order = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		order.clickCreateDeliveryScheduleBtn();
		
	}
	@Then("Change the seller and save it")
	public void change_the_seller_and_save_it() throws InterruptedException {
		Delivery_Module order = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(5000);
		order.captureDriverDetailsBeforeChange();
		Thread.sleep(3000);
		order.clickChangeDriverBtn();
		order.assignDriverToUnassignedOrders();
		Thread.sleep(1000);
		order.clickSaveBtn();
		Thread.sleep(1000);
		order.clickYesBtn();
		Thread.sleep(1000);
		order.clickOKBtn();
	}
	@And("validate the change seller")
	public void validate_the_change_seller() {
		Delivery_Module order = testsetup.pageObjectManager.getDelivery_Module(); 
		order.validateDriverChanges();
	}
	
	
	
// validate the schedule id search filter
	@When("User captures an existing schedule from the page")
	public void user_captures_an_existing_schedule_from_the_page() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.searchRandomExistingScheduleId();
		Thread.sleep(1000);
		schedule.clearInput();
	}
	@When("User searches the existing Schedule ID")
	public void user_searches_the_existing_schedule_id() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.searchExistingDriverType();
		Thread.sleep(1000);
		schedule.clearInput2();
	    
	}
	
	@Then("Schedule ID filter should display matching records")
	public void schedule_id_filter_should_display_matching_records() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.searchExistingSellerType();	    
	}
	
// Schedule Type count
	@When("User captures an existing schedule type from the page")
	public void user_captures_an_existing_schedule_type_from_the_page() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.printScheduleTypeCount();
	}
	@When("User searches both schedule Type")
	public void user_searches_both_schedule_type() {
	    
	}
	@Then("Schedule Type filter should display matching records\\/count")
	public void schedule_type_filter_should_display_matching_records_count() {
	    
	}

// search schedule type
	@When("User searches Schedule Type {string}")
	public void user_searches_schedule_type(String scheduleType) throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module(); 
		Thread.sleep(1000);
		schedule.validateScheduleTypeFilter(scheduleType);
		Thread.sleep(2000);
//		schedule.clearInput3();
	}
	@Then("all displayed schedules should have Schedule Type {string}")
	public void all_displayed_schedules_should_have_schedule_type(String string) {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	@When("User clears the Schedule Type filter")
	public void user_clears_the_schedule_type_filter() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module(); 
		Thread.sleep(1000);
		schedule.clearScheduleTypeFilter();
	}
	
//validate status box count
	
//	@When("User validates {string} status count")
//	public void user_validates_status_count(String status)
//	        throws InterruptedException {
//
//	    Delivery_Module schedule =
//	            testsetup.pageObjectManager.getDelivery_Module();
//
//	    schedule.validateStatusCount(status);
//	}
	
	@When("User validates Completed status count")
	public void user_validates_completed_status_count()
	        throws InterruptedException {

	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    schedule.validateStatusCount("Completed");
	}
	
	@When("User validates Active status count")
	public void user_validates_active_status_count()
	        throws InterruptedException {

	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    schedule.validateStatusCount("Active");
	}
	
	@When("User validates Initiated status count")
	public void user_validates_initiated_status_count()
	        throws InterruptedException {

	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    schedule.validateStatusCount("Initiated");
	}
	
	@When("User validates In Progress status count")
	public void user_validates_in_progress_status_count()
	        throws InterruptedException {

	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    schedule.validateStatusCount("In Progress");
	}
	
	
// Incomplete orders
	@When("Change the order status to incomplete")
	public void change_the_order_status_to_incomplete() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.clickCreateDeliveryScheduleBtn();
		Thread.sleep(2000);
		schedule.DragAndDrop();
		Thread.sleep(1000);
		schedule.OrderSelection();
		Thread.sleep(2000);
		schedule.clickNext();
		Thread.sleep(3000);
		schedule.selectRandomVehicleAndTime();
		Thread.sleep(2000);
		schedule.clickNextBtn();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
		Thread.sleep(1000);
		schedule.changeStatusInitiatedToActive();
		
	}
	@Then("Go to the create schedule page and search the orders to be incompleted")
	public void go_to_the_create_schedule_page_and_search_the_orders_to_be_incompleted() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);		
//		schedule.ReSchedule();
		schedule.ReScheduleforIncompleteOrdes();
	}
	@Then("Varify the orders are vailable for reschedule")
	public void varify_the_orders_are_vailable_for_reschedule() {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	
	
// Edit schedule
	@When("Delivery schedule is created with initiated status")
	public void delivery_schedule_is_created_with_initiated_status() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.clickCreateDeliveryScheduleBtn();
		Thread.sleep(5000);
		schedule.DragAndDrop();
		Thread.sleep(2000);
		schedule.OrderSelection1();
		Thread.sleep(2000);
		schedule.clickNext();
		Thread.sleep(3000);
		schedule.selectRandomVehicleAndTime();
		Thread.sleep(2000);
		schedule.clickNextBtn();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
		Thread.sleep(1000);
//		schedule.changeStatusInitiatedToActive();
	}
	@When("User click Edit Scedule Details option")
	public void user_click_edit_scedule_details_option() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.clickEditSchedule();
		Thread.sleep(2000);
		schedule.clickNextBtn();
	}
	@When("User can select are unselect the orders")
	public void user_can_select_are_unselect_the_orders() throws InterruptedException {
		stockSummaryPage stockSummaryPage = testsetup.pageObjectManager.getStockSummaryPage();
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(5000);		
		schedule.expandAndSelectSingleOrder();

		Thread.sleep(2000);
		schedule.clickNextBtn();
	}
	@When("Save and validate the edit changes")
	public void save_and_validate_the_edit_changes() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(2000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
	}

//edit order
	@When("User expands the order and selects the checkbox")
	public void UserExpandsOrderAndSelectsCheckbox()
	        throws InterruptedException {

	    Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();
     Thread.sleep(3000);
	    selectedOrders =
	            schedule.ExpandOrderAndSelectAllOrders();

	    System.out.println("Selected Orders:");

	    for (SelectedOrder order : selectedOrders) {

	        System.out.println(
	                "Order ID = " + order.getOrderId()
	                + " | Business = " + order.getBusinessName()
	        );
	    }
	}
	
	@When("User clicks Next button")
	public void UserClicksNextButton() throws InterruptedException {
		Delivery_Module schedule =testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(1000);
		schedule.NextButtonClick();

	}
	
	@Then("Selected orders should be displayed under their business")
	public void SelectedOrdersShouldBeDisplayedUnderTheirBusiness() throws InterruptedException {
		Delivery_Module schedule =testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		schedule.validateSelectedOrders(selectedOrders);		
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
		Thread.sleep(1000);
	}
	
//Delete schedule
	@When("Create Delivery schedule with initiated status")
	public void create_delivery_schedule_with_initiated_status() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.clickCreateDeliveryScheduleBtn();
		Thread.sleep(5000);
		schedule.DragAndDrop();
		Thread.sleep(2000);
//		schedule.OrderSelection1();
		selectedOrderIds = schedule.selectAllOrdersUnderDriver();

//	    System.out.println("Captured Order IDs:");

	    for (String orderId : selectedOrderIds) {
//	        System.out.println(orderId);
	    }
		Thread.sleep(2000);
		schedule.clickNext();
		Thread.sleep(3000);
		schedule.selectRandomVehicleAndTime();
		Thread.sleep(2000);
		schedule.clickNextBtn();
		Thread.sleep(2000);
		schedule.clickSubmitBtn();
		Thread.sleep(3000);
		schedule.clickConfirmBtn();
		Thread.sleep(1000);
		schedule.clickOKBtn();
		Thread.sleep(1000);
		schedule.changeStatusInitiatedToActive();
		Thread.sleep(2000);
		schedule.deleteSchedule();
		Thread.sleep(3000);
		schedule.clickCreateDeliveryScheduleBtn();
	}
	@Then("Change delivery status to Active to Delete and")
	public void change_delivery_status_to_active_to_delete_and() {
	   
	}
	@Then("Validate those orders are avalaible for create new schedule")
	public void validate_those_orders_are_avalaible_for_create_new_schedule() throws InterruptedException {
		Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(5000);
	    schedule.verifyOrdersAvailableForReschedule(
	            selectedOrderIds
	    );
	}
	
	
// Pickup Schedule
	@When("User can select warehouse and departure time")
	public void user_can_select_warehouse_and_departure_time() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		
		Delivery_Module delivery =
	            testsetup.pageObjectManager.getDelivery_Module();
	
		Thread.sleep(1000);
	    schedule.clickCreatePickupScheduleBtn();
		Thread.sleep(1000);
	    schedule.selectDepartureTime();
	    Thread.sleep(1000);
	    schedule.selectDeliveryHub();
	    Thread.sleep(2000);
	    	    
	}
	@Then("User selects Pickup By {string}")
	public void user_selects_pickup_by(String pickupBy) throws InterruptedException {
		
		 this.pickupBy = pickupBy;
		 
//		 System.out.println("Pickup By received from feature: [" + pickupBy + "]");

		    Delivery_Module deliveryModule =
		            testsetup.pageObjectManager.getDelivery_Module();

		    deliveryModule.selectPickupBy(pickupBy);
	    
	}
	
	@When("User clicks Next button1")
	public void UserClicksNextButton1() throws InterruptedException {
//		Delivery_Module schedule =testsetup.pageObjectManager.getDelivery_Module();

		Delivery_Module schedule =
	            testsetup.pageObjectManager.getDelivery_Module();

	    System.out.println("====================================");
	    System.out.println("Pickup By selected: " + pickupBy);
	    System.out.println("Processing Pickup By flow...");
	    System.out.println("====================================");

	    // FIRST handle additional fields
	    schedule.handlePickupByFlow(pickupBy);

	    // THEN click Next
	    schedule.NextButtonClick();

	}
	
	@When("User selects an available LPOs")
	public void user_selects_an_available_lp_os() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();

		
		 Thread.sleep(15000);

		    selectedLPOs =
		            schedule.ExpandAndSelectAllLPOs();

//		    System.out.println("====================================");
//		    System.out.println("CAPTURED LPOs");
//		    System.out.println("====================================");
//
//		    for (SelectedLPO lpo : selectedLPOs) {
//
//		        System.out.println(
//		            "LPO = " + lpo.getLpoNumber()
//		            + " | Seller Name = "
//		            + lpo.getSellerName()
//		        );
//		    }
		}
	    	    
	   
	@When("User creates the pickup schedule with completed status")
	public void user_creates_the_pickup_schedule_with_completed_status() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		Thread.sleep(2000);
		// Move from Select LPOs → Review
	    schedule.NextButtonClick();

	    Thread.sleep(2000);

	    // Validate captured Seller Names
//	    schedule.validateSelectedLPOs(selectedLPOs);

	    // Submit
	    schedule.clickSubmitBtn();

	    Thread.sleep(3000);

	    schedule.clickConfirmBtn();

	    Thread.sleep(1000);

	    schedule.clickOKBtn();
	    
	    Thread.sleep(1000);
		schedule.changeStatusInitiatedToActive();
		
		Thread.sleep(2000);
		schedule.CompletingSchedule();
	}
	
	
//Validate the no of orders and order value and Avg order value
	
	@When("User can check if the no of orders and order value and Avg order value should shows correct")
	public void user_can_check_if_the_no_of_orders_and_order_value_and_avg_order_value_should_shows_correct() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		
		Thread.sleep(2000);
		// Capture first schedule summary
	    scheduleSummary =
	            schedule.captureFirstScheduleSummary();

	    // Click first schedule
	    schedule.clickFirstSchedule();

	    // Capture Order IDs from View Schedule
	    scheduleOrderIds =
	            schedule.captureAllScheduleOrderIds();
	    
	 // =========================================================
	    // STEP 1 - VALIDATE NO OF ORDERS
	    // =========================================================

	    int expectedNoOfOrders =
	            scheduleSummary.getNoOfOrders();

	    int actualNoOfOrders =
	            scheduleOrderIds.size();

	    System.out.println("====================================");
	    System.out.println("NO OF ORDERS VALIDATION");
	    System.out.println("====================================");
	    System.out.println("Expected No Of Orders = " + expectedNoOfOrders);
	    System.out.println("Actual No Of Orders   = " + actualNoOfOrders);

	    Assert.assertEquals(
	            actualNoOfOrders,
	            expectedNoOfOrders,
	            "No of Orders validation failed!"
	    );

	    System.out.println("PASS: No of Orders validation passed.");
		 
	}
	
//	@When("Verify the details")
//	public void verify_the_details() throws InterruptedException {
//		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
//					 
//	}
	
	@When("Go to view order page and search the order ids and get the order amount for validating {string}")
	public void go_to_view_order_page_and_search_the_order_ids_and_get_the_order_amount_for_validating(String string) {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	@When("Calculate all orders amount with expected order value and validate both value should match")
	public void calculate_all_orders_amount_with_expected_order_value_and_validate_both_value_should_match() {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	@When("Calculate the same for Avg Order Value")
	public void calculate_the_same_for_avg_order_value() {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	@When("Finally validate all order details")
	public void finally_validate_all_order_details() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		
		// Navigate to View Orders
	    schedule.navigateToViewOrders();
	    
	    Thread.sleep(4000);
	    
	    // Use scheduleOrderIds here
	    double totalOrderValue =
	            schedule.calculateTotalOrderValue(scheduleOrderIds);

	    // Compare with captured schedule values
	    schedule.validateOrderValue(
	            scheduleSummary,
	            totalOrderValue
	    );
	    
		schedule.validateAverageOrderValue(
				scheduleSummary, 
				totalOrderValue
				);
	}
	
//COD Collection
	@Given("User opens the COD Collection page and verify the cash orders which is delivered by cash from delivery app")
	public void user_opens_the_cod_collection_page_and_verify_the_cash_orders_which_is_delivered_by_cash_from_delivery_app() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
//		schedule.clickCODCollection();
	}
	@Given("Validate the details for the order")
	public void validate_the_details_for_the_order() {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
	}
	@Given("Verify the Vehicle Details and Fuel Details")
	public void verify_the_vehicle_details_and_fuel_details() throws InterruptedException {
		Delivery_Module schedule = testsetup.pageObjectManager.getDelivery_Module();
		schedule.captureVehicleDetails();
		Thread.sleep(1000);
		schedule.captureFuelDetails();
	}
	
}
