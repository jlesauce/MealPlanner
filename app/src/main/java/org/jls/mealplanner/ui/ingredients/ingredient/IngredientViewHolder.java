package org.jls.mealplanner.ui.ingredients.ingredient;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    protected ImageView ingredientImage;
    protected TextView ingredientName;

    public IngredientViewHolder(View view) {
        super(view);
        ingredientImage = itemView.findViewById(R.id.ingredientIcon);
        ingredientName = itemView.findViewById(R.id.ingredientName);
    }

    public ImageView getIngredientImageView() {
        return ingredientImage;
    }

    public TextView getIngredientNameTextView() {
        return ingredientName;
    }
}
