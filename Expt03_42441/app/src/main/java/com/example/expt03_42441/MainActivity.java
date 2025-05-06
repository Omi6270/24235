package com.example.expt03_42441;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView userEmail;
    private MaterialButton signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        userEmail = findViewById(R.id.userEmail);
        signOutButton = findViewById(R.id.signOutButton);

        // Check if user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // If not logged in, go to login screen
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Display user's email
        userEmail.setText("Logged in as: " + currentUser.getEmail());

        // Set up sign out button
        signOutButton.setOnClickListener(v -> signOut());
    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(MainActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        // Redirect to login screen
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}