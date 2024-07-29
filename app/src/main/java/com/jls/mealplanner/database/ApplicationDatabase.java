package com.jls.mealplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {IngredientEntity.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "application_database.db";
    private static ApplicationDatabase INSTANCE = null;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized void initializeInstance(Context context) {
        if (ApplicationDatabase.INSTANCE == null) {
            ApplicationDatabase.INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static ApplicationDatabase getInstance() {
        if (ApplicationDatabase.INSTANCE == null) {
            throw new IllegalStateException(ApplicationDatabase.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return ApplicationDatabase.INSTANCE;
    }

    public abstract IngredientDao ingredientDao();
}