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

    @ColumnInfo(name = "is_possessed")
    public boolean isPossessed;

    @ColumnInfo(name = "is_in_grocery_list")
    public boolean isInGroceryList;

    @ColumnInfo(name = "icon_id")
    public String iconId;
}
