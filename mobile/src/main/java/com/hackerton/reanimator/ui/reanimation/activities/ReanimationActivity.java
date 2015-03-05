package com.hackerton.reanimator.ui.reanimation.activities;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.GooglePlayServicesActivity;
import com.hackerton.reanimator.ui.reanimation.fragments.ReanimationFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by traxdata on 05/03/15.
 */
public class ReanimationActivity extends GooglePlayServicesActivity implements
        DataApi.DataListener {

    public static interface ReanimationCommunicationInterface {

        void setBreathCount(int count);

        void setPushCount(int count);

        void startTimer();

        void stopTimer();
    }

    public static final String START_URI = "/start";

    public static final String STOP_URI = "/stop";

    public static final String PUSH_COUNT_URI = "/count_breath";

    public static final String BREATH_COUNT_URI = "/count_push";

    public static final String PUSH_COUNT_KEY = "count_push";

    public static final String BREATH_COUNT_KEY = "count_breath";

    private static final String TAG = ReanimationActivity.class.getCanonicalName();

    private ReanimationFragment mReanimationFragment;

    @Override
    protected GoogleApiClient.Builder customizeGoogleClient(GoogleApiClient.Builder builder) {
        super.customizeGoogleClient(builder);
        builder.addApi(Wearable.API);
        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mReanimationFragment = new ReanimationFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mReanimationFragment).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(getGoogleApiClient(), this);
        if (mReanimationFragment != null) {
            mReanimationFragment.stopTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReanimationFragment != null) {
            mReanimationFragment.startTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static Intent getIntent(final Context context) {
        return new Intent(context, ReanimationActivity.class);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Wearable.DataApi.addListener(getGoogleApiClient(), this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo(START_URI) == 0) {
                    onReanimationStart();
                } else if (item.getUri().getPath().compareTo(STOP_URI) == 0) {
                    onReanimationStop();
                } else if (item.getUri().getPath().compareTo(BREATH_COUNT_URI) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updateBreathCount(dataMap.getInt(BREATH_COUNT_KEY));
                } else if (item.getUri().getPath().compareTo(PUSH_COUNT_URI) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updatePushCount(dataMap.getInt(PUSH_COUNT_KEY));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    private void onReanimationStart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mReanimationFragment != null) {
                    mReanimationFragment.startTimer();
                }
            }
        });
    }

    private void onReanimationStop() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mReanimationFragment != null) {
                    mReanimationFragment.startTimer();
                }
            }
        });
    }

    private void updateBreathCount(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mReanimationFragment != null) {
                    mReanimationFragment.setBreathCount(count);
                }
            }
        });
    }

    private void updatePushCount(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mReanimationFragment != null) {
                    mReanimationFragment.setPushCount(count);
                }
            }
        });
    }
}
