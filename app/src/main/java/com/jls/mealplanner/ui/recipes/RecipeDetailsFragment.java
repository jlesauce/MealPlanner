package com.jls.mealplanner.ui.recipes;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.model.RecipeViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private final String TAG = RecipeDetailsFragment.class.getSimpleName();

    private RecipeViewModel recipesViewModel;
    private RecipeEntity recipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("RecipeDetailsFragment requires recipe_id argument");
        }

        int recipe_id = getArguments().getInt("recipe_id");
        recipesViewModel = new ViewModelProvider(requireActivity())
                .get(RecipeViewModel.class);
        recipe = recipesViewModel.getRecipeById(recipe_id);

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        TextView recipeName = view.findViewById(R.id.recipeName);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);
        TextView recipeDescription = view.findViewById(R.id.recipeDescription);
        LinearLayout stepsContainer = view.findViewById(R.id.stepsContainer);
        RecyclerView ingredientsRecyclerView = view.findViewById(R.id.recipeIngredientsRecyclerView);
        Pair<List<String>, ArrayList<String>> stepsAndIngredients = extractStepsAndIngredients(recipe);

        recipeName.setText(recipe.name);
        recipeDescription.setText(recipe.description);
        updateRecipeInFavoritesButton(favoriteButton, recipe.isInFavorite);
        updateRecipeStepsContainer(stepsAndIngredients.first, stepsContainer);
        createIngredientsListRecyclerView(stepsAndIngredients.second, ingredientsRecyclerView);

        favoriteButton.setOnClickListener(v -> recipeToFavoritesButtonClicked(favoriteButton));

        return view;
    }

    private Pair<List<String>, ArrayList<String>> extractStepsAndIngredients(RecipeEntity recipe) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> steps = gson.fromJson(recipe.stepsAsJson, listType);
        ArrayList<String> ingredients = gson.fromJson(recipe.ingredientsAsJson, listType);
        return new Pair<>(steps, ingredients);
    }

    private void recipeToFavoritesButtonClicked(ImageButton favoriteButton) {
        recipe.isInFavorite = !recipe.isInFavorite;
        Log.d(TAG, "Add recipe to favorites button clicked, isInFavorite=" + recipe.isInFavorite);
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

    private void createIngredientsListRecyclerView(ArrayList<String> ingredients,
                                                   RecyclerView ingredientsRecyclerView) {
        IngredientsViewModel ingredientsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientsViewModel.class);
        IngredientIconsViewModel iconsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientIconsViewModel.class);
        RecipeIngredientsViewAdapter viewAdapter = new RecipeIngredientsViewAdapter(this,
                                                                                    ingredientsViewModel,
                                                                                    iconsViewModel,
                                                                                    ingredients);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsRecyclerView.setAdapter(viewAdapter);
    }
}