package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class RequestOnlyNurseActivity extends MyToolbar {

    private Spinner spNurseType;
    private EditText etNumberOfChildren;
    private EditText etDateOfBirth;
    private EditText etNumberOfHours;
    private EditText etDateFrom;
    private EditText etLocation;
    private EditText etInfo;
    private CheckBox chIsComplex;
    private Button btnSubmit;
    private int PLACE_PICKER_REQUEST = 1;
    private double lat, lng;
    private int isComplex = 0;
    private UserSendRequestController userSendRequestController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_only_nurse);
        setupToolbar(this, false, true, true);
        tvTitle.setText("Nurse Service");
        findView();
        onClick();
    }

    public void findView() {
        spNurseType = findViewById(R.id.sp_nurse_type);
        etNumberOfChildren = findViewById(R.id.et_number_of_children);
        etDateOfBirth = findViewById(R.id.et_date_of_birth);
        etNumberOfHours = findViewById(R.id.et_number_of_hours);
        etDateFrom = findViewById(R.id.et_date_from);
        etLocation = findViewById(R.id.et_location);
        etInfo = findViewById(R.id.et_info);
        chIsComplex = findViewById(R.id.ch_is_complex);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void onClick() {
        etDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDateTime(getFragmentManager(), etDateFrom);
            }
        });
        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().showDate(getFragmentManager(), etDateOfBirth);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RequestOnlyNurseActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }

    private void sendRequest() {
        if (chIsComplex.isChecked()) {
            isComplex = 1;
        } else {
            isComplex = 0;
        }
        if (etDateFrom.getText().toString().trim().isEmpty()) {
            etDateFrom.setError(getString(R.string.select_date));
        } else if (etLocation.getText().toString().trim().length() < 10) {
            etLocation.setError(getString(R.string.enter_location));
        } else if (MyDateTimeFactor.convertStringToDateWithoutSecond(etDateFrom.getText().toString()).before(MyDateTimeFactor.getDateTimeAfter24Hour().getTime())) {
            Toast.makeText(this, "date from must be after 24 hours", Toast.LENGTH_LONG).show();
        } else if (etDateOfBirth.getText().toString().trim().isEmpty()) {
            etDateOfBirth.setError("Enter date of birth");
        } else if (etNumberOfHours.getText().toString().trim().isEmpty()) {
            etNumberOfHours.setError("Enter number of hour");
        } else {
            final MyDialog myDialog = new MyDialog();
            myDialog.showMyDialog(RequestOnlyNurseActivity.this);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
            try {
                cal.setTime(sdf.parse(etDateFrom.getText().toString()));
                cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(etNumberOfHours.getText().toString()));
                sdf.format(cal.getTime());
                RequestAndResponse.requestService(RequestOnlyNurseActivity.this,
                        IntentDataKey.NURSE_SERVICE,
                        MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(etDateFrom.getText().toString()),
                        MyDateTimeFactor.changeDateTimeWithoutSecondToFullDateTime(sdf.format(cal.getTime())),
                        etLocation.getText().toString(),
                        lat, lng, etNumberOfChildren.getText().toString(), etDateOfBirth.getText().toString(),
                        etInfo.getText().toString(), isComplex, spNurseType.getSelectedItem().toString(), new BaseResponseInterface<Service>() {
                            @Override
                            public void onSuccess(Service service) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(IntentDataKey.ADD_SERVICE_DATA_KEY, service);
                                setResult(Activity.RESULT_OK, resultIntent);
                                myDialog.dismissMyDialog();
                                finish();
                            }

                            @Override
                            public void onFailed(String errorMessage) {
                                Toast.makeText(RequestOnlyNurseActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                myDialog.dismissMyDialog();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private UserSendRequestController getController() {
        if (userSendRequestController == null) {
            userSendRequestController = new UserSendRequestController(this);
        }
        return userSendRequestController;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                etLocation.setText(place.getAddress().toString());
                etLocation.setError(null);
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
            }
        }
    }
}
