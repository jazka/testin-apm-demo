// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
//
// Copyright (C) 2015 Testin.  All rights reserved.
//
// This file is an original work developed by Testin

package com.testin.apm.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.testin.agent.TestinAgent;
import com.testin.agent.TestinCheckUpdateHandler;
/**
 * Http test Activity
 */
public class UpdateCheckingActivity extends Activity {
    public Button mDefaultCheckingBtn;
    public Button mCustomerCheckingBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_checking);

        init();
    }

    /**
     * Initialization
     */
    public void init() {

        OnClickListener l = new ButtonListener();
        mDefaultCheckingBtn = (Button) findViewById(R.id.default_checking_btn);
        mDefaultCheckingBtn.setOnClickListener(l);

        mCustomerCheckingBtn = (Button) findViewById(R.id.customer_checking_btn);
        mCustomerCheckingBtn.setOnClickListener(l);
    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.default_checking_btn:
                    TestinAgent.checkUpdate(UpdateCheckingActivity.this);
                    break;
                case R.id.customer_checking_btn:
                    TestinAgent.checkUpdate(UpdateCheckingActivity.this, new TestinCheckUpdateHandler() {
                        @Override
                        public void onUpdate(final String notes, final String url) {
                            if (TextUtils.isEmpty(url)) {
                                return;
                            }

                            new AlertDialog.Builder(UpdateCheckingActivity.this)
                                .setTitle("Customer更新")
                                .setMessage(notes)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                Uri uri = Uri.parse(url);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                //
                                            }
                                        }
                                    })
                                .setNegativeButton("取消", null)
                                .show();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

}
