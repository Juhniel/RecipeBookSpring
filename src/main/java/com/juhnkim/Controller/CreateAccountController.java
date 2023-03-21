package com.juhnkim.Controller;

import com.juhnkim.Model.User;
import com.juhnkim.Model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CreateAccountController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-account")
    public String createAccount(
            @RequestParam("username") String username,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword, Model model
            ){

        if(password.equals(confirmPassword)) {
            User user = new User(username, firstName, lastName, password, email);
            userService.hashPassword(user);
            model.addAttribute("accountSuccessfullyCreated", "You have succesfully created an account.");
            return "../templates/html/createAccount";
        }else {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "../templates/html/createAccount";
        }

    }
}
