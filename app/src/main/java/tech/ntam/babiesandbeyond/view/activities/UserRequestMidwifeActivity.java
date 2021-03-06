package tech.ntam.babiesandbeyond.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.AvailableTimeMidwife;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.SectionOrRowMidwife;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeTimeSlots;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class UserRequestMidwifeActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvName, tvTotal, tvStatus, tvLocation, tvFees, tvBio;
    private RecyclerView recycleView;
    private Button btnCancel;
    private LinearLayout btnPay;
    private MidwifeService midwifeService;
    private String date;
    private int totalPrice = 0;
    private List<SectionOrRowMidwife> sectionOrRowMidwives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_midwife);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.midwife_service);
        findViewById();
        onClick();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MidwifeService item = ServiceSharedPref.getMyMidwife(this);
        if (item != null) {
            midwifeService = item;
            checkServiceStatus();
            ServiceSharedPref.clearMyMidwife(this);
        }
    }

    private void setData() {
        midwifeService = getIntent().getParcelableExtra(IntentDataKey.MY_SERVICE);//ServiceSharedPref.getMyMidwife(this);
        if (midwifeService == null)
            finish();
        tvName.append(" : " + midwifeService.getMidwifeName());
        tvLocation.setText(midwifeService.getLocation());
        tvBio.setText(midwifeService.getBio());
        if (!midwifeService.getMidwifePhoto().isEmpty())
            Utils.MyGlide(this, ivProfilePhoto, midwifeService.getMidwifePhoto());
        sectionOrRowMidwives = new ArrayList<>();
        for (MidwifeRequestModel item : midwifeService.getTimeSlots()) {
            if (!item.getDate().equals(date)) {
                // different day add section
                sectionOrRowMidwives.add(new SectionOrRowMidwife(
                        MyDateTimeFactor.convertDateStringToDayOfWeek(item.getDate()), item.getDate()));
                date = item.getDay();
            }
            // add row
            sectionOrRowMidwives.add(new SectionOrRowMidwife(new AvailableTimeMidwife(item.getTimeFrom24H(), item.getTimeTo24H())));
            calTotalCost(item.getDateTimeFrom(), item.getDateTimeTo());
        }
        // set total cost
        tvTotal.setText(" (cost " + totalPrice + ") ");
        MidwifeTimeSlots adapter = new MidwifeTimeSlots(this, sectionOrRowMidwives);
        recycleView.setAdapter(adapter);
        tvFees.append(" : " + String.valueOf(totalPrice) + " $");
        checkServiceStatus();

    }

    private void checkServiceStatus() {
        tvStatus.setText(getString(R.string.status) + " : " + midwifeService.getMidwifeStatus());
        if (midwifeService.getMidwifeStatus().equals(Constant.PENDING)) {
            btnPay.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (midwifeService.getMidwifeStatus().equals(Constant.ASK_FOR_PAY)) {
            btnPay.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (midwifeService.getMidwifeStatus().equals(Constant.CASH)||midwifeService.getMidwifeStatus().equals(Constant.PaymentOnline)) {
            btnPay.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
        }
    }

    private void onClick() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog dialog = new MyDialog();
                dialog.showMyDialog(UserRequestMidwifeActivity.this);
                RequestAndResponse.cancelResercationMidwife(UserRequestMidwifeActivity.this, midwifeService.getUniqueKey(), new BaseResponseInterface() {
                    @Override
                    public void onSuccess(Object o) {
                        dialog.dismissMyDialog();
                        midwifeService.setMidwifeStatus(Constant.CANCEL);
                        ServiceSharedPref.setMyMidwife(UserRequestMidwifeActivity.this, midwifeService);
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(UserRequestMidwifeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        dialog.dismissMyDialog();
                    }
                });
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRequestMidwifeActivity.this, PaymentMethodActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE, midwifeService);
                intent.putExtra(IntentDataKey.AMOUNT,totalPrice);
                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvName = findViewById(R.id.tv_name);
        recycleView = findViewById(R.id.recycle_view);
        btnCancel = findViewById(R.id.btn_cancel);
        btnPay = findViewById(R.id.btn_pay);
        tvTotal = findViewById(R.id.tv_total);
        tvStatus = findViewById(R.id.tv_status);
        tvLocation = findViewById(R.id.tv_location);
        tvFees = findViewById(R.id.tv_fees);
        tvBio = findViewById(R.id.tv_bio);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void calTotalCost(Date startHour, Date endHour) {
        // this method calculate total cost depend on hours

        // get number of hour between times
        int numberOfHour = (int) MyDateTimeFactor.getHourBetweenTwoDate(endHour, startHour);
        totalPrice = totalPrice + (midwifeService.getPricePerHour() * numberOfHour);
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
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            switch (action) {
                case "2": // update midwife ask for payment
                    btnPay.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    midwifeService.setMidwifeStatus(Constant.ASK_FOR_PAY);
                    break;
                case "3": // delete midwife ask for payment
                    Toast.makeText(context, "Your Request Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };

}
