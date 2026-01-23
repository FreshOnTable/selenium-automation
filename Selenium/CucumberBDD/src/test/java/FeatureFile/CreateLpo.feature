

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
	Given User navigates to Create LPO page1
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
  
  @Lpo_01      
  Scenario Outline: Change seller
  Given User navigates to Create LPO page1
  When select random products
  Then click change seller button 
  And change seller randomly and save changes
  And validate the changed seller in first page
                
