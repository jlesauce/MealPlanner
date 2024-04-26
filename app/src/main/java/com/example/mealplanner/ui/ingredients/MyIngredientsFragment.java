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

import com.example.mealplanner.R;


public class MyIngredientsFragment extends Fragment {

    private final IngredientsController controller;

    public MyIngredientsFragment(IngredientsController controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_my_ingredients, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.myIngredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addItemDivider(recyclerView);

        IngredientAdapter adapter = new IngredientAdapter(controller, IngredientVisibility.MY_STOCK);
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