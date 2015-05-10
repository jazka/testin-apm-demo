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
    private static final String SEPERATOR_LINE = "---------";
    private static final String BEGIN_TAG = "Begin: ";
    private static final String END_TAG = "End: ";
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
        execSqlWithLog("INSERT INTO " + TABLE_NAME + " (name, price) VALUES (?, ?)",
                new Object[]{ "pen", 4 });

        ContentValues cv = new ContentValues();
        cv.put("name", "book");
        cv.put("price", 5);
        mLogTextView.append("insert:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.insert(TABLE_NAME, null, cv);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        cv.clear();
        cv.put("name", "cup");
        cv.put("price", 6);
        mLogTextView.append("insertOrThrow:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.insertOrThrow(TABLE_NAME, null, cv);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        cv.clear();
        cv.put("name", "drink");
        cv.put("price", 8);
        mLogTextView.append("insertWithOnConflict:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
     * Test all kinds of query methods
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void query() throws SQLiteException {
        execSqlWithLog("SELECT * FROM " + TABLE_NAME + " WHERE price > ?", new String[]{"6"});

        mLogTextView.append("rawQuery:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("rawQuery with CancellationSignal:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE price >= ?", new String[]{"4"}, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("rawQueryWithFactory:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.rawQueryWithFactory(null, "SELECT COUNT(*) FROM " + TABLE_NAME + "WHERE name = ?",
                new String[]{"book"}, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("rawQueryWithFactory with CancellationSignal:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.rawQueryWithFactory(null, "SELECT * FROM " + TABLE_NAME + " limit ?",
                new String[]{"2"}, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("query with limit:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.query(TABLE_NAME, new String[]{"name"}, null, null, null, null, null, new String("3"));
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("query with distinct, limit and CancellationSignal:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.query(true, TABLE_NAME, new String[]{"name"}, null, null, null, null, null, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("query:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.query(TABLE_NAME, null, null, null, null, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("query with distinct and limit:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.query(false, TABLE_NAME, new String[]{"price"}, null, null, null, null, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("queryWithFactory:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.queryWithFactory(null, true, TABLE_NAME, null, null, null, null, null, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();

        mLogTextView.append("queryWithFactory with CancellationSignal:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.queryWithFactory(null, true, TABLE_NAME, new String[]{"price"}, null, null,
                null, null, null, null, null);
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
     * Test all kinds of update methods
     */
    public void update() throws SQLiteException {
        execSqlWithLog("UPDATE " + TABLE_NAME + " SET price = 10 WHERE name = \"drink\"");

        ContentValues cv = new ContentValues();
        cv.put("price", 2);
        mLogTextView.append("update:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.update(TABLE_NAME, cv, "name = ?", new String[]{"pen"});
        logCurrentTime(END_TAG);
        logSeparatorLine();

        cv.clear();
        cv.put("price", 7);
        mLogTextView.append("updateWithOnConflict:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.updateWithOnConflict(TABLE_NAME, cv, "name = ?", new String[]{"cup"},
                SQLiteDatabase.CONFLICT_IGNORE);
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
     * Test all kinds of delete methods
     */
    public void delete() throws SQLiteException {
        execSqlWithLog("DELETE FROM " + TABLE_NAME + " WHERE price > 20");

        mLogTextView.append("delete:");
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.delete(TABLE_NAME, "name = ?", new String[]{"book"});
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
     * Overwrite execSql method with performance log
     */
    public void execSqlWithLog(String sql) {
        if (mSqliteDb == null) {
            return;
        }

        mLogTextView.append("execSQL: " + sql);
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.execSQL(sql);
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
     * Overwrite execSql method with performance log
     */
    public void execSqlWithLog(String sql, Object[] bindArgs) {
        if (mSqliteDb == null) {
            return;
        }

        mLogTextView.append("execSQL with bindArgs: " + sql);
        logCurrentTime(BEGIN_TAG);
        mSqliteDb.execSQL(sql, bindArgs);
        logCurrentTime(END_TAG);
        logSeparatorLine();
    }

    /**
    * Log current time
    * @param tag The tag of performance type
    */
    public void logCurrentTime(String tag) {
        long tm = System.currentTimeMillis();
        mLogTextView.append("\r\n" + tag + Long.toString(tm));
    }

    /**
    * Log separator line to make more readable
    */
    public void logSeparatorLine() {
        mLogTextView.append("\r\n" + SEPERATOR_LINE + "\r\n");
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
                logSeparatorLine();
            }
        }
    }
}
