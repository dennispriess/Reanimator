package com.hackerton.reanimator;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MassageActivity extends GooglePlayServicesActivity {

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
                vibrate();

            }
        });
    }


    private void vibrate() {
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mCountDownTimer = new CountDownTimer(30000, 1000) {
            int count = 1;

            @Override
            public void onFinish() {
                mCountDownTimer.cancel();
            }

            @Override
            public void onTick(final long l) {
                count++;
                vibrator.vibrate(200);
                mTextView.setText(String.valueOf(count));
            }
        };
        mCountDownTimer.start();


    }

}
