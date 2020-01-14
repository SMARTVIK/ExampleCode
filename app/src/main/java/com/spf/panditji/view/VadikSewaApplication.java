package com.spf.panditji.view;

import android.app.Application;
import android.content.SharedPreferences;

import com.spf.panditji.util.Constants;

public class VadikSewaApplication extends Application {

    private static VadikSewaApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
         instance = this;
    }

    public SharedPreferences getSharedPrefs(){
        return getSharedPreferences (Constants.SHARED_PREFS, MODE_PRIVATE);
    }

    public static VadikSewaApplication getInstance(){
        return instance;
    }

}
