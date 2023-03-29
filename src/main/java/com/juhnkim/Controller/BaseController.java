package com.juhnkim.Controller;
import com.juhnkim.Model.Entity.User;
import org.springframework.ui.Model;

import com.juhnkim.Model.Utils.ThymeleafUtil;

import jakarta.servlet.http.HttpSession;
public abstract class BaseController {

    protected void addLoggedInUser(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            model.addAttribute("loggedInUser", loggedInUser);
        }
        model.addAttribute("thymeleafUtil", new ThymeleafUtil());
    }
}