package com.example.wishes.service;

import com.example.wishes.dto.UserRegistrationDTO;
import com.example.wishes.exception.BadRequestException;
import com.example.wishes.exception.ResourceNotFoundException;
import com.example.wishes.model.Role;
import com.example.wishes.model.User;
import com.example.wishes.repository.RoleRepository;
import com.example.wishes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO userRegistrationDTO){
        //validate username
        if(userRepository.existsByUsername(userRegistrationDTO.getUsername())){
            throw new BadRequestException("Username already exists");
        }
        //create user/add user
        User user = new User(
                userRegistrationDTO.getUsername(),
                passwordEncoder.encode(userRegistrationDTO.getPassword())
        );

        //add role to the user
        Role defaultRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        user.addRole(defaultRole);

        return userRepository.save(user);
    }

}
