package com.example.storefinderbackend.repository;

import com.example.storefinderbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
