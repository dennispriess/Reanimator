package com.hackerton.reanimator;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

/**
 * Created by dennispriess on 05/03/15.
 */
public class BreathActivity extends GooglePlayServicesActivity {

    private CountDownTimer mCountDownTimer;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setTextColor(getResources().getColor(R.color.red));
                vibrate();

            }
        });
    }


    private void vibrate() {
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mCountDownTimer = new CountDownTimer(3000, 1000) {
            int count = 1;

            @Override
            public void onFinish() {
                mCountDownTimer.cancel();
            }

            @Override
            public void onTick(final long l) {
                count++;
                vibrator.vibrate(2000);
                mTextView.setText(String.valueOf(count));
            }
        };
        mCountDownTimer.start();


    }

}
