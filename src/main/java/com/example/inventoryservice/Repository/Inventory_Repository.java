package com.example.inventoryservice.Repository;

import com.example.inventoryservice.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Inventory_Repository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkucode(String skucode);

    List<Inventory> findBySkucodeIn(List<String> skucode);
}
