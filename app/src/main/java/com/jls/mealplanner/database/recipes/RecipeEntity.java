package com.jls.mealplanner.database.recipes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "is_in_favorite")
    public boolean isInFavorite;

    @ColumnInfo(name = "icon_id")
    public String iconId;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "steps_as_json")
    public String stepsAsJson;

    @ColumnInfo(name = "ingredients_as_json")
    public String ingredientsAsJson;
}