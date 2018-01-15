package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.dialog.WorkShopDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ShowWorkshopInfoActivity extends MyToolbar {

    private TextView tvWorkshopName;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvStatus;
    private TextView tvFee;
    private TextView tvSpeakerBio;
    private TextView tvSpeakerName;
    private Button btnPay;
    private Workshop workshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_workshop_info);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.workshop_information);
        findViewById();
        setData();
    }

    private void setData() {
        workshop = getIntent().getParcelableExtra(IntentDataKey.SHOW_WORKSHOP_DATA_KEY);
        if (workshop == null)
            finish();
        tvDateFrom.setText(workshop.getStartDate());
        tvTimeFrom.setText(workshop.getStartTime());
        tvDateTo.setText(workshop.getEndDate());
        tvTimeTo.setText(workshop.getEndTime());
        tvLocation.setText(workshop.getLocation());
        tvFee.setText(workshop.getPrice());
        tvWorkshopName.setText(workshop.getName());
        tvStatus.setText(workshop.getWorkshopStatusName());
        tvSpeakerName.setText(workshop.getSpeakerName());
        tvSpeakerBio.setText(workshop.getSpeakerBio());
        if(workshop.isComing()){
            btnPay.setEnabled(false);
            btnPay.setText(R.string.pay);
        }else {
            btnPay.setEnabled(true);
            btnPay.setText(R.string.send_request);
        }
    }

    private void findViewById() {
        tvWorkshopName = findViewById(R.id.tv_workshop_name);
        tvDateFrom = findViewById(R.id.tv_date_from);
        tvTimeFrom = findViewById(R.id.tv_time_from);
        tvDateTo = findViewById(R.id.tv_date_to);
        tvTimeTo = findViewById(R.id.tv_time_to);
        tvLocation = findViewById(R.id.tv_location);
        tvStatus = findViewById(R.id.tv_status);
        tvFee = findViewById(R.id.tv_fee);
        btnPay = findViewById(R.id.tv_pay);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workshop.isComing())
                    return;
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(ShowWorkshopInfoActivity.this);
                RequestAndResponse.sendWorkshopRequest(ShowWorkshopInfoActivity.this, workshop.getId(), new BaseResponseInterface<Workshop>() {
                    @Override
                    public void onSuccess(Workshop workshop) {
                        Toast.makeText(ShowWorkshopInfoActivity.this, R.string.waiting_confirmation, Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY, workshop);
                        setResult(Activity.RESULT_OK, resultIntent);
                        myDialog.dismissMyDialog();
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(ShowWorkshopInfoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                    }
                });
            }
        });
    }
}
