package com.example.productservice.Service;

import com.example.productservice.DTO.Product_Request;
import com.example.productservice.DTO.Product_Response;
import com.example.productservice.Entity.Product;
import com.example.productservice.Repository.Product_Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class Product_Service {

    private final Product_Repository product_repository;

    public Product createProduct(Product_Request product_request) {

        Product product = Product.builder()
                .description(product_request.getDescription())
                .price(product_request.getPrice())
                .name(product_request.getName())
                .build();
        log.info("product is saved");
        return product_repository.save(product);
    }

    public List<Product_Response> getAllProducts() {
        List<Product> products = product_repository.findAll();
        return products.stream().map(product -> mapToProduct(product)).collect(Collectors.toList());
    }


    public Page<Product> getAllProductsByPage(Pageable pageable) {
        Page<Product> products = product_repository.findAll(pageable);
        return products;
    }

    public List<Product_Response> getProductByProductName(String name) {
        List<Product> products = product_repository.findByNameContainingIgnoreCase(name);
        return products.stream().map(product -> mapToProduct(product)).collect(Collectors.toList());
    }

    private Product_Response mapToProduct(Product product) {
        return Product_Response.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
