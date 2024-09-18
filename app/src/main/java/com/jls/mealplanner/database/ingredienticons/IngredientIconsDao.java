package com.jls.mealplanner.database.ingredienticons;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientIconsDao {

    @Insert
    void insertIngredientIcon(IngredientIconEntity ingredientIcon);

    @Update
    void updateIngredientIcon(IngredientIconEntity ingredientIcon);

    @Delete
    void deleteIngredientIcon(IngredientIconEntity ingredientIcon);

    @Query("SELECT * FROM icons")
    LiveData<List<IngredientIconEntity>> getAllIngredientIcons();
}