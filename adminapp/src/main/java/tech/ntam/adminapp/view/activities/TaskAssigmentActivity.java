package tech.ntam.adminapp.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class TaskAssigmentActivity extends AppCompatActivity {


    private TextView tvTitle;
    private TextView tvClientName;
    private TextView tvLocation;
    private TextView tvDateTimeFrom;
    private TextView tvDateTimeTo;
    private TextView tvError;
    private Spinner spService;
    private Button btnCreateInvoice;
    private Request request;

    private List<String> availableServiceName;
    private List<Integer> availableServiceId;
    private ArrayAdapter<String> adapter;

    private String noStaff = "No staff available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assigment);
        findViewById();
        initObject();
        setDataRequest();
        onClick();
    }

    private void onClick() {
        btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(TaskAssigmentActivity.this);
                    RequestAndResponse.assignStaff(TaskAssigmentActivity.this,
                            request.getServiceTypeName(),
                            availableServiceId.get(spService.getSelectedItemPosition()),
                            request.getId(), request.getUserId(),
                            new BaseResponseInterface<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(TaskAssigmentActivity.this, s, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            setResult(Activity.RESULT_OK, new Intent());

                            finish();
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(TaskAssigmentActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                        }
                    });
                }
        });
    }

    private void initObject() {
        request = getIntent().getParcelableExtra(IntentDataKey.REQUEST);
        if (request == null)
            finish();
        availableServiceId = new ArrayList<>();
        availableServiceName = new ArrayList<>();
    }

    private void setDataRequest() {
        tvTitle.setText(request.getServiceTypeName()+" Assignment");
        tvClientName.setText(request.getUserName());
        tvLocation.setText(request.getLocation());
        tvDateTimeFrom.setText(request.getStartDateTime());
        tvDateTimeTo.setText(request.getEndDateTime());
        fetchData();
    }

    private void fetchData() {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getAvaliableStaff(this, request.getServiceTypeId(), request.getFullStartDate(), request.getFullEndDate(), new BaseResponseInterface<List<Service>>() {
            @Override
            public void onSuccess(final List<Service> services) {
                availableServiceName.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(Service item:services){
                            availableServiceId.add(item.getId());
                            availableServiceName.add(item.getName());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, availableServiceName);
                                adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                                spService.setAdapter(adapter);
                                if(availableServiceName.size()==0){
                                    tvError.setText(noStaff);
                                    spService.setEnabled(false);
                                    btnCreateInvoice.setEnabled(false);
                                }
                                myDialog.dismissMyDialog();
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onFailed(String errorMessage) {
                tvError.setText(errorMessage);
                spService.setEnabled(false);
                btnCreateInvoice.setEnabled(false);
                myDialog.dismissMyDialog();
            }
        });
    }

    private void findViewById() {
        tvTitle = findViewById(R.id.tv_title);
        tvClientName = findViewById(R.id.tv_client_name);
        tvLocation = findViewById(R.id.tv_location);
        tvDateTimeFrom = findViewById(R.id.tv_date_time_from);
        tvDateTimeTo = findViewById(R.id.tv_date_time_to);
        spService = findViewById(R.id.sp_service);
        btnCreateInvoice = findViewById(R.id.btn_create_invoice);
        tvError = findViewById(R.id.tv_error);
    }
}
