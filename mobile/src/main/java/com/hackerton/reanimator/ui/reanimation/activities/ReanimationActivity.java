package com.hackerton.reanimator.ui.reanimation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

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

/**
 * Created by traxdata on 05/03/15.
 */
public class ReanimationActivity extends GooglePlayServicesActivity implements
        DataApi.DataListener {

    private static final String TAG = ReanimationActivity.class.getCanonicalName();

    public static final String START_URI = "/start";
    public static final String STOP_URI = "/stop";
    public static final String PUSH_COUNT_URI = "/count_breath";
    public static final String BREATH_COUNT_URI = "/count_push";

    public static final String PUSH_COUNT_KEY = TAG + "_count_push";
    public static final String BREATH_COUNT_KEY = TAG + "_count_breath";

    private ReanimationFragment mReanimationFragment;

    public static Intent getIntent(final Context context) {
        return new Intent(context, ReanimationActivity.class);
    }

    @Override
    protected GoogleApiClient.Builder customizeGoogleClient(GoogleApiClient.Builder builder) {
        super.customizeGoogleClient(builder);
        builder.addApi(Wearable.API);
        return builder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mReanimationFragment = new ReanimationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ReanimationFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(getGoogleApiClient(), this);
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

    }

    private void onReanimationStop() {

    }

    private void updatePushCount(final int count) {

    }

    private void updateBreathCount(final int counn) {

    }

    public static interface ReanimationCommunicationInterface {

    }
}
