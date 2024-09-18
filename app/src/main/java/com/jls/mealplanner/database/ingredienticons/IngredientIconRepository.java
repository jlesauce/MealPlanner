package com.jls.mealplanner.database.ingredienticons;

import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

import java.util.List;

public class IngredientIconRepository {
    private final IngredientIconsDao ingredientIconsDao;
    private final LiveData<List<IngredientIconEntity>> allIngredientIcons;

    public IngredientIconRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        ingredientIconsDao = db.ingredientsIconDao();
        allIngredientIcons = ingredientIconsDao.getAllIngredientIcons();
    }

    public LiveData<List<IngredientIconEntity>> getAllIngredientIcons() {
        return allIngredientIcons;
    }

    public void insert(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconsDao.insertIngredientIcon(ingredientIcon));
    }

    public void update(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconsDao.updateIngredientIcon(ingredientIcon));
    }

    public void delete(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconsDao.deleteIngredientIcon(ingredientIcon));
    }
}