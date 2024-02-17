package com.vishal.web.data;

import java.util.List;

public class Recipe {
    private Long id;
    private String name;
    private String description;
    private List<String> ingredients;
    // Other fields as needed

    // Constructors, getters, and setters

    public Recipe() {
        // Default constructor
    }

    public Recipe(Long id, String name, String description, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    // Getters and setters
    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Ingredients
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
