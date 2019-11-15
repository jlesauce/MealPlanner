package org.jls.mealplanner.ui.ingredients.ingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.ingredient.Ingredient;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<IngredientCategory> ingredientCategory;
    private MutableLiveData<ArrayList<Ingredient>> ingredientsItemsData;

    public IngredientsViewModel() {
        ingredientCategory = new MutableLiveData<>();
        ingredientCategory.setValue(IngredientCategory.NONE);

        ingredientsItemsData = new MutableLiveData<>();
        ingredientsItemsData.setValue(new ArrayList<Ingredient>());
    }

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory.getValue();
    }

    public void setIngredientCategory(final IngredientCategory ingredientCategory) {
        this.ingredientCategory.setValue(ingredientCategory);
    }

    public void setIngredientsItemsData(ArrayList<Ingredient> ingredientsItemsData) {
        this.ingredientsItemsData.setValue(ingredientsItemsData);
    }

    public MutableLiveData<ArrayList<Ingredient>> getIngredientsItemsData() {
        return ingredientsItemsData;
    }
}