package com.hackerton.reanimator.ui.intro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackerton.reanimator.R;
import com.hackerton.reanimator.ui.BaseFragment;

/**
 * Created by traxdata on 05/03/15.
 */
public class IntroPageFragment extends BaseFragment {

    public static final String TAG = IntroPageFragment.class.getCanonicalName();

    public static final int PAGE_1 = 0;
    public static final int PAGE_2 = 1;

    public static final int PAGE_COUNT = 2;

    public static final String EXTRA_PAGE = TAG + "_page";

    private int mPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPage = savedInstanceState.getInt(EXTRA_PAGE);
        } else {
            mPage = getArguments().getInt(EXTRA_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int resource;
        switch (mPage) {
            default:
            case PAGE_1:
                resource = R.layout.fragment_into_01;
                break;
            case PAGE_2:
                resource = R.layout.fragment_into_02;
                break;
        }

        final View view = inflater.inflate(resource, container, false);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_PAGE, mPage);
    }
}
