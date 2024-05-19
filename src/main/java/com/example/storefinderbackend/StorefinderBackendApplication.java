package com.example.storefinderbackend;

import com.example.storefinderbackend.entity.Store;
import com.example.storefinderbackend.repository.StoreRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StorefinderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorefinderBackendApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(StoreRepository storeRepository) {
		return args -> {
			System.out.println("Adding initial stores...");

			//storeRepository.save(new Store(null, "Store1", "123 Main St", "123-456-7890", "9 AM - 5 PM", "A nice store", "http://example.com/logo1.png"));
			//storeRepository.save(new Store(null, "Store2", "456 Elm St", "987-654-3210", "10 AM - 6 PM", "Another nice store", "http://example.com/logo2.png"));

			System.out.println("Finding store by name...");

			Store store = storeRepository.findByName("Store2");
			if (store != null) {
				System.out.println("Store found: " + store);
			} else {
				System.out.println("Store not found");
			}
		};
	}
}
