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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    public static RecipeDetailsFragment newInstance(RecipeEntity recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString("name", recipe.name);
        args.putBoolean("isInFavorite", recipe.isInFavorite);
        args.putString("description", recipe.description);
        args.putString("stepsAsJson", recipe.stepsAsJson);
        args.putString("ingredientsAsJson", recipe.ingredientsAsJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        if (getArguments() == null) {
            return view;
        }

        String name = getArguments().getString("name");
        boolean isInFavorite = getArguments().getBoolean("isInFavorite");
        String description = getArguments().getString("description");
        String stepsAsJson = getArguments().getString("stepsAsJson");
        String ingredientsAsJson = getArguments().getString("ingredientsAsJson");

        TextView recipeName = view.findViewById(R.id.recipeName);
        TextView recipeDescription = view.findViewById(R.id.recipeDescription);
        LinearLayout stepsContainer = view.findViewById(R.id.stepsContainer);
        LinearLayout ingredientsContainer = view.findViewById(R.id.ingredientsContainer);

        recipeName.setText(name);
        recipeDescription.setText(description);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();

        // Convert stepsAsJson to a list and iterate over it
        List<String> steps = gson.fromJson(stepsAsJson, listType);
        for (int i = 0; i < steps.size(); i++) {
            TextView stepTextView = new TextView(getContext());
            stepTextView.setText((i + 1) + ". " + steps.get(i));
            stepsContainer.addView(stepTextView);
        }

        // Convert ingredientsAsJson to a list and iterate over it
        List<String> ingredients = gson.fromJson(ingredientsAsJson, listType);
        for (String ingredient : ingredients) {
            TextView ingredientTextView = new TextView(getContext());
            ingredientTextView.setText(ingredient);
            ingredientsContainer.addView(ingredientTextView);
        }

        return view;
    }
}