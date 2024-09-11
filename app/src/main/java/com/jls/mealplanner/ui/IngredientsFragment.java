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
import com.jls.mealplanner.ui.ingredients.IngredientsViewPagerAdapter;

public class IngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        String[] tabTitles = new String[]{
                getResources().getString(R.string.my_ingredients),
                getResources().getString(R.string.manage_ingredients),
                getResources().getString(R.string.grocery_list)};

        TabLayout tabLayout = view.findViewById(R.id.ingredientsTabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.ingredientsViewPager);
        IngredientsViewPagerAdapter viewPagerAdapter = new IngredientsViewPagerAdapter(getChildFragmentManager(),
                                                                                       getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();

        return view;
    }
}
