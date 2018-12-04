package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class GetServiceQuotationActivity extends MyToolbar {

    private Spinner spServiceName;
    private EditText etNoOfDay;
    private EditText etNoOfHour;
    private EditText etLocation;
    private EditText etSpecialRequest;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_service_quotation);
        setupToolbar(this, false, true, true);
        tvTitle.setText("Get Service Quotation");
        findView();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNoOfDay.getText().toString().trim().isEmpty()) {
                    etNoOfDay.setError("please fill field");
                } else if (etNoOfHour.getText().toString().trim().isEmpty()) {
                    etNoOfHour.setError("please fill field");
                } else if (etLocation.getText().toString().trim().isEmpty()) {
                    etLocation.setError("please fill field");
                } else if (etSpecialRequest.getText().toString().trim().isEmpty()) {
                    etSpecialRequest.setError("please fill field");
                } else if (Integer.parseInt(etNoOfHour.getText().toString()) > 24) {
                    Toast.makeText(GetServiceQuotationActivity.this, "maximum hours per day 24 hour", Toast.LENGTH_SHORT).show();
                } else {
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(GetServiceQuotationActivity.this);
                    RequestAndResponse.getServiceQuotation(GetServiceQuotationActivity.this,
                            spServiceName.getSelectedItem().toString(), etNoOfDay.getText().toString(),
                            etNoOfHour.getText().toString(), etSpecialRequest.getText().toString(),
                            etLocation.getText().toString(), new BaseResponseInterface<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    myDialog.dismissMyDialog();
                                    Toast.makeText(GetServiceQuotationActivity.this, s, Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailed(String errorMessage) {
                                    myDialog.dismissMyDialog();
                                    Toast.makeText(GetServiceQuotationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    public void findView() {
        spServiceName = findViewById(R.id.sp_service_name);
        etNoOfDay = findViewById(R.id.et_no_of_day);
        etNoOfHour = findViewById(R.id.et_no_of_hour);
        etLocation = findViewById(R.id.et_location);
        etSpecialRequest = findViewById(R.id.et_special_request);
        btnSubmit = findViewById(R.id.btn_submit);
    }
}
