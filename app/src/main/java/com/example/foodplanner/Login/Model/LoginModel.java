package com.example.foodplanner.Login.Model;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.HomeScreen.View.Home_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel {
    FirebaseAuth firebaseAuth;

    public LoginModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void logInAccountInFirebase(String email, String password, Context context) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task ->  {

                        if (task.isSuccessful()) {
                            context.startActivity(new Intent(context, Home_Activity.class));

                        } else {
                           // Toast.makeText(LogIn.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                });
    }
}
