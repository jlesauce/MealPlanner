package com.jls.mealplanner.ui.recipes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private final RecipeListType recipeListType;
    private final ArrayList<RecipeEntity> recipes;

    public RecipeAdapter(Fragment fragment, final RecipeListType recipeListType) {
        this.recipeListType = recipeListType;
        this.recipes = new ArrayList<>();

        // FIXME To be removed
        this.recipes.add(new RecipeEntity("Recipe 1", false, ""));
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        RecipeEntity currentRecipe = recipes.get(position);
        initRecipeItem(holder, currentRecipe);

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Recipe clicked: " + currentRecipe.name);
        });

    }

    private void initRecipeItem(@NonNull RecipeViewHolder holder, final RecipeEntity recipe) {
        holder.recipeName.setText(recipe.name);
        holder.recipeIcon.setImageResource(R.drawable.recipe_icon);
        holder.updateRecipeInFavoritesButton(recipe.isInFavorites);

        // Add listeners
        holder.addRecipeToFavorites.setOnClickListener(
                v -> recipeToFavoritesButtonClicked(holder, recipe));
    }

    private void recipeToFavoritesButtonClicked(RecipeViewHolder holder, final RecipeEntity recipe) {
        recipe.isInFavorites = !recipe.isInFavorites;
        holder.updateRecipeInFavoritesButton(recipe.isInFavorites);
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeIcon;
        TextView recipeName;
        ImageButton addRecipeToFavorites;

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
}