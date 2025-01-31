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
    private final MutableLiveData<Integer> selectedYear;

    public WeeklyRecipesViewModel(@NonNull Application application) {
        super(application);
        repository = new WeeklyRecipeRepository();
        selectedWeekNumber = new MutableLiveData<>(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        selectedYear = new MutableLiveData<>(Calendar.getInstance().get(Calendar.YEAR));
    }

    public LiveData<Integer> getSelectedWeekNumber() {
        return selectedWeekNumber;
    }

    public void setSelectedWeekNumber(int weekNumber) {
        selectedWeekNumber.setValue(weekNumber);
    }

    public MutableLiveData<Integer> getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int year) {
        selectedYear.setValue(year);
    }

    public LiveData<List<WeeklyRecipeEntity>> getRecipeForDay(int year, int weekNumber, int dayOfWeek) {
        return repository.getRecipesForDay(year, weekNumber, dayOfWeek);
    }

    public void insert(WeeklyRecipeEntity weeklyRecipe) {
        repository.insert(weeklyRecipe);
    }
}