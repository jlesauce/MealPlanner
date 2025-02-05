package com.jls.mealplanner.ui.ingredients;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.jls.mealplanner.R;
import com.jls.mealplanner.databinding.FragmentIngredientsBinding;
import com.jls.mealplanner.ui.SearchableFragment;

public class IngredientsFragment extends SearchableFragment {

    private final String TAG = IngredientsFragment.class.getSimpleName();

    private FragmentIngredientsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "Creating " + TAG);
        binding = FragmentIngredientsBinding.inflate(inflater, container, false);

        createTabs();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Destroying " + TAG);
        binding = null;
    }

    private void createTabs() {
        String[] tabTitles = new String[]{
                getResources().getString(R.string.my_ingredients),
                getResources().getString(R.string.manage_ingredients),
                getResources().getString(R.string.grocery_list)};

        ViewPager2 viewPager = binding.ingredientsViewPager;
        IngredientsViewPagerAdapter viewPagerAdapter = new IngredientsViewPagerAdapter(getChildFragmentManager(),
                                                                                       getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.ingredientsTabLayout, viewPager,
                              (tab, position) -> tab.setText(tabTitles[position])).attach();
    }
}
