package com.jls.mealplanner.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.jls.mealplanner.R;

import java.util.HashSet;
import java.util.Set;

public class SearchableFragment extends Fragment {

    private final String TAG = SearchableFragment.class.getSimpleName();

    private FragmentActivity activity;
    private MenuProvider menuProvider;
    private Set<OnUserSearchChangeListener> onUserSearchChangeListeners;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onUserSearchChangeListeners = new HashSet<>();

        activity = getActivity();
        if (activity != null) {
            addFragmentMenuProvider(activity);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        removeFragmentMenuProvider();
        super.onDestroyView();
    }

    public void addOnQueryTextChangeCallback(OnUserSearchChangeListener onUserSearchChangeListener) {
        onUserSearchChangeListeners.add(onUserSearchChangeListener);
    }

    protected void addFragmentMenuProvider(FragmentActivity activity) {
        Log.d(TAG, "Adding menu provider to fragment");
        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.top_menu_search, menu);
                addSearchQueryTextListener(menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        };
        activity.addMenuProvider(menuProvider);
    }

    protected void addSearchQueryTextListener(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.searchItemMenu);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView == null) {
            Log.e(TAG, "Search view is null");
            return;
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                String userSearchedText = text.trim().toLowerCase();
                Log.d(TAG, "User searched text: " + userSearchedText);
                for (OnUserSearchChangeListener listener : onUserSearchChangeListeners) {
                    listener.onUserSearchText(userSearchedText);
                }
                return true;
            }
        });
    }

    protected void removeFragmentMenuProvider() {
        if (activity != null && menuProvider != null) {
            Log.d(TAG, "Removing menu provider from fragment");
            activity.removeMenuProvider(menuProvider);
            activity.invalidateOptionsMenu();
        }
    }
}
