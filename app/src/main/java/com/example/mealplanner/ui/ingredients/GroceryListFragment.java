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


public class GroceryListFragment extends Fragment {

    private final IngredientsController controller;

    public GroceryListFragment(IngredientsController controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_groceries_list, container, false);
        ArrayList<Ingredient> ingredientList = SharedDataHolder.getInstance().getGroceryList();

        RecyclerView recyclerView = view.findViewById(R.id.groceryListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addItemDivider(recyclerView);

        IngredientAdapter adapter = new IngredientAdapter(controller, ingredientList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addItemDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ingredients_list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}