package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.babiesandbeyond.view.adapter.TaskTimeSlotsAdapter;
import tech.ntam.babiesandbeyond.view.dialog.RateUserDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;

public class HistoryDetailsActivity extends MyToolbar {
    private UserHistory history;
    private TextView tvServiceRating,tvServiceRatingText, tvServiceType;
    private TextView tvDateFrom, tvDateTo, tvLocation;
    private TextView tvDateFromText, tvDateToText;
    private Button btnSendRate;
    private RecyclerView recycleView;
    private TextView tvComment;
    private RelativeLayout dateContain;
    private TextView date,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        findView();
        // get object from intent
        Bundle bundle = getIntent().getBundleExtra(IntentDataKey.BUNDLE);
        history = bundle.getParcelable(IntentDataKey.FEEDBACK);
        if (history == null)
            finish();
        setData();
        setRating(history);
    }

    private void setData() {
        tvTitle.setText(history.getName());
        tvServiceType.setText(history.getType());
        tvLocation.setText(history.getLocation());
        if (history.getComment().isEmpty()) {
            tvComment.setText("None");
        } else {
            tvComment.setText(history.getComment());
        }
        if (history.isMidwife()) {
            tvDateFromText.setVisibility(View.GONE);
            tvDateFrom.setVisibility(View.GONE);
            tvDateToText.setVisibility(View.GONE);
            tvDateTo.setVisibility(View.GONE);
            dateContain.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.VISIBLE);
            date.setText(history.getHistoryDates().get(0).getDate());
            day.setText(MyDateTimeFactor.convertDateStringToDayOfWeek(history.getHistoryDates().get(0).getDate()));
            TaskTimeSlotsAdapter adapter = new TaskTimeSlotsAdapter(history.getHistoryDates());
            recycleView.setAdapter(adapter);

        } else {
            tvDateFrom.setText(history.getHistoryDates().get(0).getFullStartDate());
            tvDateTo.setText(history.getHistoryDates().get(0).getFullEndDate());
            recycleView.setVisibility(View.GONE);
        }
    }

    private void findView() {
        setupToolbar(this, false, true, false);
        tvServiceRating = findViewById(R.id.tv_service_rating);
        tvServiceRatingText = findViewById(R.id.tv_service_rating_text);
        btnSendRate = findViewById(R.id.btn_send_rate);
        tvServiceType = findViewById(R.id.tv_service_type);
        tvDateFrom = findViewById(R.id.tv_date_from);
        tvDateTo = findViewById(R.id.tv_date_to);
        tvDateFromText = findViewById(R.id.tv_date_from_text);
        tvDateToText = findViewById(R.id.tv_date_to_text);
        tvLocation = findViewById(R.id.tv_location);
        recycleView = findViewById(R.id.recycle_view);
        tvComment = findViewById(R.id.tv_comment);
        dateContain = findViewById(R.id.date_contain);
        date = findViewById(R.id.tv_day_date);
        day = findViewById(R.id.tv_day_name);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        btnSendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSharedPref.setUserHistory(HistoryDetailsActivity.this, history);
                Intent i = new Intent(HistoryDetailsActivity.this, RateUserDialogActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserHistory history = ServiceSharedPref.getUserHistory(this);
        if (history != null) {
            setRating(history);
        }
    }

    private void setRating(UserHistory history) {
        if (history.getRate() > 0) {
            tvServiceRating.setText(history.getRateString());
            tvServiceRating.setVisibility(View.VISIBLE);
            tvServiceRatingText.setVisibility(View.VISIBLE);
            btnSendRate.setVisibility(View.GONE);
        } else {
            tvServiceRating.setVisibility(View.GONE);
            tvServiceRatingText.setVisibility(View.GONE);
            btnSendRate.setVisibility(View.VISIBLE);
        }
    }
}
