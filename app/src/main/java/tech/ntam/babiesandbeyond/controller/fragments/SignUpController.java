package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.view.activities.ActiveAccountActivity;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.mylibrary.MyDialog;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignUpController {

    private Activity activity;

    public SignUpController(Activity activity) {
        this.activity = activity;
    }

    public void SignUp(String email, String name, String phone, String password) {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse
                .register(activity, email, password, name, phone, new BaseResponseInterface<UserData>() {
                    @Override
                    public void onSuccess(UserData userData) {
                        if (userData != null) {
                            User user = userData.getUser();
                            UserSharedPref.setUserInfo(activity, user.getUser_token(), user.getEmail(),user.getId(),user.getName(),user.getPhoto(),user.getPhone(),false,userData.getVerificationCode(),false);
                            Toast.makeText(activity, activity.getString(R.string.register_successful), Toast.LENGTH_SHORT).show();
                            myDialog.dismissMyDialog();
                            activity.finish();
                            activity.startActivity(new Intent(activity, ActiveAccountActivity.class));
                        }
                    }

                    @Override
                    public void onFailed(String errorMessage) {

                        myDialog.dismissMyDialog();
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
