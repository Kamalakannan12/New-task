package com.example.ecommerce.module;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicate {

    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("C:\\Users\\acer\\Documents\\Book1.xlsx");
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Set<String> uniqueValues = new HashSet<>();
            Set<String> duplicates = new HashSet<>();

            for (Row row : sheet) {
                Cell cell = row.getCell(1); // column index (Name column)

                if (cell != null) {
                    String value = cell.toString();

                    if (!uniqueValues.add(value)) {
                        duplicates.add(value); // duplicate found
                    }
                }
            }

            System.out.println("Duplicate Values:");
            for (String dup : duplicates) {
                System.out.println(dup);
            }

            workbook.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
