package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Invoice;
import com.example.ecommerce.service.InvoiceService;
import com.example.ecommerce.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private PdfService pdfService;
@Autowired
private InvoiceService invoiceService;
//    @GetMapping("/{orderId}")
//    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {
//
//        byte[] pdf = pdfService.generateInvoice(orderId);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=invoice_" + orderId + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }
    @GetMapping("/invoiceid/{Id}")
    public ResponseEntity<?> getinvoicedetai(@PathVariable Long Id){
       Invoice check= invoiceService.checkbyid(Id);
       if(check==null){
       return ResponseEntity.status(404).body("Invoice not found");}
       else
           return ResponseEntity.status(200).body(check);

    }
}
