package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.ChatController;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.Message;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.adapter.ItemChatAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupToolbar(this, false, true, false);
        findViewById();
        onClick();

    }

    @Override
    protected void onStart() {
        super.onStart();
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
    }

    private ChildEventListener getChildEventListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                itemChatAdapter.addMessage(message);
                recycleView.scrollToPosition(itemChatAdapter.getItemCount() - 1);
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
                //EasyImage.openChooserWithGallery(ChatActivity.this, "Select Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                ImagePicker.pickImage(ChatActivity.this, "Select your image:");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        // TODO do something with the bitmap
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(ChatActivity.this);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] myData = baos.toByteArray();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        UploadTask uploadTask = mStorageRef.child(String.valueOf(MyDateTimeFactor.getTimeStamp())).putBytes(myData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                dialog.dismissMyDialog();
                Toast.makeText(ChatActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                getController().createImageMessage(downloadUrl.toString(), bitmap.getWidth(), bitmap.getHeight());
                dialog.dismissMyDialog();
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                final MyDialog dialog = new MyDialog();
                dialog.showMyDialog(ChatActivity.this);
                try {
                    final Bitmap myBitmap = ImageFactor.getBitmapImageFromFilePathAfterResize(imageFile);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    UploadTask uploadTask = mStorageRef.child(String.valueOf(MyDateTimeFactor.getTimeStamp())).putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            dialog.dismissMyDialog();
                            Toast.makeText(ChatActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            getController().createImageMessage(downloadUrl.toString(),myBitmap.getWidth(),myBitmap.getHeight());
                            dialog.dismissMyDialog();
                        }
                    });
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                final MyDialog dialog = new MyDialog();
                dialog.showMyDialog(ChatActivity.this);
                try {
                    final Bitmap myBitmap = ImageFactor.getBitmapImageFromFilePathAfterResize(imageFile);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    UploadTask uploadTask = mStorageRef.child(String.valueOf(MyDateTimeFactor.getTimeStamp())).putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            dialog.dismissMyDialog();
                            Toast.makeText(ChatActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            getController().createImageMessage(downloadUrl.toString(),myBitmap.getWidth(),myBitmap.getHeight());
                            dialog.dismissMyDialog();
                        }
                    });
                } catch (FileNotFoundException e) {
                    Toast.makeText(ChatActivity.this, R.string.photo_large, Toast.LENGTH_SHORT).show();
                    dialog.dismissMyDialog();
                }
            }
        });
    }*/

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

}
