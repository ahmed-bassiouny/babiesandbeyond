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

    private final int REQUEST_CODE = 154;
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
        midwifeId = getIntent().getIntExtra(IntentDataKey.MIDWIFE, 0);
        if (midwifeId == 0)
            finish();

    }

    private void onClick() {
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMidwifeActivity.this, AddRequestMidwifeActivity.class);
                intent.putExtra(IntentDataKey.MIDWIFE, midwifeId);
                startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            MidwifeRequest request = data.getParcelableExtra(IntentDataKey.ADD_MIDWIFE_REQUEST);
            if (request != null) {
                if (adapter == null) {
                    adapter = new RequestMidwifeItemAdapter();
                    recyclerView.setAdapter(adapter);
                }
                adapter.insertItem(request);
            }

        }
    }

}
