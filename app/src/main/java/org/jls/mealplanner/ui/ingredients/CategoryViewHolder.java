package org.jls.mealplanner.ui.ingredients;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jls.mealplanner.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    protected ImageView categoryImage;
    protected TextView categoryName;

    public CategoryViewHolder(View view) {
        super(view);
        categoryImage = itemView.findViewById(R.id.ingredientCategoryIcon);
        categoryName = itemView.findViewById(R.id.ingredientCategoryName);
    }

    public ImageView getCategoryImageView() {
        return categoryImage;
    }

    public TextView getCategoryNameTextView() {
        return categoryName;
    }
}
