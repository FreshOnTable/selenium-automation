package Utils;

import DAO.OrderDAO;

import java.util.List;

import org.bson.Document;

public class ExcelValidator {

    private final OrderDAO orderDAO;

    public ExcelValidator() {
        orderDAO = new OrderDAO();
    }

    public void validateOrderExceptionReport(
            List<ExcelRowData> excelRows) {

        int totalRows = excelRows.size();

        int passedRows = 0;
        int failedRows = 0;

        System.out.println();
        System.out.println(
                "=============================================="
        );
        System.out.println(
                "ORDER EXCEPTION REPORT VALIDATION"
        );
        System.out.println(
                "=============================================="
        );

        for (int i = 0;
             i < excelRows.size();
             i++) {

            ExcelRowData excel =
                    excelRows.get(i);

            System.out.println();
            System.out.println(
                    "----------------------------------------------"
            );

            System.out.println(
                    "Validating Excel Row: "
                            + (i + 1)
            );

            System.out.println(
                    "Invoice No: "
                            + excel.getInvoiceNumber()
            );

            System.out.println(
                    "Product: "
                            + excel.getProductName()
            );

            Document mongoOrder =
                    orderDAO.getByInvoiceNumber(
                            excel.getInvoiceNumber()
                    );

            if (mongoOrder == null) {

                System.out.println(
                        "❌ FAIL - Invoice not found in MongoDB"
                );

                failedRows++;

                continue;
            }

            System.out.println(
                    "✅ Invoice found in MongoDB"
            );

            boolean rowPassed = true;

            // ----------------------------------------
            // PRODUCT VALIDATION
            // ----------------------------------------

            List<Document> products =
                    orderDAO.getProducts(
                            mongoOrder
                    );

            Document matchingProduct =
                    findMatchingProduct(
                            products,
                            excel.getProductName()
                    );

            if (matchingProduct == null) {

                System.out.println(
                        "❌ Product FAIL"
                );

                System.out.println(
                        "Excel Product : "
                                + excel.getProductName()
                );

                System.out.println(
                        "Mongo Product : NOT FOUND"
                );

                rowPassed = false;

            } else {

                System.out.println(
                        "✅ Product PASS"
                );

                // ------------------------------------
                // UOM
                // ------------------------------------

                String mongoUom =
                        matchingProduct.getString(
                                "uom"
                        );

                if (compareText(
                        excel.getUom(),
                        mongoUom
                )) {

                    System.out.println(
                            "✅ UOM PASS"
                    );

                } else {

                    System.out.println(
                            "❌ UOM FAIL"
                    );

                    System.out.println(
                            "Excel: "
                                    + excel.getUom()
                    );

                    System.out.println(
                            "Mongo: "
                                    + mongoUom
                    );

                    rowPassed = false;
                }

                // ------------------------------------
                // QUANTITY
                // ------------------------------------

                Double mongoQuantity =
                        getDouble(
                                matchingProduct,
                                "prd_qty"
                        );

                Double excelQuantity =
                        parseDouble(
                                excel.getQuantity()
                        );

                if (excelQuantity != null
                        && mongoQuantity != null
                        && Math.abs(
                                excelQuantity
                                        - mongoQuantity
                        ) < 0.01) {

                    System.out.println(
                            "✅ Quantity PASS"
                    );

                } else {

                    System.out.println(
                            "❌ Quantity FAIL"
                    );

                    System.out.println(
                            "Excel: "
                                    + excel.getQuantity()
                    );

                    System.out.println(
                            "Mongo: "
                                    + mongoQuantity
                    );

                    rowPassed = false;
                }
            }

            // ----------------------------------------
            // SALES USER
            // ----------------------------------------

            String mongoSalesUser =
                    mongoOrder.getString(
                            "mapped_sales_uname"
                    );

            if (!excel.getSalesUser().isBlank()) {

                if (compareText(
                        excel.getSalesUser(),
                        mongoSalesUser
                )) {

                    System.out.println(
                            "✅ Sales User PASS"
                    );

                } else {

                    System.out.println(
                            "❌ Sales User FAIL"
                    );

                    System.out.println(
                            "Excel: "
                                    + excel.getSalesUser()
                    );

                    System.out.println(
                            "Mongo: "
                                    + mongoSalesUser
                    );

                    rowPassed = false;
                }
            }

            // ----------------------------------------
            // FINAL RESULT
            // ----------------------------------------

            if (rowPassed) {

                System.out.println(
                        "🎯 ROW RESULT: PASS"
                );

                passedRows++;

            } else {

                System.out.println(
                        "🚨 ROW RESULT: FAIL"
                );

                failedRows++;
            }
        }

        System.out.println();
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "TOTAL ROWS   : " + totalRows
        );

        System.out.println(
                "PASSED ROWS  : " + passedRows
        );

        System.out.println(
                "FAILED ROWS  : " + failedRows
        );

        System.out.println(
                "=============================================="
        );

        if (failedRows > 0) {

            throw new AssertionError(
                    "Report validation failed. "
                            + failedRows
                            + " row(s) failed."
            );
        }

        System.out.println(
                "🎉 REPORT VALIDATION PASSED"
        );
    }

    private Document findMatchingProduct(
            List<Document> products,
            String excelProductName) {

        if (excelProductName == null) {
            return null;
        }

        String expected =
                excelProductName
                        .trim()
                        .toLowerCase();

        for (Document product :
                products) {

            String mongoProductName =
                    product.getString(
                            "prd_nm"
                    );

            if (mongoProductName == null) {
                continue;
            }

            if (mongoProductName
                    .trim()
                    .equalsIgnoreCase(
                            expected
                    )) {

                return product;
            }
        }

        return null;
    }

    private boolean compareText(
            String excel,
            String mongo) {

        if (excel == null ||
                mongo == null) {

            return false;
        }

        return excel.trim()
                .equalsIgnoreCase(
                        mongo.trim()
                );
    }

    private Double parseDouble(
            String value) {

        try {

            if (value == null ||
                    value.isBlank()) {

                return null;
            }

            return Double.parseDouble(
                    value.replace(",", "")
            );

        } catch (Exception e) {

            return null;
        }
    }

    private Double getDouble(
            Document document,
            String field) {

        Object value =
                document.get(field);

        if (value == null) {
            return null;
        }

        if (value instanceof Number) {

            return ((Number) value)
                    .doubleValue();
        }

        try {

            return Double.parseDouble(
                    value.toString()
            );

        } catch (Exception e) {

            return null;
        }
    }
}
