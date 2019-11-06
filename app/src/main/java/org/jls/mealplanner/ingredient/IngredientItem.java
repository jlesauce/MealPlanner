package org.jls.mealplanner.ingredient;

import androidx.annotation.DrawableRes;

public class IngredientItem {

    @DrawableRes
    private int imageResource;
    private String ingredientName;

    public IngredientItem(@DrawableRes int imageResource, String ingredientName) {
        this.imageResource = imageResource;
        this.ingredientName = ingredientName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
