package org.jls.mealplanner.ui.ingredients.ingredient.form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.IngredientBaseController;
import org.jls.mealplanner.ingredient.IngredientBaseModel;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;

public class AddNewIngredientActivity extends AppCompatActivity {

    private IngredientCategory ingredientCategory;
    private IngredientBaseController ingredientBaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ingredient);
        ingredientCategory = IngredientCategory.NONE;
        ingredientBaseController = new IngredientBaseController(new IngredientBaseModel());

        retrieveIntentData(getIntent());
    }

    public void addNewIngredient(@NonNull final View view) {
        EditText ingredientNameText = findViewById(R.id.ingredientNameEditText);
        String ingredientName = ingredientNameText.getText().toString();

        if (!ingredientName.isEmpty()) {
            ingredientBaseController.addNewIngredient(ingredientCategory, ingredientName);
        }
    }

    private void retrieveIntentData(final Intent intent) {
        String categoryId = intent.getStringExtra("category");
        if (categoryId != null && !categoryId.isEmpty()) {
            ingredientCategory = IngredientCategory.toIngredientCategory(categoryId);
        }
    }
}
