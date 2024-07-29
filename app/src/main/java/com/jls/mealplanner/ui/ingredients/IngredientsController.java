package com.jls.mealplanner.ui.ingredients;

import androidx.annotation.NonNull;

import com.jls.mealplanner.model.Ingredient;
import com.jls.mealplanner.model.SharedDataHolder;

import java.util.ArrayList;

public class IngredientsController {

    private final SharedDataHolder sharedDataHolder;


    public IngredientsController(@NonNull SharedDataHolder sharedDataHolder) {
        this.sharedDataHolder = sharedDataHolder;
    }

    public ArrayList<Ingredient> getIngredients(IngredientVisibility ingredientVisibility) {
        switch (ingredientVisibility) {
            case MY_STOCK:
                return getMyStock();
            case ALL_INGREDIENTS:
                return new ArrayList<>(sharedDataHolder.getIngredients().values());
            case MY_GROCERY_LIST:
                return getGroceryList();
            default:
                return new ArrayList<>();
        }
    }

    public ArrayList<Ingredient> getMyStock() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient ingredient : sharedDataHolder.getIngredients().values()) {
            if (ingredient.isPossessed()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    public ArrayList<Ingredient> getGroceryList() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient ingredient : sharedDataHolder.getIngredients().values()) {
            if (ingredient.isInGroceryList()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    public void addIngredientToMyIngredients(final Ingredient ingredient) {
        setIngredientInMyStock(ingredient, true);
        sharedDataHolder.notifyDataChanged();
    }

    public void removeIngredientFromMyIngredients(final Ingredient ingredient) {
        setIngredientInMyStock(ingredient, false);
        sharedDataHolder.notifyDataChanged();
    }

    public void addIngredientToGroceryList(final Ingredient ingredient) {
        setIngredientInGroceryList(ingredient, true);
        sharedDataHolder.notifyDataChanged();
    }

    public void removeIngredientFromGroceryList(final Ingredient ingredient) {
        setIngredientInGroceryList(ingredient, false);
        sharedDataHolder.notifyDataChanged();
    }

    public void setIngredientInMyStock(Ingredient ingredient, boolean isPossessed) {
        if (sharedDataHolder.getIngredients().containsKey(ingredient.getIngredientName())) {
            Ingredient ingredientFromList = sharedDataHolder.getIngredients().get(ingredient.getIngredientName());
            assert ingredientFromList != null;
            ingredientFromList.setPossessed(isPossessed);
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list : " + ingredient.getIngredientName());
        }
    }

    public void setIngredientInGroceryList(Ingredient ingredient, boolean isInGroceryList) {
        if (sharedDataHolder.getIngredients().containsKey(ingredient.getIngredientName())) {
            Ingredient ingredientFromList = sharedDataHolder.getIngredients().get(ingredient.getIngredientName());
            assert ingredientFromList != null;
            ingredientFromList.setInGroceryList(isInGroceryList);
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list : " + ingredient.getIngredientName());
        }
    }
}