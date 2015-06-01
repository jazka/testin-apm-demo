// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class for Fragment test
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment1 extends Fragment {
    private static final String THEAD_NAME = "Testin";
    private static final String TOAST_TEXT = "Failed to get the image by HTTP";
    private static final String HTTP_IMAGE =
            "http://img5.imgtn.bdimg.com/it/u=3612145126,3157281017&fm=21&gp=0.jpg";
    private static final int TIME_OUT = 6 * 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startThread();
    }

    /**
     * Start thread to get image by http and show it
     */
    private void startThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = getHttpImage(HTTP_IMAGE);
                showImage(BitmapFactory.decodeStream(is));
            }
        });

        thread.setName(THEAD_NAME);
        thread.start();
    }

    /**
     * Show the image on UI thread
     * @param bm The bitmap of image
     */
    private void showImage(final Bitmap bm) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bm == null) {
                    Toast.makeText(getActivity(), TOAST_TEXT, Toast.LENGTH_LONG).show();
                } else {
                    ImageView iv = (ImageView) getActivity().findViewById(R.id.thread_test_iv);
                    iv.setImageBitmap(bm);
                }
            }
        });
    }

    /**
     * Get the image from http
     * @param url The url
     * @return InputStream of the image
     */
    private InputStream getHttpImage(String url) {
        InputStream is = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            // Nothing
        } catch (IOException e) {
            // Nothing
        }
        return is;
    }
}
