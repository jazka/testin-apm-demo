// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Test for OkHttp
 */
public class OkHttpTest extends HttpTest {
    public static final int SC_OK = 200;
    public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;


    OkHttpTest() {
        mClient = new OkHttpClient();
    }

    @Override
    void postData() throws Exception {
        super.postData();

        RequestBody body = RequestBody.create(TEXT, "This is just for okhttp test");
        mRequest = new Request.Builder().url(mUrl).post(body).build();
        mStartTime = System.currentTimeMillis();
        mResponse = mClient.newCall(mRequest).execute();
        mEndTime = System.currentTimeMillis();
        mStatusCode = mResponse.code();

        if (SC_OK == mStatusCode) {
            mResponseDataSize = mResponse.body().string().length();
        }
    }

    @Override
    void getData() throws Exception {
        super.getData();

        mRequest = new Request.Builder().url(mUrl).build();
        mStartTime = System.currentTimeMillis();
        mResponse = mClient.newCall(mRequest).execute();
        mEndTime = System.currentTimeMillis();
        mStatusCode = mResponse.code();

        if (SC_OK == mStatusCode) {
            mResponseDataSize = mResponse.body().string().length();
        }
    }
}
