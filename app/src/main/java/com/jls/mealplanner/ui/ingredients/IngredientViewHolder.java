package com.jls.mealplanner.ui.ingredients;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public ImageView ingredientIcon;
    public TextView ingredientName;
    public ImageButton addIngredientToGroceryList;
    public CheckBox addToMyIngredientsCheckBox;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientIcon = itemView.findViewById(R.id.ingredientIcon);
        ingredientName = itemView.findViewById(R.id.ingredientName);
        addIngredientToGroceryList = itemView.findViewById(R.id.addIngredientToGroceryList);
        addToMyIngredientsCheckBox = itemView.findViewById(R.id.addToMyIngredientsCheckBox);
    }

    public void updateIngredientInGroceryListButton(boolean isInGroceryList) {
        addIngredientToGroceryList.setImageResource(
                isInGroceryList ? R.drawable.grocery_list_icon_enabled : R.drawable.grocery_list_icon_disabled);
    }
}