package org.jls.mealplanner.model;

import org.jls.mealplanner.ingredient.IngredientBaseModel;

public class SharedDataHolder {

    private static SharedDataHolder INSTANCE = null;

    private IngredientBaseModel ingredientBaseModel;

    private SharedDataHolder() {
        ingredientBaseModel = new IngredientBaseModel();
    }

    public final static SharedDataHolder getInstance() {
        if (SharedDataHolder.INSTANCE == null) {
            SharedDataHolder.INSTANCE = new SharedDataHolder();
        }
        return SharedDataHolder.INSTANCE;
    }

    public IngredientBaseModel getIngredientBaseModel() {
        return ingredientBaseModel;
    }
}
