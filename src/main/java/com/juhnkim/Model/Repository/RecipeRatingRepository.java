package com.juhnkim.Model.Repository;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.RecipeRating;
import com.juhnkim.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRatingRepository extends JpaRepository<RecipeRating, Long> {
    List<RecipeRating> findByRecipeRecipeId(Long recipeId);

    RecipeRating findByUserAndRecipe(User user, Recipe recipe);

}

