package com.jls.mealplanner.database.ingredients;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientsDao {
    @Query("SELECT * FROM ingredients")
    LiveData<List<IngredientEntity>> getAllIngredients();

    @Update
    void updateIngredient(IngredientEntity ingredient);

    @Insert
    void insertIngredient(IngredientEntity ingredient);

    @Query("SELECT * FROM ingredients WHERE name LIKE :ingredientName LIMIT 1")
    IngredientEntity findByName(final String ingredientName);
}
