package com.example.ecommerce.service;
import com.example.ecommerce.entity.Order;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.example.ecommerce.service
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    @Autowired
    private OrderRepository orderRepo;

    public byte[] generateInvoice(Long orderId) {

        try {
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            document.add(new Paragraph("INVOICE")
                    .setBold()
                    .setFontSize(20));

            document.add(new Paragraph(" "));

            // Customer Details
            document.add(new Paragraph("Customer Name: " + order.getName()));
            document.add(new Paragraph("Email: " + order.getEmail()));
            document.add(new Paragraph("Phone: " + order.getPhoneNo()));

            document.add(new Paragraph(" "));

            // 🔹 Order Info
            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Order Date: " + order.getOrderDate()));

            document.add(new Paragraph(" "));

            // 🔹 Table
            float[] columnWidth = {200F, 100F, 100F};
            Table table = new Table(columnWidth);

            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Total");

            table.addCell(order.getProductName());
            table.addCell(String.valueOf(order.getQuantity()));
            table.addCell(String.valueOf(order.getTotalPrice()));

            document.add(table);

            document.add(new Paragraph(" "));

            // 🔹 Total
            document.add(new Paragraph("Total Amount: ₹ " + order.getTotalPrice())
                    .setBold());

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Thank you for your purchase!"));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Invoice generation failed: " + e.getMessage());
        }
    }
}
