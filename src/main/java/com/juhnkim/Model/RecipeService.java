package com.juhnkim.Model;

import com.juhnkim.Model.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired RecipeRepository recipeRepository;

    public void saveRecipe(Recipe recipe) {
        if (recipeRepository.findByRecipeName(recipe.getRecipeName()).isEmpty()) {
            recipeRepository.save(recipe);
        }
    }


}
