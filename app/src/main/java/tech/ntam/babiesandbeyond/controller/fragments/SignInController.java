package tech.ntam.babiesandbeyond.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.view.activities.NurseHomeActivity;
import tech.ntam.babiesandbeyond.view.activities.UserHomeActivity;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignInController {

    private Context context;

    public SignInController(Context context) {
        this.context = context;
    }

    public void SignIn(String email, String password) {
        RequestAndResponse.login(email, password, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(context, user.getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }
}
