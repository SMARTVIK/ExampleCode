package com.spf.panditji.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public final class L {
    private static onLogAdded loglistener;

    public interface onLogAdded {
        void onAdded(String msg);
    }

    public static void bind(onLogAdded onLogAdded) {
        L.loglistener = onLogAdded;
    }

    private static final String LOG_TAG = "Vaidik-Sewa";

    private static Boolean SHOW_LOGS = true;

    public static void initLogs(boolean ShowLogs){
        L.SHOW_LOGS = ShowLogs;
    }

    public static void d(Object... objects) {
        if (SHOW_LOGS)
            for (Object object : objects) {
                Log.d(LOG_TAG, (getCodeLocation().toString()) + " " + (object == null ? "null" : "" + object));
                if (loglistener != null)
                    loglistener.onAdded(object == null ? "null" : "" + object);
            }
    }

    public static void json(Object json) {
        if (SHOW_LOGS)
            if (json == null) {
                Log.d(LOG_TAG, "null");
            } else if (json instanceof JSONObject)
                try {
                    Log.d(LOG_TAG, getCodeLocation().toString() + " " + ((JSONObject) json).toString(4));
                } catch (JSONException e) {
                    wtf(e);
                }
            else
                try {
                    Log.d(LOG_TAG, getCodeLocation().toString() + " " + (new JSONObject(json.toString()).toString(4)));
                } catch (JSONException e) {
                    wtf(e);
                }
    }

    public static void e(Object... objects) {
        if (SHOW_LOGS)
            for (Object object : objects)
                Log.e(LOG_TAG, getCodeLocation().toString() + " " + (object == null ? "null" : "" + object));
    }

    public static void i(Object... objects) {
        if (SHOW_LOGS)
            for (Object object : objects)
                Log.i(LOG_TAG, getCodeLocation().toString() + " " + (object == null ? "null" : "" + object));
    }

    public static void v(Object... objects) {
        if (SHOW_LOGS)
            for (Object object : objects)
                Log.v(LOG_TAG, getCodeLocation().toString() + " " + (object == null ? "null" : "" + object));
    }

    public static void w(Object... objects) {
        if (SHOW_LOGS)
            for (Object object : objects)
                Log.w(LOG_TAG, getCodeLocation().toString() + " " + (object == null ? "null" : "" + object));
    }

    public static void wtf(Throwable... throwables) {
        if (SHOW_LOGS)
            for (Throwable throwable : throwables) {
                Log.wtf(LOG_TAG, throwable == null ? new Throwable() {
                    @Override
                    public String getMessage() {
                        return getCodeLocation().toString() + " null";
                    }
                } : throwable);
                if (loglistener != null && throwable != null) {
                    loglistener.onAdded(Log.getStackTraceString(throwable));
                }
            }
    }

    private static CodeLocation getCodeLocation() {
        return getCodeLocation(3);
    }

    private static CodeLocation getCodeLocation(int depth) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement[] filteredStackTrace = new StackTraceElement[stackTrace.length - depth];
        System.arraycopy(stackTrace, depth, filteredStackTrace, 0, filteredStackTrace.length);
        return new CodeLocation(filteredStackTrace);
    }

    private static class CodeLocation {
        final String mFileName;
        final int mLineNumber;
        final StackTraceElement[] mStackTrace;


        private CodeLocation(StackTraceElement[] stackTrace) {
            mStackTrace = stackTrace;
            StackTraceElement root = stackTrace[0];
            mFileName = root.getFileName();
            mLineNumber = root.getLineNumber();
        }


        @Override
        public String toString() {
            return "(" +
                    mFileName +
                    ':' +
                    mLineNumber +
                    ") ";
        }
    }
}