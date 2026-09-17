@tag
Feature: Title of your feature
  I want to use this template for my feature file
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful

@PendingOrder
Scenario Outline: Validate all test cases for a Pending Order

And the user selects a business from the list
And the user selects products and enters product quantities
Then the user selects a payment method
And the user selects a shipping date
And the user selects the billing and shipping addresses
Then the user enters the LPO number
And the user selects the delivery user
Then the order is created successfully
And the page navigates to the View Order page
And the page navigates to the Edit Order page
And the user edits the product quantities
And the user reserves the stock 
And the user generates the invoice 
And the user capture the qty in the pending order
And the user delivers the order
And the user change the status to awaiting payment
And the user process the pending Order


    
    