package com.example.vitquizapp.activities;
import com.example.vitquizapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText signupEmail, signupPassword;
    private Button signupStudentButton, signupFacultyButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupStudentButton = findViewById(R.id.signupStudentButton);
        signupFacultyButton = findViewById(R.id.signupFacultyButton);

        signupStudentButton.setOnClickListener(v -> registerUser("student"));
        signupFacultyButton.setOnClickListener(v -> registerUser("faculty"));
    }

    private void registerUser(String role) {
        String email = signupEmail.getText().toString();
        String password = signupPassword.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("role", role);

                    db.collection("users").document(auth.getCurrentUser().getUid())
                            .set(user).addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "User Registered!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            });
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show());
    }
}
