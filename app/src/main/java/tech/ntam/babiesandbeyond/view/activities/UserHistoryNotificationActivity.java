package tech.ntam.babiesandbeyond.view.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.History;
import tech.ntam.babiesandbeyond.view.adapter.HistoryItemAdapter;
import tech.ntam.babiesandbeyond.view.adapter.NotificationItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyNotificationItem;

public class UserHistoryNotificationActivity extends MyToolbar {

    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().getBooleanExtra("history", false)) {
            setupToolbar(this, false, true,true);
            tvTitle.setText(R.string.history);
            loadHistory();
        } else {
            setupToolbar(this, false, true,false);
            tvTitle.setText("Notification");
            List<DummyNotificationItem> dummyNotificationItems = new ArrayList<>();
            DummyNotificationItem dummyNotificationItem1 = new DummyNotificationItem("Your Payment Request is Done","Just now","13$");
            dummyNotificationItems.add(dummyNotificationItem1);
            DummyNotificationItem dummyNotificationItem2 = new DummyNotificationItem("You have a Confirm For Nurse Request","Just now","");
            dummyNotificationItems.add(dummyNotificationItem2);
            DummyNotificationItem dummyNotificationItem3 = new DummyNotificationItem("Your Payment Request is Done","Just now","10$");
            dummyNotificationItems.add(dummyNotificationItem3);
            NotificationItemAdapter notificationItemAdapter = new NotificationItemAdapter(dummyNotificationItems);
            recycleView.setAdapter(notificationItemAdapter);
        }
    }
    private void loadHistory(){
        MyDialog.showMyDialog(this);
        RequestAndResponse.getHistory(this, new BaseResponseInterface<List<History>>() {
            @Override
            public void onSuccess(List<History> historyList) {
                HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(historyList);
                recycleView.setAdapter(historyItemAdapter);
                MyDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                MyDialog.dismissMyDialog();
                Toast.makeText(UserHistoryNotificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
