package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class UserSendRequestActivity extends MyToolbar {


    private Spinner spService;
    private EditText etChooseDateFrom, etChooseDateTo, etLocation;
    private UserSendRequestController userSendRequestController;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_request);
        findViewById();
        onClick();
        getController().loadServiceType(spService);
        setupToolbar(this, false, true, true);
        tvTitle.setText("Request");
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
        } else {
            Toast.makeText(this, getController().getIdFromSpinner(spService.getSelectedItem().toString())+"", Toast.LENGTH_SHORT).show();
            //getController().saveData();
        }
    }
}
