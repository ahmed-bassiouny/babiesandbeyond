package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.ServiceFeedback;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class HistoryDetailsActivity extends MyToolbar {

    private ServiceFeedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        setupToolbar(this,false,true,false);
        tvTitle.setText("Jodi Foster");
        feedback = getIntent().getParcelableExtra(IntentDataKey.FEEDBACK);
        if (feedback  == null)
            finish();
        findViewById(R.id.btn_send_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryDetailsActivity.this, RateUserDialogActivity.class);
                i.putExtra(IntentDataKey.FEEDBACK,feedback);
                startActivity(i);
            }
        });
    }
}
