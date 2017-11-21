package com.example.somnath.kart.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SOMNATH on 19-11-2017.
 */


public class CheckNetwork extends Activity{
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
               this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
