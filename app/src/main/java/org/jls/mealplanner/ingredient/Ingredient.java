package org.jls.mealplanner.ingredient;

import androidx.annotation.DrawableRes;

public class Ingredient {

    private String name;
    @DrawableRes
    private int imageResource;

    public Ingredient(String name, @DrawableRes int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getIngredientName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
