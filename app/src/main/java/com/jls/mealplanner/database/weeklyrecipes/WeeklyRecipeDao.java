package com.jls.mealplanner.database.weeklyrecipes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeeklyRecipeDao {

    @Insert
    void insert(WeeklyRecipeEntity weeklyRecipe);

    @Query("SELECT * FROM weekly_recipes WHERE week_number = :weekNumber AND day_of_week = :dayOfWeek AND year = :year")
    LiveData<List<WeeklyRecipeEntity>> getRecipesForDay(int year, int weekNumber, int dayOfWeek);
}