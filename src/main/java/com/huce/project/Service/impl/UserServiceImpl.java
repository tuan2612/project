package com.huce.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.io.IOException;
import java.nio.file.*;
import com.huce.project.repository.AccessRightRepository;
import com.huce.project.repository.FolderRepository;
import com.huce.project.repository.UserRepository;
import com.huce.project.service.UserService;
import com.huce.project.entity.AccessRightEntity;
import com.huce.project.entity.EnumAccessType;
import com.huce.project.entity.FolderEntity;
import com.huce.project.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERNAME_REGEX = "^(?!\\d)[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String pathroot="D:\\StoreFileUser";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FolderRepository folderrepo;
    @Autowired
    private AccessRightRepository accessrepo;
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

    @Override
    public void AddFolder(String username, String namefolder) {
        UserEntity userentity = userRepository.findByUsername(username);
        if(userentity!=null){
            Date currentDate = new Date();
            FolderEntity folder=new FolderEntity();
            folder.setFoldername(pathroot+"\\"+namefolder);
            folder.setFolder_size(0);
            folder.setCreatedAt(currentDate);
            folderrepo.save(folder);
            Path root = Paths.get(pathroot,namefolder);
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                // Dừng luồng thực thi trong 1000 giây (1 giây = 1000 milliseconds)
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Xử lý nếu luồng bị gián đoạn
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Thread was interrupted", e);
            }
            AccessRightEntity accsess=new AccessRightEntity();
            long folder_id=folderrepo.findByFoldername(pathroot+"\\"+namefolder).getFolderId();
            if(folder_id!=0){
                accsess.setUserId(userentity.getUserID());
            accsess.setAccessType(EnumAccessType.READ_WRITE);
            accsess.setFofId(folder_id);
            accsess.setTypefof(0);
            accessrepo.save(accsess);
            }
        }

    }
}
