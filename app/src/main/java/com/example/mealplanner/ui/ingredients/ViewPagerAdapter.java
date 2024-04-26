package com.example.mealplanner.ui.ingredients;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mealplanner.IngredientsController;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final static int NB_OF_TABS = 3;
    private final IngredientsController controller;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, IngredientsController controller) {
        super(fragmentManager, lifecycle);
        this.controller = controller;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyIngredientsFragment(controller);
            case 1:
                return new ManageIngredientsFragment(controller);
            case 2:
                return new GroceryListFragment(controller);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NB_OF_TABS;
    }
}