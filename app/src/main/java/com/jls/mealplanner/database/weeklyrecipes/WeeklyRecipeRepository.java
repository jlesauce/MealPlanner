package com.jls.mealplanner.database.weeklyrecipes;

import androidx.lifecycle.LiveData;

import com.jls.mealplanner.database.ApplicationDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeeklyRecipeRepository {

    private final ExecutorService executorService;
    private final WeeklyRecipeDao weeklyRecipeDao;

    public WeeklyRecipeRepository() {
        ApplicationDatabase db = ApplicationDatabase.getInstance();
        weeklyRecipeDao = db.weeklyRecipeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(WeeklyRecipeEntity weeklyRecipe) {
        executorService.execute(() -> weeklyRecipeDao.insert(weeklyRecipe));
    }

    public LiveData<List<WeeklyRecipeEntity>> getRecipesForDay(int weekNumber, int dayOfWeek) {
        return weeklyRecipeDao.getRecipesForDay(weekNumber, dayOfWeek);
    }
}