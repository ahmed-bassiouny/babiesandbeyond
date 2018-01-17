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
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.view.dialog.EventDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyClass;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
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

    @Override
    public void onResume() {
        super.onResume();
        loadEvents();
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

    private void setEventInCalendar(List<tech.ntam.babiesandbeyond.model.Event> eventList) {
        List<Event> events = new ArrayList<>();
        for (tech.ntam.babiesandbeyond.model.Event item : eventList) {
            events.add(new Event(R.color.gray_bold, MyDateTimeFactor.convertStringToDate(item.getStartDate()).getTime(), item));
        }
        if (compactCalendarView != null)
            compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(events);
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
                    tech.ntam.babiesandbeyond.model.Event myEvent = (tech.ntam.babiesandbeyond.model.Event) event.getData();
                    Intent intent = new Intent(getActivity(), EventDialogActivity.class);
                    intent.putExtra(IntentDataKey.SHOW_EVENT_DATA_KEY,myEvent);
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
    private void loadEvents(){
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getContext());
        RequestAndResponse.getEvents(getContext(), new BaseResponseInterface<List<tech.ntam.babiesandbeyond.model.Event>>() {
            @Override
            public void onSuccess(List<tech.ntam.babiesandbeyond.model.Event> events) {
                setEventInCalendar(events);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }
}
