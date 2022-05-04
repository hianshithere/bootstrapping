package com.practice.bootstrapping.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.practice.bootstrapping.entity.User;

public class ExcelHelper {

	public static List<User> excelToUsers(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			List<User> users = new ArrayList<>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				User user = new User();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						user.setFirstname(currentCell.getStringCellValue());
						break;
					case 1:
						user.setLastname(currentCell.getStringCellValue());
						break;
					case 2:
						user.setDescription(currentCell.getStringCellValue());
						break;
					}
					cellIdx++;
				}
				users.add(user);
			}
			workbook.close();
			return users;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
