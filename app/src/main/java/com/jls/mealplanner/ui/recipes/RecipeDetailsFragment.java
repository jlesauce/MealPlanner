package com.jls.mealplanner.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.model.RecipeViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private final RecipeViewModel recipesViewModel;
    private final RecipeEntity recipe;

    public RecipeDetailsFragment(RecipeViewModel recipesViewModel, RecipeEntity recipe) {
        super();
        this.recipesViewModel = recipesViewModel;
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        TextView recipeName = view.findViewById(R.id.recipeName);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);
        TextView recipeDescription = view.findViewById(R.id.recipeDescription);
        LinearLayout stepsContainer = view.findViewById(R.id.stepsContainer);
        LinearLayout ingredientsContainer = view.findViewById(R.id.ingredientsContainer);

        recipeName.setText(recipe.name);
        recipeDescription.setText(recipe.description);
        updateRecipeInFavoritesButton(favoriteButton, recipe.isInFavorite);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> steps = gson.fromJson(recipe.stepsAsJson, listType);
        List<String> ingredients = gson.fromJson(recipe.ingredientsAsJson, listType);

        updateRecipeStepsContainer(steps, stepsContainer);
        updateRecipeIngredientsContainer(ingredients, ingredientsContainer);

        favoriteButton.setOnClickListener(v -> recipeToFavoritesButtonClicked(favoriteButton));

        return view;
    }

    private void recipeToFavoritesButtonClicked(ImageButton favoriteButton) {
        recipe.isInFavorite = !recipe.isInFavorite;
        updateRecipeInFavoritesButton(favoriteButton, recipe.isInFavorite);
        this.recipesViewModel.updateRecipe(recipe);
    }

    public void updateRecipeInFavoritesButton(ImageButton favoriteButton, boolean isInFavorites) {
        favoriteButton.setImageResource(
                isInFavorites ? R.drawable.favorite_icon_enabled : R.drawable.favorite_icon_disabled);
    }

    private void updateRecipeStepsContainer(List<String> steps, LinearLayout stepsContainer) {
        for (int i = 0; i < steps.size(); i++) {
            TextView stepTextView = new TextView(getContext());
            stepTextView.setText((i + 1) + ". " + steps.get(i));
            stepsContainer.addView(stepTextView);
        }
    }

    private void updateRecipeIngredientsContainer(List<String> ingredients, LinearLayout ingredientsContainer) {
        for (String ingredient : ingredients) {
            TextView ingredientTextView = new TextView(getContext());
            ingredientTextView.setText(ingredient);
            ingredientsContainer.addView(ingredientTextView);
        }
    }
}