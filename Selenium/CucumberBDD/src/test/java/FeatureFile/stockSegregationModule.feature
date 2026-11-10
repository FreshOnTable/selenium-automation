
@tag
Feature: Title of your feature
  I want to use this template for my feature file
  
  Background:
	  Given Launch the browser
    When Login with username and password
    Then User is landing on the dashboard
    And Veryfy the login is successful

   @stock_01
   Scenario: Lpo stock validation
   Given User is on Create LPO page
   When Select some existing products in lpo page
   When User changes any 1 LPO to the "Awaiting for Acceptance" status for completing LPO 
   Then Open the stock segregation page and search the LPO 
   And Click more button and capture the rejection
   Then User can capture the rejection and submit it
   And Validate the rejection in stock summary page
   
   