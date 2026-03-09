
@RegressionTesting

Feature: End to End order Process

Background:
    Given User Launch the browser
    When User opens URL "https://mktadmin.freshontable.com/"
    And User enters username "UAEADMIN" and password "Admin@4321"
    And Click on login button
    Then Page Title should be "FRESHONTABLE"
    And Log in is successfull
    
 Scenario: Order Creation
    Given User navigates to the Place Order page
    And User selects a business from the list
    And User selects the products and enters product quantity
    Then User selects the payment method
    And User selects the shipping date
    And User selects the billing address and shipping address
    Then User enters the LPO number
    And User selects the Outlet (optional) and Delivery User (optional)
    Then The order is created successfully
    
    
 Scenario: Edit Order
     Given User Navigates to the Edit Order Page 
     And User Add the Product
     And User select the products
     And User Edit the qty for the product

 
   Scenario: Lpo Creation
    Given User navigates to the Create LPO page
    And User selects products which are already approved
    And User moves to the second page
    And User moves to the third page
    Then User clicks the Submit button and confirms
    And the LPO is created successfully
    Then User changes any 4 LPOs to the "Waiting for Acceptance" status
    And LPO fulfillment Process
    And User changes the LPO status to "LPO at Hub"
    And User changes the LPO status to "Fulfilled"
    And User fills all mandatory fields to "Fulfill LPO"
    And User verifies and approves the LPO
    Then LPO is completed successfully 
 
 
  Scenario: Stock Validation
Given User navigates to the LPO Page and validates the data  
And User fetches the data from the LPO Page  
And User enters the Product ID
And User enters the Product Name  
And User enters the UOM
And User checks the seller and clicks on the seller  
And User clicks on the Batch Details of that product  
And then User clicks the LPO Number  
And User validates that the stock entry quantity in LPO and the updated stock quantity in Stock Batch Details are correct
And User validates all products one by one

      
  #Scenario: Order Compilation
  #Given User navigates to the Order Compilation page
  #And Search the order Id
  #Then Clicks more button and Reserve Batch
  #When the user reserves the stock
  #And Check details and Submit it
  #Then the stock is reserved successfully
#
  #
 #Scenario: Delivery Schedule
  #Given the user navigates to the Delivery module
  #Then the user clicks on the "Create Delivery Schedule" button
  #And the user filters the order by Order ID and updates the driver
  #Then the user saves the changes and performs drag-and-drop
  #And the user selects the order and proceeds to the next step
  #Then the user selects the departure time and vehicle
  #And the user verifies that all selected orders are displayed and clicks Submit
  #Then the delivery schedule is created successfully
 #
  #Scenario: Generate Invoice
  #Given User navigates to the View Order page
  #And User searches for the Order ID
  #When User clicks the More button and selects Edit Order Details
  #And User verifies the order details and clicks the "Generate Invoice" button
  #Then User gets a confirmation popup and clicks OK
  #And Order is invoiced successfully
  #Then User verifies the order status is "Awaiting Payment"
  #And User go to more button and click "Payment Against Invoice" option
  #Then User select the order and enters the Amount,Transaction id and comments
  #And Order is updated to "Completed" status successfully
    #
@Regression1 
   Scenario: Receiving zero qty with validating debit note and rejection note
   Given User navigates to the Create LPO page
   Then User changes any 1 LPO to the "Awaiting for Acceptance" status1
   When User completes LPO with zero received qty
   And User changes any 1 LPO to the "Partially Fulfilled And Awaiting For Approval" Lpo status
    #
