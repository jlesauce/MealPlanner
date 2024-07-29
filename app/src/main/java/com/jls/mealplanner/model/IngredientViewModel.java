package com.jls.mealplanner.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.IngredientRepository;
import com.jls.mealplanner.database.IngredientEntity;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private final IngredientRepository repository;
    private final LiveData<List<IngredientEntity>> allIngredients;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        repository = new IngredientRepository();
        allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<IngredientEntity>> getAllIngredients() {
        return allIngredients;
    }

    public void insert(IngredientEntity ingredient) {
        repository.insert(ingredient);
    }
}
