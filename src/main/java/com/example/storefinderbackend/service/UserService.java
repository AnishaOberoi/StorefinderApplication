package com.example.storefinderbackend.service;


import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.exception.UserException;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
