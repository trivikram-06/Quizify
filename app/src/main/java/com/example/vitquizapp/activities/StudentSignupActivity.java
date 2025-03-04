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
    private EditText signupName, signupEmail, signupPassword;
    private Button signupButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = signupName.getText().toString();
        String email = signupEmail.getText().toString();
        String password = signupPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("email", email);
            user.put("role", "student");

            db.collection("users").document(auth.getCurrentUser().getUid())
                    .set(user).addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Student Registered!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentSignupActivity.this, LoginActivity.class));
                        finish();
                    });
        }).addOnFailureListener(e -> Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show());
    }
}
