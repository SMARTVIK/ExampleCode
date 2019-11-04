package com.spf.panditji.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.spf.panditji.util.Constants;

public class Utility {


    public static void setUserSignUpCompleted(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILE_NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.SIGN_UP,true).commit();
    }
}
