package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.ChatController;
import tech.ntam.babiesandbeyond.model.FirebaseRoot;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.Message;
import tech.ntam.babiesandbeyond.view.adapter.GroupItemAdapter;
import tech.ntam.babiesandbeyond.view.adapter.ItemChatAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ChatActivity extends MyToolbar {

    private Group group;
    private RecyclerView recycleView;
    private ImageView ivChooseAttachment;
    private TextView tvSend;
    private EditText etMessage;
    private ItemChatAdapter groupItemAdapter;
    private ChatController chatController;
    private ItemChatAdapter itemChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupToolbar(this, false, true, false);
        findViewById();
        onClick();
        loadChatGroup();

    }

    private void loadChatGroup() {
        group = getIntent().getParcelableExtra(IntentDataKey.SHOW_GROUP_DATA_KEY);
        if (group == null)
            finish();
        else {
            tvTitle.setText(group.getName());
        }
        loadChat();
    }

    private void loadChat() {
        itemChatAdapter = new ItemChatAdapter(ChatActivity.this);
        recycleView.setAdapter(itemChatAdapter);
        FirebaseDatabase.getInstance().getReference().child(FirebaseRoot.GROUP)
                .child(String.valueOf(group.getId()))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message message = dataSnapshot.getValue(Message.class);
                        itemChatAdapter.addMessage(message);
                        recycleView.scrollToPosition(itemChatAdapter.getItemCount()- 1);
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
                });

    }

    private void onClick() {
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText().toString().trim().isEmpty())
                    return;
                getController().createMessage(etMessage.getText().toString());
                etMessage.setText("");
            }
        });
    }

    private void findViewById() {
        recycleView = findViewById(R.id.recycle_view);
        ivChooseAttachment = findViewById(R.id.iv_choose_attachment);
        tvSend = findViewById(R.id.tv_send);
        etMessage = findViewById(R.id.et_message);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ChatController getController() {
        if (chatController == null)
            chatController = new ChatController(this, group.getId());
        return chatController;
    }
}
