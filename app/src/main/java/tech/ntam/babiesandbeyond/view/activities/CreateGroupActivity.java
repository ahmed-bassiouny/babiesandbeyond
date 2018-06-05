package tech.ntam.babiesandbeyond.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
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
                ImagePicker.pickImage(CreateGroupActivity.this, "Select your image:");

            }
        });
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(CreateGroupActivity.this, "Select your image:");
            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etGroupName.getText().toString().trim().isEmpty()) {
                    etGroupName.setError(getString(R.string.fill_data));
                } else if (etGroupDescription.getText().toString().trim().isEmpty()) {
                    etGroupDescription.setError(getString(R.string.fill_data));
                } else {
                    final MyDialog myDialog = new MyDialog();
                    myDialog.showMyDialog(CreateGroupActivity.this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // save data and create group
                            RequestAndResponse.createGroup(CreateGroupActivity.this, etGroupName.getText().toString(),
                                    etGroupDescription.getText().toString(), photo, new BaseResponseInterface<Group>() {
                                        @Override
                                        public void onSuccess(Group group) {
                                            Toast.makeText(CreateGroupActivity.this, "You Request sent Successfully,wait the confirmation", Toast.LENGTH_SHORT).show();
                                            myDialog.dismissMyDialog();
                                            Intent resultIntent = new Intent();
                                            resultIntent.putExtra(IntentDataKey.ADD_GROUP_DATA_KEY, group);
                                            setResult(Activity.RESULT_OK, resultIntent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailed(final String errorMessage) {
                                            final String error = errorMessage;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(CreateGroupActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    myDialog.dismissMyDialog();
                                                }
                                            });
                                        }
                                    });
                        }
                    }).start();

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
        final Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if(bitmap == null)
            return;
        ivProfilePhoto.setImageBitmap(bitmap);
        Utils.convertImageFromBitmapToStringBase64(bitmap, new ProcessInterface() {
            @Override
            public void completed(String item) {
                photo = item;
            }
        });

    }
    /*
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
    }*/
}
