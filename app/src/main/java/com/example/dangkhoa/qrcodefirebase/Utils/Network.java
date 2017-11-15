package com.example.dangkhoa.qrcodefirebase.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class Network {
    static int TYPE_WIFI = 1;
    static int TYPE_MOBILE = 2;
    static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo nwiWifi = cm.getNetworkInfo(cm.TYPE_WIFI);
        NetworkInfo nwiMobile = cm.getNetworkInfo(cm.TYPE_MOBILE);

        if (nwiWifi != null && nwiWifi.isConnected()) {
            return TYPE_WIFI;
        }

        if (nwiMobile != null && nwiMobile.isConnected()) {
            return TYPE_MOBILE;
        }

        return TYPE_NOT_CONNECTED;
    }

    public static boolean getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        if (conn == TYPE_WIFI) return true;

        if (conn == TYPE_MOBILE) return true;

        return false;
    }
}
