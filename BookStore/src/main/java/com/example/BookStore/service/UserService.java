package com.example.BookStore.service;

import com.example.BookStore.entity.User;
import com.example.BookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User userLogin(String username, String password) {
        User user = userRepository.findByUserName(username);
        if(user != null && user.getPassword().equals(password)){
            return user;
        }
       return null;
    }
}
