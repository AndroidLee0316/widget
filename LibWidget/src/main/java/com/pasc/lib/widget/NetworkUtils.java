package com.pasc.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkUtils {
    public NetworkUtils() {
    }

    public static boolean isWifiConnect(Context context) {
        @SuppressLint("WrongConstant") ConnectivityManager connManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo mWifiInfo = connManager.getNetworkInfo(1);
        return mWifiInfo.isConnected();
    }

    public static boolean isNetworkAvailable(Context context) {
        @SuppressLint("WrongConstant") ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            @SuppressLint("WrongConstant") ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            @SuppressLint("WrongConstant") ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(1);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            @SuppressLint("WrongConstant") ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(0);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static final boolean ping() {
        String result = null;

        try {
            try {
                String ip = "www.baidu.com";
                Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);
                InputStream input = p.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                StringBuffer stringBuffer = new StringBuffer();
                String content = "";

                while((content = in.readLine()) != null) {
                    stringBuffer.append(content);
                }

                int status = p.waitFor();
                if (status == 0) {
                    result = "success";
                    boolean var8 = true;
                    return var8;
                }

                result = "failed";
            } catch (IOException var13) {
                result = "IOException";
            } catch (InterruptedException var14) {
                result = "InterruptedException";
            }

            return false;
        } finally {
            ;
        }
    }

}
