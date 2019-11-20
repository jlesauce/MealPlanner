package org.jls.mealplanner.ingredient;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientBaseModel {

    private HashMap<IngredientCategory, ArrayList<Ingredient>> ingredients;

    public IngredientBaseModel() {
        ingredients = new HashMap<>();
        ingredients.put(IngredientCategory.PROTEINS, new ArrayList<Ingredient>());
        ingredients.put(IngredientCategory.VEGETABLES, new ArrayList<Ingredient>());
        ingredients.put(IngredientCategory.CARBOHYDRATES, new ArrayList<Ingredient>());
        ingredients.put(IngredientCategory.NONE, new ArrayList<Ingredient>());
    }

    public void addNewIngredient(final String name, final IngredientCategory category) {
        Ingredient ingredient = new Ingredient(name, R.drawable.icon_default_ingredient);
        ingredients.get(category).add(ingredient);
    }

    public ArrayList<Ingredient> getListOfIngredientsForCategory(final IngredientCategory category) {
        return ingredients.get(category);
    }
}
