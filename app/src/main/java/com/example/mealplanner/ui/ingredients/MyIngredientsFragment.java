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
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.model.SharedDataHolder;

import java.util.ArrayList;


public class MyIngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_my_ingredients, container, false);

        ArrayList<Ingredient> ingredientList = new ArrayList<>(SharedDataHolder.getInstance().getIngredients().values());

        RecyclerView recyclerView = view.findViewById(R.id.myIngredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Add item divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ingredients_list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        IngredientAdapter adapter = new IngredientAdapter(ingredientList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}