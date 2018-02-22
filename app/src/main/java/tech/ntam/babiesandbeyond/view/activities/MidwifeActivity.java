package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class MidwifeActivity extends MyToolbar {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife);
        setupToolbar(this,false,true,false);
        tvTitle.setText(R.string.midwife_service);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        fetchData();

    }

    private void fetchData(){
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        RequestAndResponse.getAllMidwife(this, new BaseResponseInterface<List<Midwife>>() {
            @Override
            public void onSuccess(List<Midwife> midwives) {
                MidwifeItemAdapter midwifeItemAdapter = new MidwifeItemAdapter(MidwifeActivity.this,midwives);
                recyclerView.setAdapter(midwifeItemAdapter);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(MidwifeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }
}
