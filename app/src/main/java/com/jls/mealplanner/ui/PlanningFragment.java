package com.jls.mealplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.WeeklyRecipesViewModel;
import com.jls.mealplanner.ui.planning.DayAdapter;

import java.util.Calendar;

public class PlanningFragment extends Fragment {

    private WeeklyRecipesViewModel weeklyRecipesViewModel;
    private TextView currentWeekText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning, container, false);
        currentWeekText = view.findViewById(R.id.current_week_text);

        weeklyRecipesViewModel = new ViewModelProvider(requireActivity())
                .get(WeeklyRecipesViewModel.class);

        weeklyRecipesViewModel.getSelectedWeekNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer weekNumber) {
                updateWeekText(weekNumber);
            }
        });

        createListOfWeekDays(view);
        addWeekButtonsListeners(view);

        return view;
    }

    private void createListOfWeekDays(View view) {
        DayAdapter dayAdapter = new DayAdapter(this, weeklyRecipesViewModel);
        int numOfColumns = 2;
        RecyclerView daysRecyclerView = view.findViewById(R.id.planning_days_recycler_view);
        daysRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), numOfColumns));
        daysRecyclerView.setAdapter(dayAdapter);
    }

    private void addWeekButtonsListeners(View view) {
        Button prevWeekButton = view.findViewById(R.id.previous_week_button);
        Button nextWeekButton = view.findViewById(R.id.next_week_button);
        prevWeekButton.setOnClickListener(v -> navigateToPreviousWeek());
        nextWeekButton.setOnClickListener(v -> navigateToNextWeek());
    }

    private void navigateToPreviousWeek() {
        Integer currentWeek = weeklyRecipesViewModel.getSelectedWeekNumber().getValue();
        if (currentWeek != null && currentWeek > 1) {
            weeklyRecipesViewModel.setSelectedWeekNumber(currentWeek - 1);
        }
    }

    private void navigateToNextWeek() {
        Integer currentWeek = weeklyRecipesViewModel.getSelectedWeekNumber().getValue();
        if (currentWeek != null && currentWeek < Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR)) {
            weeklyRecipesViewModel.setSelectedWeekNumber(currentWeek + 1);
        }
    }

    private void updateWeekText(int weekNumber) {
        String text = getString(R.string.week) + " " + weekNumber;
        currentWeekText.setText(text);
    }
}