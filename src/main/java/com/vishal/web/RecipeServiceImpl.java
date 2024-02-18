package com.vishal.web;

import com.vishal.web.backend.ChromaDbConnector;
import com.vishal.web.backend.LocalAILLMConnector;
import com.vishal.web.backend.MasterChefConnector;
import com.vishal.web.data.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final static Logger LOG = LoggerFactory.getLogger(RecipeServiceImpl.class);
    @Autowired
    ChromaDbConnector vectorDB;
    @Autowired
    LocalAILLMConnector llmConnector;
    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private long nextId = 1;

    @Autowired
    private MasterChefConnector masterChef;
    public Recipe searchRecipe(String freeText){

        Recipe searchedRec = new Recipe();
        String ragData = vectorDB.getData(freeText);
        //String details = llmConnector.inference(freeText,ragData);
        masterChef.getChefHelper().chat(freeText);
        searchedRec.setDescription(ragData);
        return searchedRec;
    }
    @Override
    public void addRecipe(Recipe recipe) {
        recipe.setId(nextId++);
        recipeMap.put(recipe.getId(), recipe);
    }

    public void addRecipe(String recipe) {
        LOG.info(recipe);
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

