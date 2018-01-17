package tech.ntam.babiesandbeyond.controller.activities;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tech.ntam.babiesandbeyond.model.FirebaseRoot;
import tech.ntam.babiesandbeyond.model.FirebaseUser;
import tech.ntam.babiesandbeyond.model.Message;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.ChatActivity;
import tech.ntam.mylibrary.MyDateTimeFactor;

/**
 * Created by bassiouny on 16/01/18.
 */

public class ChatController {

    private Context context;
    private int groupId;
    private FirebaseUser firebaseUser;

    public ChatController(Context context, int groupId) {
        this.context = context;
        this.groupId = groupId;
    }

    public void createMessage(String txtMessage) {
        Message message = new Message(txtMessage, UserSharedPref.getId(context), MyDateTimeFactor.getTimeStamp());
        String key = getGroupReference().push().getKey();
        getGroupReference().child(key).setValue(message);
        setUser();
    }

    private void setUser() {
        if (firebaseUser == null) {
            firebaseUser = new FirebaseUser();
            firebaseUser.setName(UserSharedPref.getName(context));
            firebaseUser.setPhoto(UserSharedPref.getPhoto(context));
            getUserReference().setValue(firebaseUser);
        }
    }

    private DatabaseReference getGroupReference() {
        return FirebaseDatabase.getInstance().getReference().child(FirebaseRoot.GROUP).child(String.valueOf(groupId));
    }

    private DatabaseReference getUserReference() {
        return FirebaseDatabase.getInstance().getReference().child(FirebaseRoot.USERS).child(String.valueOf(UserSharedPref.getId(context)));
    }
}
