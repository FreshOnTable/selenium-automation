package global;

public class Global {

    private static String orderId;
    private static String lpoId;

    public static String getOrderId() {
        return orderId;
    }

    public static void setOrderId(String orderId) {
    	
        Global.orderId = orderId;
//    }
//    public static String getlpoId() {
//        return lpoId;
//    }
//
//    public static void setlpoId(String lpoId) {
//        Global.orderId = lpoId;
    }
}