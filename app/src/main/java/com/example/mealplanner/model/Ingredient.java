package com.example.mealplanner.model;

import androidx.annotation.DrawableRes;

public class Ingredient {

    private final String name;
    @DrawableRes
    private final int imageResource;

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
