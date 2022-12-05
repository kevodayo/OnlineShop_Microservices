package com.example.inventoryservice.Service;

import com.example.inventoryservice.DTO.Inventory_Response;
import com.example.inventoryservice.Repository.Inventory_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Inventory_Service {

    private final Inventory_Repository inventory_repository;

    @Transactional(readOnly = true)
    public List<Inventory_Response> isInStock(List<String> skucode){
       return inventory_repository.findBySkucodeIn(skucode)
               .stream().map(inventory -> Inventory_Response.builder()
                       .isInStock(inventory.getQuantity() > 0)
                       .skucode(inventory.getSkucode())
                       .build()).collect(Collectors.toList());
     }
}
