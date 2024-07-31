package com.jls.mealplanner.database.ingredients;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class IngredientEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "isPossessed")
    public boolean isPossessed;

    @ColumnInfo(name = "isInGroceryList")
    public boolean isInGroceryList;
}
