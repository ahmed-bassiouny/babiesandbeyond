package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.fragments.UserWorkshopController;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.babiesandbeyond.view.dialog.WorkShopDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserWorkshopFragment extends Fragment {

    private static UserWorkshopFragment userWorkshopFragment;
    private CompactCalendarView compactCalendarView;
    private TextView tvDate;
    private UserWorkshopController userWorkshopController;

    public UserWorkshopFragment() {
        // Required empty public constructor
    }


    public static UserWorkshopFragment newInstance() {
        if (userWorkshopFragment == null) {
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

    @Override
    public void onResume() {
        super.onResume();
        getUserWorkshopController().getWorkshop(compactCalendarView);
    }

    private void initObject() {
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Utils.setCurrentDate(tvDate);
    }

    private void onClick() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (compactCalendarView.getEvents(dateClicked).size() > 0) {
                    Event event = compactCalendarView.getEvents(dateClicked).get(0);
                    compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.gray_bold);
                    Workshop workshop= (Workshop) event.getData();
                    Intent intent = new Intent(getActivity(), WorkShopDialogActivity.class);
                    intent.putExtra(IntentDataKey.SHOW_WORKSHOP_DATA_KEY,workshop);
                    startActivity(intent);
                }else {
                    compactCalendarView.setCurrentSelectedDayBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Utils.setDate(tvDate, firstDayOfNewMonth);
            }
        });
    }

    private UserWorkshopController getUserWorkshopController() {
        if (userWorkshopController == null)
            userWorkshopController = new UserWorkshopController(getActivity());
        return userWorkshopController;
    }
}
