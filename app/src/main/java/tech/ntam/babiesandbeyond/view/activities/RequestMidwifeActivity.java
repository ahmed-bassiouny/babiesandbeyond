package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.MidwifeRequest;
import tech.ntam.babiesandbeyond.view.adapter.RequestMidwifeItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;

public class RequestMidwifeActivity extends MyToolbar {

    private List<MidwifeRequest> midwifeRequests;
    private RecyclerView recyclerView;
    private RequestMidwifeItemAdapter adapter;
    private Button btnAddAppointment;
    private int midwifeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_midwife);
        findViewById();
        onClick();
        midwifeRequests = new ArrayList<>();
        midwifeId = getIntent().getIntExtra(IntentDataKey.MIDWIFE,0);
        if(midwifeId == 0)
            finish();
        // create dummy data
        midwifeRequests.add(new MidwifeRequest("12:30","16:30","8-8-2018","Sunday"));
        midwifeRequests.add(new MidwifeRequest("12:30","16:30","8-8-2018","Sunday"));
        midwifeRequests.add(new MidwifeRequest("12:30","16:30","8-8-2018","Sunday"));
        adapter = new RequestMidwifeItemAdapter(midwifeRequests);
        recyclerView.setAdapter(adapter);

    }

    private void onClick() {
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMidwifeActivity.this,AddRequestMidwifeActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE,midwifeId);
                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.add_request);
        btnAddAppointment = findViewById(R.id.btn_add_appointment);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
