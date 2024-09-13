// RecipeDetailsFragment.java
package com.jls.mealplanner.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;

import java.util.ArrayList;

public class RecipeDetailsFragment extends Fragment {

    private RecipeEntity recipe;

    public static RecipeDetailsFragment newInstance(RecipeEntity recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString("name", recipe.name);
        args.putBoolean("isInFavorites", recipe.isInFavorites);
        args.putString("iconId", recipe.iconId);
        args.putString("description", recipe.description);
        args.putStringArrayList("steps", new ArrayList<>(recipe.steps));
        args.putStringArrayList("ingredients", new ArrayList<>(recipe.ingredients));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        if (getArguments() != null) {
            recipe = new RecipeEntity(
                    getArguments().getString("name"),
                    getArguments().getBoolean("isInFavorites"),
                    getArguments().getString("iconId"),
                    getArguments().getString("description"),
                    getArguments().getStringArrayList("steps"),
                    getArguments().getStringArrayList("ingredients")
            );
        }

        TextView recipeName = view.findViewById(R.id.recipeName);
        TextView recipeDescription = view.findViewById(R.id.recipeDescription);
        LinearLayout stepsContainer = view.findViewById(R.id.stepsContainer);
        LinearLayout ingredientsContainer = view.findViewById(R.id.ingredientsContainer);

        recipeName.setText(recipe.name);
        recipeDescription.setText(recipe.description);

        for (int i = 0; i < recipe.steps.size(); i++) {
            TextView stepTextView = new TextView(getContext());
            stepTextView.setText((i + 1) + ". " + recipe.steps.get(i));
            stepsContainer.addView(stepTextView);
        }

        for (String ingredient : recipe.ingredients) {
            TextView ingredientTextView = new TextView(getContext());
            ingredientTextView.setText(ingredient);
            ingredientsContainer.addView(ingredientTextView);
        }

        return view;
    }
}