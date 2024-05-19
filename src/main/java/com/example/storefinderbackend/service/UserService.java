package com.example.storefinderbackend.service;

import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public List<Integer> getFavoriteStores(Long id) {
        User user = getUserById(id);
        return user.getFavStores();
    }

    public User addFavoriteStore(Long id, Integer storeId) {
        User user = getUserById(id);
        user.addFavStore(storeId);
        return userRepository.save(user);
    }

    public User removeFavoriteStore(Long id, Integer storeId) {
        User user = getUserById(id);
        user.removeFavStore(storeId);
        return userRepository.save(user);
    }
}