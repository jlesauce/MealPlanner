package com.jls.mealplanner.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.model.WeeklyRecipesViewModel;

/**
 * Builds the data-backed ViewModels from the {@link AppContainer}, supplying their repository
 * dependencies. ViewModels with no dependencies (e.g. AboutViewModel, SettingsViewModel) do not
 * need this factory and can keep the default one.
 */
public class AppViewModelFactory implements ViewModelProvider.Factory {

    private final AppContainer container;

    public AppViewModelFactory(AppContainer container) {
        this.container = container;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(IngredientsViewModel.class)) {
            return (T) new IngredientsViewModel(container.ingredientRepository);
        } else if (modelClass.isAssignableFrom(IngredientIconsViewModel.class)) {
            return (T) new IngredientIconsViewModel(container.ingredientIconRepository);
        } else if (modelClass.isAssignableFrom(RecipeViewModel.class)) {
            return (T) new RecipeViewModel(container.recipesRepository);
        } else if (modelClass.isAssignableFrom(WeeklyRecipesViewModel.class)) {
            return (T) new WeeklyRecipesViewModel(container.weeklyRecipeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
