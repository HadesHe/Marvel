package com.avenging.hades.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Hades on 2017/5/16.
 */

public class AvengingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
