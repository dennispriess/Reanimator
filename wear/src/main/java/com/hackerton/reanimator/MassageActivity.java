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

public class MassageActivity extends GooglePlayServicesActivity {

    public static final String START_URI = "/start";

    public static final String STOP_URI = "/stop";

    public static final String PUSH_COUNT_URI = "/count_breath";

    public static final String PUSH_COUNT_KEY = "count_push";

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

    private void sendIncreaseCount(int count) {

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(PUSH_COUNT_URI);
        putDataMapReq.getDataMap().putInt(PUSH_COUNT_KEY, count++);
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

        mCountDownTimer = new CountDownTimer(31000, 1000) {
            int count = 0;

            @Override
            public void onFinish() {
                mCountDownTimer.cancel();
                startActivity(new Intent(MassageActivity.this, BreathActivity.class));

            }

            @Override
            public void onTick(final long l) {
                count++;
                sendIncreaseCount(count);
                vibrator.vibrate(200);
                mTextView.setText(String.valueOf(count));
            }
        };
        mCountDownTimer.start();


    }

}
