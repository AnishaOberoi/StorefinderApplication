package com.example.storefinderbackend.service;
import com.example.storefinderbackend.entity.Product;
import com.example.storefinderbackend.entity.Store;
import com.example.storefinderbackend.repository.ProductRepository;
import com.example.storefinderbackend.repository.StoreRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private final StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    public Store updateStore(Long id, Store storeDetails) {
        Store store = getStoreById(id);
        store.setName(storeDetails.getName());
        store.setContactDetails(storeDetails.getContactDetails());
        store.setOperatingHours(storeDetails.getOperatingHours());
        store.setDescription(storeDetails.getDescription());
        //store.setLocation(storeDetails.getLocation());
        return storeRepository.save(store);
    }

    public void deleteUser(Long id) {
        Store store = getStoreById(id);
        storeRepository.delete(store);
    }

    public Store findByName(String name) {
        return storeRepository.findByName(name);
    }
    public List<Store> findStoresByProductName(String productName) {
        return storeRepository.findStoresByProductName(productName);
    }

    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    public Store addProductToStore(Long storeId, Long productId) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (storeOptional.isPresent() && productOptional.isPresent()) {
            Store store = storeOptional.get();
            Product product = productOptional.get();
            store.getProducts().add(product);
            return storeRepository.save(store);
        }

        return null;
    }

}
