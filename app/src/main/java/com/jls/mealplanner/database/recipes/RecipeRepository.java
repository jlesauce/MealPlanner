package com.jls.mealplanner.database.recipes;

import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

import java.util.List;

public class RecipeRepository {
    private final RecipeDao recipeDao;
    private final LiveData<List<RecipeEntity>> allRecipes;

    public RecipeRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<RecipeEntity>> getAllRecipes() {
        return allRecipes;
    }

    public void insert(RecipeEntity recipe) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.insertRecipe(recipe);
        });
    }

    public void update(RecipeEntity recipe) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.updateRecipe(recipe);
        });
    }
}
