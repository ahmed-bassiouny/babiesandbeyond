package tech.ntam.adminapp.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import tech.ntam.adminapp.R;

public class ServiceDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvClientName;
    private TextView tvLocation;
    private TextView tvDateTimeFrom;
    private TextView tvDateTimeTo;
    private TextView tvChooseService;
    private Spinner spService;
    private Button btnCreateInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        findViewById();
        onClick();
    }

    private void onClick() {
        btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViewById() {
        tvTitle = findViewById(R.id.tv_title);
        tvClientName = findViewById(R.id.tv_client_name);
        tvLocation = findViewById(R.id.tv_location);
        tvDateTimeFrom = findViewById(R.id.tv_date_time_from);
        tvDateTimeTo = findViewById(R.id.tv_date_time_to);
        tvChooseService = findViewById(R.id.tv_choose_service);
        spService = findViewById(R.id.sp_service);
        btnCreateInvoice = findViewById(R.id.btn_create_invoice);
    }
}
