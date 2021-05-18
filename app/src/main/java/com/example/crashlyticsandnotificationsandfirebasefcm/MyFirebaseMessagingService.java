package com.example.crashlyticsandnotificationsandfirebasefcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    @Override
    public void onNewToken(@NonNull String s) {

        super.onNewToken(s);
        Log.i(TAG, "onNewToken: "+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String,String> map= remoteMessage.getData();
            String name=map.get("name");
            String phone=map.get("phone");
            String address=map.get("address");

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.i(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            showNotifications(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    public void showNotifications(String title ,String bodey){

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
String NOTIFICATION_CHANNEL_ID="ayaId";

if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

    NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"News channel",NotificationManager.IMPORTANCE_HIGH);
    notificationChannel.setLightColor(Color.RED);
    notificationChannel.enableLights(true);
    notificationChannel.enableVibration(true);
    notificationChannel.setVibrationPattern(new long[]{0,500,0,500});
    notificationManager.createNotificationChannel(notificationChannel);

}


NotificationCompat.Builder  notificationBuilder= new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID);
notificationBuilder.setAutoCancel(true)
        .setWhen(System.currentTimeMillis()).setShowWhen(true).setContentTitle(title).setContentText(bodey).setSmallIcon(R.mipmap.ic_launcher);
notificationManager.notify(0,notificationBuilder.build());
    }
}