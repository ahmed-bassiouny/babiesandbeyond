package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.AvailableTimeMidwife;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.SectionOrRowMidwife;
import tech.ntam.babiesandbeyond.model.TimeSlotsMidwife;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeTimeSlots;
import tech.ntam.mylibrary.IntentDataKey;

public class MidewifeTimeslotsActivity extends AppCompatActivity {

    private Midwife midwife;
    private RecyclerView recyclerView;
    private List<SectionOrRowMidwife> sectionOrRowMidwives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midewife_timeslots);
        findViewById();
        setData();
    }

    private void setData() {
        Bundle b = getIntent().getBundleExtra(IntentDataKey.BUNDLE);
        midwife = b.getParcelable(IntentDataKey.MIDWIFE);
        if(midwife == null)
            finish();
        sectionOrRowMidwives = new ArrayList<>();
        for (TimeSlotsMidwife item : midwife.getTimeSlotsMidwifeList()) {
            sectionOrRowMidwives.add(new SectionOrRowMidwife(item.getDay()));
            for(AvailableTimeMidwife itemTime:item.getAvailableTimeMidwives()){
                sectionOrRowMidwives.add(new SectionOrRowMidwife(itemTime));
            }
        }
        MidwifeTimeSlots adapter = new MidwifeTimeSlots(sectionOrRowMidwives);
        recyclerView.setAdapter(adapter);
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
