package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tech.ntam.babiesandbeyond.R;

/**
 * Created by bassiouny on 19/12/17.
 */

public class UserSendRequestController {

    private Context context;
    private Calendar now;

    public UserSendRequestController(Context context) {
        this.context = context;
        now = Calendar.getInstance();
    }

    public void showDateTime(final FragmentManager fragmentManager, final EditText editText) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showDateTime(fragmentManager,editText,year,monthOfYear,dayOfMonth);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setAccentColor(context.getResources()
        .getColor(R.color.colorPrimary));
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    public void showDateTime(FragmentManager fragmentManager,final EditText editText, final int year, final int monthOfYear, final int dayOfMonth) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                editText.setText(year + "-" + monthOfYear + "-" + dayOfMonth + "-" + hourOfDay + ":" + minute);
            }
        },now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),now.get(Calendar.SECOND), true);

        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setThemeDark(true);
        timePickerDialog.setAccentColor(context.getResources()
                .getColor(R.color.colorPrimary));
        timePickerDialog.show(fragmentManager,"Timepickerdialog");
    }
    public void loadServiceType(Spinner spinner){
        //load service type form backend
       // ex nurse , midwife,babysitting

        /* this list temp*/
        List<String> list = new ArrayList<String>();

        list.add("Nurse");
        list.add("MideWife");
        list.add("Babysitting");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
