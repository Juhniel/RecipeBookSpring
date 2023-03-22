package com.juhnkim.Controller;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.Repository.UserRepository;
import com.juhnkim.Model.User;
import com.juhnkim.Model.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model,
                            HttpSession session) {
        // call authenticateUser method of UserService

        // check if authentication is successful
        if (userService.authenticateUser(username, password)) {
            // redirect to a certain page upon successful authentication
            User user = userRepository.findByUsername(username).get();
            // create a session for the logged-in user
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        } else {
            // redirect to the login page with an error message upon failed authentication
            model.addAttribute("errorUserLogin", "Check your username and password"); //Returna till ett error meddelande med model attribute
            return "redirect:/";
        }
    }

    @PostMapping("/logout")
    public String userLogOut(HttpSession session){
        session.getAttribute("loggedInUser");
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/checkSession")
    public ResponseEntity<String> mySession(HttpSession session) {
        if (session != null && session.getAttribute("loggedInUser") != null) {
            // If the session exists and has a "loggedInUser" attribute, return a response with the session ID
            return ResponseEntity.ok().body("{\"sessionId\": \"" + session.getId() + "\"}");
        } else {
            // If the session does not exist or does not have a "loggedInUser" attribute, return a response with no session ID
            return ResponseEntity.ok().body("{}");
        }
    }
}

