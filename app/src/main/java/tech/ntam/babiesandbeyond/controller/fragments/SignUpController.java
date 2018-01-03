package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignUpController {

    private Activity activity;

    public SignUpController(Activity activity) {
        this.activity = activity;
    }

    public void SignUp(String email, String name, String phone, String password) {
        MyDialog.showMyDialog(activity);
        RequestAndResponse
                .register(activity, email, password, name, phone, new BaseResponseInterface<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) {
                            MyDialog.dismissMyDialog();
                            UserSharedPref.setUserInfo(activity, user.getUser_token(), user.getEmail(),user.getId());
                            Toast.makeText(activity, activity.getString(R.string.register_successful), Toast.LENGTH_SHORT).show();
                            activity.finish();
                            activity.startActivity(new Intent(activity, UserHomeActivity.class));
                        }
                    }

                    @Override
                    public void onFailed(String errorMessage) {

                        MyDialog.dismissMyDialog();
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
