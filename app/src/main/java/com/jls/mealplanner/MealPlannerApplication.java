package com.jls.mealplanner;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.di.AppContainer;
import com.jls.mealplanner.di.AppViewModelFactory;

import java.io.IOException;

/**
 * Application entry point. Owns the application-scoped {@link AppContainer} (database + repositories)
 * and the {@link AppViewModelFactory} used to inject repositories into ViewModels.
 */
public class MealPlannerApplication extends Application {

    private static final String TAG = MealPlannerApplication.class.getSimpleName();

    private AppContainer appContainer;
    private AppViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Initializing application dependencies");

        boolean forceDbReinstall = getResources().getBoolean(R.bool.force_database_reinstall);
        try {
            ApplicationDatabase database = ApplicationDatabase.create(this, forceDbReinstall);
            appContainer = new AppContainer(database);
            viewModelFactory = new AppViewModelFactory(appContainer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize the application database", e);
        }
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }

    public AppViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    /** Convenience accessor for the app-scoped ViewModel factory from any Context. */
    public static AppViewModelFactory viewModelFactory(Context context) {
        return ((MealPlannerApplication) context.getApplicationContext()).viewModelFactory;
    }
}
