package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 06/01/18.
 */

public class UserProfileController {
    private Activity activity;

    public UserProfileController(Activity activity) {
        this.activity = activity;
    }

    public void getProfileData(final EditText etName, final EditText etPhone, final CircleImageView ivProfilePhoto) {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.getProfile(activity, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                etName.setText(user.getName());
                etPhone.setText(user.getPhone());
                if(!user.getPhone().isEmpty())
                    Utils.MyGlide(activity, ivProfilePhoto, user.getPhoto());

                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfile(String photo ,EditText etName,EditText etPhone) {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.updateProfile(activity, etName.getText().toString(), etPhone.getText().toString(), photo, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }
}
