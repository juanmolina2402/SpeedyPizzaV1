package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
            }
        };
        Timer t = new Timer();
        t.schedule(tt, 1000);
    }
}