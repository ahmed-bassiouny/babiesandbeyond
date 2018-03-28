package tech.ntam.adminapp.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import tech.ntam.adminapp.model.MidwifeService;
import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyNotification;

/**
 * Created by Developer on 21/01/18.
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
        //Toast.makeText(this,"",Toast.LENGTH_LONG);
        new MyNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody()).showNotification();
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        /*try {
            JSONObject object = new JSONObject(intent.getExtras().get("a_data").toString());
            MidwifeService request = new Gson().fromJson(object.get("service_obj").toString(),MidwifeService.class);
            Log.e( "handleIntent: ",request.getUniqueKey()+"" );
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        Log.e("handleIntent: ",intent.getExtras().get("a_data").toString() );
        try {
            Intent myIntent;
            // convert notification to json object
            JSONObject object = new JSONObject(intent.getExtras().get("a_data").toString());
            // get action
            String action = object.get(IntentDataKey.NOTIFICATION_ACTION).toString();
            // get notification type and create intent
            switch (object.get(IntentDataKey.NOTIFICATION_TYPE).toString()) {
                case IntentDataKey.NOTIFICATION_NURSE:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_NURSE);
                    if (action.equals(IntentDataKey.ADD_REQUEST)) {
                        Request nurse = new Gson().fromJson(object.get(IntentDataKey.NOTIFICATION_SERVICE_OBJECT).toString(), Request.class);
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_SERVICE_OBJECT, nurse);
                    } else {
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_ID, object.get(IntentDataKey.NOTIFICATION_ID).toString());
                    }
                    break;
                case IntentDataKey.NOTIFICATION_BABYSITTER:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_BABYSITTER);
                    if (action.equals(IntentDataKey.ADD_REQUEST)) {
                        Request babysitter = new Gson().fromJson(object.get(IntentDataKey.NOTIFICATION_SERVICE_OBJECT).toString(), Request.class);
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_SERVICE_OBJECT, babysitter);
                    } else {
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_ID, object.get(IntentDataKey.NOTIFICATION_ID).toString());
                    }
                    break;
                case IntentDataKey.NOTIFICATION_WORKSHOP:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_WORKSHOP);
                    if (action.equals(IntentDataKey.ADD_REQUEST)) {
                        Workshop workshop = new Gson().fromJson(object.get(IntentDataKey.NOTIFICATION_WORKSHOP_OBJECT).toString(), Workshop.class);
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_WORKSHOP_OBJECT, workshop);
                    } else {
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_ID, object.get(IntentDataKey.NOTIFICATION_ID).toString());
                    }
                    break;
                case IntentDataKey.NOTIFICATION_MIDWIFE:
                    myIntent = new Intent(IntentDataKey.NOTIFICATION_MIDWIFE);
                    if (action.equals(IntentDataKey.ADD_REQUEST)) {
                        MidwifeService midwifeService = new Gson().fromJson(object.get(IntentDataKey.NOTIFICATION_SERVICE_OBJECT).toString(), MidwifeService.class);
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_SERVICE_OBJECT, midwifeService);
                    } else {
                        myIntent.putExtra(IntentDataKey.NOTIFICATION_ID, object.get(IntentDataKey.NOTIFICATION_ID).toString());
                    }
                    break;
                default:
                    return;
            }
            // get action
            myIntent.putExtra(IntentDataKey.NOTIFICATION_ACTION, object.get(IntentDataKey.NOTIFICATION_ACTION).toString());
            broadcaster.sendBroadcast(myIntent);
        } catch (JSONException e) {
        }
    }

}
