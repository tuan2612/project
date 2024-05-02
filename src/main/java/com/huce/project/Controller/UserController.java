package com.huce.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.huce.project.Entity.UserEntity;
import com.huce.project.Service.UserService;
import com.huce.project.Utils.JWTUtils;
import com.huce.project.model.LoginRequestDTO;
import com.huce.project.model.LoginResponseDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity newUser) {
        try {

            userService.registerUser(newUser);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            
            userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
           String token = JWTUtils.generateToken(loginRequest.getUsername());
            LoginResponseDTO responseDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
