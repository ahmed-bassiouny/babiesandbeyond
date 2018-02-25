package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.Period;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.view.adapter.RequestMidwifeItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class RequestMidwifeActivity extends MyToolbar implements ParseObject<MidwifeRequestModel> {

    private final int REQUEST_CODE = 154;
    private RecyclerView recyclerView;
    private RequestMidwifeItemAdapter adapter;
    private Button btnAddAppointment,btnPay;
    private int midwifeId;
    private int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_midwife);
        findViewById();
        onClick();
        midwifeId = getIntent().getIntExtra(IntentDataKey.MIDWIFE, 0);
        if (midwifeId == 0)
            finish();

    }

    private void onClick() {
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMidwifeActivity.this, AddRequestMidwifeActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE, midwifeId);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter==null || adapter.getList().size()==0){
                    Toast.makeText(RequestMidwifeActivity.this, "please "+getString(R.string.add_appointment), Toast.LENGTH_SHORT).show();
                }else {
                    reserveMidwife();
                }
            }
        });
    }

    private void findViewById() {
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.add_request);
        btnAddAppointment = findViewById(R.id.btn_add_appointment);
        btnPay = findViewById(R.id.btn_pay);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            MidwifeRequestModel request = data.getParcelableExtra(IntentDataKey.ADD_MIDWIFE_REQUEST);
            if (request != null) {
                if (adapter == null) {
                    adapter = new RequestMidwifeItemAdapter(this);
                    recyclerView.setAdapter(adapter);
                }
                adapter.insertItem(request);
                updateTotalCost((int)MyDateTimeFactor.getHourBetweenTwoDate(request.getDateTimeTo(),request.getDateTimeFrom()),true);
            }

        }
    }
    private void reserveMidwife(){
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.reserveMidwife(this, midwifeId, adapter.getList(), new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                dialog.dismissMyDialog();
                Toast.makeText(RequestMidwifeActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String errorMessage) {
                dialog.dismissMyDialog();
                Toast.makeText(RequestMidwifeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalCost(int numberOfHour,boolean positive){
        int pricePerHour = 100;
        if(positive)
            total = total +(pricePerHour * numberOfHour);
        else
            total = total -(pricePerHour * numberOfHour);
        btnPay.setText(getString(R.string.pay));
        if(total > 0)
            btnPay.append(" total cost "+total);
    }

    @Override
    public void getMyObject(MidwifeRequestModel request) {
        updateTotalCost((int)MyDateTimeFactor.getHourBetweenTwoDate(request.getDateTimeTo(),request.getDateTimeFrom()),false);
    }
}
