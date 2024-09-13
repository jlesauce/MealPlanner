package com.jls.mealplanner.database.recipes;

import java.util.List;

public class RecipeEntity {
    public String name;
    public boolean isInFavorites;
    public String iconId;
    public String description;
    public List<String> steps;
    public List<String> ingredients;

    public RecipeEntity(String name, boolean isInFavorites, String iconId, String description, List<String> steps,
                        List<String> ingredients) {
        this.name = name;
        this.isInFavorites = isInFavorites;
        this.iconId = iconId;
        this.description = description;
        this.steps = steps;
        this.ingredients = ingredients;
    }
}