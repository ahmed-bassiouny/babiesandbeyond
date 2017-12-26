package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.dialog.ChangePasswordDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.QrCodeActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyClass;

public class UserProfileActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etName, etPhone;
    private Button btnChangePassword, btnViewHistory, btnViewQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById();
        onClick();
        setupToolbar(this,false,true);
        DummyClass.setTitleText(etName);
        etPhone.setText("4646464863");
    }

    private void onClick() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, ChangePasswordDialogActivity.class));

            }
        });
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, UserHistoryNotificationActivity.class));
            }
        });
        btnViewQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, QrCodeActivity.class));
            }
        });
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUploadPhoto = findViewById(R.id.tv_upload_photo);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnViewHistory = findViewById(R.id.btn_view_history);
        btnViewQrCode = findViewById(R.id.btn_view_qr_code);
    }
}
