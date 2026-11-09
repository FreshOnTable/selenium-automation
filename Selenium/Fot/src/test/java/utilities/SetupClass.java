package utilities;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SetupClass {

    private static WebDriver driver;

    public static WebDriver getDriver() throws Exception {

        if (driver == null) {

            FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/java/global.properties");

            Properties prop = new Properties();
            prop.load(fis);

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
//           options.addArguments("--incognito");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.get(prop.getProperty("URL"));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}