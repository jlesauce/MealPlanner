package com.jls.mealplanner.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeEntity;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeRepository;

import java.util.Calendar;
import java.util.List;

public class WeeklyRecipesViewModel extends AndroidViewModel {

    private final WeeklyRecipeRepository repository;
    private final MutableLiveData<Integer> selectedWeekNumber;

    public WeeklyRecipesViewModel(@NonNull Application application) {
        super(application);
        repository = new WeeklyRecipeRepository();
        selectedWeekNumber = new MutableLiveData<>(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
    }

    public LiveData<Integer> getSelectedWeekNumber() {
        return selectedWeekNumber;
    }

    public void setSelectedWeekNumber(int weekNumber) {
        selectedWeekNumber.setValue(weekNumber);
    }

    public LiveData<List<WeeklyRecipeEntity>> getRecipeForDay(int weekNumber, int dayOfWeek) {
        return repository.getRecipesForDay(weekNumber, dayOfWeek);
    }

    public void insert(WeeklyRecipeEntity weeklyRecipe) {
        repository.insert(weeklyRecipe);
    }
}