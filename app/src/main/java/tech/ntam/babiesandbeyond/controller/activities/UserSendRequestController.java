package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.database.ServiceTypeDatabase;
import tech.ntam.babiesandbeyond.model.ServiceType;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;

/**
 * Created by bassiouny on 19/12/17.
 */

public class UserSendRequestController {

    private Activity activity;
    private Calendar now;

    public UserSendRequestController(Activity activity) {
        this.activity = activity;
        now = Calendar.getInstance();
    }

    public void showDateTime(final FragmentManager fragmentManager, final EditText editText) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        showDateTime(fragmentManager, editText, year, monthOfYear, dayOfMonth);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setAccentColor(activity.getResources()
                .getColor(R.color.colorPrimary));
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    public void showDateTime(FragmentManager fragmentManager, final EditText editText, final int year, final int monthOfYear, final int dayOfMonth) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                editText.setText(year + "-" + getValueDateDigit(monthOfYear + 1) + "-" + getValueDateDigit(dayOfMonth) + " " + getValueDateDigit(hourOfDay) + ":" + getValueDateDigit(minute) + ":" + getValueDateDigit(second));
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);

        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setThemeDark(true);
        timePickerDialog.setAccentColor(activity.getResources()
                .getColor(R.color.colorPrimary));
        timePickerDialog.show(fragmentManager, "Timepickerdialog");
    }

    public void loadServiceType(Spinner spinner) {
        //load service type form backend
        // ex nurse , midwife,babysitting

        /* this list temp*/
        List<String> list = new ArrayList<String>();
        for (ServiceType item : ServiceTypeDatabase.getServiceTypes()) {
            list.add(item.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void saveData(int serviceTypeId, String startDate, String endDate, String location) {
        MyDialog.showMyDialog(activity);
        // send new request to service to save it
        RequestAndResponse.requestService(activity, serviceTypeId, startDate, endDate, location, new BaseResponseInterface<ParentResponse>() {
            @Override
            public void onSuccess(ParentResponse parentResponse) {
                MyDialog.dismissMyDialog();
                Toast.makeText(activity, R.string.request_save, Toast.LENGTH_SHORT).show();
                activity.finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                MyDialog.dismissMyDialog();
            }
        });
    }

    public int getIdFromSpinner(String text) {
        int result = 0;
        for (ServiceType item : ServiceTypeDatabase.getServiceTypes()) {
            if (item.getName().equals(text)) {
                result = item.getId();
                break;
            }
        }
        return result;
    }

    private String getValueDateDigit(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
    }
}
