package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.dialog.WorkShopDialogActivity;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserWorkshopFragment extends Fragment {

    private static UserWorkshopFragment userWorkshopFragment;
    private CompactCalendarView compactCalendarView;
    private TextView tvDate;

    public UserWorkshopFragment() {
        // Required empty public constructor
    }
    public static UserWorkshopFragment newInstance(){
        if(userWorkshopFragment == null){
            userWorkshopFragment = new UserWorkshopFragment();
        }
        return userWorkshopFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_workshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        tvDate = view.findViewById(R.id.tv_date);
        initObject();
        onClick();
    }

    private void initObject() {
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Utils.setCurrentDate(tvDate);
    }

    private void onClick() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                startActivity(new Intent(getActivity(), WorkShopDialogActivity.class));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Utils.setDate(tvDate,firstDayOfNewMonth);
            }
        });
    }
}
