package stepDefinitions;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import pageObject.StockModule;
import utilities.BaseClass;

public class StockModuleSteps {

    public BaseClass setupclass;
    private StockModule Stock;
    public static int currentIndex = 0;

    public StockModuleSteps(BaseClass setupclass) {
        this.setupclass = setupclass;

        this.Stock = setupclass.pageObjectManager.getstockModule();
    }

    @Given("User navigates to the LPO Page and validates the data")
    public void user_navigates_to_the_lpo_page_and_validates_the_data() throws Exception {
    	    	
    	Stock.clearStoredData();

        for (int i = 1; i <= 3; i++) {

            System.out.println("\n===== FETCHING LPO → " + i + " =====");

            Stock.clickLPO();
            Stock.clickMore(i);
            Stock.clickViewLPO();

            Stock.fetchData();
        }

        System.out.println("TOTAL PRODUCTS COLLECTED → "
            + Stock.getProductNames().size());
    }
    

    @Given("User fetches the data from the LPO Page")
    public void user_fetches_the_data_from_the_lpo_page() throws Exception {

   // 	Stock.fetchData();
        
    }

    
    @Given("User enters the Product ID")
    public void user_enters_the_product_ID() throws InterruptedException {

    	    int index = StockModuleSteps.currentIndex;

    	    Long productID = Stock.getPrdIds().get(index);

    	    Stock.searchProductId(productID);
    	}
    

    @Given("User enters the Product Name")
    public void user_enters_the_product_name() throws InterruptedException {

    	
    	  int index = StockModuleSteps.currentIndex;

    	    String product = Stock.getProductNames().get(index);

    	    Stock.searchProductName(product);
 	   
    	
    }
    
    
    @Given("User enters the UOM")
    public void user_enters_the_uom() throws InterruptedException {
    	
    	int index = StockModuleSteps.currentIndex;

        String uom = Stock.getUoms().get(index);

        Stock.searchUom(uom);	
    	
    }
    

    @Given("User checks the seller and clicks on the seller")
    public void user_checks_the_seller_and_clicks_on_the_seller() throws Exception {
    	
    	int index = StockModuleSteps.currentIndex;

        Stock.SellerClick(Stock.getSellers().get(index));

    }

    @Given("User clicks on the Batch Details of that product")
    public void user_clicks_on_the_batch_details_of_that_product() {

        Stock.batchDetails();
    }

    @Given("then User clicks the LPO Number")
    public void then_user_clicks_the_lpo_number() throws InterruptedException {

    	int index = StockModuleSteps.currentIndex;

    	Stock.lpoNumber(Stock.getLpoIds().get(index));
    	
    }

    @Given("User validates that the stock entry quantity in LPO and the updated stock quantity in Stock Batch Details are correct")
    public void user_validates_that_the_stock_entry_quantity_in_lpo_and_the_updated_stock_quantity_in_stock_batch_details_are_correct() {
    	
    	  currentIndex = 0;
    	
    	String prdName = Stock.getProductNames().get(currentIndex);
        Double expectedQty = Stock.getRecQtys().get(currentIndex);

        System.out.println("\n==============================");
        System.out.println("VALIDATING PRODUCT → " + prdName);
  

    	            Stock.Validation(expectedQty);
    	        }
        
    
         @Given("User validates all products one by one")
                public void user_validates_all_products_one_by_one() throws Exception {

                    WebDriverWait wait =
                        new WebDriverWait(Stock.driver, Duration.ofSeconds(10));

                    int total = Stock.getProductNames().size();

                    	  for (int i = currentIndex + 1; i < total; i++) {

                        currentIndex = i;

                        Stock.driver.get("https://mktadmin.freshontable.com/inventory/ledger");

                        user_enters_the_product_ID();
                        user_enters_the_product_name();
                        user_enters_the_uom();
                        user_checks_the_seller_and_clicks_on_the_seller();
                        user_clicks_on_the_batch_details_of_that_product();
                        then_user_clicks_the_lpo_number();

                        String prdName = Stock.getProductNames().get(currentIndex);
                        Double expectedQty = Stock.getRecQtys().get(currentIndex);

                        System.out.println("\n==============================");
                        System.out.println("VALIDATING PRODUCT → " + prdName);

                        Stock.Validation(expectedQty);

                        System.out.println("==============================");

                        Stock.refreshPage();
                    }
                
        }
    
     
    }
