package com.huce.project.Service;
import com.huce.project.Entity.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(User user);

}

