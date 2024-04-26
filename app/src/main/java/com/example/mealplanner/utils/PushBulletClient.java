package com.example.mealplanner.utils;

import android.util.Log;
import android.util.Pair;

import com.example.mealplanner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PushBulletClient {

    public static final String API_URL = "https://api.pushbullet.com/v2";
    private static final String TAG = "PushBulletClient";

    private final ExecutorService executor;
    private final String apiKey;

    public PushBulletClient(final String apiKey) {
        this.executor = Executors.newSingleThreadExecutor();
        this.apiKey = apiKey;
    }

    public void pushNote(final String title, final String body) throws IOException {
        List<Pair<String, String>> payload = new ArrayList<>();
        payload.add(new Pair<>("type", "note"));
        payload.add(new Pair<>("title", title));
        payload.add(new Pair<>("body", body));

        executeRequest("POST", payload);
    }

    private void executeRequest(final String method, List<Pair<String, String>> payload) {
        if (!executor.isShutdown()) {
            Future<Integer> future = executor.submit(() -> {
                try {
                    return sendRequest(method, API_URL + "/pushes", payload);
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred", e);
                    return null;
                } catch (JSONException e) {
                    Log.e(TAG, "Error occurred while converting payload to JSON", e);
                    return null;
                }
            });

            executor.submit(() -> {
                try {
                    Integer result = future.get();
                    if (result != null) {
                        Log.d(TAG, "Request completed with status code: " + result);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error occurred while processing result", e);
                }
            });
        } else {
            throw new IllegalStateException("Executor is shutdown. Cannot submit new tasks.");
        }
    }

    private int sendRequest(String method, String targetUrl,
                            List<Pair<String, String>> payload) throws IOException, JSONException {
        URL url = new URL(targetUrl);
        HttpURLConnection client = (HttpURLConnection) url.openConnection();

        client.setRequestMethod(method);

        // Set request header
        for (Pair<String, String> pair : createHeader()) {
            client.setRequestProperty(pair.first, pair.second);
        }

        // Write payload data to connection output stream
        client.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(client.getOutputStream())) {
            wr.writeBytes(convertPayloadToJsonString(payload));
        }

        int responseCode = client.getResponseCode();
        client.disconnect();
        return responseCode;
    }

    private List<Pair<String, String>> createHeader() {
        List<Pair<String, String>> header = new ArrayList<>();
        header.add(new Pair<>("Content-Type", "application/json"));
        header.add(new Pair<>("Access-Token", apiKey));
        return header;
    }

    private String convertPayloadToJsonString(List<Pair<String, String>> payload) throws JSONException {
        JSONObject jsonPostData = new JSONObject();
        for (Pair<String, String> pair : payload) {
            jsonPostData.put(pair.first, pair.second);
        }
        return jsonPostData.toString();
    }
}
