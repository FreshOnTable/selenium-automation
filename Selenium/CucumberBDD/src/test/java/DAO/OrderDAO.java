package DAO;

import DB.MongoDBConnection;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class OrderDAO {

    private final MongoCollection<Document> orderCollection;

    public OrderDAO() {

        MongoDatabase database =
                MongoDBConnection.getDatabase();

        orderCollection =
                database.getCollection("order");
    }

    public Document getByInvoiceNumber(
            String invoiceNumber) {

        return orderCollection.find(
                eq("inv_number", invoiceNumber)
        ).first();
    }

    public Document getByOrderId(
            String orderId) {

        return orderCollection.find(
                eq("order_id", orderId)
        ).first();
    }

    public List<Document> getProducts(
            Document order) {

        List<Document> result =
                new ArrayList<>();

        if (order == null) {
            return result;
        }

        List<Document> prdDtls =
                order.getList(
                        "prd_dtls",
                        Document.class
                );

        if (prdDtls == null) {
            return result;
        }

        for (Document prdDtl : prdDtls) {

            List<Document> products =
                    prdDtl.getList(
                            "prd_info",
                            Document.class
                    );

            if (products != null) {

                result.addAll(products);
            }
        }

        return result;
    }
}
