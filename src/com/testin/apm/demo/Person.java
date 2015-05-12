// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

/**
 * The class for Gson test
 */
public class Person {
    private String mName;
    private int mAge;
    private Birthday mBirthday;

    /**
     * Constructor
     */
    public Person(String name, int age, Birthday birthday) {
        mName = name;
        mAge = age;
        mBirthday = birthday;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public Birthday getBirthday() {
        return mBirthday;
    }

    public void setBirthday(Birthday birthday) {
        mBirthday = birthday;
    }

    @Override
    public String toString() {
        return "Person [name=" + mName + ", age=" + mAge + ", birthday=" + mBirthday + "]";
    }
}
