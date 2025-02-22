package com.practice.bootstrapping.helper;

import com.practice.bootstrapping.entity.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    private static final int HEADER_ROW_INDEX = 0;
    private static final int FIRSTNAME_COLUMN_INDEX = 0;
    private static final int LASTNAME_COLUMN_INDEX = 1;
    private static final int DESCRIPTION_COLUMN_INDEX = 2;

    private ExcelHelper() {
        // Prevent instantiation
    }

    public static List<User> excelToUsers(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            return extractUsersFromSheet(sheet);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage(), e);
        }
    }

    private static List<User> extractUsersFromSheet(Sheet sheet) {
        List<User> users = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // Start from 1 to skip header
            Row currentRow = sheet.getRow(rowIndex);
            if (currentRow != null) {
                User user = createUserFromRow(currentRow);
                users.add(user);
            }
        }
        return users;
    }

    private static User createUserFromRow(Row row) {
        User user = new User();
        user.setFirstname(getCellValue(row, FIRSTNAME_COLUMN_INDEX));
        user.setLastname(getCellValue(row, LASTNAME_COLUMN_INDEX));
        user.setDescription(getCellValue(row, DESCRIPTION_COLUMN_INDEX));
        return user;
    }

    private static String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        return (cell != null) ? cell.getStringCellValue() : "";
    }
}
