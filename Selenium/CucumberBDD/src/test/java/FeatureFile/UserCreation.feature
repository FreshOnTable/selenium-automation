
@tag
Feature: User Creation


  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful
    
    @BusinessAccount
    Scenario: creating a Business Account
 	  Given User navigates to the Administrator Module
 		And User clicks on the Create Organization option
    Then The user selects an organization type from the dropdown
    And Selects "Business" as the organization type
    And Fills in all mandatory fields for Organization Details
    Then Fills in all mandatory fields for Organization Admin Details
    And Fills in all necessary address details
   	And Uploads all mandatory documents
    And Enables all communication channels   
    And Provides additional required information
    Then Finally once users clicks submit button users will create successfully
    And After that User can change the status for Initiated to Active for Business Account
    Then Validate the user is created successfully
    
    @SellerAccount
    Scenario: creating a Supplier Account
    Given User navigates to the Administrator Module
    And User clicks on the Create Organization option
    And User selects "Seller" option as the organization type
    And User fills the Organisation Profile Information
    And User fills the Contact Details
    Then User fills the Organization Admin Details
    And Fills the Address Details   
    And Enables all communication channels if required
    And Provides Additional Information if required
    Then Finally once users clicks submit button users will create successfully
    And Ater that User can change the status for Initiated to Active for Seller Account
    Then Validate the user is created successfully
    
    
    @ConsumerAccount
     Scenario: creating a Supplier Account
    Given User navigates to the Administrator Module
    And User clicks on the Create Organization option
    And User selects "Consumer" option as the organizations type
    And User fills the Organisation Profile Information
    And User fills the Contact Details
    Then User fills the Organization Admin Details
    And Fills the Address Details   
    And Enables all communication channels if required 
    Then Finally once users clicks submit button users will create successfully
    
    @EditOrganisation
    Scenario: Edit Existing Organisation details
    Given User navigates to the Organization module
    And Search created organisation and click edit option in more button
    And Edit the organisation details for required fields
    And Save and update the edit details
    Then Validate the edit information updated correctly
    
    @UpdateAddress1
    Scenario: Update Primary Address details for Org
    Given User navigates to the Organization module
    When Select Existing organisation and click Manage Address option in more button
    Then Update Primary Addreess details for organisation
    And Validate the saved details for updated correctly
    
    
    @UpdateAddress
    Scenario: Add new Address details for Org
    Given User navigates to the Organization module
    When Select existing organisation and click Manage Address option
    Then Add New Address details for organisation
    And Validate the updated address details
    
    
    @AddUser
    Scenario: Create new user for an Existing Organisation    
    Given User navigates to the Administrator Module
 		And User clicks on the Create Organization option
    Then The user selects an organization type from the dropdown
    And Selects "Business" as the organization type
    And Fills in all mandatory fields for Organization Details
    Then Fills in all mandatory fields for Organization Admin Details
    And Fills in all necessary address details
   	And Uploads all mandatory documents
    And Enables all communication channels   
    And Provides additional required information
    Then Finally once users clicks submit button users will create successfully
    And After that User can change the status for Initiated to Active for Business Account
    Then Validate the user is created successfully   
    When User select busines name 
    And User fills all mandotory fields required for user creation
    And User clicks submit button to save the changes and verify the details 
    
    @CreateNodeUser
    Scenario: Create node users/Internal user for Assign node user
    Given User navigate to the Organization module
    When User clicks on the Add User option
    Then User needs to select the Tenant Admin as option
    And User fills all mandotory fields 
    Then User need to select the required user position
   
    
    
    
    
    
    
    
    
    
    
    
    
    
