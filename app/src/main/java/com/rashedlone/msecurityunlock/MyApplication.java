package com.rashedlone.msecurityunlock;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rashedlone0 on 3/5/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();

    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
