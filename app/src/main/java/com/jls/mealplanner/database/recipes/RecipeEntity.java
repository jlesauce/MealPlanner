package com.jls.mealplanner.database.recipes;

public class RecipeEntity {
    public String name;
    public boolean isInFavorites;
    public String iconId;

    public RecipeEntity(String name, boolean isInFavorites, String iconId) {
        this.name = name;
        this.isInFavorites = isInFavorites;
        this.iconId = iconId;
    }
}
