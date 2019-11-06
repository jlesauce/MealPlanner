package org.jls.mealplanner.ui.ingredients.ingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.IngredientItem;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<IngredientItem>> ingredientsItemsData;
    private ArrayList<IngredientItem> ingredientsItems;

    public IngredientsViewModel() {
        ingredientsItems = new ArrayList<>();
        ingredientsItemsData = new MutableLiveData<>();
        ingredientsItemsData.setValue(ingredientsItems);

        initializeIngredientItems();
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