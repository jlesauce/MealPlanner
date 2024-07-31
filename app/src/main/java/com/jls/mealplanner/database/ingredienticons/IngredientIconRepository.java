package com.jls.mealplanner.database.ingredienticons;

import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

import java.util.List;

public class IngredientIconRepository {
    private final IngredientIconDao ingredientIconDao;
    private final LiveData<List<IngredientIconEntity>> allIngredientIcons;

    public IngredientIconRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        ingredientIconDao = db.ingredientIconDao();
        allIngredientIcons = ingredientIconDao.getAllIngredientIcons();
    }

    public LiveData<List<IngredientIconEntity>> getAllIngredientIcons() {
        return allIngredientIcons;
    }

    public void insert(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconDao.insertIngredientIcon(ingredientIcon));
    }

    public void update(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconDao.updateIngredientIcon(ingredientIcon));
    }

    public void delete(IngredientIconEntity ingredientIcon) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> ingredientIconDao.deleteIngredientIcon(ingredientIcon));
    }
}