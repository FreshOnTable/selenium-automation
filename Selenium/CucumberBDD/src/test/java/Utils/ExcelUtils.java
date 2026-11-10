package Utils;

import java.io.FileInputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    public static void readExcel(File file) throws Exception {

        FileInputStream fis =
                new FileInputStream(file);

        Workbook workbook =
                WorkbookFactory.create(fis);

        Sheet sheet =
                workbook.getSheetAt(0);

        System.out.println(
                "Sheet Name: " + sheet.getSheetName()
        );

        for (Row row : sheet) {

            for (Cell cell : row) {

                System.out.print(
                        cell.toString() + " | "
                );
            }

            System.out.println();
        }

        workbook.close();
        fis.close();
    }
}