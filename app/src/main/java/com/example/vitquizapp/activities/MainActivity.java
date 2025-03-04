package com.example.vitquizapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;

import com.example.vitquizapp.R;

public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure button ID matches XML
        Button startButton = findViewById(R.id.startButton);

        // Click listener to open LoginActivity
        startButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // Smooth transition
            } catch (Exception e) {
                Log.e("MainActivity", "Error starting LoginActivity", e);
            }
        });
    }
}
