package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
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
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(activity);
        RequestAndResponse.login(activity,email, password, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                myDialog.dismissMyDialog();
                if (user.getUserTypeId().equals(User.USER)) {
                    // save user information in sharedpref
                    UserSharedPref.setUserInfo(activity, user.getUser_token(),user.getEmail(),user.getId(),user.getName(),user.getPhoto(),user.getPhone());
                    activity.startActivity(new Intent(activity, UserHomeActivity.class));
                    activity.finish();
                } else if (user.getUserTypeId().equals(User.STAFF)) {
                    activity.startActivity(new Intent(activity, NurseHomeActivity.class));
                    activity.finish();
                } else {
                    Toast.makeText(activity, R.string.user_not_found, Toast.LENGTH_SHORT).show();
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
