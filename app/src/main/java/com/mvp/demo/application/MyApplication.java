package com.mvp.demo.application;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Administrator on 2016/9/21.
 * 主分支 master 主分支
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
    }
}