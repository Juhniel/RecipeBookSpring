package com.juhnkim.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhnkim.Model.Repository.RecipeRepository;
import com.juhnkim.Model.Entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TastyApi {
    private static final String API_URL = "https://tasty.p.rapidapi.com";
    private static final String API_KEY = "4ffadcac92msh7ec8ded0a27768bp1e6430jsn6ad083d3242a";
    @Autowired
    private RecipeRepository recipeRepository;

    public TastyApi(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void fetchRecipes() throws Exception {
        int batchSize = 40;

        for (int i = 0; i < 241; i++) {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/recipes/list?from=" + (i * batchSize) + "&size=" + batchSize))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode resultsNode = rootNode.path("results");
                for (JsonNode resultNode : resultsNode) {
                    String name = resultNode.path("name").asText();
                    List<Recipe> existingRecipes = recipeRepository.findByRecipeName(name);

                    if (!existingRecipes.isEmpty()) {
                        String image = resultNode.path("thumbnail_url").asText();
                        JsonNode recipeNode = resultNode.path("recipes").get(0);

                        String videoUrl;
                        int numServings;

                        if (recipeNode != null) {
                            videoUrl = recipeNode.path("original_video_url").asText(null);
                            numServings = recipeNode.path("num_servings").asInt(-1);
                        } else {
                            videoUrl = resultNode.path("original_video_url").asText(null);
                            numServings = resultNode.path("num_servings").asInt(-1);
                        }

                        // Update the existing recipe with new numServings and videoUrl
                        for (Recipe existingRecipe : existingRecipes) {
                            if (numServings != -1) {
                                existingRecipe.setRecipeServings(numServings);
                            } else {
                                System.out.println("num_servings is missing for recipe: " + name);
                            }

                            if (videoUrl != null) {
                                existingRecipe.setRecipeVideo(videoUrl);
                            } else {
                                System.out.println("original_video_url is missing for recipe: " + name);
                            }

                            recipeRepository.save(existingRecipe);
                        }
                    }
                }

                Thread.sleep(200);
            }
        }
    }
}