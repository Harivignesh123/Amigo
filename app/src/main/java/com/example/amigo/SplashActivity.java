package com.example.amigo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Intent iw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() { @Override
        public void run() {
            iw= new Intent(SplashActivity.this, OneTimeuserPreferenceActivity.class);
            startActivity(iw);
            finish();
        }},3000);

    }
}