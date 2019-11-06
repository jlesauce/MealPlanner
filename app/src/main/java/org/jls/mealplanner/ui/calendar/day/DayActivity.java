package org.jls.mealplanner.ui.calendar.day;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jls.mealplanner.R;

public class DayActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_activity);

        addClickListenerToAddLunchIngredientButton();
    }

    private void addClickListenerToAddLunchIngredientButton() {
        FloatingActionButton addLunchIngredientButton = findViewById(R.id.addLunchIngredientButton);
        addLunchIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
