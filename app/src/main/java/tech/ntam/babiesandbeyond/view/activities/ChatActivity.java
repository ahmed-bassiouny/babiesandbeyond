package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupToolbar(this,false,true,false);
        findViewById();
        onClick();
        loadChatGroup();

    }

    private void loadChatGroup() {
        group = getIntent().getParcelableExtra(IntentDataKey.SHOW_GROUP_DATA_KEY);
        if(group == null)
            finish();
        else {
            tvTitle.setText(group.getName());
        }
        setFakeData();
    }

    private void setFakeData() {
        List<Message> messages = new ArrayList<>();
        Message message1 = new Message("medicina domesticus tumultumque est. ",true,"text");
        messages.add(message1);
        Message message2 = new Message("ratione resisteres, tanquam brevis nuclear vexatum iacere. ",false,"text");
        messages.add(message2);
        Message message3 = new Message("pes raptus boreas est. ",true,"text");
        messages.add(message3);
        Message message11 = new Message("amicitia salvus diatria est. ",true,"text");
        messages.add(message11);
        Message message22 = new Message("cottas nocere, tanquam barbatus. ",false,"text");
        messages.add(message22);
        Message message33 = new Message("est camerarius rumor, cesaris. ",true,"text");
        messages.add(message33);
        Message message44 = new Message("",true,"photo");
        messages.add(message44);
        Message message55 = new Message("",false,"photo");
        messages.add(message55);
        groupItemAdapter = new ItemChatAdapter(messages,this);
        recycleView.setAdapter(groupItemAdapter);
    }

    private void onClick() {
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMessage.getText().toString().trim().isEmpty())
                    return;
                Message message = new Message(etMessage.getText().toString(),true,"text");
                groupItemAdapter.addMessage(message);
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
}
