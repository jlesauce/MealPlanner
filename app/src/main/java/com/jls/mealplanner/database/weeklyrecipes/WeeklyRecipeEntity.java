package com.jls.mealplanner.database.weeklyrecipes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weekly_recipes")
public class WeeklyRecipeEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int week_number;

    public int day_of_week;

    public int recipe_id;
}