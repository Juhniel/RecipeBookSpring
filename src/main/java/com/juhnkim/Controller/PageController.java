package com.juhnkim.Controller;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.User;
import com.juhnkim.Model.UserFavouriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserFavouriteService userFavouriteService;

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

        if (session.getAttribute("loggedInUser") != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            favoriteRecipeIds = userFavouriteService.getFavouriteRecipeIds(loggedInUser.getUserId());
        }

        model.addAttribute("favoriteRecipeIds", favoriteRecipeIds);
        return "../templates/index";
    }

    @GetMapping("/articles")
    public String articles() {
        return "../templates/html/articles";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "../templates/html/about";
    }

    @GetMapping("/contact")
    public String contactUs() {
        return "../templates/html/contact";
    }
}