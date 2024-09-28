package com.jls.mealplanner.ui.recipes;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public ImageView recipeIcon;
    public TextView recipeName;
    public ImageButton addRecipeToFavorites;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeIcon = itemView.findViewById(R.id.recipeIcon);
        recipeName = itemView.findViewById(R.id.recipeName);
        addRecipeToFavorites = itemView.findViewById(R.id.addRecipeToFavorites);
    }

    public void updateRecipeInFavoritesButton(boolean isInFavorites) {
        addRecipeToFavorites.setImageResource(
                isInFavorites ? R.drawable.favorite_icon_enabled : R.drawable.favorite_icon_disabled);
    }
}