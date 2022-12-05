package com.example.productservice.Controller;

import com.example.productservice.DTO.Product_Request;
import com.example.productservice.DTO.Product_Response;
import com.example.productservice.Entity.Product;
import com.example.productservice.Service.Product_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product/")
public class Product_Controller {

    private final Product_Service product_service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product_Request product_request) {
        return product_service.createProduct(product_request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product_Response> getProducts() {
        return product_service.getAllProducts();
    }


    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> getProductsByPage(@RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(0, 10);
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        Page<Product> pages = product_service.getAllProductsByPage(pageable);
        return pages;

    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Product_Response> getProductsByName(@RequestParam String name) {
        return product_service.getProductByProductName(name);
    }
}
