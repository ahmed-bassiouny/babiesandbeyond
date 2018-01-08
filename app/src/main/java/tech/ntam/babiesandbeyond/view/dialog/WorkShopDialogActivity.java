package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.mylibrary.DummyClass;
import tech.ntam.mylibrary.IntentDataKey;

public class WorkShopDialogActivity extends AppCompatActivity {

    private TextView tvWorkshopName;
    private TextView tvWorkshopDateTimeFrom;
    private TextView tvWorkshopDateTimeTo;
    private TextView tvStatus;
    private TextView tvSpeakerName;
    private TextView tvSpeakerBio;
    private TextView tvEventDescription;
    private Workshop workshop;
    private Button btnComing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_shop_dialog);
        findViewById();
        setData();
    }

    private void setData() {
        workshop = getIntent().getParcelableExtra(IntentDataKey.SHOW_WORKSHOP_DATA_KEY);
        if (workshop == null)
            finish();
        tvWorkshopName.setText(workshop.getName());
        tvWorkshopDateTimeFrom.setText(workshop.getStartDate());
        tvWorkshopDateTimeTo.setText(workshop.getEndDate());
        tvSpeakerName.setText(workshop.getSpeakerName());
        tvSpeakerBio.setText(workshop.getSpeakerBio());
        tvStatus.setText(workshop.getPaymentStatus());
        tvEventDescription.setText(workshop.getDescription());
    }

    private void findViewById() {
        tvWorkshopName = findViewById(R.id.tv_workshop_name);
        tvWorkshopDateTimeFrom = findViewById(R.id.tv_workshop_date_time_from);
        tvWorkshopDateTimeTo = findViewById(R.id.tv_workshop_date_time_to);
        tvStatus = findViewById(R.id.tv_status);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        tvEventDescription = findViewById(R.id.tv_event_description);
        btnComing = findViewById(R.id.btn_coming);
        btnComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
