package com.mvp.demo.application;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Administrator on 2016/9/21.
 * 这是分支step2 MVP+Dagger2
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
    }
}