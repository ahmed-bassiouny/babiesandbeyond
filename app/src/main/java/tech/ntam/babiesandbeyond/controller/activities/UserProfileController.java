package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
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
        RequestAndResponse.getProfile(activity, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                updateInfoShared(user.getName(), user.getPhoto(), user.getPhone());
                getDataFromSharefPref(etName,etPhone,ivProfilePhoto);
            }

            @Override
            public void onFailed(String errorMessage) {
            }
        });
    }

    public void updateProfile(String photo, final EditText etName, final EditText etPhone, final UpdateSuccess updateSuccess) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.updateProfile(activity, etName.getText().toString(), etPhone.getText().toString(), photo, new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                updateInfoShared(etName.getText().toString(), etPhone.getText().toString());
                updateSuccess.updated();
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }

    private void updateInfoShared(String name, String photo, String phone) {
        UserSharedPref.setUserInfo(activity, name, photo, phone);
    }
    private void updateInfoShared(String name, String phone) {
        UserSharedPref.setUserInfo(activity, name, phone);
    }

    public void getDataFromSharefPref(final EditText etName, final EditText etPhone, final CircleImageView ivProfilePhoto) {
        etName.setText(UserSharedPref.getName(activity));
        etPhone.setText(UserSharedPref.getPhone(activity));
        String photo = UserSharedPref.getPhoto(activity);
        if (!photo.isEmpty())
            Utils.MyGlide(activity, ivProfilePhoto, photo);
    }
    public interface UpdateSuccess{
        void updated();
    }
}
