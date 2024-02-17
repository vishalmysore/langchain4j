package com.vishal.web;

import com.vishal.web.backend.ChromaDbConnector;
import com.vishal.web.data.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    ChromaDbConnector vectorDB;
    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private long nextId = 1;

    public Recipe searchRecipe(String freeText){

        Recipe searchedRec = new Recipe();
        searchedRec.setDescription(vectorDB.getData(freeText));
        return searchedRec;
    }
    @Override
    public void addRecipe(Recipe recipe) {
        recipe.setId(nextId++);
        recipeMap.put(recipe.getId(), recipe);
    }

    public void addRecipe(String recipe) {
        vectorDB.addData(recipe);
    }

    @Override
    public void modifyRecipe(Recipe recipe) {
        recipeMap.put(recipe.getId(), recipe);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeMap.get(id);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipeMap.values());
    }

    @Override
    public boolean exists(Long id) {
        return recipeMap.containsKey(id);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeMap.remove(id);
    }
}

