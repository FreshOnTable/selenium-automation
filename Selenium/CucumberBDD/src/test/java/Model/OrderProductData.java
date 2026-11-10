package Model;

public class OrderProductData {

    private String orderId;
    private String invoiceNumber;
    private String invoiceDate;

    private String productId;
    private String productName;
    private String uom;

    private Double quantity;

    private String salesUser;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
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

        return "OrderProductData{" +
                "orderId='" + orderId + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", uom='" + uom + '\'' +
                ", quantity=" + quantity +
                ", salesUser='" + salesUser + '\'' +
                '}';
    }
}
