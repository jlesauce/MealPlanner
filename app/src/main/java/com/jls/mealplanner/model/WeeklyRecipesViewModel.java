package com.jls.mealplanner.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeEntity;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeRepository;

import java.util.Calendar;
import java.util.List;

public class WeeklyRecipesViewModel extends ViewModel {

    private final WeeklyRecipeRepository repository;
    private final MutableLiveData<Integer> selectedWeekNumber;
    private final MutableLiveData<Integer> selectedYear;

    public WeeklyRecipesViewModel(WeeklyRecipeRepository repository) {
        this.repository = repository;
        this.selectedWeekNumber = new MutableLiveData<>(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        this.selectedYear = new MutableLiveData<>(Calendar.getInstance().get(Calendar.YEAR));
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