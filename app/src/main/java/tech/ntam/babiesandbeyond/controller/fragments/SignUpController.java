package tech.ntam.babiesandbeyond.controller.fragments;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.api.api_model.response.ParentResponse;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;

/**
 * Created by bassiouny on 17/12/17.
 */

public class SignUpController {

    private Activity activity;

    public SignUpController(Activity activity) {
        this.activity = activity;
    }

    public void SignUp(final EditText email,final EditText name,final EditText phone,final EditText password) {
        RequestAndResponse
                .register(email.getText().toString(),
                        password.getText().toString(),
                        name.getText().toString(),
                        phone.getText().toString(), new BaseResponseInterface<ParentResponse>() {
                    @Override
                    public void onSuccess(ParentResponse parentResponse) {
                        if(parentResponse.getStatus()){
                            Toast.makeText(activity, parentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");
                            name.setText("");
                            phone.setText("");
                            activity.onBackPressed();
                        }
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
