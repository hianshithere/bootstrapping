package com.practice.bootstrapping.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.practice.bootstrapping.entity.User;

public class ExcelHelper {
    private ExcelHelper() {
    }

    /**
     * Parse the first sheet of the provided Excel input stream into a list of {@link User}.
     * The first row is treated as a header and skipped. Cell values are read using
     * {@link DataFormatter} to safely handle numeric/boolean/date cells.
     *
     * Notes:
     * - This method preserves the original InputStream ownership (it does not close the stream).
     * - It defensively handles blank rows and missing cells.
     */
    public static List<User> excelToUsers(InputStream is) {
        DataFormatter formatter = new DataFormatter();
        List<User> users = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return users;
            }

            // Validate header row contains expected columns (firstname, lastname, description)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Missing header row in Excel sheet");
            }
            String h0 = new DataFormatter().formatCellValue(headerRow.getCell(0)).trim().toLowerCase();
            String h1 = new DataFormatter().formatCellValue(headerRow.getCell(1)).trim().toLowerCase();
            String h2 = new DataFormatter().formatCellValue(headerRow.getCell(2)).trim().toLowerCase();
            if (!"firstname".equals(h0) || !"lastname".equals(h1) || !"description".equals(h2)) {
                throw new IllegalArgumentException("Invalid header. Expected: firstname, lastname, description but found: "
                        + h0 + ", " + h1 + ", " + h2);
            }

            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header row
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                // Defensive: skip completely blank rows
                if (isRowBlank(currentRow)) {
                    rowNumber++;
                    continue;
                }

                User user = new User();

                // Read cells by index (0=firstname,1=lastname,2=description)
                user.setFirstname(getCellString(currentRow, 0, formatter));
                user.setLastname(getCellString(currentRow, 1, formatter));
                user.setDescription(getCellString(currentRow, 2, formatter));

                users.add(user);
                rowNumber++;
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file", e);
        }
    }

    private static String getCellString(Row row, int index, DataFormatter formatter) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        String value = formatter.formatCellValue(cell);
        if (value != null) {
            value = value.trim();
            if (value.isEmpty()) {
                return null;
            }
        }
        return value;
    }

    private static boolean isRowBlank(Row row) {
        if (row == null) return true;
        Iterator<Cell> cellIter = row.cellIterator();
        DataFormatter formatter = new DataFormatter();
        while (cellIter.hasNext()) {
            Cell c = cellIter.next();
            String v = formatter.formatCellValue(c);
            if (v != null && !v.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
