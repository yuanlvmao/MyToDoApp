package com.wutongtech.mytodoapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by wutongtech_shengmao on 18-8-23 14:36.
 * 作用：
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
