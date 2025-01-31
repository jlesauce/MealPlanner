package com.jls.mealplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jls.mealplanner.database.ingredienticons.IngredientIconEntity;
import com.jls.mealplanner.database.ingredienticons.IngredientIconsDao;
import com.jls.mealplanner.database.ingredients.IngredientEntity;
import com.jls.mealplanner.database.ingredients.IngredientsDao;
import com.jls.mealplanner.database.recipes.RecipeEntity;
import com.jls.mealplanner.database.recipes.RecipesDao;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeDao;
import com.jls.mealplanner.database.weeklyrecipes.WeeklyRecipeEntity;
import com.jls.mealplanner.utils.AssetUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {IngredientEntity.class, IngredientIconEntity.class, RecipeEntity.class, WeeklyRecipeEntity.class},
        version = 9, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static final String DEFAULT_DB_NAME = "default_database.db";
    private static final String DATABASE_NAME = "application_database.db";
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final String TAG = ApplicationDatabase.class.getSimpleName();

    private static ApplicationDatabase INSTANCE = null;


    public static synchronized void initializeInstance(final Context context,
                                                       boolean forceDbReinstall) throws IOException {
        if (ApplicationDatabase.INSTANCE == null) {
            Log.d(TAG, "Initializing " + ApplicationDatabase.class.getSimpleName());
            if (forceDbReinstall) {
                deleteDatabase(context);
            }
            if (!databaseExists(context)) {
                copyDefaultDatabaseToApplicationStorage(context);
            }

            ApplicationDatabase.INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                                ApplicationDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // FIXME Remove this line when app deployed
                    .build();
        } else {
            throw new RuntimeException(ApplicationDatabase.class.getSimpleName() +
                                               " is already initialized, call getInstance() method instead.");
        }
    }

    public static ApplicationDatabase getInstance() {
        if (ApplicationDatabase.INSTANCE == null) {
            throw new IllegalStateException(ApplicationDatabase.class.getSimpleName() +
                                                    " is not initialized, call initializeInstance(..) method first.");
        }
        return ApplicationDatabase.INSTANCE;
    }

    public abstract IngredientsDao ingredientsDao();

    public abstract IngredientIconsDao ingredientsIconDao();

    public abstract RecipesDao recipesDao();

    public abstract WeeklyRecipeDao weeklyRecipeDao();

    public static void copyDefaultDatabaseToApplicationStorage(final Context context) throws IOException {
        File databaseFile = context.getDatabasePath(DATABASE_NAME);

        if (databaseFile.exists()) {
            throw new RuntimeException("Database file already exists in application storage");
        }
        if (!AssetUtils.fileExistInAssets(context, DEFAULT_DB_NAME)) {
            throw new FileNotFoundException("Default database file not found in assets");
        }

        String databaseStrPath = context.getDatabasePath(DATABASE_NAME).getPath();
        Path databasePath = Paths.get(databaseStrPath);
        writeDefaultDatabaseToDatabasePath(context, databasePath);

        if (!databasePath.toFile().exists()) {
            throw new RuntimeException("Failed to copy default database to application storage");
        } else {
            Log.i(TAG, "Successfully copied default database to application storage");
        }
    }

    private static void writeDefaultDatabaseToDatabasePath(final Context context,
                                                           final Path outputFilePath) throws IOException {
        InputStream inputStream = context.getAssets().open(ApplicationDatabase.DEFAULT_DB_NAME);
        OutputStream outputStream = Files.newOutputStream(outputFilePath);

        Log.i(TAG, "Copying default database from " + ApplicationDatabase.DEFAULT_DB_NAME + " to " + outputFilePath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private static void deleteDatabase(Context context) {
        File databaseFile = context.getDatabasePath(DATABASE_NAME);
        if (databaseFile.exists()) {
            if (databaseFile.delete()) {
                Log.i(TAG, "Successfully deleted database file: " + databaseFile.getPath());
            } else {
                throw new RuntimeException("Failed to delete database file: " + databaseFile.getPath());
            }
        }
    }

    private static boolean databaseExists(final Context context) {
        return context.getDatabasePath(DATABASE_NAME).exists();
    }
}