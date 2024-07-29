package com.jls.mealplanner.database;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientRepository {
    private final IngredientDao ingredientDao;
    private final LiveData<List<IngredientEntity>> allIngredients;

    public IngredientRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        ingredientDao = db.ingredientDao();
        allIngredients = ingredientDao.getAllIngredients();
    }

    public LiveData<List<IngredientEntity>> getAllIngredients() {
        return allIngredients;
    }

    public void insert(IngredientEntity ingredient) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            ingredientDao.insertIngredient(ingredient);
        });
    }
}
