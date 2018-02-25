package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.controller.activities.UserSendRequestController;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class AddRequestMidwifeActivity extends MyToolbar {

    private CalendarView calendarView;
    private LinearLayout linearFrom;
    private TextView tvFrom;
    private LinearLayout linearTo;
    private TextView tvTo;
    private Button btnAdd;
    private UserSendRequestController userSendRequestController;
    private String time = "00:00";
    private int midwifeId;
    private MidwifeRequestModel midwifeRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request_midwife);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.add_appointment);
        midwifeId = getIntent().getIntExtra(IntentDataKey.MIDWIFE, 0);
        if(midwifeId == 0)
            finish();
        findViewById();
        onClick();
        setOlderRequest();
    }

    private void onClick() {
        linearFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserSendRequestController().showTime(getFragmentManager(), tvFrom, 00, 00);
            }
        });
        linearTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFrom.getText().toString().equals(time)) {
                    tvFrom.setError(getString(R.string.invalid_Time));
                } else {
                    tvFrom.setError(null);
                    String[] time = tvFrom.getText().toString().split(":");
                    int h = Integer.parseInt(time[0]);
                    int m = Integer.parseInt(time[1]);
                    getUserSendRequestController().showTime(getFragmentManager(), tvTo, h, m);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFrom.getText().toString().equals(time)) {
                    tvFrom.setError(getString(R.string.invalid_Time));
                } else if (tvTo.getText().toString().equals(time)) {
                    tvFrom.setError(null);
                    tvTo.setError(getString(R.string.invalid_Time));
                } else {
                    tvFrom.setError(null);
                    tvTo.setError(null);
                    checkMidwife(MyDateTimeFactor.convertTimestampToString(calendarView.getDate()),MyDateTimeFactor.convertTimestampToDayOfWeek(calendarView.getDate()));
                }
            }
        });
    }

    private void findViewById() {
        calendarView = findViewById(R.id.calendarView);
        linearFrom = findViewById(R.id.linear_from);
        tvFrom = findViewById(R.id.tv_from);
        linearTo = findViewById(R.id.linear_to);
        tvTo = findViewById(R.id.tv_to);
        btnAdd = findViewById(R.id.btn_add);
        calendarView.setMinDate(MyDateTimeFactor.getDateTimeAfter24Hour().getTimeInMillis());
    }

    private UserSendRequestController getUserSendRequestController() {
        if (userSendRequestController == null)
            userSendRequestController = new UserSendRequestController(this);
        return userSendRequestController;
    }

    private void checkMidwife(final String date, final String day) {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.checkMidwife(this, midwifeId, tvFrom.getText().toString()
                , tvTo.getText().toString(), date, new BaseResponseInterface<String>() {
                    @Override
                    public void onSuccess(String s) {
                        dialog.dismissMyDialog();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(IntentDataKey.ADD_MIDWIFE_REQUEST,
                                new MidwifeRequestModel(tvFrom.getText().toString(),tvTo.getText().toString(),date,day));
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(AddRequestMidwifeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        dialog.dismissMyDialog();
                    }
                });
    }
    private void setOlderRequest(){
        midwifeRequestModel = getIntent().getParcelableExtra(IntentDataKey.REQUEST);
        if(midwifeRequestModel == null)
            return;
        tvFrom.setText(midwifeRequestModel.getTimeFrom());
        tvTo.setText(midwifeRequestModel.getTimeTo());
        try {
            calendarView.setDate(MyDateTimeFactor.convertStringToTimestamp(midwifeRequestModel.getDate()));
        } catch (ParseException e) {

        }
    }
}
