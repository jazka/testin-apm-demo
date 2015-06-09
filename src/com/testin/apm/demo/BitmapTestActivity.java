// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Bitmap test Activity
 */
public class BitmapTestActivity extends Activity {
    private static final String ASSERT_IMAGE = "cat.jpg";
    private static final String SDCARD_IMAGE =
            Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/1425887387662.jpg";
    private static final String SDCARD_IMAGE1 =
            Environment.getExternalStorageDirectory().getPath() + "/DCIM/IMG_20150511_121511.jpg";
    private static final String HTTP_IMAGE =
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=f8ac12e5af6eddc426e7b2fa09dbb6a2/"
            + "42a98226cffc1e177cdab0314890f603738de94d.jpg";
    private static final String ASYNC_HTTP_IMAGE =
            "http://pic.baomihua.com/photos/201110/m_6_634545725224218750_16585344.jpg";
    private static final String PROGRESS_DIALOG_TITLE = "AsyncTask Test";
    private static final String PROGRESS_DIALOG_MESSAGE = "Wait to get the picture from internet...";
    private static final int TIME_OUT = 6 * 1000;
    private static final int BUFFER_SIZE = 4096;
    private BitmapFactory.Options mOptions;
    public Spinner mSpinner;
    public ImageView mBitmapTestIv;
    public TextView mLogTextView;
    private ProgressDialog mProgressDlg;
    public boolean mFstLogData;
    public boolean mUserSelectedSpi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_test);

        init();
    }

    /**
     * Initialization
     */
    public void init() {
        mFstLogData = true;
        mUserSelectedSpi = false;
        mOptions = new BitmapFactory.Options();
        mOptions.inSampleSize = 8;
        mOptions.inPurgeable = true;

        mSpinner = (Spinner) findViewById(R.id.bitmap_decode_methods_sp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Config.getDecodeMethods());
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());

        mLogTextView = (TextView) findViewById(R.id.log_tv);
        mLogTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        mBitmapTestIv = (ImageView) findViewById(R.id.bitmap_test_iv);

        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setTitle(PROGRESS_DIALOG_TITLE);
        mProgressDlg.setMessage(PROGRESS_DIALOG_MESSAGE);
        mProgressDlg.setCancelable(false);
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new AsyncTaskTest().execute(ASYNC_HTTP_IMAGE);
    }

    /**
     * Test for decodeStream method
     * @param method The flag of method tested
     */
    public void decodeStreamTest(String method) throws IOException, MalformedURLException, IOException {
        Bitmap bitmap = null;
        AssetManager asm = getAssets();
        InputStream is = asm.open(ASSERT_IMAGE);

        if (mFstLogData) {
            mLogTextView.setText(method);
            mFstLogData = false;
        } else {
            mLogTextView.append(method);
        }
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        bitmap = withOptions(method)
                        ? BitmapFactory.decodeStream(is, null, mOptions)
                        : BitmapFactory.decodeStream(is);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        is.close();
        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Test for decodeFile method
     * @param method The flag of method tested
     */
    public void decodeFileTest(String method) {
        Bitmap bitmap = null;

        mLogTextView.append(method);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        bitmap = withOptions(method)
                        ? BitmapFactory.decodeFile(SDCARD_IMAGE, mOptions)
                        : BitmapFactory.decodeFile(SDCARD_IMAGE);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Test for decodeByteArray method
     * @param method The flag of method tested
     */
    public void decodeByteArrayTest(String method) throws IOException {
        Bitmap bitmap = null;
        InputStream is = getHttpImage(HTTP_IMAGE);
        byte[] imageData = Util.inputStreamToByte(is, BUFFER_SIZE);

        mLogTextView.append(method);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        bitmap = withOptions(method)
                        ? BitmapFactory.decodeByteArray(imageData, 0, imageData.length, mOptions)
                        : BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        is.close();
        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Test for decodeFileDescriptor method
     * @param method The flag of method tested
     */
    public void decodeFileDescriptorTest(String method) throws IOException {
        Bitmap bitmap = null;
        FileInputStream fis = new FileInputStream(SDCARD_IMAGE1);

        mLogTextView.append(method);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        bitmap = withOptions(method)
                        ? BitmapFactory.decodeFileDescriptor(fis.getFD(), null, mOptions)
                        : BitmapFactory.decodeFileDescriptor(fis.getFD());
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        fis.close();
        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Test for decodeResource method
     * @param method The flag of method tested
     */
    public void decodeResourceTest(String method) {
        Bitmap bitmap = null;

        mLogTextView.append(method);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        bitmap = withOptions(method)
                        ? BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher, mOptions)
                        : BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Test for decodeResourceStream method
     * @param method The flag of method tested
     */
    public void decodeResourceStreamTest(String method) throws IOException {
        Bitmap bitmap = null;
        final TypedValue value = new TypedValue();
        InputStream is = getResources().openRawResource(R.drawable.ic_launcher, value);

        mLogTextView.append(method);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        BitmapFactory.decodeResourceStream(getResources(), value, is, null, mOptions);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        is.close();
        mBitmapTestIv.setImageBitmap(bitmap);
    }

    /**
     * Get the image from http
     * @param url The url
     * @return InputStream of the image
     */
    private InputStream getHttpImage(String url) throws MalformedURLException, IOException {
        InputStream is = null;
        URL imageUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
        conn.setConnectTimeout(TIME_OUT);
        conn.setRequestMethod("GET");
        conn.connect();
        is = conn.getInputStream();
        return is;
    }

    /**
     * Test for decodeResourceStream method
     * @param method The flag of method tested
     * @return true if the method contains options parameter, or false
     */
    private boolean withOptions(String method) {
        return method.contentEquals("Options");
    }

    class SpinnerOnSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (!mUserSelectedSpi) {
                mUserSelectedSpi = true;
                return;
            }

            String method = adapterView.getItemAtPosition(position).toString();
            try {
                switch(position) {
                    case 0:
                    case 1:
                        decodeStreamTest(method);
                        break;
                    case 2:
                    case 3:
                        decodeFileTest(method);
                        break;
                    case 4:
                    case 5:
                        decodeByteArrayTest(method);
                        break;
                    case 6:
                    case 7:
                        decodeFileDescriptorTest(method);
                        break;
                    case 8:
                    case 9:
                        decodeResourceTest(method);
                        break;
                    case 10:
                        decodeResourceStreamTest(method);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                mLogTextView.append(e.toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class AsyncTaskTest extends AsyncTask<String, Integer, InputStream> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDlg.show();
        }

        @Override
        protected InputStream doInBackground(String... params) {
            InputStream is = null;
            try {
                URL imageUrl = new URL(params[0]);
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

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(InputStream result) {
            super.onPostExecute(result);

            try {
                Bitmap bm = BitmapFactory.decodeStream(result);
                mBitmapTestIv.setImageBitmap(bm);
                mProgressDlg.dismiss();
            } catch (Exception e) {
                // Nothing
            }
        }
    }
}
