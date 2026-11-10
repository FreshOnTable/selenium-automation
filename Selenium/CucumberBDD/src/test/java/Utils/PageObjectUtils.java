package Utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

 public class PageObjectUtils {
	
	private WebDriver driver;
    private WebDriverWait wait;

    public PageObjectUtils(WebDriver driver) {
        this.driver = driver;
       this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /* ================== SAFE CLICK ================== */
//    public void click(By locator) {
    public void click(By locator) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;

            } catch (StaleElementReferenceException e) {
                attempts++;

            } catch (ElementClickInterceptedException e) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return;
            }
        }

        throw new RuntimeException("Unable to click element after retries: " + locator);
    }


//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//                element.click();
//                return; // success → exit
//            } catch (StaleElementReferenceException e) {
//                attempts++;
//                }catch (ElementClickInterceptedException e ) {
//                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//                    JavascriptExecutor js = (JavascriptExecutor) driver;
//                    js.executeScript("arguments[0].click();", element);
//                    return;
//                }
                             
    /* ================== TYPE ================== */
//    public void clearInput(WebElement element) {
//        element.click();
//        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//        element.sendKeys(Keys.DELETE);
//
//    }

    /* ================== POPUP ================== */
    public void ValidateAndCofirmPopUp(By popUPLocator, By okButtonLocator, String expectedMessage) {
        String actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(popUPLocator)).getText();
        Assert.assertEquals(actualText, expectedMessage);
        click(okButtonLocator);
    }

    /* ================== DROPDOWN ================== */
//    public void selectByvisibleText(By dropDownLocator, String value) {
//        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropDownLocator));
//        new Select(dropdown).selectByVisibleText(value);
//    }
    public void selectByvisibleText(By locator, String value) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                Select select = new Select(dropdown);
                select.selectByVisibleText(value);
                return; // success → exit
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }

        throw new RuntimeException("Dropdown became stale even after retries: " + locator);
    }

    public void selectByValue(By dropDownLocator, String value) {

        Select select = new Select(driver.findElement(dropDownLocator));
        select.selectByValue(value);
    }

    /* ================== GRID SELECTION ================== */
    public void selectByText(By locator, String value) {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

        for (int i = 0; i < 5; i++) {
            try {
                List<WebElement> rows = driver.findElements(locator);

                for (WebElement row : rows) {
                    if (row.getText().trim().equalsIgnoreCase(value)) {
                        wait.until(ExpectedConditions.elementToBeClickable(row)).click();
                        return;
                    }
                }
            } catch (StaleElementReferenceException ignored) {
            }
        }
        throw new RuntimeException("Option not found in grid: " + value);
    }

    /* ================== LOADER ================== */
//    public void waitForLoaderToDisappear(By loader) {
//        wait.until(driver ->
//                driver.findElements(loader)
//                        .stream()
//                        .noneMatch(WebElement::isDisplayed));
//    }
    public void waitForLoaderToDisappear(By loader) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for loader to appear (optional but recommended)
//        wait.until(ExpectedConditions.presenceOfElementLocated(loader));

        // Wait for loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    /* ================== VISIBILITY ================== */
    public void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        // TODO Auto-generated method stub

    }

    public void safeSendKeys(By locator, String value) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.clear();
                element.sendKeys(value);
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Unable to send keys after retries: " + locator);
    } 


} 

