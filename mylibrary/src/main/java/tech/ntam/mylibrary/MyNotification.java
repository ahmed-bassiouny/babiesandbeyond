package tech.ntam.mylibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Developer on 29/01/18.
 */

public class MyNotification {

    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public MyNotification(Context context,String title,String message) {
        this.context = context;
        mBuilder =
                new NotificationCompat.Builder(context, "channel_id")
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.appicon)
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
                        .setContentText(message);
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification() {
                mNotificationManager.notify(1, mBuilder.build());
    }
}
