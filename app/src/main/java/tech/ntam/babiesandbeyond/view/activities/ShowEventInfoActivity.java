package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.dialog.EventDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ShowEventInfoActivity extends MyToolbar {

    private TextView tvEventName;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvSpeakerBio;
    private TextView tvSpeakerName;
    private Button btnComing;
    private Event event;
    private boolean isComing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_info);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.event_information);
        findViewById();
        setData();
    }

    private void setData() {
        event = getIntent().getParcelableExtra(IntentDataKey.SHOW_EVENT_DATA_KEY);
        if (event == null)
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
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(ShowEventInfoActivity.this);
                RequestAndResponse.sendStatusEvent(ShowEventInfoActivity.this, event.getId(), !isComing, new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        event.setComing(!event.isComing());
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.CHANGE_EVENT_DATA_KEY, event);
                        setResult(Activity.RESULT_OK, resultIntent);
                        myDialog.dismissMyDialog();
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(ShowEventInfoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                    }
                });
            }
        });
    }
}
