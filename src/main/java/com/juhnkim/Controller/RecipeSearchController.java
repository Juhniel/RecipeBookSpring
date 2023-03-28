package com.juhnkim.Controller;

import com.juhnkim.Model.Entity.Recipe;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.Entity.User;
import com.juhnkim.Model.Service.UserFavouriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecipeSearchController extends BaseController{

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserFavouriteService userFavouriteService;

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("searchTerm") String searchTerm, Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        addLoggedInUser(model, session);
        if (loggedInUser != null) {
            List<Recipe> recipes = recipeRepository.findByRecipeNameContainingIgnoreCase(searchTerm);
            List<Long> favoriteRecipeIds = userFavouriteService.getFavouriteRecipeIds(loggedInUser.getUserId());

            model.addAttribute("recipes", recipes);
            model.addAttribute("favoriteRecipeIds", favoriteRecipeIds);
        } else {
            // Handle the case when the user is not logged in
            List<Recipe> recipes = recipeRepository.findByRecipeNameContainingIgnoreCase(searchTerm);
            model.addAttribute("recipes", recipes);

        }
        return "../templates/html/searchResults";
    }
}
