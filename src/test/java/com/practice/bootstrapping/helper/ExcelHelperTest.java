package com.practice.bootstrapping.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import com.practice.bootstrapping.entity.User;

import java.util.List;

public class ExcelHelperTest {

    @Test
    void excelToUsers_parsesRowsSuccessfully() throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            // header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("firstname");
            header.createCell(1).setCellValue("lastname");
            header.createCell(2).setCellValue("description");

            // row 1
            Row r1 = sheet.createRow(1);
            r1.createCell(0).setCellValue("Alice");
            r1.createCell(1).setCellValue("Smith");
            r1.createCell(2).setCellValue("Developer");

            // row 2 - numeric description to ensure DataFormatter handles it
            Row r2 = sheet.createRow(2);
            r2.createCell(0).setCellValue("Bob");
            r2.createCell(1).setCellValue("Jones");
            r2.createCell(2).setCellValue(12345);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            InputStream is = new ByteArrayInputStream(bos.toByteArray());

            List<User> users = ExcelHelper.excelToUsers(is);

            assertEquals(2, users.size());
            User u1 = users.get(0);
            assertEquals("Alice", u1.getFirstname());
            assertEquals("Smith", u1.getLastname());
            assertEquals("Developer", u1.getDescription());

            User u2 = users.get(1);
            assertEquals("Bob", u2.getFirstname());
            assertEquals("Jones", u2.getLastname());
            assertEquals("12345", u2.getDescription());
        }
    }
}
