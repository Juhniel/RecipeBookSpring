package com.juhnkim.Model.Service;

import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.Entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired RecipeRepository recipeRepository;

//    public Recipe getRecipeById(Long recipeId) {
//        return recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
//    }

    public Recipe getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }


    public void saveRecipe(Recipe recipe) {
        if (recipeRepository.findByRecipeName(recipe.getRecipeName()).isEmpty()) {
            recipeRepository.save(recipe);
        }
    }



}
