package tech.ntam.babiesandbeyond.controller.activities;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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

    public void createTextMessage(String txtMessage) {
        String key = getGroupReference().push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(Message.USER_ID,UserSharedPref.getId(context));
        hashMap.put(Message.TIME_STAMP,MyDateTimeFactor.getTimeStamp());
        hashMap.put(Message.TEXT_MESSAGE,txtMessage);
        getGroupReference().child(key).updateChildren(hashMap);
        setUser();
    }
    public void createImageMessage(String imageUrl,int width,int height) {
        String key = getGroupReference().push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(Message.USER_ID,UserSharedPref.getId(context));
        hashMap.put(Message.TIME_STAMP,MyDateTimeFactor.getTimeStamp());
        hashMap.put(Message.IMAGE_URL,imageUrl);
        hashMap.put(Message.IMAGE_WIDTH,width);
        hashMap.put(Message.IMAGE_HEIGHT,height);
        getGroupReference().child(key).updateChildren(hashMap);
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
