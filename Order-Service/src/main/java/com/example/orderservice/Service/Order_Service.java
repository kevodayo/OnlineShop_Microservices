package com.example.orderservice.Service;

import com.example.orderservice.DTO.*;
import com.example.orderservice.Entity.Order;
import com.example.orderservice.Entity.OrderLineitems;
import com.example.orderservice.Repository.OrderRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Order_Service {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List <OrderLineitems> orderLineitems = orderRequest.getOrderlineRequestDtos().stream()
                        .map(orderlineRequestDto -> mapToEntity(orderlineRequestDto))
                        .collect(Collectors.toList());
        order.setOrderLineItems(orderLineitems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineitems::getSkuCode)
                .collect(Collectors.toList());

        //Call inventory service and place order if the product is in stock
        Inventory_Response [] result = webClient.get().uri("http://localhost:8083/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skucode", skuCodes).build())
                        .retrieve().bodyToMono(Inventory_Response[].class).block();

        Boolean allproducts = Arrays.stream(result).allMatch(inventory_response -> inventory_response.isInStock());
        if(allproducts){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

    private OrderLineitems mapToEntity(OrderlineRequestDto orderlineRequestDto) {

        OrderLineitems orderLineitems = new OrderLineitems();
        orderLineitems.setQuantity(orderlineRequestDto.getQuantity());
        orderLineitems.setPrice(orderlineRequestDto.getPrice());
        orderLineitems.setSkuCode(orderlineRequestDto.getSkuCode());
        return orderLineitems;
    }

    public List<Order_Response> getOrders(){
        List <Order> orders = orderRepository.findAll();
       return orders.stream().map(order -> mapToOrderResponseDTO(order)).collect(Collectors.toList());
        //return orderRepository.findAll();
    }

    private Order_Response mapToOrderResponseDTO(Order order) {

        return Order_Response.builder()
                .orderLineResponseDTOS(order.getOrderLineItems().stream().map(orderLineitems -> mapToOrderlineResponseDTO(orderLineitems)).collect(Collectors.toList()))
                .build();
    }
    private OrderLineResponseDTO mapToOrderlineResponseDTO(OrderLineitems orderLineitems) {

       return OrderLineResponseDTO.builder()
               .price(orderLineitems.getPrice())
               .quantity(orderLineitems.getQuantity())
               .skuCode(orderLineitems.getSkuCode())
               .build();
    }
}
