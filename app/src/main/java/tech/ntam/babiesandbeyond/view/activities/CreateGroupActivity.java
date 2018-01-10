package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.ImageFactor;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.ProcessInterface;

public class CreateGroupActivity extends MyToolbar {

    private CircleImageView ivProfilePhoto;
    private TextView tvUploadPhoto;
    private EditText etGroupName, etGroupDescription;
    private Button btnCreateGroup;
    private String photo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        setupToolbar(this, false, true, false);
        tvTitle.setText(R.string.new_group);
        findViewById();
        onCLick();
    }

    private void onCLick() {
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(CreateGroupActivity.this, "Select Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etGroupName.getText().toString().trim().isEmpty()) {
                    etGroupName.setError(getString(R.string.fill_data));
                } else if (etGroupDescription.getText().toString().trim().isEmpty()) {
                    etGroupDescription.setError(getString(R.string.fill_data));
                } else if (photo.isEmpty()) {
                    Toast.makeText(CreateGroupActivity.this, R.string.select_photo, Toast.LENGTH_SHORT).show();
                } else {
                    // save data and create group
                    final MyDialog myDialog =new MyDialog();
                    myDialog.showMyDialog(CreateGroupActivity.this);
                    RequestAndResponse.createGroup(CreateGroupActivity.this, etGroupName.getText().toString(),
                            etGroupDescription.getText().toString(), photo, new BaseResponseInterface<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    Toast.makeText(CreateGroupActivity.this, s, Toast.LENGTH_SHORT).show();
                                    myDialog.dismissMyDialog();
                                }

                                @Override
                                public void onFailed(String errorMessage) {
                                    Toast.makeText(CreateGroupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    myDialog.dismissMyDialog();
                                }
                            });
                }
            }
        });
    }

    private void findViewById() {
        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        tvUploadPhoto = findViewById(R.id.tv_upload_photo);
        etGroupName = findViewById(R.id.et_group_name);
        etGroupDescription = findViewById(R.id.et_group_description);
        btnCreateGroup = findViewById(R.id.btn_create_group);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(this);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Bitmap myBitmap = null;
                try {
                    myBitmap = ImageFactor.getBitmapImageFromFilePathAfterResize(imageFile);

                    ivProfilePhoto.setImageBitmap(myBitmap);
                    Utils.convertImageFromBitmapToStringBase64(myBitmap, new ProcessInterface() {
                        @Override
                        public void completed(String item) {
                            photo = item;
                            myDialog.dismissMyDialog();
                        }
                    });
                } catch (FileNotFoundException e) {
                    Toast.makeText(CreateGroupActivity.this, R.string.photo_large, Toast.LENGTH_SHORT).show();
                    myDialog.dismissMyDialog();
                }
            }

        });
    }
}
