package com.example.foodplanner.Register.Presenter;

import com.example.foodplanner.Register.Model.RegisterModel;

public class RegisterPresenter implements Bridge.Presenter {
    private Bridge.View view;
    private RegisterModel model;

    public RegisterPresenter(Bridge.View view) {
        this.view = view;
        this.model = new RegisterModel();
    }

    @Override
    public void register(String email, String password,String Confirmpassword) {
        model.createAccountInFirebase(email, password,Confirmpassword, new RegisterModel.OnRegisterListener() {
            @Override
            public void onSuccess() {
                view.onRegisterSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onRegisterFailure(errorMessage);
            }
        });
    }
}
