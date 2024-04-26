package com.example.mealplanner;

import androidx.annotation.NonNull;

import com.example.mealplanner.model.SharedDataHolder;

public class IngredientsController {

    private final SharedDataHolder sharedDataHolder;


    public IngredientsController(@NonNull SharedDataHolder sharedDataHolder) {
        this.sharedDataHolder = sharedDataHolder;
    }
}