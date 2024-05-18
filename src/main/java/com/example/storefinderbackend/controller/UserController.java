package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/{id}/favStores")
    public ResponseEntity<List<Integer>> getFavoriteStores(@PathVariable Long id) {
        List<Integer> favStores = userService.getFavoriteStores(id);
        return ResponseEntity.ok(favStores);
    }

    @PostMapping(value = "/{id}/fav_stores")
    public ResponseEntity<User> addFavoriteStore(@PathVariable Long id, @RequestBody Integer storeId) {
        User user = userService.addFavoriteStore(id, storeId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}/favStores/{storeId}")
    public ResponseEntity<User> removeFavoriteStore(@PathVariable Long id, @PathVariable Integer storeId) {
        User user = userService.removeFavoriteStore(id, storeId);
        return ResponseEntity.ok(user);
    }
}
