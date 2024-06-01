package com.example.storefinderbackend.repository;

import com.example.storefinderbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByEmail(String email);

    User findByUsername(String username);

}
