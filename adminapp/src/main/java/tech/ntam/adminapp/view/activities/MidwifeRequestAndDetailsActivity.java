package tech.ntam.adminapp.view.activities;

import android.app.Activity;
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
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.AvailableTimeMidwife;
import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.model.MidwifeRequestModel;
import tech.ntam.adminapp.model.MidwifeService;
import tech.ntam.adminapp.model.SectionOrRowMidwife;
import tech.ntam.adminapp.view.adapter.MidwifeTimeSlots;
import tech.ntam.adminapp.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class MidwifeRequestAndDetailsActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvNotAvailable;
    private TextView tvName, tvStatus, tvLocation, tvFees, tvBio;

    private RecyclerView recycleView;
    private Button btnApprove;
    private Midwife midwife;
    private MidwifeService midwifeService;
    private List<SectionOrRowMidwife> sectionOrRowMidwives;
    private String day;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife_request_and_details);
        setupToolbar(getString(R.string.midwife));

        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvName = findViewById(R.id.tv_name);
        tvStatus = findViewById(R.id.tv_status);
        tvLocation = findViewById(R.id.tv_location);
        tvFees = findViewById(R.id.tv_fees);
        tvBio = findViewById(R.id.tv_bio);
        recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        btnApprove = findViewById(R.id.btn_approve);
        tvNotAvailable = findViewById(R.id.tv_not_available);
        midwife = getIntent().getParcelableExtra(IntentDataKey.MIDWIFE);
        midwifeService = getIntent().getParcelableExtra(IntentDataKey.MIDWIFE_SERVICE);
        if(midwife !=null){
            setMidwife();
        }else {
            setMidwifeService();
        }
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveMidwifeRequest();
            }
        });
    }
    private void setMidwife(){
        tvName.setText(midwife.getName());
        if(!midwife.getPhoto().isEmpty())
            Utils.MyGlide(this,ivProfilePhoto,midwife.getPhoto());
        sectionOrRowMidwives = new ArrayList<>();
        for (MidwifeRequestModel item : midwife.getTimeSlotsMidwifeList()) {
            if(!item.getDay().equals(day)){
                // different day add section
                sectionOrRowMidwives.add(new SectionOrRowMidwife(item.getDay(),""));
                day = item.getDay();
            }
            // add row
            sectionOrRowMidwives.add(new SectionOrRowMidwife(new AvailableTimeMidwife(item.getTimeFrom(),item.getTimeTo())));
        }
        if(sectionOrRowMidwives.size()>0) {
            MidwifeTimeSlots adapter = new MidwifeTimeSlots(this, sectionOrRowMidwives);
            recycleView.setAdapter(adapter);
            recycleView.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);

        }else {
            recycleView.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }
        btnApprove.setVisibility(View.GONE);

    }
    private void setMidwifeService(){
        tvName.setText(midwifeService.getMidwifeName());
        tvStatus.setText(getString(R.string.status) + " : " + midwifeService.getMidwifeStatus());
        tvLocation.setText(midwifeService.getLocation());
        tvBio.setText(midwifeService.getBio());
        tvFees.setText(getString(R.string.fee)+" "+midwifeService.getPricePerHour()+" $");
        if(!midwifeService.getMidwifePhoto().isEmpty())
            Utils.MyGlide(this,ivProfilePhoto,midwifeService.getMidwifePhoto());

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
        btnApprove.setVisibility(View.VISIBLE);
    }
    private void approveMidwifeRequest(){
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.approveMidwifeRequest(this, midwifeService.getUniqueKey(), new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(MidwifeRequestAndDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(IntentDataKey.MIDWIFE_SERVICE, midwifeService.getUniqueKey());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(MidwifeRequestAndDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });
    }

}
