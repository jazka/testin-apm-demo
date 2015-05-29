// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Sqlite test Activity
 */
public class SqliteTestActivity extends Activity {
    private static final String DB_NAME = "apmdemo.db";
    private static final String TABLE_NAME = "production";
    public Button mInsertBtn;
    public Button mQueryBtn;
    public Button mUpdateBtn;
    public Button mDeleteBtn;
    public TextView mLogTextView;
    public SQLiteDatabase mSqliteDb = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_test);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSqliteDb != null) {
            mSqliteDb.close();
        }
    }

    /**
     * Initialization
     */
    public void init() {
        OnClickListener l = new ButtonListener();
        mInsertBtn = (Button) findViewById(R.id.insert_btn);
        mInsertBtn.setOnClickListener(l);

        mQueryBtn = (Button) findViewById(R.id.query_btn);
        mQueryBtn.setOnClickListener(l);

        mUpdateBtn = (Button) findViewById(R.id.update_btn);
        mUpdateBtn.setOnClickListener(l);

        mLogTextView = (Button) findViewById(R.id.delete_btn);
        mLogTextView.setOnClickListener(l);

        mLogTextView = (TextView) findViewById(R.id.log_tv);
        mLogTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        createDb();
    }

    /**
     * Create database only when activity creating
     */
    public void createDb() {
        try {
            mSqliteDb = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        } catch (SQLiteException e) {
            mLogTextView.setText(e.toString());
            return;
        }

        mLogTextView.setText("");
        execSqlWithLog("DROP TABLE IF EXISTS " + TABLE_NAME);
        execSqlWithLog("CREATE TABLE " + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price SMALLINT)");
    }

    /**
     * Test all kinds of insert methods
     */
    public void insert() throws SQLiteException {
        ContentValues cv = new ContentValues();
        cv.put("name", "book");
        cv.put("price", 5);
        mLogTextView.append("insert:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.insert(TABLE_NAME, null, cv);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        cv.clear();
        cv.put("name", "cup");
        cv.put("price", 6);
        mLogTextView.append("insertOrThrow:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.insertOrThrow(TABLE_NAME, null, cv);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        cv.clear();
        cv.put("name", "drink");
        cv.put("price", 8);
        mLogTextView.append("insertWithOnConflict:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
     * Test all kinds of query methods
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void query() throws SQLiteException {
        mLogTextView.append("rawQuery:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("rawQuery with CancellationSignal:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE price >= ?", new String[]{"4"}, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("rawQueryWithFactory:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.rawQueryWithFactory(null, "SELECT COUNT(*) FROM " + TABLE_NAME + "WHERE name = ?",
                new String[]{"book"}, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("rawQueryWithFactory with CancellationSignal:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.rawQueryWithFactory(null, "SELECT * FROM " + TABLE_NAME + " limit ?",
                new String[]{"2"}, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("query with limit:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.query(TABLE_NAME, new String[]{"name"}, null, null, null, null, null, new String("3"));
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("query with distinct, limit and CancellationSignal:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.query(true, TABLE_NAME, new String[]{"name"}, null, null, null, null, null, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("query:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.query(TABLE_NAME, null, null, null, null, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("query with distinct and limit:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.query(false, TABLE_NAME, new String[]{"price"}, null, null, null, null, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("queryWithFactory:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.queryWithFactory(null, true, TABLE_NAME, null, null, null, null, null, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        mLogTextView.append("queryWithFactory with CancellationSignal:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.queryWithFactory(null, true, TABLE_NAME, new String[]{"price"}, null, null,
                null, null, null, null, null);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
     * Test all kinds of update methods
     */
    public void update() throws SQLiteException {
        execSqlWithLog("UPDATE " + TABLE_NAME + " SET price = 10 WHERE name = \"drink\"");

        ContentValues cv = new ContentValues();
        cv.put("price", 2);
        mLogTextView.append("update:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.update(TABLE_NAME, cv, "name = ?", new String[]{"pen"});
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);

        cv.clear();
        cv.put("price", 7);
        mLogTextView.append("updateWithOnConflict:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.updateWithOnConflict(TABLE_NAME, cv, "name = ?", new String[]{"cup"},
                SQLiteDatabase.CONFLICT_IGNORE);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
     * Test all kinds of delete methods
     */
    public void delete() throws SQLiteException {
        execSqlWithLog("DELETE FROM " + TABLE_NAME + " WHERE price > 20");

        mLogTextView.append("delete:");
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.delete(TABLE_NAME, "name = ?", new String[]{"book"});
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
     * Overwrite execSql method with performance log
     */
    public void execSqlWithLog(String sql) {
        if (mSqliteDb == null) {
            return;
        }

        mLogTextView.append("execSQL: " + sql);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.execSQL(sql);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    /**
     * Overwrite execSql method with performance log
     */
    public void execSqlWithLog(String sql, Object[] bindArgs) {
        if (mSqliteDb == null) {
            return;
        }

        mLogTextView.append("execSQL with bindArgs: " + sql);
        Util.logCurrentTime(mLogTextView, Util.BEGIN_TAG);
        mSqliteDb.execSQL(sql, bindArgs);
        Util.logCurrentTime(mLogTextView, Util.END_TAG);
        Util.logSeparatorLine(mLogTextView);
    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (mSqliteDb == null) {
                return;
            }
            try {
                switch (v.getId()) {
                    case R.id.insert_btn:
                        insert();
                        break;
                    case R.id.query_btn:
                        query();
                        break;
                    case R.id.update_btn:
                        update();
                        break;
                    case R.id.delete_btn:
                        delete();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                mLogTextView.append("\r\n" + e.toString());
                Util.logSeparatorLine(mLogTextView);
            }
        }
    }
}
