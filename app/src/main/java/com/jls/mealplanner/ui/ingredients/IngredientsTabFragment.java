package com.jls.mealplanner.ui.ingredients;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.IngredientIconsViewModel;
import com.jls.mealplanner.model.IngredientsViewModel;
import com.jls.mealplanner.ui.TabFragment;


public class IngredientsTabFragment extends TabFragment {

    private final String TAG = IngredientsTabFragment.class.getSimpleName();
    private final IngredientListType listType;

    public IngredientsTabFragment(IngredientListType listType) {
        super(R.layout.tab_ingredients, R.id.ingredientsRecyclerView);
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

        IngredientsViewModel ingredientsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientsViewModel.class);
        IngredientIconsViewModel iconsViewModel = new ViewModelProvider(requireActivity())
                .get(IngredientIconsViewModel.class);

        IngredientsFragment parentFragment = (IngredientsFragment) getParentFragment();
        if (parentFragment == null) {
            throw new IllegalStateException("The parent fragment is null.");
        }

        IngredientsViewAdapter adapter = new IngredientsViewAdapter(parentFragment, this, ingredientsViewModel,
                                                                    iconsViewModel, this.listType);
        recyclerView.setAdapter(adapter);
    }
}