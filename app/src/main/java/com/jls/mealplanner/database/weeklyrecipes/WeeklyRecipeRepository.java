package com.jls.mealplanner.database.weeklyrecipes;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeeklyRecipeRepository {

    private final ExecutorService executorService;
    private final WeeklyRecipeDao weeklyRecipeDao;

    public WeeklyRecipeRepository(WeeklyRecipeDao weeklyRecipeDao) {
        this.weeklyRecipeDao = weeklyRecipeDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(WeeklyRecipeEntity weeklyRecipe) {
        executorService.execute(() -> weeklyRecipeDao.insert(weeklyRecipe));
    }

    public LiveData<List<WeeklyRecipeEntity>> getRecipesForDay(int year, int weekNumber, int dayOfWeek) {
        return weeklyRecipeDao.getRecipesForDay(year, weekNumber, dayOfWeek);
    }
}