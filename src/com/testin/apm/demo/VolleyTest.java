// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Test for VolleyTest
 */
public class VolleyTest extends HttpTest {
    public static final int SC_OK = 200;
    public Context mContext;

    VolleyTest(Context context) {
        mContext = context;
    }

    @Override
    void postData() throws Exception {
        super.postData();

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mResponseDataSize = response.length();
                        mStatusCode = SC_OK;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mStatusCode = error.networkResponse.statusCode;
                    }
                });
        // Add the request to the RequestQueue.
        mStartTime = System.currentTimeMillis();
        queue.add(stringRequest);
        queue.start();
        mEndTime = System.currentTimeMillis();
    }

    @Override
    void getData() throws Exception {
        super.getData();

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mResponseDataSize = response.length();
                        mStatusCode = SC_OK;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mStatusCode = error.networkResponse.statusCode;
                    }
                });
        // Add the request to the RequestQueue.
        mStartTime = System.currentTimeMillis();
        queue.add(stringRequest);
        queue.start();
        mEndTime = System.currentTimeMillis();
    }
}
