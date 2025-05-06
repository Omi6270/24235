package com.example.expt04_42441;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText nameInput;
    private Spinner subjectSpinner;
    private RadioGroup genderRadioGroup;
    private CheckBox graduateCheck, postGraduateCheck, phdCheck;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        graduateCheck = findViewById(R.id.graduateCheck);
        postGraduateCheck = findViewById(R.id.postGraduateCheck);
        phdCheck = findViewById(R.id.phdCheck);
        submitButton = findViewById(R.id.submitButton);

        // Set up spinner
        String[] subjects = {"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, subjects);
        subjectSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(v -> {
            if (validateForm()) {
                saveData();
                startShowActivity();
            }
        });
    }

    private boolean validateForm() {
        if (nameInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences("RegistrationData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Save name
        editor.putString("name", nameInput.getText().toString().trim());

        // Save subject
        editor.putString("subject", subjectSpinner.getSelectedItem().toString());

        // Save gender
        String gender = genderRadioGroup.getCheckedRadioButtonId() == R.id.maleRadio ? "Male" : "Female";
        editor.putString("gender", gender);

        // Save qualifications
        ArrayList<String> qualifications = new ArrayList<>();
        if (graduateCheck.isChecked()) qualifications.add("Graduate");
        if (postGraduateCheck.isChecked()) qualifications.add("Post Graduate");
        if (phdCheck.isChecked()) qualifications.add("PhD");
        editor.putString("qualifications", String.join(", ", qualifications));

        editor.apply();
    }

    private void startShowActivity() {
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);
    }
}