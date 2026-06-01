package com.example.ecommerce.service;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
public class DownloadService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    public byte[] downloadxlsxfile() {
        try{
        List<Order> orders=orderService.getorderstable();
        Workbook workbook= new XSSFWorkbook();
        Sheet sheet= workbook.createSheet("orders");
        Row header=sheet.createRow(0);
        header.createCell(0).setCellValue("Order_id");
        header.createCell(1).setCellValue("Order_date");
        header.createCell(2).setCellValue("quantity");
        header.createCell(3).setCellValue("total_price");
        header.createCell(4).setCellValue("product_id");
        header.createCell(5).setCellValue("user_id");
        int Rownum=1;
        for (Order order:orders){
            Row row=sheet.createRow(Rownum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getOrderDate().toLocalDate().toString());
            row.createCell(2).setCellValue(order.getQuantity());
            row.createCell(3).setCellValue(order.getTotalPrice());
            row.createCell(4).setCellValue(order.getProduct().getId());
            //row.createCell(5).setCellValue(order.getId());
        }
        for (int i = 0; i <= 5; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();}
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ByteArrayInputStream generateCsv() {

        List<Order> orders = orderService.getorderstable();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        writer.println("OrderId,OrderDate,ProductId,UserId,Quantity,Price");

        for (Order order : orders) {

            String orderDate = (order.getOrderDate() != null)
                    ? order.getOrderDate().toString()
                    : "";

            Long productId = (order.getProduct() != null)
                    ? order.getProduct().getId()
                    : null;

            writer.println(
                    order.getId() + "," +
                            orderDate + "," +
                            productId + "," +
                            order.getQuantity() + "," +
                            order.getTotalPrice()
            );
        }

        writer.flush();

        return new ByteArrayInputStream(out.toByteArray());
    }

//    public ByteArrayInputStream generatePdf(Long id) {
//
//        try {
//            UserDetails user =userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));;
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//            PdfWriter writer = new PdfWriter(out);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            // Title
//            document.add(new Paragraph("USER DETAIL")
//                    .setBold()
//                    .setFontSize(18));
//
//            document.add(new Paragraph(" "));
//
//            // Table (columns)
//            float[] columnWidth = {100F, 200F, 100F, 100F,200F,200F};
//            Table table = new Table(columnWidth);
//
//            table.addCell("ID");
//            table.addCell("Address");
//            table.addCell("Email");
//            table.addCell("Name");
//            table.addCell("PhoneNo");
//            table.addCell("LastActive");
//
//            // Data rows
//
//                table.addCell(String.valueOf(user.getId()));
//                table.addCell(user.getAddress());
//                table.addCell(String.valueOf(user.getEmail()));
//                table.addCell(String.valueOf(user.getName()));
//                table.addCell(String.valueOf(user.getPhone()));
//                table.addCell(user.getLastactive().toString());
//
//            document.add(table);
//
//            document.close();
//
//            return new ByteArrayInputStream(out.toByteArray());
//
//        } catch (Exception e) {
//            throw new RuntimeException("PDF generation failed: " + e.getMessage());
//        }
//    }

    public ByteArrayInputStream exportProductOrdersToExcel(Product product) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Product Orders");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Product ID");
            header.createCell(1).setCellValue("Product Name");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("quantity");
            header.createCell(4).setCellValue("CreatedTime");
            header.createCell(5).setCellValue("Brand");
            header.createCell(6).setCellValue("ImagePath");

            header.createCell(7).setCellValue("Order ID");
            header.createCell(8).setCellValue("Order Date");
            header.createCell(9).setCellValue("Quantity");
            header.createCell(10).setCellValue("Total Price");

            int rowIdx = 1;
            List<Order> orders = product.getOrders();

            boolean firstRow = true;
            if (orders != null && !orders.isEmpty()) {

                for (Order order : orders) {

                    Row row = sheet.createRow(rowIdx++);

                    if (firstRow) {

                        fillProduct(row, product);
                        firstRow = false;
                    } else {
                        for (int i = 0; i <= 6; i++) {
                            row.createCell(i).setCellValue("");
                        }
                    }
                   fillOrder(row,order);
                }

            }

            else {

                Row row = sheet.createRow(rowIdx++);

                fillProduct(row, product);
                fillEmpty(row, 7, 10);
            }
            for (int i = 0; i <= 10; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e) {
            throw new RuntimeException("xlsx generation failed: " + e.getMessage());
        }
    }
//all products
    private void fillProduct(Row row, Product product) {
        row.createCell(0).setCellValue(product.getId());
        row.createCell(1).setCellValue(product.getName());
        row.createCell(2).setCellValue(product.getPrice());
        row.createCell(3).setCellValue(product.getQuantity());
        row.createCell(4).setCellValue(product.getCreatedAt().toString());
        row.createCell(5).setCellValue(product.getBrand());
        row.createCell(6).setCellValue(product.getImagePath());
    }

    private void fillOrder(Row row, Order order) {
        row.createCell(7).setCellValue(order.getId());
        row.createCell(8).setCellValue(order.getOrderDate().toString());
        row.createCell(9).setCellValue(order.getQuantity());
        row.createCell(10).setCellValue(order.getTotalPrice());
    }

    private void fillEmpty(Row row, int start, int end) {
        for (int i = start; i <= end; i++) {
            row.createCell(i).setCellValue("");
        }
    }
    public ByteArrayInputStream export(List<Product> products) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        int rowIdx = 0;

        // HEADER
        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Product ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Price");
        header.createCell(3).setCellValue("Quantity");
        header.createCell(4).setCellValue("Created At");
        header.createCell(5).setCellValue("Brand");
        header.createCell(6).setCellValue("Image");

        header.createCell(7).setCellValue("Order ID");
        header.createCell(8).setCellValue("Order Date");
        header.createCell(9).setCellValue("Order Qty");
        header.createCell(10).setCellValue("Total Price");

        for (Product product : products) {

            List<Order> orders = product.getOrders();
            boolean firstRow = true;

            //  Has Order
            if (orders != null && !orders.isEmpty()) {

                for (Order order : orders) {

                    Row row = sheet.createRow(rowIdx++);

                    if (firstRow) {
                        fillProduct(row, product);
                        firstRow = false;
                    } else {
                        fillEmpty(row, 0, 6);
                    }

                    fillOrder(row, order);
                }

            }
            //  No Orders
            else {

                Row row = sheet.createRow(rowIdx++);
                fillProduct(row, product);
                fillEmpty(row, 7, 10);
            }
        }
        for (int i = 0; i <= 10; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream exportCSV(List<Product> products) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        // Header row (removed description)
        writer.write("ProductId,Name,Price,Quantity,CreatedAt,Brand,ImagePath,OrderId,OrderDate,OrderQty,TotalPrice");
        writer.println();

        for (Product product : products) {

            List<Order> orders = product.getOrders();

            // CASE 1: No orders
            if (orders == null || orders.isEmpty()) {
                writer.write(
                        product.getId() + "," +
                                product.getName() + "," +
                                product.getPrice() + "," +
                                product.getQuantity() + "," +
                                product.getCreatedAt() + "," +
                                product.getBrand() + "," +
                                product.getImagePath() + "," +
                                ",,,," // empty order columns
                );
                writer.println();
            }

            // CASE 2: Has orders
            else {
                boolean firstRow = true;

                for (Order order : orders) {

                    if (firstRow) {
                        writer.write(
                                product.getId() + "," +
                                        product.getName() + "," +
                                        product.getPrice() + "," +
                                        product.getQuantity() + "," +
                                        product.getCreatedAt() + "," +
                                        product.getBrand() + "," +
                                        product.getImagePath() + ","
                        );
                        firstRow = false;
                    } else {
                        // Avoid repeating product details
                        writer.write(",,,,,,,");

                    }

                    // Order details
                    writer.write(
                            order.getId() + "," +
                                    order.getOrderDate() + "," +
                                    order.getQuantity() + "," +
                                    order.getTotalPrice()
                    );

                    writer.println();
                }
            }
        }

        writer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
