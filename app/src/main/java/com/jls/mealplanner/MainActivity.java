package com.jls.mealplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.databinding.ActivityMainBinding;
import com.jls.mealplanner.ui.OnUserSearchChangeListener;
import com.jls.mealplanner.utils.UserPermInteractor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration appBarConfiguration;

    private DrawerLayout drawerLayout = null;
    private Set<OnUserSearchChangeListener> onUserSearchChangeListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating main activity");
        super.onCreate(savedInstanceState);

        onUserSearchChangeListeners = new HashSet<>();
        initializeDatabase();
        createUi();
        sendUserTestRequest();
    }

    private void createUi() {
        Log.d(TAG, "Creating UI components");

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
        getMenuInflater().inflate(R.menu.top_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView == null) {
            Log.e(TAG, "Search view is null");
            return false;
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                String userSearchedText = text.trim().toLowerCase();
                Log.d(TAG, "User searched text: " + userSearchedText);
                for (OnUserSearchChangeListener listener : onUserSearchChangeListeners) {
                    listener.onUserSearchText(userSearchedText);
                }
                return true;
            }
        });

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

        if (id == R.id.menu_settings || id == R.id.nav_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
            return true;
        } else if (id == R.id.menu_about || id == R.id.nav_about) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addOnQueryTextChangeCallback(OnUserSearchChangeListener onUserSearchChangeListener) {
        onUserSearchChangeListeners.add(onUserSearchChangeListener);
    }

    private void initializeDatabase() {
        try {
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