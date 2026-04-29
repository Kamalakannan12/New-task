package com.example.ecommerce.controller;

import com.example.ecommerce.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {

        byte[] pdf = pdfService.generateInvoice(orderId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice_" + orderId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
