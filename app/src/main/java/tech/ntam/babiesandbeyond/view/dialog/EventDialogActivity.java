package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.mylibrary.DummyClass;
import tech.ntam.mylibrary.IntentDataKey;

public class EventDialogActivity extends AppCompatActivity {

    private TextView tvEventName;
    private TextView tvEventDateTimeFrom;
    private TextView tvEventDateTimeTo;
    private TextView tvStatus;
    private TextView tvSpeakerName;
    private TextView tvSpeakerBio;
    private TextView tvEventDescription;
    private Button btnComing;
    private boolean isComing = false;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dialog);
        findViewById();
        setData();
    }

    private void setData() {
        event = getIntent().getParcelableExtra(IntentDataKey.SHOW_EVENT_DATA_KEY);
        tvEventName.setText(event.getName());
        tvEventDateTimeFrom.setText(event.getStartDate());
        tvEventDateTimeTo.setText(event.getEndDate());
        tvSpeakerName.setText(event.getSpeakerName());
        tvSpeakerBio.setText(event.getSpeakerBio());
        tvEventDescription.setText(event.getDescription());
        checkEventStatus();
    }

    private void findViewById() {
        tvEventName = findViewById(R.id.tv_event_name);
        tvEventDateTimeFrom = findViewById(R.id.tv_event_date_time_from);
        tvEventDateTimeTo = findViewById(R.id.tv_event_date_time_to);
        tvStatus = findViewById(R.id.tv_status);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        tvEventDescription = findViewById(R.id.tv_event_description);
        btnComing = findViewById(R.id.btn_coming);
        btnComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event == null)
                    return;
                MyDialog.showMyDialog(EventDialogActivity.this);
                RequestAndResponse.sendStatusEvent(EventDialogActivity.this, event.getId(), !isComing, new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        event.setComing(!isComing);
                        MyDialog.dismissMyDialog();
                        checkEventStatus();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(EventDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        MyDialog.dismissMyDialog();
                    }
                });
            }
        });
    }

    private void checkEventStatus() {
        if (event.isComing()) {
            tvStatus.setText(R.string.coming);
            btnComing.setText(R.string.not_coming);
            isComing = true;
        } else {
            tvStatus.setText(R.string.not_coming);
            btnComing.setText(R.string.coming);
            isComing = false;
        }
    }
}
