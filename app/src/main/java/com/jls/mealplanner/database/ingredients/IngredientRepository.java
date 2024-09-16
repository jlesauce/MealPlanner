package com.jls.mealplanner.database.ingredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

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

    public void update(IngredientEntity ingredient) {
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            ingredientDao.updateIngredient(ingredient);
        });
    }

    public MutableLiveData<IngredientEntity> findByName(String ingredientName) {
        MutableLiveData<IngredientEntity> ingredientLiveData = new MutableLiveData<>();
        ApplicationDatabase.databaseWriteExecutor.execute(() -> {
            IngredientEntity ingredient = ingredientDao.findByName(ingredientName);
            ingredientLiveData.postValue(ingredient);
        });
        return ingredientLiveData;
    }
}
