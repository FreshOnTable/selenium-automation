package pageObject;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryModule {
	
	public WebDriver driver;

	public DeliveryModule(WebDriver driver) {
		this.driver = driver;

	}

	By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
	By clickdelivery = By.xpath("//ul//li//span[text()='Delivery']");
	By placeschedule = By.xpath("//ul//li//span[text()='Schedule']");
//	By createdeliveryschedule = By.xpath("//button[text()='Create Delivery Schedule +']");

	public void clickgrid() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(clickgrid).click();
	}

	public void clickdeliveryoption() throws InterruptedException {
		Thread.sleep(2500);
		driver.findElement(clickdelivery).click();
	}

	public void clickschedule() throws InterruptedException {
		Thread.sleep(2500);
		driver.findElement(placeschedule).click();
	}

	public void clickcreatedeliveryschedule() throws InterruptedException {
		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[text()='Create Delivery Schedule +']")).click();
	}

	public void OrderFilteration() throws InterruptedException {

		Thread.sleep(10000);

		By filterIcon = By.xpath("(//*[name()='svg' and @data-testid='FilterAltRoundedIcon'])[1]");
		Thread.sleep(1000);
		By searchBoxLocator = By.xpath("//input[@type='text' and @placeholder='Order ID']");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(filterIcon))).click();

		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));

		Thread.sleep(2000);

		String orderId = PlaceOrder.orderId;
		System.out.println("Order ID = " + orderId);

		searchBox.clear();
		searchBox.sendKeys(orderId);
		searchBox.sendKeys(Keys.ENTER);

//		driver.findElement(By.xpath("//button[text()='Change Drivers']")).click();
//
//		WebElement dropdown = driver.findElement(By.xpath("//select[@class='grid-select alt-down-arrow']"));
//		dropdown.click();
//
//		Select select = new Select(dropdown);
//
//		List<WebElement> options = select.getOptions();
//
//		options.remove(0);
//
//		WebElement randomOption = options.get(new Random().nextInt(options.size()));
//
//		select.selectByVisibleText(randomOption.getText());

	}

	public void DragAndDrop() throws InterruptedException {

		Thread.sleep(5000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement source = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='Driver Name']")));

		WebElement target = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'grid-grouping-bar')]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", source);
		js.executeScript("arguments[0].scrollIntoView(true);", target);

		Actions actions = new Actions(driver);
		actions.clickAndHold(source).moveToElement(target).release().build().perform();
	}

	public void OrderSelection() throws InterruptedException {

		Thread.sleep(1000);

		List<WebElement> driverCheckboxes = driver
				.findElements(By.xpath("//div[@class='grid-checkbox-div group-by-column-check']"));
		for (int i = 0; i < driverCheckboxes.size(); i++) {
			List<WebElement> updatedList = driver
					.findElements(By.xpath("//div[@class='grid-checkbox-div group-by-column-check']"));
			WebElement checkbox = updatedList.get(i);
			if (!checkbox.isSelected()) {
				checkbox.click();
				Thread.sleep(300);

			}
		}
		driver.findElement(By.xpath("(//button[@class='button button-primary button-small'])[2]")).click();
	}

	public void selectVehicleAndTime() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Random random = new Random();

		List<WebElement> userSections = driver.findElements(By.xpath("//div[contains(@class, 'review-set')]"));

		for (WebElement section : userSections) {
			try {
				WebElement nameElement = section.findElement(By.xpath(".//div[contains(@class,'review-header')]"));
				String name = nameElement.getText().trim();

				Thread.sleep(1000);

				boolean isUnassigned = name.equalsIgnoreCase("Unassigned User");

				WebElement departureDropdownElem = section.findElement(By.xpath(".//select[@name='pickupBy']"));
				wait.until(ExpectedConditions.elementToBeClickable(departureDropdownElem));
				Select departureSelect = new Select(departureDropdownElem);
				List<WebElement> departureOptions = departureSelect.getOptions();

				if (departureOptions.size() > 1) {
					int index = random.nextInt(departureOptions.size() - 1) + 1;
					departureSelect.selectByIndex(index);
				}

				Thread.sleep(1000);

				WebElement vehicleDropdownElem = section.findElement(By.xpath(".//select[@name='vehicle']"));
				wait.until(ExpectedConditions.elementToBeClickable(vehicleDropdownElem));
				Select vehicleSelect = new Select(vehicleDropdownElem);
				List<WebElement> vehicleOptions = vehicleSelect.getOptions();

				if (vehicleOptions.size() > 1) {
					int index = random.nextInt(vehicleOptions.size() - 1) + 1;
					vehicleSelect.selectByIndex(index);
				}

				Thread.sleep(2000);

				if (isUnassigned) {
					WebElement driverDropdownElem = section.findElement(By.xpath(".//select[@name='userName']"));
					wait.until(ExpectedConditions.elementToBeClickable(driverDropdownElem));
					Select driverSelect = new Select(driverDropdownElem);
					List<WebElement> driverOptions = driverSelect.getOptions();

					if (driverOptions.size() > 1) {
						int index = random.nextInt(driverOptions.size() - 1) + 1;
						driverSelect.selectByIndex(index);
					}
				}

			} catch (Exception e) {
				System.out.println("Error in section: " + e.getMessage());
			}

		}
	}

	public void Submit() throws InterruptedException {

		WebElement contentContainer = driver.findElement(By.cssSelector("div.content-cont.toggle"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = 0;", contentContainer);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//div[text()='Next ']")).click();

		Thread.sleep(3000);

		driver.findElement(By.xpath("//button[text()='SUBMIT']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='Confirm']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(1000);
	}

	public void scheduleCreated() throws InterruptedException {

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.findElement(By.xpath("(//button[@class='actions-dropdown dropdown-toggle btn btn-success'])[1]"))
				.click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[text()='Change Status']")).click();

		Thread.sleep(2000);

		WebElement scheduleStatus = wait1.until(ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
		Select dropdown1 = new Select(scheduleStatus);
		dropdown1.selectByVisibleText("Active");

		Thread.sleep(2000);

		WebElement comments = wait1
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @name='comment']")));

		comments.click();
		comments.sendKeys("Status changed to Active");

		driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();

	}

	public void CompletingSchedule() throws InterruptedException {

		Thread.sleep(2000);

		driver.findElement(
		    By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]")
		).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='View Trip']")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='dropdown-container']//img")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//li[text()='View Details']")).click();

		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		By businessBy = By.xpath("//div[contains(@class,'accordion-header')]");
		By ordersBy = By.xpath("//tbody/tr[not(contains(@style,'display:none'))]");

		// ✅ WAIT until at least one business exists
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, 0));

		int businessCount = driver.findElements(businessBy).size();

		for (int i = 0; i < businessCount; i++) {

		    // ✅ SAFE access
		    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
		    WebElement currentBusiness = driver.findElements(businessBy).get(i);
		    wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

		    Thread.sleep(2000);

		    while (true) {

		        List<WebElement> orders = driver.findElements(ordersBy);
		        WebElement pendingOrder = null;

		        for (WebElement order : orders) {
		            boolean isCompleted =
		                order.findElements(By.xpath(".//div[@title='Completed']")).size() > 0;

		            if (!isCompleted) {
		                pendingOrder = order;
		                break;
		            }
		        }

		        if (pendingOrder == null) {

		            System.out.println("All orders completed for business " + (i + 1));

		            // ✅ SAFE re-click
		            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
		            currentBusiness = driver.findElements(businessBy).get(i);
		            wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

		            Thread.sleep(1500);
		            break;
		        }

		        WebElement moreBtn = pendingOrder.findElement(
		            By.xpath(".//button[contains(@class,'actions-dropdown')]"));
		        wait.until(ExpectedConditions.elementToBeClickable(moreBtn)).click();

		        wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//a[normalize-space()='Change Status']"))).click();

		        WebElement statusDropdown = wait.until(
		            ExpectedConditions.presenceOfElementLocated(By.name("selectedStatus")));
		        new Select(statusDropdown).selectByVisibleText("Completed");

		        WebElement comment = wait.until(
		            ExpectedConditions.elementToBeClickable(By.name("comment")));
		        comment.clear();
		        comment.sendKeys("Schedule Completed Successfully");

		        wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='Submit']"))).click();

		        Thread.sleep(2000);

		        // ✅ SAFE re-open after DOM refresh
		        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(businessBy, i));
		        currentBusiness = driver.findElements(businessBy).get(i);
		        wait.until(ExpectedConditions.elementToBeClickable(currentBusiness)).click();

		        Thread.sleep(1500);
		    }
		}
		   
	}

}
