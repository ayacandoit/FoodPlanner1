package com.example.foodplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {
    EditText emailEditTxt, passwordEditTxt, confirmPassEditTxt;
    Button createAccount;
    TextView loginTextView;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        emailEditTxt = findViewById(R.id.EmailEditText);
        passwordEditTxt = findViewById(R.id.PasswordEditText);
        confirmPassEditTxt = findViewById(R.id.ConfirmPasswordEditText);
        createAccount = findViewById(R.id.createAcountBtn);
        loginTextView = findViewById(R.id.LoginTxt);

        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(v -> createAccount());
        loginTextView.setOnClickListener(v ->startActivity(new Intent(RegisterScreen.this, LogIn.class)));
    }

    void createAccount() {
        String email = emailEditTxt.getText().toString().trim();
        String password = passwordEditTxt.getText().toString().trim();
        String confirmPassword = confirmPassEditTxt.getText().toString().trim();

        boolean isValidated = validateData(email, password, confirmPassword);
        if (!isValidated) {
            return;
        }
        createAccountInFirebase(email, password);
    }

    void createAccountInFirebase(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterScreen.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterScreen.this, MainActivity.class));

                        finish();
                    } else {
                        Toast.makeText(RegisterScreen.this, "Failed to create account: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    boolean validateData(String email, String password, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditTxt.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            passwordEditTxt.setError("Password length is invalid (must be at least 6 characters)");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPassEditTxt.setError("Password does not match");
            return false;
        }
        return true;
    }
}
