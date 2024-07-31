package com.jls.mealplanner.database.ingredienticons;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "icons")
public class IngredientIconEntity {

    public IngredientIconEntity() {
        shortName = "";
        iconPath = "";
    }

    @PrimaryKey
    @ColumnInfo(name = "short_name")
    @NonNull
    public String shortName;

    @ColumnInfo(name = "icon_path")
    @NonNull
    public String iconPath;
}