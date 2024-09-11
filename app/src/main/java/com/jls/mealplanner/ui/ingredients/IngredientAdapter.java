package com.jls.mealplanner.ui.ingredients;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.model.IngredientIconViewModel;
import com.jls.mealplanner.model.IngredientViewModel;
import com.jls.mealplanner.utils.AssetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final IngredientViewModel ingredientsViewModel;
    private final IngredientListType ingredientsVisibility;
    private final ArrayList<IngredientEntity> ingredients;
    private final HashMap<String, IngredientIconEntity> icons;

    private final String TAG = IngredientAdapter.class.getSimpleName();

    public IngredientAdapter(Fragment fragment, IngredientViewModel viewModel, IngredientIconViewModel iconsViewModel,
                             final IngredientListType ingredientListType) {
        this.ingredientsViewModel = viewModel;
        this.ingredientsVisibility = ingredientListType;
        this.ingredients = new ArrayList<>();
        this.icons = new HashMap<>();


        this.ingredientsViewModel.getAllIngredients().observe(fragment, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(@Nullable final List<IngredientEntity> allIngredients) {
                IngredientListType v = ingredientsVisibility;

                if (allIngredients == null) {
                    return;
                }

                ingredients.clear();
                for (IngredientEntity ingredient : allIngredients) {
                    if (v == IngredientListType.MY_STOCK && ingredient.isPossessed) {
                        ingredients.add(ingredient);
                    } else if (v == IngredientListType.MY_GROCERY_LIST && ingredient.isInGroceryList) {
                        ingredients.add(ingredient);
                    } else if (v == IngredientListType.ALL_INGREDIENTS) {
                        ingredients.add(ingredient);
                    }
                }

                notifyDataSetChanged();
            }
        });

        iconsViewModel.getAllIngredientIcons().observe(fragment, allIcons -> {
            if (allIcons == null) {
                return;
            }

            icons.clear();
            for (IngredientIconEntity icon : allIcons) {
                icons.put(icon.shortName, icon);
            }

            notifyDataSetChanged();
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
        IngredientEntity currentIngredient = ingredients.get(position);
        initIngredientItem(holder, currentIngredient);
    }

    private void initIngredientItem(@NonNull IngredientViewHolder holder, final IngredientEntity ingredient) {
        holder.ingredientName.setText(ingredient.name);
        holder.addToMyIngredientsCheckBox.setChecked(ingredient.isPossessed);

        IngredientIconEntity iconEntity = icons.get(ingredient.iconId);
        if (iconEntity != null) {
            Bitmap bitmapIcon = AssetUtils.getIconFromAssets(holder.ingredientIcon.getContext(), iconEntity.iconPath);
            holder.ingredientIcon.setImageBitmap(bitmapIcon);
        } else {
            Log.e(TAG, "Icon not found for ingredient: " + ingredient.name + " with iconId: " + ingredient.iconId);
            holder.ingredientIcon.setImageResource(R.drawable.ingredients_icon);
        }

        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);

        // Add listeners
        holder.addIngredientToGroceryList.setOnClickListener(
                v -> ingredientInGroceryListButtonClicked(holder, ingredient));
        holder.addToMyIngredientsCheckBox.setOnClickListener(
                v -> ingredientInMyIngredientsCheckBoxClicked(holder, ingredient));
    }

    private void ingredientInGroceryListButtonClicked(IngredientViewHolder holder, final IngredientEntity ingredient) {
        if (ingredient.isInGroceryList) {
            ingredient.isInGroceryList = false;
            holder.updateIngredientInGroceryListButton(false);
        } else {
            ingredient.isInGroceryList = true;
            holder.updateIngredientInGroceryListButton(true);
        }
        this.ingredientsViewModel.updateIngredient(ingredient);
    }

    private void ingredientInMyIngredientsCheckBoxClicked(IngredientViewHolder holder,
                                                          final IngredientEntity ingredient) {
        ingredient.isPossessed = !ingredient.isPossessed;
        this.ingredientsViewModel.updateIngredient(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
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