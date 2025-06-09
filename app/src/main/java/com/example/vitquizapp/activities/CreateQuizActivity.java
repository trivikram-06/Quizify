package com.example.vitquizapp.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;

import java.util.Calendar;

public class CreateQuizActivity extends AppCompatActivity {

    private EditText courseName, facultyName, startTime, endTime;
    private Button btnNext;
    private Calendar calendar;
    private String start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        // Initialize UI Elements
        courseName = findViewById(R.id.courseName);
        facultyName = findViewById(R.id.facultyName);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        btnNext = findViewById(R.id.btnNext);

        // Time Picker for Start Time
        startTime.setOnClickListener(v -> showTimePicker(true));
        endTime.setOnClickListener(v -> showTimePicker(false));

        // Next Button Click
        btnNext.setOnClickListener(v -> {
            String course = courseName.getText().toString().trim();
            String faculty = facultyName.getText().toString().trim();

            if (course.isEmpty() || faculty.isEmpty() || start == null || end == null) {
                Toast.makeText(CreateQuizActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            } else {
                // Move to AddQuestionsActivity
                Intent intent = new Intent(CreateQuizActivity.this, AddQuestionsActivity.class);
                intent.putExtra("courseName", course);
                intent.putExtra("facultyName", faculty);
                intent.putExtra("startTime", start);
                intent.putExtra("endTime", end);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showTimePicker(boolean isStartTime) {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            String time = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
            if (isStartTime) {
                start = time;
                startTime.setText(time);
            } else {
                end = time;
                endTime.setText(time);
            }
        }, hour, minute, true);
        timePicker.show();
    }
}
