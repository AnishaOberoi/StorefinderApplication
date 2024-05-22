package com.example.storefinderbackend.repository;

import com.example.storefinderbackend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);

    @Query("SELECT s FROM Store s JOIN s.products p WHERE p.name = :productName")
    List<Store> findStoresByProductName(@Param("productName") String productName);
}
