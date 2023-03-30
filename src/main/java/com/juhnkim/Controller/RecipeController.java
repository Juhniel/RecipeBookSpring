package com.juhnkim.Controller;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.RecipeRatingService;
import com.juhnkim.Model.RecipeService;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.TastyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRatingService recipeRatingService;
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipe/{id}")
    public String getRecipe(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe != null) {
            model.addAttribute("recipe", recipe);

            Double averageRating = recipeRatingService.getAverageRatingByRecipeId(id);
            model.addAttribute("averageRating", averageRating);

            return "../templates/html/recipe";
        } else {
            model.addAttribute("errorMessage", "Recipe not found");
            return "../templates/html/error";
        }
    }



    @GetMapping("/createAccount")
    public String getCreateAccountPage() {
//        return "../templates/html/createAccount";
        return "../templates/html/createAccount";
    }

    @GetMapping("/fetch-recipe")
    public void fetchRecipe() throws Exception {
        TastyApi tastyApi = new TastyApi(recipeRepository);
        tastyApi.fetchRecipes();
    }

    @GetMapping("/clear-database")
    public void clearRecipeTable() {
        recipeRepository.deleteAll();
    }

//    @GetMapping("/delete-duplicates")
//    public void deleteDuplicateRecipes() {
//        List<String> distinctRecipeNames = recipeRepository.findDistinctRecipeNames();
//        for (String recipeName : distinctRecipeNames) {
//            List<Recipe> recipesToDelete = recipeRepository.findByRecipeName(recipeName);
//            if (recipesToDelete.size() > 1) {
//                recipeRepository.deleteInBatch(recipesToDelete.subList(1, recipesToDelete.size()));
//            }
//        }
//    }
}




