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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// import com.testin.agent.TestinAgent;

/**
 * Url report interface test Activity
 */
public class UrlReportActivity extends Activity {
    public EditText mUrlEt;
    public EditText mMethodEt;
    public EditText mContentTypeEt;
    public EditText mLatencyEt;
    public EditText mBytesRecvEt;
    public EditText mBytesSendEt;
    public EditText mStatusCodeEt;
    public Button mReportBtn;
    public TextView mLogTextView;
    public boolean mFstLogData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reporturl_test);

        init();
    }

    /**
     * Initialization
     */
    public void init() {
        mFstLogData = true;

        mUrlEt = (EditText) findViewById(R.id.edit_url);
        mMethodEt = (EditText) findViewById(R.id.edit_method);
        mContentTypeEt = (EditText) findViewById(R.id.edit_content_type);
        mLatencyEt = (EditText) findViewById(R.id.edit_latency);
        mBytesRecvEt = (EditText) findViewById(R.id.edit_bytes_recv);
        mBytesSendEt = (EditText) findViewById(R.id.edit_bytes_send);
        mStatusCodeEt = (EditText) findViewById(R.id.edit_status_code);

        mReportBtn = (Button) findViewById(R.id.report_url_btn);
        mReportBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doReport();
            }
        });

        mLogTextView = (TextView) findViewById(R.id.log_tv);
        mLogTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void doReport() {
        try {
            String url = mUrlEt.getText().toString();
            String method = mMethodEt.getText().toString();
            String contentType = mContentTypeEt.getText().toString();
            long latency = Long.parseLong(mLatencyEt.getText().toString());
            long brecv = Long.parseLong(mBytesRecvEt.getText().toString());
            long bsend = Long.parseLong(mBytesSendEt.getText().toString());
            int scode = Integer.parseInt(mStatusCodeEt.getText().toString());

            if (mFstLogData) {
                mLogTextView.setText("URL: " + url);
                mFstLogData = false;
            } else {
                mLogTextView.append("URL: " + url);
            }

            mLogTextView.append("\r\n" + "Method: " + method);
            mLogTextView.append("\r\n" + "ContentType: " + contentType);
            mLogTextView.append("\r\n" + "Latency: " + latency);
            mLogTextView.append("\r\n" + "BytesRecv: " + brecv);
            mLogTextView.append("\r\n" + "BytesSend: " + bsend);
            mLogTextView.append("\r\n" + "StatusCode: " + scode);

            // Add Testin Agent interface here
            // TestinAgent.reportURL(url, method, contentType, latency, brecv, bsend, scode);
        } catch (NumberFormatException e) {
            if (mFstLogData) {
                mLogTextView.setText(e.toString());
                mFstLogData = false;
            } else {
                mLogTextView.append("\r\n" + e.toString());
            }
        } finally {
            Util.logSeparatorLine(mLogTextView);
        }
    }
}
