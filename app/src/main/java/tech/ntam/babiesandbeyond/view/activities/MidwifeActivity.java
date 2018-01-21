package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class MidwifeActivity extends MyToolbar {

    private EditText etLocation;
    private RecyclerView recyclerView;
    private Button btnSend;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife);
        setupToolbar(this,false,true,false);
        tvTitle.setText("Midwife Service");
        recyclerView = findViewById(R.id.recycle_view);
        etLocation = findViewById(R.id.et_location);
        btnSend = findViewById(R.id.btn_send);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MidwifeItemAdapter midwifeItemAdapter = new MidwifeItemAdapter();
        recyclerView.setAdapter(midwifeItemAdapter);
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MidwifeActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                etLocation.setText(place.getAddress().toString());
            }
        }
    }
}
