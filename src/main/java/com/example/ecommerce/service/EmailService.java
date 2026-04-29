package com.example.ecommerce.service;

import com.example.ecommerce.entity.Order;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    public void sendOrderConfirmation(String toEmail, Order order,byte[] pdfbytes,String ccEmal) {
try {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom("kamalakannan200418@gmail.com");
    helper.setTo(toEmail);
    helper.setCc(ccEmal);
    helper.setSubject("Order Placed Successfully");

    helper.setText(
            "Order Confirmed!\n\n" +
                    "Order ID: " + order.getId() + "\n" +
                    "Product: " + order.getProductName() + "\n" +
                    "Quantity: " + order.getQuantity() + "\n" +
                    "Total Price: " + order.getTotalPrice()
    );
    //FileSystemResource files=new FileSystemResource(new File("C:\\Users\\acer\\Documents\\Book1.xlsx"));
    helper.addAttachment("Invoice"+order.getId()+".pdf",new ByteArrayResource(pdfbytes));

    mailSender.send(message);
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    //multiple user
    public void sendallmail(String[] allmail)  {
try {
    for (String email : allmail) {

        if (email != null && !email.trim().isEmpty()) {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("kamalakannan200418@gmail.com");
            helper.setTo(email);
            helper.setSubject("New offers going on");
            String htmlContent =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<body style='font-family: Arial, sans-serif; background-color:lightgray; margin:0; padding:0;'>" +

                            "<table align='center' width='600' style='background-color:white; border-radius:10px; overflow:hidden;'>" +

                            // Header
                            "<tr>" +
                            "<td style='background-color:blue; color:white; text-align:center; padding:20px;'>" +
                            "<h1>🎉 Special Offers Just for You!</h1>" +
                            "</td>" +
                            "</tr>" +

                            // Body
                            "<tr>" +
                            "<td style='padding:20px; color:black;'>" +
                            "<h2>Hello Customer,</h2>" +
                            "<p>We are excited to bring you our latest <b>exclusive deals</b> and offers.</p>" +
                            "<p>Don't miss out on amazing discounts available for a limited time.</p>" +

                            // Button
                            "<div style='text-align:center; margin:30px 0;'>" +
                            "<a href='https://www.flipkart.com' " +
                            "style='background-color:green; color:white; padding:12px 25px; text-decoration:none; border-radius:5px; font-size:16px;'>" +
                            "Shop Now" +
                            "</a>" +
                            "</div>" +

                            "<p>If you have any questions, feel free to contact us.</p>" +
                            "</td>" +
                            "</tr>" +

                            // Footer
                            "<tr>" +
                            "<td style='background-color:lightgray; text-align:center; padding:15px; font-size:12px; color:gray;'>" +
                            "<p>© 2026 Your Company. All rights reserved.</p>" +
                            "<p><a href='https://www.flipkart.com' style='color:blue;'>Visit Website</a></p>" +
                            "</td>" +
                            "</tr>" +

                            "</table>" +
                            "</body>" +
                            "</html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        }
    }
} catch (Exception e) {
    logger.error("something went wrong",e);
}
    }

    public void sendcc(String[] newmail, String cc) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("kamalakannan200418@gmail.com");
            helper.setTo(newmail);
            helper.setCc(cc);
            helper.setSubject("Order Placed Successfully");

            helper.setText("order placed");
            FileSystemResource files=new FileSystemResource(new File("C:\\Users\\acer\\Documents\\Book1.xlsx"));
            helper.addAttachment("Book1.xlsx",files);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    }

