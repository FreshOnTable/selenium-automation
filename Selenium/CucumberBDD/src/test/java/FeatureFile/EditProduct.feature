
Feature: EditProduct
  
 Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful
    
    
  Scenario Outline: Edit product details successfully
  Given the user is on the "Edit Product" page
  When the user notes the existing product details
  And the user changes the product name to "<ProductName>"
  And the user changes the department to "<Department>"
  And the user changes the product category to "<Category>"
  And the user changes the product subcategory to "<SubCategory>"
  And the user changes the product classification to "<ProductClassification>"
  And the user changes the product temperature to "<ProductTemperature>"  
  Then the updated product details should be displayed successfully
  
  
  Scenario Outline: Edit product variant details successfully
  Given the user is on the "Edit Product" page
  When the user notes the existing product variant details
  And the user changes the weight to "<Weight>"
  And the user changes the purchase price to "<Purchase Price>"
  And the user changes the selling price to "<Selling Price>"
  And the user changes the stock availability to "<Stock Availability>"
  Then the updated product variant details should be displayed successfully
  
  @editProduct
  Scenario Outline: Test cases of product variant details
  Given the user is on the "Edit Product" page
  When the user tries to submit the variant without saving it
  And the user tries to add a duplicate variant
  And the user create the new variant
  And the user deletes the variant and creates the same variant again; the deleted variant should be reactivated.
  
  
  
 



  


  
  
  
    