package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import android.widget.TextView;

public class FacultySignupActivity extends AppCompatActivity {
    private EditText facultyName, facultyEmail, facultyPassword;
    private Button facultySignupButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        facultyName = findViewById(R.id.facultyName);
        facultyEmail = findViewById(R.id.facultyEmail);
        facultyPassword = findViewById(R.id.facultyPassword);
        facultySignupButton = findViewById(R.id.facultySignupButton);

        facultySignupButton.setOnClickListener(v -> registerFaculty());
        TextView loginRedirect = findViewById(R.id.facultyLoginRedirect);
        loginRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(FacultySignupActivity.this, FacultyLoginActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void registerFaculty() {
        String name = facultyName.getText().toString();
        String email = facultyEmail.getText().toString();
        String password = facultyPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String userId = auth.getCurrentUser().getUid();
                    Map<String, Object> faculty = new HashMap<>();
                    faculty.put("name", name);
                    faculty.put("email", email);
                    faculty.put("password", password);

                    db.collection("users").document("faculty").collection("facultyAccounts")
                            .document(userId)
                            .set(faculty)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(FacultySignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();

                                // 🔥 Redirect to Faculty Login Page After Signup
                                Intent intent = new Intent(FacultySignupActivity.this, FacultyLoginActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(FacultySignupActivity.this, "Error saving data!", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(FacultySignupActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show());
    }
}
