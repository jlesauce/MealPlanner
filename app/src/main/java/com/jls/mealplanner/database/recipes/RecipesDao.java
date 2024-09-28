package com.jls.mealplanner.database.recipes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipesDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntity>> getAllRecipes();

    @Update
    void updateRecipe(RecipeEntity recipe);

    @Insert
    void insertRecipe(RecipeEntity recipe);
}
