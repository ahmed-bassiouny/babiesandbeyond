package tech.ntam.babiesandbeyond.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyNotification;

/**
 * Created by bassiouny on 21/01/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new MyNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()).showNotification();
        Intent intent = null;
        try {

            // convert notification to json object
            JSONObject object = new JSONObject(remoteMessage.getData().values().toArray()[0].toString());
            // get notification type and create intent
            switch (object.get(IntentDataKey.NOTIFICATION_TYPE).toString()) {
                case IntentDataKey.NOTIFICATION_GROUP:
                    intent = new Intent(IntentDataKey.NOTIFICATION_GROUP);
                    break;
                case IntentDataKey.NOTIFICATION_SERVICE:
                    intent = new Intent(IntentDataKey.NOTIFICATION_SERVICE);
                    break;
            }
            // get action
            intent.putExtra(IntentDataKey.NOTIFICATION_ACTION, object.get(IntentDataKey.NOTIFICATION_ACTION).toString());
            // get id
            intent.putExtra(IntentDataKey.NOTIFICATION_ID,object.get(IntentDataKey.NOTIFICATION_ID).toString());

            broadcaster.sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
