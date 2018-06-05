package tech.ntam.adminapp.view.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.view.adapter.NurseItemAdapter;
import tech.ntam.adminapp.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class CalendarActivity extends MyToolbar {

    private CalendarView calendarView;
    private ProgressBar progress;
    private RecyclerView recycler;
    private String selectedDay;
    private NurseItemAdapter nurseItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setupToolbar("Nurse Calendar");
        calendarView = findViewById(R.id.calendarView);
        progress = findViewById(R.id.progress);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(this));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDay = year + "-" +getValueDateDigit(month+1)+"-"+getValueDateDigit(dayOfMonth);
                Log.e( "onSelectedDayChange: ",selectedDay );
                loadData();
            }
        });
    }

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        RequestAndResponse.getOccupiedNurses(this, selectedDay, new BaseResponseInterface<List<Service>>() {
            @Override
            public void onSuccess(List<Service> services) {
                nurseItemAdapter = new NurseItemAdapter(CalendarActivity.this, services);
                recycler.setAdapter(nurseItemAdapter);
                progress.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(CalendarActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
            }
        });
    }
    private String getValueDateDigit(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
    }
}
