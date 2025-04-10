package com.example.group4app;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FetchRecommendationTask extends AsyncTask<String, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private final TextView resultTextView;

    public FetchRecommendationTask(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String symptom = params[0];
        String response = "";
        try {
            String encodedSymptom = URLEncoder.encode(symptom, "UTF-8");
            URL url = new URL("http://10.0.2.2:8080/cold_remedies_api/get_recommendation.php?symptom=" + encodedSymptom);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            response = responseBuilder.toString();
            reader.close();
        } catch (Exception e) {
            Log.e("FetchTask", "Network error: " + e.getMessage(), e);
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String recommendation = jsonObject.getString("recommendation");
                if (resultTextView != null) {
                    resultTextView.setText("Recommended: " + recommendation);
                }
            } else {
                resultTextView.setText("No recommendation found.");
            }
        } catch (Exception e) {
            Log.e("FetchTask", "Parsing error: " + e.getMessage(), e);
            resultTextView.setText("Error parsing response.");
        }
    }
}




