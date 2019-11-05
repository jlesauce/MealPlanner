package org.jls.mealplanner.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.jls.mealplanner.R;

public class IngredientsFragment extends Fragment {

    private IngredientsViewModel ingredientsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);
        View root = inflater.inflate(R.layout.ingredients_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        ingredientsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}