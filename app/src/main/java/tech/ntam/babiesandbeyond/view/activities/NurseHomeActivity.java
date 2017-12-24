package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.Utils;

public class NurseHomeActivity extends AppCompatActivity {

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
    }

    private void findViewById() {
        tvDate = findViewById(R.id.tv_date);
        compactcalendarView = findViewById(R.id.compactcalendar_view);
        recycleView = findViewById(R.id.recycle_view);
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
