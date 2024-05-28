package com.huce.project.service;
import com.huce.project.entity.UserEntity;

public interface UserService {
    UserEntity registerUser(UserEntity user);
    void loginUser(String username, String password);
    void AddFolder(String username,String namefolder);
}

