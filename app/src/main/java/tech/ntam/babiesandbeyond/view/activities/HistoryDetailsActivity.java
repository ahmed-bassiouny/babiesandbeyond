package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.ServiceFeedback;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class HistoryDetailsActivity extends MyToolbar {
    private ServiceFeedback feedback;
    private RatingBar ratingBar;
    private TextView tvServiceRating;
    private Button btnSendRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        findView();
        // get object from intent
        feedback = getIntent().getParcelableExtra(IntentDataKey.FEEDBACK);
        if(feedback == null)
            finish();
        setRating(feedback);
    }

    private void findView() {
        setupToolbar(this,false,true,false);
        tvTitle.setText("Jodi Foster");
        ratingBar = findViewById(R.id.rating_bar);
        tvServiceRating = findViewById(R.id.tv_service_rating);
        btnSendRate = findViewById(R.id.btn_send_rate);
        btnSendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSharedPref.setServiceFeedback(HistoryDetailsActivity.this,feedback);
                Intent i = new Intent(HistoryDetailsActivity.this, RateUserDialogActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceFeedback feedback = ServiceSharedPref.getServiceFeedback(this);
        if(feedback != null){
            setRating(feedback);
        }
    }

    private void setRating(ServiceFeedback feedback) {
        if(feedback.getRate()>0){
            ratingBar.setRating(feedback.getRate());
            ratingBar.setVisibility(View.VISIBLE);
            tvServiceRating.setVisibility(View.VISIBLE);
            btnSendRate.setVisibility(View.GONE);
        }else {
            ratingBar.setVisibility(View.GONE);
            tvServiceRating.setVisibility(View.GONE);
            btnSendRate.setVisibility(View.VISIBLE);
        }
    }
}
