package com.example.mealplanner.model;

import com.example.mealplanner.R;
import com.example.mealplanner.database.IngredientEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedDataHolder {

    private static SharedDataHolder INSTANCE = null;

    private final Set<Runnable> observers = new HashSet<>();

    private final HashMap<String, Ingredient> ingredients;
    private IngredientViewModel ingredientsViewModel;
    private boolean isTestRequestSent;

    private SharedDataHolder() {
        ingredients = new HashMap<>();
        isTestRequestSent = false;
    }

    public void addObserver(Runnable observer) {
        observers.add(observer);
    }

    public void notifyDataChanged() {
        notifyObservers();
    }

    private void notifyObservers() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }

    private void initIngredientsList(List<IngredientEntity> ingredientEntities) {
        if (ingredientEntities == null) {
            throw new RuntimeException("Ingredients list is null");
        }

        for (IngredientEntity ingredient : ingredientEntities) {
            ingredients.put(ingredient.name, new Ingredient(
                    ingredient.name,
                    R.drawable.ingredients_icon,
                    ingredient.isPossessed,
                    ingredient.isInGroceryList));
        }
    }

    public static SharedDataHolder getInstance() {
        if (SharedDataHolder.INSTANCE == null) {
            SharedDataHolder.INSTANCE = new SharedDataHolder();
        }
        return SharedDataHolder.INSTANCE;
    }

    public void setIngredientsViewModel(IngredientViewModel ingredientViewModel) {
        this.ingredientsViewModel = ingredientViewModel;
        this.ingredientsViewModel.getAllIngredients().observeForever(this::initIngredientsList);
    }

    public HashMap<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean isTestRequestSent() {
        return isTestRequestSent;
    }

    public void setTestRequestSent(boolean isSent) {
        isTestRequestSent = isSent;
    }
}
