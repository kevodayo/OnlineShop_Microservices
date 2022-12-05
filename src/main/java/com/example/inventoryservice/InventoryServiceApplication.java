package com.example.inventoryservice;

import com.example.inventoryservice.Entity.Inventory;
import com.example.inventoryservice.Repository.Inventory_Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(Inventory_Repository inventory_repository){

		return args -> {
			Inventory inventory = new Inventory();
			inventory.setQuantity(986);
			inventory.setSkucode("iphone_code");

			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(1);
			inventory1.setSkucode("iphone_code6");

			inventory_repository.save(inventory);
			inventory_repository.save(inventory1);
		};
	}

}
