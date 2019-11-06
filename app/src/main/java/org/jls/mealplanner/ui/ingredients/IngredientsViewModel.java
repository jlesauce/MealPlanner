package org.jls.mealplanner.ui.ingredients;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jls.mealplanner.R;

import java.util.ArrayList;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CategoryItem>> categoryItemsData;
    private ArrayList<CategoryItem> categoryItems;

    public IngredientsViewModel() {
        categoryItems = new ArrayList<>();
        categoryItemsData = new MutableLiveData<>();
        categoryItemsData.setValue(categoryItems);

        initializeCategoryItems();
    }

    public MutableLiveData<ArrayList<CategoryItem>> getCategoryItemsData() {
        return categoryItemsData;
    }

    private void initializeCategoryItems() {
        categoryItems.add(new CategoryItem(R.drawable.proteins, "Protéines"));
        categoryItems.add(new CategoryItem(R.drawable.vegetables, "Légumes"));
        categoryItems.add(new CategoryItem(R.drawable.carbohydrates, "Féculents"));
    }
}