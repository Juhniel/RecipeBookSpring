package com.juhnkim.Controller;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.User;
import com.juhnkim.Model.UserFavouriteRecipes;
import com.juhnkim.Model.UserFavouriteService;
import com.juhnkim.Model.RecipeRatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RecipeSearchController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserFavouriteService userFavouriteService;

    @Autowired
    private RecipeRatingService recipeRatingService;

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("searchTerm") String searchTerm, Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Recipe> recipes = recipeRepository.findByRecipeNameContainingIgnoreCase(searchTerm);

        // Fetch the average ratings for all the recipes
        Map<Long, Double> averageRatings = new HashMap<>();
        for (Recipe recipe : recipes) {
            double averageRating = recipeRatingService.getAverageRating(recipe.getRecipeId());
            averageRatings.put(recipe.getRecipeId(), averageRating);
        }

        if (loggedInUser != null) {
            List<Long> favoriteRecipeIds = userFavouriteService.getFavouriteRecipeIds(loggedInUser.getUserId());
            model.addAttribute("favoriteRecipeIds", favoriteRecipeIds);
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("averageRatings", averageRatings);

        return "../templates/html/searchResults";
    }
}
