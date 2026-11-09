package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.LoginPage;
import utilities.BaseClass;
import utilities.SetupClass;

public class LoginSteps {
	
//	public BaseClass setupclass;
//
//	public LoginSteps(BaseClass setupclass) {
//		this.setupclass = setupclass;
//}
	
	 BaseClass base;
	    LoginPage login;

	    public LoginSteps(BaseClass base) {
	        this.base = base;
	        this.login = base.pageObjectManager.getLoginPage();
	    }
	
	@Given("User Launch the Chrome browser")
	public void user_launch_the_chrome_browser() {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();

	}

	@When("User enters username {string} and password {string}")
	public void user_enters_credentials(String username, String password) throws InterruptedException {
//	    LoginPage login = setupclass.pageObjectManager.getLoginPage();
	    login.enterUsername(username);
	    login.enterPassword(password);
	}

	@When("Click on login button")
	public void click_on_login_button() {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
		login.clickLoginButton();
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String title) {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
		
//		login.loginValidation();
	}
	
	@When("User clcik on the Log out link")
	public void user_clcik_on_the_log_out_link() throws InterruptedException {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
		
	}
	
	@Then("Page title should be the {string}")
	public void page_title_should_be_the(String string) {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
	}

	@And("Log in is successfull")
	public void logged_in_success() {
//		LoginPage login = setupclass.pageObjectManager.getLoginPage();
//		login.quiteBrowser();
	}


}
