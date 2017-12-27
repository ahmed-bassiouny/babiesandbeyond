package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.adapter.HistoryItemAdapter;
import tech.ntam.babiesandbeyond.view.adapter.NotificationItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyHistoryModel;
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
            tvTitle.setText("History");
            List<DummyHistoryModel> dummyHistoryModels = new ArrayList<>();
            DummyHistoryModel dummyHistoryModel1 = new DummyHistoryModel("Nurse", "location about nurse", "2 Oct - 5 Oct", "60$");
            dummyHistoryModels.add(dummyHistoryModel1);
            DummyHistoryModel dummyHistoryModel2 = new DummyHistoryModel("Midwife", "location about midwife", "9 Oct - 10 Oct", "120$");
            dummyHistoryModels.add(dummyHistoryModel2);
            DummyHistoryModel dummyHistoryModel3 = new DummyHistoryModel("Workshop", "location about workshop", "2 Nov - 5 Nov", "300$");
            dummyHistoryModels.add(dummyHistoryModel3);
            DummyHistoryModel dummyHistoryModel4 = new DummyHistoryModel("Nurse", "location about nurse", "2 Dec - 5 Dec", "90$");
            dummyHistoryModels.add(dummyHistoryModel4);
            HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(dummyHistoryModels);
            recycleView.setAdapter(historyItemAdapter);
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
}
