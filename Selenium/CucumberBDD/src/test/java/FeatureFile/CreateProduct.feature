

Feature: Products
  
 Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful
    
    @productcreation
    Scenario Outline: Create new products 
    Given The user is on the "Create Product" page.
    When Click submit button without filling mandatory fields and handle the popup message.
    And Add an product images.
    Then fill all mandatory field which are there in products creation page.
    And Add atleast one variant of the product and fill all the mandatory fields and save the details.
    Then Click submit button validate all mandatory fields and the product is created successfully.
    Given user to click on the view products grid
    When after creating a product its in under initiated status
#   Then select initiated status option in the status filtration  
    And click change status option in more button
    And change the product status as active
    Then click submit button
    And product status changed successfully
    
  
   
    @ViewProducts
  Scenario Outline: View Product
  Given the user is on the View Product page
  When the user changes the status of the product from Active to Inactive
  And the user changes the status of the product from Inactive to Active
  And the user changes the status of the variant from Active to Inactive
  And the user changes the status of the variant from Inactive to Active
  And the user changes the status of the variant from Active to Deleted
  And the user changes the status of the variant from Inactive to Deleted
  And the user changes the status of the variant using Bulk status change from Active to Inactive
  And the user changes the status of the variant using Bulk status change from Inactive to Active
  
  
  @ProductVariant
 Scenario Outline: Product Variant
 Given the user is on the Product Variant page
 When the user changes the status of the product seller-wise from Active to Inactive
 And the user changes the status of the product seller-wise from Inactive to Active
 And the user changes the status of the product seller-wise from Active to Delete
 And the user changes the status of the product seller-wise from Inactive to Delete
 And the user changes the status of the product using bulk status change from Active to Inactive
 And the user changes the status of the product using bulk status change from Inactive to Active
 
 

 
  
    
    
    
    
    
    
    
    
    
    
    