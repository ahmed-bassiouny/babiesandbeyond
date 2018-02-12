package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Date;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

public class UserSendRequestActivity extends MyToolbar {


    private Spinner spService;
    private EditText etChooseDateFrom, etChooseDateTo, etLocation;
    private UserSendRequestController userSendRequestController;
    private Button btnSend;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_request);
        findViewById();
        onClick();
        getController().loadServiceType(spService);
        setupToolbar(this, false, true, true);
        tvTitle.setText(R.string.request);
    }

    private void findViewById() {
        spService = findViewById(R.id.sp_service);
        etChooseDateFrom = findViewById(R.id.et_choose_date_from);
        etChooseDateTo = findViewById(R.id.et_choose_date_to);
        etLocation = findViewById(R.id.et_location);
        btnSend = findViewById(R.id.btn_send);
    }

    private void onClick() {
        etChooseDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDateTime(getFragmentManager(), etChooseDateFrom);
            }
        });
        etChooseDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDateTime(getFragmentManager(), etChooseDateTo);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromViewAndSendToService();
            }
        });
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(UserSendRequestActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                etLocation.setText(place.getAddress().toString());
            }
        }
    }

    private UserSendRequestController getController() {
        if (userSendRequestController == null) {
            userSendRequestController = new UserSendRequestController(this);
        }
        return userSendRequestController;
    }

    private void getDataFromViewAndSendToService() {
        if (etChooseDateFrom.getText().toString().trim().isEmpty()) {
            etChooseDateFrom.setError(getString(R.string.select_date));
        } else if (etChooseDateTo.getText().toString().trim().isEmpty()) {
            etChooseDateTo.setError(getString(R.string.select_date));
        } else if (etLocation.getText().toString().trim().length() < 10) {
            etLocation.setError(getString(R.string.enter_location));
        } else if (MyDateTimeFactor.convertStringToDate(etChooseDateFrom.getText().toString()).before(new Date())) {
            Toast.makeText(this, R.string.invalid_Date, Toast.LENGTH_SHORT).show();
        }  else if (MyDateTimeFactor.convertStringToDate(etChooseDateTo.getText().toString()).before(MyDateTimeFactor.convertStringToDate(etChooseDateFrom.getText().toString()))) {
            Toast.makeText(this, R.string.invalid_Date, Toast.LENGTH_SHORT).show();
        }else {
            /*getController().saveData(getController().getIdFromSpinner(spService.getSelectedItem().toString())
                    , etChooseDateFrom.getText().toString(), etChooseDateTo.getText().toString(), etLocation.getText().toString());*/
        }
    }
}
