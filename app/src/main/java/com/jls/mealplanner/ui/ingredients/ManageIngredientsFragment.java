package com.jls.mealplanner.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.IngredientIconViewModel;
import com.jls.mealplanner.model.IngredientViewModel;

import java.util.Objects;


public class ManageIngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_all_ingredients, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.allIngredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addItemDivider(recyclerView);

        IngredientViewModel ingredientsViewModel = new ViewModelProvider(requireActivity()).get(
                IngredientViewModel.class);
        IngredientIconViewModel iconsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientIconViewModel.class);
        IngredientAdapter adapter = new IngredientAdapter(this, ingredientsViewModel,
                                                          iconsViewModel, IngredientVisibility.ALL_INGREDIENTS);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addItemDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                                                                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ingredients_list_divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}