package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * Created by bassiouny on 06/01/18.
 */

public class UserProfileController {
    private Activity activity;

    public UserProfileController(Activity activity) {
        this.activity = activity;
    }

    public void getProfileData(final EditText etName
            , final EditText etPhone
            , final CircleImageView ivProfilePhoto
            , final Button btnChangePassword) {
        RequestAndResponse.getProfile(activity, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                updateInfoShared(user.getName(), user.getPhoto(), user.getPhone());
                getDataFromSharefPref(etName, etPhone, ivProfilePhoto,btnChangePassword);
            }

            @Override
            public void onFailed(String errorMessage) {
            }
        });
    }

    public void updateProfile(final String photo, final EditText etName, final EditText etPhone, final UpdateSuccess updateSuccess) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();
    }

    private void updateInfoShared(String name, String photo, String phone) {
        UserSharedPref.setUserInfo(activity, name, photo, phone);
    }

    private void updateInfoShared(String name, String phone) {
        UserSharedPref.setUserInfo(activity, name, phone);
    }

    public void getDataFromSharefPref(final EditText etName
            , final EditText etPhone
            , final CircleImageView ivProfilePhoto
            , Button btnChangePassword) {
        etName.setText(UserSharedPref.getName(activity));
        etPhone.setText(UserSharedPref.getPhone(activity));
        String photo = UserSharedPref.getPhoto(activity);
        if (!photo.isEmpty())
            Utils.MyGlide(activity, ivProfilePhoto, photo);
        if(UserSharedPref.getPassword(activity).isEmpty()){
            btnChangePassword.setVisibility(View.GONE);
        }else {
            btnChangePassword.setVisibility(View.VISIBLE);
        }
    }

    public interface UpdateSuccess {
        void updated();
    }
}
