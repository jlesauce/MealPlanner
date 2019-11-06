package org.jls.mealplanner.ui.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;

import java.util.ArrayList;

public class IngredientCategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private ArrayList<CategoryItem> categoryIems;

    public IngredientCategoriesAdapter(ArrayList<CategoryItem> items) {
        this.categoryIems = items;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.getCategoryImageView().setImageResource(getCategoryItemAtPosition(position).getImageResource());
        holder.getCategoryNameTextView().setText(getCategoryItemAtPosition(position).getCategoryName());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categoryIems.size();
    }

    private CategoryItem getCategoryItemAtPosition(final int position) {
        return categoryIems.get(position);
    }
}
