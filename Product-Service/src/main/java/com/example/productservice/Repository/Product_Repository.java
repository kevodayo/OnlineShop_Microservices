package com.example.productservice.Repository;

import com.example.productservice.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Product_Repository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String productName);

    Page<Product> findAll(Pageable pageable);
}
