package com.example.foodplanner.Login.Presenter;

public interface Bridge {
    interface View {
        void onLoginSuccess();
        void onLoginFailure(String message);
    }

    interface Presenter {
        void LogIn(String email, String password,String Confirmpassword);
    }
}
