package com.jls.mealplanner.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.database.recipes.RecipesRepository;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private final RecipesRepository repository;
    private final LiveData<List<RecipeEntity>> allRecipes;

    public RecipeViewModel(RecipesRepository repository) {
        this.repository = repository;
        this.allRecipes = repository.getAllRecipes();
    }

    public LiveData<List<RecipeEntity>> getAllRecipes() {
        return allRecipes;
    }

    public void updateRecipe(RecipeEntity recipe) {
        repository.update(recipe);
    }

    public void insert(RecipeEntity recipe) {
        repository.insert(recipe);
    }

    public LiveData<RecipeEntity> getRecipeById(int recipeId) {
        return repository.getRecipeById(recipeId);
    }
}
