package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.api.api_model.response.RegisterResponse;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
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
                .register(email, password, name, phone, new BaseResponseInterface<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse registerResponse) {
                        if (registerResponse != null) {
                            MyDialog.dismissMyDialog();
                            UserSharedPref.setUserInfo(activity, registerResponse.getUser().getUser_token(),registerResponse.getUser().getEmail());
                            Toast.makeText(activity, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            activity.finish();
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
