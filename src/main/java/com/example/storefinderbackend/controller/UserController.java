package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.request.StoreIdRequest;
import com.example.storefinderbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/fav_stores")
    public ResponseEntity<List<Long>> getFavoriteStores(@PathVariable Long id) {
        List<Long> favStores = userService.getFavoriteStores(id);
        return ResponseEntity.ok(favStores);
    }

    @PostMapping("/{id}/fav_stores")
    public ResponseEntity<User> addFavoriteStore(@PathVariable Long id, @RequestBody StoreIdRequest storeIdRequest) {
        Long storeId=storeIdRequest.getStoreId();
        User user = userService.addFavoriteStore(id, storeId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}/fav_stores/{storeId}")
    public ResponseEntity<User> removeFavoriteStore(@PathVariable Long id, @PathVariable Long storeId) {
        User user = userService.removeFavoriteStore(id, storeId);
        return ResponseEntity.ok(user);
    }
}
