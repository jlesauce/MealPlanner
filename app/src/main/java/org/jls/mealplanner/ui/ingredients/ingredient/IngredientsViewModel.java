package org.jls.mealplanner.ui.ingredients.ingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.IngredientItem;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<IngredientCategory> ingredientCategory;
    private MutableLiveData<ArrayList<IngredientItem>> ingredientsItemsData;
    private ArrayList<IngredientItem> ingredientsItems;

    public IngredientsViewModel() {
        ingredientCategory = new MutableLiveData<>();
        ingredientCategory.setValue(IngredientCategory.NONE);

        ingredientsItems = new ArrayList<>();
        ingredientsItemsData = new MutableLiveData<>();
        ingredientsItemsData.setValue(ingredientsItems);

        initializeIngredientItems();
    }

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory.getValue();
    }

    public void setIngredientCategory(final IngredientCategory ingredientCategory) {
        this.ingredientCategory.setValue(ingredientCategory);
    }

    public MutableLiveData<ArrayList<IngredientItem>> getIngredientsItemsData() {
        return ingredientsItemsData;
    }

    private void initializeIngredientItems() {
        ingredientsItems.add(new IngredientItem(R.drawable.icon_proteins, "Tomate"));
        ingredientsItems.add(new IngredientItem(R.drawable.icon_vegetables, "Concombre"));
        ingredientsItems.add(new IngredientItem(R.drawable.icon_carbohydrates, "Salade"));
    }
}