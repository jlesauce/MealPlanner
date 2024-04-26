package com.example.mealplanner.model;

import com.example.mealplanner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SharedDataHolder {

    private static SharedDataHolder INSTANCE = null;

    private final Set<Runnable> observers = new HashSet<>();

    private final HashMap<String, Ingredient> ingredients;
    private boolean isTestRequestSent;

    private SharedDataHolder() {
        ingredients = new HashMap<>();
        initIngredientsWithDummyValues();
        isTestRequestSent = false;
    }

    public void addObserver(Runnable observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }

    private void initIngredientsWithDummyValues() {
        ingredients.put("Apple", new Ingredient("Apple", R.drawable.ingredients_icon, true, false));
        ingredients.put("Banana", new Ingredient("Banana", R.drawable.ingredients_icon, false, true));
        ingredients.put("Cherry", new Ingredient("Cherry", R.drawable.ingredients_icon));
        ingredients.put("Date", new Ingredient("Date", R.drawable.ingredients_icon));
        ingredients.put("Elderberry", new Ingredient("Elderberry", R.drawable.ingredients_icon));
        ingredients.put("Fig", new Ingredient("Fig", R.drawable.ingredients_icon));
        ingredients.put("Grape", new Ingredient("Grape", R.drawable.ingredients_icon));
        ingredients.put("Honeydew", new Ingredient("Honeydew", R.drawable.ingredients_icon));
        ingredients.put("Iceberg lettuce", new Ingredient("Iceberg lettuce", R.drawable.ingredients_icon));
        ingredients.put("Jalapeno", new Ingredient("Jalapeno", R.drawable.ingredients_icon));
        ingredients.put("Kiwi", new Ingredient("Kiwi", R.drawable.ingredients_icon));
        ingredients.put("Lemon", new Ingredient("Lemon", R.drawable.ingredients_icon));
        ingredients.put("Mango", new Ingredient("Mango", R.drawable.ingredients_icon));
        ingredients.put("Nectarine", new Ingredient("Nectarine", R.drawable.ingredients_icon));
        ingredients.put("Orange", new Ingredient("Orange", R.drawable.ingredients_icon));
        ingredients.put("Papaya", new Ingredient("Papaya", R.drawable.ingredients_icon));
        ingredients.put("Quince", new Ingredient("Quince", R.drawable.ingredients_icon));
        ingredients.put("Raspberry", new Ingredient("Raspberry", R.drawable.ingredients_icon));
        ingredients.put("Strawberry", new Ingredient("Strawberry", R.drawable.ingredients_icon));
        ingredients.put("Tomato", new Ingredient("Tomato", R.drawable.ingredients_icon));
        ingredients.put("Ugli fruit", new Ingredient("Ugli fruit", R.drawable.ingredients_icon));
        ingredients.put("Vanilla", new Ingredient("Vanilla", R.drawable.ingredients_icon));
        ingredients.put("Watermelon", new Ingredient("Watermelon", R.drawable.ingredients_icon));
        ingredients.put("Xigua", new Ingredient("Xigua", R.drawable.ingredients_icon));
        ingredients.put("Yellow pepper", new Ingredient("Yellow pepper", R.drawable.ingredients_icon));
        ingredients.put("Zucchini but with an extremely long name",
                new Ingredient("Zucchini but with an extremely long name", R.drawable.ingredients_icon));
        ingredients.put("Onion", new Ingredient("Onion", R.drawable.ingredients_icon));
        ingredients.put("Garlic", new Ingredient("Garlic", R.drawable.ingredients_icon));
        ingredients.put("Cucumber", new Ingredient("Cucumber", R.drawable.ingredients_icon));
        ingredients.put("Carrot", new Ingredient("Carrot", R.drawable.ingredients_icon));
    }

    public static SharedDataHolder getInstance() {
        if (SharedDataHolder.INSTANCE == null) {
            SharedDataHolder.INSTANCE = new SharedDataHolder();
        }
        return SharedDataHolder.INSTANCE;
    }

    public HashMap<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Ingredient> getMyIngredients() {
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients.values()) {
            if (ingredient.isPossessed()) {
                myIngredients.add(ingredient);
            }
        }
        return myIngredients;
    }

    public ArrayList<Ingredient> getGroceryList() {
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients.values()) {
            if (ingredient.isInGroceryList()) {
                myIngredients.add(ingredient);
            }
        }
        return myIngredients;
    }

    public void addIngredientToGroceryList(String ingredientName) {
        Ingredient ingredient = getIngredients().get(ingredientName);
        if (ingredient != null) {
            ingredient.setInGroceryList(true);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list : name= " + ingredientName);
        }
    }

    public void removeIngredientFromGroceryList(String ingredientName) {
        Ingredient ingredient = getIngredients().get(ingredientName);
        if (ingredient != null) {
            ingredient.setInGroceryList(false);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list : name= " + ingredientName);
        }
    }

    public void addIngredientToMyStock(String ingredientName) {
        Ingredient ingredient = getIngredients().get(ingredientName);
        if (ingredient != null) {
            ingredient.setPossessed(true);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list");
        }
    }

    public void removeIngredientFromMyStock(String ingredientName) {
        Ingredient ingredient = getIngredients().get(ingredientName);
        if (ingredient != null) {
            ingredient.setPossessed(false);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Ingredient not found in the list");
        }
    }

    public boolean isTestRequestSent() {
        return isTestRequestSent;
    }

    public void setTestRequestSent(boolean isSent) {
        isTestRequestSent = isSent;
    }
}
