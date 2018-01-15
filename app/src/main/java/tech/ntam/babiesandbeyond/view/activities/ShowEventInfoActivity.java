package tech.ntam.babiesandbeyond.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ShowEventInfoActivity extends MyToolbar{

    private TextView tvEventName;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvSpeakerBio;
    private TextView tvSpeakerName;
    private Button btnComing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_info);
        setupToolbar(this,false,true,false);
        tvTitle.setText(R.string.event_information);
        findViewById();
        setData();
    }

    private void setData() {
        Event event = getIntent().getParcelableExtra(IntentDataKey.SHOW_EVENT_DATA_KEY);
        if(event == null)
            finish();
        tvDateFrom.setText(event.getStartDate());
        tvTimeFrom.setText(event.getStartTime());
        tvDateTo.setText(event.getEndDate());
        tvTimeTo.setText(event.getEndTime());
        tvLocation.setText(event.getLocation());
        tvEventName.setText(event.getName());
        tvSpeakerName.setText(event.getSpeakerName());
        tvSpeakerBio.setText(event.getSpeakerBio());
    }
    private void findViewById() {
        tvEventName = findViewById(R.id.tv_workshop_name);
        tvDateFrom = findViewById(R.id.tv_date_from);
        tvTimeFrom = findViewById(R.id.tv_time_from);
        tvDateTo = findViewById(R.id.tv_date_to);
        tvTimeTo = findViewById(R.id.tv_time_to);
        tvLocation = findViewById(R.id.tv_location);
        btnComing = findViewById(R.id.tv_coming);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        btnComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
