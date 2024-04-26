package com.example.mealplanner.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.IngredientsController;
import com.example.mealplanner.R;
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.model.SharedDataHolder;

import java.util.ArrayList;


public class ManageIngredientsFragment extends Fragment {

    private final IngredientsController controller;

    public ManageIngredientsFragment(IngredientsController controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_all_ingredients, container, false);
        ArrayList<Ingredient> ingredientList = new ArrayList<>(SharedDataHolder.getInstance().getIngredients().values());

        RecyclerView recyclerView = view.findViewById(R.id.allIngredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addItemDivider(recyclerView);

        IngredientAdapter adapter = new IngredientAdapter(controller, ingredientList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addItemDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ingredients_list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}