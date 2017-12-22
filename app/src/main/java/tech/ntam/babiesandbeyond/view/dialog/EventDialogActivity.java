package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;

public class EventDialogActivity extends AppCompatActivity {

    private TextView tvEventName;
    private TextView tvEventDateTimeFrom;
    private TextView tvEventDateTimeTo;
    private TextView tvStatus;
    private TextView tvSpeakerName;
    private TextView tvSpeakerBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dialog);
        findViewById();
    }

    private void findViewById() {
        tvEventName = findViewById(R.id.tv_event_name);
        tvEventDateTimeFrom = findViewById(R.id.tv_event_date_time_from);
        tvEventDateTimeTo = findViewById(R.id.tv_event_date_time_to);
        tvStatus = findViewById(R.id.tv_status);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        findViewById(R.id.btn_coming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
