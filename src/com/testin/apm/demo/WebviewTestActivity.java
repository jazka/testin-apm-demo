// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Webview test Activity
 */
public class WebviewTestActivity extends Activity {
    private static final String WEBVIEW_URL = "http://www.baidu.com/";
    private static final String ALERT_DIALOG_TITLE = "Hint";
    private static final String ALERT_DIALOG_MESSAGE = "Do you quit?";
    private static final String DIALOG_POSITIVE_BTN_TEXT = "OK";
    private static final String DIALOG_NEGATIVE_BTN_TEXT = "Cancle";
    private WebView mWebview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_test);

        init();
    }

    /**
     * Initialization
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        mWebview = (WebView) findViewById(R.id.webview);

        WebSettings wSettings = mWebview.getSettings();
        wSettings.setBuiltInZoomControls(true);
        wSettings.setUseWideViewPort(true);
        wSettings.setLoadWithOverviewMode(true);
        wSettings.setJavaScriptEnabled(true);

        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl(WEBVIEW_URL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                    return true;
                } else {
                    new AlertDialog.Builder(this)
                        .setTitle(ALERT_DIALOG_TITLE)
                        .setMessage(ALERT_DIALOG_MESSAGE)
                        .setPositiveButton(DIALOG_POSITIVE_BTN_TEXT, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton(DIALOG_NEGATIVE_BTN_TEXT, null).show();
                }
                break;
            default:
                break;
        }

        return false;
    }
}
