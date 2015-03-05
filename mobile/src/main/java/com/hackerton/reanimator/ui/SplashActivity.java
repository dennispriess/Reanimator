package com.hackerton.reanimator.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.intro.activities.IntroActivity;


public class SplashActivity extends ActionBarActivity {

    private static Handler HANDLER = new Handler();

    private static final int SPLASH_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(IntroActivity.getIntent(SplashActivity.this));
            }
        }, SPLASH_DURATION);
    }
}
