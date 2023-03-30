package com.juhnkim.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Recipe {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long recipeId;
    private String recipeName;

    private String recipeInstructions;
    private String recipeIngredients;

    private String recipeImage;


    @ManyToMany(mappedBy = "favouriteRecipe")
    private List<User> user = new ArrayList<>();

    public Recipe() {

    }

    public Recipe(Long recipeId) {
        this.recipeId = recipeId;
    }


    public Recipe(String recipeName, String recipeInstructions, String recipeIngredients, String recipeImage) {
        this.recipeName = recipeName;
        this.recipeInstructions = recipeInstructions;
        this.recipeIngredients = recipeIngredients;
        this.recipeImage = recipeImage;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

}

