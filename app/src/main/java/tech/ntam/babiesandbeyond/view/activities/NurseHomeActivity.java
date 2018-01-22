package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.adapter.TaskItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyTaskModel;
import tech.ntam.mylibrary.Utils;

public class NurseHomeActivity extends MyToolbar {

    private TextView tvDate;
    private CompactCalendarView compactcalendarView;
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        findViewById();
        initObject();
        onClick();
        setupToolbar(this, false, false,false);
        tvTitle.setText("Schedule");
        setDummyData();
    }

    private void setDummyData() {
        List<DummyTaskModel> dummyTaskModels = new ArrayList<>();
        DummyTaskModel dummyTaskModel1 = new DummyTaskModel("Martin Jackson", "2 Dec 12 pm - 3 Dec 5 pm", "location about Martin Jackson", 1);
        dummyTaskModels.add(dummyTaskModel1);
        DummyTaskModel dummyTaskModel2 = new DummyTaskModel("Martin Jackson 2", "9 Oct 12 pm - 9 Oct 5 pm", "location about Martin Jackson 2", 2);
        dummyTaskModels.add(dummyTaskModel2);
        DummyTaskModel dummyTaskModel3 = new DummyTaskModel("Martin Jackson 3", "15 Nov 12 pm - 20 Dec 7 pm", "location about Martin Jackson 3", 3);
        dummyTaskModels.add(dummyTaskModel3);
        //TaskItemAdapter taskItemAdapter = new TaskItemAdapter(this, dummyTaskModels);
        //recycleView.setAdapter(taskItemAdapter);
    }

    private void findViewById() {
        tvDate = findViewById(R.id.tv_date);
        compactcalendarView = findViewById(R.id.compactcalendar_view);
        recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initObject() {
        compactcalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Utils.setCurrentDate(tvDate);
    }

    private void onClick() {
        compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Utils.setDate(tvDate, firstDayOfNewMonth);
            }
        });
    }
}
