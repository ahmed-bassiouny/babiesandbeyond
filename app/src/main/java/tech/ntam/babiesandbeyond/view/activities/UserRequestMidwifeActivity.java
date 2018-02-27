package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.model.AvailableTimeMidwife;
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
    private TextView tvName;
    private RecyclerView recycleView;
    private Button btnCancel;
    private Button btnPay;
    private MidwifeService midwifeService;
    private String date;
    private List<SectionOrRowMidwife> sectionOrRowMidwives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_midwife);
        setupToolbar(this,false,true,false);
        tvTitle.setText(R.string.midwife_service);
        findViewById();
        onClick();
        setData();
    }

    private void setData() {
        midwifeService = ServiceSharedPref.getMyMidwife(this);
        if(midwifeService == null)
            finish();
        tvName.setText(midwifeService.getMidwifeName());
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
            sectionOrRowMidwives.add(new SectionOrRowMidwife(new AvailableTimeMidwife(item.getTimeFrom(), item.getTimeTo())));
        }
        MidwifeTimeSlots adapter = new MidwifeTimeSlots(this, sectionOrRowMidwives);
        recycleView.setAdapter(adapter);
        if(midwifeService.getMidwifeStatus().equals(Constant.PENDING)){
            btnCancel.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.GONE);
        }else {
            btnCancel.setVisibility(View.GONE);
            btnPay.setVisibility(View.GONE);
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
                        ServiceSharedPref.setMyMidwife(UserRequestMidwifeActivity.this,midwifeService);
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
                Intent intent = new Intent(UserRequestMidwifeActivity.this,PaymentMethodActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE,midwifeService);
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
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

}
