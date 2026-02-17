package pageObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StockModule {

    public WebDriver driver;

    // ===== STORED VALUES =====
 // ===== STORED VALUES =====
    private String lpoId;
    private String sellerName;

    // MULTIPLE PRODUCTS SUPPORT
    private List<String> productNames = new ArrayList<>();
    private List<String> uoms = new ArrayList<>();
    private List<Double> recQtys = new ArrayList<>();
    private List<Long> prodIds = new ArrayList<>();
    private List<String> lpoIds = new ArrayList<>();
    private List<String> sellers = new ArrayList<>();
    
//    private boolean validationCompleted = false;

    public StockModule(WebDriver driver) {
        this.driver = driver;
    }

    // ===== GETTERS =====
    
    public List<String> getLpoIds() {
        return lpoIds;
    }

    public List<String> getSellers() {
        return sellers;
    }

    public String getLpoId() {
        return lpoId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public List<String> getUoms() {
        return uoms;
    }

    public List<Double> getRecQtys() {
        return recQtys;
    }
    
    public List<Long> getPrdIds() {
        return prodIds;
    }

    // ===== SETTERS =====

    public void setLpoId(String lpoId) {
        this.lpoId = lpoId;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public void setUoms(List<String> uoms) {
        this.uoms = uoms;
    }

    public void setRecQtys(List<Double> recQtys) {
        this.recQtys = recQtys;
    }
    
    public void setPrdIds(List<Long> prodIds) {
        this.prodIds = prodIds;
    }
       

    // ===== LOCATORS =====

    By clickgrid = By.xpath("//div[@class='menu-cont' ] //img[@alt='img']");
    By clickprocur = By.xpath("//ul//li//span[text()='Procurement']");
    By clicklpo = By.xpath("//ul//li//span[text()='LPO']");
    By clickmore = By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[1]");
    By clickViewLPO = By.xpath("//a[text()='View LPO']");

    // ===== NAVIGATION =====

    public void clickLPO() {
        driver.get("https://mktadmin.freshontable.com/procurement/lpo");
    }

//    public void clickMore() {
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.findElement(clickmore).click();
//    }
    
    public void clickMore(int index) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement moreBtn = wait.until(ExpectedConditions
            .elementToBeClickable(
                By.xpath("(//button[@type='button' and @class='actions-dropdown dropdown-toggle btn btn-success'])[" + index + "]")));

        moreBtn.click();
    }


    public void clickViewLPO() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(clickViewLPO).click();
    }

    // =====================================================
    // 🔥 READ ALL PRODUCTS FROM LPO – ANY COUNT
    // =====================================================

    public void fetchData() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // -------- LPO ID --------
        String lpoIdstr = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@class='text-ve-modal label']")))
                .getText();

        String[] lpoParts = lpoIdstr.split(":");

        if (lpoParts.length > 1) {
            lpoId = lpoParts[1].trim();
        }

       else {
            throw new Exception("Unable to parse LPO ID → " + lpoIdstr);
        }

        // AFTER SELLER READ
        sellerName = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//input[@name='Seller'])[1]")))
                .getAttribute("value");

        // USE CLASS LEVEL VALUES DIRECTLY
        String currentLpo = lpoId;
        String currentSeller = sellerName;


        // -------- COUNT ROWS --------
 //       List<WebElement> rows =
//            driver.findElements(By.xpath("//table[@class='table table-bordered']//tbody//tr"));
//
//        int totalProducts = rows.size();
//
//        System.out.println("TOTAL PRODUCTS FOUND → " + totalProducts);

        // ===== LOOP EACH PRODUCT =====
        List<WebElement> rows = driver.findElements(
        	    By.xpath("//table[@class='table table-bordered']//tbody/tr"));

        	System.out.println("TOTAL ROWS FOUND → " + rows.size());

        	int actualProduct = 1;

        	for (WebElement row : rows) {

        	    List<WebElement> cols = row.findElements(By.tagName("td"));

        	    // Skip rows that are not real product rows
        	    if (cols.size() < 10) {
        	        System.out.println("Skipping non-data row → columns: " + cols.size());
        	        continue;
        	    }

        	    String pName = cols.get(3).getText();   // td[4]
        	    String PId   = cols.get(1).getText();   //td[2]
        	    String uom   = cols.get(4).getText();   // td[5]
        	    String qty   = cols.get(9).getText();   // td[10]
        	    

        	    productNames.add(pName);
        	    uoms.add(uom);
        	    recQtys.add(Double.valueOf(qty));
        	    prodIds.add(Long.valueOf(PId));
        	    lpoIds.add(currentLpo);
        	    sellers.add(currentSeller);

        	    System.out.println("------ PRODUCT " + actualProduct + " ------");
        	    System.out.println("Product Name : " + pName);
        	    System.out.println("UOM          : " + uom);
        	    System.out.println("Rec Qty      : " + qty);
        	    System.out.println("PID          : " + PId);

        	    actualProduct++;
        	}


        System.out.println("LPO ID : " + lpoId);
        System.out.println("Seller : " + sellerName);
    }

    // =====================================================
    // 🔥 VALIDATE ALL PRODUCTS ONE BY ONE
    // =====================================================

//    public void validateAllProductsInStock() throws Exception {
//    	
//    	 if (validationCompleted) return;
//
//        for (int i = 0; i < productNames.size(); i++) {
//
//            System.out.println("\n===== VALIDATING PRODUCT " + (i+1) + " =====");
//
//            driver.get("https://mktadmin.freshontable.com/inventory/ledger");
//            
//            searchProductId(prodIds.get(i));
//
//            searchProductName(productNames.get(i));
//
//            searchUom(uoms.get(i));
//
//            SellerClick(sellerName);
//
//            batchDetails();
//
//            lpoNumber(lpoId);
//
//            Validation(recQtys.get(i));
//
//            driver.navigate().refresh();
//            
//        }
        
//        validationCompleted = true;
//    }

    
    public void searchProductId(Long productID) throws InterruptedException {
    	
    	  driver.get("https://mktadmin.freshontable.com/inventory/ledger");

        Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchBox = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("(//div[@class='custom-filter-icon'])[1]")));

        searchBox.click();

        WebElement input = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@placeholder='PID/SKU']")));

        input.clear();
        input.sendKeys(String.valueOf(productID));
        input.sendKeys(Keys.ENTER);

        Thread.sleep(2000);
    }
    
    
    
    

    public void searchProductName(String PrdName) throws InterruptedException {

        Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchBox = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("(//div[@class='custom-filter-icon'])[2]")));

        searchBox.click();

        WebElement input = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@placeholder='Product Name/ Seller Name']")));

        input.clear();
        input.sendKeys(PrdName);
        input.sendKeys(Keys.ENTER);

        Thread.sleep(2000);
    }

    public void searchUom(String uom) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement UOMSearch = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("(//div[@class='custom-filter-icon'])[4]")));

        UOMSearch.click();

        WebElement input1 = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@placeholder='UOM']")));

        input1.clear();
        input1.sendKeys(uom);
        input1.sendKeys(Keys.ENTER);

        Thread.sleep(2000);

        WebElement arrow = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//*[name()='svg' and @data-testid='KeyboardArrowRightIcon']")));

        arrow.click();
        
        
     // ===== ONLY CHANGED THIS PART =====

//     // wait for any backdrop
//     wait.until(ExpectedConditions.invisibilityOfElementLocated(
//         By.cssSelector(".MuiBackdrop-root")));
//
//     WebElement arrow = wait.until(ExpectedConditions
//         .presenceOfElementLocated(
//             By.xpath("//*[name()='svg' and @data-testid='KeyboardArrowRightIcon']")));
//
//     // JS click to avoid intercept
//     ((JavascriptExecutor) driver)
//         .executeScript("arguments[0].click();", arrow);

     Thread.sleep(2000);

    }

    public void SellerClick(String SellerName) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> sellers = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(
                        By.xpath("//td[normalize-space()='" + SellerName.trim() + "']")));

        boolean clicked = false;

        for (WebElement seller : sellers) {

            if (seller.getText().trim().equalsIgnoreCase(SellerName.trim())) {

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", seller);

                clicked = true;
                break;
            }
        }

        if (!clicked) {
            throw new Exception("Seller NOT FOUND → " + SellerName);
        }

    }

    public void batchDetails() {

        WebElement dropdown =
            driver.findElement(By.cssSelector("select.details-type-select"));

        Select select = new Select(dropdown);

        select.selectByVisibleText("Batch Details");
    }

    public void lpoNumber(String lpoId) throws InterruptedException {
    	
    	Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement LpoSearch = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("(//div[@class='custom-filter-icon'])[3]")));

        LpoSearch.click();

        WebElement input1 = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@placeholder='Reference']")));

        input1.clear();
        input1.sendKeys(lpoId);
        input1.sendKeys(Keys.ENTER);

        Thread.sleep(2000);
    }

    public void Validation(Double expectedQty) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
            
            String PerformedAction = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//table//tr//td[5]")))
                    .getText();

            String QtyStr = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//table//tr//td[6]")))
                    .getText();

            Double actualQty = Double.valueOf(QtyStr);

            System.out.println("Performed Action : " + PerformedAction);
            System.out.println("StockIn Qty  : " + actualQty);
            System.out.println("LPO Rec Qty : " + expectedQty);

            // ✅ FIXED LOGIC
            if (expectedQty.equals(actualQty)) {
                System.out.println("MATCHED ✅ → " + actualQty);
            } else {
                System.out.println("MISMATCH ❌ → UI:" + actualQty + " LPO:" + expectedQty);
            }
        }
       
		

	public void refreshPage() {
		    driver.navigate().refresh();
		
		
	}


	public void clearStoredData() {

	    productNames.clear();
	    uoms.clear();
	    recQtys.clear();
	    prodIds.clear();

	    lpoIds.clear();
	    sellers.clear();

	    lpoId = null;
	    sellerName = null;
	}
	
	
}
