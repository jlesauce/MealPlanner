package com.jls.mealplanner.ui.ingredients;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class IngredientsViewPagerAdapter extends FragmentStateAdapter {

    private final static int NB_OF_TABS = 3;

    public IngredientsViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IngredientsTabFragment(IngredientListType.MY_STOCK);
            case 1:
                return new IngredientsTabFragment(IngredientListType.ALL_INGREDIENTS);
            case 2:
                return new IngredientsTabFragment(IngredientListType.MY_GROCERY_LIST);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NB_OF_TABS;
    }
}