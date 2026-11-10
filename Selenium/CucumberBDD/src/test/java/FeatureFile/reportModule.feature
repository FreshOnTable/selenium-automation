Feature: LPO Module
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful
    
  @downloadReport
  Scenario: Validate Order Exception Report

    Given User navigates to Report Module

    When User selects "Order Exception Report"

    And User enters report start date "05/05/2026"

    And User enters report end date "10/06/2026"

    And User selects download type "Excel"

    And User selects date type "Created Date"
    
    And User selects status "Completed, Cancelled, Awaiting Payment"

    And User clicks Submit

    Then Order Exception Report should be downloaded and validated
    
    And User navigates to Download Reports

    And User searches the captured reference id

    And User waits for report status to be "Completed"

    And User downloads and validates the report

    Then Order Exception Report should be validated
    
    