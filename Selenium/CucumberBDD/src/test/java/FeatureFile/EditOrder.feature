@tag
Feature: Title of your feature
  I want to use this template for my feature file
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful

@EditOrder
Scenario: Validate all functionalities of editing an order
  Given the user navigates to the Edit Order page
  When the user adds a product
  And the user selects the product
  And the user updates the quantity of the product
  And the user deletes the product
  And the user sets the quantity to zero
  And the user changes the unit of measure (UOM)
  And the user updates the product price
  And the user changes the payment terms
  And the user updates the LPO number
  And the user changes the delivery user
  Then the order should be updated successfully
   