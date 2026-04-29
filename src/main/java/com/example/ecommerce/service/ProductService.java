package com.example.ecommerce.service;
import java.time.*;
import com.example.ecommerce.configuration.calculation;
import com.example.ecommerce.mobile.Mobile;
import com.example.ecommerce.module.ImageSaveThread;
import com.example.ecommerce.module.Imagesave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private Map<String, Mobile> mobileMap;
    @Autowired
    private Map<String, calculation> calculationMap;
    private final String uploadimg="upload/";

    public Product addProduct(Product product) {
        product.setCreatedAt(product.CurrentDateTime());
        Mobile mobile = mobileMap.get(product.getBrand().toLowerCase());

        if (mobile == null) {
            System.out.println("Invalid input");
        }
        product.setDescription(mobile.getDetails());

        return repo.save(product);
    }
    //Method overloading
    //image upload
    //Thread handle
    public Product addProduct(String name, double price, int quantity, String brand,String email, MultipartFile image) {

        try {
            // check image
            if (image == null || image.isEmpty()) {
                throw new RuntimeException("Image is empty");
            }

            // create folder
            String uploadDir = "C:\\Users\\acer\\Desktop" + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // save file
            LocalDateTime TimeDate= LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String time = LocalDateTime.now().format(formatter);
            String fileName = time + "_" + image.getOriginalFilename();
//            ImageSaveThread thread = new ImageSaveThread(image, uploadDir, fileName);
//            thread.start();
//            thread.sleep(1000);

            // Runnable thread
            Imagesave runthread=new Imagesave(image, uploadDir, fileName);
            Thread T1=new Thread(runthread);
            T1.start();

            // create product
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setBrand(brand);
//            product.setEmail(email);
            product.setCreatedAt(product.CurrentDateTime());
            product.setImagePath(fileName);
            Mobile mobile = mobileMap.get(product.getBrand().toLowerCase());
            if (mobile == null) {
                System.out.println("Invalid input");
            }
            product.setDescription(mobile.getDetails());

            //calculation
            List<Product> list = List.of(product);

            double amount = calculationMap.get("subtotal").calculate(list);
            double tax = calculationMap.get("tax").calculate(list);
            double total = calculationMap.get("final").calculate(list);

            product.setAmount(amount);
            product.setTax(tax);
            product.setTotal_Amount(total);

            return repo.save(product);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while uploading image");
        }
    }
//get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

//get product by Id
    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

//Update product
    public Product updateProduct(Long id, Product newProduct) {
        Product existing = repo.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(newProduct.getName());
            existing.setPrice(newProduct.getPrice());
            existing.setQuantity(newProduct.getQuantity());
            Mobile mobile = mobileMap.get(newProduct.getBrand().toLowerCase());

            if (mobile != null) {
                existing.setName(mobile.getDetails());
                existing.setBrand(newProduct.getBrand());
            }
            return repo.save(existing);
        }
        return null;
    }
//update specific value using patch
    public Product patchUpdateProduct(Long id, Product newProduct) {

        Product existing = repo.findById(id).orElse(null);

        if (existing != null && newProduct != null) {

            if (newProduct.getName() != null) {
                existing.setName(newProduct.getName());
            }

            if (newProduct.getPrice() != 0) {
                existing.setPrice(newProduct.getPrice());
            }

            if (newProduct.getQuantity() != 0) {
                existing.setQuantity(newProduct.getQuantity());
            }

            return repo.save(existing);
        }

        return null;
    }

    //Delete products
    public String deleteProduct(Long id) {
        repo.deleteById(id);
        return "Product deleted successfully";
    }
}
