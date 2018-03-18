package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Notification;
import tech.ntam.babiesandbeyond.view.adapter.HistoryItemAdapter;
import tech.ntam.babiesandbeyond.view.adapter.NotificationItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

public class UserHistoryNotificationActivity extends MyToolbar implements ParseObject<UserHistory> {

    private RecyclerView recycleView;
    private HistoryItemAdapter historyItemAdapter;

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
        ServiceSharedPref.clearUserHistory(this);
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getUserHistory(this, new BaseResponseInterface<List<UserHistory>>() {
            @Override
            public void onSuccess(List<UserHistory> userHistories) {
                historyItemAdapter = new HistoryItemAdapter(UserHistoryNotificationActivity.this,userHistories);
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
    @Override
    protected void onResume() {
        super.onResume();
        UserHistory userHistory= ServiceSharedPref.getUserHistory(this);
        if(userHistory != null) {
            historyItemAdapter.updateFeed(userHistory);
            ServiceSharedPref.clearUserHistory(this);
        }
    }

    @Override
    public void getMyObject(UserHistory userHistory) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentDataKey.FEEDBACK,userHistory);
        Intent i = new Intent(UserHistoryNotificationActivity.this, HistoryDetailsActivity.class);
        i.putExtra(IntentDataKey.BUNDLE,bundle);
        startActivity(i);
    }
}
