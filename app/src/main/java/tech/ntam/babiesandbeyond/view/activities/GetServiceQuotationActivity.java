package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class GetServiceQuotationActivity extends MyToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_service_quotation);
        setupToolbar(this, false, true, true);
        tvTitle.setText("Get Service Quotation");
    }
}
