package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class ShowServiceInfoActivity extends MyToolbar {


    private TextView tvServiceType;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvStatus;
    private TextView tvFee;
    private Button btnPay, btnCancel;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service_info);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.service_information);
        findViewById();
        setData();
    }

    private void setData() {
        service = getIntent().getParcelableExtra(IntentDataKey.SHOW_SERVICE_DATA_KEY);
        if (service == null)
            finish();
        tvDateFrom.setText(service.getStartDate());
        tvTimeFrom.setText(service.getStartTime());
        tvDateTo.setText(service.getEndDate());
        tvTimeTo.setText(service.getEndTime());
        tvLocation.setText(service.getLocation());
        tvFee.setText(service.getPrice());
        tvServiceType.setText(service.getServiceTypeName());
        tvStatus.setText(service.getServiceStatusString());
        if (service.getServiceStatusString().equals(Constant.PENDING)) {
            btnPay.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (service.getServiceStatusString().equals(Constant.ASK_FOR_PAY)) {
            btnPay.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (service.getServiceStatusString().equals(Constant.DONE)) {
            btnPay.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
        } else if (service.getServiceStatusString().equals(Constant.CASH)) {
            btnPay.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
            tvStatus.setText(Constant.DONE+" ("+service.getServiceStatusString()+")");
        }
    }

    private void findViewById() {
        tvServiceType = findViewById(R.id.tv_service_type);
        tvDateFrom = findViewById(R.id.tv_date_from);
        tvTimeFrom = findViewById(R.id.tv_time_from);
        tvDateTo = findViewById(R.id.tv_date_to);
        tvTimeTo = findViewById(R.id.tv_time_to);
        tvLocation = findViewById(R.id.tv_location);
        tvStatus = findViewById(R.id.tv_status);
        tvFee = findViewById(R.id.tv_fee);
        btnPay = findViewById(R.id.tv_pay);
        btnCancel = findViewById(R.id.btn_cancel);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog myDialog = new MyDialog();
                myDialog.showMyDialog(ShowServiceInfoActivity.this);
                RequestAndResponse.cancelService(ShowServiceInfoActivity.this, service.getId(), new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(ShowServiceInfoActivity.this, s, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.CANCEL_TEXT, service.getId());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(ShowServiceInfoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        myDialog.dismissMyDialog();
                    }
                });
            }
        });
    }
}
