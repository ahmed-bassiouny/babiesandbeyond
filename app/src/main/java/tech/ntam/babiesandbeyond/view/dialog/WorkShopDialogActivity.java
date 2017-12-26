package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.DummyClass;

public class WorkShopDialogActivity extends AppCompatActivity {

    private TextView tvWorkshopName;
    private TextView tvWorkshopDateTimeFrom;
    private TextView tvWorkshopDateTimeTo;
    private TextView tvStatus;
    private TextView tvSpeakerName;
    private TextView tvSpeakerBio;
    private TextView tvEventDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_shop_dialog);
        findViewById();
        setData();
    }

    private void setData() {
        DummyClass.setTitleText(tvWorkshopName);
        DummyClass.setDateTimeText(tvWorkshopDateTimeFrom);
        DummyClass.setDateTimeText(tvWorkshopDateTimeTo);
        DummyClass.setTitleText(tvStatus);
        DummyClass.setTitleText(tvSpeakerName);
        DummyClass.setShortText(tvSpeakerBio);
        DummyClass.setShortText(tvEventDescription);

    }
    private void findViewById() {
        tvWorkshopName = findViewById(R.id.tv_workshop_name);
        tvWorkshopDateTimeFrom = findViewById(R.id.tv_workshop_date_time_from);
        tvWorkshopDateTimeTo = findViewById(R.id.tv_workshop_date_time_to);
        tvStatus = findViewById(R.id.tv_status);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        tvEventDescription = findViewById(R.id.tv_event_description);
        findViewById(R.id.btn_coming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
