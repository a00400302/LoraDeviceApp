package com.yxkj.loradeviceapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.yxkj.loradeviceapp.App;

import java.lang.Thread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String TAG = "CrashHandler";
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private Map<String, String> infos = new HashMap();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        restartAPP(PathInterpolatorCompat.MAX_NUM_POINTS, 1);
        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    public void restartAPP(int delay, int code) {
        ((AlarmManager) App.getAppContext().getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + ((long) delay), PendingIntent.getActivity(App.getAppContext(), 0, App.getAppContext().getPackageManager().getLaunchIntentForPackage(App.getAppContext().getPackageName()), 1073741824));
        Process.killProcess(Process.myPid());
        System.exit(code);
    }
}