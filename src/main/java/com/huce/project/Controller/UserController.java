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

@RestController
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
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity userEntity) {
        try {

            LoginRequestDTO loginRequestDTO = modelMapper.map(userEntity, LoginRequestDTO.class);

            userService.loginUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

            String token = JWTConfig.generateToken(loginRequestDTO.getUsername());
            LoginResponseDTO responseDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            // Nếu có lỗi xảy ra trong quá trình đăng nhập, trả về lỗi UNAUTHORIZED
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
