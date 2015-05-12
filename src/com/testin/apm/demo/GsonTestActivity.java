// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson and Android Json test Activity
 */
public class GsonTestActivity extends Activity {
    public Button mGsonBtn;
    public Button mJsonBtn;
    public TextView mLogTextView;
    public boolean mFstLogData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gson_test);

        init();
    }

    /**
     * Initialization
     */
    public void init() {
        mFstLogData = true;

        OnClickListener l = new ButtonListener();
        mGsonBtn = (Button) findViewById(R.id.gson_btn);
        mGsonBtn.setOnClickListener(l);

        mJsonBtn = (Button) findViewById(R.id.json_btn);
        mJsonBtn.setOnClickListener(l);

        mLogTextView = (TextView) findViewById(R.id.log_tv);
        mLogTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
    * Test for gson methods
    */
    public void gsonTest() throws IOException, JsonSyntaxException {
        Person person;
        String jsonString;
        String listString;
        Type typeofSrc;
        Gson gson = new Gson();
        List<Person> list = new ArrayList<Person>();
        JsonObject jsonObject = new JsonObject();

        person = new Person("zhaokaiqiang", 22, new Birthday(1992, 1, 19));

        setLogData("toJson: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jsonString = gson.toJson(person);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with String and Class: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(jsonString, Person.class);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with Reader and Class: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(new StringReader(jsonString), Person.class);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        list.add(person);
        list.add(person);
        typeofSrc = new TypeToken<List<Person>>(){}.getType();
        setLogData("toJson with Type: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        listString = gson.toJson(list, typeofSrc);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with String and Type: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(listString, typeofSrc);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("toJson with Appendable: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.toJson(person, new StringWriter());
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("toJson with Type and Appendable: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.toJson(list, typeofSrc, new StringWriter());
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with Reader and Type: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(new StringReader(listString), typeofSrc);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with JsonReader and Type: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(new JsonReader(new StringReader(jsonString)), Person.class);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("toJson with Reader and JsonWriter: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.toJson(list, typeofSrc, new JsonWriter(new StringWriter()));
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        jsonObject.addProperty("name", "zhao");
        jsonObject.addProperty("age", 22);
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        jsonObject.add("person", jsonElement);
        setLogData("toJson with JsonElement: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jsonString = gson.toJson(jsonObject);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("toJson with JsonElement and Appendable: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.toJson(jsonObject, new StringWriter());
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("toJson with JsonElement and JsonWriter: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.toJson(jsonObject, new JsonWriter(new StringWriter()));
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with JsonElement and Class: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(jsonObject.get("person"), Person.class);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("fromJson with JsonElement and Type: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        gson.fromJson(jsonObject, new TypeToken<Object>(){}.getType());
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
    * Test for Android json methods
    */
    public void jsonTest() throws JSONException {
        String jsonStr = "{\"total\":300,\"url\":\"http:wap.abc.com\",\"bizs\": {"
                + "\"biz\":[{\"id\":555555,\"name\":\"兰州烧饼\",\"add\":\"北京市海定区中关村\"},"
                + "{\"id\":666666,\"name\":\"兰州拉面\",\"add\":\"北京市海定区中关村\"},"
                + "{\"id\":888888,\"name\":\"肯德基\",\"add\":\"北京市海定区中关村\"}]}}";

        setLogData("JSONObject constructor: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        JSONObject jObject = new JSONObject(jsonStr);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("JSONObject toString: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jObject.toString();
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("JSONObject toString with indentSpaces: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jObject.toString(3);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        String jsonArrayStr =
                "[ { \"keyValueId\": 1, \"cityCode\": \"3501\", \"cityName\": \"福州\","
                    + "\"keyName\": \"housFundAccount\", \"keyText\": \"账号\","
                    + "\"keyDesc\": \"公积金的账号\",\"keyValue\":\"350104198001019999\","
                    + "\"isPrivate\": \"0\" },"
                    + "{ \"keyValueId\": 2, \"cityCode\": \"3501\", \"cityName\": \"福州\","
                    + "\"keyName\": \"housFundPassword\", \"keyText\": \"密码\","
                    + "\"keyDesc\": \"公积金的密码\", \"keyValue\": \"9999\", \"isPrivate\": \"1\" }"
                    + "]";

        setLogData("JSONArray constructor: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        JSONArray jArray = new JSONArray(jsonArrayStr);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("JSONArray toString: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jArray.toString();
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        setLogData("JSONArray toString with indentSpaces: ");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        jArray.toString(3);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
    * Set the TextView context with log data
    */
    public void setLogData(String logData) {
        if (mFstLogData) {
            mLogTextView.setText(logData);
            mFstLogData = false;
        } else {
            mLogTextView.append(logData);
        }
    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.gson_btn:
                        gsonTest();
                        break;
                    case R.id.json_btn:
                        jsonTest();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                setLogData(e.toString());
                Util.logSeparatorLine(mLogTextView);
            }
        }
    }
}
