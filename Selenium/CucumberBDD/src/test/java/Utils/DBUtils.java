package Utils;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.internal.connection.Connection;

//public class DBUtils {
//
//    public Connection getConnection() throws Exception {
//
////        return DriverManager.getConnection(
////                DB_URL,
////                DB_USERNAME,
////                DB_PASSWORD
////        );
//    }
//    
//    public List<Map<String, String>> getOrderData(
//            String referenceId) throws Exception {
//
//        List<Map<String, String>> data =
//                new ArrayList<>();
//
//        String query =
//                "SELECT order_id, business_name, status, amount " +
//                "FROM orders " +
//                "WHERE ...";

//        try (
//            Connection connection = getConnection();
//            PreparedStatement statement =
//                    connection.prepareStatement(query)
//        ) {
//
//            statement.setString(1, referenceId);
//
//            ResultSet rs = statement.executeQuery();
//
//            while (rs.next()) {
//
//                Map<String, String> row =
//                        new HashMap<>();
//
//                row.put(
//                        "order_id",
//                        rs.getString("order_id")
//                );
//
//                row.put(
//                        "business_name",
//                        rs.getString("business_name")
//                );
//
//                row.put(
//                        "status",
//                        rs.getString("status")
//                );
//
//                row.put(
//                        "amount",
//                        rs.getString("amount")
//                );
//
//                data.add(row);
//            }
//        }
//
//        return data;
//    }
//}