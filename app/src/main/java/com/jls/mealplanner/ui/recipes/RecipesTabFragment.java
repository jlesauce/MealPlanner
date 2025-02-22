package com.jls.mealplanner.ui.recipes;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.ui.TabFragment;


public class RecipesTabFragment extends TabFragment {

    private final RecipeListType listType;

    public RecipesTabFragment(RecipeListType listType) {
        super(R.layout.tab_recipes, R.id.recipesRecyclerView);
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
        RecipesViewAdapter adapter = new RecipesViewAdapter(this, this.listType, recipesViewModel);
        recyclerView.setAdapter(adapter);
    }
}