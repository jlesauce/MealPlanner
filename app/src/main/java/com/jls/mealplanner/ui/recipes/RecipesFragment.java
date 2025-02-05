package com.jls.mealplanner.ui.recipes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.jls.mealplanner.R;
import com.jls.mealplanner.databinding.FragmentRecipesBinding;
import com.jls.mealplanner.ui.SearchableFragment;

public class RecipesFragment extends SearchableFragment {

    private static final String TAG = RecipesFragment.class.getSimpleName();

    private FragmentRecipesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "Creating " + TAG);
        binding = FragmentRecipesBinding.inflate(inflater, container, false);

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
                getResources().getString(R.string.my_recipes),
                getResources().getString(R.string.all_recipes),
                getResources().getString(R.string.favorites)};

        ViewPager2 viewPager = binding.recipesViewPager;
        RecipesViewPagerAdapter viewPagerAdapter = new RecipesViewPagerAdapter(getChildFragmentManager(),
                                                                               getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.recipesTabLayout, viewPager,
                              (tab, position) -> tab.setText(tabTitles[position])).attach();
    }
}