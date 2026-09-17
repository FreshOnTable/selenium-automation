package PageObject;

import org.openqa.selenium.WebDriver;

public class Objectmanager {
	
public WebDriver driver;
	
	public LoginSnoc loginPage;
	public CreateLpo createlpo;
	public PlaceOrder placeorder;
	public Create_bulk_order bulkorder;
	public CreateProduct productcreation;
	public Delivery_Module deliveryschedule;
	public UserCreation usercreation;
	public UOM_Conversion conversion;
	public PendingOrder pendingOrder;
	public EditOrder editOrder;
	public EditProduct editProduct;
	public ProductStatusValidation productStatusValidation;
	public ProductMaster productMaster;

	
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
	
	public CreateProduct getProduct_Module() {
		productcreation = new CreateProduct(driver);
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
	
	public PendingOrder getPending_Order() {
		pendingOrder = new PendingOrder(driver);
		return pendingOrder;
	}
	
	public EditOrder getEdit_Order() {
		editOrder = new EditOrder(driver);
		return editOrder;
	}
	
	public EditProduct getEdit_Product() {
		editProduct = new EditProduct(driver);
		return editProduct;
	}
	
	public ProductStatusValidation getProduct_Status_Validation() {
		productStatusValidation = new ProductStatusValidation(driver);
		return productStatusValidation;
	}


	public ProductMaster getProduct_Master() {
		productMaster = new ProductMaster(driver);
		return productMaster;
	}
	
		

}
