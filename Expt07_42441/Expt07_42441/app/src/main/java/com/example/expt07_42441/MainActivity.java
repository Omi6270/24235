package com.example.expt07_42441;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageButton btnPrevious = findViewById(R.id.btnPrevious);
        ImageButton btnNext = findViewById(R.id.btnNext);
        TabLayout tabIndicator = findViewById(R.id.tabIndicator);

        // Add your images here
        int[] images = {
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                R.drawable.image5
        };

        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        viewPager.setAdapter(adapter);

        // Setup tab indicator
        new TabLayoutMediator(tabIndicator, viewPager,
                (tab, position) -> tab.setIcon(R.drawable.tab_selector)
        ).attach();

        // Navigation buttons
        btnPrevious.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true);
            }
        });

        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < images.length - 1) {
                viewPager.setCurrentItem(currentItem + 1, true);
            }
        });
    }
}