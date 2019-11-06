package org.jls.mealplanner.ui.ingredients.category;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.CategoryItem;

import java.util.ArrayList;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CategoryItem>> categoryItemsData;
    private ArrayList<CategoryItem> categoryItems;

    public CategoriesViewModel() {
        categoryItems = new ArrayList<>();
        categoryItemsData = new MutableLiveData<>();
        categoryItemsData.setValue(categoryItems);

        initializeCategoryItems();
    }

    public MutableLiveData<ArrayList<CategoryItem>> getCategoryItemsData() {
        return categoryItemsData;
    }

    private void initializeCategoryItems() {
        categoryItems.add(new CategoryItem(IngredientCategory.PROTEINS, R.drawable.icon_proteins, "Protéines"));
        categoryItems.add(new CategoryItem(IngredientCategory.VEGETABLES, R.drawable.icon_vegetables, "Légumes"));
        categoryItems.add(new CategoryItem(IngredientCategory.CARBOHYDRATES, R.drawable.icon_carbohydrates, "Féculents"));
    }
}