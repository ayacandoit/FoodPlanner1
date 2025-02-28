package com.example.foodplanner.Login.Presenter;

import com.example.foodplanner.Login.Model.LoginModel;
import com.example.foodplanner.Register.Model.RegisterModel;

public class LoginPresenter implements Bridge.Presenter {
    private Bridge.View view;
    private LoginModel model;

    public LoginPresenter(Bridge.View view) {
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public void LogIn(String email, String password,String Confirmpassword) {
        model.logInAccountInFirebase(email, password, new LoginModel.OnLoginListener(){

            @Override
            public void onSuccess() {
                view.onLoginSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onLoginFailure(errorMessage);

            }
        });
    }

}

