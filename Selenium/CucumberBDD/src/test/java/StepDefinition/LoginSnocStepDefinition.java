package StepDefinition;

import PageObject.LoginSnoc;
import Utils.Setuptest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSnocStepDefinition {
	
	public Setuptest testsetup;

	public LoginSnocStepDefinition(Setuptest testSetup) {
		this.testsetup = testSetup;
	}

	@Given("Launch the browser")
	public void launch_the_browser() throws Throwable {
	}
	
	@When("Login with username and password")
	public void login_with_username_and_password() throws InterruptedException {
		LoginSnoc s1 = testsetup.pageObjectManager.getLoginPage();
		s1.enterusername();
		s1.enterpassword();
	}

	@Then("User is landing on the dashboard")
	public void user_is_landing_on_the_dashboard() throws InterruptedException {
		LoginSnoc s1 = testsetup.pageObjectManager.getLoginPage();		
		Thread.sleep(1000);
		s1.clickOnLoginButton();
		Thread.sleep(1000);
		s1.dismissPopupIfPresent();
	}

	@Then("Veryfy the login is successful")
	public void veryfy_the_login_is_successful() {
		LoginSnoc s1 = testsetup.pageObjectManager.getLoginPage();
//	    s1.SnocAdminValidation();
	}

}
