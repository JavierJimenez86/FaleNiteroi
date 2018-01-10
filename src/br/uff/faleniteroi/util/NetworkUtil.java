package br.uff.faleniteroi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	
	public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        
        return activeNetwork != null ? true : false;
    }
	
}