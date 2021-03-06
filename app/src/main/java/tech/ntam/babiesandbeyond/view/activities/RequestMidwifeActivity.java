package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.time.Period;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.interfaces.MidwifeRequestInterface;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.view.adapter.RequestMidwifeItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.mylibrary.interfaces.Constant;

public class RequestMidwifeActivity extends MyToolbar implements MidwifeRequestInterface {
    private final int REQUEST_CODE_NEW = 154;
    private final int REQUEST_CODE_EDIT = 152;
    private int PLACE_PICKER_REQUEST = 1;
    private int position = 0;
    private RecyclerView recyclerView;
    private RequestMidwifeItemAdapter adapter;
    private Button btnAddAppointment;
    private Midwife midwife;
    private int total = 0;
    private TextView tvTotal;
    private LinearLayout btnPay;
    private List<MidwifeRequestModel> midwifeRequestModels;
    private EditText etLocation;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_midwife);
        midwife = getIntent().getParcelableExtra(IntentDataKey.MIDWIFE);
        if (midwife == null)
            finish();
        findViewById();
        onClick();
    }

    private void onClick() {
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMidwifeActivity.this, AddRequestMidwifeActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE, midwife.getId());
                startActivityForResult(intent, REQUEST_CODE_NEW);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLocation.getText().toString().trim().isEmpty()) {
                    etLocation.setError(getString(R.string.enter_location));
                }else if (adapter == null || adapter.getList().size() == 0) {
                    Toast.makeText(RequestMidwifeActivity.this, "please " + getString(R.string.add_appointment), Toast.LENGTH_SHORT).show();
                } else if (adapter.getItemCount() == 1) {
                    reserveMidwife();
                } else {
                    midwifeRequestModels = adapter.getList();
                    int size = midwifeRequestModels.size();
                    for (int i = 1; i < size; i++) {
                        MidwifeRequestModel firstRequest = midwifeRequestModels.get(i - 1);
                        MidwifeRequestModel secondRequest = midwifeRequestModels.get(i);
                        if (firstRequest.getDate().equals(secondRequest.getDate())) {
                            if (firstRequest.getHourFrom() <= secondRequest.getHourTo()
                                    && secondRequest.getHourFrom() <= firstRequest.getHourFrom()) {
                                Toast.makeText(RequestMidwifeActivity.this, "please check your requests", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    reserveMidwife();
                }
            }
        });
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RequestMidwifeActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }

    private void findViewById() {
        setupToolbar(this, false, true, false);
        tvTitle.setText(midwife.getName());
        btnAddAppointment = findViewById(R.id.btn_add_appointment);
        btnPay = findViewById(R.id.btn_pay);
        tvTotal = findViewById(R.id.tv_total);
        recyclerView = findViewById(R.id.recycle_view);
        etLocation = findViewById(R.id.et_location);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PLACE_PICKER_REQUEST) {
                Place place = PlacePicker.getPlace(this, data);
                etLocation.setText(place.getAddress().toString());
                etLocation.setError(null);
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
            } else {
                MidwifeRequestModel request = data.getParcelableExtra(IntentDataKey.ADD_MIDWIFE_REQUEST);
                if (request != null) {
                    if (adapter == null) {
                        adapter = new RequestMidwifeItemAdapter(this);
                        recyclerView.setAdapter(adapter);
                    }
                    if (requestCode == REQUEST_CODE_NEW) {
                        adapter.insertItem(request);
                        updateTotalCost((int) MyDateTimeFactor.getHourBetweenTwoDate(request.getDateTimeTo(), request.getDateTimeFrom()), true);
                    } else {
                        // edit request
                        // 1- get old price
                        MidwifeRequestModel oldRequest = adapter.getItem(position);
                        // 2- remove old price
                        updateTotalCost((int) MyDateTimeFactor.getHourBetweenTwoDate(oldRequest.getDateTimeTo(), oldRequest.getDateTimeFrom()), false);
                        // 3- set new item
                        adapter.updateItem(request, position);
                        // 4- set new price
                        updateTotalCost((int) MyDateTimeFactor.getHourBetweenTwoDate(request.getDateTimeTo(), request.getDateTimeFrom()), true);

                    }
                }
            }
        }
    }

    private void reserveMidwife() {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.reserveMidwife(this, midwife.getId(), etLocation.getText().toString(), lng, lat, adapter.getList(), new BaseResponseInterface<MidwifeService>() {
            @Override
            public void onSuccess(MidwifeService midwifeService) {
                dialog.dismissMyDialog();
                midwifeService.setMidwifeStatus(Constant.PENDING);
                Toast.makeText(RequestMidwifeActivity.this, "Waiting admin approve", Toast.LENGTH_SHORT).show();
                ServiceSharedPref.setMyMidwife(RequestMidwifeActivity.this, midwifeService);
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                dialog.dismissMyDialog();
                Toast.makeText(RequestMidwifeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalCost(int numberOfHour, boolean positive) {
        int pricePerHour = Integer.parseInt(midwife.getPrice());
        if (positive)
            total = total + (pricePerHour * numberOfHour);
        else
            total = total - (pricePerHour * numberOfHour);
        if (total > 0) {
            tvTotal.setText(" (cost " + total + ")");
            btnPay.setVisibility(View.VISIBLE);
        } else {
            btnPay.setVisibility(View.GONE);
        }
    }


    @Override
    public void editRequest(MidwifeRequestModel midwifeRequestModel, int position) {
        this.position = position;
        Intent i = new Intent(this, AddRequestMidwifeActivity.class);
        i.putExtra(IntentDataKey.REQUEST, midwifeRequestModel);
        i.putExtra(IntentDataKey.MIDWIFE, midwife.getId());
        startActivityForResult(i, REQUEST_CODE_EDIT);
    }

    @Override
    public void removeRequest(MidwifeRequestModel midwifeRequestModel) {
        updateTotalCost((int) MyDateTimeFactor.getHourBetweenTwoDate(midwifeRequestModel.getDateTimeTo(), midwifeRequestModel.getDateTimeFrom()), false);
    }
}
