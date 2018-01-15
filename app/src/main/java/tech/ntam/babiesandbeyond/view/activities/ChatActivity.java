package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ChatActivity extends MyToolbar {

    private Group group;
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
    }

    private void onClick() {
    }

    private void findViewById() {
    }
}
