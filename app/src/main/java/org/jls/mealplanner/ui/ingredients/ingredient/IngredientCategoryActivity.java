package org.jls.mealplanner.ui.ingredients.ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

public class IngredientCategoryActivity extends AppCompatActivity {

    private IngredientsViewModel ingredientsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_category);

        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);

        retrieveIntentData(getIntent());
        updateCategoryLabel();
        populateIngredientsInRecyclerView();
    }

    private void retrieveIntentData(final Intent intent) {
        String categoryId = intent.getStringExtra("category");
        ingredientsViewModel.setIngredientCategory(IngredientCategory.toIngredientCategory(categoryId));
    }

    private void updateCategoryLabel() {
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        IngredientCategory category = ingredientsViewModel.getIngredientCategory();
        categoryTextView.setText(category.displayName());
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
