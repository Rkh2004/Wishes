package com.example.wishes.controller;

import com.example.wishes.dto.UserRegistrationDTO;
import com.example.wishes.model.User;
import com.example.wishes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        User user = userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok("User registered successfully");
    }
}
