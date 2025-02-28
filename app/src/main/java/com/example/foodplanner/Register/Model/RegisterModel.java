package com.example.foodplanner.Register.Model;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterModel {
    FirebaseAuth firebaseAuth;

    public RegisterModel() {
        firebaseAuth= FirebaseAuth.getInstance();
    }
    public void createAccountInFirebase(String email, String password, String Confirmpassword,
                                        OnRegisterListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }
    public interface OnRegisterListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

}
