package stepDefinitions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import pageObject.ObjectManager;
import utilities.BaseClass;
import utilities.SetupClass;

public class Hooks {

    BaseClass base;
    private static boolean isLoggedIn = false;

    public Hooks(BaseClass base) {
        this.base = base;
    }

    @Before(order = 0)
    public void launchBrowser() throws Exception {

        base.driver = SetupClass.getDriver();
        base.pageObjectManager = new ObjectManager(base.driver);
    }

    @Before(order = 1)
    public void loginOnce() {

        if (!isLoggedIn) {

            WebDriverWait wait =
                new WebDriverWait(base.driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("userName")));

            base.pageObjectManager.getLoginPage()
                .enterUsername("UAEADMIN");
            base.pageObjectManager.getLoginPage()
                .enterPassword("Admin@1234");
            base.pageObjectManager.getLoginPage()
                .clickLoginButton();

  //          wait.until(ExpectedConditions.visibilityOfElementLocated(
  //              By.xpath("//span[text()='FRESHONTABLE']")));

            isLoggedIn = true;
        }
    }

    @After
    public void tearDown() {
        // keep browser open for all scenarios
        // SetupClass.quitDriver(); ← enable only if needed
    }
}