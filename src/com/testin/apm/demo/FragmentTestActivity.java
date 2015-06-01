// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Fragment and thread test Activity
 */
public class FragmentTestActivity extends Activity {
    private static final String TOAST_TEXT = "The fragment is not availavle for current SDK!";
    private Fragment1 mFragment1 = new Fragment1();
    private Fragment2 mFragment2 = new Fragment2();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);

        init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        showFragment();
    }

    /**
     * Initialization
     */
    public void init() {
        showFragment();
    }

    /**
     * Check if the sdk version is available for fragment
     * @return true if fragment is available or false
     */
    private boolean isFragmentAvailable() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            Toast.makeText(getApplicationContext(), TOAST_TEXT, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Show fragment according orientation
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showFragment() {
        if (!isFragmentAvailable()) {
            return;
        }

        int ori = getResources().getConfiguration().orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_test_layout, mFragment1).commit();
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_test_layout, mFragment2).commit();
        }
    }
}
