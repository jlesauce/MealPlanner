package com.jls.mealplanner.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredienticons.IngredientIconRepository;

import java.util.List;

public class IngredientIconsViewModel extends ViewModel {

    private final IngredientIconRepository repository;
    private final LiveData<List<IngredientIconEntity>> allIngredientIcons;

    public IngredientIconsViewModel(IngredientIconRepository repository) {
        this.repository = repository;
        this.allIngredientIcons = repository.getAllIngredientIcons();
    }

    public LiveData<List<IngredientIconEntity>> getAllIngredientIcons() {
        return allIngredientIcons;
    }

    public void insert(IngredientIconEntity ingredientIcon) {
        repository.insert(ingredientIcon);
    }
}