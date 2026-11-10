package Utils;

public class Global {

	    private long orderId;
	    private static String capturedAction;
	    private static String batchNumber;
	    private static double capturedRejectQty;
	    
	    private String existingScheduleId;
	    private String existingDriverType;
	    private String existingDriverSellerName;
	    
	    private int noOfOrders;
	    private double orderValue;
	    private double avgOrderValue;
	    
	    private static String generatedOrgName;

	    // Getter
	    public long getOrderId() {
	        return orderId;
	    }

	    // Setter
	    public void setOrderId(long orderId) {
	        this.orderId = orderId;
	    }
	    
	    // Action Setter
	    public static String getActionType() {
	        return capturedAction;
	    }

	    public static String getGeneratedOrgName() {
	    	System.out.println("Global.getGeneratedOrgName() called, returning: " + generatedOrgName);
	        return generatedOrgName;
	    }

	    public static void setGeneratedOrgName(String generatedOrgName) {
	        Global.generatedOrgName = generatedOrgName;
	        System.out.println("Global.setGeneratedOrgName() called, set to: " + generatedOrgName);
	        
	    }
	   
	    public static void setActionType(String capturedAction) {
	        Global.capturedAction = capturedAction;
	    }
	    
	   // Batch Setter
	    public static String getBatchNumber() {
	        return batchNumber;
	    }

	    
	    public static void setBatchNumber(String capturedBatchNo) {
	        Global.batchNumber = capturedBatchNo;
	    }
	    
	    // Reject Quantity
	    public static double getRejectQty() {
	        return capturedRejectQty;
	    }

	    public static void setRejectQty(double capturedRejectQty) {
	        Global.capturedRejectQty = capturedRejectQty;
	    }
	    
	    public String getExistingScheduleId() {
	        return existingScheduleId;
	    }

	    public String getExistingDriverType() {
	        return existingDriverType;
	    }

	    public String getExistingDriverSellerName() {
	        return existingDriverSellerName;
	    }


//validate no of orders, order value, and average order value	    
	    public Global(int noOfOrders,
                double orderValue,
                double avgOrderValue) {

this.noOfOrders = noOfOrders;
this.orderValue = orderValue;
this.avgOrderValue = avgOrderValue;
}

public int getNoOfOrders() {
return noOfOrders;
}

public double getOrderValue() {
return orderValue;
}

public double getAvgOrderValue() {
return avgOrderValue;
}

}

