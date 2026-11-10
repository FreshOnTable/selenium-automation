

Feature: LPO Module
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful


	Scenario Outline: By selecting Delivery Hub create LPOs
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


 Scenario: Create FOC LPO
  Given User navigates to Create LPO page
  Then only FOC orders should be displayed with zero order margin


  Scenario Outline: Validate Approval Types 
    Given User navigates to Create LPO page
    When User selects "<ApprovalType>" from Approval Type filter
    And User clicks on Apply Filters button
    Then Only "<ApprovalType>" products should be displayed in LPO list

    Examples:
      | ApprovalType        |
      | Approval Requested  |
      | Approved            |
      | Rejected            |
      | All                 |
  
    @Lpo_01
  Scenario Outline: Change seller
  Given User is on Create LPO page
  When User select random products
  And User clicks on change seller button 
  And User selects a different seller and submits
  Then Seller should be updated successfully in LPO page
  
   @Lpo_02  
  Scenario: Add more products in 2nd Page
  Given User is on Create LPO page
  When User creates LPO with random product qty and delivery hub
  Then User selects random delivery hub
  Then LPO should be created successfully
  

Scenario Outline: LPO Acceptance
  Given User is on Create LPO page
  When User changes any 1 LPO to the "Awaiting for Acceptance" status
  When User changes the LPO status to "<Status>" with value "<Value>"
  Then LPO status should be updated to "<Status>"
  And User completes the LPO process

Examples:
  | Value | Status                     |
  | 109   | Accepted But Unfulfilled   |
  | 110   | Partially Accepted         |
  | 770   | Rejected                   |
  | 771   | Cancelled                  |
   
                  
Scenario: Add more batches for QC rejection
	Given User is on Create LPO page
	When User changes any 1 LPO status to the "Awaiting for Acceptance"
	When User rejects some qty in QC check
	Then User will add batches for rejected qty
	And User will do Verify and Approve batches 
	Then Fullfullment is completed 
	And LPO status should updated as LPO Completed
	 
	 
   Scenario: generate Debit note and Rejection note
   Given User is on Create LPO page
   Then User changes any 1 LPO to the "Awaiting for Acceptance" status1
   When User completes LPO with zero received qty
   And User changes any 1 LPO to the "Partially Fulfilled And Awaiting For Approval" Lpo status 
   
   @Lpo_03
   Scenario: Edit LPO 
   Given User is on Create LPO page
   When Select some existing products
   And Select delivery hub for all products
   And Lpo created with 'Waiting For Acceptance' status
   Then Edit the Lpo with random qty
   And Save the changes
   
    @Lpo_04
   Scenario: Add procurement comments
   Given User is on Create LPO page
   When Click more button for available products in Lpo page
   And Click add procurement comments
   Then Select suitable procurement comment
   And Save the comments
   
    
  Scenario: Request for Approval products
   Given User is on Create LPO page
   When Select the product for an approval request 
   And Click more button and send approval request 
   Then Confirm the approval request sent  
   
    
   Scenario: Lpo stock validation
   Given User is on Create LPO page
   When Select some existing products in lpo page
   When User changes any 1 LPO to the "Awaiting for Acceptance" status for completing LPO 
   Then Open the stock summary page and search the product 
   And validate the stock entry is happend or not
   
