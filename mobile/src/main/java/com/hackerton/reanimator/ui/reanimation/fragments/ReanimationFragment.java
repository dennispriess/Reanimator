package com.hackerton.reanimator.ui.reanimation.fragments;

import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.BaseFragment;
import com.hackerton.reanimator.ui.reanimation.activities.ReanimationActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by traxdata on 05/03/15.
 */
public class ReanimationFragment extends BaseFragment
        implements ReanimationActivity.ReanimationCommunicationInterface {

    private static Handler HANDLER = new Handler();

    private TextView mBreathCountTextView;

    private TextView mPushCountTextView;

    private long mStartTime;

    private TextView mTimerTextView;

    private long mTimerUpdateDelay = 200;

    private long mtTimeInMilliseconds;

    private boolean started = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reanimating, container, false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimerTextView = (TextView) view.findViewById(R.id.timer);
        mBreathCountTextView = (TextView) view.findViewById(R.id.breath_count);
        mPushCountTextView = (TextView) view.findViewById(R.id.push_count);
    }

    @Override
    public void setBreathCount(int count) {
        startTimer();
        mPushCountTextView.setText("0");
        mBreathCountTextView.setText(String.valueOf(count));
    }

    @Override
    public void setPushCount(int count) {
        startTimer();
        mBreathCountTextView.setText("0");

        mPushCountTextView.setText(String.valueOf(count));
    }

    @Override
    public void startTimer() {
        if (!started) {
            started = true;
            mStartTime = SystemClock.uptimeMillis();
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mtTimeInMilliseconds = SystemClock.uptimeMillis() - mStartTime;
                    int secs = (int) (mtTimeInMilliseconds / 1000);
                    int mins = secs / 60;
                    secs = secs % 60;
                    int hours = mins / 60;

                    mTimerTextView.setText("" + hours + ":"
                            + String.format("%02d", mins) + ":"
                            + String.format("%02d", secs));
                    HANDLER.postDelayed(this, mTimerUpdateDelay);
                }
            }, 0);
        }

    }

    @Override
    public void stopTimer() {
        // just stop it
        HANDLER.removeCallbacksAndMessages(null);
    }

}
