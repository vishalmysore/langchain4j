package com.vishal.web;

import com.vishal.web.data.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class CookGPTController {

    @Autowired
    private RecipeService recipeService;

    // Add a new recipe
    @PostMapping("/addRecipe")
    public ResponseEntity<Void> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addRecipeText")
    public ResponseEntity<Void> addRecipeText(@RequestBody String recipeTxt) {
        recipeService.addRecipe(recipeTxt);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/searchRecipe")
    public ResponseEntity<Recipe> addRecipe(@RequestBody String freeText) {
        Recipe  rec = recipeService.searchRecipe(freeText);
        return new ResponseEntity<>(rec,HttpStatus.CREATED);
    }


    // Modify an existing recipe
    @PutMapping("/modifyRecipe/{id}")
    public ResponseEntity<Void> modifyRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        if (!recipeService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recipe.setId(id);
        recipeService.modifyRecipe(recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get a recipe by ID
    @GetMapping("/getRecipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping("/getAllRecipe")
    public ResponseEntity<Recipe> getRecipes( ){
        Recipe recipe = new Recipe();
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }
    // Other endpoints as needed (e.g., get all recipes, delete recipe, etc.)
}
