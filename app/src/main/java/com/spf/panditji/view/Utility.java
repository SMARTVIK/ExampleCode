package com.spf.panditji.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.spf.panditji.util.Constants;
import com.spf.panditji.view.fragment.HomeFragment;

public class Utility {


    public static void setUserSignUpCompleted(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILE_NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.SIGN_UP,true).commit();
    }

    public static boolean checkInternetConnection(Context homeFragment) {
        ConnectivityManager cm = (ConnectivityManager) homeFragment.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
