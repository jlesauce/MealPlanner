package com.jls.mealplanner.ui.recipes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RecipesViewPagerAdapter extends FragmentStateAdapter {

    private final static int NB_OF_TABS = 3;

    public RecipesViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RecipesTabFragment(RecipeListType.MY_RECIPES);
            case 1:
                return new RecipesTabFragment(RecipeListType.ALL_RECIPES);
            case 2:
                return new RecipesTabFragment(RecipeListType.FAVORITE_RECIPES);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NB_OF_TABS;
    }
}