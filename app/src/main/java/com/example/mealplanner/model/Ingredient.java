package com.example.mealplanner.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class Ingredient {

    private final String name;
    @DrawableRes
    private final int imageResource;
    private boolean isPossessed;
    private boolean isInGroceryList;

    public Ingredient(@NonNull String name, @DrawableRes int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
        this.isPossessed = false;
        this.isInGroceryList = false;
    }

    public Ingredient(@NonNull String name, @DrawableRes int imageResource, boolean isPossessed,
                      boolean isInGroceryList) {
        this(name, imageResource);
        this.isPossessed = isPossessed;
        this.isInGroceryList = isInGroceryList;
    }

    public String getIngredientName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isPossessed() {
        return isPossessed;
    }

    public void setPossessed(boolean possessed) {
        isPossessed = possessed;
    }

    public boolean isInGroceryList() {
        return isInGroceryList;
    }

    public void setInGroceryList(boolean inGroceryList) {
        isInGroceryList = inGroceryList;
    }
}
