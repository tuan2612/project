package com.huce.project.Service;
import com.huce.project.Entity.UserEntity;

public interface UserService {
    UserEntity registerUser(UserEntity user);
    UserEntity loginUser(UserEntity user);

}

