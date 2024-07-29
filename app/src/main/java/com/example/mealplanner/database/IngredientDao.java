package com.example.mealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredients")
    LiveData<List<IngredientEntity>> getAllIngredients();

    @Insert
    void insertIngredient(IngredientEntity ingredient);

    @Query("SELECT * FROM ingredients WHERE id IN (:ingredientsIds)")
    LiveData<List<IngredientEntity>> loadAllByIds(int[] ingredientsIds);

    @Query("SELECT * FROM ingredients WHERE name LIKE :ingredientName LIMIT 1")
    IngredientEntity findByName(final String ingredientName);

    @Insert
    void insertAll(IngredientEntity... ingredients);

    @Delete
    void delete(IngredientEntity ingredient);
}
