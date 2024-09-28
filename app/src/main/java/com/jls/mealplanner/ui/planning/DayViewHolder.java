package com.jls.mealplanner.ui.planning;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;

public class DayViewHolder extends RecyclerView.ViewHolder {
    public TextView dayTextView;
    public RecyclerView recipesRecyclerView;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        dayTextView = itemView.findViewById(R.id.day_text_view);
        recipesRecyclerView = itemView.findViewById(R.id.recipes_recycler_view);
    }
}