package org.jls.mealplanner.ingredient;

import androidx.annotation.DrawableRes;

import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

public class CategoryItem {

    private IngredientCategory ingredientCategory;
    @DrawableRes
    private int imageResource;
    private String categoryDisplayName;

    public CategoryItem(IngredientCategory ingredientCategory,
                        @DrawableRes int imageResource,
                        String categoryDisplayName) {
        this.ingredientCategory = ingredientCategory;
        this.imageResource = imageResource;
        this.categoryDisplayName = categoryDisplayName;
    }

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }
}
