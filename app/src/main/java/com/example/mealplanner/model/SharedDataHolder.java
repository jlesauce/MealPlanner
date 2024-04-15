package com.example.mealplanner.model;

import com.example.mealplanner.R;

import java.util.HashMap;

public class SharedDataHolder {

    private static SharedDataHolder INSTANCE = null;

    private final HashMap<String, Ingredient> ingredients;

    private SharedDataHolder() {
        ingredients = new HashMap<>();
        initIngredientsWithDummyValues();
    }

    private void initIngredientsWithDummyValues() {
        ingredients.put("Apple", new Ingredient("Apple", R.drawable.ingredients_icon));
        ingredients.put("Banana", new Ingredient("Banana", R.drawable.ingredients_icon));
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
        ingredients.put("Zucchini", new Ingredient("Zucchini but with an extremely long name", R.drawable.ingredients_icon));
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
}
