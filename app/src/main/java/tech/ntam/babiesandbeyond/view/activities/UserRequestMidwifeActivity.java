package tech.ntam.babiesandbeyond.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;

public class UserRequestMidwifeActivity extends AppCompatActivity {

    private CircleImageView ivProfilePhoto;
    private TextView tvName;
    private RecyclerView recycleView;
    private Button btnCancel;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_midwife);
        findViewById();
        onClick();
    }

    private void onClick() {
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvName = findViewById(R.id.tv_name);
        recycleView = findViewById(R.id.recycle_view);
        btnCancel = findViewById(R.id.btn_cancel);
        btnPay = findViewById(R.id.btn_pay);
    }
}
