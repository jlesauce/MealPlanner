package org.jls.mealplanner.ui.ingredients.ingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.ingredient.IngredientItem;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<IngredientCategory> ingredientCategory;
    private MutableLiveData<ArrayList<IngredientItem>> ingredientsItemsData;

    public IngredientsViewModel() {
        ingredientCategory = new MutableLiveData<>();
        ingredientCategory.setValue(IngredientCategory.NONE);

        ingredientsItemsData = new MutableLiveData<>();
        ingredientsItemsData.setValue(new ArrayList<IngredientItem>());
    }

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory.getValue();
    }

    public void setIngredientCategory(final IngredientCategory ingredientCategory) {
        this.ingredientCategory.setValue(ingredientCategory);
    }

    public void setIngredientsItemsData(ArrayList<IngredientItem> ingredientsItemsData) {
        this.ingredientsItemsData.setValue(ingredientsItemsData);
    }

    public MutableLiveData<ArrayList<IngredientItem>> getIngredientsItemsData() {
        return ingredientsItemsData;
    }
}