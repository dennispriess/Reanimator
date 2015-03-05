package com.hackerton.reanimator.ui.intro.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hackerton.reanimator.ui.intro.fragments.IntroPageFragment;

/**
 * Created by traxdata on 05/03/15.
 */
public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment fragment = new IntroPageFragment();
        final Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putInt(IntroPageFragment.EXTRA_PAGE, IntroPageFragment.PAGE_1);
                break;
            case 1:
                bundle.putInt(IntroPageFragment.EXTRA_PAGE, IntroPageFragment.PAGE_2);
                break;
            case 2:
                bundle.putInt(IntroPageFragment.EXTRA_PAGE, IntroPageFragment.PAGE_3);
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return IntroPageFragment.PAGE_COUNT;
    }
}