package com.jls.mealplanner.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.jls.mealplanner.R;
import com.jls.mealplanner.model.SharedDataHolder;

import java.io.IOException;

public class UserPermInteractor {


    public static void sendUserTestRequest(Resources resources, DrawerLayout drawerLayout) {
        SharedDataHolder sharedDataHolder = SharedDataHolder.getInstance();

        if (resources.getBoolean(R.bool.execute_user_test_request)) {
            if (!sharedDataHolder.isTestRequestSent()) {
                askUserTest(drawerLayout.getContext(), resources, sharedDataHolder);
                sharedDataHolder.setTestRequestSent(true);
            }
        }
    }

    private static void askUserTest(Context context, Resources resources, SharedDataHolder sharedDataHolder) {
        View customView = LayoutInflater.from(context).inflate(R.layout.dialog_request_access, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setView(customView).create();

        EditText editText = customView.findViewById(R.id.editText);
        editText.requestFocus();
        editText.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String value = editText.getText().toString();
                sendTestRequest(value, resources.getString(R.string.push_bullet_api_key), sharedDataHolder);
                dialog.dismiss();
                return true;
            }
            return false;
        });

        dialog.show();
    }

    public static void sendTestRequest(final String message, String apiKey, SharedDataHolder sharedDataHolder) {
        PushBulletClient client = new PushBulletClient(apiKey);
        try {
            client.pushNote("From Android", message);
            sharedDataHolder.setTestRequestSent(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
