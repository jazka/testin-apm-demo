// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Test for HttpURLConnection
 */
public class HttpUrlConTest extends HttpTest {
    HttpURLConnection mUrlCon;

    @Override
    void postData() throws Exception {
        super.postData();

        URL l = new URL(mUrl);
        mUrlCon = (HttpURLConnection) l.openConnection();
        mUrlCon.setDoOutput(true);
        mUrlCon.setDoInput(true);
        mUrlCon.setRequestMethod("POST");
        mUrlCon.setUseCaches(false);
        mUrlCon.setRequestProperty("Content-type", "application/x-java-serialized-object");
        mUrlCon.connect();

        DataOutputStream dos = new DataOutputStream(mUrlCon.getOutputStream());
        String postContent = URLEncoder.encode("Thi is just for test", "UTF-8");
        dos.write(postContent.getBytes());
        dos.flush();
        dos.close();
        mRequestDataSize = postContent.length();

        mStartTime = System.currentTimeMillis();
        mUrlCon.getInputStream();
        mEndTime = System.currentTimeMillis();
        mStatusCode = mUrlCon.getResponseCode();
        mResponseDataSize = mUrlCon.getContentLength();
        mUrlCon.disconnect();
    }

    @Override
    void getData() throws Exception {
        super.getData();

        URL l = new URL(mUrl);
        mUrlCon = (HttpURLConnection) l.openConnection();
        mUrlCon.connect();

        mStartTime = System.currentTimeMillis();
        mUrlCon.getInputStream();
        mEndTime = System.currentTimeMillis();
        mStatusCode = mUrlCon.getResponseCode();
        mResponseDataSize = mUrlCon.getContentLength();
        mUrlCon.disconnect();
    }
}
