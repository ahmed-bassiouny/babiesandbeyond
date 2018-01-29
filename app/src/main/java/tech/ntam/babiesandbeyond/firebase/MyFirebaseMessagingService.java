package tech.ntam.babiesandbeyond.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import tech.ntam.mylibrary.MyNotification;

/**
 * Created by bassiouny on 21/01/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new MyNotification(this,"","").showNotification();
    }
}
