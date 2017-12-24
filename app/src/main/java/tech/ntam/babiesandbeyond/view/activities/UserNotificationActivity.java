package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import tech.ntam.babiesandbeyond.R;

public class UserNotificationActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recycleView = findViewById(R.id.recycle_view);
    }
}
