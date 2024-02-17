package com.vishal.web;

import com.vishal.web.data.Recipe;

import java.util.List;

public interface RecipeService {
    void addRecipe(Recipe recipe);
    void addRecipe(String recipe);
    void modifyRecipe(Recipe recipe);
    Recipe getRecipe(Long id);
    List<Recipe> getAllRecipes();
    boolean exists(Long id);
    void deleteRecipe(Long id);
    Recipe searchRecipe(String freeText);
}
