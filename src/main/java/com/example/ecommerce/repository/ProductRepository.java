package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;

//product storage repository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}