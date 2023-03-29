package com.juhnkim.Controller;

import com.juhnkim.Model.Entity.Recipe;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.Entity.User;
import com.juhnkim.Model.Service.UserFavouriteService;
import com.juhnkim.Model.Service.UserService;
import com.juhnkim.Model.Utils.ThymeleafUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PageController extends BaseController{

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserFavouriteService userFavouriteService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(org.springframework.ui.Model model, HttpSession session) {
        // popular recipes in index
        List<Long> popularRecipeIds = Arrays.asList(484L, 1559L, 4036L, 4288L);
        List<Recipe> popularRecipes = (List<Recipe>) recipeRepository.findAllById(popularRecipeIds);
        model.addAttribute("popularRecipes", popularRecipes);

        // latest recipes in index
        List<Long> latestRecipeIds = Arrays.asList(6977L, 6978L, 6979L, 6980L);
        List<Recipe> latestRecipes = (List<Recipe>) recipeRepository.findAllById(latestRecipeIds);
        model.addAttribute("latestRecipes", latestRecipes);

        // Initialize an empty list of favoriteRecipeIds
        List<Long> favoriteRecipeIds = new ArrayList<>();

        // Add new ThymeleafUtil instance to the model
        model.addAttribute("thymeleafUtil", new ThymeleafUtil());
        if (session.getAttribute("loggedInUser") != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            model.addAttribute("loggedInUser", loggedInUser);
            favoriteRecipeIds = userFavouriteService.getFavouriteRecipeIds(loggedInUser.getUserId());
        }

        model.addAttribute("favoriteRecipeIds", favoriteRecipeIds);
        return "../templates/index";
    }

    @GetMapping("/articles")
    public String articles(Model model, HttpSession session) {
        addLoggedInUser(model, session);
        return "../templates/html/articles";
    }

    @GetMapping("/about")
    public String aboutUs(Model model, HttpSession session) {
        addLoggedInUser(model, session);
        return "../templates/html/about";
    }

    @GetMapping("/contact")
    public String contactUs(Model model, HttpSession session) {
        addLoggedInUser(model, session);
        return "../templates/html/contact";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        addLoggedInUser(model, session);
        return "../templates/html/settings";
    }

    @PostMapping("/settings/update")
    public String settingsUpdate(@RequestParam("name") String name,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirmPassword,
                                 HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Update username
        if (name != null && !name.trim().isEmpty()) {
            loggedInUser.setUsername(name.trim());
        }

        // Update password
        if (password != null && !password.trim().isEmpty()) {
            if (password.equals(confirmPassword)) {
                // You might want to hash the password before saving it
                loggedInUser.setUserPassword(passwordEncoder.encode(password));
            } else {
                model.addAttribute("error", "Passwords do not match.");
                return "../templates/html/settings";
            }
        }

        userService.saveUser(loggedInUser);
        return "redirect:/settings";
    }
}