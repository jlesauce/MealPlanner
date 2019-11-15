package org.jls.mealplanner.ui.ingredients.ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.IngredientBaseModel;
import org.jls.mealplanner.model.SharedDataHolder;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;
import org.jls.mealplanner.ui.ingredients.ingredient.form.AddNewIngredientActivity;

public class IngredientCategoryActivity extends AppCompatActivity {

    private static final int ADD_NEW_INGREDIENT_REQUEST = 1;

    private IngredientsViewModel ingredientsViewModel;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_category);
        IngredientBaseModel ingredientBaseModel = SharedDataHolder.getInstance().getIngredientBaseModel();

        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);
        retrieveIntentData(getIntent());
        ingredientsViewModel.setIngredientsItemsData(ingredientBaseModel.getListOfIngredientsForCategory(ingredientsViewModel.getIngredientCategory()));

        updateCategoryLabel();
        populateIngredientsInRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_NEW_INGREDIENT_REQUEST) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void retrieveIntentData(final Intent intent) {
        String categoryId = intent.getStringExtra("category");
        if (categoryId != null && !categoryId.isEmpty()) {
            ingredientsViewModel.setIngredientCategory(IngredientCategory.toIngredientCategory(categoryId));
        }
    }

    private void updateCategoryLabel() {
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        IngredientCategory category = ingredientsViewModel.getIngredientCategory();
        categoryTextView.setText(category.displayName());
    }

    private void populateIngredientsInRecyclerView() {
        recyclerView = findViewById(R.id.ingredientsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        IngredientsViewAdapter ingredientsAdapter = new IngredientsViewAdapter(ingredientsViewModel.getIngredientsItemsData().getValue());
        recyclerView.setAdapter(ingredientsAdapter);
    }

    public void startAddIngredientActivity(@NonNull final View view) {
        Intent intent = new Intent(this, AddNewIngredientActivity.class);
        intent.putExtra("category", ingredientsViewModel.getIngredientCategory().id());
        startActivityForResult(intent, ADD_NEW_INGREDIENT_REQUEST);
    }
}
