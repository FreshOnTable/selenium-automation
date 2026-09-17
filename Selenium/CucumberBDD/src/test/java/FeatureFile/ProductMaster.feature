Feature: Product Master
  
 Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful

@productmaster
Scenario: Create, View and Update the Product Master

    Given the user is on the Product Master Page

    When the user creates the Product Line of the product

    And the user creates the Product Category of the product

    And the user creates the Product Sub Category of the product
    
     And the user updating the Description of the Product Line
     
     And the user updating the Description of the Product Category
     
     And the user updating the Description of the Product Sub Category

    And the user updating the Description Temperature

    And the user updating the Description UOM
    
    And the user updating the Description Allergens

    And the user updating the Description Farm Technology
  
    
