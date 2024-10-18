package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    public String getAccount(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("image", userService.getBase64ImageString());
        return "account";
    }

    @GetMapping("/account/edit")
    public String getEditAccount(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("image", userService.getBase64ImageString());
        return "account-edit";
    }

    @PatchMapping(value = "/account/edit", consumes = "multipart/form-data")
    public ResponseEntity updateAccount(@RequestParam String name, @RequestParam String surname,
                                        @RequestParam String email, @Nullable @RequestParam MultipartFile profilePhoto) {
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty())
            return ResponseEntity.badRequest().body(null);
        if (profilePhoto == null)
            userService.editProfile(name, surname, email);
        else
            userService.editProfile(name, surname, email, profilePhoto);
        return ResponseEntity.ok().build();
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

/*class UserDto {
    private String name;
    private String surname;
    private String email;
    private MultipartFile profilePhoto;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}*/
