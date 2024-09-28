package com.jls.mealplanner.database.recipes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntity {

    public RecipeEntity() {
        name = "no_name";
        isInFavorite = false;
        iconId = "default";
        description = "Recipe description";
        stepsAsJson = "[]";
        ingredientsAsJson = "[]";
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    @ColumnInfo(name = "is_in_favorite")
    public boolean isInFavorite;

    @ColumnInfo(name = "icon_id")
    public String iconId;

    @ColumnInfo(name = "description")
    @NonNull
    public String description;

    @ColumnInfo(name = "steps_as_json")
    @NonNull
    public String stepsAsJson;

    @ColumnInfo(name = "ingredients_as_json")
    @NonNull
    public String ingredientsAsJson;
}