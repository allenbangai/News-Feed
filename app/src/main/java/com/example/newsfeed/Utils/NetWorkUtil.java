package com.example.newsfeed.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.SafeBrowsingResponse;

public class NetWorkUtil {

    public NetWorkUtil(){

    }

    public String getInternetConnectivityStatus(Context context){
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null){
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
            } else{
                status = "No internet is available";
            }
        }else {
            status = "No internet is available";
        }
        return status;
    }
}
