package com.example.expt03_42441;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    private MaterialButton signupButton;
    private View progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        progressBar = findViewById(R.id.progressBar);

        // Setup click listeners
        signupButton.setOnClickListener(v -> attemptSignup());
        findViewById(R.id.loginText).setOnClickListener(v -> finish());
    }

    private void attemptSignup() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validation
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        signupButton.setEnabled(false);

        // Create user with Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    signupButton.setEnabled(true);

                    if (task.isSuccessful()) {
                        // Sign up success
                        Toast.makeText(SignupActivity.this,
                                "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        // If sign up fails, display a message to the user
                        Toast.makeText(SignupActivity.this,
                                "Sign up failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}