package com.hackerton.reanimator.ui.intro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.BaseActivity;
import com.hackerton.reanimator.ui.intro.adapter.IntroAdapter;

/**
 * Created by traxdata on 05/03/15.
 */
public class IntroActivity extends BaseActivity {
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
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}