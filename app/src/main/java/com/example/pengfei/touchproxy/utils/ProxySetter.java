package com.example.pengfei.touchproxy.utils;

import android.content.ContentResolver;
import android.provider.Settings.Secure;
import android.util.Log;


public class ProxySetter {
    private static final String TAG = "PROXY_SETTER";

    public boolean turnOnProxy(ContentResolver cr, String proxy_addr_port) {
        Log.v(TAG, proxy_addr_port);
        if (Secure.putString(cr, Secure.HTTP_PROXY, proxy_addr_port)) {
            String proxy_addr_set = Secure.getString(cr, Secure.HTTP_PROXY);
            Log.v(TAG, proxy_addr_set);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean turnOffProxy(ContentResolver cr) {
        if (Secure.putString(cr, Secure.HTTP_PROXY, "")) {
            return true;
        }
        else {
            return false;
        }
    }
}