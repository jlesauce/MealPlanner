package com.jls.mealplanner.ui.recipes;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.RecipeViewModel;
import com.jls.mealplanner.ui.TabFragment;


public class RecipesTabFragment extends TabFragment {

    private final String TAG = RecipesTabFragment.class.getSimpleName();
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
        Log.i(TAG, "Creating " + TAG);
        RecipeViewModel recipesViewModel = new ViewModelProvider(requireActivity())
                .get(RecipeViewModel.class);

        RecipesFragment parentFragment = (RecipesFragment) getParentFragment();
        if (parentFragment == null) {
            throw new IllegalStateException("The parent fragment is null.");
        }

        RecipesViewAdapter adapter = new RecipesViewAdapter(parentFragment, this, this.listType, recipesViewModel);
        recyclerView.setAdapter(adapter);
    }
}