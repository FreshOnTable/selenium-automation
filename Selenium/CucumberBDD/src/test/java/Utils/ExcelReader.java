package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private final DataFormatter formatter =
            new DataFormatter();

    public List<ExcelRowData> readOrderExceptionReport(
            String filePath) throws IOException {

        List<ExcelRowData> result =
                new ArrayList<>();

        try (FileInputStream fis =
                     new FileInputStream(filePath);
             Workbook workbook =
                     new XSSFWorkbook(fis)) {

            Sheet sheet =
                    workbook.getSheetAt(0);

            int headerRowIndex =
                    findHeaderRow(
                            sheet,
                            "INVOICE NO"
                    );

            if (headerRowIndex == -1) {

                throw new RuntimeException(
                        "❌ Could not find 'INVOICE NO' header"
                );
            }

            System.out.println(
                    "✅ Excel header row found at: "
                            + (headerRowIndex + 1)
            );

            Row headerRow =
                    sheet.getRow(headerRowIndex);

            Map<String, Integer> columnMap =
                    buildColumnMap(headerRow);

            printHeaders(columnMap);

            for (int i = headerRowIndex + 1;
                 i <= sheet.getLastRowNum();
                 i++) {

                Row row =
                        sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                ExcelRowData data =
                        convertRow(
                                row,
                                columnMap
                        );

                /*
                 * Ignore completely empty rows.
                 */
                if (isEmpty(data)) {
                    continue;
                }

                result.add(data);
            }
        }

        System.out.println(
                "✅ Excel data rows found: "
                        + result.size()
        );

        return result;
    }

    private int findHeaderRow(
            Sheet sheet,
            String requiredHeader) {

        for (int i = 0;
             i <= sheet.getLastRowNum();
             i++) {

            Row row =
                    sheet.getRow(i);

            if (row == null) {
                continue;
            }

            for (Cell cell : row) {

                String value =
                        formatter
                                .formatCellValue(cell)
                                .trim();

                if (value.equalsIgnoreCase(
                        requiredHeader)) {

                    return i;
                }
            }
        }

        return -1;
    }

    private Map<String, Integer> buildColumnMap(
            Row headerRow) {

        Map<String, Integer> map =
                new HashMap<>();

        for (Cell cell : headerRow) {

            String header =
                    formatter
                            .formatCellValue(cell)
                            .trim()
                            .toUpperCase();

            if (!header.isBlank()) {

                map.put(
                        normalize(header),
                        cell.getColumnIndex()
                );
            }
        }

        return map;
    }

    private String normalize(String value) {

        return value
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
    }

    private String getValue(
            Row row,
            Map<String, Integer> columnMap,
            String... possibleHeaders) {

        for (String header :
                possibleHeaders) {

            Integer index =
                    columnMap.get(
                            normalize(header)
                    );

            if (index != null) {

                Cell cell =
                        row.getCell(index);

                if (cell == null) {
                    return "";
                }

                return formatter
                        .formatCellValue(cell)
                        .trim();
            }
        }

        return "";
    }

    private ExcelRowData convertRow(
            Row row,
            Map<String, Integer> columnMap) {

        ExcelRowData data =
                new ExcelRowData();

        data.setInvoiceNumber(
                getValue(
                        row,
                        columnMap,
                        "INVOICE NO",
                        "INVOICE NUMBER",
                        "INVOICE"
                )
        );

        data.setInvoiceDate(
                getValue(
                        row,
                        columnMap,
                        "INVOICED DATE",
                        "INVOICE DATE"
                )
        );

        data.setBuyerName(
                getValue(
                        row,
                        columnMap,
                        "BUYER NAME",
                        "BUSINESS NAME",
                        "BUSINESS"
                )
        );

        data.setProductName(
                getValue(
                        row,
                        columnMap,
                        "PRODUCT NAME",
                        "PRODUCT"
                )
        );

        data.setUom(
                getValue(
                        row,
                        columnMap,
                        "UOM"
                )
        );

        data.setQuantity(
                getValue(
                        row,
                        columnMap,
                        "INITIAL ORDERED QTY",
                        "ORDERED QTY",
                        "QTY"
                )
        );

        data.setSalesUser(
                getValue(
                        row,
                        columnMap,
                        "SALES USER"
                )
        );

        return data;
    }

    private boolean isEmpty(
            ExcelRowData data) {

        return data.getInvoiceNumber().isBlank()
                && data.getProductName().isBlank()
                && data.getUom().isBlank();
    }

    private void printHeaders(
            Map<String, Integer> columnMap) {

        System.out.println(
                "========== EXCEL HEADERS =========="
        );

        for (String header :
                columnMap.keySet()) {

            System.out.println(
                    header
            );
        }

        System.out.println(
                "==================================="
        );
    }
}


