
@tag
Feature: Delivery Module

  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful
    
     
    Scenario: Creating delivery schedule
    Given Navigate to delivery module  
    When validate the one components atleast on home page
    Then if user wants to create delivery schedule then they need to click "create deleivery schedule" button
    And check order id and enterprise name search field working/not
    And if users wants to change the drivers need to double click on the driver name so that they will get dropdown for driver list
    Then save the changes and do the drag and drop so orders are rearranging based on drivers
    And select the orders with respect to dreiveres and go to the next step
    Then 2nd step select the departure time and vehicle
    And check all the selected orders are visible in this step and click submit.
    And check the delivery schedule created successfully.
    
    
    
    Scenario: Randomly update complete/incomplete order status in schedule level(Trip Details)
    Given Navigate to delivery module  
    When Create delivery schedule with initiated status
    And Use can change the schedule status as Initiated status to Active
    And User go to view trip details page and update the order status complete/incomplete
    Then User can mantain list for incompleted orders and verifys those order are available for reschedules
    And check and verify the incompleted orders available in create schedule page
    
           
    Scenario: Change Driver for unassigned orders
    Given Navigate to delivery module 
    When User can select the unassigned orders for driver
    Then Change the seller and save it
    And validate the change seller 
      
   
 		Scenario: Validate Search filters for schedule id, driver name and driver type
    Given Navigate to delivery module
    When User captures an existing schedule from the page
    And User searches the existing Schedule ID
    Then Schedule ID filter should display matching records

 
		Scenario: Validate Schedule Type filter count
    Given Navigate to delivery module
    When User searches Schedule Type "Delivery"
    Then all displayed schedules should have Schedule Type "Delivery"
    When User clears the Schedule Type filter
    And User searches Schedule Type "Pickup"
    Then all displayed schedules should have Schedule Type "Pickup"    
    
   
    Scenario Outline: Validate schedule status count
    Given Navigate to delivery module
   	When User validates Completed status count
   	When User validates Active status count
   	When User validates Initiated status count
   	When User validates In Progress status count
   	
   	
   	
   	Scenario: Validate incomplete order should be available for reschedule
   	Given Navigate to delivery module
   	When Change the order status to incomplete
   	Then Go to the create schedule page and search the orders to be incompleted 
   	And Varify the orders are vailable for reschedule
   	
   	
   	Scenario: Edit single order
   	Given Navigate to delivery module
   	When Delivery schedule is created with initiated status
   	And User click Edit Scedule Details option
   	And User can select are unselect the orders
   	And Save and validate the edit changes
   	
   	
   	Scenario: Edit Schedule
   	Given Navigate to delivery module
   	When Delivery schedule is created with initiated status
   	Then User click Edit Scedule Details option
   	When User expands the order and selects the checkbox
   	And User clicks Next button
   	Then Selected orders should be displayed under their business
   	
   	
   	Scenario: Delete delivery schedule and create new Schedule with old orders
   	Given Navigate to delivery module
   	When Create Delivery schedule with initiated status 
   	Then Change delivery status to Active to Delete and  
   	And Validate those orders are avalaible for create new schedule
   	
   	
   	 @delivery
   	Scenario Outline: Create pickup schedule for different Pickup By
   	Given Navigate to delivery module
   	When User can select warehouse and departure time
    Then User selects Pickup By "<PickupBy>"
    And User clicks Next button1
    And User selects an available LPOs
    And User creates the pickup schedule with completed status
    
   Examples:
        | PickupBy       |
#        | Admin          |
#        | Seller         |
        | Delivery User  |
        
   	
   	   	
   	Scenario: Validate the number of orders, order value, and Avg value
   	Given Navigate to delivery module
   	When User can check if the no of orders and order value and Avg order value should shows correct
   	And Go to view order page and search the order ids and get the order amount for validating 'Order Value'
   	And Calculate all orders amount with expected order value and validate both value should match
   	And Calculate the same for Avg Order Value
   	And Finally validate all order details 
   	
   	
   	Scenario: Validating COD orders and Vehicle details 
   	Given Navigate to delivery module
   	And User opens the COD Collection page and verify the cash orders which is delivered by cash from delivery app
   	And Validate the details for the order
   	And Verify the Vehicle Details and Fuel Details
   	  
   	
   	
   	