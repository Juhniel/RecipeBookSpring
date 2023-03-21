package com.juhnkim.Model;

import com.juhnkim.Model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        }
        // Check if the provided password matches the stored password
        return BCrypt.checkpw(password, user.get().getUserPassword());
    }

    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

}


