package com.jls.mealplanner.ui.ingredients;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.utils.AssetUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientsViewAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private final String TAG = IngredientsViewAdapter.class.getSimpleName();

    private final IngredientsViewModel ingredientsViewModel;
    private final IngredientListType ingredientsListType;
    private final ArrayList<IngredientEntity> ingredients;
    private final HashMap<String, IngredientIconEntity> icons;

    public IngredientsViewAdapter(Fragment fragment, IngredientsViewModel viewModel,
                                  IngredientIconsViewModel iconsViewModel,
                                  final IngredientListType ingredientListType) {
        this.ingredientsViewModel = viewModel;
        this.ingredientsListType = ingredientListType;
        this.ingredients = new ArrayList<>();
        this.icons = new HashMap<>();


        this.ingredientsViewModel.getAllIngredients().observe(fragment, allIngredients -> {
            IngredientListType v = ingredientsListType;

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
        });

        iconsViewModel.getAllIngredientIcons().observe(fragment, allIcons -> {
            if (allIcons == null) {
                return;
            }

            icons.clear();
            for (IngredientIconEntity icon : allIcons) {
                icons.put(icon.shortName, icon);
            }

            notifyItemRangeInserted(0, icons.size());
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

        holder.addIngredientToGroceryList.setOnClickListener(
                v -> ingredientInGroceryListButtonClicked(holder, ingredient));
        holder.addToMyIngredientsCheckBox.setOnClickListener(
                v -> ingredientInMyIngredientsCheckBoxClicked(holder, ingredient));
    }

    private void ingredientInGroceryListButtonClicked(IngredientViewHolder holder, final IngredientEntity ingredient) {
        int position = holder.getAdapterPosition();
        ingredient.isInGroceryList = !ingredient.isInGroceryList;
        holder.updateIngredientInGroceryListButton(ingredient.isInGroceryList);
        this.ingredientsViewModel.updateIngredient(ingredient);
        notifyItemChanged(position);
    }

    private void ingredientInMyIngredientsCheckBoxClicked(IngredientViewHolder holder,
                                                          final IngredientEntity ingredient) {
        int position = holder.getAdapterPosition();
        ingredient.isPossessed = !ingredient.isPossessed;
        this.ingredientsViewModel.updateIngredient(ingredient);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }
}