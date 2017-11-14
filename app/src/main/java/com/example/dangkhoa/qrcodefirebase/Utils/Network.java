package com.example.dangkhoa.qrcodefirebase.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class Network {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI: {
                    return TYPE_WIFI;
                }
                case ConnectivityManager.TYPE_MOBILE: {
                    return TYPE_MOBILE;
                }
                default: return TYPE_NOT_CONNECTED;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        boolean status = false ;
        if (conn == TYPE_WIFI) {
            status = true;
        } else if (conn == TYPE_MOBILE) {
            status = true;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = false;
        }
        return status;
    }
}
