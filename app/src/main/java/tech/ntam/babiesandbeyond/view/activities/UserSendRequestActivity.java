package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;

public class UserSendRequestActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener
        , TimePickerDialog.OnTimeSetListener {


    private Spinner spService;
    private EditText etChooseDateFrom, etChooseDateTo, etLocation;
    private UserSendRequestController userSendRequestController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_request);
        findViewById();
        onClick();
    }

    private void findViewById() {
        spService = findViewById(R.id.sp_service);
        etChooseDateFrom = findViewById(R.id.et_choose_date_from);
        etChooseDateTo = findViewById(R.id.et_choose_date_to);
        etLocation = findViewById(R.id.et_location);
    }
    private void onClick() {
        etChooseDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDateTime(UserSendRequestActivity.this,getFragmentManager());
            }
        });
        etChooseDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDateTime(UserSendRequestActivity.this,getFragmentManager());
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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
