package tech.ntam.adminapp.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.IntentDataKey;

public class ServiceDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvBirthday;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        findViewById();
        setData();
    }
    
    private void findViewById() {
        tvTitle = findViewById(R.id.tv_title);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);
        tvBirthday = findViewById(R.id.tv_birthday);
    }
    private void setData(){
        service = getIntent().getParcelableExtra(IntentDataKey.SERVICE);
        if(service == null)
            finish();
        tvName.setText(service.getName());
        tvPhone.setText(service.getPhone());
        tvEmail.setText(service.getEmail());
        tvBirthday.setText(service.getBirthday());
    }
}
