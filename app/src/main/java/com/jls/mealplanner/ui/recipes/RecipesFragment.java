package com.jls.mealplanner.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jls.mealplanner.R;
import com.jls.mealplanner.databinding.FragmentRecipesBinding;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] tabTitles = new String[]{
                getResources().getString(R.string.my_recipes),
                getResources().getString(R.string.all_recipes),
                getResources().getString(R.string.favorites)};

        TabLayout tabLayout = view.findViewById(R.id.recipesTabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.recipesViewPager);
        RecipesViewPagerAdapter viewPagerAdapter = new RecipesViewPagerAdapter(getChildFragmentManager(),
                                                                               getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}