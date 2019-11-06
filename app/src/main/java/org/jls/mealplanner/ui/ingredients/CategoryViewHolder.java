package org.jls.mealplanner.ui.ingredients;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public CategoryViewHolder(TextView v) {
        super(v);
        textView = v;
    }

    public TextView getTextView() {
        return textView;
    }
}
