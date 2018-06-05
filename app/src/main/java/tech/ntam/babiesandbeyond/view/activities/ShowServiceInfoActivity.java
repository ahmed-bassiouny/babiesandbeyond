package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.model.Workshop;
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
    private TextView tvName, tvNameValue;
    private Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service_info);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.service_information);
        findViewById();
    }

    @Override
    protected void onResume() {
        super.onResume();
        service = ServiceSharedPref.getMyService(this);
        if (service == null)
            finish();
        setData();
    }

    private void setData() {
        tvDateFrom.setText(service.getStartDate());
        tvTimeFrom.setText(service.getStartTime());
        tvDateTo.setText(service.getEndDate());
        tvTimeTo.setText(service.getEndTime());
        tvLocation.setText(service.getLocation());
        tvFee.setText(service.getPrice());
        tvServiceType.setText(service.getServiceTypeName());
        tvStatus.setText(service.getServiceStatusString());
        if (service.getStaffName().isEmpty()) {
            tvName.setVisibility(View.INVISIBLE);
            tvNameValue.setVisibility(View.INVISIBLE);
        } else {
            tvName.setVisibility(View.VISIBLE);
            tvNameValue.setVisibility(View.VISIBLE);
            tvNameValue.setText(service.getStaffName());
        }
        if (service.getServiceStatusString().equals(Constant.PENDING)) {
            btnPay.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (service.getServiceStatusString().equals(Constant.ASK_FOR_PAY)) {
            btnPay.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (service.getServiceStatusString().equals(Constant.CASH)) {
            btnPay.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
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
        tvName = findViewById(R.id.tv_name);
        tvNameValue = findViewById(R.id.tv_name_value);
        btnCancel = findViewById(R.id.btn_cancel);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowServiceInfoActivity.this, PaymentMethodActivity.class);
                intent.putExtra(IntentDataKey.MY_SERVICE, service);
                startActivity(intent);
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
                        service.setServiceStatusName(Constant.CANCEL);
                        ServiceSharedPref.setMyService(ShowServiceInfoActivity.this, service);
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

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_SERVICE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String serviceId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
            if(!serviceId.equals(service.getIdString()))
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            String price = intent.getStringExtra(IntentDataKey.NOTIFICATION_SERVICE_PRICE);
            String staffName = intent.getStringExtra(IntentDataKey.NOTIFICATION_STAFF_NAME);
            switch (action) {
                case "0": // delete service
                    Toast.makeText(context, "Your Request Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case "1": // update service ask for payment
                    service.updateServiceStatusName();
                    service.setPrice(price);
                    service.setStaffName(staffName);
                    setData();
                    ServiceSharedPref.setMyService(ShowServiceInfoActivity.this,service);
                    break;
            }

        }
    };

}
