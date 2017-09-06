package com.example.administrator.musicplayer;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Yellow on 2017/9/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        setupLeakCanary();
    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
}
