package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserServiceFragment extends Fragment {

    private static UserServiceFragment userServiceFragment;
    private CompactCalendarView compactCalendarView;
    private TextView tvDate;
    private Button btnSendRequest;
    private Calendar cal;
    private int year, month; // get current year and get current month

    public UserServiceFragment() {
        // Required empty public constructor
    }

    public static UserServiceFragment newInstance() {
        if (userServiceFragment == null)
            userServiceFragment = new UserServiceFragment();
        return userServiceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        tvDate = view.findViewById(R.id.tv_date);
        btnSendRequest = view.findViewById(R.id.btn_send_request);
        initObject();
        onClick();


    }

    private void initObject() {
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Date date = new Date();
        setDate(date);

    }

    private void onClick() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setDate(firstDayOfNewMonth);
            }
        });
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void setDate(Date date) {
        cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        tvDate.setText(Utils.getMonthString(month) + " " + year);
    }
}
