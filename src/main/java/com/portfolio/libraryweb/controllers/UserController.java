package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import com.portfolio.libraryweb.services.UserService;
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
    private UserService userService;

    @GetMapping("/sign_up")
    public String addNewUser() {
        return "sign-up";
    }


    @PostMapping("/sign_up")
    public ResponseEntity registerNewUser(@RequestBody User user) {
        if (userService.register(user) != null)
            return ResponseEntity.ok(user);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    //TODO: rewrite the logic of this method
    @PostMapping("/unique_username")
    public ResponseEntity isUnique(@RequestBody UsernameDto username) {
        if (userService.isUsernameFree(username.getUsername()))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.ok().build();
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
