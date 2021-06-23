package com.yxkj.loradeviceapp;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.yxkj.loradeviceapp.util.Utils;

public class App extends Application {
    private static App sInstance;

    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        Utils.init(getAppContext());
        Hawk.init(getAppContext()).setLogInterceptor(new LogInterceptor() {
            /* class com.example.loradevicesapp.App.AnonymousClass1 */

            @Override // com.orhanobut.hawk.LogInterceptor
            public void onLog(String message) {
                Logger.d(message);
            }
        }).build();
    }

    public static Context getAppContext() {
        return sInstance;
    }
}