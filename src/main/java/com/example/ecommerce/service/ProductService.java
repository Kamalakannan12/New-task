package com.example.ecommerce.service;

import com.example.ecommerce.configuration.calculation;
import com.example.ecommerce.mobile.Mobile;
import com.example.ecommerce.configuration.ImageSaveThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    public Product addProduct(String name, double price, int quantity, String brand, MultipartFile image) {

        try {
            // check image
            if (image == null || image.isEmpty()) {
                throw new RuntimeException("Image is empty");
            }

            // create folder
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // save file
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//            File file = new File(uploadDir + fileName);
//            image.transferTo(file);
            ImageSaveThread thread = new ImageSaveThread(image, uploadDir, fileName);
            thread.start();

            // create product
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setBrand(brand);
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
            e.printStackTrace(); // VERY IMPORTANT
            throw new RuntimeException("Error while uploading image");
        }
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

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

    public String deleteProduct(Long id) {
        repo.deleteById(id);
        return "Product deleted successfully";
    }
}
