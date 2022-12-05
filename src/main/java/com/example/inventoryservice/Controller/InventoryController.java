package com.example.inventoryservice.Controller;

import com.example.inventoryservice.DTO.Inventory_Response;
import com.example.inventoryservice.Service.Inventory_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final Inventory_Service inventory_service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory_Response> isInStock(@RequestParam List<String> skucode){
       return inventory_service.isInStock(skucode);
    }

}
