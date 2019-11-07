package org.jls.mealplanner.ingredient;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientBaseModel {

    private HashMap<IngredientCategory, ArrayList<IngredientItem>> ingredients;

    public IngredientBaseModel() {
        ingredients = new HashMap<>();
        ingredients.put(IngredientCategory.PROTEINS, new ArrayList<IngredientItem>());
        ingredients.put(IngredientCategory.VEGETABLES, new ArrayList<IngredientItem>());
        ingredients.put(IngredientCategory.CARBOHYDRATES, new ArrayList<IngredientItem>());
        ingredients.put(IngredientCategory.NONE, new ArrayList<IngredientItem>());

        createDummyIngredients();
    }

    public void addNewIngredient(final IngredientCategory category, final String name) {
        if (!ingredients.containsKey(category)) {
            IngredientItem ingredient = new IngredientItem(R.drawable.icon_default_ingredient, name);
            ingredients.get(category).add(ingredient);
        }
    }

    public ArrayList<IngredientItem> getListOfIngredientsForCategory(final IngredientCategory category) {
        ArrayList<IngredientItem> ingredientsList = ingredients.get(category);
        return ingredientsList != null ? new ArrayList<>(ingredientsList) : null;
    }

    private void createDummyIngredients() {
        ArrayList<IngredientItem> proteins = ingredients.get(IngredientCategory.PROTEINS);
        proteins.add(new IngredientItem(R.drawable.icon_default_ingredient, "Steack hâché"));
        proteins.add(new IngredientItem(R.drawable.icon_default_ingredient, "Poisson"));
        proteins.add(new IngredientItem(R.drawable.icon_default_ingredient, "Saucisse"));

        ArrayList<IngredientItem> vegetables = ingredients.get(IngredientCategory.VEGETABLES);
        vegetables.add(new IngredientItem(R.drawable.icon_default_ingredient, "Courgette"));
        vegetables.add(new IngredientItem(R.drawable.icon_default_ingredient, "Tomate"));
        vegetables.add(new IngredientItem(R.drawable.icon_default_ingredient, "Carotte"));

        ArrayList<IngredientItem> carbohydrates = ingredients.get(IngredientCategory.CARBOHYDRATES);
        carbohydrates.add(new IngredientItem(R.drawable.icon_default_ingredient, "Pâtes"));
        carbohydrates.add(new IngredientItem(R.drawable.icon_default_ingredient, "Riz"));
        carbohydrates.add(new IngredientItem(R.drawable.icon_default_ingredient, "Lentilles"));
    }
}
