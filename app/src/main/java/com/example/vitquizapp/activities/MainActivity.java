package com.example.vitquizapp.activities;
import com.example.vitquizapp.R;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure button ID matches XML
        Button startButton = findViewById(R.id.startButton);

        // Click listener to open LoginActivity
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Closes MainActivity so user can't go back to it with "Back" button
        });
    }
}
