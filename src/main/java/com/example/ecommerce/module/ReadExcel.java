package com.example.ecommerce.module;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
    public static void main(String[] args) {

        String filePath = "C:\\Users\\acer\\Documents\\Book1.xlsx";

        try {
            //  Open file
            FileInputStream file = new FileInputStream(filePath);

            //  Create workbook
            Workbook workbook = new XSSFWorkbook(file);

            // Get first sheet
            Sheet sheet = workbook.getSheetAt(0);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {

                        case STRING:
                            System.out.print(cell.getStringCellValue() + " ");

                            break;

                        case NUMERIC:

                                if (DateUtil.isCellDateFormatted(cell)) {

                                    Date date = cell.getDateCellValue();

                                    System.out.println(sdf.format(date));

                                } else {

                                    double value = cell.getNumericCellValue();
                                    System.out.print( value+"\t");
                                }

                            break;

                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + " ");
                            break;

                        default:
                            System.out.print(" ");
                    }
                }
                System.out.println();
            }

            //display matching row value
            int product_id = 3;
            for (Row row : sheet) {
                if(row.getRowNum()==0){
                    continue;       //header skip
                }

                Cell cell = row.getCell(0); // id column

                if (cell != null && cell.getCellType() == CellType.NUMERIC) {

                    int prodNo = (int) cell.getNumericCellValue();

                    if (prodNo == product_id) {

                        System.out.println("Record Found:");

                        for (Cell c : row) {
                            System.out.print(c.toString() + " ");
                        }

                        System.out.println();
                    }
                }
            }

            //display column value using index
            int columnIndex=2;
            for (Row row : sheet) {

                Cell cell = row.getCell(columnIndex);

                if (cell != null) {
                    System.out.println(cell.toString());
                }
            }
            int lastrow=sheet.getLastRowNum();
            Row add= sheet.createRow(lastrow+1);
            add.createCell(0).setCellValue(6);
            add.createCell(1).setCellValue("Mobile");
            add.createCell(2).setCellValue(4);
            add.createCell(3).setCellValue(1008);
            Date date = sdf.parse("18-03-2026");   // convert string → Date

            Cell cell = add.createCell(4);
            cell.setCellValue(date);

            CellStyle style = workbook.createCellStyle();//it create style object to change appearence
            CreationHelper helper = workbook.getCreationHelper();//HELP to create format id for excel understand
            style.setDataFormat(helper.createDataFormat().getFormat("dd-MM-yyyy"));

            cell.setCellStyle(style);

            //delete data using index

            int regNoToDelete = 3; //  input

            int rowIndexToDelete = -1;

            //  Finding row
            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue; // skip header

                  cell = row.getCell(0); // RegNo column

                if (cell != null && cell.getCellType() == CellType.NUMERIC) {

                    int regNo = (int) cell.getNumericCellValue();

                    if (regNo == regNoToDelete) {
                        rowIndexToDelete = row.getRowNum();
                        break;
                    }
                }
            }
            file.close();
            if (rowIndexToDelete != -1) {

                int lastRowNum = sheet.getLastRowNum();

                if (rowIndexToDelete >= 0 && rowIndexToDelete < lastRowNum) {

                    // Shift rows up
                    sheet.shiftRows(rowIndexToDelete + 1, lastRowNum, -1);

                } else if (rowIndexToDelete == lastRowNum) {

                    // Last row remove
                    Row removingRow = sheet.getRow(rowIndexToDelete);
                    if (removingRow != null) {
                        sheet.removeRow(removingRow);
                    }
                }

                System.out.println("Row deleted successfully!");

            } else {
                System.out.println("Id not found!");
            }
            FileOutputStream out=new FileOutputStream(filePath);
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //copy file from one dir to another dir
        Path source = Paths.get("C:\\Users\\acer\\Documents\\Book1.xlsx");
        Path destination = Paths.get("C:\\Users\\acer\\Desktop\\Book1.xlsx");
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
