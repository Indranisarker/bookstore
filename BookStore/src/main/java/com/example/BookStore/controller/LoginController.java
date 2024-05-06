package com.example.BookStore.controller;

import com.example.BookStore.domain.UserDTO;
import com.example.BookStore.entity.User;
import com.example.BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String showLoginForm(UserDTO user, Model model){
        model.addAttribute("user", user);
            return "login";
    }
    @PostMapping("/userLogin")
    public String login(Model model, @ModelAttribute("user") UserDTO userDTO){
        User user =  userService.userLogin(userDTO.getUserName(), userDTO.getPassword());
      if(user != null) {
          model.addAttribute("username",userDTO.getUserName());
          model.addAttribute("message", "Successfully Logged In");
          return "userDetails";
      }
      else{
                model.addAttribute("message", "Invalid username or password! Please try again.");
                return "login";
        }
    }
}
