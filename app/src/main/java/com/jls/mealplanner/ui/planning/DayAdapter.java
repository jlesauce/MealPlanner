package com.jls.mealplanner.ui.planning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.WeeklyRecipesViewModel;
import com.jls.mealplanner.ui.PlanningFragment;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {

    private final List<String> daysOfWeek;
    private final WeeklyRecipesViewModel weeklyRecipesViewModel;
    private final PlanningFragment fragment;

    public DayAdapter(PlanningFragment fragment, WeeklyRecipesViewModel weeklyRecipesViewModel) {
        this.fragment = fragment;
        this.daysOfWeek = getDaysOfWeek();
        this.weeklyRecipesViewModel = weeklyRecipesViewModel;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.dayTextView.setText(daysOfWeek.get(position));
        holder.recipesRecyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        holder.recipesRecyclerView.setAdapter(new DayRecipeAdapter(fragment, weeklyRecipesViewModel, position));
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    private static List<String> getDaysOfWeek() {
        Locale locale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] weekdays = dfs.getWeekdays();
        // The first element is an empty string, so we need to skip it
        List<String> days = Arrays.asList(weekdays).subList(1, weekdays.length);
        // Reorder to start with Monday and capitalize the first letter
        return Arrays.asList(
                capitalize(days.get(1)), // Monday
                capitalize(days.get(2)),
                capitalize(days.get(3)),
                capitalize(days.get(4)),
                capitalize(days.get(5)),
                capitalize(days.get(6)),
                capitalize(days.get(0)) // Sunday
        );
    }

    private static String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}