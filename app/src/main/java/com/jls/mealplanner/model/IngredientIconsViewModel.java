package com.jls.mealplanner.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredienticons.IngredientIconRepository;

import java.util.List;

public class IngredientIconsViewModel extends AndroidViewModel {

    private final IngredientIconRepository repository;
    private final LiveData<List<IngredientIconEntity>> allIngredientIcons;

    public IngredientIconsViewModel(@NonNull Application application) {
        super(application);
        repository = new IngredientIconRepository();
        allIngredientIcons = repository.getAllIngredientIcons();
    }

    public LiveData<List<IngredientIconEntity>> getAllIngredientIcons() {
        return allIngredientIcons;
    }

    public void updateIngredientIcon(IngredientIconEntity ingredientIcon) {
        repository.update(ingredientIcon);
    }

    public void insert(IngredientIconEntity ingredientIcon) {
        repository.insert(ingredientIcon);
    }
}