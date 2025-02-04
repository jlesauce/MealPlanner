package com.jls.mealplanner.database.recipes;

import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

import java.util.List;

public class RecipesRepository {
    private final RecipesDao recipesDao;
    private final LiveData<List<RecipeEntity>> allRecipes;

    public RecipesRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        recipesDao = db.recipesDao();
        allRecipes = recipesDao.getAllRecipes();
    }

    public LiveData<List<RecipeEntity>> getAllRecipes() {
        return allRecipes;
    }

    public void insert(RecipeEntity recipe) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            recipesDao.insertRecipe(recipe);
        });
    }

    public void update(RecipeEntity recipe) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            recipesDao.updateRecipe(recipe);
        });
    }

    public RecipeEntity getRecipeById(int recipeId) {
        return recipesDao.getRecipeById(recipeId);
    }
}
