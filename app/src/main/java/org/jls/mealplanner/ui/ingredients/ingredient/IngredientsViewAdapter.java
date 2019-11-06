package org.jls.mealplanner.ui.ingredients.ingredient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ingredient.IngredientItem;

import java.util.ArrayList;

public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private ArrayList<IngredientItem> ingredientItems;

    public IngredientsViewAdapter(ArrayList<IngredientItem> items) {
        this.ingredientItems = items;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.getIngredientImageView().setImageResource(getIngredientItemAtPosition(position).getImageResource());
        holder.getIngredientNameTextView().setText(getIngredientItemAtPosition(position).getIngredientName());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return ingredientItems.size();
    }

    private IngredientItem getIngredientItemAtPosition(final int position) {
        return ingredientItems.get(position);
    }
}
