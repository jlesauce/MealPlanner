package org.jls.mealplanner.ui.ingredients.ingredient.form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.jls.mealplanner.R;

public class AddNewIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ingredient);
    }

    public void addNewIngredient(@NonNull final View view) {

    }
}
