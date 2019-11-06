package org.jls.mealplanner.ui.ingredients;

import androidx.annotation.DrawableRes;

public class CategoryItem {

    @DrawableRes
    private int imageResource;
    private String categoryName;

    public CategoryItem(@DrawableRes int imageResource, String categoryName) {
        this.imageResource = imageResource;
        this.categoryName = categoryName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
