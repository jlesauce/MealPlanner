package com.jls.mealplanner.ui.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.jls.mealplanner.model.Ingredient;
import com.jls.mealplanner.model.SharedDataHolder;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final IngredientsController controller;
    private final IngredientVisibility ingredientsVisibility;
    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(final IngredientsController controller, final IngredientVisibility ingredientVisibility) {
        SharedDataHolder sharedDataHolder = SharedDataHolder.getInstance();
        this.controller = controller;
        sharedDataHolder.addObserver(this::updateIngredientsList);
        this.ingredientsVisibility = ingredientVisibility;
        ingredients = controller.getIngredients(ingredientVisibility);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, final int position) {
        Ingredient currentIngredientItem = ingredients.get(position);
        initIngredientItem(holder, currentIngredientItem);

    }

    private void initIngredientItem(@NonNull IngredientViewHolder holder, final Ingredient ingredient) {
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.ingredientIcon.setImageResource(ingredient.getImageResource());
        holder.addToMyIngredientsCheckBox.setChecked(ingredient.isPossessed());

        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList());

        // Add listeners
        holder.addIngredientToGroceryList.setOnClickListener(
                v -> ingredientInGroceryListButtonClicked(holder, ingredient));
        holder.addToMyIngredientsCheckBox.setOnClickListener(
                v -> ingredientInMyIngredientsCheckBoxClicked(holder, ingredient));
    }

    private void ingredientInGroceryListButtonClicked(IngredientViewHolder holder, final Ingredient ingredient) {
        if (ingredient.isInGroceryList()) {
            controller.removeIngredientFromGroceryList(ingredient);
            holder.updateIngredientInGroceryListButton(false);
        } else {
            controller.addIngredientToGroceryList(ingredient);
            holder.updateIngredientInGroceryListButton(true);
        }
    }

    private void ingredientInMyIngredientsCheckBoxClicked(IngredientViewHolder holder, final Ingredient ingredient) {
        if (ingredient.isPossessed()) {
            controller.removeIngredientFromMyIngredients(ingredient);
        } else {
            controller.addIngredientToMyIngredients(ingredient);
        }
    }

    private void updateIngredientsList() {
        this.ingredients = controller.getIngredients(ingredientsVisibility);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return ingredients.size();
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

        public void updateIngredientInGroceryListButton(boolean isInGroceryList) {
            addIngredientToGroceryList.setImageResource(
                    isInGroceryList ? R.drawable.grocery_list_icon_enabled : R.drawable.grocery_list_icon_disabled);
        }
    }
}