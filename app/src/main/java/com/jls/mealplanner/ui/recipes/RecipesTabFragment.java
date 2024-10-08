package com.jls.mealplanner.ui.recipes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.ui.TabFragment;


public class RecipesTabFragment extends TabFragment {

    private final RecipeListType listType;
    private final Fragment topFragment;

    public RecipesTabFragment(@NonNull Fragment topFragment, RecipeListType listType) {
        super(R.layout.tab_recipes, R.id.recipesRecyclerView);
        this.topFragment = topFragment;
        this.listType = listType;
    }

    /**
     * Customizes the view of the fragment.
     * <p>
     * This method is called after the RecyclerView is created and the adapter is set in the
     * method {@link TabFragment#onCreateView}.
     *
     * @param recyclerView the RecyclerView of the fragment.
     */
    protected void onCustomCreateView(RecyclerView recyclerView) {
        RecipeViewModel recipesViewModel = new ViewModelProvider(requireActivity())
                .get(RecipeViewModel.class);
        IngredientsViewModel ingredientsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientsViewModel.class);
        IngredientIconsViewModel iconsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientIconsViewModel.class);

        RecipesViewAdapter adapter = new RecipesViewAdapter(topFragment, this, this.listType, recipesViewModel,
                                                            ingredientsViewModel, iconsViewModel);
        recyclerView.setAdapter(adapter);
    }
}