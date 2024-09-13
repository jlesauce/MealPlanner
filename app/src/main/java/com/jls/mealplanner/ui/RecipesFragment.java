package com.jls.mealplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jls.mealplanner.R;
import com.jls.mealplanner.ui.recipes.RecipesViewPagerAdapter;

public class RecipesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        String[] tabTitles = new String[]{
                getResources().getString(R.string.my_recipes),
                getResources().getString(R.string.all_recipes),
                getResources().getString(R.string.favorites)};

        TabLayout tabLayout = view.findViewById(R.id.recipesTabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.recipesViewPager);
        RecipesViewPagerAdapter viewPagerAdapter = new RecipesViewPagerAdapter(this,
                                                                               getChildFragmentManager(),
                                                                               getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();

        return view;
    }
}