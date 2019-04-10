package com.example.shalin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_ACTIVITY_TIMER = 1000;  //time duration for splash activity to run
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to hide titlebar and to request full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_activity);

        //thread will start from here
        SplashLauncher splashLauncher = new SplashLauncher();
        splashLauncher.start();
    }

    class SplashLauncher extends Thread{
        public void run(){
            try {
                sleep(SPLASH_ACTIVITY_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //after some time mainactivity will start i.e main layout of appliction
            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}

