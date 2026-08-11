package PageObject;

import org.openqa.selenium.WebDriver;

public class Objectmanager {
	
public WebDriver driver;
	
	public LoginSnoc loginPage;
	public CreateLpo createlpo;
	public PlaceOrder placeorder;
	public Create_bulk_order bulkorder;
	public Product_Module productcreation;
	public Delivery_Module deliveryschedule;
	public UserCreation usercreation;
	public UOM_Conversion conversion;
	public stockSummaryPage stocksummary;
	
	public Objectmanager(WebDriver driver) {
		this.driver = driver;
	}

	public LoginSnoc getLoginPage() {
		loginPage = new LoginSnoc(driver);
		return loginPage;
	}
	
	public CreateLpo getCreateLpo() {
		createlpo = new CreateLpo(driver);
		return createlpo;
	}
	
	public PlaceOrder getPlaceOrder() {
		placeorder = new PlaceOrder(driver);
		return placeorder;
		
	}
	public Create_bulk_order getCreate_bulk_order() {
		bulkorder =new Create_bulk_order(driver);
		return bulkorder;
	}
	
	public Product_Module getProduct_Module() {
		productcreation = new Product_Module(driver);
		return productcreation;
	}
	
	public Delivery_Module getDelivery_Module() {
		deliveryschedule = new Delivery_Module(driver);
		return deliveryschedule;
	}
	
	public UserCreation getUserCreation() {
		usercreation = new UserCreation(driver);
		return usercreation;
	}
	
	public UOM_Conversion getUOM_Conversion() {
		conversion = new UOM_Conversion(driver);
		return conversion;
	}
	
	public stockSummaryPage getStockSummaryPage() {
		stocksummary = new stockSummaryPage(driver);
		return stocksummary;
	}


}
