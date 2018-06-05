package tech.ntam.babiesandbeyond.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.ChatController;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.FirebaseRoot;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.Message;
import tech.ntam.babiesandbeyond.model.UserMessage;
import tech.ntam.babiesandbeyond.view.adapter.ItemChatAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.interfaces.Constant;

public class ChatActivity extends MyToolbar implements ParseObject<String> {

    private Group group;
    private RecyclerView recycleView;
    private ImageView ivChooseAttachment;
    private TextView tvSend;
    private EditText etMessage;
    private ChatController chatController;
    private ItemChatAdapter itemChatAdapter;
    private StorageReference mStorageRef;
    private ChildEventListener childEventListener;
    private ProgressBar progress;
    private boolean imageDownloaded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupToolbar(this, false, true, false);
        findViewById();
        onClick();
        imageDownloaded = true;
        setDataGroup();

    }

    private void setDataGroup() {
        group = getIntent().getParcelableExtra(IntentDataKey.SHOW_GROUP_DATA_KEY);
        if (group == null)
            finish();
        else {
            tvTitle.setText(group.getName());
        }
        loadChat();
    }

    private void loadChat() {
        int userId = UserSharedPref.getId(this);
        itemChatAdapter = new ItemChatAdapter(ChatActivity.this, userId);
        recycleView.setAdapter(itemChatAdapter);
        progress.setVisibility(View.VISIBLE);
        getController().getGroupReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getController().getGroupReference().addChildEventListener(getChildEventListener());

    }

    @Override
    protected void onStop() {
        super.onStop();
        getController().getGroupReference().removeEventListener(childEventListener);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private ChildEventListener getChildEventListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                if (!message.getMessage().isEmpty()) {
                    convertMessageToUserMessage(message);
                } else if (!message.getImageURL().isEmpty() && imageDownloaded) {
                    convertMessageToUserMessage(message);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return childEventListener;
    }

    private void onClick() {
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText().toString().trim().isEmpty())
                    return;
                getController().createTextMessage(etMessage.getText().toString());
                etMessage.setText("");
            }
        });
        ivChooseAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(ChatActivity.this, "Select your image:");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] myData = baos.toByteArray();
            // create local message
            Message message = new Message(UserSharedPref.getId(this), MyDateTimeFactor.getTimeStamp(), bitmap.getHeight(), bitmap.getWidth(), myData);
            final int position = itemChatAdapter.addMessage(new UserMessage("", "", message));
            mStorageRef = FirebaseStorage.getInstance().getReference();

            UploadTask uploadTask = mStorageRef.child(String.valueOf(MyDateTimeFactor.getTimeStamp())).putBytes(myData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ChatActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    itemChatAdapter.deleteMessage(position);

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    getController().createImageMessage(downloadUrl.toString(), bitmap.getWidth(), bitmap.getHeight());
                    imageDownloaded = false;
                    itemChatAdapter.updateMessageUrl(downloadUrl.toString(), position);
                }
            });
        }
    }

    private void findViewById() {
        recycleView = findViewById(R.id.recycle_view);
        ivChooseAttachment = findViewById(R.id.iv_choose_attachment);
        tvSend = findViewById(R.id.tv_send);
        etMessage = findViewById(R.id.et_message);
        progress = findViewById(R.id.progress);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ChatController getController() {
        if (chatController == null)
            chatController = new ChatController(this, group.getId());
        return chatController;
    }

    @Override
    public void getMyObject(String s) {
        Intent i = new Intent(this, ViewImageActivity.class);
        i.putExtra(IntentDataKey.SHOW_IMAGE, s);
        startActivity(i);
    }

    private void convertMessageToUserMessage(final Message message) {
        // get user name and photo from firebase
        FirebaseDatabase.getInstance().getReference()
                .child(FirebaseRoot.USERS)
                .child(String.valueOf(message.getUserId()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> map = (Map) dataSnapshot.getValue();
                        itemChatAdapter.addMessage(new UserMessage(map.get("name"), map.get("photo"), message));
                        recycleView.scrollToPosition(itemChatAdapter.getItemCount() - 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_GROUP));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String groupId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
            if (groupId == null || groupId.isEmpty())
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            //       group from notification equal this group   delete group
            if (groupId.equals(String.valueOf(group.getId())) && action.equals("5")) {
                Toast.makeText(context, "Group Deleted", Toast.LENGTH_SHORT).show();
                finish();
                //          group from notification equal this group   delete user
            }else if (groupId.equals(String.valueOf(group.getId())) && action.equals("")) {
                Toast.makeText(context, "you are out of the group", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };

}
