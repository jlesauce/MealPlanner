package com.jls.mealplanner.ui.recipes;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.ui.ingredients.IngredientViewHolder;
import com.jls.mealplanner.utils.AssetUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeIngredientsViewAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private final IngredientsViewModel ingredientsViewModel;
    private final HashMap<String, IngredientIconEntity> icons;
    private final ArrayList<String> ingredientNames;
    private final Fragment fragment;

    public RecipeIngredientsViewAdapter(Fragment fragment, IngredientsViewModel viewModel,
                                        IngredientIconsViewModel iconsViewModel,
                                        ArrayList<String> ingredientNames) {
        this.fragment = fragment;
        this.ingredientsViewModel = viewModel;
        this.icons = new HashMap<>();
        this.ingredientNames = ingredientNames;


        iconsViewModel.getAllIngredientIcons().observe(fragment, allIcons -> {
            if (allIcons == null) {
                return;
            }

            icons.clear();
            for (IngredientIconEntity icon : allIcons) {
                icons.put(icon.shortName, icon);
            }

            notifyItemRangeInserted(0, ingredientNames.size());
        });
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, final int position) {
        String currentIngredientName = ingredientNames.get(position);
        ingredientsViewModel.searchIngredient(currentIngredientName)
                .observe(fragment.getViewLifecycleOwner(), ingredientEntity -> {
                    onSearchIngredientResultReceived(holder, ingredientEntity, currentIngredientName);
                });
    }

    private void onSearchIngredientResultReceived(@NonNull IngredientViewHolder holder,
                                                  IngredientEntity ingredientEntity, String ingredientName) {
        int position = holder.getAdapterPosition();

        if (ingredientEntity != null) {
            updateViewWithIngredientEntity(ingredientEntity, holder);
        } else {
            updateViewWhenIngredientNotFound(holder, ingredientName);
        }
        notifyItemChanged(position);
    }

    private void updateViewWithIngredientEntity(IngredientEntity ingredient, IngredientViewHolder holder) {
        holder.ingredientName.setText(ingredient.name);
        holder.addToMyIngredientsCheckBox.setChecked(ingredient.isPossessed);
        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);

        IngredientIconEntity iconEntity = icons.get(ingredient.iconId);
        if (iconEntity != null) {
            Bitmap bitmapIcon = AssetUtils.getIconFromAssets(holder.ingredientIcon.getContext(),
                                                             iconEntity.iconPath);
            holder.ingredientIcon.setImageBitmap(bitmapIcon);
        } else {
            holder.ingredientIcon.setImageResource(R.drawable.ingredients_icon);
        }

        holder.addIngredientToGroceryList.setOnClickListener(
                v -> ingredientInGroceryListButtonClicked(holder, ingredient));
        holder.addToMyIngredientsCheckBox.setOnClickListener(
                v -> ingredientInMyIngredientsCheckBoxClicked(holder, ingredient));
    }

    private static void updateViewWhenIngredientNotFound(IngredientViewHolder holder, String ingredientName) {
        holder.ingredientIcon.setImageResource(R.drawable.ingredients_icon);
        holder.ingredientName.setText(ingredientName);
        holder.addIngredientToGroceryList.setVisibility(View.GONE);
        holder.addToMyIngredientsCheckBox.setVisibility(View.GONE);
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
    }

    private void ingredientInGroceryListButtonClicked(IngredientViewHolder holder, final IngredientEntity ingredient) {
        ingredient.isInGroceryList = !ingredient.isInGroceryList;
        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);
        this.ingredientsViewModel.updateIngredient(ingredient);
        notifyItemChanged(holder.getAdapterPosition());
    }

    private void ingredientInMyIngredientsCheckBoxClicked(IngredientViewHolder holder,
                                                          final IngredientEntity ingredient) {
        ingredient.isPossessed = !ingredient.isPossessed;
        this.ingredientsViewModel.updateIngredient(ingredient);
        notifyItemChanged(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return ingredientNames == null ? 0 : ingredientNames.size();
    }
}