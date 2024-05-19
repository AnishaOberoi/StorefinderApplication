package com.example.storefinderbackend.service;
import com.example.storefinderbackend.entity.Store;
import com.example.storefinderbackend.repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

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

}
