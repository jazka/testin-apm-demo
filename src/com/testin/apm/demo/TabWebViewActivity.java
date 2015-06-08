// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;

/**
 * Webview test Activity with tabs
 */
public class TabWebViewActivity extends TabActivity implements TabHost.TabContentFactory {

    private static final String WEBVIEW_URL = "http://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("web1")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("web2")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("web3")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("web4")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator("web5")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab6").setIndicator("web6")
                .setContent(this));
    }

    @Override
    public View createTabContent(String tag) {
        final WebView webview = new WebView(this);

        WebSettings wSettings = webview.getSettings();
        wSettings.setBuiltInZoomControls(true);
        wSettings.setUseWideViewPort(true);
        wSettings.setLoadWithOverviewMode(true);
        wSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(WEBVIEW_URL);
        return webview;
    }
}
