package com.jls.mealplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jls.mealplanner.R;

import java.util.Objects;


public abstract class TabFragment extends Fragment {

    private final int tabLayout;
    private final int recyclerViewId;

    public TabFragment(@LayoutRes int tabLayout, @IdRes int recyclerViewId) {
        this.tabLayout = tabLayout;
        this.recyclerViewId = recyclerViewId;
    }

    abstract protected void onCustomCreateView(RecyclerView recyclerView);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.tabLayout, container, false);

        RecyclerView recyclerView = view.findViewById(this.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addItemDivider(recyclerView);
        onCustomCreateView(recyclerView);

        return view;
    }

    private void addItemDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                                                                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(requireContext(), R.drawable.item_list_divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}