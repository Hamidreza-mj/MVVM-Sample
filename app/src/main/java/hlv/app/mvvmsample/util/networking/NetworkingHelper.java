package hlv.app.mvvmsample.util.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import hlv.app.mvvmsample.App;

public class NetworkingHelper {
    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = -1;

    private static NetworkingHelper networkingHelper;

    private NetworkingHelper() {
    }

    public static NetworkingHelper getNetworking() {
        if (networkingHelper == null)
            networkingHelper = new NetworkingHelper();

        return networkingHelper;
    }

    private int getConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }


    public boolean isOnline() {
        int connectionState = NetworkingHelper.getNetworking().getConnectivity(App.get().applicationContext);
        boolean status = false;

        if (connectionState == NetworkingHelper.TYPE_WIFI)
            status = true;
        else if (connectionState == NetworkingHelper.TYPE_MOBILE)
            status = true;
        else if (connectionState == NetworkingHelper.TYPE_NOT_CONNECTED)
            status = false;

        return status;
    }
}