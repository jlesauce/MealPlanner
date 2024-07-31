package com.jls.mealplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.jls.mealplanner.database.ApplicationDatabase;
import com.jls.mealplanner.model.SharedDataHolder;
import com.jls.mealplanner.ui.PlanningFragment;
import com.jls.mealplanner.ui.RecipesFragment;
import com.jls.mealplanner.ui.ingredients.IngredientsFragment;
import com.jls.mealplanner.utils.PushBulletClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout = null;
    private SharedDataHolder sharedDataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedDataHolder = SharedDataHolder.getInstance();
        initializeDatabase();

        create_ui(savedInstanceState);
        send_user_test_request_if_not_already_sent();
    }

    private void create_ui(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

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

    private void addHandleBackPressedCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
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