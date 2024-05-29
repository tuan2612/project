package com.huce.project.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.huce.project.config.JWTConfig;
import com.huce.project.entity.UserEntity;
import com.huce.project.model.LoginRequestDTO;
import com.huce.project.model.LoginResponseDTO;
import com.huce.project.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity newUser) {
        try {
            userService.registerUser(newUser);
            try {
                // Dừng luồng thực thi trong 1000 giây (1 giây = 1000 milliseconds)
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Xử lý nếu luồng bị gián đoạn
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Thread was interrupted", e);
            }
            userService.AddFolder(newUser.getUsername(),newUser.getUsername());
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity userEntity,HttpSession session,HttpServletResponse response) {
        try {

            LoginRequestDTO loginRequestDTO = modelMapper.map(userEntity, LoginRequestDTO.class);

            userService.loginUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

            String token = JWTConfig.generateToken(response,loginRequestDTO.getUsername());
            LoginResponseDTO responseDTO = new LoginResponseDTO(token);
            session.setAttribute("name", userEntity.getUsername());
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            // Nếu có lỗi xảy ra trong quá trình đăng nhập, trả về lỗi UNAUTHORIZED
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
