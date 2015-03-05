package com.hackerton.reanimator.ui.intro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.hackerton.reanimator.AppConsts;
import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.BaseActivity;
import com.hackerton.reanimator.ui.intro.adapter.IntroAdapter;
import com.hackerton.reanimator.ui.intro.fragments.IntroPageFragment;
import com.hackerton.reanimator.ui.reanimation.activities.ReanimationActivity;

/**
 * Created by traxdata on 05/03/15.
 */
public class IntroActivity extends BaseActivity {

    private static final int NUM_PAGES = 5;

    private Button mNextButton;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    public static Intent getIntent(final Context context) {
        return new Intent(context, IntroActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new IntroAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mNextButton = (Button) findViewById(R.id.next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() >= IntroPageFragment.PAGE_COUNT - 1) {
                    mSharedPreferences.edit().putBoolean(AppConsts.PREFS.FIRSTLAUNCH, false).apply();
                    startActivity(ReanimationActivity.getIntent(IntroActivity.this));
                    finish();
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1, true); //getItem(-1) for previous
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}