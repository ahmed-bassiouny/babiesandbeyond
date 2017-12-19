package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by bassiouny on 19/12/17.
 */

public class UserSendRequestController {

    public void showDateTime(DatePickerDialog.OnDateSetListener OnDateSetListener , FragmentManager fragmentManager){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                OnDateSetListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setAccentColor("#dbc0b6");
        dpd.show(fragmentManager, "Datepickerdialog");
    }
}
