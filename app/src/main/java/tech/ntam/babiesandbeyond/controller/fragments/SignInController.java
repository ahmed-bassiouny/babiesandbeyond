package tech.ntam.babiesandbeyond.controller.fragments;

import android.content.Context;
import android.content.Intent;

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
        context.startActivity(new Intent(context, UserHomeActivity.class));
    }
}
