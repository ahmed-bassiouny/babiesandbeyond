package tech.ntam.babiesandbeyond.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;
import tech.ntam.babiesandbeyond.view.activities.NurseHomeActivity;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignInController {

    private Context context;

    public SignInController(Context context) {
        this.context = context;
    }

    public void SignIn(String email, String password) {
        MyDialog.showMyDialog(context);
        RequestAndResponse.login(email, password, new BaseResponseInterface<UserData>() {
            @Override
            public void onSuccess(UserData userData) {
                MyDialog.dismissMyDialog();
                if (userData.getUser().getUserTypeId().equals(User.USER)) {
                    context.startActivity(new Intent(context, UserHomeActivity.class));
                } else if (userData.getUser().getUserTypeId().equals(User.USER)) {
                    context.startActivity(new Intent(context, NurseHomeActivity.class));
                } else {
                    Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                MyDialog.dismissMyDialog();
                Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
