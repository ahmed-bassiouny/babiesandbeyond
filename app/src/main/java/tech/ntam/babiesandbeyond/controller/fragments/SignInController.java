package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.NurseHomeActivity;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignInController {

    private Activity activity;

    public SignInController(Activity activity) {
        this.activity = activity;
    }

    public void SignIn(String email, String password) {
        MyDialog.showMyDialog(activity);
        RequestAndResponse.login(email, password, new BaseResponseInterface<UserData>() {
            @Override
            public void onSuccess(UserData userData) {
                MyDialog.dismissMyDialog();
                if (userData.getUser().getUserTypeId().equals(User.USER)) {
                    UserSharedPref.setUserInfo(activity, userData.getUser().getUser_token(),userData.getUser().getEmail());
                    activity.startActivity(new Intent(activity, UserHomeActivity.class));
                    activity.finish();
                } else if (userData.getUser().getUserTypeId().equals(User.USER)) {
                    activity.startActivity(new Intent(activity, NurseHomeActivity.class));
                    activity.finish();
                } else {
                    Toast.makeText(activity, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                MyDialog.dismissMyDialog();
                Toast.makeText(activity, R.string.user_not_found, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
