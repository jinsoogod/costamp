package com.hanium.costamp;

/**
 * Created by korea on 2016-07-28.
 */
//최종작업일자 20160728 15:21
//최종작업자 : 이은영


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;


public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String result = data.getString("result");

        //큰 아이콘
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        //알림 사운드
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //알림 클릭시 이동할 인텐트 (사진전송 dialog)
        Intent intent = new Intent(this, picture_transmission_dialog.class);
        //노티피케이션을 생성할때 매개변수는 PendingIntent이므로 Intent를 PendingIntent로 만들어주어야함.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //노티피케이션 빌더 : 위에서 생성한 이미지나 텍스트, 사운드등을 설정해줍니다.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setLargeIcon(bitmap)
                .setContentTitle("코스탬프(COSTAMP)")
                .setContentText(result)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //노티피케이션을 생성합니다.
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

}
