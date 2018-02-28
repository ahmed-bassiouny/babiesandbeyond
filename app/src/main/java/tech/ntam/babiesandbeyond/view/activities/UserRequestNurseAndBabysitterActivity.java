package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;

public class UserRequestNurseAndBabysitterActivity extends MyToolbar {


    private EditText etChooseDateFrom, etChooseDateTo, etLocation;
    private UserSendRequestController userSendRequestController;
    private Button btnSend;
    private int PLACE_PICKER_REQUEST = 1;
    private int serviceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_nurse_and_babysitter);
        findViewById();
        onClick();
        setupToolbar(this, false, true, true);
        serviceSelected = getIntent().getIntExtra(IntentDataKey.SERVICE, 0);
        if (serviceSelected == 0)
            finish();
        if (serviceSelected == IntentDataKey.BABYSITTER_SERVICE)
            tvTitle.setText(R.string.babysitter_service);
        else if (serviceSelected == IntentDataKey.NURSE_SERVICE)
            tvTitle.setText(R.string.nurse_service);
    }

    private void findViewById() {
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
                    startActivityForResult(builder.build(UserRequestNurseAndBabysitterActivity.this), PLACE_PICKER_REQUEST);
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
                etLocation.setError(null);
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
        } else if (MyDateTimeFactor.convertStringToDateWithoutSecond(etChooseDateFrom.getText().toString()).before(MyDateTimeFactor.getDateTimeAfter24Hour().getTime())) {
            Toast.makeText(this, "date from must be after 24 hours", Toast.LENGTH_LONG).show();
        } else if (MyDateTimeFactor.convertStringToDateWithoutSecond(etChooseDateTo.getText().toString()).before(MyDateTimeFactor.convertStringToDateWithoutSecond(etChooseDateFrom.getText().toString()))) {
            Toast.makeText(this, "date to must be after "+etChooseDateFrom.getText().toString(), Toast.LENGTH_LONG).show();
        } else {
            getController().saveData(serviceSelected
                    , MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(etChooseDateFrom.getText().toString())
                    , MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(etChooseDateTo.getText().toString())
                    , etLocation.getText().toString());
        }
    }
}
