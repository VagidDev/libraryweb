package com.portfolio.libraryweb.services;

import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(com.portfolio.libraryweb.config.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
    }
}
