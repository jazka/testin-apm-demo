// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utils of common methods
 */
public class Util {
    private static final String SEPERATOR_LINE = "---------";
    public static final String BEGIN_TAG = "Begin: ";
    public static final String END_TAG = "End: ";

    /**
    * Log separator line to make more readable
    * @param tv The TextView
    */
    public static void logSeparatorLine(TextView tv) {
        tv.append("\r\n" + SEPERATOR_LINE + "\r\n");
    }

    /**
    * Log current time
    * @param tv The TextView
    * @param tag The tag of performance type
    */
    public static void logCurrentTime(TextView tv, String tag) {
        long tm = System.currentTimeMillis();
        tv.append("\r\n" + tag + Long.toString(tm));
    }

    /**
    * Translate inputstream to byte array
    * @param in The inputstream
    * @param bufSize The buffer size of byte array
    */
    public static byte[] inputStreamToByte(InputStream in, int bufSize) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[bufSize];
        int count = -1;
        while ((count = in.read(data, 0, bufSize)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        return outStream.toByteArray();
    }
}
