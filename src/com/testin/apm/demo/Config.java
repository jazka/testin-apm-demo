// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Config for the application
 */
public class Config {
    static final String TEST_TYPE_KEY = "test_type_key";
    static final String TYPE_HTTP_CLIENT = "test_http_client";
    static final String TYPE_HTTP_URL = "test_http_url_connection";
    static final String TYPE_OK_HTTP = "test_ok_http";

    private static final List<String> URLLIST = new ArrayList<String>() { {
        add("http://www.baidu.com/");
        add("https://download.oneapm.com/android_agent/eclipse/");
        add("http://crash.testin.cn/cpi/crash");
        add("http://121.40.57.182:8080/apiserver/new/test/error");
    } };

    private static final List<String> DECODE_METHODS = new ArrayList<String>() { {
        add("decodeStream");
        add("decodeStream with Options");
        add("decodeFile");
        add("decodeFile with Options");
        add("decodeByteArray");
        add("decodeByteArray with Options");
        add("decodeFileDescriptor");
        add("decodeFileDescriptor with Options");
        add("decodeResource");
        add("decodeResource with Options");
        add("decodeResourceStream");
    } };

    static List<String> getUrlList() {
        return URLLIST;
    }

    static String getDefaultUrl() {
        return URLLIST.get(0);
    }

    static List<String> getDecodeMethods() {
        return DECODE_METHODS;
    }

    static String getDefaultDecodeMethod() {
        return DECODE_METHODS.get(0);
    }
}
