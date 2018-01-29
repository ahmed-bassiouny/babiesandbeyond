package tech.ntam.babiesandbeyond.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tech.ntam.babiesandbeyond.utils.UserSharedPref;

/**
 * Created by bassiouny on 21/01/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        UserSharedPref.setNotificationToken(this,refreshedToken);
        Log.e("onTokenRefresh: ",refreshedToken );
    }
}
