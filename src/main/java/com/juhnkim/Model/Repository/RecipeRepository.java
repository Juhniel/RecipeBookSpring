package com.juhnkim.Model.Repository;

import com.juhnkim.Model.Entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByRecipeName(String searchTerm);
    List<Recipe> findByRecipeNameContainingIgnoreCase(String searchTerm);
}


