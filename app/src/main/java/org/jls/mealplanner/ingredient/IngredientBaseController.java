package org.jls.mealplanner.ingredient;

import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

public class IngredientBaseController {

    private IngredientBaseModel model;

    public IngredientBaseController(IngredientBaseModel ingredientBaseModel) {
        model = ingredientBaseModel;
    }

    public void addNewIngredient(final String name, final IngredientCategory category) {
        if (checkIngredientName(name)) {
            model.addNewIngredient(name, category);
        }
    }

    private boolean checkIngredientName(final String name) {
        return name != null && !name.isEmpty();
    }
}
