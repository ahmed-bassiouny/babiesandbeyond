package tech.ntam.babiesandbeyond.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;

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
        tvStatus.setText(workshop.getWorkshopStatusName());
        tvEventDescription.setText(workshop.getDescription());
        if(workshop.isComing()){
            btnComing.setEnabled(false);
        }else {
            btnComing.setEnabled(true);
        }
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
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(WorkShopDialogActivity.this);
                RequestAndResponse.sendWorkshopRequest(WorkShopDialogActivity.this, workshop.getId(), new BaseResponseInterface<Workshop>() {
                    @Override
                    public void onSuccess(Workshop workshop) {
                        Toast.makeText(WorkShopDialogActivity.this, R.string.waiting_confirmation, Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY, workshop);
                        setResult(Activity.RESULT_OK, resultIntent);
                        myDialog.dismissMyDialog();
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(WorkShopDialogActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                    }
                });
            }
        });
    }
}
