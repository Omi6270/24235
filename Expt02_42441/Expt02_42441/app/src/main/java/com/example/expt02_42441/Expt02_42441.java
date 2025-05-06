package com.example.expt02_42441;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Expt02_42441 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all CardViews
        CardView homeCard = findViewById(R.id.homeCard);
        CardView settingsCard = findViewById(R.id.settingsCard);
        CardView profileCard = findViewById(R.id.profileCard);
        CardView notificationsCard = findViewById(R.id.notificationsCard);

        // Set click listeners for each card
        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                if (v == homeCard) {
                    message = "Home clicked";
                } else if (v == settingsCard) {
                    message = "Settings clicked";
                } else if (v == profileCard) {
                    message = "Profile clicked";
                } else if (v == notificationsCard) {
                    message = "Notifications clicked";
                }
                Toast.makeText(Expt02_42441.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        // Assign click listener to each card
        homeCard.setOnClickListener(cardClickListener);
        settingsCard.setOnClickListener(cardClickListener);
        profileCard.setOnClickListener(cardClickListener);
        notificationsCard.setOnClickListener(cardClickListener);
    }
}