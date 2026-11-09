package pageObject;

import org.openqa.selenium.WebDriver;

public class ObjectManager {
	
	public WebDriver driver;

	public LoginPage loginPage;
	public LpoPage Lpopage;
	public PlaceOrder placeorder;
	public OrderCompilation orderCompilation;
	public DeliveryModule deliveryModule;
//	public StockModule stockModule;

	public ObjectManager(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage getLoginPage() {
		loginPage = new LoginPage(driver);
		return loginPage;
	}

	public LpoPage getLpopage() {
		Lpopage = new LpoPage(driver);
		return Lpopage;
	}
	
//	public StockModule getstockModule() {
//		stockModule = new StockModule(driver);
//		return stockModule;
//	}
	

	public PlaceOrder getplaceorder() {
		placeorder = new PlaceOrder(driver);
		return placeorder;

	}
	
	public OrderCompilation getOrderCompilation() {
		orderCompilation = new OrderCompilation(driver);
				return orderCompilation;
		
	}
	
	public DeliveryModule getdeliveryModule()  {
		deliveryModule = new DeliveryModule(driver);
				return deliveryModule;
		
	}
	

}
