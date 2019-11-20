package org.jls.mealplanner.ui.ingredients.ingredient;

import android.content.Intent;
import android.content.res.AssetManager;
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
import org.jls.mealplanner.ingredient.Ingredient;
import org.jls.mealplanner.ingredient.IngredientBaseModel;
import org.jls.mealplanner.model.SharedDataHolder;
import org.jls.mealplanner.ui.ingredients.category.IngredientCategory;
import org.jls.mealplanner.ui.ingredients.ingredient.form.AddNewIngredientActivity;
import org.jls.mealplanner.util.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IngredientCategoryActivity extends AppCompatActivity {

    private static final int ADD_NEW_INGREDIENT_REQUEST = 1;

    private static final String INGREDIENT_JASON_FILE_BASENAME = "ingredients" + File.separator;

    private IngredientsViewModel ingredientsViewModel;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_category);

        updateDataModels();
        updateCategoryLabel();
        populateIngredientsInRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_NEW_INGREDIENT_REQUEST) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void updateDataModels() {
        IngredientBaseModel sharedIngredientsModel = SharedDataHolder.getInstance().getIngredientBaseModel();
        ingredientsViewModel = ViewModelProviders.of(this).get(IngredientsViewModel.class);
        retrieveIntentData(getIntent());

        ArrayList<Ingredient> ingredients = sharedIngredientsModel.getListOfIngredientsForCategory(
                ingredientsViewModel.getIngredientCategory());
        ingredientsViewModel.setIngredientsItemsData(ingredients);


        retrieveIngredientsFromAssets(getAssets(), ingredients);
    }

    private void retrieveIngredientsFromAssets(final @NonNull AssetManager assets,
                                               final @NonNull ArrayList<Ingredient> ingredients) {
        IngredientCategory category = ingredientsViewModel.getIngredientCategory();
        String ingredientJsonFilename = INGREDIENT_JASON_FILE_BASENAME + category.id() + ".json";

        try {
            InputStreamReader streamReader = new InputStreamReader(
                    assets.open(ingredientJsonFilename));
            JsonParser parser = new JsonParser(streamReader);
            JSONObject jsonObject = parser.parse();

            JSONArray proteinsArray = jsonObject.getJSONArray(category.id());
            for (int ingredientIndex = 0; ingredientIndex < proteinsArray.length(); ++ingredientIndex) {
                JSONObject ingredientObject = proteinsArray.getJSONObject(ingredientIndex);

                Ingredient ingredient = new Ingredient(ingredientObject.getString("label"),
                                                       R.drawable.icon_default_ingredient);
                ingredients.add(ingredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void retrieveIntentData(final Intent intent) {
        String categoryId = intent.getStringExtra("category");
        if (categoryId != null && !categoryId.isEmpty()) {
            ingredientsViewModel.setIngredientCategory(
                    IngredientCategory.toIngredientCategory(categoryId));
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
                                                                    LinearLayoutManager.VERTICAL,
                                                                    false);
        recyclerView.setLayoutManager(layoutManager);

        IngredientsViewAdapter ingredientsAdapter = new IngredientsViewAdapter(
                ingredientsViewModel.getIngredientsItemsData().getValue());
        recyclerView.setAdapter(ingredientsAdapter);
    }

    public void startAddIngredientActivity(@NonNull final View view) {
        Intent intent = new Intent(this, AddNewIngredientActivity.class);
        intent.putExtra("category", ingredientsViewModel.getIngredientCategory().id());
        startActivityForResult(intent, ADD_NEW_INGREDIENT_REQUEST);
    }
}
