package com.juhnkim.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhnkim.Model.Repository.RecipeRepository;
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
    private static final String API_KEY = "44dfda61dfmsh97a0065bd84d37dp119920jsnb157a8a9906b";
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
                    .uri(URI.create(API_URL + "/recipes/list?from=" + (i*batchSize) + "&size=" + batchSize))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode resultsNode = rootNode.path("results");

                List<Recipe> recipes = new ArrayList<>();
                Set<String> existingNames = new HashSet<>();

                for (JsonNode recipeNode : resultsNode) {
                    String name = recipeNode.path("name").asText();
                    List<Recipe> existingRecipes = recipeRepository.findByRecipeName(name);

                    if(existingNames.contains(name)) {
                        continue;
                    }

                    if (!existingRecipes.isEmpty()) {
                        existingNames.add(name);
                        continue; // skip this recipe if it already exists
                    }

                    String image = recipeNode.path("thumbnail_url").asText();
                    List<String> instructionsList = new ArrayList<>();
                    List<String> ingredients = new ArrayList<>();
                    if (recipeNode.has("recipes")) {
                        continue;
                    } else {
                        // Retrieve instructions and ingredients from top-level recipe node
                        JsonNode instructionsNode = recipeNode.path("instructions");
                        for (JsonNode instructionNode : instructionsNode) {
                            String instructions = instructionNode.path("display_text").asText();
                            instructionsList.add(instructions);
                        }
                        JsonNode sectionsNode = recipeNode.path("sections");
                        for (JsonNode sectionNode : sectionsNode) {
                            JsonNode componentsNode = sectionNode.path("components");
                            for (JsonNode componentNode : componentsNode) {
                                String ingredient = componentNode.path("raw_text").asText();
                                ingredients.add(ingredient);
                            }
                        }
                    }
                    recipes.add(new Recipe(name, instructionsList.toString(), ingredients.toString(), image));
                }

                for (Recipe recipe : recipes) {
                    recipeRepository.save(recipe);
                }
            }
            Thread.sleep(200);
        }
    }
}