package com.jls.mealplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.databinding.ActivityMainBinding;
import com.jls.mealplanner.utils.UserPermInteractor;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating " + TAG);
        super.onCreate(savedInstanceState);

        initializeDatabase();
        createUi();
        sendUserTestRequest();
    }

    private void createUi() {
        Log.d(TAG, "Creating " + TAG + " components");

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawerLayout = binding.drawerLayout;
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_planning, R.id.nav_ingredients, R.id.nav_recipes)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationView navigationView = binding.navigationView;
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Creating " + TAG + " options menu");
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingsItemMenu || id == R.id.nav_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
            return true;
        } else if (id == R.id.aboutItemMenu || id == R.id.nav_about) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeDatabase() {
        try {
            Log.d(TAG, "Initializing database");
            ApplicationDatabase.initializeInstance(this,
                                                   getResources().getBoolean(R.bool.force_database_reinstall));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendUserTestRequest() {
        UserPermInteractor.sendUserTestRequest(getResources(), drawerLayout);
    }
}