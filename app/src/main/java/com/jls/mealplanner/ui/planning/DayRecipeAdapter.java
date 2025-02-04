package com.jls.mealplanner.ui.planning;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeEntity;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.model.WeeklyRecipesViewModel;
import com.jls.mealplanner.ui.recipes.RecipeViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayRecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {


    private static final String TAG = DayRecipeAdapter.class.getSimpleName();

    private final PlanningFragment fragment;
    private final ArrayList<WeeklyRecipeEntity> recipesForTheDay;
    private final HashMap<Integer, RecipeEntity> allRecipes;
    private final Map<String, Observer<List<WeeklyRecipeEntity>>> weeklyRecipesObservers;
    private final WeeklyRecipesViewModel weeklyRecipesViewModel;

    public DayRecipeAdapter(PlanningFragment fragment, WeeklyRecipesViewModel weeklyRecipesViewModel, int currentDay) {
        this.fragment = fragment;
        this.weeklyRecipesViewModel = weeklyRecipesViewModel;
        this.recipesForTheDay = new ArrayList<>();
        this.allRecipes = new HashMap<>();
        this.weeklyRecipesObservers = new HashMap<>();

        RecipeViewModel recipeViewModel = new ViewModelProvider(fragment.requireActivity())
                .get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(fragment, recipes -> {
            if (recipes == null) {
                return;
            }
            this.allRecipes.clear();
            for (RecipeEntity recipe : recipes) {
                this.allRecipes.put(recipe.id, recipe);
            }
        });

        weeklyRecipesViewModel.getSelectedWeekNumber().observe(fragment, weekNumber -> {
            updateDayRecipes(fragment, weeklyRecipesViewModel, getCurrentYear(), weekNumber, currentDay);
            notifyDataSetChanged();
        });
    }

    private void updateDayRecipes(PlanningFragment fragment, WeeklyRecipesViewModel weeklyRecipesViewModel,
                                  int currentYear, int currentWeek, int currentDay) {
        /*
          Implementing a map to store the observers for each day of the week to avoid multiple observers for the same
          day and week, because otherwise, each time the week is changed, a new observer is created for the same day.
         */
        String key = currentWeek + "-" + currentDay;
        Observer<List<WeeklyRecipeEntity>> currentObserver = weeklyRecipesObservers.get(key);

        if (currentObserver != null) {
            weeklyRecipesViewModel.getRecipeForDay(currentYear, currentWeek, currentDay)
                    .removeObserver(currentObserver);
        }

        currentObserver = recipes -> {
            if (recipes == null) {
                return;
            }

            this.recipesForTheDay.clear();
            this.recipesForTheDay.addAll(recipes);

            notifyDataSetChanged();
        };

        weeklyRecipesObservers.put(key, currentObserver);
        weeklyRecipesViewModel.getRecipeForDay(currentYear, currentWeek, currentDay).observe(fragment, currentObserver);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        WeeklyRecipeEntity currentRecipe = recipesForTheDay.get(position);
        RecipeEntity recipe = allRecipes.get(currentRecipe.recipe_id);

        if (recipe == null) {
            Log.e(TAG, "Recipe not found for id: " + currentRecipe.recipe_id);
            return;
        }

        initRecipeItem(holder, recipe.name);
        minimizeRecipeSize(holder);

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Recipe clicked: " + currentRecipe);
        });
    }

    private void initRecipeItem(@NonNull RecipeViewHolder holder, final String recipe) {
        holder.recipeName.setText(recipe);
        holder.recipeIcon.setImageResource(R.drawable.recipe_icon);
        holder.addRecipeToFavorites.setVisibility(View.GONE);
    }

    private void minimizeRecipeSize(@NonNull RecipeViewHolder holder) {
        holder.recipeIcon.getLayoutParams().width = WRAP_CONTENT;
        holder.recipeIcon.getLayoutParams().height = WRAP_CONTENT;
        holder.recipeIcon.requestLayout();

        int textSize = fragment.requireContext().getResources().getDimensionPixelSize(R.dimen.reduced_text_size);
        holder.recipeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        holder.itemView.setLayoutParams(layoutParams);
    }

    private int getCurrentYear() {
        Integer year = weeklyRecipesViewModel.getSelectedYear().getValue();
        return year != null ? year : Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public int getItemCount() {
        return recipesForTheDay.size();
    }
}