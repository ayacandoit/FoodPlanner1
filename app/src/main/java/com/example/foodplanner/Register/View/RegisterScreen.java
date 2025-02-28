package com.example.foodplanner.Register.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.HomeScreen.View.Home_Activity;
import com.example.foodplanner.Login.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.Register.Model.RegisterModel;
import com.example.foodplanner.Register.Presenter.Bridge;
import com.example.foodplanner.Register.Presenter.RegisterPresenter;

public class RegisterScreen extends AppCompatActivity implements Bridge.View {
    EditText emailEditTxt, passwordEditTxt, confirmPassEditTxt;
    Button createAccount;
    TextView loginTextView;
    RegisterModel loginModel;
    Context context;
    private RegisterPresenter presenter;

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
        loginModel = new RegisterModel();

        presenter = new RegisterPresenter(this);

        createAccount.setOnClickListener(v -> createAccount());
        loginTextView.setOnClickListener(v -> startActivity(new Intent(RegisterScreen.this, LogIn.class)));
    }

    void createAccount() {
        String email = emailEditTxt.getText().toString().trim();
        String password = passwordEditTxt.getText().toString().trim();
        String confirmPassword = confirmPassEditTxt.getText().toString().trim();

        boolean isValidated = validateData(email, password, confirmPassword);
        if (!isValidated) {
            return;
        }

        presenter.register(email, password, confirmPassword);
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

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(RegisterScreen.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterScreen.this, Home_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailure(String message) {
        Toast.makeText(RegisterScreen.this, "Failed to create account: " + message, Toast.LENGTH_LONG).show();
    }
}
