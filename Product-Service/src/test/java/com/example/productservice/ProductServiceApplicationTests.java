package com.example.productservice;

import com.example.productservice.DTO.Product_Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class  ProductServiceApplicationTests {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @DynamicPropertySource
    static void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
    @Test
    void shouldcreateProduct()  {

        try {
            Product_Request product_request =  getProductRequest();
            String productRequestString = objectMapper.writeValueAsString(product_request);
            mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(productRequestString))
                    .andExpect(status().isCreated());
        }catch (Exception e){
            System.out.println("There is an error");
        }


    }

    private Product_Request getProductRequest() {
        return Product_Request.builder()
                .name("eggs")
                .description("2 trays")
                .price(BigDecimal.valueOf(15))
                .build();
    }

}
