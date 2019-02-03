package com.pisarev.nytimes;

import android.app.Application;

import com.pisarev.nytimes.retrofit.RetroClient;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetroClient.init();
    }
}
