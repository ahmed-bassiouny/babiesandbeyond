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
import tech.ntam.babiesandbeyond.view.dialog.EventDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyClass;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserEventsFragment extends Fragment {


    private CompactCalendarView compactCalendarView;
    private TextView tvDate;
    private static UserEventsFragment userEventsFragment;

    public UserEventsFragment() {
        // Required empty public constructor
    }

    public static UserEventsFragment newInstance() {
        if (userEventsFragment == null) {
            userEventsFragment = new UserEventsFragment();
        }
        return userEventsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_events, container, false);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
        titleToolbar.setTitleToolbar(getString(R.string.events));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getContext()!=null && isVisibleToUser){
            MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
            titleToolbar.setTitleToolbar(getString(R.string.events));
        }
    }

    private void initObject() {
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Utils.setCurrentDate(tvDate);
        DummyClass.setDaySelected(compactCalendarView);
    }

    private void onClick() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                startActivity(new Intent(getActivity(), EventDialogActivity.class));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Utils.setDate(tvDate, firstDayOfNewMonth);
            }
        });
    }
}
