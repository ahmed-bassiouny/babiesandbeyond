package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.AvailableTimeMidwife;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.SectionOrRowMidwife;
import tech.ntam.babiesandbeyond.model.TimeSlotsMidwife;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeTimeSlots;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

public class MidewifeTimeslotsActivity extends MyToolbar {

    private Midwife midwife;
    private RecyclerView recyclerView;
    private CircleImageView ivProfilePhoto;
    private TextView tvName;
    private Button btnAddRequest;
    private List<SectionOrRowMidwife> sectionOrRowMidwives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midewife_timeslots);
        setupToolbar(this,false,true,false);
        findViewById();
        setData();
    }

    private void setData() {
        Bundle b = getIntent().getBundleExtra(IntentDataKey.BUNDLE);
        midwife = b.getParcelable(IntentDataKey.MIDWIFE);
        if(midwife == null)
            finish();
        tvTitle.setText(midwife.getName());
        tvName.setText(midwife.getName());
        if(!midwife.getPhoto().isEmpty())
            Utils.MyGlide(this,ivProfilePhoto,midwife.getPhoto());
        sectionOrRowMidwives = new ArrayList<>();
        for (TimeSlotsMidwife item : midwife.getTimeSlotsMidwifeList()) {
            sectionOrRowMidwives.add(new SectionOrRowMidwife(item.getDay()));
            for(AvailableTimeMidwife itemTime:item.getAvailableTimeMidwives()){
                sectionOrRowMidwives.add(new SectionOrRowMidwife(itemTime));
            }
        }
        MidwifeTimeSlots adapter = new MidwifeTimeSlots(this,sectionOrRowMidwives);
        recyclerView.setAdapter(adapter);
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvName = findViewById(R.id.tv_name);
        btnAddRequest = findViewById(R.id.btn_add_request);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
