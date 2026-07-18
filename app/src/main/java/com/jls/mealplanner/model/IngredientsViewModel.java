package com.jls.mealplanner.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.database.ingredients.IngredientRepository;

import java.util.List;

public class IngredientsViewModel extends ViewModel {

    private final IngredientRepository repository;
    private final LiveData<List<IngredientEntity>> allIngredients;

    public IngredientsViewModel(IngredientRepository repository) {
        this.repository = repository;
        this.allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<IngredientEntity>> getAllIngredients() {
        return allIngredients;
    }

    public void updateIngredient(IngredientEntity ingredient) {
        repository.update(ingredient);
    }

    public void insert(IngredientEntity ingredient) {
        repository.insert(ingredient);
    }

    public MutableLiveData<IngredientEntity> searchIngredient(String ingredientName) {
        return repository.findByName(ingredientName);
    }
}
