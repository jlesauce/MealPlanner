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
import com.jls.mealplanner.model.RecipeViewModel;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private final Fragment topFragment;
    private final ArrayList<RecipeEntity> recipes;
    private final RecipeViewModel recipesViewModel;

    public RecipeAdapter(@NonNull Fragment topFragment, @NonNull Fragment fragment, final RecipeListType listType,
                         RecipeViewModel recipesViewModel) {
        this.topFragment = topFragment;
        this.recipes = new ArrayList<>();
        this.recipesViewModel = recipesViewModel;

        recipesViewModel.getAllRecipes().observe(fragment, allRecipes -> {
            if (allRecipes == null) {
                return;
            }
            recipes.clear();
            for (RecipeEntity recipe : allRecipes) {
                if (listType == RecipeListType.MY_RECIPES) {
                    // FIXME To be implemented
                    break;
                } else if (listType == RecipeListType.ALL_RECIPES) {
                    recipes.add(recipe);
                } else if (listType == RecipeListType.FAVORITE_RECIPES && recipe.isInFavorite) {
                    recipes.add(recipe);
                }
            }
            notifyDataSetChanged();
        });
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
            RecipeDetailsFragment fragment = new RecipeDetailsFragment(recipesViewModel, currentRecipe);
            topFragment.getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void initRecipeItem(@NonNull RecipeViewHolder holder, final RecipeEntity recipe) {
        holder.recipeName.setText(recipe.name);
        holder.recipeIcon.setImageResource(R.drawable.recipe_icon);
        holder.updateRecipeInFavoritesButton(recipe.isInFavorite);

        holder.addRecipeToFavorites.setOnClickListener(
                v -> recipeToFavoritesButtonClicked(holder, recipe));
    }

    private void recipeToFavoritesButtonClicked(RecipeViewHolder holder, final RecipeEntity recipe) {
        recipe.isInFavorite = !recipe.isInFavorite;
        holder.updateRecipeInFavoritesButton(recipe.isInFavorite);
        this.recipesViewModel.updateRecipe(recipe);
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