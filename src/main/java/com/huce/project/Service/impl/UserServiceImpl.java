package com.huce.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huce.project.repository.UserRepository;
import com.huce.project.service.UserService;
import com.huce.project.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERNAME_REGEX = "^(?!\\d)[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (!user.getUsername().matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException("Invalid username format");
        }

        if (!user.getPassword().matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("Password is not strong enough");
        }

        if (!user.getEmail().matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void loginUser(String username, String password) {
        UserEntity existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new IllegalArgumentException("Username does not exist");
        } else {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new IllegalArgumentException("Incorrect password");
            }
        }
    }
}
