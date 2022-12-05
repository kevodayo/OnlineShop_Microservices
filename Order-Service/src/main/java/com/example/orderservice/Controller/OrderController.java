package com.example.orderservice.Controller;

import com.example.orderservice.DTO.OrderRequest;
import com.example.orderservice.DTO.Order_Response;
import com.example.orderservice.Entity.Order;
import com.example.orderservice.Service.Order_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/")
@RequiredArgsConstructor
public class OrderController {

    private final Order_Service order_service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        order_service.placeOrder(orderRequest);
        return "Order placed successfully.";
    }
    @GetMapping
    public List<Order_Response> getOrders(){
        return order_service.getOrders();
    }
}
