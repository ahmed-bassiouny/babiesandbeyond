package tech.ntam.adminapp.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.User;
import tech.ntam.mylibrary.MyDialog;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
    public void SignIn(String email, String password) {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(this);
        /*RequestAndResponse.login(this,email, password, new BaseResponseInterface<User>() {
            @Override
            public void onSuccess(User user) {
                myDialog.dismissMyDialog();
                if (user.getUserTypeId().equals(User.USER)) {
                    // save user information in sharedpref
                    UserSharedPref.setUserInfo(activity, user.getUser_token(),user.getEmail(),user.getId(),user.getName(),user.getPhoto(),user.getPhone(),false);
                    activity.startActivity(new Intent(activity, UserHomeActivity.class));
                    activity.finish();
                } else if (user.getUserTypeId().equals(User.NURSE)||user.getUserTypeId().equals(User.MIDWIFE)||user.getUserTypeId().equals(User.BABYSITTER)) {
                    UserSharedPref.setUserInfo(activity, user.getUser_token(),user.getEmail(),user.getId(),user.getName(),user.getPhoto(),user.getPhone(),true);
                    activity.startActivity(new Intent(activity, NurseTasksHomeActivity.class));
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
        });*/
    }
}
