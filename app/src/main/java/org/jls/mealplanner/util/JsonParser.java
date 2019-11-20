package org.jls.mealplanner.util;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonParser {

    private InputStreamReader inputStream;

    public JsonParser(final @NonNull InputStreamReader jsonStream) {
        inputStream = jsonStream;
    }

    public JSONObject parse() throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(inputStream);
        String jsonString = readAllLinesFromReader(streamReader);

        return new JSONObject(jsonString);
    }

    private String readAllLinesFromReader(final @NonNull BufferedReader reader) throws IOException {
        StringBuilder jsonStringBuilder = new StringBuilder();

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            jsonStringBuilder.append(currentLine);
        }
        return jsonStringBuilder.toString();
    }
}
