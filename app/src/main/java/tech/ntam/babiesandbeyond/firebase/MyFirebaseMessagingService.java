package tech.ntam.babiesandbeyond.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        new MyNotification(this, intent.getExtras().get("gcm.notification.title").toString(), intent.getExtras().get("gcm.notification.body").toString()).showNotification();
        Intent myIntent = null;
        try {

            // convert notification to json object
            JSONObject object = new JSONObject(intent.getExtras().get("a_data").toString());
            // get notification type and create intent
            switch (object.get(IntentDataKey.NOTIFICATION_TYPE).toString()) {
                case IntentDataKey.NOTIFICATION_GROUP:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_GROUP);
                    break;
                case IntentDataKey.NOTIFICATION_SERVICE:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_SERVICE);
                    break;
                case IntentDataKey.NOTIFICATION_WORKSHOP:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_WORKSHOP);
                    break;
                case IntentDataKey.NOTIFICATION_EVENTS:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_EVENTS);
                    break;
                default:
                    return;
            }
            // get action
            myIntent.putExtra(IntentDataKey.NOTIFICATION_ACTION, object.get(IntentDataKey.NOTIFICATION_ACTION).toString());
            // get id
            myIntent.putExtra(IntentDataKey.NOTIFICATION_ID, object.get(IntentDataKey.NOTIFICATION_ID).toString());

            broadcaster.sendBroadcast(myIntent);
        } catch (JSONException e) {
        }
    }
}
