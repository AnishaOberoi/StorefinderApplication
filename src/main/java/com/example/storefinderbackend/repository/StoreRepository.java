package com.example.storefinderbackend.repository;

import com.example.storefinderbackend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);


}
