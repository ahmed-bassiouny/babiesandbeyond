package tech.ntam.adminapp.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;

public class AccountActivity extends MyToolbar {

    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private ImageView ivProfilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupToolbar(getString(R.string.account));
        findViewById();
        setData();
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(AccountActivity.this, R.style.AlertDialogStyle);
                } else {
                    builder = new AlertDialog.Builder(AccountActivity.this);
                }
                builder.setMessage("Are you sure you want to Logout?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UserSharedPref.clearShared(AccountActivity.this);
                                Intent intent = new Intent(AccountActivity.this, SignInActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    private void setData() {
        etName.setText(UserSharedPref.getName(AccountActivity.this));
        etPhone.setText(UserSharedPref.getPhone(AccountActivity.this));
        etEmail.setText(UserSharedPref.getEmail(AccountActivity.this));
        Utils.MyGlide(AccountActivity.this,ivProfilePhoto,UserSharedPref.getPhoto(AccountActivity.this));
    }

    private void findViewById() {
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
    }
}
