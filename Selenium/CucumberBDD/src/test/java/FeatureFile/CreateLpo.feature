

Feature: LPO Module
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful


	Scenario Outline: Validate Delivery Hub filter
  Given User navigates to Create LPO page
  When User applies Delivery Hub filter "<hubValue>"
  Then Only orders from Delivery Hub "<expectedHub>" should be displayed
  
  
  Examples:
| hubValue | expectedHub |
| 50127    | Warehouse4A, Belhasa Compound, Al Quoz Industrial Area 3, Dubai, UAE |
| 50508    | Fresh On table, Al Bahyah Al bayah 1, Abu Dhabi |
	
	
	Scenario: Validate Seller
	Given User navigates to Create LPO page
	Then  Only products of selected seller should be displayed after applying filters
	
	
	Scenario: Validate existing Seller
	Given User is on Create LPO page
	Then User filters LPOs by seller name from table


Scenario: only Foc orders filter
Given User navigates to Create LPO page
Then only FOC orders should be displayed with zero order margin


  Scenario Outline: Validate LPO products by Approval Type filter
    Given User navigates to Create LPO page
    When User selects "<ApprovalType>" from Approval Type filter
    And User clicks on Apply Filters button
    Then Only "<ApprovalType>" products should be displayed in LPO list

    Examples:
      | ApprovalType        |
      | Approval Requested  |
      | Approved            |
      | Rejected            |
  
    
  
  Scenario Outline: Change seller
  Given User is on Create LPO page
  When User select random products
  And User clicks on change seller button 
  And User selects a different seller and submits
  Then Seller should be updated successfully in LPO page
  
  
   @Lpo_01
  Scenario Outline: 2nd Page
  Given User is on Create LPO page
  When User creates LPO with random product qty and delivery hub
  Then User selects random delivery hub
  Then LPO should be created successfully
                
