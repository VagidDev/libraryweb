package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/sign_up")
    public String addNewUser() {
        return "sign-up";
    }


    @PostMapping("/sign_up")
    public ResponseEntity registerNewUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/unique_username")
    public ResponseEntity isUnique(@RequestBody UsernameDto username) {
        Optional<User> optionalUser = userRepository.findByUsername(username.getUsername());
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/account/")
    public String getAccount() {
        return "account";
    }

    @GetMapping("/account/edit")
    public String getEditAccount() {
        return "account-edit";
    }
}

//Возможно, должен быть вариант по лучше данного
class UsernameDto {
    private String username;

    UsernameDto() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
