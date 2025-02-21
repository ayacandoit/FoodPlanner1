package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class SplashActivity extends AppCompatActivity {
    Handler handler=new Handler();
    Runnable runnable;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        runnable =new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;
                if(currentUser==null)
                {                 intent=new Intent(SplashActivity.this, RegisterScreen.class);


                }else{
                 intent=new Intent(SplashActivity.this, LogIn.class);}
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable,3200);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable
        );
    }
}