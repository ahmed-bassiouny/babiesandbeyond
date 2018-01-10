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
import tech.ntam.babiesandbeyond.model.Notification;
import tech.ntam.babiesandbeyond.view.adapter.HistoryItemAdapter;
import tech.ntam.babiesandbeyond.view.adapter.NotificationItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyNotificationItem;
import tech.ntam.mylibrary.IntentDataKey;

public class UserHistoryNotificationActivity extends MyToolbar {

    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().getBooleanExtra(IntentDataKey.SHOW_HISTORY_DATA_KEY, false)) {
            setupToolbar(this, false, true, true);
            tvTitle.setText(R.string.history);
            loadHistory();
        } else {
            setupToolbar(this, false, true, false);
            tvTitle.setText(R.string.notification);
            loadNotification();
        }
    }

    private void loadHistory() {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getHistory(this, new BaseResponseInterface<List<History>>() {
            @Override
            public void onSuccess(List<History> historyList) {
                HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(historyList);
                recycleView.setAdapter(historyItemAdapter);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(UserHistoryNotificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadNotification() {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getNotification(this, new BaseResponseInterface<List<Notification>>() {
            @Override
            public void onSuccess(List<Notification> historyList) {
                NotificationItemAdapter notificationItemAdapter = new NotificationItemAdapter(historyList);
                recycleView.setAdapter(notificationItemAdapter);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(UserHistoryNotificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
