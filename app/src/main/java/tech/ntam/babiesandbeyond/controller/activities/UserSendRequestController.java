package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.model.ServiceType;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by Developer on 19/12/17.
 */
public class UserSendRequestController {

    private Activity activity;
    private Calendar now;

    /**
     * Instantiates a new User send request controller.
     *
     * @param activity the activity
     */
    public UserSendRequestController(Activity activity) {
        this.activity = activity;
        now = Calendar.getInstance();
    }

    /**
     * Show date time.
     *
     * @param fragmentManager the fragment manager
     * @param editText        the edit text
     */
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
        dpd.setYearRange(Calendar.getInstance().get(Calendar.YEAR),(Calendar.getInstance().get(Calendar.YEAR)+1));
        dpd.setMinDate(MyDateTimeFactor.getDateTimeAfter24Hour());
        dpd.setAccentColor(activity.getResources()
                .getColor(R.color.colorPrimary));
        dpd.show(fragmentManager, "Datepickerdialog");
    }

    public void showDate(final FragmentManager fragmentManager, final EditText editText) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + getValueDateDigit(monthOfYear + 1) + "-" + getValueDateDigit(dayOfMonth);
                        editText.setText(date);
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

    /**
     * Show date time.
     *
     * @param fragmentManager the fragment manager
     * @param editText        the edit text
     * @param year            the year
     * @param monthOfYear     the month of year
     * @param dayOfMonth      the day of month
     */
    public void showDateTime(FragmentManager fragmentManager, final EditText editText, final int year, final int monthOfYear, final int dayOfMonth) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                String date = MyDateTimeFactor.changeFullDateTimeTODateTimeWithoutSecond(year + "-" + getValueDateDigit(monthOfYear + 1) + "-" + getValueDateDigit(dayOfMonth) + " " + getValueDateDigit(hourOfDay) + ":" + getValueDateDigit(minute) + ":" + getValueDateDigit(second));
                editText.setText(date);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);

        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setThemeDark(true);
        timePickerDialog.setAccentColor(activity.getResources()
                .getColor(R.color.colorPrimary));
        timePickerDialog.show(fragmentManager, "Timepickerdialog");
    }

    /**
     * Show time.
     *
     * @param fragmentManager the fragment manager
     * @param textView        the text view
     * @param h               the h
     */
    public void showTime(FragmentManager fragmentManager, final TextView textView,int h) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                textView.setText(MyDateTimeFactor.convertTimeFrom24To12(getValueDateDigit(hourOfDay) + ":" + "00"+":00"));
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);

        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setThemeDark(true);
        timePickerDialog.enableMinutes(false);
        timePickerDialog.setMinTime(h,00,00);
        timePickerDialog.setAccentColor(activity.getResources()
                .getColor(R.color.colorPrimary));
        timePickerDialog.show(fragmentManager, "Timepickerdialog");
    }

    /**
     * Save data.
     *
     * @param serviceTypeId the service type id
     * @param startDate     the start date
     * @param endDate       the end date
     * @param location      the location
     * @param lat           the lat
     * @param lng           the lng
     */
    public void saveData(int serviceTypeId, String startDate, String endDate, String location,double lat,double lng) {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(activity);
        // send new request to service to save it
        RequestAndResponse.requestService(activity, serviceTypeId, startDate, endDate, location
                ,lat,lng,"0","1990-01-01","",0,"", new BaseResponseInterface<Service>() {
            @Override
            public void onSuccess(Service service) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(IntentDataKey.ADD_SERVICE_DATA_KEY, service);
                activity.setResult(Activity.RESULT_OK, resultIntent);
                myDialog.dismissMyDialog();
                activity.finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }


    private String getValueDateDigit(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
    }
}
