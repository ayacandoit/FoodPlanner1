package com.example.foodplanner.Register.Presenter;

public interface Bridge {

        interface View {
            void onRegisterSuccess();
            void onRegisterFailure(String message);
        }

        interface Presenter {
            void register(String email, String password,String Confirmpassword);
        }

}
