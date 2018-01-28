package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.utils.UserSharedPref;
import tech.ntam.babiesandbeyond.view.fragments.SignInFragment;
import tech.ntam.babiesandbeyond.view.fragments.SignUpFragment;
import tech.ntam.mylibrary.Utils;

public class SignIn_UpActivity extends AppCompatActivity {


    private TextView tvSignIn;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_sign_in__up);
        findViewById();
        onClick();
        checkIfUserLoggedIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initObject();
    }

    private void checkIfUserLoggedIn() {
        if (!UserSharedPref.getEmail(this).isEmpty() && UserSharedPref.isStaff(this)) {
            startActivity(new Intent(SignIn_UpActivity.this, NurseTasksHomeActivity.class));
            finish();
        }else if (!UserSharedPref.getEmail(this).isEmpty()) {
            startActivity(new Intent(SignIn_UpActivity.this, UserHomeActivity.class));
            finish();
        }
    }

    private void initObject() {
        Utils.goToFragment(R.id.frame_fragment, this, SignInFragment.newInstance(), false, null);
    }

    private void onClick() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSignUp.setBackground(null);
                tvSignIn.setBackground(getResources().getDrawable(R.drawable.line));
                Utils.goToFragment(R.id.frame_fragment, SignIn_UpActivity.this, SignInFragment.newInstance(), false, null);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSignIn.setBackground(null);
                tvSignUp.setBackground(getResources().getDrawable(R.drawable.line));
                Utils.goToFragment(R.id.frame_fragment, SignIn_UpActivity.this, SignUpFragment.newInstance(), false, null);
            }
        });
    }

    private void findViewById() {
        tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignUp = findViewById(R.id.tv_sign_up);
    }
}
