// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Http test Activity
 */
public class HttpTestActivity extends Activity {
    public Button mPostBtn;
    public Button mGetBtn;
    public Spinner mSpinner;
    public TextView mLogTextView;
    public String mUrl;
    public HttpTest mHttpTest;
    public boolean mFstLogData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_test);

        init();
    }

    /**
     * Initialization
     */
    public void init() {
        mFstLogData = true;

        String testType = getIntent().getStringExtra(Config.TEST_TYPE_KEY);
        if (testType.compareTo(Config.TYPE_HTTP_URL) == 0) {
            mHttpTest = new HttpUrlConTest();
        } else if (testType.compareTo(Config.TYPE_HTTP_CLIENT) == 0) {
            mHttpTest = new HttpClientTest();
        } else if (testType.compareTo(Config.TYPE_OK_HTTP) == 0) {
            mHttpTest = new OkHttpTest();
        } else if (testType.compareTo(Config.TYPE_VOLLEY) == 0) {
            mHttpTest = new VolleyTest(this);
        }

        OnClickListener l = new ButtonListener();
        mPostBtn = (Button) findViewById(R.id.http_post_btn);
        mPostBtn.setOnClickListener(l);

        mGetBtn = (Button) findViewById(R.id.http_get_btn);
        mGetBtn.setOnClickListener(l);

        mSpinner = (Spinner) findViewById(R.id.url_list_sp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Config.getUrlList());
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());

        mLogTextView = (TextView) findViewById(R.id.log_tv);
        mLogTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
    * Set the TextView context with log data
    */
    public void setLogData(String logData) {
        if (mFstLogData) {
            mLogTextView.setText(logData);
            mFstLogData = false;
        } else {
            mLogTextView.append(logData);
        }

        Util.logSeparatorLine(mLogTextView);
    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (mHttpTest != null) {
                boolean showLog = true;
                try {
                    switch (v.getId()) {
                        case R.id.http_post_btn:
                            mHttpTest.setUrl(mUrl);
                            mHttpTest.postData();
                            break;
                        case R.id.http_get_btn:
                            mHttpTest.setUrl(mUrl);
                            mHttpTest.getData();
                            break;
                        default:
                            showLog = false;
                            break;
                    }

                    if (showLog) {
                        setLogData(mHttpTest.getStatisticData());
                    }
                } catch (Exception e) {
                    setLogData(e.toString());
                }
            }
        }
    }

    class SpinnerOnSelectedListener implements OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            mUrl = adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
