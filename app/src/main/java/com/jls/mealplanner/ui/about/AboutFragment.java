package com.jls.mealplanner.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jls.mealplanner.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel model = new ViewModelProvider(this).get(AboutViewModel.class);
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final TextView textView = binding.textAbout;
        model.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}