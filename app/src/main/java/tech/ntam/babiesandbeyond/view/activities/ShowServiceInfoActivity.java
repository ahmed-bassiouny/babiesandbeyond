package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class ShowServiceInfoActivity extends MyToolbar {


    private TextView tvServiceType;
    private TextView tvDateFrom;
    private TextView tvTimeFrom;
    private TextView tvDateTo;
    private TextView tvTimeTo;
    private TextView tvLocation;
    private TextView tvStatus;
    private TextView tvFee;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service_info);
        setupToolbar(this,false,true,false);
        tvTitle.setText(R.string.service_information);
        findViewById();
        setData();
    }

    private void setData() {
        Service service = getIntent().getParcelableExtra(IntentDataKey.SHOW_SERVICE_DATA_KEY);
        if(service == null)
            finish();
        tvDateFrom.setText(service.getStartDate());
        tvTimeFrom.setText(service.getStartTime());
        tvDateTo.setText(service.getEndDate());
        tvTimeTo.setText(service.getEndTime());
        tvLocation.setText(service.getLocation());
        tvFee.setText(service.getPrice());
        tvServiceType.setText(ServiceTypeList.getServiceTypeNameFromId(service.getServiceTypeId()));
        tvStatus.setText(service.getServiceStatusString());
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
        btnPay .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
