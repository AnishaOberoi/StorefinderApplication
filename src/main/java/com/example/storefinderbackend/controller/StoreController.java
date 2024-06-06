package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.entity.Location;
import com.example.storefinderbackend.entity.Reviews;
import com.example.storefinderbackend.entity.Store;
import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.exception.AdminAccessRequiredException;
import com.example.storefinderbackend.repository.StoreRepository;
import com.example.storefinderbackend.service.StoreService;
import com.example.storefinderbackend.util.DistanceCalculator;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private final StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;


    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Store> getStoreByName(@PathVariable String name) {
        Store store = storeService.findByName(name);
        return store != null ? ResponseEntity.ok(store) : ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        return store != null ? ResponseEntity.ok(store) : ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<List<Store>> getStoresByProductName(@PathVariable String productName) {
        List<Store> stores = storeService.findStoresByProductName(productName);
        return stores.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stores);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteStoreById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user.getRole().equals("admin")) {
            try {
                storeService.deleteStore(id);
                return ResponseEntity.noContent().build();
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new AdminAccessRequiredException();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Store> addStore(@AuthenticationPrincipal User user, @RequestBody Store store) {
        if (user.getRole().equals("admin")) {
            Store savedStore = storeService.addStore(store);
            return ResponseEntity.ok(savedStore);
        } else {
            throw new AdminAccessRequiredException();
        }
    }

    @PostMapping("/{id}/add-product/{productId}")
    public ResponseEntity<Store> addProductToStore(@AuthenticationPrincipal User user, @PathVariable Long id, @PathVariable Long productId) {

            Store updatedStore = storeService.addProductToStore(id, productId);
            return updatedStore != null ? ResponseEntity.ok(updatedStore) : ResponseEntity.notFound().build();
    }

    @GetMapping("/nearest")
    public Store getNearestStore(@RequestParam double userLat, @RequestParam double userLng) {
        List<Store> stores = storeRepository.findAll();
        Store nearestStore = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Store store : stores) {
            Location location = store.getLocation();
            double distance = DistanceCalculator.calculateDistance(userLat, userLng, location.getLatitude(), location.getLongitude());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestStore = store;
            }
        }
        return nearestStore;
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<Store> addReviewToStore(@PathVariable Long id, @RequestBody Reviews review) {
        Store updatedStore = storeService.addReviewToStore(id, review);
        return ResponseEntity.ok(updatedStore);
    }

}

