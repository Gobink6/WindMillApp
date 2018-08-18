package com.robotics.katamaron.windmill;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceServices extends FirebaseInstanceIdService {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String DevicesToken = "DevicesToken";

    @Override
    public void onTokenRefresh() {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        String refeshtoken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase",  refeshtoken);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(DevicesToken, refeshtoken);
        editor.commit();

    }
}
