package com.jls.mealplanner.di;

import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.database.ingredienticons.IngredientIconRepository;
import com.jls.mealplanner.database.ingredients.IngredientRepository;
import com.jls.mealplanner.database.recipes.RecipesRepository;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeRepository;

/**
 * Application-scoped dependency container. Owns the database and the repositories, wiring their
 * dependencies together via constructor injection. A single instance is created and held by
 * {@code MealPlannerApplication} for the whole application lifetime.
 */
public class AppContainer {

    public final ApplicationDatabase database;
    public final IngredientRepository ingredientRepository;
    public final IngredientIconRepository ingredientIconRepository;
    public final RecipesRepository recipesRepository;
    public final WeeklyRecipeRepository weeklyRecipeRepository;

    public AppContainer(ApplicationDatabase database) {
        this.database = database;
        this.ingredientRepository = new IngredientRepository(database.ingredientsDao());
        this.ingredientIconRepository = new IngredientIconRepository(database.ingredientsIconDao());
        this.recipesRepository = new RecipesRepository(database.recipesDao());
        this.weeklyRecipeRepository = new WeeklyRecipeRepository(database.weeklyRecipeDao());
    }
}
