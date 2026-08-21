package InventoryManagment;

import InventoryManagment.entity.Product;
import InventoryManagment.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryReservation {
    public static void main(String[] args) {
        SpringApplication.run(InventoryReservation.class, args);
    }

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository){
        return args -> {
            if(productRepository.count() == 0){
                Product p1 = new Product();
                p1.setName("laptop");
                p1.setAvailableQuantity(10);
                productRepository.save(p1);
            }
        };
    }
}
