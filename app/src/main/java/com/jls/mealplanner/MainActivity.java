package com.jls.mealplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.model.SharedDataHolder;
import com.jls.mealplanner.ui.IngredientsFragment;
import com.jls.mealplanner.ui.OnUserSearchChangeListener;
import com.jls.mealplanner.ui.PlanningFragment;
import com.jls.mealplanner.ui.RecipesFragment;
import com.jls.mealplanner.utils.PushBulletClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout = null;
    private SharedDataHolder sharedDataHolder;
    private Set<OnUserSearchChangeListener> onUserSearchChangeListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onUserSearchChangeListeners = new HashSet<>();

        sharedDataHolder = SharedDataHolder.getInstance();
        initializeDatabase();

        create_ui(savedInstanceState);
        send_user_test_request_if_not_already_sent();
    }

    private void create_ui(Bundle savedInstanceState) {
        Log.d(TAG, "Creating UI components");

        Toolbar toolbar = createToolbar();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,
                                                                 R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        addHandleBackPressedCallback();

        select_planning_fragment(savedInstanceState, navigationView);
    }

    private Toolbar createToolbar() {
        Toolbar toolbar = findViewById(R.id.week_navigation_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            Log.d(TAG, "About clicked from top bar menu");
            return true;
        } else if (id == R.id.settings) {
            Log.d(TAG, "Settings clicked from top bar menu");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addHandleBackPressedCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    } else {
                        finish();
                    }
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void addOnQueryTextChangeCallback(OnUserSearchChangeListener onUserSearchChangeListener) {
        onUserSearchChangeListeners.add(onUserSearchChangeListener);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_planning) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                   new PlanningFragment()).commit();
        } else if (itemId == R.id.menu_ingredients) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                   new IngredientsFragment()).commit();
        } else if (itemId == R.id.menu_recipes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                   new RecipesFragment()).commit();
        } else if (itemId == R.id.menu_settings) {
            Log.d(TAG, "Settings clicked from navigation menu");
        } else if (itemId == R.id.menu_about) {
            Log.d(TAG, "About clicked from navigation menu");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void select_planning_fragment(Bundle savedInstanceState, NavigationView navigationView) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                   new PlanningFragment()).commit();
            navigationView.setCheckedItem(R.id.menu_planning);
        }
    }

    private void send_user_test_request_if_not_already_sent() {
        if (getResources().getBoolean(R.bool.execute_user_test_request)) {
            if (!sharedDataHolder.isTestRequestSent()) {
                askUserTest(drawerLayout.getContext());
                sharedDataHolder.setTestRequestSent(true);
            }
        }
    }

    private void askUserTest(Context context) {
        View customView = LayoutInflater.from(context).inflate(R.layout.dialog_request_access, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setView(customView).create();

        EditText editText = customView.findViewById(R.id.editText);
        editText.requestFocus();
        editText.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String value = editText.getText().toString();
                sendTestRequest(value);
                dialog.dismiss();
                return true;
            }
            return false;
        });

        dialog.show();
    }

    public void sendTestRequest(final String message) {
        PushBulletClient client = new PushBulletClient(getResources().getString(R.string.push_bullet_api_key));
        try {
            client.pushNote("From Android", message);
            sharedDataHolder.setTestRequestSent(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDatabase() {
        try {
            ApplicationDatabase.initializeInstance(this,
                                                   getResources().getBoolean(R.bool.force_database_reinstall));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}