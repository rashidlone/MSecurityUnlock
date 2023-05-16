package com.rashedlone.msecurityunlock;

/**
 * Created by Rashedlone0 on 2-Jan-17.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap, bitmap_main;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



       //notification
        String noti_title = remoteMessage.getData().get("noti_title");
        String imageUri = remoteMessage.getData().get("noti_image");

        //info for activity
        String main_image = remoteMessage.getData().get("main_image");
        String main_header = remoteMessage.getData().get("main_header");
        String main_info = remoteMessage.getData().get("main_info");
        String main_button = remoteMessage.getData().get("main_button");
        String main_button_link = remoteMessage.getData().get("main_button_link");




        //To get a Bitmap image from the URL received
        bitmap = getBitmapfromUrl(imageUri);
        bitmap_main = getBitmapfromUrl(main_image);

        sendNotification(noti_title, bitmap, bitmap_main, main_header, main_info, main_button, main_button_link);

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String noti_title, Bitmap image, Bitmap image_main, String main_header, String main_info, String main_button, String main_button_link) {
        Intent intent = new Intent(this, Notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       //pass data to Notification.java
        intent.putExtra("main_image", image_main);
        intent.putExtra("main_header", main_header);
        intent.putExtra("main_info", main_info);
        intent.putExtra("main_button", main_button);
        intent.putExtra("main_button_link", main_button_link);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(image)/*Notification icon image*/
        .setSmallIcon(getNotificationIcon()).setContentTitle(getResources().getString(R.string.app_name)).setSubText("").setContentText(noti_title)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true).setOngoing(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_about : R.drawable.ic_launcher;
    }
}
