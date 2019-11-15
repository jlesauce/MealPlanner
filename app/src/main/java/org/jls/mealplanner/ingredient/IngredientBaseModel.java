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

        createDummyIngredients();
    }

    public void addNewIngredient(final IngredientCategory category, final String name) {
        Ingredient ingredient = new Ingredient(R.drawable.icon_default_ingredient, name);
        ingredients.get(category).add(ingredient);
    }

    public ArrayList<Ingredient> getListOfIngredientsForCategory(final IngredientCategory category) {
        return ingredients.get(category);
    }

    private void createDummyIngredients() {
        ArrayList<Ingredient> proteins = ingredients.get(IngredientCategory.PROTEINS);
        proteins.add(new Ingredient(R.drawable.icon_default_ingredient, "Steack hâché"));
        proteins.add(new Ingredient(R.drawable.icon_default_ingredient, "Poisson"));
        proteins.add(new Ingredient(R.drawable.icon_default_ingredient, "Saucisse"));

        ArrayList<Ingredient> vegetables = ingredients.get(IngredientCategory.VEGETABLES);
        vegetables.add(new Ingredient(R.drawable.icon_default_ingredient, "Courgette"));
        vegetables.add(new Ingredient(R.drawable.icon_default_ingredient, "Tomate"));
        vegetables.add(new Ingredient(R.drawable.icon_default_ingredient, "Carotte"));

        ArrayList<Ingredient> carbohydrates = ingredients.get(IngredientCategory.CARBOHYDRATES);
        carbohydrates.add(new Ingredient(R.drawable.icon_default_ingredient, "Pâtes"));
        carbohydrates.add(new Ingredient(R.drawable.icon_default_ingredient, "Riz"));
        carbohydrates.add(new Ingredient(R.drawable.icon_default_ingredient, "Lentilles"));
    }
}
