package com.example.expt04_42441;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    private TextView nameText, subjectText, genderText, qualificationsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Initialize views
        nameText = findViewById(R.id.nameText);
        subjectText = findViewById(R.id.subjectText);
        genderText = findViewById(R.id.genderText);
        qualificationsText = findViewById(R.id.qualificationsText);

        // Load and display data
        loadData();
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences("RegistrationData", MODE_PRIVATE);

        nameText.setText(prefs.getString("name", ""));
        subjectText.setText(prefs.getString("subject", ""));
        genderText.setText(prefs.getString("gender", ""));
        qualificationsText.setText(prefs.getString("qualifications", "None"));
    }
}