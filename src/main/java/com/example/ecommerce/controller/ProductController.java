package com.example.ecommerce.controller;

import com.example.ecommerce.service.DownloadService;
import jakarta.transaction.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private DownloadService downloadService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }
    @PostMapping("/add/manual")
    public Product addProduct(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam String brand,
            @RequestParam String email,
            @RequestParam MultipartFile image)throws IOException {

        return service.addProduct(name, price, quantity,brand,email,image);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product>products=service.getAllProducts();
        return products;
    }

    @GetMapping("/{id}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProduct(@PathVariable Long id) {

        Product check = service.getProductById(id);
        if(check==null){
            return ResponseEntity.status(404).body("product not found");
        }
        return ResponseEntity.status(200).body(check);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @PatchMapping("/{id}")
    public Product updateProductPartially(@PathVariable Long id,
                                          @RequestBody Product product) {
        return service.patchUpdateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {

        String path = System.getProperty("user.dir") + "/uploads/" + fileName;
        File file = new File(path);

        byte[] image = Files.readAllBytes(file.toPath());

        return ResponseEntity
                .ok()
                .header("Content-Type", Files.probeContentType(file.toPath()))
                .body(image);
    }
    @GetMapping("/export/product/{id}")
    public ResponseEntity<byte[]> export(@PathVariable Long id) throws Exception {
        Product product = service.getProductById(id);

        ByteArrayInputStream excel = downloadService.exportProductOrdersToExcel(product);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=product_orders.xlsx")
                .body(excel.readAllBytes());
    }
    @GetMapping("/export/product")
    public ResponseEntity<byte[]> export() throws Exception {
        List<Product>products=service.getAllProducts();
        ByteArrayInputStream excel = downloadService.export(products);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=product_orders.xlsx")
                .body(excel.readAllBytes());
    }
    @GetMapping("/export/csv")
    public ResponseEntity<?> exportCSV() throws Exception {

        List<Product> products = service.getAllProducts();

        ByteArrayInputStream csv = downloadService.exportCSV(products);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product_orders.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csv));
    }
//    @GetMapping("/export/user/{id}")
//    public ResponseEntity<?> generatepdf(@PathVariable Long id) throws Exception {
//        ByteArrayInputStream pdf = downloadService.generatePdf(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product_orders.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdf));
//    }
}
