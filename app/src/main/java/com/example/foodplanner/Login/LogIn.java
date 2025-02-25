package com.example.foodplanner.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.Register.View.RegisterScreen;
import com.example.foodplanner.HomeScreen.View.Home_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText emailEditTxt, passwordEditTxt;
    Button logInBtn;
    TextView signUpTxt;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        emailEditTxt = findViewById(R.id.EmailEditText2);
        passwordEditTxt = findViewById(R.id.PasswordEditText2);
        logInBtn = findViewById(R.id.signinBtn);
        signUpTxt = findViewById(R.id.SignUpTxt);

        firebaseAuth = FirebaseAuth.getInstance();

        logInBtn.setOnClickListener(v -> loginUser());
        signUpTxt.setOnClickListener(v -> startActivity(new Intent(LogIn.this, RegisterScreen.class)));
    }

    void loginUser() {
        String email = emailEditTxt.getText().toString().trim();
        String password = passwordEditTxt.getText().toString().trim();

        if (!validateData(email, password)) {
            return;
        }
        logInAccountInFirebase(email, password);
    }

    void logInAccountInFirebase(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogIn.this, Home_Activity.class));
                            finish();
                        } else {
                            Toast.makeText(LogIn.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    boolean validateData(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditTxt.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            passwordEditTxt.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }
}