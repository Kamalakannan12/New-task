package com.example.ecommerce.module;
import com.example.ecommerce.module.ReadExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MergeFile {
      public static void copyRow(Row sourceRow, Row targetRow) {

          for (Cell cell : sourceRow) {

              Cell newCell = targetRow.createCell(cell.getColumnIndex());

              switch (cell.getCellType()) {

                  case STRING:
                      newCell.setCellValue(cell.getStringCellValue());
                      break;

                  case NUMERIC:
                      newCell.setCellValue(cell.getNumericCellValue());
                      break;

                  case BOOLEAN:
                      newCell.setCellValue(cell.getBooleanCellValue());
                      break;

                  default:
                      newCell.setCellValue(cell.toString());
              }
          }
      }
    public static void main(String[] args) {
        String file1 = "C:\\Users\\acer\\Documents\\Book1.xlsx";
        String file2 = "C:\\Users\\acer\\Desktop\\Book1.xlsx";
        String output = "C:\\Users\\acer\\Desktop\\Merged.xlsx";

        try {
            Workbook wb1 = new XSSFWorkbook(new FileInputStream(file1));
            Workbook wb2 = new XSSFWorkbook(new FileInputStream(file2));

            Sheet sheet1 = wb1.getSheetAt(0);
            Sheet sheet2 = wb2.getSheetAt(0);

            Workbook mergedWB = new XSSFWorkbook();
            Sheet mergedSheet = mergedWB.createSheet("Merged Data");

            int rowIndex = 0;

            //  Copy File1 data
            for (Row row : sheet1) {
                Row newRow = mergedSheet.createRow(rowIndex++);

                copyRow(row, newRow);
            }

            //  Copy File2 data (skip header)
            for (Row row : sheet2) {
                if (row.getRowNum() == 0) continue;

                Row newRow = mergedSheet.createRow(rowIndex++);
                copyRow(row, newRow);
            }

            FileOutputStream out = new FileOutputStream(output);
            mergedWB.write(out);

            wb1.close();
            wb2.close();
            mergedWB.close();
            out.close();

            System.out.println("Excel files merged successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
