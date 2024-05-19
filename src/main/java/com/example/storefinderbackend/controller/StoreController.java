package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.entity.Store;
import com.example.storefinderbackend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Store> getStoreByName(@PathVariable String name) {
        Store store = storeService.findByName(name);
        return store != null ? ResponseEntity.ok(store) : ResponseEntity.notFound().build();
    }
}

