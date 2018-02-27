package tech.ntam.adminapp.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class WorkshopDetailsActivity extends AppCompatActivity {

    private TextView tvWorkshopName;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvStatusName;
    private TextView tvStatus;
    private TextView tvFee;
    private TextView tvSpeakerBio;
    private TextView tvSpeakerName;
    private Workshop workshop;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_details);
        findViewById();
        setData();
    }

    private void setData() {
        workshop = getIntent().getParcelableExtra(IntentDataKey.MY_WORKSHOP);
        if (workshop == null)
            finish();
        tvDateFrom.setText(workshop.getStartDate());
        tvTimeFrom.setText(workshop.getStartTime());
        tvDateTo.setText(workshop.getEndDate());
        tvTimeTo.setText(workshop.getEndTime());
        tvLocation.setText(workshop.getLocation());
        tvFee.setText(workshop.getPrice());
        tvWorkshopName.setText(workshop.getName());
        /*if (!workshop.getWorkshopStatusName().isEmpty()) {
            tvStatus.setText(workshop.getWorkshopStatusName());
            tvStatusName.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
        }*/
        tvSpeakerName.setText(workshop.getSpeakerName());
        tvSpeakerBio.setText(workshop.getSpeakerBio());
        if (workshop.getUserId().isEmpty()) {
            btnPay.setVisibility(View.GONE);
        } else {
            btnPay.setVisibility(View.VISIBLE);
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
        tvStatusName = findViewById(R.id.textView17);
        tvFee = findViewById(R.id.tv_fee);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        btnPay = findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWorkshopInvoice();
            }
        });
    }

    private void createWorkshopInvoice() {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.createWorkshopInvoice(this, workshop.getUserWorkshopId(), workshop.getUserId(), new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY, workshop.getId());
                setResult(Activity.RESULT_OK, resultIntent);
                dialog.dismissMyDialog();
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(WorkshopDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });
    }
}
