package tech.ntam.babiesandbeyond.controller.activities;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

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

    public void getProfileData(final EditText etName, final EditText etPhone , final CircleImageView ivProfilePhoto){
        MyDialog.showMyDialog(activity);
        RequestAndResponse.getProfile(activity, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                MyDialog.dismissMyDialog();
                etName.setText(user.getName());
                etPhone.setText(user.getPhone());
                Utils.MyGlide(activity,ivProfilePhoto,user.getPhoto());
            }

            @Override
            public void onFailed(String errorMessage) {
                MyDialog.dismissMyDialog();
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
