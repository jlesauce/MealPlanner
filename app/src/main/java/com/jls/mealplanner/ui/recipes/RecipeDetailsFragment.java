package com.jls.mealplanner.ui.recipes;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jls.mealplanner.R;
import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.model.IngredientIconViewModel;
import com.jls.mealplanner.model.IngredientViewModel;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.ui.ingredients.IngredientViewHolder;
import com.jls.mealplanner.utils.AssetUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private final RecipeViewModel recipesViewModel;
    private final RecipeEntity recipe;
    private final IngredientViewModel ingredientsViewModel;
    private final HashMap<String, IngredientIconEntity> icons;

    public RecipeDetailsFragment(RecipeViewModel recipesViewModel, IngredientViewModel ingredientsViewModel,
                                 IngredientIconViewModel iconsViewModel,
                                 RecipeEntity recipe) {
        super();
        this.recipesViewModel = recipesViewModel;
        this.ingredientsViewModel = ingredientsViewModel;
        this.recipe = recipe;
        this.icons = new HashMap<>();

        iconsViewModel.getAllIngredientIcons().observe(this, allIcons -> {
            if (allIcons == null) {
                return;
            }

            icons.clear();
            for (IngredientIconEntity icon : allIcons) {
                icons.put(icon.shortName, icon);
            }
        });
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

        favoriteButton.setOnClickListener(v -> recipeToFavoritesButtonClicked(favoriteButton));

        ingredientsViewModel.getAllIngredients().observe(getViewLifecycleOwner(), allIngredients -> {
            updateRecipeIngredientsContainer(ingredients, ingredientsContainer);
        });

        return view;
    }

    private void recipeToFavoritesButtonClicked(ImageButton favoriteButton) {
        recipe.isInFavorite = !recipe.isInFavorite;
        updateRecipeInFavoritesButton(favoriteButton, recipe.isInFavorite);
        this.recipesViewModel.updateRecipe(recipe);
    }

    private void ingredientInGroceryListButtonClicked(IngredientViewHolder holder, final IngredientEntity ingredient) {
        ingredient.isInGroceryList = !ingredient.isInGroceryList;
        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);
        this.ingredientsViewModel.updateIngredient(ingredient);
    }

    private void ingredientInMyIngredientsCheckBoxClicked(IngredientViewHolder holder,
                                                          final IngredientEntity ingredient) {
        ingredient.isPossessed = !ingredient.isPossessed;
        this.ingredientsViewModel.updateIngredient(ingredient);
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
        ingredientsContainer.removeAllViews();

        for (String ingredientName : ingredients) {
            ingredientsViewModel.searchIngredient(ingredientName).observe(getViewLifecycleOwner(), ingredientEntity -> {
                onSearchIngredientResultReceived(ingredientsContainer, ingredientEntity, ingredientName);
            });
        }
    }

    private void onSearchIngredientResultReceived(LinearLayout ingredientsContainer,
                                                  IngredientEntity ingredientEntity, String ingredientName) {
        View itemView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_ingredient, ingredientsContainer, false);
        IngredientViewHolder holder = new IngredientViewHolder(itemView);

        if (ingredientEntity != null) {
            updateViewWithIngredientEntity(ingredientEntity, holder);
        } else {
            updateViewWhenIngredientNotFound(holder, ingredientName);
        }

        ingredientsContainer.addView(itemView);
    }

    private void updateViewWithIngredientEntity(IngredientEntity ingredient, IngredientViewHolder holder) {
        holder.ingredientName.setText(ingredient.name);
        holder.addToMyIngredientsCheckBox.setChecked(ingredient.isPossessed);
        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);

        IngredientIconEntity iconEntity = icons.get(ingredient.iconId);
        if (iconEntity != null) {
            Bitmap bitmapIcon = AssetUtils.getIconFromAssets(holder.ingredientIcon.getContext(),
                                                             iconEntity.iconPath);
            holder.ingredientIcon.setImageBitmap(bitmapIcon);
        } else {
            holder.ingredientIcon.setImageResource(R.drawable.ingredients_icon);
        }

        holder.addIngredientToGroceryList.setOnClickListener(
                v -> ingredientInGroceryListButtonClicked(holder, ingredient));
        holder.addToMyIngredientsCheckBox.setOnClickListener(
                v -> ingredientInMyIngredientsCheckBoxClicked(holder, ingredient));
    }

    private static void updateViewWhenIngredientNotFound(IngredientViewHolder holder, String ingredientName) {
        holder.ingredientIcon.setImageResource(R.drawable.ingredients_icon);
        holder.ingredientName.setText(ingredientName);
        holder.addIngredientToGroceryList.setVisibility(View.GONE);
        holder.addToMyIngredientsCheckBox.setVisibility(View.GONE);
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
    }
}