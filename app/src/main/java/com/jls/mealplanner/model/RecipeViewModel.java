package com.jls.mealplanner.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.database.recipes.RecipesRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private final RecipesRepository repository;
    private final LiveData<List<RecipeEntity>> allRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipesRepository();
        allRecipes = repository.getAllRecipes();
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
}
