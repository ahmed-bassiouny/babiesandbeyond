package tech.ntam.adminapp.view.toolbar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tech.ntam.adminapp.R;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * Created by bassiouny on 26/12/17.
 */

public class MyToolbar extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ivBack;
    protected TextView tvTitle;

    protected void setupToolbar(String title) {
        toolbar = findViewById(R.id.my_toolbar);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.toolbar_title);
        tvTitle.setText(title);
        toolbar.setTitle("");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }
}
