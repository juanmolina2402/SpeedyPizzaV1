package com.restaurant_bd.speedypizza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(validate()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        };
        Timer t = new Timer();
        t.schedule(tt, 1000);
    }
    private boolean validate(){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ID_EMPLOYEE, MODE_PRIVATE);
        boolean  b= false;
        File f = new File("/data/data/com.restaurant_bd.speedypizza/shared_prefs/employeeId.xml");
        if(f.exists()){
            if(!sharedPreferences.getString("id_employee", "0").isEmpty()) {
                b = true;
            }
        }
        return b;
    }
}