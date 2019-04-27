package com.example.vladislavsvasiljevs.pchub.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public static final String FIREBASE_TOKEN = "AAAA4_XhGR8:APA91bEcmjPYhU5sPgr549gen8c3Xl1wvMJDASHDsqMExExfZwT2dWRJ7MkGYGMSps62Gf_3a-xja_jlefzGmRcFAzEkN0yCOvQAVa4ITrybTHEp10VeTBuaoIzCWpbeVQRH_TkZbl4o";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(FIREBASE_TOKEN, refreshedToken).apply();
    }

}