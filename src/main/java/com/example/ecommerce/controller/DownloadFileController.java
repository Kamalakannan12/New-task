package com.example.ecommerce.controller;

import com.example.ecommerce.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/export")
public class DownloadFileController {
    @Autowired
    private DownloadService downloadService;
    @Autowired

    @GetMapping("/xlsx")
    public ResponseEntity<byte[]> downloadxlsx(){
        byte[] exportdata=downloadService.downloadxlsxfile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(exportdata);
    }
    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> downloadCsv(){
        ByteArrayInputStream file = downloadService.generateCsv();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(file));
    }
//    @GetMapping("/pdf")
//    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable Long id) {
//
//        ByteArrayInputStream pdf = downloadService.generatePdf(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdf));
//    }


}
