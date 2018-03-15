package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class ContactActivity extends MyToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setupToolbar(this,false,true,false);
        tvTitle.setText(getString(R.string.contact_us));

    }
}
