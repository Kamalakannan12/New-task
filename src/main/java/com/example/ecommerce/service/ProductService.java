package com.example.ecommerce.service;

import com.example.ecommerce.mobile.Mobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private Map<String, Mobile> mobileMap;

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
    public Product addProduct(String name, double price, int quantity, String brand) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setBrand(brand);
        product.setCreatedAt(product.CurrentDateTime());
        Mobile mobile = mobileMap.get(product.getBrand().toLowerCase());
        product.setDescription(mobile.getDetails());
        return repo.save(product);
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
