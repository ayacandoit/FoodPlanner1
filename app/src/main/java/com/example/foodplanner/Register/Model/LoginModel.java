package com.example.foodplanner.Register.Model;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.widget.Toast;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.Register.View.RegisterScreen;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel {
    FirebaseAuth firebaseAuth;

    public LoginModel(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }
    void createAccountInFirebase(String email, String password,RegisterListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
//                        Toast.makeText(RegisterScreen.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegisterScreen.this, MainActivity.class));
//
//                        finish();
                    } else {
                        listener.onFailure("Failed to create account: ");
                        //Toast.makeText(RegisterScreen.this, "Failed to create account: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    public interface RegisterListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

}
