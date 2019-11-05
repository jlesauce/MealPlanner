package org.jls.mealplanner.ui.calendar.day;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import org.jls.mealplanner.R;

public class DayActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_activity);
        DayModel viewModel = ViewModelProviders.of(this).get(DayModel.class);

        final TextView lunchTextView = findViewById(R.id.lunchTextView);
        lunchTextView.setText(viewModel.getLunchLabel().getValue());

        final TextView dinnerTextView = findViewById(R.id.dinnerTextView);
        dinnerTextView.setText(viewModel.getDinnerLabel().getValue());
    }
}
