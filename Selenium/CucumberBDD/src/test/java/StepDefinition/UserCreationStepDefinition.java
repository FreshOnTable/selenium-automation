package StepDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

import PageObject.UserCreation;
import Utils.Setuptest;
import Utils.TestDataGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Utils.Global;

public class UserCreationStepDefinition {
	
	private String generatedPinCode;
	private String generatedTRN;
	
	private String businessFirstName;
	private String businessLastName;
	
	private String addressLine1;
	private String addressLine2;
	
	private String mobileNumber;
	private String email;
	
	private String generatedOrgName;
	
	private String newOrganizationName = "Test Organization Name";
	private String farmClientName = " Test Org Name123";
	
	
//	private Global global = new Global();
	Setuptest testsetup;
	public UserCreationStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
	}
	
	@Given("User navigates to the Administrator Module")
	public void user_navigates_to_the_administrator_module() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(1000);
		userCreation.clickgrid();
		Thread.sleep(1000);
		userCreation.clickAdministrationoption();
		
	}

	@Given("User clicks on the Create Organization option")
	public void user_clicks_on_the_create_organization_option() throws InterruptedException {
	    UserCreation user = testsetup.pageObjectManager.getUserCreation();
	    Thread.sleep(2000);
	    user.clickCreateOrganisation();
	}

	@Then("The user selects an organization type from the dropdown")
	public void the_user_selects_an_organization_type_from_the_dropdown() throws InterruptedException {
		UserCreation user = testsetup.pageObjectManager.getUserCreation();
		user.enterOrgName();
	}
		
	@And("Selects \"Business\" as the organization type")
	public void selects_business_as_the_organization_type() throws InterruptedException {
	    UserCreation user = testsetup.pageObjectManager.getUserCreation();
	    user.orgTypebusiness();
	}

	@Then("Fills in all mandatory fields for Organization Details")
	public void fills_in_all_mandatory_fields_for_organization_details() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		businessFirstName =
	            TestDataGenerator.generateBusinessFirstName();

	    businessLastName =
	            TestDataGenerator.generateBusinessLastName();
	    
		userCreation.enterBusinessFirstName(businessFirstName);
	    userCreation.enterBusinessLastName(businessLastName);
		
		generatedPinCode = TestDataGenerator.generatePinCode();
	    generatedTRN = TestDataGenerator.generateTRN();
	    userCreation.enterTRN(generatedTRN);
		
	    userCreation.selectGrading();
	    userCreation.selectCategory();
//	    userCreation.mobileNo();
//		Thread.sleep(2000);
//		userCreation.Email();
	    
	    mobileNumber =
	            TestDataGenerator.generateMobileNumber();

	    email =
	            TestDataGenerator.generateEmail();

	    System.out.println("Mobile Number : " + mobileNumber);
	    System.out.println("Email        : " + email);
	    
	    userCreation.entermobileNo(mobileNumber);
	    userCreation.enterEmail(email);
	}

	@Then("Fills in all mandatory fields for Organization Admin Details")
	public void fills_in_all_mandatory_fields_for_organization_admin_details() throws InterruptedException {
		UserCreation user = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(2000);
		user.UserNametype();
		user.Password();
		user.confirmPassword();
	}

	@Then("Fills in all necessary address details")
	public void fills_in_all_necessary_address_details() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		 addressLine1 =
		            TestDataGenerator.generateDubaiAddressLine1();

		    addressLine2 =
		            TestDataGenerator.generateDubaiAddressLine2();
		    
		    userCreation.enterAddressLine1(addressLine1);
		    userCreation.enterAddressLine2(addressLine2);
		
		generatedPinCode = TestDataGenerator.generatePinCode();
		userCreation.enterPinCode(generatedPinCode);
		
		userCreation.Location();
		Thread.sleep(2000);
		userCreation.Country();
		Thread.sleep(1000);
		userCreation.State();
		Thread.sleep(1000);
		userCreation.Zone();
		Thread.sleep(1000);
		userCreation.City();
	}
	
	@Then("Uploads all mandatory documents")
	public void uploads_all_mandatory_documents() throws InterruptedException {
		
	    UserCreation userCreation =
	            testsetup.pageObjectManager.getUserCreation();
	    
	    userCreation.uploadAllDocuments();

//	    userCreation.uploadVATCertificate();
//	    userCreation.uploadCreditApplicationForm();
//	    userCreation.uploadTradeLicense();
	}

	@Then("Enables all communication channels")
	public void enables_all_communication_channels() throws InterruptedException {
		UserCreation user = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(1000);
		user.paymentTerms();
	}

	
	@Then("Provides additional required information")
	public void provides_additional_required_information() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		
		  userCreation.selectRandomOrganisationTag();

		    userCreation.selectRandomBillingEntity();
		    Thread.sleep(3000);
		    userCreation.selectRandomSalesUser();
		    
		    
	}
	
	@Then("Finally once users clicks submit button users will create successfully")
	public void finally_once_users_clicks_submit_button_users_will_create_successfully() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(1000);
	    userCreation.submitForm();
	}
	@And("After that User can change the status for Initiated to Active for Business Account")
	public void ater_that_user_can_change_the_status_for_initiated_to_active_for_business_account() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(2000);
		userCreation.clickgrid();
		Thread.sleep(1000);
		userCreation.clickOrganisationMenue();
		Thread.sleep(2000);
		userCreation.selectBusinessOption();
		Thread.sleep(1000);
		
		String organizationGetName=Global.getGeneratedOrgName();

	    userCreation.searchOrganization(organizationGetName);
	    Thread.sleep(1000);
	    userCreation.changeStatusfor_InitiatedToActive();
	    Thread.sleep(1000);
	    userCreation.approveOrganisation();
	    Thread.sleep(1000);
	    userCreation.checkOrgStatusChangedToActive();
	    Thread.sleep(1000);		
		userCreation.validateOrgStatusActive(organizationGetName);
		
	}
	
	@Then("Validate the user is created successfully")
	public void validate_the_users_is_created_successfully()
			throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(2000);
		userCreation.clickgrid();
		Thread.sleep(1000);
		userCreation.navigateUsersPage();
		String organizationGetName=Global.getGeneratedOrgName();
		
		Thread.sleep(1000);
		userCreation.searchOrganization2(organizationGetName);		
		userCreation.validateUserStatusActive(organizationGetName);
	
}
	
// Seller Account Creation	

	@Given("User selects {string} option as the organization type")
	public void user_selects_option_as_the_organization_type(String string) throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		userCreation.orgTypeSeller();		
	}
	@Given("User fills the Organisation Profile Information")
	public void user_fills_the_organisation_profile_information() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		userCreation.enterOrgName2();
		 generatedTRN = TestDataGenerator.generateTRN();
		 userCreation.enterTRN(generatedTRN);
	}
	@Given("User fills the Contact Details")
	public void user_fills_the_contact_details() {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		
		businessFirstName =
	            TestDataGenerator.generateBusinessFirstName();

	    businessLastName =
	            TestDataGenerator.generateBusinessLastName();
	    
		userCreation.enterBusinessFirstName(businessFirstName);
	    userCreation.enterBusinessLastName(businessLastName);
	    
	    mobileNumber =
	            TestDataGenerator.generateMobileNumber();

	    email =
	            TestDataGenerator.generateEmail();
	    
	    userCreation.entermobileNo(mobileNumber);
	    userCreation.enterEmail(email);
	    
	}
	@Then("User fills the Organization Admin Details")
	public void user_fills_the_organization_admin_details() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(2000);
		userCreation.UserNametype();
		userCreation.Password();
		userCreation.confirmPassword();
	}
	@Then("Fills the Address Details")
	public void fills_the_address_details() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		addressLine1 =
	            TestDataGenerator.generateDubaiAddressLine1();

	    addressLine2 =
	            TestDataGenerator.generateDubaiAddressLine2();
	    
	    userCreation.enterAddressLine1(addressLine1);
	    userCreation.enterAddressLine2(addressLine2);
	
	generatedPinCode = TestDataGenerator.generatePinCode();
	userCreation.enterPinCode(generatedPinCode);
	
	userCreation.Location();
	Thread.sleep(2000);
	userCreation.Country();
	Thread.sleep(1000);
	userCreation.State();
	Thread.sleep(1000);
	userCreation.Zone();
	Thread.sleep(1000);
	userCreation.City();
	}
	@Then("Enables all communication channels if required")
	public void enables_all_communication_channels_if_required() {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
	   
	}
	@Then("Provides Additional Information if required")
	public void provides_additional_information_if_required() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(1000);
		userCreation.selectPaymentTerms();
	}
	
	@And("Ater that User can change the status for Initiated to Active for Seller Account")
	public void Ater_that_user_can_change_the_status_for_initiated_to_active_for_seller_account() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		Thread.sleep(2000);
		userCreation.clickgrid();
		Thread.sleep(1000);
		userCreation.clickOrganisationMenue();
		Thread.sleep(2000);
		userCreation.changeStatusforSeller();
		Thread.sleep(1000);
		
		String organizationGetName=Global.getGeneratedOrgName();

	    System.out.println(
	            "Organization name passed to search: " + organizationGetName
	    );

	    userCreation.searchOrganization(organizationGetName);
	    
	    Thread.sleep(1000);
	    userCreation.changeStatusfor_InitiatedToActive();
	    Thread.sleep(1000);
	    userCreation.clickMoreBtn();
	    Thread.sleep(1000);
	    userCreation.uploadAllDocumentsforSeller();
	    Thread.sleep(1000);
	    userCreation.saveSubmitActions();
	    Thread.sleep(1000);
	    userCreation.changedStatusToActive();
	    Thread.sleep(1000);
	    userCreation.checkOrgStatusChangedToActive();
		
	}

// Create Consumer Account
	@Given("User selects {string} option as the organizations type")
	public void user_selects_option_as_the_organizations_type(String string) throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		userCreation.orgTypeConsumer();
	}
	
	
//Edit Organization
	
	@Given("User navigates to the Organization module")
	public void user_navigate_to_the_organization_module() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		
		Thread.sleep(2000);
		userCreation.clickgrid();
		Thread.sleep(1000);
		userCreation.clickOrganisationMenue();
		Thread.sleep(2000);
		userCreation.selectBusinessOption();
		Thread.sleep(1000);
		
		
	}
	@Given("Search created organisation and click edit option in more button")
	public void search_created_organisation_and_click_edit_option_in_more_button() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
		
		userCreation.clickEditOrganisationDetails();
		userCreation.updateOrganizationDetails(newOrganizationName, farmClientName);
//		userCreation.editOrganizationDetails2();
//		userCreation.validateOrganizationUpdate();
	}
	@Given("Edit the organisation details for required fields")
	public void edit_the_organisation_details_for_required_fields() {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
	}
	@Given("Save and update the edit details")
	public void save_and_update_the_edit_details() {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation();
	}
	@Then("Validate the edit information updated correctly")
	public void validate_the_edit_information_updated_correctly() {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
	}
	
//Update Address
	@When("Select Existing organisation and click Manage Address option in more button")
	public void select_existing_organisation_and_click_manage_address_option_in_more_button() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		userCreation.clickManageAddress();
	}
	@Then("Update Primary Addreess details for organisation")
	public void update_primary_addreess_details_for_organisation() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		Thread.sleep(4000);
		userCreation.captureOldAddressDetails();
	}
	@Then("Validate the saved details for updated correctly")
	public void validate_the_saved_details_for_updated_correctly() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		
		 addressLine1 =
		            TestDataGenerator.generateDubaiAddressLine1();

		    addressLine2 =
		            TestDataGenerator.generateDubaiAddressLine2();
		    
		    userCreation.enterAddressLine1(addressLine1);
		    userCreation.enterAddressLine2(addressLine2);
		
		generatedPinCode = TestDataGenerator.generatePinCode();
		userCreation.enterPinCode(generatedPinCode);
		Thread.sleep(2000);
//		userCreation.Location2();
		Thread.sleep(1000);		
		userCreation.selectRandomZoneAndCity();
		userCreation.clickUpdateAddress();
		userCreation.clickOKAfterUpdate();
	}
	
// update new address
	
	@When("Select one organisation and click Manage Address option")
	public void select_one_organisation_and_click_manage_address_option() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		userCreation.click_AddNewAddress();
	}
	
	@Then("Add New Address details for organisation")
	public void add_new_address_details_for_organisation() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		
		addressLine1 =
	            TestDataGenerator.generateDubaiAddressLine1();

	    addressLine2 =
	            TestDataGenerator.generateDubaiAddressLine2();
	    
	    userCreation.enterAddressLine1(addressLine1);
	    userCreation.enterAddressLine2(addressLine2);
	
	generatedPinCode = TestDataGenerator.generatePinCode();
	userCreation.enterPinCode(generatedPinCode);
	
	userCreation.Location2();
	Thread.sleep(2000);
	userCreation.Country2();
	Thread.sleep(1000);
	userCreation.State2();
	Thread.sleep(1000);
	userCreation.Zone2();
	Thread.sleep(1000);
	userCreation.City2();
	}
	
	@And("Validate the updated address details")
	public void validate_the_updated_address_details() throws InterruptedException {
		UserCreation userCreation = testsetup.pageObjectManager.getUserCreation(); 
		userCreation.clickAddBtn();
	}
		
}


