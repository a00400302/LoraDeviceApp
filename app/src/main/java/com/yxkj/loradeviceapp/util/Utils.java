package com.yxkj.loradeviceapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

public class Utils {
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context2) {
        context = context2.getApplicationContext();
    }

    public static Context getContext() {
        Context context2 = context;
        if (context2 != null) {
            return context2;
        }
        throw new NullPointerException("u should init first");
    }

    public static Activity getActivity(View view) {
        for (Context context2 = view.getContext(); context2 instanceof ContextWrapper; context2 = ((ContextWrapper) context2).getBaseContext()) {
            if (context2 instanceof Activity) {
                return (Activity) context2;
            }
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    public static String getString(int id) {
        return context.getResources().getString(id);
    }

    public static <T> T checkNotNull(T obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }
}