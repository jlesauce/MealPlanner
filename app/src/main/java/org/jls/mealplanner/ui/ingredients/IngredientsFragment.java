package org.jls.mealplanner.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ui.ingredients.category.CategoriesViewModel;
import org.jls.mealplanner.ui.ingredients.category.CategoriesViewAdapter;

public class IngredientsFragment extends Fragment {

    private CategoriesViewModel categoriesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        populateIngredientCategoriesInRecyclerView(rootView);

        return rootView;
    }

    private void populateIngredientCategoriesInRecyclerView(@NonNull View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.ingredientCategoriesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CategoriesViewAdapter categoriesAdapter = new CategoriesViewAdapter(categoriesViewModel.getCategoryItemsData().getValue());
        recyclerView.setAdapter(categoriesAdapter);
    }
}
