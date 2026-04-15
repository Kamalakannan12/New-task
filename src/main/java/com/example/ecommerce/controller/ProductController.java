package com.example.ecommerce.controller;

import jakarta.transaction.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

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
            @RequestParam MultipartFile image)throws IOException {

        return service.addProduct(name, price, quantity,brand, image);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
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
}
