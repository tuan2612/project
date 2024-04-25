package com.huce.project.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import com.huce.project.Entity.User;
import com.huce.project.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERNAME_REGEX = "^(?!\\d)[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String GMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional 
    public User registerUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
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

        return userRepository.save(user);
    }
    

    @Override
    @Transactional 
    public User loginUser(User user) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || existingUser.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username does not exist");
        } else {
            if (!existingUser.getPassword().equals(user.getPassword())) {
                throw new IllegalArgumentException("Incorrect password");
            }
            return existingUser;
        }
    }
}
