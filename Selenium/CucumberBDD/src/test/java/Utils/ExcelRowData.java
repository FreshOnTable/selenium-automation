package Utils;

public class ExcelRowData {

	    private String invoiceNumber;
	    private String invoiceDate;
	    private String buyerName;
	    private String productName;
	    private String uom;
	    private String quantity;
	    private String salesUser;

	    public String getInvoiceNumber() {
	        return invoiceNumber;
	    }

	    public void setInvoiceNumber(String invoiceNumber) {
	        this.invoiceNumber = invoiceNumber;
	    }

	    public String getInvoiceDate() {
	        return invoiceDate;
	    }

	    public void setInvoiceDate(String invoiceDate) {
	        this.invoiceDate = invoiceDate;
	    }

	    public String getBuyerName() {
	        return buyerName;
	    }

	    public void setBuyerName(String buyerName) {
	        this.buyerName = buyerName;
	    }

	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName;
	    }

	    public String getUom() {
	        return uom;
	    }

	    public void setUom(String uom) {
	        this.uom = uom;
	    }

	    public String getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(String quantity) {
	        this.quantity = quantity;
	    }

	    public String getSalesUser() {
	        return salesUser;
	    }

	    public void setSalesUser(String salesUser) {
	        this.salesUser = salesUser;
	    }

	    @Override
	    public String toString() {

	        return "ExcelRowData{" +
	                "invoiceNumber='" + invoiceNumber + '\'' +
	                ", invoiceDate='" + invoiceDate + '\'' +
	                ", buyerName='" + buyerName + '\'' +
	                ", productName='" + productName + '\'' +
	                ", uom='" + uom + '\'' +
	                ", quantity='" + quantity + '\'' +
	                ", salesUser='" + salesUser + '\'' +
	                '}';
	    }
	}

