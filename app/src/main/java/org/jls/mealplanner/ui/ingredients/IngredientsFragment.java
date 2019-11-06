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

public class IngredientsFragment extends Fragment {

    private IngredientsViewModel ingredientsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);
        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        populateIngredientCategoriesInRecyclerView(rootView);

        return rootView;
    }

    private void populateIngredientCategoriesInRecyclerView(@NonNull View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.ingredientCategoriesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        IngredientCategoriesAdapter categoriesAdapter = new IngredientCategoriesAdapter(ingredientsViewModel.getCategoryItemsData().getValue());
        recyclerView.setAdapter(categoriesAdapter);
    }
}
