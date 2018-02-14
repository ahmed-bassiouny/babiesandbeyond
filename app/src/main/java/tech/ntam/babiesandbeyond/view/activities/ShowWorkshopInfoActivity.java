package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.interfaces.Constant;

public class ShowWorkshopInfoActivity extends MyToolbar {

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
    private Button btnPay, btnCancel;
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
        tvFee.setText(workshop.getPrice()+"$");
        tvWorkshopName.setText(workshop.getName());
        if (!workshop.getWorkshopStatusName().isEmpty()) {
            tvStatus.setText(workshop.getWorkshopStatusName());
            tvStatusName.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
        }
        tvSpeakerName.setText(workshop.getSpeakerName());
        tvSpeakerBio.setText(workshop.getSpeakerBio());
        if (workshop.getWorkshopStatusName().equals(Constant.PENDING)) {
            btnPay.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (workshop.getWorkshopStatusName().equals(Constant.ASK_FOR_PAY)) {
            btnPay.setText(R.string.pay);
            btnPay.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (workshop.getWorkshopStatusName().equals(Constant.DONE)) {
            btnPay.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
        } else {
            btnPay.setText(R.string.send_request);
            btnCancel.setVisibility(View.GONE);
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
        btnPay = findViewById(R.id.tv_pay);
        btnCancel = findViewById(R.id.tv_cancel);
        tvSpeakerName = findViewById(R.id.tv_speaker_name);
        tvSpeakerBio = findViewById(R.id.tv_speaker_bio);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workshop.getWorkshopStatusName().equals(Constant.ASK_FOR_PAY)) {
                    Intent intent = new Intent(ShowWorkshopInfoActivity.this,PaymentMethodActivity.class);
                    intent.putExtra(IntentDataKey.MY_WORKSHOP,workshop);
                    startActivityForResult(intent,IntentDataKey.CHANGE_WORKSHOP_DATA_CODE);
                } else {
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(ShowWorkshopInfoActivity.this);
                    RequestAndResponse.sendWorkshopRequest(ShowWorkshopInfoActivity.this, workshop.getId(), new BaseResponseInterface<Workshop>() {
                        @Override
                        public void onSuccess(Workshop workshop) {
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
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(ShowWorkshopInfoActivity.this);
                RequestAndResponse.cancelWorkshop(ShowWorkshopInfoActivity.this, workshop.getId(), new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        workshop.setWorkshopStatusName("");
                        Intent resultIntent = new Intent();
                        workshop.setWorkshopId(workshop.getId());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentDataKey.CHANGE_WORKSHOP_DATA_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Workshop workshop = data.getParcelableExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY);
            if (workshop != null){
                tvStatus.setText(Constant.CASH);
                btnPay.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
            }


        }
    }
}
