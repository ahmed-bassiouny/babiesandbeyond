package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.controller.activities.UserProfileController;
import tech.ntam.babiesandbeyond.view.dialog.ChangePasswordDialogActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.dialog.QrCodeActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyClass;
import tech.ntam.mylibrary.Utils;

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
        getUserProfileController().getProfileData(etName, etPhone, ivProfilePhoto);
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
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editable)
                    return;
                EasyImage.openChooserWithGallery(UserProfileActivity.this, "Select Photo",EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyDialog.showMyDialog(this);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                ivProfilePhoto.setImageBitmap(myBitmap);
                Utils.convertImageFromBitmapToStringBase64(myBitmap, new Utils.ProcessInterface() {
                    @Override
                    public void completed(String item) {
                        photo = item;
                        MyDialog.dismissMyDialog();
                    }
                });
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
        if (item.getItemId() == R.id.edit) {
            if (editable) {
                // here i will save new data
                etName.setEnabled(false);
                etPhone.setEnabled(false);
                item.setIcon(R.drawable.ic_edit_24dp);
                editable = false;
                getUserProfileController().updateProfile(photo,etName,etPhone);
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

}
