package com.example.group4app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private EditText symptomsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components
        resultTextView = findViewById(R.id.recommendations_section);  // ✅ Fixed ID
        symptomsInput = findViewById(R.id.symptoms_input);
        Button submitButton = findViewById(R.id.submit_symptoms);

        // Handle button click
        submitButton.setOnClickListener(v -> {
            String symptom = symptomsInput.getText().toString().trim();
            if (!symptom.isEmpty()) {
                new FetchRecommendationTask(resultTextView).execute(symptom);
            } else {
                resultTextView.setText("Please enter a symptom.");
            }
        });
    }
}







