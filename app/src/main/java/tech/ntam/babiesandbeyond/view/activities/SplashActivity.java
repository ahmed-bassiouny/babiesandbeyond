package tech.ntam.babiesandbeyond.view.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.UserSharedPref;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                checkIfUserLoggedIn();
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkIfUserLoggedIn() {
        if (!UserSharedPref.getEmail(this).isEmpty() && UserSharedPref.isStaff(this)) {
            startActivity(new Intent(SplashActivity.this, NurseTasksHomeActivity.class));
            finish();
        } else if (!UserSharedPref.getEmail(this).isEmpty() && UserSharedPref.isActive(this)) {
            startActivity(new Intent(SplashActivity.this, UserHomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, SignIn_UpActivity.class));
            finish();
        }
    }
}
