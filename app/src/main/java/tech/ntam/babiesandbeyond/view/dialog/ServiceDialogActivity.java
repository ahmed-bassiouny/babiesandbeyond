package tech.ntam.babiesandbeyond.view.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.database.ServiceTypeDatabase;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceType;
import tech.ntam.mylibrary.IntentDataKey;

public class ServiceDialogActivity extends AppCompatActivity {

    private TextView tvServiceType;
    private TextView tvServiceDateTimeFrom;
    private TextView tvServiceDateTimeTo;
    private TextView tvStatus;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_dialog);

        tvServiceType = findViewById(R.id.tv_service_type);
        tvServiceDateTimeFrom = findViewById(R.id.tv_service_date_time_from);
        tvServiceDateTimeTo = findViewById(R.id.tv_service_date_time_to);
        tvStatus = findViewById(R.id.tv_status);
        tvLocation = findViewById(R.id.tv_location);
        setDate();
    }

    private void setDate() {
        Service service = getIntent().getParcelableExtra(IntentDataKey.SHOW_SERVICE_DATA_KEY);
        tvServiceDateTimeFrom.setText(service.getStartDate());
        tvServiceDateTimeTo.setText(service.getEndDate());
        tvLocation.setText(service.getLocation());
        tvStatus.setText("Pending");
        tvServiceType.setText(ServiceTypeDatabase.getServiceTypeNameFromId(service.getServiceTypeId()));
    }
}
