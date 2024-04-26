package com.example.mealplanner.ui.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.IngredientsController;
import com.example.mealplanner.R;
import com.example.mealplanner.model.Ingredient;
import com.example.mealplanner.model.SharedDataHolder;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> ingredientsList;
    private final IngredientsController controller;

    public IngredientAdapter(IngredientsController controller, ArrayList<Ingredient> ingredientsList) {
        this.controller = controller;
        this.ingredientsList = ingredientsList;
        SharedDataHolder.getInstance().addObserver(this::notifyDataSetChanged);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient currentIngredientItem = ingredientsList.get(position);

        holder.ingredientName.setText(currentIngredientItem.getIngredientName());
        holder.ingredientIcon.setImageResource(currentIngredientItem.getImageResource());
        holder.addToMyIngredientsCheckBox.setChecked(currentIngredientItem.isPossessed());

        holder.addIngredientToGroceryList.setOnClickListener(v -> {
            if (!currentIngredientItem.isInGroceryList()) {
                SharedDataHolder.getInstance().addIngredientToGroceryList(currentIngredientItem.getIngredientName());
            } else {
                SharedDataHolder.getInstance().removeIngredientFromGroceryList(
                        currentIngredientItem.getIngredientName());
            }
        });

        holder.addToMyIngredientsCheckBox.setOnClickListener(v -> {
            if (currentIngredientItem.isPossessed()) {
                SharedDataHolder.getInstance().removeIngredientFromMyStock(currentIngredientItem.getIngredientName());
            } else {
                SharedDataHolder.getInstance().addIngredientToMyStock(currentIngredientItem.getIngredientName());
            }
        });
    }

    private void updateIngredientsList() {
        this.ingredientsList = SharedDataHolder.getInstance().getMyIngredients();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientIcon;
        TextView ingredientName;
        ImageButton addIngredientToGroceryList;
        CheckBox addToMyIngredientsCheckBox;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientIcon = itemView.findViewById(R.id.ingredientIcon);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            addIngredientToGroceryList = itemView.findViewById(R.id.addIngredientToGroceryList);
            addToMyIngredientsCheckBox = itemView.findViewById(R.id.addToMyIngredientsCheckBox);
        }
    }
}