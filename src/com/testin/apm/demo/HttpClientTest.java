// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Test for HttpClient
 */
public class HttpClientTest extends HttpTest {
    private HttpClient mClient;
    private HttpGet mGetRequest;
    private HttpPost mPostRequest;
    private HttpResponse mResponse;

    HttpClientTest() {
        mClient = new DefaultHttpClient();
    }

    @Override
    void postData() throws Exception {
        super.postData();

        mPostRequest = new HttpPost(mUrl);
        String postBody = "This is just for test";
        HttpEntity entity = new StringEntity(postBody, "utf-8");
        mPostRequest.setEntity(entity);
        mRequestDataSize = postBody.length();
        mStartTime = System.currentTimeMillis();
        mResponse = mClient.execute(mGetRequest);
        mEndTime = System.currentTimeMillis();
        mStatusCode = mResponse.getStatusLine().getStatusCode();

        if (HttpStatus.SC_OK == mStatusCode) {
            String responseStr = EntityUtils.toString(mResponse.getEntity());
            mResponseDataSize = responseStr.length();
        }
    }

    @Override
    void getData() throws Exception {
        super.getData();

        mGetRequest = new HttpGet(mUrl);
        mStartTime = System.currentTimeMillis();
        mResponse = mClient.execute(mGetRequest);
        mEndTime = System.currentTimeMillis();
        mStatusCode = mResponse.getStatusLine().getStatusCode();

        if (HttpStatus.SC_OK == mStatusCode) {
            String responseStr = EntityUtils.toString(mResponse.getEntity());
            mResponseDataSize = responseStr.length();
        }
    }
}
