package com.hackerton.reanimator;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

/**
 * Created by dennispriess on 05/03/15.
 */
public class BreathActivity extends GooglePlayServicesActivity {

    public static final String START_URI = "/start";

    public static final String BREATH_COUNT_URI = "/count_push";

    public static final String BREATH_COUNT_KEY = "count_breath";

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

    private void sendIncreaseCount(int count) {

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(BREATH_COUNT_URI);
        putDataMapReq.getDataMap().putInt(BREATH_COUNT_KEY, count++);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(getGoogleApiClient(), putDataReq);

    }

    private void sendStart() {

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(START_URI);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(getGoogleApiClient(), putDataReq);

    }


    private void vibrate() {
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sendStart();

        mCountDownTimer = new CountDownTimer(6000, 2000) {
            int count = 0;

            @Override
            public void onFinish() {
                mCountDownTimer.cancel();
                startActivity(new Intent(BreathActivity.this, MassageActivity.class));
                BreathActivity.this.finish();
            }

            @Override
            public void onTick(final long l) {
                count++;
                sendIncreaseCount(count);
                vibrator.vibrate(1500);
                mTextView.setText(String.valueOf(count));
            }
        };
        mCountDownTimer.start();


    }

}
