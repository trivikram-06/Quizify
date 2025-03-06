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

public class StudentSignupActivity extends AppCompatActivity {
    private EditText nameInput, emailInput, passwordInput;
    private Button signupButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.signupName);
        emailInput = findViewById(R.id.signupEmail);
        passwordInput = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> registerStudent());
    }

    private void registerStudent() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();

                        // ✅ Save student details in Firestore
                        Map<String, Object> student = new HashMap<>();
                        student.put("name", name);
                        student.put("email", email);

                        db.collection("users").document("students")
                                .collection("studentAccounts")
                                .document(userId)
                                .set(student)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, StudentLoginActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
