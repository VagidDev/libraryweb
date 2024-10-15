package com.portfolio.libraryweb.services;

import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    public User register(@NotNull User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isUsernameFree(@NotNull String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.isEmpty();
    }

    public void editProfile(String name, String surname, String email) {
        User user = getCurrentUser();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        userRepository.save(user);
    }

    public void editProfile(String name, String surname, String email, MultipartFile photo) {
        User user = getCurrentUser();
        try {
            String[] splitFileName = Objects.requireNonNull(photo.getOriginalFilename()).split("\\.");
            String format = splitFileName[splitFileName.length - 1];
            Path saveDir = Path.of("images/" + user.getUsername() + "." + format);
            boolean isExists = Files.exists(saveDir);
            if (isExists)
                Files.delete(saveDir);
            Files.copy(photo.getInputStream(), saveDir);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setImage(saveDir.toString());
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("We are in shit!");
            throw new RuntimeException(e);
        }
    }

}
