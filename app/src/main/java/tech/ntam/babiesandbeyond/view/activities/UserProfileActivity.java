package tech.ntam.babiesandbeyond.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mvc.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.controller.activities.UserProfileController;
import tech.ntam.babiesandbeyond.view.dialog.ChangePasswordDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.QrCodeActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.ProcessInterface;

public class UserProfileActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etName, etPhone;
    private Button btnChangePassword, btnViewHistory, btnViewQrCode;
    private UserProfileController userProfileController;
    private boolean editable = false;
    private String photo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById();
        onClick();
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.profile);
        getUserProfileController().getDataFromSharefPref(etName, etPhone, ivProfilePhoto, btnChangePassword);
        getUserProfileController().getProfileData(etName, etPhone, ivProfilePhoto, btnChangePassword);
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
                intent.putExtra(IntentDataKey.SHOW_HISTORY_DATA_KEY, true);
                startActivity(intent);
            }
        });
        btnViewQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, QrCodeActivity.class));
            }
        });
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable)
                    return;
                checkStoragePermissionGranted();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bitmap == null)
            return;
        ivProfilePhoto.setImageResource(0);
        ivProfilePhoto.setImageBitmap(bitmap);
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(UserProfileActivity.this);
        Utils.convertImageFromBitmapToStringBase64(bitmap, new ProcessInterface() {
            @Override
            public void completed(String item) {
                photo = item;
                dialog.dismissMyDialog();
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            if (editable) {
                // here i will save new data
                if (etName.getText().toString().trim().isEmpty()) {
                    etName.setError(getString(R.string.invalid_name));
                } else if (etPhone.getText().toString().isEmpty()) {
                    etPhone.setError(getString(R.string.invalid_phone));
                } else {
                    getUserProfileController().updateProfile(photo, etName, etPhone, new UserProfileController.UpdateSuccess() {
                        @Override
                        public void updated() {
                            etName.setEnabled(false);
                            etPhone.setEnabled(false);
                            item.setIcon(R.drawable.ic_edit_24dp);
                            editable = false;
                        }
                    });
                }
            } else {
                // here make edit text editable
                item.setIcon(R.drawable.ic_check_24dp);
                etName.setEnabled(true);
                etPhone.setEnabled(true);
                editable = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public UserProfileController getUserProfileController() {
        if (userProfileController == null)
            userProfileController = new UserProfileController(this);
        return userProfileController;
    }

    public void checkStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                ImagePicker.pickImage(UserProfileActivity.this, "Select your image:");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            ImagePicker.pickImage(UserProfileActivity.this, "Select your image:");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ImagePicker.pickImage(UserProfileActivity.this, "Select your image:");
        }
    }
}
