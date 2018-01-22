package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.view.activities.UserSendRequestActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.dialog.ServiceDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserServiceFragment extends Fragment {

    private static UserServiceFragment userServiceFragment;
    private CompactCalendarView compactCalendarView;
    private TextView tvDate;
    private Button btnSendRequest;

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

    private void setServiceInCalendar(List<Service> serviceList) {
        List<Event> events = new ArrayList<>();
        for (Service item : serviceList) {
            events.add(new Event(R.color.gray_bold, MyDateTimeFactor.convertStringToDate(item.getFullStartDate()).getTime(), item));
        }
        if (compactCalendarView != null)
            compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(events);
    }


    private void initObject() {
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        Date date = new Date();
        Utils.setDate(tvDate, date);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadService();
    }

    private void onClick() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (compactCalendarView.getEvents(dateClicked).size() > 0) {
                    Event event = compactCalendarView.getEvents(dateClicked).get(0);
                    compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.gray_bold);
                    Service service = (Service) event.getData();
                    Intent intent = new Intent(getActivity(), ServiceDialogActivity.class);
                    intent.putExtra(IntentDataKey.SHOW_SERVICE_DATA_KEY, service);
                    startActivity(intent);
                } else {
                    compactCalendarView.setCurrentSelectedDayBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Utils.setDate(tvDate, firstDayOfNewMonth);
            }
        });
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserSendRequestActivity.class));
            }
        });
    }

    private void loadService() {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getActivity());
        RequestAndResponse.getMyService(getContext(), new BaseResponseInterface<UserService>() {
            @Override
            public void onSuccess(UserService userService) {
                if (userService != null) {
                    ServiceTypeList.setServiceTypes(userService.getServiceTypes());
                    setServiceInCalendar(userService.getServices());
                    myDialog.dismissMyDialog();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
