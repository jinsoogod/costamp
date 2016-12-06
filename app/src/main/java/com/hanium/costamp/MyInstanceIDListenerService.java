package com.hanium.costamp;

/**
 * Created by korea on 2016-07-28.
 */
//최종작업일자 20160728 02:05
//최종작업자 : 이은영

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Log.e("refresh","refresh");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}