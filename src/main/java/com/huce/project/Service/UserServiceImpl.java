package com.huce.project.Service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.huce.project.Entity.UserEntity;
import com.huce.project.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERNAME_REGEX = "^(?!\\d)[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ;

    @Autowired
    private UserRepository userRepository;

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

        if (!user.getEmail().matches(GMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        user.setRole_id(1);; // Default role for users is 1

        // Tạo timestamp cho ngày đăng ký
        Timestamp registrationTimestamp = new Timestamp(new Date().getTime());
        user.setRegistrationDate(registrationTimestamp);
    
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void loginUser(String username, String password) {
        UserEntity existingUser = userRepository.findByUsername(username);
        if (existingUser == null ) {

            throw new IllegalArgumentException("Username does not exist");
        } else {

            if (!existingUser.getPassword().equals(password)) {
                throw new IllegalArgumentException("Incorrect password");
            }
        }
    }

}
