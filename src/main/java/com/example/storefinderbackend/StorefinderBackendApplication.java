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

}
