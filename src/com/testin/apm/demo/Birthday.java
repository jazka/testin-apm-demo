// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

/**
 * The class for Gson test
 */
public class Birthday {
    private int mYear;
    private int mMonth;
    private int mDay;

    /**
     * Constructor
     */
    public Birthday(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    @Override
    public String toString() {
        return "Birthday [year=" + mYear + ", month=" + mMonth + ", day=" + mDay + "]";
    }
}
