package org.jls.mealplanner.ui.ingredients.ingredient;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;

public class IngredientCategoryActivity extends AppCompatActivity {

    private IngredientsViewModel ingredientsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_category);

        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);
        populateIngredientsInRecyclerView();
    }

    private void populateIngredientsInRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.ingredientsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        IngredientsViewAdapter ingredientsAdapter = new IngredientsViewAdapter(ingredientsViewModel.getIngredientsItemsData().getValue());
        recyclerView.setAdapter(ingredientsAdapter);
    }
}
