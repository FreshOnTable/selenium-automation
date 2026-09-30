package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Basetest {

    protected static WebDriver driver;

    public WebDriver webDriverManager() throws IOException {

        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir")
                        + "/src/test/resources/global.properties");

        Properties prop = new Properties();
        prop.load(fis);

        String browser = prop.getProperty("browser");
        String url = prop.getProperty("url");

        // Close existing browser
        if (driver != null) {
            driver.quit();
        }

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();

            // Incognito
            options.addArguments("--incognito");

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            // ==========================================
            // DOWNLOAD CONFIGURATION
            // ==========================================

            String downloadPath =
                    System.getProperty("user.home") + "\\Downloads\\SeleniumReports";
            
        

            Map<String, Object> prefs = new HashMap<>();

            prefs.put("download.default_directory", downloadPath);
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("safebrowsing.enabled", true);

            options.setExperimentalOption("prefs", prefs);
            
            System.out.println("📁 Download directory: " + downloadPath);

            // ==========================================

            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(6));

        driver.manage().window().maximize();

        driver.get(url);

        System.out.println(
                "📁 Chrome download directory: "
                        + System.getProperty("user.dir")
                        + "/target/downloads"
        );

        return driver;
    }
}