package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserProfileController;
import tech.ntam.babiesandbeyond.view.dialog.ChangePasswordDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.QrCodeActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyClass;

public class UserProfileActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etName, etPhone;
    private Button btnChangePassword, btnViewHistory, btnViewQrCode;
    private UserProfileController userProfileController;
    private boolean editable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById();
        onClick();
        setupToolbar(this, false, true,false);
        tvTitle.setText(R.string.profile);
        getUserProfileController().getProfileData(etName,etPhone,ivProfilePhoto);
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
                Intent intent = new Intent(UserProfileActivity.this, UserHistoryNotificationActivity.class);
                intent.putExtra("history", true);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.edit){
            if(editable){
                // here i will save new data
                etName.setEnabled(false);
                etPhone.setEnabled(false);
                item.setIcon(R.drawable.ic_edit_24dp);
                editable=false;
            }else {
                // here make edit text editable
                item.setIcon(R.drawable.ic_check_24dp);
                etName.setEnabled(true);
                etPhone.setEnabled(true);
                editable=true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public UserProfileController getUserProfileController(){
        if(userProfileController == null)
            userProfileController = new UserProfileController(this);
        return userProfileController;
    }
}
