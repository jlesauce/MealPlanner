package org.jls.mealplanner.ui.ingredients;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> ingredientCategories;
    private ArrayList<String> ingredientCategoriesList;

    public IngredientsViewModel() {
        ingredientCategoriesList = new ArrayList<>();
        ingredientCategories = new MutableLiveData<>();
        ingredientCategories.setValue(ingredientCategoriesList);

        initializeCategories();
    }

    public MutableLiveData<ArrayList<String>> getIngredientCategories() {
        return ingredientCategories;
    }

    private void initializeCategories() {
        ingredientCategoriesList.add("Protéines");
        ingredientCategoriesList.add("Légumes");
        ingredientCategoriesList.add("Féculents");
    }
}