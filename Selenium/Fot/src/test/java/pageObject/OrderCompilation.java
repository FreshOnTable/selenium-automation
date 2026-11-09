package pageObject;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import global.Global;

import org.openqa.selenium.TimeoutException;

public class OrderCompilation {
	
	public WebDriver driver;

	public OrderCompilation(WebDriver driver) {
		this.driver = driver;
		
		}
	
	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickorderProcessing = By.xpath("//ul//li//span[text()='Order Processing']");
	By orderCompilation = By.xpath("//ul//li//span[text()='Order Compilation']");
	
	public void clickgrid() {
		driver.findElement(clickgrid).click();
	}

	public void clickorderProcessing() throws InterruptedException {
		driver.findElement(clickorderProcessing).click();
		Thread.sleep(2000);
	}

	public void orderCompilation() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(orderCompilation).click();
	}

	public void stockReserving() throws InterruptedException {

		driver.navigate().refresh();

		Thread.sleep(2000);

	    By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
	    By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(8000);

//	    String orderId = PlaceOrder.orderId;
		String orderId = Global.getOrderId();
	    System.out.println("Order ID = " + orderId);

	    searchBox.clear();
	    searchBox.sendKeys(orderId);
	    Thread.sleep(1000);
	    searchBox.sendKeys(Keys.ENTER);
	    
	    Thread.sleep(3000);
	    
	}
	
	public void stockReserve() throws InterruptedException {

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//tbody/tr[not(contains(@style,'display:none'))]")));

		Thread.sleep(2000);

		WebElement moreBtn = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//button[contains(@class,'actions-dropdown')])[2]")));
		moreBtn.click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Reserve Batch']"))).click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='SUBMIT']"))).click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Confirm']"))).click();

		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='OK']"))).click();
		
		WebElement backArrow = driver.findElement(
			    By.cssSelector("[data-testid='ArrowBackIcon']")
			);
			backArrow.click();
	}

//invoice generation 

	public void clickOrderOption() {
		driver.findElement(By.xpath("//span[text()='Order']")).click();

	}

	public void clickViewOrder() {
		driver.findElement(By.xpath("//span[text()='View Orders']")).click();
	}

	public void clickMoreButton() throws InterruptedException {
//		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        By.cssSelector(".fot-loader")));
		driver.findElement(By.xpath("(//button[text()='More'])[1]")).click();
	}
	
	public void clickShowAllOptions() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='Show all options']")).click();
	}

	public void clickEditOrderDetails1() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div//a[@class='dropdown-item'])[2]")).click();
	}

	public void clickGenerateInvoiceButton() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[text()='Generate Invoice'])")).click();
	}
	
	public void invoiceConfirmation() throws InterruptedException {
		Thread.sleep(3000);
      driver.findElement(By.xpath("//button[normalize-space()='OK']")).click();
		Thread.sleep(2000);
	}
	
	
	  public void waitUntilAwaitingPaymentAndClickMore() throws InterruptedException {

	        int refreshTimes = 5;
	        int refreshIntervalSec = 30;

	        By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
	        By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        // ---------------- REFRESH PAGE ----------------
	        for (int i = 1; i <= refreshTimes; i++) {
	            System.out.println("Refresh Attempt " + i + " of " + refreshTimes);
	            driver.navigate().refresh();
	            Thread.sleep(refreshIntervalSec * 1000L);
	        }

	        // ---------------- CLICK FILTER ICON ----------------
	        clickWithRetry(filterIcon, wait, js);

	        // ---------------- ENTER ORDER ID ----------------
	        WebElement searchBox = wait.until(
	                ExpectedConditions.elementToBeClickable(searchBoxLocator)
	        );

	        Thread.sleep(3000);

//	        String orderId = PlaceOrder.orderId;
//	        System.out.println("Order ID = " + orderId);
	        
	        String orderId = Global.getOrderId();
		    System.out.println("Order ID = " + orderId);

	        searchBox.clear();
	        searchBox.sendKeys(orderId);
	        Thread.sleep(1000);
	        searchBox.sendKeys(Keys.ENTER);

	        Thread.sleep(3000);
	    }

	    // ---------------- HELPER METHOD ----------------
	    private void clickWithRetry(By locator, WebDriverWait wait, JavascriptExecutor js) {

	        int attempts = 0;

	        while (attempts < 5) {
	            try {
	                WebElement element = wait.until(
	                        ExpectedConditions.refreshed(
	                                ExpectedConditions.elementToBeClickable(locator)
	                        )
	                );

	                // Handle overlays safely
	                List<WebElement> overlays =
	                        driver.findElements(By.cssSelector(".overlay, .modal, .loader"));

	                for (WebElement overlay : overlays) {
	                    if (overlay.isDisplayed()) {
	                        js.executeScript("arguments[0].style.display='none';", overlay);
	                        System.out.println("Overlay dismissed");
	                    }
	                }

	                // Scroll into view
	                js.executeScript(
	                        "arguments[0].scrollIntoView({block:'center'});",
	                        element
	                );

	                try {
	                    element.click();
	                } catch (ElementClickInterceptedException e) {
	                    js.executeScript("arguments[0].click();", element);
	                }

	                return;

	            } 
	            catch (StaleElementReferenceException |
	                     TimeoutException |
	                     ElementClickInterceptedException e) {

	                attempts++;
	                System.out.println("Retrying click (" + attempts + "/5) due to "
	                        + e.getClass().getSimpleName());

	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException ignored) {}
	            }
	        }

	        throw new RuntimeException("Failed to click element after retries: " + locator);
	    }

	   
 public void clickMoreOption() throws InterruptedException {
	 
	 Thread.sleep(3000);
	 
	 driver.findElement(By.xpath("//table//tbody/tr[1]//button[contains(text(),'More')]")).click();
	 
	 Thread.sleep(2000);
	 
	 driver.findElement(By.xpath("//div[text()='Show all options']")).click();
 }


	public void clickPaymentAgainstInvoice() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    Thread.sleep(2000);

	    By paymentOption = By.xpath("//a[normalize-space()='Payment Against Invoice']");

	    wait.until(ExpectedConditions.elementToBeClickable(paymentOption)).click();
	}

	public void completeOrder() throws InterruptedException {
		
		Thread.sleep(2500);
		
		driver.findElement(By.xpath("(//input[@class='grid-checkbox-input  cursor-pointer' and @type='checkbox'])[2]")).click();
		
		Thread.sleep(2500);
		
		// ---------------- GENERATE TRANSACTION ID ----------------
		int prefixLength = 5;      // 5 random letters
		int digitLength = 11;      // 11 digits

		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		// Generate random 5-letter prefix
		for (int i = 0; i < prefixLength; i++) {
		    sb.append(letters.charAt(random.nextInt(letters.length())));
		}

		// Generate digits
		for (int i = 0; i < digitLength; i++) {
		    sb.append(random.nextInt(10));
		}

		String transactionId = sb.toString();
		System.out.println("Generated Transaction ID: " + transactionId);

		// ---------------- SEND KEYS ----------------
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement transIdInput = wait.until(
		        ExpectedConditions.elementToBeClickable(
		                By.xpath("//input[@name='transId']")
		        )
		);

		transIdInput.clear();
		transIdInput.sendKeys(transactionId);

		
		Thread.sleep(2500);
		
		wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//textarea[@type='textarea']")))
		    .sendKeys("Payment completed successfully");
		
		Thread.sleep(2500);
		
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		
		Thread.sleep(2500);
		
		driver.findElement(By.xpath("//button[text()='Confirm']")).click();
		
		Thread.sleep(2500);
		
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		
		
	}


}
