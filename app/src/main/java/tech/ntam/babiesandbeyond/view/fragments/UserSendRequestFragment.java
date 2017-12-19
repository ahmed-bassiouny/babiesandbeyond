package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.fragments.UserSendRequestController;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserSendRequestFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener
        , TimePickerDialog.OnTimeSetListener {


    private Spinner spService;
    private EditText etChooseDateFrom, etChooseDateTo, etLocation;
    private UserSendRequestController userSendRequestController;

    public UserSendRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_send_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spService = view.findViewById(R.id.sp_service);
        etChooseDateFrom = view.findViewById(R.id.et_choose_date_from);
        etChooseDateTo = view.findViewById(R.id.et_choose_date_to);
        etLocation = view.findViewById(R.id.et_location);
        onClick();
    }

    private void onClick() {
        etChooseDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        UserSendRequestFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );*/
                //dpd.show(getActivity().getFragmentManager()   , "Datepickerdialog");
                getController().showDateTime(UserSendRequestFragment.this,getActivity().getFragmentManager());
            }
        });
    }

    private UserSendRequestController getController() {
        if (userSendRequestController == null) {
            userSendRequestController = new UserSendRequestController();
        }
        return userSendRequestController;
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.e("onDateSet: ", "year" + year);
        Log.e("onDateSet: ", "month" + monthOfYear);
        Log.e("onDateSet: ", "dayOfMonth" + dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Log.e("onTimeSet: ", "hourOfDay = " + hourOfDay);
        Log.e("onTimeSet: ", "minute = " + minute);
    }
}
